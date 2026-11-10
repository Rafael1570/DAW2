<?php
namespace App\Controller;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\BadRequestHttpException;
use Symfony\Component\Routing\Attribute\Route;

final class TareasController extends AbstractController
{
    private const TAREAS = [
        1 => ['id' => 1, 'titulo' => 'Repasar rutas', 'descripcion' => 'Practicar los atributos Route.'],
        2 => ['id' => 2, 'titulo' => 'Estudiar Twig', 'descripcion' => 'Crear una plantilla con un listado.'],
        3 => ['id' => 3, 'titulo' => 'Hacer ejercicios', 'descripcion' => 'Completar la práctica de Symfony.'],
        4 => ['id' => 4, 'titulo' => 'Revisar la entrega', 'descripcion' => 'Comprobar todas las URL.'],
    ];

    // La misma acción devuelve HTML o JSON según el sufijo.
    #[Route('/tareas.{_format}', name: 'app_tareas', defaults: ['_format' => 'html'], requirements: ['_format' => 'html|json'], methods: ['GET'])]
    public function index(Request $request, string $_format): Response
    {
        $tareas = $request->getSession()->get('tareas', self::TAREAS);
        if ($_format === 'json') {
            return $this->json(array_values($tareas));
        }
        return $this->render('tareas/index.html.twig', ['tareas' => $tareas]);
    }

    #[Route('/tareas/nueva', name: 'app_tareas_nueva', methods: ['POST'])]
    public function nueva(Request $request): Response
    {
        $titulo = trim($request->request->getString('titulo'));
        if ($titulo === '') {
            throw new BadRequestHttpException('El título es obligatorio.');
        }
        $tareas = $request->getSession()->get('tareas', self::TAREAS);
        $id = max(array_keys($tareas)) + 1;
        $tareas[$id] = ['id' => $id, 'titulo' => $titulo, 'descripcion' => trim($request->request->getString('descripcion'))];
        $request->getSession()->set('tareas', $tareas);
        return $this->redirectToRoute('app_tareas', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/tareas/{id}', name: 'app_tareas_show', requirements: ['id' => '\d+'], methods: ['GET'])]
    public function show(int $id, Request $request): Response
    {
        $tareas = $request->getSession()->get('tareas', self::TAREAS);
        if (!isset($tareas[$id])) {
            throw $this->createNotFoundException('Tarea no encontrada.');
        }
        return $this->render('tareas/show.html.twig', ['tarea' => $tareas[$id]]);
    }
}
