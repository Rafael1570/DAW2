<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class SaludoController extends AbstractController
{
    #[Route('/saludo', name: 'app_saludo')]
    public function index(): Response
    {
        return $this->render('saludo/index.html.twig', [
            'controller_name' => 'SaludoController',
        ]);
    }
    #[Route('/saludo/{name}', name: 'app_saludo_nombre')]
    public function saludo(string $name): Response
    {
        return new Response('Hola, '. htmlspecialchars($name, ENT_QUOTES, 'UTF-8'));
    }
}
