// Rafael Vega lIbrero

let numerodados;
numerodados = prompt("Cúantos dados desea tirar:");
while (numerodados > 4 || numerodados <1 ){
    numerodados = prompt("Cúantos dados desea tirar:")
}
let resultado = "";
for (let i = 1; i<= numerodados; i++){
    let dado = Math.floor(Math.random() * 6 + 1);
    console.log(dado);
    resultado += "Dado " + i + ": " + dado + "  ";
}

alert(resultado);

