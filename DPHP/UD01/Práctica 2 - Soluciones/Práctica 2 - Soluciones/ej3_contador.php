

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Contador de Vocales</title>
    </head>
    <body>

    <h1>Contador de Vocales</h1>

    <form method="post" action="">
        <label for="texto">Introduce una cadena de texto:</label><br>
        <textarea name="texto" id="texto" required></textarea><br>
        <button type="submit">Contar vocales</button>
    </form>

    <?php
    if ($_SERVER["REQUEST_METHOD"] === "POST") {
        $texto = trim($_POST["texto"]);

        if ($texto === "") {
            echo "<div>Por favor, introduce un texto válido.</div>";
        } else {
            // Pasamos todo a minúsculas para ignorar mayúsculas
            $texto = strtolower($texto);

            // Contador de vocales
            $contador = 0;
            $vocales = ['a', 'e', 'i', 'o', 'u'];

            for ($i = 0; $i < strlen($texto); $i++) {
                if (in_array($texto[$i], $vocales)) {
                    $contador++;
                }
            }

            echo "<div>";
            echo "El texto introducido contiene <strong>$contador</strong> vocal(es).";
            echo "</div>";
        }
    }
    ?>

    </body>
</html>
