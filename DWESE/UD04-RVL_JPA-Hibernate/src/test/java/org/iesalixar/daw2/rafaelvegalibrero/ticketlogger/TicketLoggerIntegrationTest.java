package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.mappers.*;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
class TicketLoggerIntegrationTest {
    @Autowired MockMvc mvc;
    @Autowired RegionDAO regions;
    @Autowired ProvinceDAO provinces;

    @Test
    void initialDataAndPagination() throws Exception {
        assertThat(regions.countRegions()).isEqualTo(18);
        assertThat(provinces.countProvinces()).isEqualTo(52);
        mvc.perform(get("/regions")).andExpect(status().isOk())
                .andExpect(model().attribute("regions", hasSize(10)))
                .andExpect(model().attribute("totalPages", 2))
                .andExpect(model().attribute("totalElements", 18L));
        mvc.perform(get("/provinces").param("page", "5").param("size", "10"))
                .andExpect(status().isOk()).andExpect(model().attribute("provinces", hasSize(2)))
                .andExpect(model().attribute("offset", 50));
    }

    @Test
    void sortingAndLinksPreserveParameters() throws Exception {
        var asc = provinces.listProvincesPage(0, 10, "name", "asc");
        var desc = provinces.listProvincesPage(0, 10, "name", "desc");
        assertThat(asc.getFirst().getName()).isNotEqualTo(desc.getFirst().getName());
        assertThat(regions.listRegionsPage(0, 10, "code", "asc").getFirst().getCode()).isEqualTo("01");
        assertThat(regions.listRegionsPage(0, 10, "code", "desc").getFirst().getCode()).isEqualTo("18");
        mvc.perform(get("/provinces").param("sortField", "regionName").param("sortDir", "desc"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("sortField=regionName&amp;sortDir=desc")));
    }

