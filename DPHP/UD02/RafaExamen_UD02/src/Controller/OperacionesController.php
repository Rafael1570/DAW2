<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class OperacionesController extends AbstractController
{
    #[Route('/operaciones', name: 'app_operaciones')]
    public function index(): Response
    {
        return $this->render('operaciones/index.html.twig', [
            'controller_name' => 'OperacionesController',
        ]);
    }
    #[Route('/doble/{numero}', name: 'app_doble')]
    public function doble(int $numero): Response
    {
        $doble = $numero * 2;
        return $this->render('operaciones/doble.html.twig', ['numero' => $numero, 'doble' => $doble]);
    }
    #[Route('/resta/{a}/{b}', name: 'app_resta')]
    public function resta(int $a, int $b): Response
    {
        $resta = $a - $b;
        return $this->render('operaciones/resta.html.twig', ['a' => $a, 'b' => $b, 'resta' => $resta]);
    }
    #[Route('/porcentaje/{numero}/{porcentaje?10}', name: 'app_porcentaje')]
    public function porcentaje(int $numero, int $porcentaje): Response
    {
        $resultado = $numero * $porcentaje/100;
        return $this->render('operaciones/porcentaje.html.twig', ['numero' => $numero, 'porcentaje' => $porcentaje, 'resultado' => $resultado]);
    }
}
