<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpKernel\Exception\AccessDeniedHttpException;
use Symfony\Component\Routing\Attribute\Route;

class HomeController extends AbstractController
{
    #[Route('/home', name: 'app_home', methods: ['GET'])]
    public function home(Request $request): Response
    {
        $usuario = $request->getSession()->get('username');
        if ($usuario === null) {
            return $this->redirectToRoute('app_login');
        }

        return $this->render('home.html.twig', ['usuario' => $usuario]);
    }

    #[Route('/logout', name: 'app_logout', methods: ['POST'])]
    public function logout(Request $request): Response
    {
        if (!$this->isCsrfTokenValid('logout', $request->request->getString('_token'))) {
            throw new AccessDeniedHttpException('El formulario de cierre de sesión no es válido.');
        }

        $request->getSession()->invalidate();

        return $this->redirectToRoute('app_login', status: Response::HTTP_SEE_OTHER);
    }
}
