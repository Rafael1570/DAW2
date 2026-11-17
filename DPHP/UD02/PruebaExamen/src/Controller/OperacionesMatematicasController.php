<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class OperacionesMatematicasController extends AbstractController
{
    #[Route('/operaciones/matematicas', name: 'app_operaciones_matematicas')]
    public function index(): Response
    {
        return $this->render('operaciones_matematicas/index.html.twig', [
            'controller_name' => 'OperacionesMatematicasController',
        ]);
    }
    #[Route('/doble/{numero}', name: 'app_doble')]
    public function doble(int $numero): Response
    {
        $doble = $numero * 2;
        return $this->render('operaciones_matematicas/doble.html.twig', ['doble' => $doble, 'numero' => $numero]);
    }
    #[Route('/suma/{a}/{b}', name: 'app_suma')]
    public function suma(int $a, int $b): Response
    {
        $suma = $a + $b;
        return $this->render('operaciones_matematicas/suma.html.twig', ['a' => $a, 'b' => $b, 'suma' => $suma]);
    }
}
