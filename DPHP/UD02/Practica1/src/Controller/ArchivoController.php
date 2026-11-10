<?php
namespace App\Controller;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Exception\BadRequestHttpException;
use Symfony\Component\Routing\Attribute\Route;

final class ArchivoController extends AbstractController
{
    #[Route('/archivo/{anio}/{mes}/{dia}', name: 'app_archivo', requirements: ['anio' => '\d{4}', 'mes' => '0?[1-9]|1[0-2]', 'dia' => '0?[1-9]|[12]\d|3[01]'])]
    public function index(int $anio, ?int $mes = null, ?int $dia = null): Response
    {
        if ($dia !== null && !checkdate($mes, $dia, $anio)) {
            throw $this->createNotFoundException('Fecha no válida.');
        }
        $meses = [1 => 'enero', 'febrero', 'marzo', 'abril', 'mayo', 'junio', 'julio', 'agosto', 'septiembre', 'octubre', 'noviembre', 'diciembre'];
        $fecha = (string) $anio;
        if ($mes !== null) { $fecha = $meses[$mes].' de '.$anio; }
        if ($dia !== null) { $fecha = $dia.' de '.$fecha; }
        return $this->render('archivo/index.html.twig', ['fecha' => $fecha]);
    }
}
