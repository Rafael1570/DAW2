<?php

namespace App\Service;

use Symfony\Component\DependencyInjection\Attribute\Autowire;

class Usuarios
{
    private const DEMOSTRACION = [
        'admin' => 'admin',
        'user25' => '2025',
        'user56' => '2026',
    ];

    public function __construct(
        #[Autowire('%kernel.project_dir%/var/usuarios')]
        private readonly string $directorio,
    ) {
    }

    public function autenticar(string $usuario, string $password): bool
    {
        if (isset(self::DEMOSTRACION[$usuario])) {
            return hash_equals(self::DEMOSTRACION[$usuario], $password);
        }

        $archivo = $this->archivo($usuario);
        if (!is_file($archivo)) {
            return false;
        }

        $contenido = file_get_contents($archivo);
        if ($contenido === false) {
            throw new \RuntimeException('No se pudo leer el usuario.');
        }

        $datos = json_decode($contenido, true, flags: JSON_THROW_ON_ERROR);

        return password_verify($password, $datos['password']);
    }

    public function registrar(string $usuario, string $password, string $nombre, string $apellidos, string $email): bool
    {
        $archivo = $this->archivo($usuario);
        if (isset(self::DEMOSTRACION[$usuario]) || is_file($archivo)) {
            return false;
        }

        if (!is_dir($this->directorio) && !@mkdir($this->directorio, 0700, true) && !is_dir($this->directorio)) {
            throw new \RuntimeException('No se pudo crear el directorio de usuarios.');
        }

        $datos = json_encode([
            'usuario' => $usuario,
            'nombre' => $nombre,
            'apellidos' => $apellidos,
            'email' => $email,
            'password' => password_hash($password, PASSWORD_DEFAULT),
        ], JSON_THROW_ON_ERROR);

        $temporal = tempnam($this->directorio, 'registro-');
        if ($temporal === false) {
            throw new \RuntimeException('No se pudo preparar el registro.');
        }

        try {
            if (file_put_contents($temporal, $datos) !== strlen($datos)) {
                throw new \RuntimeException('No se pudo guardar el usuario.');
            }

            // Publica el archivo completo sin sobrescribir un registro simultáneo.
            if (@link($temporal, $archivo)) {
                return true;
            }
            if (is_file($archivo)) {
                return false;
            }

            throw new \RuntimeException('No se pudo publicar el registro.');
        } finally {
            unlink($temporal);
        }
    }

    private function archivo(string $usuario): string
    {
        return $this->directorio.'/'.hash('sha256', $usuario).'.json';
    }
}
