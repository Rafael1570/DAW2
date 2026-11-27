// ANDALUCÍA INTERACTIVA · MAÑANA · EQUIPO 5
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
// de este equipo (fa). Ejemplos: faContenedor, faLista, faIndice.
// Las variables declaradas DENTRO de una función pueden llamarse tarjeta,
// imagen, boton, lista... aunque otro equipo use esos nombres en sus funciones.
// Conservad los nombres de las funciones que ya aparecen escritas abajo.
// No introduzcáis variables globales innecesarias.
// CONSEJO PARA ESTE EQUIPO: Usad exclusivamente la clave "am3_favoritos"; el array contiene IDS (números), no nombres de monumentos ni objetos completos.
// ESTRUCTURA HTML Y CSS: Las tarjetas son <div class="saved-item"> con <img>, <div class="grow"> y <button class="small-remove" data-id="...">. Si no hay favoritos, un <p class="empty-state">.
// COMPROBACIÓN: IMPORTANTE JSON: localStorage.getItem("am3_favoritos") devuelve texto o null. Si es null, empezad con []; si hay texto JSON, JSON.parse() recupera el array. Tras añadir o quitar, JSON.stringify() convierte el array a texto para guardarlo con setItem().

// EQUIPO 5 — MIS FAVORITOS (localStorage)
// HTML: #fa-select, #fa-anadir, #fa-contador y #fa-lista.
// Clases CSS: saved-item, grow, small-remove y empty-state.
// Guardad SOLO identificadores en el array de favoritos.
// IMPORTANTE: utilizad la clave exacta "am3_favoritos".

// PASO 1. Preparad el desplegable con los OCHO primeros monumentos.
//         Cread un <option> por monumento en #fa-select.
//         Cada opción mostrará un nombre y tendrá un identificador como valor.
// PASO 2. Preparad el array de favoritos. Los datos se guardan como texto JSON.
//         Recuperad con getItem la clave exacta "am3_favoritos":
//         si la clave no existe, empezad con un array vacío; si existe,
//         recuperad el array a partir del texto con JSON.parse().
// PASO 3. Programad el evento del botón «Añadir favorito».

function anadirFavorito() {
    // PASO 4. Averiguad el identificador seleccionado (es un número).
    // PASO 5. Añadid el ID NUMÉRICO al array SOLO si aún no está incluido.
//         No guardéis el nombre ni el objeto completo del monumento.
    // PASO 6. Guardad el array actualizado y redibujad los favoritos.
}

function eliminarFavorito(event) {
    // PASO 7. Identificad el monumento del botón «Quitar» pulsado.
    // PASO 8. Eliminad ese identificador del array, guardad y actualizad la vista.
}

function guardarFavoritos() {
    // PASO 9. Convertid el array de identificadores a texto JSON con
//         JSON.stringify() y guardadlo en "am3_favoritos".
//         ¡Cambiar el array en JS no actualiza automáticamente localStorage!
}

function mostrarFavoritos() {
    // PASO 10. Vaciad #fa-lista y actualizad el número de #fa-contador.
    // PASO 11. Si no hay favoritos, cread un <p class="empty-state">.
    // PASO 12. Para cada identificador guardado, localizad el monumento y
    //          cread un <div class="saved-item"> con:
    //          <img> (src y alt), <div class="grow"> con <h5> y <p>,
    //          y <button class="small-remove"> con texto «Quitar».
    //          El botón debe llevar atributo "data-id" con el id del favorito.
    // PASO 13. Programad en cada botón «Quitar» un evento que permita
    //          identificar el monumento mediante su "data-id".
}

// PASO 14. Dibujad el estado inicial después de recuperar el almacenamiento.
// COMPROBAD: añadir dos, evitar duplicados, quitar uno y recargar la página.
