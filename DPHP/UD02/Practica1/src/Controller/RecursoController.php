<?php
namespace App\Controller;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\BadRequestHttpException;
use Symfony\Component\Routing\Attribute\Route;

final class RecursoController extends AbstractController
{
    #[Route('/recurso/nuevo', name: 'app_recurso_nuevo')]
    public function nuevo(): Response
    {
        return $this->json(['id' => 1, 'nombre' => 'Producto de ejemplo', 'mensaje' => 'Recurso creado (simulación)'], Response::HTTP_CREATED);
    }
    #[Route('/symfony-docs', name: 'app_symfony_docs')]
    public function documentacion(): Response
    {
        return $this->redirect('https://symfony.com/doc/current/index.html');
    }
}
