// Rafael Vega Librero

const CARTON_FIJO = [ [3, null, 24, null, 42, null, 63, null, 83],

    [null, 12, null, 34, 49, 56, null, 75, null],

    [8, 17, null, 38, null, 58, null, null, 89] ];

// Variables que usamos

let bolasExtraidas = [];
let numerosMarcados = [];
let lineaCantada = false
let premioPendiendte = null;

// Funciones
function extraerBola() {
  // Si ya salieron las 90 bolas, paramos
  if (bolasExtraidas.length >= 90) {
    console.log("¡Ya han salido todos los números!");
    return null; 
  }
  let numero;
  // Generamos numeros hasta que encontremos uno que no esta en el array
  do {
    numero = Math.floor(Math.random() * 90) + 1;
  } while (bolasExtraidas.includes(numero));

  // Guardamos el número nuevo en el historial
  bolasExtraidas.push(numero); 
  
  return numero;
}

function marcarNumero(numero){

    if (CARTON_FIJO.includes(numero)){
        if (bolasExtraidas.includes(numero) && !numerosMarcados.includes(numero)){
            numerosMarcados.push(numero);
        }
    }
}

function comprobarLinea(){
    
}
