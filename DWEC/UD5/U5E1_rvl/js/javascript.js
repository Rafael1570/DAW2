let titulos = document.querySelectorAll("h1");

let botonRojo = document.querySelector("#boton1");
let botonAmarillo = document.querySelector("#boton2");
let botonVerde = document.querySelector("#boton3");

botonRojo.addEventListener("click", cambiarColorRojo);
botonAmarillo.addEventListener("click", cambiarColorAmarillo);
botonVerde.addEventListener("click", cambiarColorVerde);

function cambiarColorRojo() {
   for(titulo of titulos){
    titulo.className = "rojo";
   }
}
function cambiarColorAmarillo() {
    for(titulo of titulos){
        titulo.className = "amarillo";
    }
}

function cambiarColorVerde() {
    for(titulo of titulos){
        titulo.className = "verde";
    }
}