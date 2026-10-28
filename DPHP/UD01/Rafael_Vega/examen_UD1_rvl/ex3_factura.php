<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Formulario de registro</title>

</head>
<body>
<h1>Factura Luz</h1>

<form action="ex3_factura.php" method="post">
    <label for="gasto">Gasto:</label>
    <input type="float" id="gasto" name="gasto" required value="<?php htmlspecialchars($_POST['gasto'])?>">

    <label for="potencia">Indicame la potencia contratada:</label>
    <input type="float" id="potencia" name="potencia" value="<?php htmlspecialchars($_POST['potencia'])?>">
    <button type="submit">Enviar</button>
</form>
</body>
</html>

<?php
$gasto = $_POST['gasto'];
$potencia = $_POST['potencia'];
$validar = true;

while ($validar) {
    if ($gasto >= 0 ){
        echo "<p> El gasto introducido no es valido</p>";
    }else if ($potencia >= 1 || $potencia <= 15){
        echo "<p> La potencia introducido no es valido</p>";
    }
return false;
}
function calcularConsumo($Kwh, $gasto){
    $kWh = $gasto * 0.15;
    return $kWh;
}
function calcularPotencia($Kw, $potencia){
    $kw = $potencia * 30 * 0.10;
    return $kw;
}
$Kw = 0;
$kw = calcularConsumo($Kw, $potencia);
$baseimponible = $gasto + $Kw;



?>