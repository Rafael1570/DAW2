<?php
function esPrimo($numero)
{
    if ($numero < 2) {
        return false;
    }
    for ($i = 2; $i * $i <= $numero; $i++) {
        if ($numero % $i == 0) {
            return false;
        }
    }
    return true;
}
?>
<form method="post">
    <label>Número N: <input type="number" name="n" required></label>
    <button type="submit">Calcular</button>
</form>
<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $n = (int) $_POST["n"];

    if ($n < 2) {
        echo "No hay números primos en ese rango.";
    } else {
        $primos = [];
        for ($i = 2; $i <= $n; $i++) {
            if (esPrimo($i)) {
                $primos[] = $i;
            }
        }
        echo "Primos entre 2 y $n: " . implode(", ", $primos) . "<br>";
        echo "Se han encontrado " . count($primos) . " números primos.";
    }
}
?>
