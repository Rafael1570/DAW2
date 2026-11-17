<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class CalculadoraController extends AbstractController
{
    #[Route('/calculadora', name: 'app_calculadora')]
    public function index(): Response
    {
        return $this->render('calculadora/index.html.twig', [
            'controller_name' => 'CalculadoraController',
        ]);
    }
    #[Route('/cuadrado/{numero}', name: 'app_cuadrado')]
    public function cuadrado(int $numero): Response
    {
        $cuadrado = $numero * $numero;
        return $this->render('calculadora/cuadrado.html.twig', ['cuadrado' => $cuadrado, 'numero' => $numero]);
    }
    #[Route('/potencia/{base}/{exponente}', name: 'app_potencia')]
    public function potencia(int $base, int $exponente = 2): Response
    {
        $potencia = $base ** $exponente;

        return $this->render('calculadora/potencia.html.twig', ['potencia' => $potencia, 'base' => $base, 'exponente' => $exponente,]);
    }

}
