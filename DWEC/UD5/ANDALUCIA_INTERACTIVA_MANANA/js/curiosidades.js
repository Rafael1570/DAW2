// ANDALUCÍA INTERACTIVA · MAÑANA · EQUIPO 4
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
// de este equipo (cu). Ejemplos: cuContenedor, cuLista, cuIndice.
// Las variables declaradas DENTRO de una función pueden llamarse tarjeta,
// imagen, boton, lista... aunque otro equipo use esos nombres en sus funciones.
// Conservad los nombres de las funciones que ya aparecen escritas abajo.
// No introduzcáis variables globales innecesarias.
// CONSEJO PARA ESTE EQUIPO: Ya tenéis el array global const curiosidades proporcionado en ESTE archivo; conservad su nombre y no declaréis otra constante global con ese mismo nombre.
// ESTRUCTURA HTML Y CSS: Cada tarjeta <article class="curiosity"> lleva <span class="step">, título, explicación <p class="oculto"> y botón. La clase "abierta" se aplica a la tarjeta pulsada.
// COMPROBACIÓN: Al pulsar una tarjeta, no deben cambiar las otras. Buscad el párrafo correspondiente al botón pulsado.

// EQUIPO 4 — CURIOSIDADES DE ANDALUCÍA
// HTML: #cu-tarjetas. Estilos disponibles: curiosity, step, oculto y abierta.
// Los cuatro textos ya están facilitados: debéis crear las tarjetas con el DOM.

const curiosidades = [
    {titulo: "La Alhambra", texto: "Sus patios y jardines son parte esencial del conjunto palatino nazarí."},
    {titulo: "La Giralda", texto: "Su torre fue originalmente un alminar y después se convirtió en campanario."},
    {titulo: "Mezquita-Catedral", texto: "Sus célebres arcos alternan dovelas claras y rojizas."},
    {titulo: "Plaza de España", texto: "Se construyó para la Exposición Iberoamericana de 1929."}
];

// PASO 1. Localizad #cu-tarjetas y recorred las cuatro curiosidades.
// PASO 2. Cread por cada una un <article class="curiosity"> con:
//         <span class="step">, <h5>, <p> y <button> «Descubrir».
//         El <span> mostrará el número de la curiosidad.
// PASO 3. El <p> de la explicación debe comenzar con la clase "oculto".
// PASO 4. Programad el evento del botón que habéis creado en cada tarjeta.
// PASO 5. Insertad los elementos de cada tarjeta en #cu-tarjetas.

function cambiarCuriosidad(event) {
    // PASO 6. Localizad la tarjeta del botón pulsado y SU párrafo.
    // PASO 7. Mostrad u ocultad solo ese párrafo cambiando "oculto".
    //         Añadid o quitad la clase "abierta"
    //         únicamente en su tarjeta.
    // PASO 8. Alternad el texto del botón entre «Descubrir» y «Ocultar».
}

// COMPROBAD: cuatro tarjetas; todas cerradas al principio; cada una se
//            abre y cierra sin afectar a las otras tres.
