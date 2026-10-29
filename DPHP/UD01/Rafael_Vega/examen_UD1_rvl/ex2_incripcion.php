<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Formulario de registro</title>

</head>
<body>
<h1>Registro de usuario</h1>

<form action="ex2_incripcion.php" method="post">
    <label for="nombre">Nombre:</label>
    <input type="text" id="nombre" name="nombre" required value="<?php htmlspecialchars($_POST['nombre'])?>">

    <label for="correo">Correo electrónico:</label>
    <input type="email" id="correo" name="correo" required value="<?php htmlspecialchars($_POST['correo'])?>">

    <label for="edad">Edad:</label>
    <input type="number" id="edad" name="edad" min="0"  required value="<?php htmlspecialchars($_POST['edad'])?>">

    <label for="distancia">Distancia:</label>
    <input type="number" id="distancia" name="distancia" value="<?php htmlspecialchars($_POST['distancia'])?>">>

    <button type="submit">Enviar</button>
</form>
</body>
</html>
<?php
$_SERVER["REQUEST_METHOD"]="POST";
$nombre=$_POST["nombre"];
$correo=$_POST["correo"];
$edad=$_POST["edad"];
$distancia=$_POST["distancia"];

$validacion = true;

while ($validacion){
    if (strlen($nombre) < 3){
        echo "El nombre debe tener al menos 3 caracteres";
        $validacion = false;
    }else if (filter_var($correo, FILTER_VALIDATE_EMAIL)){
        echo "El formato del correo no es valido";
        $validacion = false;
    }else if ($distancia != 5 || $distancia != 10 || $distancia != 21){
        $validacion = false;
    }else if ($edad < 12 || $edad > 99){
        $validacion = false;
    }
}
$categoria = 0;
$categoria = match ($edad){
    $edad <18 => "Junior",
    $edad =18 => "Senior",
    $edad <= 39 => "Senior",
    $edad >= 40 => "Veterano",
};
?>
