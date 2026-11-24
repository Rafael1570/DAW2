let campos = document.querySelector("#campos-generados");

function generarCampos(){
    let cantidad = parseInt(document.querySelector("#cantidad").value);
    if(cantidad >=1 && cantidad <=20){
    campos.replaceChildren();
        for(let i = 1 ; i <= cantidad; i++){
            let campo = document.createElement("input");
            let etiqueta = document.createElement("label");
            etiqueta.textContent = "Elemento " + i;
            campos.append(etiqueta, campo);
        }
    }
}
let generar = document.querySelector("#btn-generar");
generar.addEventListener("click", generarCampos);

function anadirLista(){
    let lista = document.querySelector("#lista");
    let inputs = document.querySelectorAll("#campos-generados input");
    for (const input of inputs) {
        if (input.value.trim() === "") {
            continue;
        }
        let li = document.createElement("li");
        li.textContent = input.value;
        lista.append(li);
    }
    campos.replaceChildren();
}
let anadir = document.querySelector("#btn-anadir");
anadir.addEventListener("click", anadirLista);

function borrarprimero(){
    let contenedorCampos = document.querySelector("#lista");
    contenedorCampos.firstElementChild.remove();
}
function borrarultimo(){
    let contenedorCampos = document.querySelector("#lista");
    contenedorCampos.lastElementChild.remove();
}
let botonprimero = document.querySelector("#btn-borrar-primero")
let botonultimo = document.querySelector("btn-borrar-ultimo");
botonprimero.addEventListener("click", borrarprimero);
botonultimo.addEventListener("click", borrarultimo);