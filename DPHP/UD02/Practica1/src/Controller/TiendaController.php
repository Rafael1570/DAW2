<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/tienda', name: 'tienda_')]
class TiendaController extends AbstractController
{
    #[Route('', name: 'inicio')]
    public function index(): Response
    {
        return new Response('Bienvenido a la tienda');
    }

    #[Route('/catalogo', name: 'catalogo')]
    public function catalogo(): Response
    {
        return new Response('Catálogo de productos');
    }

    #[Route('/carrito', name: 'carrito')]
    public function carrito(): Response
    {
        return new Response('Carrito de compras');
    }
}
