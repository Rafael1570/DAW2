// Ejercicio 1: Rafael Vega Librero
// Mostramos el prompt para que el usuario lo vea
let respuesta = prompt ("Esta seguro de que quiere hacer esto?");
// Creamos el switch para controlar las opciones que el usuario pueda hacer
switch (respuesta) {
    case "si":
        alert ("Usted ha aceptado");
        break;
    case "no":
        alert ("Usted ha rehusado constestar");
        break;
    case respuesta == null:
        alert ("Ha reusado contestar");
        break
}