
// Rafael Vega Librero

let numero1 = prompt("Ingrese el primer número:");
let numero2 = prompt("Ingrese el segundo número:");
let tipoOperacion = prompt("Ingrese la operación: \n1=suma \n2=resta \n3=división \n4=multiplicación");

numero1 = parseFloat(numero1);
numero2 = parseFloat(numero2);

switch (tipoOperacion) {
    case "1":
        if (isNaN(numero1) || isNaN(numero2)) {
            alert("Los valores ingresados no son números válidos.");
        } else {
            let suma = (numero1, numero2) => numero1 + numero2;
            let resultadoSuma = suma(numero1, numero2);
            let cadenaSuma = "<p>" + resultadoSuma + "</p>";

            document.write(cadenaSuma);
        }
        break;

    case "2":
        if (isNaN(numero1) || isNaN(numero2)) {
            alert("Los valores ingresados no son números válidos.");
        } else {
            let resta = (numero1, numero2) => numero1 - numero2;
            let resultadoResta = resta(numero1, numero2);
            let cadenaResta = "<p>" + resultadoResta + "</p>";

            document.write(cadenaResta);
        }
        break;

    case "3":
        if (isNaN(numero1) || isNaN(numero2) || numero2 == 0) {
            alert("Los números no son válidos o el divisor es cero.");
        } else {
            let division = (numero1, numero2) => numero1 / numero2;
            let resultadoDivision = division(numero1, numero2);
            let cadenaDivision = "<p>" + resultadoDivision + "</p>";

            document.write(cadenaDivision);
        }
        break;

    case "4":
        if (isNaN(numero1) || isNaN(numero2)) {
            alert("Los valores ingresados no son números válidos.");
        } else {
            let multiplicacion = (numero1, numero2) => numero1 * numero2;
            let resultadoMultiplicacion = multiplicacion(numero1, numero2);
            let cadenaMultiplicacion = "<p>" + resultadoMultiplicacion + "</p>";

            document.write(cadenaMultiplicacion);
        }
        break;

    default:
        alert("Operación no válida.");
        break;
}
