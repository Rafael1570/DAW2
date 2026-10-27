
//Rafael Vega Librero
let puntuaciones = [120, 85, 200, 150, 60, 175, 90, 230];

// Añadir puntuaciones al final del array
function agregarPuntuacion(puntos) {
    puntuaciones.push(puntos);
}

//Usamos find() para buscar la primera puntuación
function buscarPuntuacion(puntos) {
    let busqueda = puntuaciones.find(numero => numero === puntos);
    return busqueda;
}

//Eliminar la primera posición del array original
function eliminarPuntuacion(puntos) {
    let posicion = puntuaciones.indexOf(puntos);

    if (posicion !== -1) {
        puntuaciones.splice(posicion, 1);
    }
}

//Obtener puntuaciones iguales o superiorres a 150
function filtrarPuntuacionesAltas() {
    let maxPuntuacion = puntuaciones.filter(puntos => puntos >= 150);
    return maxPuntuacion;
}

//Ordenar puntuacions con sort()
function ordenarPuntuaciones() {
    let puntuacionesOrdenadas = puntuaciones.sort((a, b) => b - a);
    return puntuacionesOrdenadas;
}

//Aplicar una bonificacion de un 10%
function aplicarBonificacion() {
    let puntuacionesBonificacion = puntuaciones.map(puntos => Math.round(puntos * 1.10));
    return puntuacionesBonificacion;
}


// Calcular el total con reduce
function calcularTotal() {
    let total = puntuaciones.reduce((suma, puntos) => {
        return suma + puntos;
    }, 0);
    return total;
}

//Comprobar si hay excelente
function hayPuntuacionExcelente() {
    let hayexcelente = puntuaciones.some(puntos => puntos >= 200);
    return hayexcelente;
}

// Comprobar el nivel minimo
function superanMinimo() {
    let todossuperan = puntuaciones.every(puntos => puntos >= 50);
    return todossuperan;
}

// Generar un infome
function generarInforme() {
    let informe = puntuaciones.join(" - ");
    return informe;
}

// INFORME
console.log("Array original:", puntuaciones);

agregarPuntuacion(180);
console.log("Después de añadir 180:", puntuaciones);

console.log("Buscar puntuación 150:", buscarPuntuacion(150));

eliminarPuntuacion(120);
console.log("Después de eliminar 120:", puntuaciones);

console.log("Puntuaciones altas:", filtrarPuntuacionesAltas());

console.log("Puntuaciones ordenadas:", ordenarPuntuaciones());

console.log("Puntuaciones con bonificación:", aplicarBonificacion());

console.log("Puntuación total:", calcularTotal());

console.log("¿Hay puntuaciones excelentes?", hayPuntuacionExcelente());

console.log("¿Todas superan el mínimo?", superanMinimo());

console.log("Informe final:", generarInforme());
