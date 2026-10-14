<?php
define("IVA", 0.21);

$carrito = [
    ["nombre" => "Ratón inalámbrico", "precio" => 15.99, "cantidad" => 2],
    ["nombre" => "Teclado Logitech", "precio" => 34.50, "cantidad" => 1],
    ["nombre" => "USB 2.0","precio" => 4.25, "cantidad" => 3],
    ["nombre" => "Auriculars inalámbricos", "precio" => 12.00, "cantidad" => 1]
];

$totalSinIva = 0;

echo "<table border='1'>";
echo "<tr>
        <th>Producto</th>
        <th>Precio</th>
        <th>Cantidad</th>
        <th>Subtotal</th>
    </tr>";
foreach ($carrito as $producto) {
    $subtotal = $producto["precio"] * $producto["cantidad"];
    $totalSinIva += $subtotal;
    echo "<tr>";
        echo "<td>" . $producto["nombre"] . "</td>";
        echo "<td>" . number_format($producto["precio"], 2) . " €</td>";
        echo "<td>" . $producto["cantidad"] . "</td>";
        echo "<td>" . number_format($subtotal, 2) . " €</td>";
    echo "</tr>";
}
echo "</table>";

$iva = $totalSinIva * IVA;
$total = $totalSinIva + $iva;

echo "<p>Total sin IVA: " . number_format($totalSinIva, 2) . " €<br>";
echo "IVA (21 %): " . number_format($iva, 2) . " €<br>";
echo "Total: " . number_format($total, 2) . " €</p>";

if ($total > 100) {
    $descuento = $total * 0.10;
    echo "<p>Descuento (10 %): -" . number_format($descuento, 2) . " €<br>";
    echo "Total con descuento: " . number_format($total - $descuento, 2) . " €</p>";
}
?>
