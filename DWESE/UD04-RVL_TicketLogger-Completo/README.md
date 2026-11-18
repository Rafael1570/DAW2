# UD04 · Usuarios, roles y perfil

Proyecto de Rafael Vega Librero para DAW2 / DWESE. Java 21, Spring Boot 3.5.6, Thymeleaf, JPA/Hibernate y MariaDB.

La entrega está limitada a **usuarios, roles y perfil**, según lo solicitado. La revisión de los seis documentos se recoge en [docs/PASOS-PDF.md](docs/PASOS-PDF.md), con un commit por paso. Los enunciados se conservan en `docs/enunciados/`.

## Arranque

```bash
cd /home/usuario-m/Documentos/DAW2/DWESE/UD04-RVL_TicketLogger-Completo
./mvnw clean install
./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
```

Abrir http://localhost:8084. El modo demo utiliza H2 en memoria: los datos se reinician al detener el proceso. Los archivos subidos permanecen en disco.

## Recorrido de la aplicación

1. **Usuarios** (`/users`): listado paginado, cabeceras ordenables con flechas, páginas numeradas, alta, edición, borrado y detalle. El formulario contiene los datos de cuenta y la selección múltiple de roles. La caducidad se calcula a 90 días del último cambio de contraseña.
2. **Roles** (`/roles`): CRUD y detalle con usuarios asociados. Un rol asignado debe desasignarse antes de eliminarlo.
3. **Avatar junto al idioma**: abre el desplegable **Editar perfil**, que lleva a `/profile/edit`.
4. **Mi perfil**: tarjeta centrada con avatar, título, subtítulo y email de solo lectura. Permite crear o actualizar nombre, apellidos, teléfono, biografía, idioma preferido e imagen. El formulario envía a `/profile/update` y vuelve a `/profile/edit` con mensaje de resultado.

Hasta que la práctica incorpora autenticación, Mi perfil utiliza `admin@app.local`. Es configurable con `PROFILE_EMAIL`. No hay login, cambio de contraseña de sesión ni logout. El menú ofrece el acceso de perfil que sí está implementado.

El modelo tiene únicamente `users`, `roles`, `user_roles` y `user_profiles`. La relación User–Role es N:M y la de User–UserProfile es 1:1 con `@MapsId`. Los perfiles no se insertan en el script de ejemplo: se crean al guardar el formulario.

## MariaDB persistente

Con Docker iniciado:

```bash
cp .env.example .env
docker compose up -d db
./mvnw spring-boot:run
```

No sobrescribir un `.env` personalizado. MariaDB usa el puerto 3312 y la aplicación el 8084. `schema.sql` y `data.sql` son los scripts de MariaDB, con las fechas y columnas descritas en el taller. El perfil demo utiliza sus equivalentes `schema-demo.sql` y `data-demo.sql`.

Los inserts de ejemplo usan `INSERT IGNORE`: al arrancar se reinsertan los datos iniciales que falten. Para dejar de cargar esos ejemplos después de preparar la base, usar `SPRING_SQL_INIT_MODE=never`.

## Imágenes

Configurar `APP_UPLOAD_PATH` (por defecto `./dwese-ticket-logger-images`). `FileStorageService.saveFile` crea archivos con nombre UUID en el subdirectorio `uploads`, conservando bytes y extensión. Spring los sirve en `/uploads/...` mediante `spring.web.resources.static-locations`.

El servicio de perfil valida imagen y límite de 2 MB. Al guardar sin archivo conserva la anterior; al sustituirla borra la antigua tras confirmar la transacción. Si la transacción se revierte, borra la nueva. Se admiten PNG, JPEG y GIF. Borrar un usuario elimina su perfil y su imagen.

## Organización final del PDF 6

`Controller → Service → DAO → Hibernate → base de datos`, con DTOs, mappers y excepciones. Los servicios tienen interfaz e implementación y llaman a DAO escritos manualmente.

- `UserDAO` / `UserDAOImpl`: CRUD, consultas JPQL y paginación con CriteriaBuilder, `setFirstResult` y `setMaxResults`.
- `RoleDAO` / `RoleDAOImpl`: CRUD de roles, resolución de IDs y consultas de asociaciones.
- `UserProfileDAO` / `UserProfileDAOImpl`: consultas, inserción y actualización del perfil con PK compartida.

Las implementaciones inyectan `EntityManager` con `@PersistenceContext` y utilizan `persist`, `merge` y `remove`. La paginación se transporta con `UserPageDTO`, sin `Page` ni `Pageable`. Se han eliminado los repositorios y las dependencias de Spring Data. Hibernate, Spring ORM y el datasource se configuran con Spring Boot.

La capa de servicios del PDF 6 se aplica sobre los DAO de Hibernate, según la indicación expresa del usuario.

Las excepciones contienen recurso, campo y valor. Los servicios no manejan mensajes i18n, modelos ni vistas. Los controladores gestionan validación de formulario, flash, redirecciones y traducción de errores. `spring.jpa.open-in-view=false` comprueba que las vistas reciben DTOs preparados.

## Ajustes necesarios entre los ejemplos

- Los ejemplos de los PDF 1–3 se aplican al módulo User; no se añaden otros módulos.
- Las longitudes de nombre y apellidos siguen el SQL (60/80); algunas capturas Java muestran 100/100.
- El campo de entrada `passwordHash` conserva el nombre de las capturas, pero se cifra con BCrypt al guardar. Los hashes no se envían a las vistas y una contraseña vacía al editar conserva la existente.
- Las diferencias de sintaxis SQL de H2 se aíslan en el perfil demo.
- Los archivos se comprueban por contenido y su limpieza respeta la confirmación de las transacciones.

## Validación

`./mvnw clean install` ejecuta las pruebas de integración y genera el JAR en `target/`. Los resultados actuales y la comprobación visual están en [docs/VALIDACION.md](docs/VALIDACION.md).
