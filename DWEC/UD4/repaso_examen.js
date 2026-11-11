let array1=[1,2,3,null, "perro", 10, 12];
// let array1_filtrado=array1.filter(n=>n!==null);
// console.log(array1_filtrado);
// let array2_filtrado=array1_filtrado.filter(n=>!isNaN(n));
// console.log(array2_filtrado);
let array1_filtrado=array1.filter(n=>!isNaN(n)&&n!==null&&n>=5);
console.log(array1_filtrado);

let array2=["perro", "casa", "coche"];
let frases=["Me gusta mi gato", "Me gusta mi coche", "Me gusta mi perro" , "Me gusta mi perro y mi casa y mi coche"];
let resultado2 = frases.every(frase=>array2.every(palabra=>frase.includes(palabra)));
let resultado = array2.some(palabra=>frases.some(frase=>frase.includes(palabra)));
// for (const frase of frases){
//     let resultado = array2.some(palabra=>frase.includes(palabra));o=array2.som3
// }

console.log(resultado2);
