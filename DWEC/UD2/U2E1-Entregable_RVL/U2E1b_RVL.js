let nombre = prompt("¿Como te llamas?");
let edad = prompt("¿Cuantos años tienes?");

let tiempoVivido = edad * 365;

switch (true) {
    case (edad < 18):
        alert("Hola " + nombre + "\n" + "Eres menor de edad y has vivido " + tiempoVivido + " días.");
        break;
    case (edad >= 18):
        alert("Hola " + nombre + "\n" + "Eres mayor de edad y has vivido " + tiempoVivido + " días.");
        break;
    
}

