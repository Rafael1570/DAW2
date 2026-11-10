<?php
namespace App\Controller;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\BadRequestHttpException;
use Symfony\Component\Routing\Attribute\Route;
#[Route('/contactos', name: 'app_contactos_')]
final class ContactosController extends AbstractController
{
    private const CONTACTOS = [
        1 => ['id' => 1, 'nombre' => 'Ana García', 'telefono' => '600111222', 'correo' => 'ana@example.com'],
        2 => ['id' => 2, 'nombre' => 'Luis Pérez', 'telefono' => '600333444', 'correo' => 'luis@example.com'],
        3 => ['id' => 3, 'nombre' => 'Marta López', 'telefono' => '600555666', 'correo' => 'marta@example.com'],
    ];
    #[Route('', name: 'index', methods: ['GET'])]
    public function index(Request $request): Response
    {
        return $this->render('contactos/index.html.twig', ['contactos' => $request->getSession()->get('contactos', self::CONTACTOS)]);
    }

    #[Route('/nuevo', name: 'nuevo', methods: ['POST'])]
    public function nuevo(Request $request): Response
    {
        $nombre = trim($request->request->getString('nombre'));
        $telefono = trim($request->request->getString('telefono'));
        $correo = trim($request->request->getString('correo'));
        if ($nombre === '' || $telefono === '' || !filter_var($correo, FILTER_VALIDATE_EMAIL)) {
            throw new BadRequestHttpException('Introduce nombre, teléfono y un correo válido.');
        }
        $contactos = $request->getSession()->get('contactos', self::CONTACTOS);
        $id = max(array_keys($contactos)) + 1;
        $contactos[$id] = compact('id', 'nombre', 'telefono', 'correo');
        $request->getSession()->set('contactos', $contactos);
        return $this->redirectToRoute('app_contactos_index', [], Response::HTTP_SEE_OTHER);
    }

    #[Route('/buscar', name: 'buscar', methods: ['GET'])]
    public function buscar(Request $request): Response
    {
        $nombre = trim($request->query->getString('nombre'));
        $contactos = $request->getSession()->get('contactos', self::CONTACTOS);
        $resultados = array_filter($contactos, static fn (array $contacto): bool => mb_stripos($contacto['nombre'], $nombre) !== false);
        return $this->json(array_values($resultados));
    }

    #[Route('/{id}', name: 'show', requirements: ['id' => '\d+'], methods: ['GET'])]
    public function show(int $id, Request $request): Response
    {
        $contactos = $request->getSession()->get('contactos', self::CONTACTOS);
        if (!isset($contactos[$id])) {
            throw $this->createNotFoundException('Contacto no encontrado.');
        }
        return $this->render('contactos/show.html.twig', ['contacto' => $contactos[$id]]);
    }
}
