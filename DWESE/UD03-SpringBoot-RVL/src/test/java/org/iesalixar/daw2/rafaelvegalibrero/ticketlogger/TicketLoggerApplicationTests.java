package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger;

import org.junit.jupiter.api.Test;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.ProvinceDAO;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.RegionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
@Transactional
class TicketLoggerApplicationTests {
    @Autowired MockMvc mvc;
    @Autowired ProvinceDAO provinces;
    @Autowired RegionDAO regions;

    @Test
    void allStagesRenderWithRealSeedData() throws Exception {
        org.junit.jupiter.api.Assertions.assertEquals(18, regions.findAll().size());
        org.junit.jupiter.api.Assertions.assertEquals(52, provinces.findAll().size());
        mvc.perform(get("/hello").param("name", "Rafael Vega Librero"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Rafael Vega Librero")));
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Proyecto de Rafael Vega Librero")));
        mvc.perform(get("/regions"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ANDALUCÍA")));
        mvc.perform(get("/provinces"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Araba/Álava")))
                .andExpect(content().string(containsString("PAÍS VASCO")));
        mvc.perform(get("/provinces/new"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Selecciona una Comunidad Autónoma")));
    }

    @Test
    void validationAndEnglishTranslationWork() throws Exception {
        mvc.perform(post("/regions/insert").param("code", "").param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("obligatorio")));
        mvc.perform(post("/provinces/insert").param("code", "99").param("name", "Prueba"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Debes seleccionar")));
        mvc.perform(get("/provinces").param("lang", "en"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Province List")));
    }

    @Test
    void provinceCanBeCreatedEditedAndDeleted() throws Exception {
        mvc.perform(post("/provinces/insert")
                .param("code", "99").param("name", "Prueba").param("region.id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/provinces"));
        mvc.perform(get("/provinces"))
                .andExpect(content().string(containsString("Prueba")));
        long newId = provinces.findAll().stream()
                .filter(province -> "99".equals(province.getCode()))
                .findFirst().orElseThrow().getId();
        mvc.perform(post("/provinces/update")
                .param("id", String.valueOf(newId)).param("code", "99")
                .param("name", "Prueba editada").param("region.id", "2"))
                .andExpect(status().is3xxRedirection());
        mvc.perform(get("/provinces"))
                .andExpect(content().string(containsString("Prueba editada")));
        // Existing seeded record: the edit form loads the related region.
        mvc.perform(get("/provinces/edit").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("selected=\"selected\">PAÍS VASCO")));
        mvc.perform(post("/provinces/delete").param("id", String.valueOf(newId)))
                .andExpect(status().is3xxRedirection());
        org.junit.jupiter.api.Assertions.assertTrue(provinces.findById(newId).isEmpty());
    }

    @Test
    void regionCrudRespectsProvinceForeignKey() throws Exception {
        mvc.perform(post("/regions/insert").param("code", "19").param("name", "PRUEBA"))
                .andExpect(status().is3xxRedirection());
        long newId = regions.findAll().stream()
                .filter(region -> "19".equals(region.getCode()))
                .findFirst().orElseThrow().getId();
        mvc.perform(post("/regions/update").param("id", String.valueOf(newId))
                .param("code", "19").param("name", "PRUEBA EDITADA"))
                .andExpect(status().is3xxRedirection());
        org.junit.jupiter.api.Assertions.assertEquals("PRUEBA EDITADA", regions.findById(newId).orElseThrow().getName());
        mvc.perform(post("/regions/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("errorMessage"));
        org.junit.jupiter.api.Assertions.assertTrue(regions.findById(1).isPresent());
        mvc.perform(post("/regions/delete").param("id", String.valueOf(newId)))
                .andExpect(status().is3xxRedirection());
        org.junit.jupiter.api.Assertions.assertTrue(regions.findById(newId).isEmpty());
    }
}
