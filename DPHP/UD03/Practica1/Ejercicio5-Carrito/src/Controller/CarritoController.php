<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RequestStack;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class CarritoController extends AbstractController
{
    #[Route('/carrito', name: 'carrito_ver', methods: ['GET'])]
    public function ver(RequestStack $requestStack): Response
    {
        $session = $requestStack->getCurrentRequest()->getSession();
        $carrito = $session->get('carrito', []);

        return $this->render('carrito.html.twig', [
            'carrito' => $carrito,
            'total' => count($carrito),
        ]);
    }

    #[Route('/carrito/anadir/{producto}', name: 'carrito_anadir', methods: ['GET'])]
    public function anadir(string $producto, RequestStack $requestStack): Response
    {
        $session = $requestStack->getCurrentRequest()->getSession();
        $carrito = $session->get('carrito', []);
        $carrito[] = $producto;
        $session->set('carrito', $carrito);

        return $this->redirectToRoute('carrito_ver');
    }

    #[Route('/carrito/vaciar', name: 'carrito_vaciar', methods: ['GET'])]
    public function vaciar(RequestStack $requestStack): Response
    {
        $session = $requestStack->getCurrentRequest()->getSession();
        $session->remove('carrito');

        return $this->redirectToRoute('carrito_ver');
    }
}
