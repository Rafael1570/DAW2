<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\RequestStack;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpKernel\Exception\AccessDeniedHttpException;
use Symfony\Component\Routing\Attribute\Route;

class LogoutController extends AbstractController
{
    #[Route('/logout', name: 'app_logout', methods: ['POST'])]
    public function logout(Request $request, RequestStack $requestStack): Response
    {
        if (!$this->isCsrfTokenValid('logout', $request->request->getString('_token'))) {
            throw new AccessDeniedHttpException('El formulario de cierre de sesión no es válido.');
        }

        $session = $requestStack->getCurrentRequest()->getSession();
        $session->invalidate();
        $this->addFlash('success', 'La sesión se ha cerrado correctamente.');

        return $this->redirectToRoute('app_login', status: Response::HTTP_SEE_OTHER);
    }
}
