<?php

namespace App\Controller;

use App\Form\RegistroType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class RegistroController extends AbstractController
{
    #[Route('/registro', name: 'app_registro', methods: ['GET', 'POST'])]
    public function registro(Request $request): Response
    {
        $form = $this->createForm(RegistroType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $datos = $form->getData();
            $datos['password'] = 'Recibida (oculta)';
            $datos['acepta_condiciones'] = $form->get('acepta_condiciones')->getData();

            return $this->render('datos.html.twig', ['datos' => $datos]);
        }

        return $this->render('registro.html.twig', ['form' => $form]);
    }
}
