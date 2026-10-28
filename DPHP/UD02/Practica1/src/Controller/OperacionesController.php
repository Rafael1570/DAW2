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
    #[Route('/operaciones/cuadrado/{numero}', name: 'app_cuadrado')]
    public function cuadrado(int $numero): Response
    {
        $cuadrado = $numero * $numero;
        return $this->render('operaciones/cuadrado.html.twig', ['cuadrado' => $cuadrado]);
    }
    //Sumamos los numero
    #[Route('/operaciones/suma/{numero1}/{numero2}', name: 'app_suma')]
    public function suma(int $numero1, int $numero2): Response
    {
        $suma = $numero1 + $numero2;
        return $this->render('operaciones/suma.html.twig', ['numero1' => $numero1, 'numero2' => $numero2, 'suma' => $suma]);
    }
    //Calculamos la potencia
    #[Route('/operaciones/potencia/{base}/{exponente}', name: 'app_potencia')]
    public function potencia(int $base, int $exponente): Response
    {
        if ($exponente === 0){
            $exponente = 2;
            $potencia = $base ** $exponente;
        }else {
            $potencia = $base ** $exponente;
        }
        return $this->render('operaciones/potencia.html.twig', ['base' => $base, 'exponente' => $exponente, 'potencia' => $potencia]);
    }

}
