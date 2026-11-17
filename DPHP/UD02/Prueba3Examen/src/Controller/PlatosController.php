<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/platos', name: 'platos_')]
class PlatosController extends AbstractController
{
    #[Route('', name: 'lista')]
    public function lista(): Response
    {
        return $this->render('platos/lista.html.twig', [
            'titulo' => 'Carta',
            'platos' => $this->platos(),
        ]);
    }

    #[Route('/{id}', name: 'detalle', requirements: ['id' => '\d+'])]
    public function detalle(string $id): Response
    {
        $plato = $this->platos()[$id] ?? null;

        if (null === $plato) {
            return new Response("No existe el plato $id", Response::HTTP_NOT_FOUND);
        }

        return $this->render('platos/detalle.html.twig', ['plato' => $plato]);
    }

    #[Route('/categoria/{categoria}', name: 'categoria')]
    public function categoria(string $categoria): Response
    {
        $platos = array_filter(
            $this->platos(),
            fn (array $plato): bool => mb_strtolower($plato['categoria']) === mb_strtolower($categoria),
        );

        return $this->render('platos/lista.html.twig', [
            'titulo' => 'Categoría: '.$categoria,
            'platos' => $platos,
        ]);
    }

    #[Route('/buscar', name: 'buscar')]
    public function buscar(Request $request): JsonResponse
    {
        $nombre = $request->query->getString('nombre');
        $platos = array_filter(
            $this->platos(),
            fn (array $plato): bool => false !== mb_stripos($plato['nombre'], $nombre),
        );

        return $this->json(array_values($platos));
    }

    private function platos(): array
    {
        return [
            1 => ['nombre' => 'Paella valenciana', 'categoria' => 'Principal', 'precio' => 14.50, 'vegetariano' => 'No'],
            2 => ['nombre' => 'Gazpacho', 'categoria' => 'Entrante', 'precio' => 6.00, 'vegetariano' => 'Sí'],
            3 => ['nombre' => 'Tarta de queso', 'categoria' => 'Postre', 'precio' => 5.50, 'vegetariano' => 'Sí'],
            4 => ['nombre' => 'Paella de marisco', 'categoria' => 'Principal', 'precio' => 16.00, 'vegetariano' => 'No'],
            5 => ['nombre' => 'Croquetas caseras', 'categoria' => 'Entrante', 'precio' => 7.50, 'vegetariano' => 'No'],
        ];
    }
}
