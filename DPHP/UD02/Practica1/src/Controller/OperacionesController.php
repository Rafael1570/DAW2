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
    // Calculamos el cuadrado del numero
    #[Route('/cuadrado/{numero}', name: 'cuadrado', requirements: ['numero' => '-?\d+'])]
    #[Route('/operaciones/cuadrado/{numero}', name: 'app_cuadrado')]
    public function cuadrado(int $numero): Response
    {
        $cuadrado = $numero * $numero;
        return $this->render('operaciones/cuadrado.html.twig', ['cuadrado' => $cuadrado]);
    }
    //Sumamos los numero
    #[Route('/suma/{numero1}/{numero2}', name: 'suma', requirements: ['numero1' => '-?\d+', 'numero2' => '-?\d+'])]
    #[Route('/operaciones/suma/{numero1}/{numero2}', name: 'app_suma')]
    public function suma(int $numero1, int $numero2): Response
    {
        $suma = $numero1 + $numero2;
        return $this->render('operaciones/suma.html.twig', ['numero1' => $numero1, 'numero2' => $numero2, 'suma' => $suma]);
    }
    //Calculamos la potencia
    #[Route('/potencia/{base}/{exponente}', name: 'potencia', requirements: ['base' => '-?\d+', 'exponente' => '-?\d+'])]
    #[Route('/operaciones/potencia/{base}/{exponente}', name: 'app_potencia')]
    public function potencia(int $base, int $exponente = 2): Response
    {
        $potencia = $base ** $exponente;
        return $this->render('operaciones/potencia.html.twig', ['base' => $base, 'exponente' => $exponente, 'potencia' => $potencia]);
    }

}
