<?php

namespace App\Controller;

use App\Dto\Contacto;
use App\Form\ContactoType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

class ContactoController extends AbstractController
{
    #[Route('/contacto', name: 'app_contacto', methods: ['GET', 'POST'])]
    public function contacto(Request $request): Response
    {
        $form = $this->createForm(ContactoType::class, new Contacto());
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->addFlash('success', 'Mensaje enviado correctamente');

            return $this->redirectToRoute('app_contacto', status: Response::HTTP_SEE_OTHER);
        }

        return $this->render('contacto.html.twig', ['form' => $form]);
    }
}
