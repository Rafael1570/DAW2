<?php

    if ($_SERVER["REQUEST_METHOD"] === "POST") {
        $num1 = $_POST["num1"];
        $num2 = $_POST["num2"];
        $operacion = $_POST["operacion"];

        // Validación básica
        if (!is_numeric($num1) || !is_numeric($num2)) {
            $mensaje = "Error: ambos valores deben ser numéricos.";
        } else {
            $num1 = floatval($num1);
            $num2 = floatval($num2);

            // Estructura condicional para elegir operación
            if ($operacion === "suma") {
                $resultado = $num1 + $num2;
                $mensaje = "La suma de $num1 y $num2 es $resultado.";
            } elseif ($operacion === "resta") {
                $resultado = $num1 - $num2;
                $mensaje = "La resta de $num1 y $num2 es $resultado.";
            } elseif ($operacion === "multiplicacion") {
                $resultado = $num1 * $num2;
                $mensaje = "La multiplicación de $num1 por $num2 es $resultado.";
            } elseif ($operacion === "division") {
                if ($num2 == 0) {
                    $mensaje = "Error: no se puede dividir entre cero.";
                } else {
                    $resultado = $num1 / $num2;
                    $mensaje = "La división de $num1 entre $num2 es $resultado.";
                }
            } else {
                $mensaje = "Operación no válida.";
            }
        }
    } else {
        $mensaje = "Por favor, envía el formulario correctamente.";
    }
?>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Resultado de la operación</title>
    </head>
    <body>

    <div class="resultado">
        <h2>Resultado:</h2>
        <p><?= $mensaje ?></p>
        <a href="ej6_operaciones.html">Volver</a>
    </div>

    </body>
</html>
