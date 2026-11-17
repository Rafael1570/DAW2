<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
final class RedireccionController extends AbstractController
{
    #[Route('/redireccion', name: 'app_redireccion')]
    public function index(): Response
    {
        return $this->render('redireccion/index.html.twig', [
            'controller_name' => 'RedireccionController',
        ]);
    }
    #[Route('/inicio', name: 'app_redireccion')]
    public function inicio(): Response
    {
        return $this->redirectToRoute('app_videojuegos_index');
    }
    #[Route('/videojuegos.json', name: 'app_videojuegos_json')]
    public function videojuegos(): Response
    {
        $videojuegos = [
            1 => 'Minecraft',
            2 => 'Valorant',
            3 => 'FIFA',
            4 => 'Fortnite'
        ];

        return $this->json($videojuegos);
    }
}
