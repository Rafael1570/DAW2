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

## IntelliJ IDEA y Docker en este ordenador

Se han instalado IntelliJ IDEA y Docker Desktop. Java 21 y Maven están en
`.local-tools` dentro del proyecto; el SDK de IntelliJ se llama `temurin-21`.

WSL 2 y Virtual Machine Platform están configurados. El contenedor de MariaDB
se ha arrancado correctamente y se han restaurado los datos de la base anterior.

1. Abre Docker Desktop y espera a que el motor esté listo.
2. Abre esta carpeta como proyecto en IntelliJ IDEA e importa `pom.xml` como Maven
   si lo solicita. El SDK del proyecto debe ser `temurin-21`.
3. En la terminal de IntelliJ, situada en esta carpeta, ejecuta
   `docker compose up -d --wait db`.
4. Comprueba el contenedor `ud02-crud-users-db` con `docker compose ps`.
5. Selecciona la configuración **CRUD Users - Jetty** y pulsa Run.
6. Abre `http://localhost:8080/users`.

La base de datos de Docker usa el archivo `.env` local y el volumen
`users_database`. La instancia de MariaDB instalada directamente en Windows
no debe estar arrancada a la vez, porque también usa el puerto 3307.
La copia de sus datos está en `%LOCALAPPDATA%\DAW2-tools\users-before-docker.sql`.
El primer arranque en Docker creará la tabla y los cuatro usuarios de ejemplo.

Para detener la aplicación, pulsa Stop en IntelliJ. Para detener el contenedor
conservando sus datos, ejecuta `docker compose stop db`.

## Comandos en la terminal de IntelliJ

Abre una terminal PowerShell nueva en la carpeta que contiene `pom.xml` y
`docker-compose.yml`. Java, Maven y Docker están disponibles en el PATH de este
ordenador. Docker Desktop debe estar abierto.

```powershell
docker compose up -d
mvn clean
mvn jetty:run
```

Cuando Jetty termine de arrancar, abre `http://localhost:8080/users`.
Pulsa Ctrl+C para detener Jetty antes de ejecutar de nuevo `mvn clean`.

El proyecto incluye Maven Wrapper fijado a Maven 3.9.16. En otros ordenadores
puedes sustituir `mvn` por `.\mvnw.cmd` en Windows o `./mvnw` en Linux/macOS;
seguirás necesitando Java 21 y Docker. El primer uso descarga Maven.

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

