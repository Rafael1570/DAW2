//Rafael Vega Librero
let nombreProyecto = prompt("Introduce el nombre del proyecto:");
let dia = prompt("Introduce el día de entrega del proyecto:");
let mes = prompt("Introduce el mes de entrega del proyecto:");


/** 1. Mostramos el nombre del proyecto en minisculas y en mayúsculas, 
 * cada una de las tres palabras separadas. y contar las letras que forma
 * el nombre del proyecto */
let nombreProyectoMinusculas = nombreProyecto.toLowerCase();
let nombreProyectoMayusculas = nombreProyecto.toUpperCase();
let palabrasSeparadas = nombreProyecto.split(" ");
let numeroLetras = nombreProyecto.length;

console.log("Nombre del proyecto en minúsculas: " + nombreProyectoMinusculas);
console.log("Nombre del proyecto en mayúsculas: " + nombreProyectoMayusculas);
console.log("Palabras separadas: " + palabrasSeparadas.join(", "));
console.log("Número de letras que forma el nombre del proyecto: " + numeroLetras);
// codigo del proyecto

let codigoProyecto = nombreProyecto.length;
for (let j = 0; j < codigoProyecto.length; j++) {
}    



/** 2.Cuenta atras para lanzamiento */

let fechaDespegue = new Date(15, 5, 2027);
let fechaActual = new Date();
let diferencia = fechaDespegue - fechaActual;
let diasRestantes = Math.floor(diferencia / (1000 * 60 * 60 * 24));

console.log("Faltan " + diasRestantes + " días para el lanzamiento de la misión");

/** 3. Revisiones anuales y mostrar solo los que caen lunes */
for (let i = fechaActual.getFullYear(); i < 2050; i++) {
    let fechaRevision = new Date(i, 0, 1);
    if (fechaRevision.getDay() === 1) {
        console.log("Revisión anual para el año " + i + ": " + fechaRevision);
    }
}

// Informe final de la misión
console.log("------------------------------------");
console.log("     Informe final de la misión:");
console.log("------------------------------------");
console.log("Nombre del proyecto: " + nombreProyecto);
console.log("Código del proyecto: "  );
console.log("Faltan " + diasRestantes + " días para el lanzamiento");
console.log("La revisión anual del proyecto caerá en lunes en los años: ");
console.log(fechaRevision + "\n")


