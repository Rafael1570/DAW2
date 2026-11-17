<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/restaurante', name: 'restaurante_')]
class RestauranteController extends AbstractController
{
    #[Route('', name: 'inicio')]
    public function inicio(): Response
    {
        return new Response('Bienvenido al restaurante');
    }

    #[Route('/mesa/{numero}', name: 'mesa', requirements: ['numero' => '\d+'])]
    public function mesa(string $numero): Response
    {
        return new Response("Has reservado la mesa $numero");
    }

    #[Route('/propina/{total}', name: 'propina', requirements: ['total' => '\d+(?:[.,]\d+)?'])]
    public function propina(string $total = '50'): Response
    {
        $propina = number_format((float) str_replace(',', '.', $total) * 0.10, 2, ',', '');

        return new Response("Propina recomendada: $propina €", headers: ['Content-Type' => 'text/plain; charset=UTF-8']);
    }

    #[Route('/reserva/{anio}/{mes}/{dia}', name: 'reserva', requirements: [
        'anio' => '\d{4}',
        'mes' => '0?[1-9]|1[0-2]',
        'dia' => '0?[1-9]|[12]\d|3[01]',
    ])]
    public function reserva(int $anio, ?int $mes = null, ?int $dia = null): Response
    {
        return $this->render('restaurante/reserva.html.twig', [
            'anio' => $anio,
            'mes' => $mes,
            'dia' => $dia,
        ]);
    }
}
