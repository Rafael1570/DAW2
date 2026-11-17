<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
#[Route('/videojuego')]
final class VideojuegoController extends AbstractController
{
    #[Route('', name: 'app_videojuegos_index')]
    public function index(): Response
    {
        $videojuegos = [
          1=> 'Minecraft',
          2=> 'Valorant',
          3=> 'Fornite',
          4=> 'GTA VI'
        ];
        return $this->render('videojuego/index.html.twig', ['videojuegos' => $videojuegos]);
    }
    #[Route('/{id}', name: 'app_videojuegos_show', requirements: ['id' => '\d+'])]
    public function show(int $id): Response
    {
        $juego = '';
        if($id === 1) {
            $juego = 'Minecraft';
        }else if($id === 2) {
            $juego = 'Valorant';
        }else if($id === 3) {
            $juego = 'Fornite';
        }else if($id === 4) {
            $juego = 'GTA VI';
        }
        return $this->render('videojuego/show.html.twig', ['id' => $id,  'juego' => $juego]);
    }
}
