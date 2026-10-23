# UD03-RVL_Spring-Boot

Aplicación de la UD03 de Desarrollo Web en Entorno Servidor, realizada por **Rafael Vega Librero** siguiendo los talleres UD03-1 a UD03-5. Incluye Spring Boot 3.5.6, Java 21, Spring MVC, Thymeleaf, JDBC, validación y localización español/inglés, además de CRUD de Comunidades Autónomas y Provincias con relación 1:N.

## Abrir en IntelliJ IDEA

Abre la carpeta del proyecto en IntelliJ IDEA y carga el `pom.xml` como proyecto Maven. Configura el Project SDK en Java 21. El proyecto contiene su configuración en `.idea`.

## Arranque local

1. Revisa las variables de `.env` (la base de datos y el usuario se configuran con `DB_URL`, `DB_DATABASE`, `DB_USER`, `DB_PASSWORD` y `DB_ROOT_PASSWORD`). MariaDB utiliza el puerto local `3310` (`DB_PORT`) para evitar conflictos con los otros proyectos.
2. Arranca MariaDB desde la raíz del proyecto con `docker compose up -d`.
3. Compila con `./mvnw clean install` y ejecuta con `./mvnw spring-boot:run`.
4. Abre `http://localhost:8080`. El selector de idioma está en la barra superior; también se admite `?lang=en` o `?lang=es`.

Spring Boot inicializa `schema.sql` y `data.sql` al arrancar. Los datos de ejemplo incluyen las 18 comunidades y las 52 provincias del taller.

## Funcionalidades

- CRUD de Comunidades Autónomas, validación de campos y control de códigos duplicados.
- CRUD de Provincias, con selección de Comunidad Autónoma, validación e identificación de duplicados.
- Listados relacionados mediante `JdbcTemplate` y un `RowMapper` de Provincia.
- Plantilla Thymeleaf reutilizable, Bootstrap y selector de idioma en español e inglés.
