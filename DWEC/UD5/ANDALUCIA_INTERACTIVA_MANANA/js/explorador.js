// ANDALUCÍA INTERACTIVA · MAÑANA · EQUIPO 1
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
// de este equipo (ex). Ejemplos: exContenedor, exLista, exIndice.
// Las variables declaradas DENTRO de una función pueden llamarse tarjeta,
// imagen, boton, lista... aunque otro equipo use esos nombres en sus funciones.
// Conservad los nombres de las funciones que ya aparecen escritas abajo.
// No introduzcáis variables globales innecesarias.
// CONSEJO PARA ESTE EQUIPO: No declaréis fuera de las funciones una variable global llamada contenedor, lista, imagen o boton sin prefijo.
// ESTRUCTURA HTML Y CSS: En #ex-resultados las tarjetas deben ser <article class="card">. Cada tarjeta contendrá un <img> y un <div class="card-text">; los avisos vacíos usarán <p class="empty-state">. La clase "compactas" se aplica al CONTENEDOR, no a cada tarjeta.
// COMPROBACIÓN: Podéis usar dentro de mostrarExplorador() variables locales sencillas como tarjeta, imagen o titulo; no interfieren con otros archivos.

// EQUIPO 1 — EXPLORADOR DE MONUMENTOS
// Trabajad exclusivamente en este archivo.
// Datos disponibles: array monumentos (de js/datos.js).
// HTML preparado: #ex-provincia, #ex-tamano, #ex-contador y #ex-resultados.
// Estilos disponibles: card, card-text, compactas y empty-state.

// PASO 1. Preparad los SEIS primeros monumentos que aparecerán en el explorador.
// PASO 2. Localizad el desplegable, el botón, el contador y el contenedor de resultados.
// PASO 3. Programad los eventos: cambio de provincia y pulsación del botón de tamaño.

function mostrarExplorador() {
    // PASO 4. Eliminad del contenedor los resultados de una búsqueda anterior.
    // PASO 5. Consultad la provincia elegida. «Todas» debe mostrar los seis.
    // PASO 6. Recorred los monumentos elegidos y cread UNA tarjeta por coincidencia:
    //         ESTRUCTURA: <article class="card"> con <img> y <div class="card-text">.
    //         En ese div cread <h5> para el nombre y <p> para la provincia.
    //         La imagen debe llevar src con la ruta del monumento y alt con su nombre.
    //         Insertad los elementos en sus padres y la tarjeta en #ex-resultados.
    // PASO 7. Actualizad #ex-contador con el número real de tarjetas mostradas.
    // PASO 8. Si no hay coincidencias, cread un aviso de clase empty-state.
}

function cambiarTamano() {
    // PASO 9. Al pulsar #ex-tamano, añadid o quitad la clase "compactas"
    //         del contenedor #ex-resultados (no de las tarjetas individuales).
    // PASO 10. Cambiad el texto del botón entre «Vista compacta» y «Vista normal».
}

// PASO 11. Ejecutad la función que dibuja el catálogo al abrir la página.
// COMPROBAD: seis tarjetas inicialmente; filtro «Todas»; provincia sin resultados;
//            botón de tamaño que puede pulsarse repetidas veces.
