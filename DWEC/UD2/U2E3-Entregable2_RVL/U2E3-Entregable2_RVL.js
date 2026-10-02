let aleatorio = Math.floor(Math.random() * 10 + 1);

let intentos = 0;
let numero;

while (true) {

    numero = prompt("Introduce un número del 1 al 10:");

    // Si el usuario pulsa Cancelar
    if (numero === null) {
        alert("El juego ha sido cancelado.");
        break;
    }

    // Si el usuario no introduce un número
    if (isNaN(numero) || numero === "") {
        alert("Error: debes introducir un número.");
        continue;
    }

    numero = Number(numero);
    intentos++;

    // Si el usuario acierta
    if (numero === aleatorio) {
        alert("¡Has acertado! El número era " + aleatorio +
              ". Has necesitado " + intentos + " intentos.");
        break;
    }

    // Indicamos si el número es menor o mayor
    if (numero < aleatorio) {
        alert("El número introducido es menor que el número secreto.");
    } else {
        alert("El número introducido es mayor que el número secreto.");
    }
}
