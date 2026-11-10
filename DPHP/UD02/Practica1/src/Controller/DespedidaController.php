<?php
namespace App\Controller;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\BadRequestHttpException;
use Symfony\Component\Routing\Attribute\Route;

final class DespedidaController extends AbstractController
{
    #[Route('/despedida', name: 'app_despedida')]
    public function despedida(): Response
    {
        return $this->redirectToRoute('app_saludo');
    }
}
