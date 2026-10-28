<?php
date_default_timezone_set("Europe/Madrid");

if (isset($_GET["reiniciar"])) {
    setcookie("visitas", "", time() - 3600);
    setcookie("ultima", "", time() - 3600);
    header("Location: ej6_visitas.php");
    exit;
}

$visitas = isset($_COOKIE["visitas"]) ? $_COOKIE["visitas"] + 1 : 1;
$ultima = $_COOKIE["ultima"] ?? "";

setcookie("visitas", $visitas, time() + 30 * 24 * 3600);
setcookie("ultima", date("d/m/Y H:i:s"), time() + 30 * 24 * 3600);

if ($visitas == 1) {
    echo "<p>Bienvenido por primera vez</p>";
} else {
    echo "<p>Has visitado esta página $visitas veces.</p>";
    echo "<p>Tu última visita fue el $ultima.</p>";
}
?>
<a href="ej6_visitas.php?reiniciar=1">Reiniciar contador</a>
