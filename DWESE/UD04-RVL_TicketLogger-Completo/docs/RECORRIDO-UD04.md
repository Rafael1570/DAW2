# Recorrido de comprobación

La revisión por documento está en [PASOS-PDF.md](PASOS-PDF.md).

1. Abrir `/users`, cambiar el tamaño de página y pulsar Email para alternar el orden. Los números del paginador conservan tamaño y orden.
2. Crear un usuario con email, contraseña, datos de cuenta y varios roles. Consultar su detalle y comprobar que se muestran los nombres técnicos de rol.
3. Editarlo con contraseña vacía: se conserva la actual. Cambiar roles y comprobar los datos de cuenta y la caducidad a 90 días.
4. Crear y editar un rol desde `/roles`. Asignarlo a un usuario: no se puede borrar hasta desasignarlo.
5. Pulsar el avatar del menú y **Editar perfil**. El enlace abre `/profile/edit` para `admin@app.local`.
6. Completar nombre, apellidos, teléfono, biografía e idioma, y seleccionar una imagen. Guardar envía el multipart a `/profile/update` y vuelve al formulario con confirmación.
7. Editar sin nueva imagen: se conserva la foto. Subir otra: se sustituye la anterior. Consultar el detalle del usuario 1 para comprobar los campos del perfil.
8. Dejar nombre vacío: se conserva el formulario y aparece el error junto al campo. Un archivo de texto renombrado a PNG se rechaza con mensaje flash.
9. Comprobar las pantallas en español e inglés.
10. Ejecutar `./mvnw clean install`. La prueba valida las cuatro tablas, CRUD, DTOs, paginación, N:M, PK compartida, archivos y transacciones sobre H2.

Para MariaDB se necesita Docker en ejecución; la configuración se describe en el README. Los cambios guardados en la demostración H2 en memoria se pierden al detener el proceso.
