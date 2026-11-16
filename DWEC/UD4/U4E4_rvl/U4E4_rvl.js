
const CARTON_FIJO = [
    [3, null, 24, null, 42, null, 63, null, 83],
    [null, 12, null, 34, 49, 56, null, 75, null],
    [8, 17, null, 38, null, 58, null, null, 89]
];

let bolasExtraidas = [];
let numerosMarcados = [];
let lineaCantada = false;
let bingoCantado = false;
let premioPendiente = null;


// 1. Extraer una bola
function extraerBola() {
    if (bolasExtraidas.length === 90) {
        return null;
    }

    let numero;

    do {
        numero = Math.floor(Math.random() * 90) + 1;
    } while (bolasExtraidas.includes(numero));

    bolasExtraidas.push(numero);

    return numero;
}


// 2. Marcar un número
function marcarNumero(numero) {
    for (let fila of CARTON_FIJO) {
        if (fila.includes(numero) &&
            bolasExtraidas.includes(numero) &&
            !numerosMarcados.includes(numero)) {

            numerosMarcados.push(numero);
        }
    }
}


// 3. Comprobar línea
function comprobarLinea() {
    for (let fila of CARTON_FIJO) {
        let completa = true;

        for (let numero of fila) {
            if (numero !== null && !bolasExtraidas.includes(numero)) {
                completa = false;
            }
        }

        if (completa) {
            return true;
        }
    }

    return false;
}


// 4. Comprobar bingo
function comprobarBingo() {
    for (let fila of CARTON_FIJO) {
        for (let numero of fila) {
            if (numero !== null && !bolasExtraidas.includes(numero)) {
                return false;
            }
        }
    }

    return true;
}


// 5. Actualizar premios
function actualizarPremios() {
    if (premioPendiente !== null) {
        return;
    }

    if (comprobarBingo() && !bingoCantado) {
        premioPendiente = "bingo";
    } else if (comprobarLinea() && !lineaCantada) {
        premioPendiente = "linea";
    }
}


// 6. Cantar línea
function cantarLinea() {
    if (premioPendiente === "linea") {
        lineaCantada = true;
        premioPendiente = null;
        return true;
    }

    return false;
}


// 7. Cantar bingo
function cantarBingo() {
    if (premioPendiente === "bingo") {
        bingoCantado = true;
        premioPendiente = null;
        return true;
    }

    return false;
}


// 8. Reiniciar partida
function reiniciarPartida() {
    bolasExtraidas = [];
    numerosMarcados = [];
    lineaCantada = false;
    bingoCantado = false;
    premioPendiente = null;
}


// 9. Jugar al bingo
function jugarBingo() {
    reiniciarPartida();

    console.log("COMIENZA EL BINGO");
    console.log("Cartón:");
    console.table(CARTON_FIJO);

    while (!bingoCantado) {
        let bola = extraerBola();

        if (bola === null) {
            break;
        }

        console.log("Ha salido la bola: " + bola);

        marcarNumero(bola);
        actualizarPremios();

        if (premioPendiente === "linea") {
            console.log("¡LÍNEA CONSEGUIDA!");
            cantarLinea();
        }

        if (premioPendiente === "bingo") {
            console.log("¡BINGO! ¡HAS GANADO!");
            cantarBingo();
            break;
        }
    }

    console.log("Partida terminada");
    console.log("Total de bolas extraídas: " + bolasExtraidas.length);
}


// Iniciar el juego
jugarBingo();
