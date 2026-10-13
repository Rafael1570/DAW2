<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Calculadora de Factorial</title>
    </head>
    <body>

    <h1>Calculadora de Factorial</h1>

    <form method="post" action="">
        <label for="numero">Introduce un número entero:</label>
        <input type="number" name="numero" id="numero" required>
        <button type="submit">Calcular factorial</button>
    </form>

    <?php
    if ($_SERVER["REQUEST_METHOD"] === "POST") {
        $numero = $_POST["numero"];

        if (!is_numeric($numero) || intval($numero) != $numero) {
            echo "<div>Por favor, introduce un número entero válido.</div>";
        } else {
            $numero = intval($numero);

            if ($numero < 0) {
                echo "<div>Error: no existe el factorial de un número negativo.</div>";
            } else {
                $factorial = 1;

                for ($i = 1; $i <= $numero; $i++) {
                    $factorial *= $i;
                }

                echo "<div>";
                echo "<strong>Resultado:</strong><br>";
                echo "$numero! = $factorial";
                echo "</div>";
            }
        }
    }
    ?>

    </body>
</html>
