<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class ProductoController extends AbstractController
{
    #[Route('/producto', name: 'app_producto')]
    public function index(): Response
    {
        return $this->render('producto/index.html.twig', [
            'controller_name' => 'ProductoController',
        ]);
    }
    #[Route('/producto/nuevo', name: 'app_producto_nuevo')]
    public function nuevo(): Response
    {
        return $this->render('producto/nuevo.html.twig', []);
    }
    #[Route('/producto/{id}', name: 'app_producto_show', requirements: ['id' => '\d+'])]
    public function producto_show(int $id): Response
    {
        return $this->render('producto/show.html.twig', ['id' => $id]);
    }
}
