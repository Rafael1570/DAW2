// Rafael Vega Librero
let dato;

while (true) {
    // Pedimos al usuario que nos introduzca un dato mediante un prompt
    dato = prompt("Introduce un dato:");

    if (dato === null) {
        alert("Programa terminado.");
        break;

    } else if (dato === "") {
        alert("Es una cadena vacía");

    } else if (isNaN(dato)) {
        alert("Es un carácter");

    } else if (Number(dato) === 0) {
        alert("El número es 0");

    } else if (Number(dato) < 100) {
        alert("El número es menor que 100");

    } else if (Number(dato) > 100) {
        alert("El número es mayor que 100");

    } else if (Number(dato) === 100) {
        alert("El número es 100");
    }
}