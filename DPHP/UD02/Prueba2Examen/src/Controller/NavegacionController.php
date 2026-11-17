<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class NavegacionController extends AbstractController
{
    #[Route('/navegacion', name: 'app_navegacion')]
    public function index(): Response
    {
        return $this->render('navegacion/index.html.twig', [
            'controller_name' => 'NavegacionController',
        ]);
    }
    #[Route('/ir-libros',name: 'app_ir_libros')]
    public function libros(): Response
    {
        return $this->redirectToRoute('app_libros');
    }
    #[Route('/documentacion', name: 'app_documentacion')]
    public function documentacion(): Response
    {
        return $this->redirect("https://symfony.com/doc/current/index.html");
    }
    #[Route('/recurso-creado', name: 'app_recurso_creado')]
    public function recursoCreado(): Response
    {
        return new Response('Recurso creado correctamente', 201);
    }
}
