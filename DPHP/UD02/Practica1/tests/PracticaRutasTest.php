<?php
namespace App\Tests;

use PHPUnit\Framework\Attributes\DataProvider;
use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

final class PracticaRutasTest extends WebTestCase
{
    public static function paginas(): iterable
    {
        yield ['/saludo', 200, 'Bienvenido'];
        yield ['/saludo/Rafa', 200, 'Hola, Rafa'];
        yield ['/cuadrado/6', 200, '36'];
        yield ['/suma/3/4', 200, '7'];
        yield ['/potencia/5/3', 200, '125'];
        yield ['/potencia/5', 200, '25'];
        yield ['/potencia/5/0', 200, '1'];
        yield ['/articulo/7', 200, '7'];
        yield ['/articulo/abc', 404, null];
        yield ['/blog/3', 200, '3'];
        yield ['/blog/mi-primer-post', 200, 'mi-primer-post'];
        yield ['/producto/nuevo', 200, '<form'];
        yield ['/producto/3', 200, '3'];
        yield ['/producto/abc', 404, null];
        yield ['/tienda', 200, 'Bienvenido'];
        yield ['/tareas', 200, 'Repasar rutas'];
        yield ['/tareas/2', 200, 'Estudiar Twig'];
        yield ['/tareas/999', 404, null];
        yield ['/tareas/nueva', 405, null];
        yield ['/contactos', 200, 'Ana'];
        yield ['/contactos/2', 200, 'Luis'];
        yield ['/contactos/nuevo', 405, null];
        yield ['/contactos/999', 404, null];
        yield ['/recurso/nuevo', 201, null];
        yield ['/saludo-yaml', 200, 'Bienvenido'];
        yield ['/admin', 200, 'administración'];
        yield ['/archivo/2026', 200, '2026'];
        yield ['/archivo/2026/3', 200, 'marzo de 2026'];
        yield ['/archivo/2026/3/15', 200, '15 de marzo de 2026'];
        yield ['/archivo/2026/2/30', 404, null];
        yield ['/libros', 200, 'Don Quijote'];
        yield ['/libros/categoria/novela', 200, 'La sombra del viento'];
        yield ['/libros/1', 200, 'Cervantes'];
        yield ['/libros/999', 404, null];
    }

    #[DataProvider('paginas')]
    public function testPaginas(string $url, int $status, ?string $texto): void
    {
        $client = static::createClient();
        $client->request('GET', $url);
        self::assertResponseStatusCodeSame($status);
        if ($texto !== null) {
            self::assertStringContainsString($texto, $client->getResponse()->getContent());
        }
    }

    public function testAltaTareaYJson(): void
    {
        $client = static::createClient();
        $client->request('POST', '/tareas/nueva', ['titulo' => 'Nueva tarea de prueba', 'descripcion' => 'Descripción de prueba']);
        self::assertResponseRedirects('/tareas', 303);
        $client->followRedirect();
        self::assertSelectorTextContains('body', 'Nueva tarea de prueba');
        $client->request('GET', '/tareas.json');
        self::assertResponseHeaderSame('content-type', 'application/json');
        $tareas = json_decode($client->getResponse()->getContent(), true, 512, JSON_THROW_ON_ERROR);
        self::assertCount(5, $tareas);
        self::assertSame('Nueva tarea de prueba', $tareas[4]['titulo']);
        $client->request('GET', '/tareas/5');
        self::assertSelectorTextContains('body', 'Descripción de prueba');
    }

    public function testAltaContactoYBusqueda(): void
    {
        $client = static::createClient();
        $client->request('POST', '/contactos/nuevo', ['nombre' => 'Ángela Prueba', 'telefono' => '611222333', 'correo' => 'angela@example.com']);
        self::assertResponseRedirects('/contactos', 303);
        $client->followRedirect();
        self::assertSelectorTextContains('body', 'Ángela Prueba');
        $client->request('GET', '/contactos/buscar?nombre='.urlencode('ángela'));
        self::assertResponseHeaderSame('content-type', 'application/json');
        $contactos = json_decode($client->getResponse()->getContent(), true, 512, JSON_THROW_ON_ERROR);
        self::assertCount(1, $contactos);
        self::assertSame('Ángela Prueba', $contactos[0]['nombre']);
        $client->request('GET', '/contactos/4');
        self::assertSelectorTextContains('body', 'angela@example.com');
    }

    public function testRedirecciones(): void
    {
        $client = static::createClient();
        $client->request('GET', '/despedida');
        self::assertResponseRedirects('/saludo');
        $client->request('GET', '/symfony-docs');
        self::assertResponseRedirects('https://symfony.com/doc/current/index.html');
    }

    public function testDatosInvalidos(): void
    {
        $client = static::createClient();
        $client->request('POST', '/tareas/nueva', ['titulo' => '  ']);
        self::assertResponseStatusCodeSame(400);
        $client->request('POST', '/contactos/nuevo', ['nombre' => 'Ana', 'telefono' => '123', 'correo' => 'incorrecto']);
        self::assertResponseStatusCodeSame(400);
    }
}
