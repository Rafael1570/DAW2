# Verificación de Ticket Logger UD04

Fecha: 6 de noviembre de 2026.

## Recorrido comprobado

El usuario consulta, crea y edita comunidades y provincias desde Thymeleaf; Spring MVC valida los DTOs, los mappers construyen entidades, los DAO ejecutan operaciones JPA y la respuesta vuelve a una vista con DTOs.

## Compilación y pruebas

Comando ejecutado desde la carpeta del proyecto:

```bash
./mvnw clean verify
```

Tras ajustar el mensaje de confirmación de borrado, se ejecutó también `./mvnw verify` sobre la versión final.

Resultado del informe de Surefire:

```text
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
```

Las pruebas usan Spring Boot completo, MockMvc, Hibernate y H2 en modo MariaDB. Se renderizan las plantillas reales de Thymeleaf. Las diez comprobaciones cubren:

1. Los datos iniciales: 18 comunidades y 52 provincias; primera y última página.
2. Ordenación ascendente y descendente; parámetros conservados en los enlaces; ordenación por nombre de comunidad en provincias.
3. Página negativa, tamaño cero, parámetros extremos, campo desconocido y página fuera de rango.
4. Inicio, alta, edición y detalles; los modelos de lectura usan DTOs y no hay claves de traducción sin resolver.
5. Traducción de paginación y acciones al español y al inglés.
6. Alta, edición, cambio de comunidad y borrado con comprobaciones posteriores sobre la base de datos.
7. Campos obligatorios, ID de edición y comunidad vacía, negativa o inexistente.
8. Códigos duplicados y registros inexistentes, sin inserción accidental al editar.
9. Listados vacíos sin páginas ficticias.
10. Edición de una comunidad conservando sus ocho provincias y borrado posterior en cascada.

## Servidor real y navegador

Se arrancó el JAR final con:

```bash
java -jar target/dwese2627-ticket-logger-webapp-rafaelvegalibrero-4.0.0.jar --spring.profiles.active=demo
```

El servidor arrancó en el puerto **8082**. Se ejecutó además un ciclo CRUD mediante HTTP real, con transacciones independientes por petición: crear comunidad, editarla, crear provincia asociada, mover la provincia a Andalucía, comprobar ambos detalles y eliminar los dos registros temporales. Los datos de ejemplo permanecen en el servidor.

| Límite del recorrido | Resultado | Evidencia |
| --- | --- | --- |
| Vista inicial | Correcto | Navegador muestra las diez primeras comunidades y “Página 1 de 2 (18 registros en total)” |
| Encabezado → controlador | Correcto | Pulsar Nombre cambia la URL a `sortField=name&sortDir=desc` y País Vasco aparece primero |
| Cambio de página | Correcto | Pulsar siguiente conserva `sortField=name&sortDir=desc` y muestra “Página 2 de 2” |
| Controlador → persistencia | Correcto | Pruebas y CRUD HTTP comprueban la escritura y lectura posterior |
| Persistencia → DTO → plantilla | Correcto | Detalles de comunidad y provincia responden correctamente con `open-in-view=false` |
| Última página de provincias | Correcto | `/provinces?page=5&size=10` responde con los dos registros restantes |
| Recursos de interfaz | Revisado | Se inspeccionó una captura del listado final en el navegador integrado |

## Incidencias corregidas durante la construcción

- Se corrigió la expresión de colección del selector de tamaños de Thymeleaf, que inicialmente impedía renderizar los listados.
- La edición de comunidad actualiza solo los campos del objeto gestionado; evita reemplazar su colección de provincias con la colección vacía del formulario convertido a entidad.
- Se seleccionó el puerto 8082 al detectar otra aplicación del alumno en 8081.
- La confirmación de borrado explica que también se eliminarán las provincias, de acuerdo con el `CascadeType.ALL` del taller.

## Límites de esta comprobación

Docker no estaba activo: la conexión a su socket devolvía que no existía. Por tanto, **no se ha probado el arranque con MariaDB**. Su driver, datasource, Compose, esquema y datos quedan configurados; las pruebas de persistencia descritas se han ejecutado con H2. No se puede deducir de ellas una validación completa de MariaDB.

No se han realizado commits, pushes, cambios de rama ni despliegues. El proyecto anterior y los cambios ajenos del repositorio permanecen intactos.
