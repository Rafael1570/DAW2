<?php

namespace App\Tests;

use PHPUnit\Framework\Attributes\DataProvider;
use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

class RestauranteRutasTest extends WebTestCase
{
    #[DataProvider('paginas')]
    public function testPaginas(string $ruta, int $estado, ?string $texto = null): void
    {
        $cliente = static::createClient();
        $cliente->request('GET', $ruta);

        self::assertResponseStatusCodeSame($estado);
        if (null !== $texto) {
            self::assertStringContainsString($texto, $cliente->getResponse()->getContent());
        }
    }

    public static function paginas(): iterable
    {
        yield ['/restaurante', 200, 'Bienvenido al restaurante'];
        yield ['/restaurante/mesa/3', 200, 'Has reservado la mesa 3'];
        yield ['/restaurante/mesa/tres', 404];
        yield ['/restaurante/propina', 200, 'Propina recomendada: 5,00 €'];
        yield ['/restaurante/propina/25.50', 200, 'Propina recomendada: 2,55 €'];
        yield ['/restaurante/reserva/2026', 200, 'Reservas de 2026'];
        yield ['/restaurante/reserva/2026/12', 200, 'Reservas de diciembre… del mes 12 de 2026'];
        yield ['/restaurante/reserva/2026/12/24', 200, 'Reserva para el 24/12/2026'];
        yield ['/platos/2', 200, 'Gazpacho'];
        yield ['/platos/letras', 404];
        yield ['/platos/9', 404, 'No existe el plato 9'];
        yield ['/recetas/2', 200, 'Página 2 del recetario'];
        yield ['/recetas/tarta-de-queso', 200, 'Receta: tarta-de-queso'];
        yield ['/recetas/nueva', 200, 'Nueva receta'];
        yield ['/horario', 200, 'Horario del restaurante'];
        yield ['/admin/pedidos', 200, 'En cocina'];
        yield ['/admin/pedidos/confirmado', 201, 'Pedido registrado'];
        yield ['/admin/pedidos/nuevo', 405];
    }

    public function testCartaFiltroYBusqueda(): void
    {
        $cliente = static::createClient();
        $cliente->request('GET', '/platos');
        self::assertSelectorCount(5, 'li a');
        self::assertSelectorExists('a[href="/platos/2"]');

        $cliente->request('GET', '/platos/categoria/PRINCIPAL');
        self::assertSelectorCount(2, 'li a');
        self::assertSelectorExists('a[href="/platos/1"]');
        self::assertSelectorExists('a[href="/platos/4"]');

        $cliente->request('GET', '/platos/buscar?nombre=paella');
        self::assertResponseIsSuccessful();
        self::assertResponseHeaderSame('content-type', 'application/json');
        $platos = json_decode($cliente->getResponse()->getContent(), true, flags: JSON_THROW_ON_ERROR);
        self::assertSame(['Paella valenciana', 'Paella de marisco'], array_column($platos, 'nombre'));
    }

    public function testRedirecciones(): void
    {
        $cliente = static::createClient();

        $cliente->request('GET', '/recetas/inicio');
        self::assertResponseRedirects('/recetas/1', 302);

        $cliente->request('GET', '/menu-del-dia');
        self::assertResponseRedirects('/platos/2', 302);

        $cliente->request('GET', '/google-maps');
        self::assertResponseRedirects('https://maps.google.com', 302);

        $cliente->request('POST', '/admin/pedidos/nuevo', ['plato' => 'Croquetas']);
        self::assertResponseRedirects('/admin/pedidos', 302);
        $cliente->followRedirect();
        self::assertSelectorTextContains('p', 'Plato recibido: Croquetas');
    }
}
