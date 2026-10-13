<!DOCTYPE html>
    <html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Números Aleatorios</title>
    </head>
    <body>

    <h1>Generador de números aleatorios</h1>

    <form method="post" action="">
        <button type="submit">Generar 10 números</button>
    </form>

    <?php
    if ($_SERVER["REQUEST_METHOD"] === "POST") {
        // Generar array de 10 números aleatorios entre 1 y 100
        $numeros = [];
        for ($i = 0; $i < 10; $i++) {
            $numeros[] = rand(1, 100);
        }

        // Calcular mayor, menor y promedio
        $mayor = max($numeros);
        $menor = min($numeros);
        $promedio = array_sum($numeros) / count($numeros);

        echo "<div>";
        echo "<p><strong>Números generados:</strong></p>";
        echo "<p>" . implode(" - ", $numeros) . "</p>";
        echo "<p><strong>Número mayor:</strong> $mayor</p>";
        echo "<p><strong>Número menor:</strong> $menor</p>";
        echo "<p><strong>Promedio:</strong> " . number_format($promedio, 2) . "</p>";
        echo "</div>";
    }
    ?>

    </body>
</html>
