// let alumno = "Martín";
// function saludar(){
//     console.log("hola " + alumno);
// }
// saludar(alumno);

// function sumar(num1, num2){
//     return num1 + num2;
// }
// let resultado=sumar(4,3);
// console.log(sumar(4,3));

// const triple=function(num1) {
//     return num1 * 3;
// }
// console.log(triple(5));

// let triple = (num1) => num1 * 3;
// let resultado = triple(5);
// console.log(resultado);

// let suma = (a, b) => a + b;
// let resultadoSuma = suma(4, 3);
// console.log(resultadoSuma);

// let resta = (a, b) => a - b;
// let resultadoResta = resta(10, 5);
// console.log(resultadoResta);
let suma= 0
function sumarTodos(...numeros) {
    for (const numero of numeros) {
        suma += numero;
    }
    return suma;
}
console.log(sumarTodos(1, 2));
