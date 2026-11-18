<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class EmpleadosController extends AbstractController
{
    #[Route('/empleado/{id}', name: 'app_empleado_show' , requirements: ['id' => '\d+'])]
    public function show(int $id): Response
    {
      return $this->render('empleados/show.html.twig', ['id' => $id]);
    }
    #[Route('/empleado/nuevo', name: 'app_empleado_new', requirements: ['id' => '\w+'])]
    public function nuevo(): Response
    {
        return $this->render('empleados/new.html.twig', []);
    }
}
