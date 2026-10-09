let continuar = true;
while (continuar){
let respuesta = prompt("Introduce numero: ");

while (isNaN(respuesta)){
    // alert("Introduce un numero");
    respuesta = prompt("Introduce un numero valido: ");
}
if (respuesta === null){
    // alert("El usuario le ha dado a cancelar");
    let confirmacion = confirm("¿Estas seguro que quieres salir?");
    if (confirmacion){
        continuar = false;
    }
}else {
    alert("Has introudcido un numero");
}
}
// let respuesta;
// do{
//     respuesta = prompt("Introduce un numero: ");
// }while (isNaN(respuesta));