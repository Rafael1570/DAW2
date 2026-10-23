let producto = "ordenador portatil";
let precio = 850;
let porcentajeDescuento = 15;

let descuento = (precio, porcentajeDescuento) => (precio * porcentajeDescuento) / 100;
let precioDescuento = descuento(precio, porcentajeDescuento);

let precioFinal = (precio, precioDescuento) => precio - precioDescuento;
let dineroFinal = precioFinal(precio, precioDescuento);
console.log("El precio final del " + producto + " con un descuento del " + porcentajeDescuento + "% es: " + dineroFinal);
