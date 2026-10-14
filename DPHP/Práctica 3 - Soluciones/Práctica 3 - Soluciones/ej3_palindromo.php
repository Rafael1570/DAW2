<form method="post">
    <label>Frase: <input type="text" name="frase" required></label>
    <button type="submit">Comprobar</button>
</form>
<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $frase = trim($_POST["frase"]);
    $invertida = strrev($frase);
    $limpia = str_replace(" ", "", strtolower($frase));

    echo "Frase original: " . $frase . "<br>";
    echo "Frase invertida: " . $invertida . "<br>";

    if ($limpia == strrev($limpia)) {
        echo "Es un palíndromo.";
    } else {
        echo "No es un palíndromo.";
    }
}
?>
