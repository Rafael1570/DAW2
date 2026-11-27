// ANDALUCÍA INTERACTIVA · MAÑANA · EQUIPO 7
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
// de este equipo (ad). Ejemplos: adContenedor, adLista, adIndice.
// Las variables declaradas DENTRO de una función pueden llamarse tarjeta,
// imagen, boton, lista... aunque otro equipo use esos nombres en sus funciones.
// Conservad los nombres de las funciones que ya aparecen escritas abajo.
// No introduzcáis variables globales innecesarias.
// CONSEJO PARA ESTE EQUIPO: Ya tenéis el array global const preguntas; no creéis otra constante global con el mismo nombre. El estado de la pregunta actual y los aciertos puede mantenerse en variables globales con prefijo ad.
// ESTRUCTURA HTML Y CSS: Construid <div class="quiz-card"> con imagen y <div class="quiz-choices">. Los tres botones llevan "choice". Tras responder añadid "correcta" o "incorrecta" SOLO al pulsado.
// COMPROBACIÓN: Controlad que un mismo intento no incremente varias veces los aciertos. Los manejadores siguen siendo funciones independientes.

// EQUIPO 7 — ADIVINA EL MONUMENTO
// HTML: #ad-pregunta, #ad-marcador y #ad-siguiente.
// Estilos CSS: quiz-card, quiz-choices, choice, correcta e incorrecta.
// Se proporcionan las tres preguntas para que os centréis en el DOM y los eventos.

const preguntas = [
    {imagen: "img/monumento-01.webp", opciones: ["La Alhambra", "La Giralda", "Catedral de Cádiz"], correcta: "La Alhambra"},
    {imagen: "img/monumento-07.webp", opciones: ["Puente Romano", "Mezquita-Catedral", "Alcazaba de Málaga"], correcta: "Mezquita-Catedral"},
    {imagen: "img/monumento-06.webp", opciones: ["Plaza Vázquez de Molina", "Generalife", "Plaza de España"], correcta: "Plaza de España"}
];

// PASO 1. Preparad las variables para la pregunta actual, los aciertos y
//         si ya se ha respondido o no a la pregunta mostrada.
// PASO 2. Programad el evento del botón «Siguiente».

function mostrarPregunta() {
    // PASO 3. Vaciad #ad-pregunta y preparad la pregunta correspondiente.
    // PASO 4. Cread <div class="quiz-card"> que contenga un <img>
    //         con src y alt y un <div class="quiz-choices">.
    // PASO 5. Dentro de quiz-choices cread un <h5> y TRES
    //         <button class="choice">,
    //         uno por respuesta. Asignad a cada botón su evento de respuesta.
    // PASO 6. Mostrad la tarjeta y actualizad el marcador con la pregunta
    //         actual (de 3) y el número de aciertos acumulados.
}

function responder(event) {
    // PASO 7. Evitad que una misma pregunta pueda responderse varias veces.
    // PASO 8. Al botón pulsado añadidle la clase "correcta"
    //         o "incorrecta"; sumad un acierto solamente cuando corresponda.
    // PASO 9. Actualizad el marcador tras la respuesta.
}

function siguientePregunta() {
    // PASO 10. No avancéis si la pregunta actual aún no está respondida.
    // PASO 11. Mostrad la siguiente pregunta o, después de la tercera,
    //          un mensaje final indicando los aciertos.
    // PASO 12. Al terminar, cambiad el botón a «Volver a jugar».
    // PASO 13. Al reiniciar, empezad por la primera pregunta con cero aciertos.
}

// PASO 14. Mostrad la primera pregunta al abrir la página.
// COMPROBAD: no avanzar sin responder, no sumar varias veces,
//            tres preguntas, resultado final y reinicio del juego.
