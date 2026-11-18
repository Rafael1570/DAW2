package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.daos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.services.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.dtos.*;
import org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.exceptions.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:workshop;MODE=MariaDB;DB_CLOSE_DELAY=-1", "spring.jpa.show-sql=false", "spring.jpa.hibernate.ddl-auto=validate"})
@AutoConfigureMockMvc
@ActiveProfiles("demo")
class UserWorkshopIntegrationTest {
    static final Path uploadRoot;
    static {try {uploadRoot=Files.createTempDirectory("ud04-pdf-test-");}catch(Exception e){throw new ExceptionInInitializerError(e);}}
    @DynamicPropertySource static void properties(DynamicPropertyRegistry registry) {registry.add("app.upload-root",uploadRoot::toString);}
    @Autowired MockMvc mvc;
    @Autowired UserDAO userDAO;
    @Autowired UserProfileDAO profileDAO;
    @Autowired UserService users;
    @Autowired RoleService roles;
    @Autowired UserProfileService profiles;
    @Autowired FileStorageService files;
    @Autowired JdbcTemplate jdbc;
    @Autowired PasswordEncoder encoder;
    @Autowired PlatformTransactionManager transactions;

    @AfterAll static void cleanup() throws Exception {
        try(var paths=Files.walk(uploadRoot)){for(var p:paths.sorted(Comparator.reverseOrder()).toList()) Files.deleteIfExists(p);}
    }
    private UserCreateDTO user(String email) {
        var dto=new UserCreateDTO();dto.setEmail(email);dto.setPasswordHash("prueba123");dto.setRoleIds(Set.of(2L));return dto;
    }
    private MockMultipartFile picture(String format) throws Exception {
        var bytes=new ByteArrayOutputStream();ImageIO.write(new BufferedImage(3,3,BufferedImage.TYPE_INT_RGB),format,bytes);
        return new MockMultipartFile("profileImageFile","../../foto."+format,"image/"+format,bytes.toByteArray());
    }
    private Path localImage(String url) {return uploadRoot.resolve(url.substring(1));}
    private void clearProfile(Long userId) {
        String image=profileDAO.findByUserId(userId).map(p->p.getProfileImage()).orElse(null);
        jdbc.update("delete from user_profiles where user_id=?",userId);files.deleteFile(image);
    }
    @Test void schemaContainsOnlyTheFourWorkshopTables() throws Exception {
        assertThat(jdbc.queryForList("select table_name from information_schema.tables where table_schema='PUBLIC'",String.class))
            .containsExactlyInAnyOrder("USERS","ROLES","USER_ROLES","USER_PROFILES");
        assertThat(userDAO.countUsers()).isEqualTo(4);
        assertThat(userDAO.findByEmail("admin@app.local").orElseThrow().getLastPasswordChange()).isNotNull();
        for(String path:List.of("/regions","/provinces")) mvc.perform(get(path)).andExpect(status().isNotFound());
    }
    @Test void profileUsesTheRoutesNamesAndFieldsShownInThePdf() throws Exception {
        mvc.perform(get("/profile")).andExpect(redirectedUrl("/profile/edit"));
        mvc.perform(get("/profile/edit")).andExpect(status().isOk())
            .andExpect(view().name("views/user-profile/user-profile-form"))
            .andExpect(model().attribute("userProfileForm",hasProperty("email",equalTo("admin@app.local"))))
            .andExpect(content().string(containsString("action=\"/profile/update\"")))
            .andExpect(content().string(containsString("name=\"userId\"")))
            .andExpect(content().string(containsString("enctype=\"multipart/form-data\"")))
            .andExpect(content().string(containsString("form-card")))
            .andExpect(content().string(containsString("dropdown-menu-end")))
            .andExpect(content().string(containsString("href=\"/profile/edit\"")))
            .andExpect(content().string(containsString("readonly")));
    }
    @Test void allViewsRenderInSpanishAndEnglishWithoutEntitySessions() throws Exception {
        for(String language:List.of("es","en"))
            for(String path:List.of("/users","/users/new","/users/edit?id=1","/users/detail?id=1","/roles","/roles/new","/roles/edit?id=1","/roles/detail?id=1","/profile/edit"))
                mvc.perform(get(path).param("lang",language)).andExpect(status().isOk())
                    .andExpect(content().string(not(containsString("??msg."))))
                    .andExpect(content().string(not(containsString("$2a$"))));
    }
    @Test void committedUserCrudKeepsAccountFieldsPasswordProfileAndSharedRoles() throws Exception {
        mvc.perform(post("/users/insert").param("email","crud@app.local").param("passwordHash","prueba123")
            .param("roleIds","1","2").param("active","true").param("accountNonLocked","false")
            .param("emailVerified","true").param("mustChangePassword","true").param("failedLoginAttempts","3")
            .param("lastPasswordChange","2026-11-01T12:30"))
            .andExpect(redirectedUrl("/users")).andExpect(flash().attributeExists("successMessage"));
        var saved=userDAO.findByEmail("crud@app.local").orElseThrow();Long id=saved.getId();
        try {
            assertThat(encoder.matches("prueba123",saved.getPasswordHash())).isTrue();
            assertThat(saved.getPasswordExpiresAt()).isEqualTo(LocalDateTime.of(2026,11,1,12,30).plusDays(90));
            assertThat(saved.isEmailVerified()).isTrue();assertThat(saved.isAccountNonLocked()).isFalse();
            assertThat(saved.getFailedLoginAttempts()).isEqualTo(3);
            var dto=profiles.getFormByEmail(saved.getEmail());dto.setFirstName("Nombre");dto.setLastName("Apellidos");profiles.updateProfile(dto,null);
            mvc.perform(post("/users/update").param("id",id.toString()).param("email","editado@app.local")
                .param("passwordHash","").param("roleIds","3").param("active","true").param("accountNonLocked","true"))
                .andExpect(redirectedUrl("/users"));
            assertThat(userDAO.findById(id).orElseThrow().getPasswordHash()).isEqualTo(saved.getPasswordHash());
            assertThat(users.getDetail(id).getRoles()).containsExactly("ROLE_MANAGER");
            assertThat(users.getDetail(id).getFirstName()).isEqualTo("Nombre");
        } finally {users.delete(id);}
        assertThat(profileDAO.findByUserId(id)).isEmpty();assertThat(roles.list()).hasSize(3);
        assertThat(users.getDetail(1L).getRoles()).containsExactly("ROLE_ADMIN","ROLE_USER");
    }
    @Test void duplicateEmailMissingRolesAndInvalidFieldsKeepTheForm() throws Exception {
        for(String rolesValue:List.of("2","999")) {
            mvc.perform(post("/users/insert").param("email",rolesValue.equals("2")?"ADMIN@app.local":"bad@app.local")
                .param("passwordHash","prueba123").param("roleIds",rolesValue))
                .andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("user",rolesValue.equals("2")?"email":"roleIds"))
                .andExpect(model().attributeExists("allRoles"));
        }
        mvc.perform(post("/users/insert").param("email","bad").param("passwordHash","x").param("failedLoginAttempts","-1"))
            .andExpect(model().attributeHasFieldErrors("user","email","passwordHash","roleIds","failedLoginAttempts"));
        assertThat(userDAO.countUsers()).isEqualTo(4);
    }
    @Test void paginationUsesNumberedLinksTechnicalRoleNamesAndStablePages() throws Exception {
        var first=users.list(0,2,"email","asc");
        var second=users.list(1,2,"email","asc");
        assertThat(first.getTotalElements()).isEqualTo(4);
        assertThat(first.getUsers()).hasSize(2);assertThat(second.getUsers()).hasSize(2);
        assertThat(first.getUsers().getFirst().getRoles()).containsExactly("ROLE_ADMIN","ROLE_USER");
        mvc.perform(get("/users").param("size","2").param("sortField","email").param("sortDir","desc"))
            .andExpect(status().isOk()).andExpect(model().attribute("totalPages",2))
            .andExpect(content().string(containsString("pagination mb-0")))
            .andExpect(content().string(containsString("sortField=email&amp;sortDir=desc")))
            .andExpect(content().string(containsString("ROLE_USER")));
        mvc.perform(get("/users").param("page","999").param("size","2"))
            .andExpect(model().attribute("currentPage",1));
        mvc.perform(get("/users").param("page","-2").param("size","0").param("sortField","unknown"))
            .andExpect(model().attribute("currentPage",0)).andExpect(model().attribute("pageSize",1))
            .andExpect(model().attribute("sortField","email"));
    }
    @Test void profileCreatesSharedPrimaryKeyAndPreservesOriginalImageBytesAndExtension() throws Exception {
        try {
            var png=picture("png");
            mvc.perform(multipart("/profile/update").file(png).param("userId","1").param("email","admin@app.local")
                .param("firstName","Rafael").param("lastName","Vega").param("bio","Mi biografía").param("locale","es_ES"))
                .andExpect(redirectedUrl("/profile/edit")).andExpect(flash().attributeExists("successMessage"));
            var p=profileDAO.findByUserId(1L).orElseThrow();String previous=p.getProfileImage();
            assertThat(p.getId()).isEqualTo(1L);assertThat(localImage(previous)).hasBinaryContent(png.getBytes());
            mvc.perform(get(previous)).andExpect(status().isOk()).andExpect(content().bytes(png.getBytes()));
            mvc.perform(get("/profile/edit")).andExpect(model().attribute("navProfileImage",previous))
                .andExpect(model().attribute("userProfileForm",hasProperty("firstName",equalTo("Rafael"))));
            var gif=picture("gif");
            mvc.perform(multipart("/profile/update").file(gif).param("userId","1").param("firstName","Rafa").param("lastName","Vega"))
                .andExpect(flash().attributeExists("successMessage"));
            String current=profiles.getFormByEmail("admin@app.local").getProfileImage();
            assertThat(current).endsWith(".gif");assertThat(localImage(current)).hasBinaryContent(gif.getBytes());
            assertThat(localImage(previous)).doesNotExist();
            mvc.perform(post("/profile/update").param("userId","1").param("profileImage","/etc/passwd")
                .param("firstName","Editado").param("lastName","Vega"))
                .andExpect(redirectedUrl("/profile/edit"));
            assertThat(profiles.getFormByEmail("admin@app.local").getProfileImage()).isEqualTo(current);
            mvc.perform(get("/users/detail").param("id","1")).andExpect(content().string(containsString("Editado")));
        } finally {clearProfile(1L);}
    }
    @Test void invalidProfileFieldsKeepBindingAndInvalidImagesUseFlashRedirect() throws Exception {
        mvc.perform(post("/profile/update").param("userId","1").param("firstName","").param("lastName",""))
            .andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("userProfileForm","firstName","lastName"));
        for(var invalid:List.of(new MockMultipartFile("profileImageFile","fake.png","image/png","not an image".getBytes()),
                new MockMultipartFile("profileImageFile","big.png","image/png",new byte[2*1024*1024+1]))) {
            mvc.perform(multipart("/profile/update").file(invalid).param("userId","1").param("firstName","Nombre").param("lastName","Apellidos"))
                .andExpect(redirectedUrl("/profile/edit")).andExpect(flash().attributeExists("errorMessage"));
            assertThat(profileDAO.findByUserId(1L)).isEmpty();
        }
    }
    @Test void rollbackRemovesNewImageAndDoesNotLeaveAPartialProfile() throws Exception {
        users.create(user("rollback@app.local"));var saved=userDAO.findByEmail("rollback@app.local").orElseThrow();
        try {
            var dto=profiles.getFormByEmail(saved.getEmail());dto.setFirstName("Nombre");dto.setLastName("Apellidos");var image=picture("png");
            assertThatThrownBy(()->new TransactionTemplate(transactions).execute(status->{profiles.updateProfile(dto,image);throw new IllegalStateException("rollback");}))
                .isInstanceOf(IllegalStateException.class);
            assertThat(profileDAO.findByUserId(saved.getId())).isEmpty();
            assertThat(localImage(dto.getProfileImage())).doesNotExist();
        } finally {users.delete(saved.getId());}
    }
    @Test void roleCrudAndProtectionOfAssignedRolesWork() throws Exception {
        mvc.perform(post("/roles/insert").param("name","ROLE_EDITOR").param("displayName","Editor")).andExpect(redirectedUrl("/roles"));
        var role=roles.list().stream().filter(r->r.name().equals("ROLE_EDITOR")).findFirst().orElseThrow();
        try {
            mvc.perform(post("/roles/update").param("id",role.id().toString()).param("name","ROLE_EDITOR").param("displayName","Editor actualizado"))
                .andExpect(redirectedUrl("/roles"));
            mvc.perform(post("/roles/insert").param("name","ROLE_EDITOR").param("displayName","Repetido"))
                .andExpect(model().attributeHasFieldErrors("role","name"));
            assertThat(roles.getForEdit(role.id()).getDisplayName()).isEqualTo("Editor actualizado");
        } finally {roles.delete(role.id());}
        mvc.perform(post("/roles/delete").param("id","1")).andExpect(redirectedUrl("/roles"))
            .andExpect(flash().attributeExists("errorMessage"));
        assertThat(roles.getDetail(1L).users()).extracting(UserDTO::getEmail).contains("admin@app.local");
    }
    @Test void semanticExceptionsContainResourceFieldAndValue() {
        assertThatThrownBy(()->profiles.getFormByEmail("missing@app.local"))
            .isInstanceOfSatisfying(ResourceNotFoundException.class,e->{assertThat(e.getResource()).isEqualTo("user");assertThat(e.getField()).isEqualTo("email");assertThat(e.getValue()).isEqualTo("missing@app.local");});
        assertThatThrownBy(()->users.create(user("admin@app.local")))
            .isInstanceOfSatisfying(DuplicateResourceException.class,e->{assertThat(e.getResource()).isEqualTo("user");assertThat(e.getField()).isEqualTo("email");});
    }
    @Test void missingUsersBecomeMessagesAndMissingRolesBecome404() throws Exception {
        for(String path:List.of("/users/edit","/users/detail"))
            mvc.perform(get(path).param("id","999999")).andExpect(redirectedUrl("/users")).andExpect(flash().attributeExists("errorMessage"));
        mvc.perform(get("/roles/detail").param("id","999999")).andExpect(status().isNotFound());
    }
    @Autowired jakarta.persistence.EntityManagerFactory entityManagerFactory;
    @Test void persistenceUsesManualDaosAndHibernateWithoutSpringData() {
        assertThat(entityManagerFactory.unwrap(org.hibernate.SessionFactory.class)).isNotNull();
        assertThatThrownBy(()->Class.forName("org.springframework.data.jpa.repository.JpaRepository"))
            .isInstanceOf(ClassNotFoundException.class);
        assertThatThrownBy(()->Class.forName("org.springframework.data.domain.Pageable"))
            .isInstanceOf(ClassNotFoundException.class);
    }

}
