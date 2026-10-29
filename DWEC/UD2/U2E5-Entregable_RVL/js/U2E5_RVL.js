// Rafael Vega Librero

let numerodados;

while (true) {

    numerodados = prompt("¿Cuántos dados desea tirar? (1-4)");

    // Si pulsa Cancelar, termina el programa
    if (numerodados === null) {
        break;
    }

    // Comprobar que introduce un número válido
    while (numerodados !== null &&
           (numerodados.trim() === "" ||
            !Number.isInteger(Number(numerodados)) ||
            numerodados < 1 ||
            numerodados > 4)) {

        alert("Debes introducir un número entre 1 y 4");
        numerodados = prompt("¿Cuántos dados desea tirar? (1-4)");
    }

    // Si pulsa Cancelar durante la validación
    if (numerodados === null) {
        break;
    }

    let resultado = "";

    // Tirar los dados
    for (let i = 1; i <= Number(numerodados); i++) {
        let dado = Math.floor(Math.random() * 6) + 1;

        console.log(dado);

        resultado += "Dado " + i + ": " + dado + "\n";
    }

    // Mostrar los resultados
    alert(resultado);
}