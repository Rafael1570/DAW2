// U3E2 · Uso del objeto window
// Completa las funciones sin cambiar sus nombres.
// Todas las páginas utilizan este archivo. Cada página tiene su propio contexto.

// Referencia compartida por las funciones de ventanas de ESTA página.
let ventanaAuxiliar = null;

// 1. Dirección y navegador

function mostrarDireccion() {
    // Muestra href, hostname, pathname y search por consola.
    // Escribe aquí tus instrucciones.
    console.log(location.href);
    console.log(location.hostname);
    console.log(location.pathname);
    console.log(location.search);
}

function mostrarNavegador() {
    // Muestra language, languages, online y userAgent por consola
    // Escribe aquí tus instrucciones.
    console.log(navigator.language);
    console.log(navigator.languages);
    console.log(navigator.onLine);
    console.log(navigator.userAgent);
}

// 2. Dimensiones y desplazamiento

function mostrarDimensiones() {
    // Muestra innerWidth/Height, outerWidth/Height, screenX/Y, screen.width/height y availWidth/Height por consola.
    // Escribe aquí tus instrucciones.
    console.log(window.innerWidth);
    console.log(window.innerHeight);
    console.log(window.outerWidth);
    console.log(window.outerHeight);
    console.log(window.screenX);
    console.log(window.screenY);
    console.log(window.screen.width);
    console.log(window.screen.height);
    console.log(window.screen.availWidth);
    console.log(window.screen.availHeight);
}

function bajar() {
    // Usa scrollBy para bajar 300 píxeles.
    // Escribe aquí tus instrucciones.
    window.scrollBy(0, 300);
}

function subir() {
    // Usa scrollBy para subir 300 píxeles.
    // Escribe aquí tus instrucciones.
    window.scrollBy(0, -300);
}

function irAPosicion() {
    // Usa scrollTo para situarse en x=0,y=1000.
    // Escribe aquí tus instrucciones.
    window.scrollTo(0, 1000);
}

function volverAlInicio() {
    // Usa scrollTo con top=0,left=0,behavior=smooth.
    // Escribe aquí tus instrucciones.
    window.scrollTo({ top: 0, left: 0, behavior: 'smooth' });
}

// 3. Navegación e historial

function irAlLaboratorio() {
    // Carga laboratorio.html mediante location.assign().
    // Escribe aquí tus instrucciones.
    window.location.assign("laboratorio.html");
}

function irAlResultado() {
    // Carga resultado.html mediante location.assign().
    // Escribe aquí tus instrucciones.
    window.location.assign("resultado.html");
}

function sustituirPorResultado() {
    // Carga resultado.html mediante location.replace().
    // Escribe aquí tus instrucciones.
    window.location.replace("resultado.html");
}

function recargarPagina() {
    // Pide confirmación; si se acepta usa location.reload().
    // Escribe aquí tus instrucciones.
    if (confirm("¿Estás seguro de que quieres recargar la página?")) {
        window.location.reload();
    }
}

function retroceder() {
    // Retrocede una entrada con history.back().
    // Escribe aquí tus instrucciones.
    window.history.back();
}

function avanzar() {
    // Avanza una entrada con history.forward().
    // Escribe aquí tus instrucciones.
    window.history.forward();
}

function consultarHistorial() {
    // Muestra history.length en consola.
    // Escribe aquí tus instrucciones.
    console.log(window.history.length);
}

// 4. Ventanas

function abrirAuxiliar() {
    // Pide confirmación; abre resultado.html en auxiliar con popup,width=400,height=250,left=500,top=300; guarda referencia y comprueba null.
    // Escribe aquí tus instrucciones.
    if (confirm("¿Estás seguro de que quieres abrir la ventana auxiliar?")) {
        let auxiliar = window.open("resultado.html", "auxiliar", "width=400,height=250,left=500,top=300");
        if (auxiliar === null) {
            alert("No se pudo abrir la ventana auxiliar.");
        }
    }
}

function reutilizarAuxiliar() {
    // Carga inicio.html en el destino auxiliar y guarda referencia.
    // Escribe aquí tus instrucciones.
    if (ventanaAuxiliar && !ventanaAuxiliar.closed) {
        ventanaAuxiliar.location.href = "inicio.html";
    }
}

function abrirOtraPestana() {
    // Abre resultado.html con _blank.
    // Escribe aquí tus instrucciones.
    window.open("resultado.html", "_blank");
}

function abrirPaginaExterna() {
    // Abre https://www.mozilla.org/ con _blank.
    // Escribe aquí tus instrucciones.
    window.open("https://www.mozilla.org/", "_blank");
}

function cerrarAuxiliar() {
    // Comprueba null y closed antes de solicitar close; avisa si no hay ventana abierta.
    // Escribe aquí tus instrucciones.
    if (ventanaAuxiliar && !ventanaAuxiliar.closed) {
        ventanaAuxiliar.close();
    } else {
        alert("No hay ventana auxiliar abierta.");
    }
}

// 5. Escritura del documento

function abrirEscrituraCarga() {
    // Abre escritura_carga.html con un destino con nombre propio.
    // Escribe aquí tus instrucciones.
    window.open("escritura_carga.html", "escritura");
}

function abrirEscrituraPosterior() {
    // Abre escritura_posterior.html con otro destino con nombre propio.
    // Escribe aquí tus instrucciones.
    window.open("escritura_posterior.html", "escrituraPosterior");
}

function escribirDuranteCarga() {
    // Escribe un h2 y un párrafo con formato HTML usando document.write; la llamada automática ya está al final del archivo.
    // Escribe aquí tus instrucciones.
    document.write("<h2>Escritura durante la carga</h2>");
    document.write("<p>Este contenido se ha escrito durante la carga de la página.</p>");
}

function sobrescribirDocumento() {
    // Pide confirmación; si se acepta escribe un h1 y un párrafo con texto fijo usando document.write() sobre esta página.
    // Escribe aquí tus instrucciones.
    if (confirm("¿Estás seguro de que quieres sobrescribir el documento?")) {
        document.write("<h1>Documento sobrescrito</h1>");
        document.write("<p>Este documento ha sido sobrescrito mediante document.write().</p>");
    }
}

// Llamada automática: no la cambies ni la elimines.
// El script se carga al final del cuerpo, sin defer, mientras se analiza el HTML.
if (document.body.id === "pagina-escritura-carga") {
    escribirDuranteCarga();
}
