//Nodo del contenedor
let contenedor = document.querySelector("#contenedor");
// Crear la tarjeta.
let tarjeta=document.createElement("div");
// Añadir la clase card
tarjeta.classList.add("card");
// Crear la imagen.
let imagen = document.createElement("img");
// Añadir la ruta
imagen.setAttribute("src", "img/alhambra.jpg");
// Añadir el alt
imagen.setAttribute("alt", "La Alhambra");
// Añadir la clase card-img-top
imagen.classList.add("card-img-top");
// Crear el div del cuerpo.
let cuerpo = document.createElement("div");
//Añadir la clase card-body
cuerpo.classList.add("card-body");
// Crear el título.
const titulo = document.createElement("h5");
//Añadir la clase card-title
titulo.classList.add("card-title");
//Añadir el textContent
titulo.textContent="La Alhambra";
// Crear la descripción (es un p).
let parrafo=document.createElement("p");
//Añadir la clase card-text
parrafo.classList.add("card-text");
//Añadir el textContent
parrafo.textContent="Monumento hisytoríco de Granada";
// Construir la tarjeta. Añadir al cuerpo el título y la descripción
cuerpo.append(titulo, parrafo);
// Añadir a la tarjeta, la imagen y el cuerpo;
tarjeta.append(imagen,cuerpo);
// Añadir la tarjeta al contenedor 
contenedor.append(tarjeta);
