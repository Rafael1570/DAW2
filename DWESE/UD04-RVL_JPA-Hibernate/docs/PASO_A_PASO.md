# Desarrollo paso a paso de UD04

Autor: Rafael Vega Librero. Fecha: 6 de noviembre de 2026.

## 0. Punto de partida y orden

El punto de partida es el proyecto JDBC de `DWESE/UD03-RVL_Spring-Boot`. Se ha copiado su aplicación a `DWESE/UD04-RVL_JPA-Hibernate` dentro del repositorio local DAW2, conservando el paquete `org.iesalixar.daw2.rafaelvegalibrero.ticketlogger`.

Se sigue la numeración didáctica **UD04-1 → UD04-2 → UD04-3**. Las versiones de JPA y DTOs con `(1)` tienen el mismo texto que las versiones sin sufijo; no requieren repetir la implementación. Los PDF son el material de referencia para el taller. Los pasos de Git que muestran se explican aquí; no se han ejecutado como órdenes de publicación o cambios de rama sobre todo el repositorio del alumno.

La carpeta contiene la versión final integrada de las tres etapas; esta guía explica su construcción, no son tres aplicaciones distintas.

## 1. UD04-1: JPA e Hibernate

### Paso 1. Sustituir la dependencia JDBC

En `pom.xml`, `spring-boot-starter-data-jdbc` se sustituye por `spring-boot-starter-data-jpa`. Spring Boot administra las versiones de Hibernate y Jakarta Persistence. Se mantienen MVC, Thymeleaf, Lombok, validación y el driver MariaDB.

### Paso 2. Configurar la persistencia

En `src/main/resources/application.properties` se configura el datasource con variables de entorno, el dialecto MariaDB, SQL visible y un pool de diez conexiones.

- `spring.jpa.hibernate.ddl-auto=none`: las tablas las crea `schema.sql`.
- `spring.sql.init.mode=always`: se ejecutan `schema.sql` y `data.sql` al arrancar.
- `spring.jpa.open-in-view=false`: las vistas no dependen de una sesión JPA abierta.

Los identificadores de SQL son BIGINT, compatibles con los Long de Java. Se usa una base distinta a la de UD03. Para probar sin Docker se añade `application-demo.properties`, que sobreescribe el datasource con H2 en memoria; no sustituye la configuración principal MariaDB.

### Paso 3. Mapear Region

En `entities/Region.java`:

1. `@Entity` y `@Table(name = "regions")` relacionan la clase con la tabla.
2. `@Id` y `@GeneratedValue(strategy = IDENTITY)` definen el identificador autogenerado.
3. `@Column` define código único, longitud y campos obligatorios.
4. `@OneToMany(mappedBy = "region", fetch = LAZY, cascade = ALL)` permite acceder a las provincias.

La colección se inicializa vacía. Se usan getters y setters de Lombok sin generar `toString` o igualdad recursiva sobre las relaciones.

### Paso 4. Mapear Province

En `entities/Province.java`, `@ManyToOne(fetch = LAZY, optional = false)` y `@JoinColumn(name = "region_id", nullable = false)` enlazan cada provincia con su comunidad. Province es el lado propietario de la relación.

### Paso 5. Migrar los DAO

`RegionDAOImpl` y `ProvinceDAOImpl` reciben `EntityManager` mediante `@PersistenceContext`.

- Los listados y las comprobaciones de código usan JPQL con parámetros.
- Las inserciones usan `persist` y los borrados `remove`.
- La edición de Province usa `merge`.
- La edición de Region modifica los campos del objeto gestionado. Esto conserva su colección de provincias, que no forma parte del formulario.
- Las escrituras usan `@Transactional`; las lecturas son transacciones de solo lectura.
- `flush` ejecuta las escrituras antes de devolver el control, de modo que el controlador puede gestionar los errores de integridad.

Se conserva la interfaz DAO del proyecto anterior y sus nombres `findAll`/`findById`. La migración no introduce Spring Data repositories para sustituir el ejercicio con EntityManager.

### Paso 6. Cargar las relaciones necesarias

El detalle de Region usa `LEFT JOIN FETCH r.provinces`; las consultas de Province cargan su Region con `JOIN FETCH`. De esta forma el mapper puede acceder a los datos necesarios después de la transacción del DAO, con `open-in-view=false`.

### Paso 7. Añadir los detalles

Los controladores incorporan `/regions/detail?id=...` y `/provinces/detail?id=...`. Las plantillas `region-detail.html` y `province-detail.html` muestran los datos de lectura y enlaces entre comunidad y provincias. Los listados incluyen “Ver detalle”. Un ID inexistente vuelve al listado con un mensaje.

**Comprobación de esta etapa:** compilación y respuestas HTTP 200 en listados, detalles y formulario de provincia.

## 2. UD04-2: DTOs y mappers

### Paso 8. Crear los DTOs de Region

Dentro de `dtos`:

- `RegionDTO`: id, code, name, para listados y opciones.
- `RegionCreateDTO`: formulario de alta; el ID debe estar vacío.
- `RegionUpdateDTO`: formulario de edición; requiere un ID positivo.
- `RegionDetailDTO`: id, code, name y `List<ProvinceDTO>`.

### Paso 9. Crear los DTOs de Province

