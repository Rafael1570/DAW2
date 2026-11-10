<?php
namespace App\Controller\Admin;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
#[Route('/admin', name: 'app_admin_')]
final class PanelController extends AbstractController
{
    #[Route('', name: 'inicio')]
    public function index(): Response
    {
        return $this->render('admin/index.html.twig');
    }
}
