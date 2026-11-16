# Ticket Logger · UD03 · Rafael Vega Librero

Práctica de Spring Boot 3.5.6, Java 21, Thymeleaf y JDBC Template. Incluye:

- UD03-1: proyecto Maven, configuración de base de datos y controlador `/hello`.
- UD03-2: layout responsive con fragmentos Thymeleaf reutilizables.
- UD03-3: CRUD de comunidades autónomas con JDBC Template.
- UD03-4: validación de formularios e internacionalización español/inglés.
- UD03-5: CRUD de provincias con relación 1:N con comunidades autónomas.

## Arranque para demostración

Este perfil usa H2 en memoria; no necesita Docker ni contraseñas:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=demo
```

Abrir `http://localhost:8080`. Los enlaces ES/EN cambian el idioma. La página
`/hello?name=Rafael%20Vega%20Librero` demuestra el primer controlador.

## Arranque con MariaDB

1. Copiar `.env.example` a `.env` y ajustar las credenciales.
2. Iniciar MariaDB con `docker compose up -d` si Docker está disponible.
3. Ejecutar `mvn spring-boot:run`.

La aplicación crea las tablas y carga 18 comunidades y 52 provincias mediante
`schema.sql` y `data.sql`. El perfil `demo` comprueba las mismas consultas y
pantallas con H2 en modo compatible con MySQL. La instalación de MariaDB no
estaba disponible durante la elaboración de estas evidencias.

## Comprobación

```bash
mvn test
```

Las pruebas cubren carga de datos, renderizado, validación, cambio de idioma,
CRUD de comunidades, CRUD de provincias y protección de la relación 1:N.
