<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
use Symfony\Contracts\Service\Attribute\Required;

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
        return $this->render('blog/pagina.html.twig', ['pagina' => $pagina]);
    }
    #[Route('/blog/{slug}', name: 'app_blog_slug', requirements: ['slug' => '\w+'])]
    public function slug(string $slug): Response
    {
        return $this->render('blog/slug.html.twig', ['slug' => $slug]);
    }
}
