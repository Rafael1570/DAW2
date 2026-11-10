<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class ArticuloController extends AbstractController
{
    #[Route('/articulo', name: 'app_articulo')]
    public function index(): Response
    {
        return $this->render('articulo/index.html.twig', [
            'controller_name' => 'ArticuloController',
        ]);
    }
    #[Route('/articulo/{id}', name: 'app_articulo_show' , requirements: ['id' => '\d+'])]
    public function articulo( int $id): Response
    {
        return $this->render('articulo/articulo.html.twig', ['id' => $id]);
    }
}
