// ANDALUCÍA INTERACTIVA · MAÑANA · EQUIPO 3
// El HTML, CSS, datos.js y navegacion.js ya están preparados.
// Usad las etiquetas, clases y atributos indicados en estos comentarios.
// No modifiquéis los archivos de los demás equipos.
// Los eventos deben utilizar funciones manejadoras independientes.
// No se permite innerHTML, appendChild ni document.write.

// NOMBRES DE VARIABLES AL UNIR LOS OCHO ARCHIVOS
// Todos los <script> de index.html comparten el ámbito global.
// Si declaramos arriba del archivo "const contenedor" y otro equipo hace lo
// mismo, al integrar habrá un error: el identificador ya existe.
// REGLA: las variables declaradas FUERA de funciones llevarán el prefijo
// de este equipo (ga). Ejemplos: gaContenedor, gaLista, gaIndice.
// Las variables declaradas DENTRO de una función pueden llamarse tarjeta,
// imagen, boton, lista... aunque otro equipo use esos nombres en sus funciones.
// Conservad los nombres de las funciones que ya aparecen escritas abajo.
// No introduzcáis variables globales innecesarias.
// CONSEJO PARA ESTE EQUIPO: Si necesitáis recordar la posición actual entre eventos, usad una variable global propia del equipo, por ejemplo gaIndiceActual.
// ESTRUCTURA HTML Y CSS: Los botones de miniatura son <button class="thumb"> con <img> dentro y atributo data-indice. Solamente el botón actual tendrá "seleccionada". En #ga-principal cambiáis src y alt; no creáis una segunda imagen principal.
// COMPROBACIÓN: En los manejadores, nombres locales como boton o imagen son seguros si están declarados DENTRO de la función.

// EQUIPO 3 — GALERÍA INTERACTIVA
// HTML preparado: #ga-miniaturas, #ga-principal, #ga-titulo,
// #ga-descripcion, #ga-anterior y #ga-siguiente.
// Clase CSS para las miniaturas: thumb. Selección actual: seleccionada.

// PASO 1. Trabajad con los SEIS primeros monumentos del array.
const gaPrimeroSeis = monumentos.slice(0, 6);
// PASO 2. Preparad una variable que indique qué imagen se está mostrando.
let imagenMostrada = 0; // índice de la imagen actual (0 a 5)
// PASO 3. Cread seis <button class="thumb">, cada uno con un <img>
//         dentro, e insertadlos en #ga-miniaturas. Las imágenes deben
//         llevar src y alt correspondientes al monumento.
//         Cada botón debe llevar el atributo "data-indice" con la posición
//         (0, 1, 2, 3, 4 o 5) de la miniatura que representa.
let button1 = document.createElement("button");
button1.classList.add("thumb");
button1.setAttribute("data-indice", "0");
let img1 = document.createElement("img");
img1.src = gaPrimeroSeis[0].imagen;
img1.alt = gaPrimeroSeis[0].nombre;
button1.appendChild(img1);

let button2 = document.createElement("button");
button2.classList.add("thumb");
button2.setAttribute("data-indice", "1");
let img2 = document.createElement("img");
img2.src = gaPrimeroSeis[1].imagen;
img2.alt = gaPrimeroSeis[1].nombre;
button2.appendChild(img2);

let button3 = document.createElement("button");
button3.classList.add("thumb");
button3.setAttribute("data-indice", "2");
let img3 = document.createElement("img");
img3.src = gaPrimeroSeis[2].imagen;
img3.alt = gaPrimeroSeis[2].nombre;
button3.appendChild(img3);

let button4 = document.createElement("button");
button4.classList.add("thumb");
button4.setAttribute("data-indice", "3");
let img4 = document.createElement("img");
img4.src = gaPrimeroSeis[3].imagen;
img4.alt = gaPrimeroSeis[3].nombre;
button4.appendChild(img4);

let button5 = document.createElement("button");
button5.classList.add("thumb");
button5.setAttribute("data-indice", "4");
let img5 = document.createElement("img");
img5.src = gaPrimeroSeis[4].imagen;
img5.alt = gaPrimeroSeis[4].nombre;
button5.appendChild(img5);

let button6 = document.createElement("button");
button6.classList.add("thumb");
button6.setAttribute("data-indice", "5");
let img6 = document.createElement("img");
img6.src = gaPrimeroSeis[5].imagen;
img6.alt = gaPrimeroSeis[5].nombre;
button6.appendChild(img6);

let gaMiniaturas = document.getElementById("ga-miniaturas");
// PASO 4. Programad los eventos de las seis miniaturas y los botones de navegación.
let botones = [button1, button2, button3, button4, button5, button6];
botones.forEach((boton) => {
    boton.addEventListener("click", pulsarMiniatura);
    gaMiniaturas.appendChild(boton);
});

let gaAnterior = document.getElementById("ga-anterior");
gaAnterior.addEventListener("click", galeriaAnterior);

let gaSiguiente = document.getElementById("ga-siguiente");
gaSiguiente.addEventListener("click", galeriaSiguiente);
function pulsarMiniatura(event) {
    // PASO 5. Averiguad qué miniatura se ha pulsado y cambiad el índice actual.
    //         Después, actualizad el contenido de la galería.
     
}

function galeriaAnterior() {
    // PASO 6. Retroceded una posición; desde la primera se pasa a la última.
    //         Actualizad después la fotografía y la selección visual.
}

function galeriaSiguiente() {
    // PASO 7. Avanzad una posición; desde la última se pasa a la primera.
    //         Actualizad después la fotografía y la selección visual.
}

function actualizarGaleria() {
    // PASO 8. Modificad los atributos "src" y "alt" de #ga-principal;
    //         actualizad el texto de #ga-titulo y
    //         el texto con localidad y provincia.
    // PASO 9. Quitad la clase "seleccionada" a todos los botones "thumb"
    //         y añadidla
    //         solamente a la miniatura correspondiente a la imagen actual.
}

// PASO 10. Mostrad inicialmente el primer monumento y marcad su miniatura.
// COMPROBAD: miniaturas, anterior, siguiente y vuelta circular en ambos extremos.
