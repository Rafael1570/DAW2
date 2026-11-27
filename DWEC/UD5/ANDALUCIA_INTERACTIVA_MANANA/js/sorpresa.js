// ANDALUCÍA INTERACTIVA · MAÑANA · EQUIPO 8
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
// de este equipo (so). Ejemplos: soContenedor, soLista, soIndice.
// Las variables declaradas DENTRO de una función pueden llamarse tarjeta,
// imagen, boton, lista... aunque otro equipo use esos nombres en sus funciones.
// Conservad los nombres de las funciones que ya aparecen escritas abajo.
// No introduzcáis variables globales innecesarias.
// CONSEJO PARA ESTE EQUIPO: Usad el array común monumentos. La selección aleatoria y la construcción de la tarjeta se hacen dentro de generarSorpresa().
// ESTRUCTURA HTML Y CSS: La tarjeta será <article class="card"> con <img> y <div class="card-text">. La explicación comienza con "oculto" y el botón tendrá "btn".
// COMPROBACIÓN: No acumuléis tarjetas. En cada pulsación de Sorpréndeme sustituid la anterior y comprobad el botón mostrar/ocultar.

// EQUIPO 8 — MONUMENTO SORPRESA (equipo 8)
// HTML: #so-generar y #so-resultado.
// Estilos CSS: card, card-text, btn y oculto.
// Utilizad el array monumentos completo, no solo sus primeros elementos.

// PASO 1. Localizad el botón «Sorpréndeme» y programad su evento.

function generarSorpresa() {
    // PASO 2. Elegid al azar UNO de los monumentos del array.
    // PASO 3. Vaciad #so-resultado para sustituir la tarjeta anterior.
    // PASO 4. Cread un <article class="card"> con un <img> (src y alt)
    //         y un <div class="card-text">.
    // PASO 5. En card-text añadid <h5> para nombre, <p> para provincia,
    //         otro <p> para descripción y un <button class="btn">.
    // PASO 6. El <p> de la descripción tendrá inicialmente la clase "oculto".
    //         El botón dirá «Mostrar descripción» y tendrá su evento.
    // PASO 7. Insertad todos los elementos en su padre correspondiente.
}

function cambiarDescripcion(event) {
    // PASO 8. Localizad la descripción de LA tarjeta cuyo botón se pulsó.
    // PASO 9. Alternad su visibilidad mediante la clase oculto.
    // PASO 10. Cambiad el texto del botón entre «Mostrar descripción»
    //          y «Ocultar descripción» según el estado actual.
}

// COMPROBAD: primera tarjeta, descripción oculta, botón mostrar/ocultar
//            y sustitución de la tarjeta al volver a pulsar «Sorpréndeme».
