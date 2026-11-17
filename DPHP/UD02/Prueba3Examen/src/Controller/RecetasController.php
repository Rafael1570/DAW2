<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class RecetasController extends AbstractController
{
    #[Route('/recetas/{pagina}', name: 'recetas_pagina', requirements: ['pagina' => '\d+'])]
    public function pagina(string $pagina): Response
    {
        return new Response("Página $pagina del recetario", headers: ['Content-Type' => 'text/plain; charset=UTF-8']);
    }

    #[Route('/recetas/{slug}', name: 'recetas_detalle', requirements: ['slug' => '[a-zA-Z][a-zA-Z0-9-]*'])]
    public function detalle(string $slug): Response
    {
        return new Response("Receta: $slug");
    }

    #[Route('/recetas/nueva', name: 'recetas_nueva', priority: 1)]
    public function nueva(): Response
    {
        return new Response('Nueva receta');
    }

    #[Route('/recetas/inicio', name: 'recetas_inicio', priority: 1)]
    public function inicio(): RedirectResponse
    {
        return $this->redirectToRoute('recetas_pagina', ['pagina' => 1]);
    }

    #[Route('/menu-del-dia', name: 'menu_del_dia')]
    public function menuDelDia(): RedirectResponse
    {
        return $this->redirectToRoute('platos_detalle', ['id' => 2]);
    }

    #[Route('/google-maps', name: 'google_maps')]
    public function googleMaps(): RedirectResponse
    {
        return $this->redirect('https://maps.google.com');
    }
}
