# Ticket Logger · UD04 · Rafael Vega Librero

Proyecto de Desarrollo Web en Entorno Servidor, basado en `../UD03-RVL_Spring-Boot` y desarrollado en este orden:

1. **UD04-1:** JPA e Hibernate, DAO con EntityManager, relaciones y vistas de detalle.
2. **UD04-2:** DTOs de lectura, creación, edición y detalle; mappers manuales y validación de formularios.
3. **UD04-3:** paginación y ordenación de comunidades autónomas y provincias.

La explicación de cada cambio está en [PASO_A_PASO.md](docs/PASO_A_PASO.md). El estado de las comprobaciones está en [VERIFICACION.md](docs/VERIFICACION.md).

## Abrir el proyecto

Abre esta carpeta en IntelliJ IDEA como proyecto Maven y selecciona **Java 21**. Usa Spring Boot 3.5.6, Thymeleaf, Hibernate y MariaDB. No necesita Node.js.

## Probar sin Docker ni configurar credenciales

Desde esta carpeta:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
```

Abre [http://localhost:8082](http://localhost:8082). El perfil `demo` utiliza H2 en memoria con modo MariaDB, con 18 comunidades y 52 provincias. Los cambios de esta demostración se pierden al detener el proceso.

## Ejecutar con MariaDB

```bash
cp .env.example .env
```

Edita las contraseñas de `.env` y después ejecuta:

```bash
docker compose up -d --wait
./mvnw spring-boot:run
```

MariaDB usa el puerto local **3311**, la base **ticket_logger_ud04** y un volumen propio del proyecto. La aplicación usa **8082**, configurable con `SERVER_PORT`. Las variables de ejemplo no se han copiado desde ningún proyecto anterior. `.env` queda excluido de Git.

`schema.sql` mantiene la estructura manualmente y `data.sql` carga los datos de ejemplo mediante `INSERT IGNORE`. Hibernate tiene `ddl-auto=none`. Al reiniciar con MariaDB, los datos de ejemplo que se hayan borrado pueden volver a insertarse; es el comportamiento de inicialización del taller.

## Comprobar y empaquetar

```bash
./mvnw clean verify
```

Las pruebas usan el perfil `demo` y no necesitan MariaDB ni Docker. El JAR se genera en `target/dwese2627-ticket-logger-webapp-rafaelvegalibrero-4.0.0.jar`.

También puedes arrancarlo así:

```bash
java -jar target/dwese2627-ticket-logger-webapp-rafaelvegalibrero-4.0.0.jar --spring.profiles.active=demo
```

## Recorrido de uso

- `/regions` y `/provinces`: listados; pulsa los encabezados para ordenar y cambia de página.
- `/regions/new` y `/provinces/new`: creación; desde cada listado se accede a edición, detalle y borrado.
- `/regions/detail?id=1`: detalle de Andalucía con sus provincias.
- `/provinces/detail?id=1`: detalle de provincia con enlace a su comunidad.
- El selector de idioma permite español e inglés; también se admite `?lang=en` o `?lang=es`.

Ejemplo: `/provinces?page=1&size=10&sortField=regionName&sortDir=desc`.

**Borrado del taller:** `CascadeType.ALL` hace que eliminar una comunidad elimine también sus provincias. La interfaz muestra una confirmación que lo indica.

El proyecto está dentro del repositorio `Rafael1570/DAW2`, en `DWESE/UD04-RVL_JPA-Hibernate`. Los cambios están guardados localmente, sin commit ni envío a GitHub.
