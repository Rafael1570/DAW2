# Evidencias de funcionamiento · UD04

**Alumno:** Rafael Vega Librero  
**Asignatura:** Desarrollo Web en Entorno Servidor · DAW2  
**Proyecto:** Ticket Logger · UD04  
**Fecha:** 6 de noviembre de 2026

Capturas completas obtenidas del navegador mientras se ejecuta la aplicación en `http://localhost:8082`. En todas aparece el nombre del alumno en el pie de la aplicación. No se ha añadido el nombre mediante edición de las capturas.

## Contenido

1. [01-regiones-paginacion.jpg](01-regiones-paginacion.jpg): Listado de comunidades: primera página, orden ascendente y total de 18 registros.
2. [02-regiones-segunda-pagina.jpg](02-regiones-segunda-pagina.jpg): Navegación a la segunda página conservando el orden.
3. [03-regiones-orden-descendente.jpg](03-regiones-orden-descendente.jpg): Orden descendente por nombre, con indicador de dirección.
4. [04-provincias-paginacion.jpg](04-provincias-paginacion.jpg): Listado paginado de provincias y su comunidad; total de 52 registros.
5. [05-detalle-region-provincias.jpg](05-detalle-region-provincias.jpg): Detalle de Andalucía con sus ocho provincias y enlaces de navegación.
6. [06-detalle-provincia-region.jpg](06-detalle-provincia-region.jpg): Detalle de Almería con enlace a Andalucía.
7. [07-formulario-edicion-provincia.jpg](07-formulario-edicion-provincia.jpg): Formulario de edición precargado con código, nombre y comunidad.
8. [08-internacionalizacion-ingles.jpg](08-internacionalizacion-ingles.jpg): Interfaz en inglés: encabezados, acciones y mensajes de paginación traducidos.

## Entorno y alcance

Spring Boot, Hibernate y Thymeleaf con el perfil de demostración `demo` (H2 en memoria, modo MariaDB). Las tablas de las capturas usan `size=5` en la URL para mostrar juntas la paginación y la autoría. El tamaño predeterminado de la aplicación sigue siendo 10.

Las capturas muestran los listados, la navegación, la ordenación, las relaciones entre entidades, el formulario de edición y el cambio de idioma. La imagen del formulario demuestra su carga con datos; no representa por sí sola una actualización enviada.