    @Test
    void paginationNormalizesInvalidAndExtremeParameters() throws Exception {
        mvc.perform(get("/regions").param("page", "-2").param("size", "0")
                        .param("sortField", "unknown").param("sortDir", "wrong"))
                .andExpect(status().isOk()).andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("pageSize", 1)).andExpect(model().attribute("sortField", "name"))
                .andExpect(model().attribute("sortDir", "asc"));
        mvc.perform(get("/provinces").param("page", "2147483647").param("size", "2147483647"))
                .andExpect(status().isOk()).andExpect(model().attribute("currentPage", 0))
                .andExpect(model().attribute("pageSize", 100));
        mvc.perform(get("/regions").param("page", "999"))
                .andExpect(status().isOk()).andExpect(model().attribute("currentPage", 1));
    }

    @Test
    void allViewsAndDetachedDtoRelationsRender() throws Exception {
        for (String path : List.of("/", "/regions/new", "/provinces/new", "/regions/edit?id=1",
                "/provinces/edit?id=1", "/regions/detail?id=1", "/provinces/detail?id=1")) {
            mvc.perform(get(path)).andExpect(status().isOk()).andExpect(content().string(not(containsString("??msg."))));
        }
        mvc.perform(get("/regions/detail").param("id", "1"))
                .andExpect(model().attribute("region", instanceOf(RegionDetailDTO.class)))
                .andExpect(content().string(containsString("Sevilla")));
        mvc.perform(get("/provinces/detail").param("id", "1"))
                .andExpect(model().attribute("province", instanceOf(ProvinceDetailDTO.class)));
        var result = mvc.perform(get("/provinces")).andReturn();
        var data = (List<?>) result.getModelAndView().getModel().get("provinces");
        assertThat(data.getFirst()).isInstanceOf(ProvinceDTO.class);
    }

    @Test
    void spanishAndEnglishMessagesRender() throws Exception {
        mvc.perform(get("/regions").param("lang", "en"))
                .andExpect(status().isOk()).andExpect(content().string(containsString("Page 1 of 2")))
                .andExpect(content().string(containsString("View detail")));
        mvc.perform(get("/regions").param("lang", "es"))
                .andExpect(status().isOk()).andExpect(content().string(containsString("Página 1 de 2")));
    }

    @Test
    @Transactional
    void crudPersistsFormChangesAndRelations() throws Exception {
        mvc.perform(post("/regions/insert").param("code", "ZZ").param("name", "Región de prueba"))
                .andExpect(status().is3xxRedirection()).andExpect(flash().attributeCount(0));
        var region = regions.findAll().stream().filter(r -> r.getCode().equals("ZZ")).findFirst().orElseThrow();
        mvc.perform(post("/regions/update").param("id", region.getId().toString())
                        .param("code", "ZZ").param("name", "Región actualizada"))
                .andExpect(status().is3xxRedirection()).andExpect(flash().attributeCount(0));
        assertThat(regions.findById(region.getId()).orElseThrow().getName()).isEqualTo("Región actualizada");
        mvc.perform(post("/provinces/insert").param("code", "TEST").param("name", "Provincia de prueba")
                        .param("regionId", region.getId().toString()))
                .andExpect(status().is3xxRedirection()).andExpect(flash().attributeCount(0));
        var province = provinces.findAll().stream().filter(r -> r.getCode().equals("TEST")).findFirst().orElseThrow();
        mvc.perform(post("/provinces/update").param("id", province.getId().toString())
                        .param("code", "TEST").param("name", "Provincia actualizada").param("regionId", "1"))
                .andExpect(status().is3xxRedirection()).andExpect(flash().attributeCount(0));
        assertThat(provinces.findById(province.getId()).orElseThrow().getRegion().getId()).isEqualTo(1L);
        mvc.perform(post("/provinces/delete").param("id", province.getId().toString()))
                .andExpect(status().is3xxRedirection()).andExpect(flash().attributeCount(0));
        assertThat(provinces.findById(province.getId())).isEmpty();
        mvc.perform(post("/regions/delete").param("id", region.getId().toString()))
                .andExpect(status().is3xxRedirection()).andExpect(flash().attributeCount(0));
        assertThat(regions.findById(region.getId())).isEmpty();
    }

    @Test
    void formsRejectInvalidAndUnknownRegion() throws Exception {
        mvc.perform(post("/regions/insert").param("code", "").param("name", ""))
                .andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("region", "code", "name"));
        mvc.perform(post("/regions/update").param("code", "01").param("name", "Test"))
                .andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("region", "id"));
        for (String regionId : List.of("", "-1", "999999")) {
            mvc.perform(post("/provinces/insert").param("code", "ZZ").param("name", "Test")
                            .param("regionId", regionId))
                    .andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("province", "regionId"));
        }
    }

    @Test
    void duplicatesAndMissingRecordsHandled() throws Exception {
        mvc.perform(post("/regions/insert").param("code", "01").param("name", "Duplicada"))
                .andExpect(redirectedUrl("/regions/new")).andExpect(flash().attributeExists("errorMessage"));
        mvc.perform(post("/provinces/insert").param("code", "01").param("name", "Duplicada").param("regionId", "1"))
                .andExpect(redirectedUrl("/provinces/new")).andExpect(flash().attributeExists("errorMessage"));
        for (String entity : List.of("regions", "provinces")) {
            for (String action : List.of("edit", "detail")) {
                mvc.perform(get("/" + entity + "/" + action).param("id", "999999"))
                        .andExpect(redirectedUrl("/" + entity)).andExpect(flash().attributeExists("errorMessage"));
            }
        }
        long count = regions.countRegions();
        mvc.perform(post("/regions/update").param("id", "999999").param("code", "ZZ").param("name", "Test"))
                .andExpect(redirectedUrl("/regions")).andExpect(flash().attributeExists("errorMessage"));
        assertThat(regions.countRegions()).isEqualTo(count);
    }

    @Test
    @Transactional
    void emptyListsDoNotProducePhantomPages() throws Exception {
        for (var province : provinces.findAll()) provinces.deleteProvince(province.getId());
        for (var region : regions.findAll()) regions.deleteRegion(region.getId());
        for (String entity : List.of("regions", "provinces")) {
            mvc.perform(get("/" + entity)).andExpect(status().isOk())
                    .andExpect(model().attribute("totalPages", 0))
                    .andExpect(content().string(containsString("Página 0 de 0")));
        }
    }
    @Test
    @Transactional
    void updatingRegionKeepsItsProvincesAndDeleteCascades() throws Exception {
        long count = provinces.countProvinces();
        mvc.perform(post("/regions/update").param("id", "1").param("code", "01").param("name", "Andalucía actualizada"))
                .andExpect(status().is3xxRedirection()).andExpect(flash().attributeCount(0));
        assertThat(provinces.countProvinces()).isEqualTo(count);
        assertThat(provinces.findAll().stream().filter(pr -> pr.getRegion().getId().equals(1L))).hasSize(8);
        mvc.perform(post("/regions/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection()).andExpect(flash().attributeCount(0));
        assertThat(regions.findById(1L)).isEmpty();
        assertThat(provinces.countProvinces()).isEqualTo(count - 8);
    }

}