- `ProvinceDTO`: id, code, name y **regionName**, suficiente para la tabla.
- `ProvinceCreateDTO`: alta con **regionId** obligatorio y positivo.
- `ProvinceUpdateDTO`: edición con ID y regionId obligatorios.
- `ProvinceDetailDTO`: incluye un `RegionDTO` para navegar a la comunidad.

Ningún DTO contiene entidades JPA. Las anotaciones `@NotBlank`, `@Size`, `@NotNull`, `@Positive` y `@Null` pertenecen a los formularios; las entidades conservan sus restricciones de persistencia.

### Paso 10. Crear los mappers manuales

`mappers/RegionMapper.java` y `ProvinceMapper.java` son clases utilitarias con métodos estáticos:

- `toDTO` y `toDTOList`: lectura de listados.
- `toDetailDTO`: lectura de detalles con las relaciones necesarias.
- `toUpdateDTO`: carga del formulario de edición.
- `toEntity`: conversión del alta o de la edición a entidad.

El mapper de Province recibe además la Region que el controlador ha localizado en la base. Así se comprueba la existencia de la relación y se evita aceptar un ID ficticio del formulario.

### Paso 11. Adaptar los controladores

Los modelos de las vistas reciben DTOs. `@Validated` valida los DTOs de alta y edición; `BindingResult` devuelve los errores al formulario. El listado de opciones del formulario de provincia también utiliza DTOs.

Los códigos duplicados generan mensajes traducidos. Antes de actualizar se comprueba que exista el registro para evitar que una edición de un ID inexistente se convierta en inserción.

### Paso 12. Adaptar las vistas

En `province-list.html`, se sustituye `province.region.name` por **province.regionName**. En `province-form.html`, el selector enlaza **regionId**, con opciones que usan `region.id`. El resto de los formularios mantiene sus nombres de atributo (`region` y `province`) para reutilizar las plantillas.

**Comprobación de esta etapa:** compilación; listados, formularios y detalles devuelven HTTP 200; un regionId inexistente produce un error de validación visible.

## 3. UD04-3: paginación y ordenación

### Paso 13. Consultas paginadas

Se añaden a las interfaces y los DAO:

```java
List<Region> listRegionsPage(int page, int size, String sortField, String sortDir);
long countRegions();
List<Province> listProvincesPage(int page, int size, String sortField, String sortDir);
long countProvinces();
```

`setFirstResult(page * size)` define el desplazamiento y `setMaxResults(size)` limita los resultados en la base de datos. Una consulta independiente obtiene el total. Las provincias cargan su comunidad con un fetch de la relación N:1.

### Paso 14. Ordenar con CriteriaBuilder

Los DAO construyen consultas Criteria. Los únicos campos permitidos son **id, code y name**; Province admite además **regionName**, que se resuelve al nombre de la región relacionada. El parámetro no se concatena a SQL. `asc` y `desc` controlan la dirección y el ID desempata para que la paginación sea estable.

### Paso 15. Recibir parámetros en los controladores

Los listados reciben `page=0`, `size=10`, `sortField=name`, `sortDir=asc` por defecto.

La clase `web/Pagination.java` calcula y publica en el modelo `currentPage`, `pageSize`, `offset`, `totalPages`, `totalElements`, `sortField`, `sortDir` y `reverseSortDir`.

- Las páginas empiezan internamente en cero.
- El tamaño se ajusta al intervalo 1–100.
- Una página negativa se lleva a cero y una demasiado alta a la última disponible.
- Los campos desconocidos se sustituyen por name y las direcciones desconocidas por asc.
- Un listado vacío muestra “Página 0 de 0” y oculta el navegador.
- Se evita el desbordamiento de enteros en el offset.

### Paso 16. Encabezados y paginador

En ambas tablas, los encabezados contienen enlaces que alternan el orden y muestran ▲/▼. Cambiar de columna reinicia a la primera página. Los enlaces anterior, siguiente y número de página conservan tamaño, campo y dirección.

El tamaño predeterminado es de 10 registros por página y puede ajustarse mediante el parámetro `size` de la URL. Los mensajes de paginación están en español e inglés.

### Paso 17. Comprobar el resultado completo

Desde la raíz del proyecto:

```bash
./mvnw clean verify
./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
```

Prueba los encabezados y la segunda página en `/regions`; abre la sexta página en `/provinces?page=5&size=10`, que contiene los dos registros restantes. Cambia el idioma, crea una comunidad y una provincia asociada, edita la provincia para moverla a otra comunidad y revisa los detalles.

Las diez pruebas de integración están en `src/test/java/org/iesalixar/daw2/rafaelvegalibrero/ticketlogger/TicketLoggerIntegrationTest.java`. El alcance y los resultados se detallan en `VERIFICACION.md`.

## Git y conservación del trabajo anterior

La aplicación nueva está dentro de DAW2, en la rama que ya estaba activa (`main`). No se han cambiado las carpetas de UD03 ni los cambios ajenos de DWEC. Tampoco se han creado ramas sobre el repositorio completo, commits o publicaciones remotas.

El PDF propone `tech/jdbc` y `refactor-hibernate` para un repositorio dedicado a la aplicación. Aquí DAW2 contiene varias asignaturas; el original JDBC se conserva en su carpeta y la evolución UD04 en otra. Puedes revisar los archivos y hacer un commit limitado a `DWESE/UD04-RVL_JPA-Hibernate` cuando quieras registrar la entrega.
