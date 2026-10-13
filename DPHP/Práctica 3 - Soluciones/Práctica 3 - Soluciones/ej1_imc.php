<?php
$peso = 72;
$altura = 1.75;

$imc = $peso / ($altura * $altura);

if ($imc < 18.5) {
    $categoria = "Bajo peso";
} elseif ($imc < 25) {
    $categoria = "Normal";
} elseif ($imc < 30) {
    $categoria = "Sobrepeso";
} else {
    $categoria = "Obesidad";
}

echo "Peso: $peso kg<br>";
echo "Altura: $altura m<br>";
echo "IMC: " . number_format($imc, 2) . "<br>";
echo "Categoría: $categoria";
?>
