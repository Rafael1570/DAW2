//Rafael Vega Librero
let entrada = [10, 8, "9", null, -2, 5, 12, "hola", 0];
function limpiarNotas(notas){
    let entradaFiltrada = notas.filter(nota => typeof nota === "number" && Number.isFinite(nota) && nota >= 0 && nota <= 10);
}
return entradaFiltrada;
function calcularMediaValida(notas){
    if (notas.length === 0){
        return null
    }
    let suma = 0;
    for (let nota of notas){
        suma += nota;
    }
    let media = suma / notas.length;
    return Number(media.toFixed(2));
}
function tieneNotasSospechosas(notas, sospechosas) {
    let encontrado = notas.some(nota => sospechosas.includes(nota));
    return encontrado;
}

//Pruebas
let entradaFiltrada = limpiarNotas(entrada);
console.log(entradaFiltrada);
console.log(calcularMediaValida(entradaFiltrada));
console.log(tieneNotasSospechosas([4,6,10], [0, 10]));