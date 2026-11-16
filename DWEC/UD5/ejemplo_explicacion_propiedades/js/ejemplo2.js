let imagen=document.querySelector("#logo");
let btnimg=document.querySelectorAll("button")[2];
btnimg.addEventListener("click", cambia_imagen);

function cambia_imagen(){
    if(imagen.getAttribute("src")==="img/logo_alixar.png"){
        imagen.setAttribute("src","img/logo_alamos.png");
    }else {
        imagen.setAttribute("src", "img/logo_alixar.png");
    }   
}