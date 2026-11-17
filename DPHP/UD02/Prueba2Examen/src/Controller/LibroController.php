<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
#[Route('/libros', name: 'app_libros')]
final class LibroController extends AbstractController
{
    #[Route('', name: 'app_libro')]
    public function index(): Response
    {
        $libros=[
            1 => "Los tres cerditos",
            2 => "Caperucita Roja",
            3 => "Los Fubolisimos",
            4 => "Solo Leveling"
        ];
        return $this->render('libro/index.html.twig', ['libros' => $libros]);
    }
    #[Route('/{id}', name: 'app_libro_show')]
    public function show($id): Response
    {
        $libros=[
            1 => "Los tres cerditos",
            2 => "Caperucita Roja",
            3 => "Los Fubolisimos",
            4 => "Solo Leveling"
        ];
        if (!isset($libros[$id])) {
            throw $this->createNotFoundException('No se encuentra el libro '.$id);
        }
        $libro=$libros[$id];
        return $this->render('libro/show.html.twig', ['id' => $id,  'libro' => $libro]);
    }
    #[Route('/categoria/{nombre}', name: 'app_libros_categoria')]
    public function categoria(string $nombre): Response
    {
        $libros = [
            1 => ['titulo' => 'Harry Potter', 'categoria' => 'fantasia'],
            2 => ['titulo' => 'It', 'categoria' => 'terror'],
            3 => ['titulo' => 'El Quijote', 'categoria' => 'novela'],
            4 => ['titulo' => 'El Hobbit', 'categoria' => 'fantasia']
        ];
        $filtrados = [];
        foreach ($libros as $id => $libro) {
            if ($libro['categoria'] === $nombre) {
                $filtrados[$id] = $libro;
            }
        }
        return $this->render('libro/categoria.html.twig', ['categoria'=> $nombre, 'libros' => $filtrados]);

    }
}
