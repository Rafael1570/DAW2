<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;
#[Route('/rrhh', name: 'app_rrhh')]
final class Ejercicio4Controller extends AbstractController
{
    #[Route('/ejercicio4', name: 'app_ejercicio4')]
    public function index(): Response
    {
        return $this->render('ejercicio4/index.html.twig', [
            'controller_name' => 'Ejercicio4Controller',
        ]);
    }
    #[Route('/empleados', name: 'app_empleados')]
    public function empleados(): Response
    {
        $empleados = [
            1 => "Rafael Vega Librero",
            2 => "Jose Maria Gomez Velez",
            3 => "Alejandro Ruiz Martinez",
            4 => "Mario Jurado de la Mota"
        ];
        return $this->render('ejercicio4/empleados.html.twig', ['empleados' => $empleados]);
    }
    #[Route('/empleados/departamento/{nombre}', name: 'app_empleados_departamento')]
    public function empleadosDepartamento(string $nombre): Response
    {
        $empleados = [
            1 => ['nombre' => 'Rafael Vega Librero', 'departamento' => 'informatica'],
            2 => ['nombre' => 'Jose Maria Gomez Velez', 'departamento' => 'ventas'],
            3 => ['nombre' => 'Alejandro Ruiz Martinez', 'departamento' => 'informatica'],
            4 => ['nombre' => 'Mario Jurado de la Mota', 'departamento' => 'ventas']
        ];
        $filtrados = [];
        foreach ($empleados as $id => $empleado) {
            if ($empleado['departamento'] === $nombre) {
                $filtrados[$id] = $empleado;
            }
        }
        return $this->render('ejercicio4/departamento.html.twig', ['empleados' => $filtrados]);
    }
}
