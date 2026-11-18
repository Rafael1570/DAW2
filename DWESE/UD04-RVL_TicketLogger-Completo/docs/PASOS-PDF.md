# Revisión secuencial de UD04 (usuarios, roles y perfil)

## Paso 1 · UD04-1: JPA e Hibernate

Revisadas las páginas 48–76. En esta entrega el ejemplo se aplica a User y Role, según el alcance solicitado. Se conservan EntityManager, persistencia transaccional, consultas JPQL, claves IDENTITY, columnas explícitas y vistas de detalle. Se retira JDBC del modelo. El esquema de la aplicación solo contiene users, roles, user_roles y user_profiles.

El datasource principal utiliza MariaDB y el esquema se gestiona con SQL. H2 es únicamente el perfil de demostración y pruebas. La relación 1:1 y la relación N:M se revisan en sus pasos 4 y 5.

## Paso 2 · UD04-2: DTOs y mappers

Revisadas las páginas 9–33 y los campos de usuario que se utilizan posteriormente en UD04-5. Se restituyen los campos de cuenta, fechas e intentos de acceso en los DTOs y formularios de User. El detalle prepara también los datos planos del perfil. Las conversiones están centralizadas en UserMapper; las entidades no reciben validaciones de formulario.

El campo de entrada de contraseña conserva el nombre passwordHash de las capturas. El valor se cifra al guardar y el hash persistido no se devuelve a la vista. Esta diferencia respecto de las capturas evita usar un hash como contraseña en una edición.

## Paso 3 · UD04-3: paginación y ordenación

Revisadas las páginas 12–29. Se utiliza CriteriaBuilder para la consulta paginada de usuarios. La vista recibe currentPage, pageSize, offset, totalElements, totalPages, sortField, sortDir y reverseSortDir. Se restituye el paginador Bootstrap con Anterior, páginas numeradas, Siguiente y flechas de ordenación en las cabeceras. Los enlaces conservan tamaño y orden. Roles es una columna no ordenable, como concreta el PDF 5.

La entrega mantiene esta paginación manual también en el paso 6. UserPageDTO transporta los resultados sin depender de Spring Data.

## Paso 4 · UD04-4: perfil 1:1 e imágenes

Revisadas todas las capturas de las páginas 8–49. El formulario único de perfil usa UserProfileFormDTO.userId, el atributo userProfileForm, GET /profile/edit y POST /profile/update. La plantilla queda en views/user-profile/user-profile-form.html, con tarjeta centrada, imagen circular de 120 px antes del título, subtítulo, email readonly, campos con errores junto a cada uno y formulario multipart. El idioma es texto de hasta 10 caracteres, como en el ejemplo. Se recupera el desplegable del avatar del header del proyecto base con su enlace Editar perfil.

FileStorageService implementa saveFile y deleteFile, mantiene la extensión y los bytes del archivo y almacena en APP_UPLOAD_PATH/uploads. Los recursos externos se sirven con spring.web.resources.static-locations. Se conserva @MapsId y la PK/FK compartida. Los mensajes flash usan successMessage y errorMessage. Se retira la edición de perfiles por ID desde el CRUD: el taller presenta un único formulario Mi perfil con admin@app.local.

En el enunciado, el SQL limita nombre/apellidos a 60/80, mientras alguna captura Java usa 100/100. Se aplican 60/80 en todas las capas para no permitir formularios que después rechace la base de datos. La imagen se comprueba por contenido además del MIME y se eliminan archivos solo tras resolver la transacción.

## Paso 5 · UD04-5: usuarios y roles N:M

Revisadas las páginas 8–23. La relación usa un Set<Role> en User y un Set<User> inverso en Role, sin entidad para la tabla intermedia. roleIds es obligatorio en los DTOs de creación y edición. El selector muestra displayName y el nombre técnico entre paréntesis. UserDTO y UserDetailDTO entregan Set<String> con nombres técnicos de rol; listado y detalle los muestran como en las capturas. RoleDAO ordena por name y resuelve los IDs seleccionados.

Se mantienen las cuatro cuentas y las asignaciones de ejemplo. Las fechas del script de MariaDB siguen NOW()/DATE_ADD; el perfil demo tiene scripts equivalentes H2. El detalle del usuario recupera todos sus datos de cuenta y los campos planos de su perfil.

## Paso 6 · UD04-6: capa de servicios sobre DAO de Hibernate

Se conservan los DAO de Hibernate por indicación expresa del usuario. Aunque algunas capturas del sexto PDF utilizan repositorios, esta entrega aplica sus reglas de separación de responsabilidades con Controller → Service → DAO. No utiliza Spring Data, JpaRepository, Page ni Pageable.

UserService, RoleService y UserProfileService tienen interfaz e implementación. Los servicios validan las reglas, coordinan los mappers y DAO, y abren las transacciones. Los DAO implementan las consultas con EntityManager/JPQL y CriteriaBuilder, y las escrituras con persist/merge/remove. UserPageDTO contiene los usuarios, página, tamaño, totales y orden.

ResourceNotFoundException y DuplicateResourceException contienen resource, field y value. InvalidFileException añade el detalle del fichero. El servicio de perfil mantiene getFormByEmail y updateProfile y coordina UserProfileMapper, UserProfileDAO y FileStorageService. Los servicios no contienen mensajes i18n, modelos, vistas ni redirecciones.

Las pruebas confirman el proveedor Hibernate, la ausencia de Spring Data del classpath y los flujos de usuario, rol y perfil. El JAR tampoco contiene bibliotecas spring-data.
