<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class RedireccionControllController extends AbstractController
{
    #[Route('/redireccion/controll', name: 'app_redireccion_controll')]
    public function index(): Response
    {
        return $this->render('redireccion_controll/index.html.twig', [
            'controller_name' => 'RedireccionControllController',
        ]);
    }
    #[Route('/panel', name: 'app_panel')]
    public function panel(): Response
    {
       return $this-> redirectToRoute('app_empleado_show');
    }
    #[Route('/normativa', name: 'app_normativa')]
    public function normativa(): Response
    {
        return $this->redirect('https://www.google.com/goto?url=CAESVAHEIkXXtJZLVvoBnMcys8Cw3tQcKqgSend2x6D9cW-7yqTivaNcExpkH56Awn6000j8MY-pNjSoLZSkpO4GRswG35LM0I5zMZzAnsL3irSQ-oJbRg&gsessionid=WvY9sHy8qIgWLC9aimsSFmbawuQCJvpGXUW9lVj2z3fEqmBYXBmebA');
    }
}
