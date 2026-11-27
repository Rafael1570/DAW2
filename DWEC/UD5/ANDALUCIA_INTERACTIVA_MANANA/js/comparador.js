// ANDALUCÍA INTERACTIVA · MAÑANA · EQUIPO 2
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
// de este equipo (co). Ejemplos: coContenedor, coLista, coIndice.
// Las variables declaradas DENTRO de una función pueden llamarse tarjeta,
// imagen, boton, lista... aunque otro equipo use esos nombres en sus funciones.
// Conservad los nombres de las funciones que ya aparecen escritas abajo.
// No introduzcáis variables globales innecesarias.
// CONSEJO PARA ESTE EQUIPO: Los dos desplegables comparten los datos pero NO las variables globales que usan otros equipos.
// ESTRUCTURA HTML Y CSS: Cada <option> debe mostrar nombre y tener el id como valor. Las fichas serán <article class="card">, con <div class="card-text">. La tarjeta más larga tendrá "destacada" y un <span class="badge">.
// COMPROBACIÓN: Comprobad que al elegir el mismo monumento dos veces solo aparece <p class="empty-state">.

// EQUIPO 2 — COMPARADOR DE MONUMENTOS
// HTML preparado: #co-uno, #co-dos, #co-comparar y #co-resultado.
// Clases CSS ya existentes: card, card-text, destacada, badge y empty-state.
// No modifiquéis el array monumentos ni el HTML.

// PASO 1. Seleccionad los DOS desplegables y utilizad los OCHO primeros monumentos.
// PASO 2. Cread para cada monumento un <option> en CADA desplegable.
//         Debe verse el nombre y guardarse el identificador como valor.
// PASO 3. Preparad selecciones iniciales DIFERENTES.
// PASO 4. Registrad el evento de pulsación del botón Comparar.

function compararMonumentos() {
    // PASO 5. Eliminad la comparación anterior de #co-resultado.
    // PASO 6. Averiguad qué dos monumentos corresponden a los valores elegidos.
    // PASO 7. Si coinciden, cread un <p class="empty-state"> y ninguna tarjeta.
    // PASO 8. Si son diferentes, cread dos <article class="card">, cada uno
    //         con <img> y un <div class="card-text"> con <h5> y <p>:
    //         imagen, nombre, provincia
    //         y duración estimada en minutos.
    // PASO 9. La tarjeta de visita más larga llevará la clase "destacada".
    //         Cread dentro un <span class="badge"> con el texto
    //         «Visita más larga». Si duran lo mismo, ninguna será destacada.
}

// COMPROBAD: dos opciones diferentes muestran dos tarjetas; la mayor se destaca;
//            escoger el mismo monumento muestra un aviso, sin tarjetas antiguas.
