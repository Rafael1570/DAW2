<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RequestStack;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class ContadorController extends AbstractController
{
    #[Route('/contador', name: 'app_contador', methods: ['GET'])]
    public function contador(RequestStack $requestStack): Response
    {
        $session = $requestStack->getCurrentRequest()->getSession();
        $visitas = $session->get('visitas', 0) + 1;
        $session->set('visitas', $visitas);

        return $this->render('contador.html.twig', ['visitas' => $visitas]);
    }

    #[Route('/contador/reiniciar', name: 'app_contador_reiniciar', methods: ['GET'])]
    public function reiniciar(RequestStack $requestStack): Response
    {
        $session = $requestStack->getCurrentRequest()->getSession();
        $session->remove('visitas');

        return $this->redirectToRoute('app_contador');
    }
}
