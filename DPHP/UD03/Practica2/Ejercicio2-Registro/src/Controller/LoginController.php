<?php

namespace App\Controller;

use App\Form\LoginType;
use App\Service\Usuarios;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\FormError;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class LoginController extends AbstractController
{
    #[Route('/login', name: 'app_login', methods: ['GET', 'POST'])]
    public function login(Request $request, Usuarios $usuarios): Response
    {
        $form = $this->createForm(LoginType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $datos = $form->getData();
            if ($usuarios->autenticar($datos['username'], $datos['password'])) {
                $this->addFlash('success', 'Credenciales correctas para '.$datos['username'].'.');

                return $this->redirectToRoute($request->attributes->getString('_route'), status: Response::HTTP_SEE_OTHER);
            }

            $form->addError(new FormError('El usuario o la contraseña son incorrectos.'));
        }

        return $this->render('login.html.twig', ['form' => $form]);
    }
}
