
// Rafael Vega Librero

let nombreProyecto = prompt("Introduce el nombre del proyecto:");

if (nombreProyecto !== null) {

    let dia = parseInt(prompt("Introduce el día de revisión:"));
    let mes = parseInt(prompt("Introduce el mes de revisión (enero = 0):"));

    /** 1. Datos del proyecto */

    let nombreProyectoMinusculas = nombreProyecto.toLowerCase();
    let nombreProyectoMayusculas = nombreProyecto.toUpperCase();

    let palabrasSeparadas = nombreProyecto.split(" ");

    let numeroLetras = nombreProyecto.replaceAll(" ", "").length;

    console.log("DATOS DEL PROYECTO");

    console.log("Nombre en minúsculas: " + nombreProyectoMinusculas);
    console.log("Nombre en mayúsculas: " + nombreProyectoMayusculas);

    console.log("Primera palabra: " + palabrasSeparadas[0]);
    console.log("Segunda palabra: " + palabrasSeparadas[1]);
    console.log("Tercera palabra: " + palabrasSeparadas[2]);

    console.log("Nombre completo: " + nombreProyecto);
    console.log("Número de letras: " + numeroLetras);

    // Código del proyecto

    let codigoProyecto = palabrasSeparadas[0][0] +
                         palabrasSeparadas[1] +
                         palabrasSeparadas[2][0];

    codigoProyecto = codigoProyecto.toLowerCase();

    console.log("Código del proyecto: " + codigoProyecto);


    /** 2. Cuenta atrás para el lanzamiento */

    let fechaDespegue = new Date(2027, 5, 15);
    let fechaActual = new Date();

    let diferencia = fechaDespegue - fechaActual;

    let diasRestantes = Math.ceil(diferencia / (1000 * 60 * 60 * 24));

    console.log("Faltan " + diasRestantes + " días para el lanzamiento");


    /** 3. Revisiones anuales que caen en lunes */

    let añosLunes = [];

    console.log("REVISIONES QUE CAERÁN EN LUNES");

    for (let i = fechaActual.getFullYear(); i <= 2050; i++) {

        let fechaRevision = new Date(i, mes, dia);

        if (fechaRevision.getDay() === 1 &&
            fechaRevision.getMonth() === mes &&
            fechaRevision.getDate() === dia) {

            console.log(i);
            añosLunes.push(i);
        }
    }


    /** 4. Informe final de la misión */

    console.log("------------------------------------");
    console.log("INFORME FINAL DE LA MISIÓN");
    console.log("------------------------------------");

    console.log("Nombre del proyecto: " + nombreProyecto);
    console.log("Código del proyecto: " + codigoProyecto);

    console.log("Faltan " + diasRestantes + " días para el lanzamiento");

    console.log("La revisión anual caerá en lunes en los años:");

    console.log(añosLunes.join("\n"));

    console.log("PROYECTO PREPARADO PARA LA MISIÓN");
}
