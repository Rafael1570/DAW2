# Validación de la revisión de los seis PDF

Comando: `./mvnw -q clean install` — finalizado con código 0.

| Pruebas | Fallos | Errores | Omitidas |
|---:|---:|---:|---:|
| 13 | 0 | 0 | 0 |

## Comprobaciones realizadas

- El esquema solo contiene USERS, ROLES, USER_ROLES y USER_PROFILES. Las entidades se validan contra el esquema H2.
- CRUD de usuarios con todos los campos de cuenta, cálculo de caducidad a 90 días, contraseñas cifradas, duplicados y selección de roles.
- Paginación manual con CriteriaBuilder, setFirstResult/setMaxResults y UserPageDTO, con ordenación y roles técnicos sin duplicar usuarios. Las páginas numeradas conservan los parámetros.
- CRUD de roles y protección de roles asignados.
- Perfil en GET /profile/edit y POST /profile/update, atributo userProfileForm y clave userId.
- Persistencia 1:1 con clave compartida; campos planos del perfil en el detalle del usuario.
- Subida multipart, bytes y extensión originales, acceso HTTP al archivo, sustitución, conservación de la imagen y rollback sin archivos huérfanos.
- Validación de campos sin perder BindingResult y errores semánticos con flash y redirección.
- ResourceNotFoundException y DuplicateResourceException con resource, field y value.
- Vistas de usuario, rol y perfil en español e inglés; hashes ausentes del HTML; open-in-view desactivado.
- Navegador: tarjeta centrada, avatar antes del título, subtítulo, email readonly y desplegable real del avatar con Editar perfil. Captura: [perfil-revisado-pdf.jpg](perfil-revisado-pdf.jpg).

## Límites de esta validación

Se han ejecutado las pruebas y el JAR en el perfil H2 de demostración. MariaDB dispone de sus scripts y configuración, pero no se ha podido verificar su conexión porque Docker no está arrancado. El modo demo reinicia los datos al detenerse; las imágenes se guardan fuera del JAR.

La correspondencia por documento y los ajustes necesarios entre ejemplos se explican en [PASOS-PDF.md](PASOS-PDF.md).

## Verificación de DAO e Hibernate

La arquitectura final es Controller → Service → DAO. UserDAOImpl, RoleDAOImpl y UserProfileDAOImpl utilizan EntityManager con persist, merge, remove y consultas JPQL. Se ha verificado que EntityManagerFactory se puede desenvolver como org.hibernate.SessionFactory y que las clases de Spring Data JpaRepository y Pageable no están disponibles. El JAR contiene hibernate-core y no incluye bibliotecas spring-data.
