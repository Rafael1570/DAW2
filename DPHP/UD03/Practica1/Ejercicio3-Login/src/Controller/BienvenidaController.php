<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RequestStack;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class BienvenidaController extends AbstractController
{
    #[Route('/bienvenida', name: 'app_bienvenida', methods: ['GET'])]
    public function bienvenida(RequestStack $requestStack): Response
    {
        $session = $requestStack->getCurrentRequest()->getSession();
        $username = $session->get('username');

        if ($username === null) {
            return $this->redirectToRoute('app_login');
        }

        return $this->render('bienvenida.html.twig', ['username' => $username]);
    }
}
