<?php

namespace App\Controller\Admin;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

#[Route('/admin/pedidos', name: 'admin_pedidos_')]
class PedidosController extends AbstractController
{
    #[Route('', name: 'lista')]
    public function lista(Request $request): Response
    {
        return $this->render('admin/pedidos.html.twig', [
            'pedidos' => $this->pedidos(),
            'platoRecibido' => $request->getSession()->getFlashBag()->get('plato_recibido'),
        ]);
    }

    #[Route('/confirmado', name: 'confirmado')]
    public function confirmado(): Response
    {
        return new Response('Pedido registrado', Response::HTTP_CREATED);
    }

    #[Route('/nuevo', name: 'nuevo', methods: ['POST'])]
    public function nuevo(Request $request): RedirectResponse
    {
        $plato = $request->request->getString('plato');
        $this->addFlash('plato_recibido', $plato);

        return $this->redirectToRoute('admin_pedidos_lista');
    }

    private function pedidos(): array
    {
        return [
            1 => ['mesa' => 3, 'plato' => 'Paella valenciana', 'estado' => 'Servido'],
            2 => ['mesa' => 7, 'plato' => 'Gazpacho', 'estado' => 'En cocina'],
            3 => ['mesa' => 1, 'plato' => 'Tarta de queso', 'estado' => 'Pendiente'],
        ];
    }
}
