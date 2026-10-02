let dato;

while(true) {
    dato = prompt("Introduce un número:");

    if (dato == 0) {
        alert("El numero es 0");

    } else if (dato < 100) {
        alert("El numero es menor que 100");

    } else if (dato > 100) {
        alert("El numero es mayor que 100");

    } else if (dato = 100){
        alert("El numero es 100");
    } else if (dato = ""){
        alert("Es una cadena vacia");
    } else if (dato.length){
        alert("Es una cadena");
    }

}