<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class BlogController extends AbstractController
{
    #[Route('/blog', name: 'app_blog')]
    public function index(): Response
    {
        return $this->render('blog/index.html.twig', [
            'controller_name' => 'BlogController',
        ]);
    }
    #[Route('/blog/{pagina}', name: 'app_blog_pagina', requirements: ['pagina' => '\d+'])]
    public function pagina(int $pagina): Response
    {
        $elementos = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j"];
        $arrayResultado= [];
        if ($pagina === 1){
            for($i = 0; $i <= 3; $i++){
                $arrayResultado[]=$elementos[$i];
            }
        }else if($pagina === 2){
            for($i = 4; $i <= 6; $i++){
                $arrayResultado[]=$elementos[$i];
            }
        }else if($pagina === 3){
            for($i = 7; $i <= 9; $i++){
                $arrayResultado[]=$elementos[$i];
            }
        }
        return $this->render('blog/pagina.html.twig', ['resultadoArray' => $arrayResultado, 'pagina' => $pagina]);
    }
    #[Route('/blog/{slug}', name: 'app_blog_slug')]
    public function slug(string $slug): Response
    {
        $elemento = ["a", "b", "c"];
        $encontrado = in_array($slug, $elemento);
        return $this->render('blog/slug.html.twig', ['slug' => $slug, 'encontrado' => $encontrado]);
    }
}
