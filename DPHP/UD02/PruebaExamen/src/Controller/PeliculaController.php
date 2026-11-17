<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Contracts\Service\Attribute\Required;

final class PeliculaController extends AbstractController
{
    #[Route('/pelicula', name: 'app_pelicula')]
    public function index(): Response
    {
        return $this->render('pelicula/index.html.twig', [
            'controller_name' => 'PeliculaController',
        ]);
    }
    #[Route('/pelicula/{id}', name: 'app_pelicula_show', requirements: ['id' => '[1-9]]'])]
    public function peliId(int $id): Response
    {
        return $this->render('pelicula/show.html.twig', ['id' => $id]);

    }
    #[Route('pelicula/nueva', name: 'app_pelicula_nueva')]
    public function nueva(): Response
    {
        return $this->render('pelicula/nueva.html.twig');
    }
}
