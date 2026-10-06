# UD02 — CRUD de usuarios

Aplicación Maven WAR con Servlets, JSP, JSTL y MariaDB. El script `src/main/resources/data.sql` crea únicamente la tabla `users` y carga datos iniciales; `users.sql` conserva por separado el SQL entregado para usuarios.

## Requisitos

- JDK 21
- Maven
- Docker Engine o Docker Desktop con Compose

## Arranque local

1. Copia `.env.example` como `.env` y configura claves locales. El `.env` real está excluido de Git.
2. Arranca la base de datos: `docker compose up -d db`.
3. Espera a que esté sana: `docker compose ps`.
4. Arranca Jetty desde esta carpeta: `mvn jetty:run`.
5. Abre `http://localhost:8080/users`.

Compose publica MariaDB en el puerto local `3307`, para no pisar otra instancia que use `3306`. La base de datos se llama `dwese_users` y persiste en el volumen `users_database`.

Para reiniciar solo el contenedor: `docker compose restart db`. Para borrar también los datos persistidos: `docker compose down -v`.

## Datos de ejemplo

El script crea `admin`, `jdoe`, `maria` y `blockeduser`; las contraseñas de ejemplo están en texto plano porque así lo pide la actividad. No reutilices esas contraseñas fuera de esta base local.

## Guion sugerido para el vídeo de entrega

- Empieza a grabar con el IDE visible y ejecuta el arranque de Jetty.
- Muestra el listado inicial de usuarios.
- Crea un usuario y comprueba que aparece en la lista.
- Intenta repetir el username, dejar obligatorios vacíos, introducir una fecha inválida y un número negativo.
- Edita el usuario, cambia estados booleanos y guarda; enseña la caducidad calculada tres meses después.
- Elimina el usuario y confirma que desaparece.
- Cambia el idioma a inglés y repite al menos una vista CRUD.

El vídeo, la invitación del profesor al repositorio y la publicación remota son entregables externos; no se generan al ejecutar la aplicación.
