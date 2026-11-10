# Práctica 1: rutas y controladores

Ejecuta desde la terminal de PhpStorm, en esta carpeta:

```powershell
.\ejecutar.ps1
```

Abre http://127.0.0.1:8000/saludo. Para comprobar las pruebas: `.\ejecutar.ps1 test`.
El script usa las extensiones necesarias para esta práctica sin cargar mysqli, que Windows bloquea en este equipo.

| Ejercicio | URL de ejemplo |
| --- | --- |
| 1 y 2 | `/saludo`, `/saludo/Rafa` |
| 3 | `/cuadrado/6`, `/suma/3/4`, `/potencia/5/3`, `/potencia/5` |
| 4 | `/articulo/7` (texto produce 404) |
| 5 | `/blog/3`, `/blog/mi-primer-post` |
| 6 | `/producto/nuevo`, `/producto/3` |
| 7 | `/despedida` redirige por nombre a `/saludo` |
| 8 | `/tienda`, `/tienda/catalogo`, `/tienda/carrito` |
| 9 y 10 | `/tareas`, `/tareas/2`; formulario en el listado envía POST a `/tareas/nueva` |
| 11 | `/contactos`, `/contactos/2`, `/contactos/buscar?nombre=Ana`; formulario POST a `/contactos/nuevo` |
| 12 | `/recurso/nuevo` devuelve JSON y estado 201 (simulación) |
| 13 | `/saludo-yaml`, definida en `config/routes.yaml` |
| 14 | `/admin`, controlador en `src/Controller/Admin` |
| 15 | `/symfony-docs` redirige a documentación externa |
| 16 | `/archivo/2026`, `/archivo/2026/3`, `/archivo/2026/3/15` |
| 17 | `/tareas` devuelve HTML; `/tareas.json` devuelve los mismos datos en JSON |
| 18 | `/libros`, `/libros/categoria/novela`, `/libros/1` |

Las tareas y los contactos nuevos se conservan en la sesión del navegador; no se utiliza base de datos. Los GET de las rutas de alta devuelven 405. Los identificadores inexistentes devuelven 404; los formularios sin datos válidos devuelven 400.
