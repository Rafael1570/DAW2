# Práctica 2 · Formulario y sesiones

Cada apartado tiene su propio proyecto Symfony, sus dependencias y sus datos. El ejercicio 3 reúne el flujo completo del enunciado.

| Proyecto | Ruta inicial | Qué puedes probar |
| --- | --- | --- |
| [Ejercicio 1 · Login](Ejercicio1-Login/) | `/` o `/login` | Autenticación con los usuarios de prueba y enlace al registro del propio proyecto. |
| [Ejercicio 2 · Registro](Ejercicio2-Registro/) | `/` o `/registro` | Validación de contraseñas, aceptación de condiciones, creación de cuenta y acceso con el nuevo usuario. |
| [Ejercicio 3 · Home y cierre de sesión](Ejercicio3-Home-Logout/) | `/` o `/login` | Login y registro, bienvenida personalizada en `/home` y cierre de sesión. |

## Arranque

Desde la carpeta del ejercicio elegido, con PHP 8.2 o superior y Composer:

```bash
composer install
php -S 127.0.0.1:8000 -t public
```

Si usas el archivo Composer de tu carpeta personal, instala con `php ~/composer.phar install`. Abre [http://127.0.0.1:8000](http://127.0.0.1:8000). Para ejecutar varios ejercicios a la vez, asigna un puerto distinto a cada servidor.

| Usuario | Contraseña |
| --- | --- |
| `admin` | `admin` |
| `user25` | `2025` |
| `user56` | `2026` |

Las cuentas nuevas se guardan en `var/usuarios/` del ejercicio que las creó. Las contraseñas de esas cuentas se almacenan como hashes.
