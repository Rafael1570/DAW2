<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Conversor de Temperaturas</title>
    </head>
    <body>

    <h1>Conversor de Temperaturas</h1>

    <form method="post" action="">
        <label for="celsius">Introduce la temperatura en °C:</label>
        <input type="text" name="celsius" id="celsius" required>
        <button type="submit">Convertir</button>
    </form>

    <?php
    if ($_SERVER["REQUEST_METHOD"] === "POST") {
        $celsius = trim($_POST["celsius"]);

        // Validar que sea numérico
        if ($celsius === "" || !is_numeric($celsius)) {
            echo "<div>Por favor, introduce un valor numérico válido.</div>";
        } else {
            // Calcular Fahrenheit y Kelvin
            $fahrenheit = ($celsius * 9 / 5) + 32;
            $kelvin = $celsius + 273.15;

            // Mostrar resultados
            echo "<div>";
            echo "<strong>Resultados:</strong><br>";
            echo "Temperatura en Celsius: $celsius °C<br>";
            echo "Temperatura en Fahrenheit: " . number_format($fahrenheit, 2) . " °F<br>";
            echo "Temperatura en Kelvin: " . number_format($kelvin, 2) . " K";
            echo "</div>";
        }
    }
    ?>

    </body>
</html>
