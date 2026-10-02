let dato;

alert(dato);

dato = prompt("Introduce un valor:");

console.log("Valor introducido: " + dato);
console.log("Tipo de dato: " + typeof dato);

let numero = Number(dato);
alert(numero);

let decimal = parseFloat(dato);
alert(decimal);

dato = null;
console.log(dato);