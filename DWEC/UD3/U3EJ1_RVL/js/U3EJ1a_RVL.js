const actualidad = new Date(2026, 9, 5, 8, 15, 0, 0);
const final = new Date(2027, 1, 8, 8, 15, 0, 0);

let tiempoRestante = final - actualidad;

console.log(tiempoRestante);

let dias = parseInt(tiempoRestante / (1000 * 60 * 60 * 24));

console.log(dias);