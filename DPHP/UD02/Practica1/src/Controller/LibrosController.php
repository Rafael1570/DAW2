<?php
namespace App\Controller;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\BadRequestHttpException;
use Symfony\Component\Routing\Attribute\Route;
#[Route('/libros', name: 'app_libros_')]
final class LibrosController extends AbstractController
{
    private const LIBROS = [
        1 => ['id' => 1, 'titulo' => 'Don Quijote de la Mancha', 'autor' => 'Miguel de Cervantes', 'categoria' => 'novela'],
        2 => ['id' => 2, 'titulo' => 'Rimas y leyendas', 'autor' => 'Gustavo Adolfo Bécquer', 'categoria' => 'poesia'],
        3 => ['id' => 3, 'titulo' => 'La sombra del viento', 'autor' => 'Carlos Ruiz Zafón', 'categoria' => 'novela'],
    ];
    #[Route('', name: 'index', methods: ['GET'])]
    public function index(): Response
    {
        return $this->render('libros/index.html.twig', ['libros' => self::LIBROS, 'categoria' => null]);
    }
    #[Route('/categoria/{nombre}', name: 'categoria', methods: ['GET'])]
    public function categoria(string $nombre): Response
    {
        $libros = array_filter(self::LIBROS, static fn (array $libro): bool => $libro['categoria'] === mb_strtolower($nombre));
        return $this->render('libros/index.html.twig', ['libros' => $libros, 'categoria' => $nombre]);
    }
    #[Route('/{id}', name: 'show', requirements: ['id' => '\d+'], methods: ['GET'])]
    public function show(int $id): Response
    {
        if (!isset(self::LIBROS[$id])) { throw $this->createNotFoundException('Libro no encontrado.'); }
        return $this->render('libros/show.html.twig', ['libro' => self::LIBROS[$id]]);
    }
}
