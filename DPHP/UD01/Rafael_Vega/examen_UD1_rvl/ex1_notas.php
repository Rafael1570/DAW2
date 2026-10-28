<?php
$alumnos = [
    "Lucía" => [6.5, 7, 8],
    "Marcos" => [4, 3.5, 5],
    "Elena" => [9, 8.5, 10],
    "Pablo" => [5, 4.5, 6],
    "Sara" => [3, 5, 4]
];

function  CalcularMedia($notas, $alumnos){
    for ($i=0; $i <= length($alumnos); $i++) {
        foreach ($alumnos[$i] as $nom => $notas) {
            $sumatotal = array_sum($notas);
            $notas = $sumatotal / count($notas);
        }
    }
    return $notas;
}

function Calificacion($media, $alumnos){
    $media = calcularMedia($media, $alumnos);
    $media = match (true) {
        $media > 5 => "insuficiente",
        $media > 6 => "Suficiente",
        $media > 7 => "Bien",
        $media > 9 => "Notable",
        default => "Sobresaliente"
    };

}


?>

