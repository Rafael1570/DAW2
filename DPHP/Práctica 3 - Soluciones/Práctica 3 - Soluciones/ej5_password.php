<form method="post">
    <label>Longitud (8-20): <input type="number" name="longitud" min="8" max="20" value="12" required></label><br>
    <label><input type="checkbox" name="mayusculas"> Mayúsculas</label><br>
    <label><input type="checkbox" name="numeros"> Números</label><br>
    <label><input type="checkbox" name="simbolos"> Símbolos</label><br>
    <button type="submit">Generar</button>
</form>
<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $longitud = (int) $_POST["longitud"];

    if ($longitud < 8 || $longitud > 20) {
        echo "La longitud debe estar entre 8 y 20 caracteres.";
    } else {
        $grupos = ["abcdefghijklmnopqrstuvwxyz"];
        if (isset($_POST["mayusculas"])) {
            $grupos[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (isset($_POST["numeros"])) {
            $grupos[] = "0123456789";
        }
        if (isset($_POST["simbolos"])) {
            $grupos[] = "!@#$%&*?-_+=";
        }

        $password = "";
        foreach ($grupos as $grupo) {
            $password .= $grupo[random_int(0, strlen($grupo) - 1)];
        }

        $todos = implode("", $grupos);
        while (strlen($password) < $longitud) {
            $password .= $todos[random_int(0, strlen($todos) - 1)];
        }

        echo "Contraseña generada: " . htmlspecialchars(str_shuffle($password));
    }
}
?>
