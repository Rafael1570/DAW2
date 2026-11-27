# Práctica 1 · Formularios y sesiones

Cada ejercicio es un proyecto Symfony independiente. Necesitas PHP 8.2 o superior y Composer.

## Cómo arrancar un ejercicio

Abre una terminal dentro de la carpeta del ejercicio y ejecuta:

```bash
composer install
php -S 127.0.0.1:8000 -t public
```

Si usas el archivo `composer.phar` de tu carpeta personal, puedes instalar las dependencias con `php ~/composer.phar install`.

En el navegador, abre `http://127.0.0.1:8000` seguido de la ruta indicada en la tabla. Pulsa Ctrl+C para parar el servidor antes de arrancar otro ejercicio en el mismo puerto.

| Proyecto | Ruta de entrada | Qué se puede probar |
| --- | --- | --- |
| [Ejercicio 1 · Registro](Ejercicio1-Registro/) | `/registro` | Envía el formulario y revisa los datos recibidos. La contraseña aparece oculta. |
| [Ejercicio 2 · Contacto](Ejercicio2-Contacto/) | `/contacto` | Envía el formulario vacío para ver los errores. Rellénalo para ver el mensaje de éxito tras la redirección. |
| [Ejercicio 3 · Login](Ejercicio3-Login/) | `/login` | Inicia sesión para ver la bienvenida. Abre `/bienvenida` en una ventana privada para comprobar la redirección. |
| [Ejercicio 4 · Contador](Ejercicio4-Contador/) | `/contador` | Recarga varias veces y pulsa «Reiniciar contador» para volver a 1. |
| [Ejercicio 5 · Carrito](Ejercicio5-Carrito/) | `/carrito` | Añade Pan, Leche y Manzana. Pulsa «Vaciar carrito» para eliminar los tres productos. |
| [Ejercicio 6 · Logout](Ejercicio6-Logout/) | `/login` | Inicia sesión, pulsa «Cerrar sesión» y comprueba que `/bienvenida` vuelve al formulario. |

Los ejercicios 3 y 6 usan el usuario de prueba `alumna` y la contraseña `Practica123`. Los ejercicios que piden capturas las tienen en su carpeta `capturas`.

## Reto del ejercicio 6

`migrate(true)` cambia el identificador de sesión y borra el anterior justo después de validar las credenciales y antes de guardar el usuario, para evitar la fijación de sesión, es decir, que alguien pueda reutilizar un identificador conocido para entrar con la sesión autenticada.

Al cerrar sesión, `invalidate()` borra los datos de la sesión y cambia su identificador. El mensaje flash se guarda después en la nueva sesión para que aparezca al volver al formulario de login.
