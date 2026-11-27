<?php

namespace App\Controller;

use App\Form\RegistroType;
use App\Service\Usuarios;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\FormError;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class RegistroController extends AbstractController
{
    #[Route('/', name: 'app_inicio', methods: ['GET', 'POST'])]
    #[Route('/registro', name: 'app_registro', methods: ['GET', 'POST'])]
    public function registro(Request $request, Usuarios $usuarios): Response
    {
        $form = $this->createForm(RegistroType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $datos = $form->getData();
            if ($usuarios->registrar($datos['usuario'], $datos['password'], $datos['nombre'], $datos['apellidos'], $datos['email'])) {
                $this->addFlash('success', 'Cuenta creada. Ya puedes iniciar sesión.');

                return $this->redirectToRoute('app_login', status: Response::HTTP_SEE_OTHER);
            }

            $form->get('usuario')->addError(new FormError('Ese nombre de usuario ya está en uso.'));
        }

        return $this->render('registro.html.twig', ['form' => $form]);
    }
}
