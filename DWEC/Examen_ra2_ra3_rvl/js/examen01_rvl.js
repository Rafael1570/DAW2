//Rafael Vega LIbrero
let nombreTecnico = localStorage.getItem("tecnico");
// comprobacion del nombre
if (nombreTecnico === null){
    let nombreTecnico = prompt("Introduce un alias porfavor: ");
    let verificacion = true;
    while (verificacion){
        if (nombreTecnico.length <8 || nombreTecnico >8){
            alert("El nombre debe de contener 8 caracteres.");
            nombreTecnico = prompt("Introduce un nombre valido");
        }else if (nombreTecnico === null){
            alert("El usuario a pulsado cancelar");
            nombreTecnico = prompt("Debes introducir un alias");
        }else {
            nombreTecnico = nombreTecnico.toUpperCase();
            nombreTecnico = localStorage.setItem("tecnico", nombreTecnico);
            alert("Bienvenido de nuevo " + nombreTecnico);
            verificacion = false;
        }
    }
}else {
    nombreTecnico = localStorage.setItem("tecnico", nombreTecnico);
    alert("Bienvenido de nuevo " + nombreTecnico);
}
// numero introducido
let numeroValido = true;
let numero = prompt("Introduce un numero");
let riesgo;
while(numeroValido){
    if(numero > 100 || numero < 0 && numero === isNan(numero)){
        alert("El numero no es valido");
        numero = prompt("Introduce un numero valido: ");
    }else if(numero === 0 || numero <= 4){
        alert("Intentos fallidos: "+ numero
            +"\nNivel de riesgo : BAJO");
        riesgo = "bajo";
        numeroValido = false;
    }else if(numero === 5 || numero <= 14) {
        alert("Intentos fallidos: "+ numero
            +"\nNivel de riesgo : MEDIO");
        riesgo ="medio";
        numeroValido = false;
    }else if(numero === 15 || numero <=29) {
        alert("Intentos fallidos: "+ numero
            +"\nNivel de riesgo : ALTO");
        riesgo = "alto";
        numeroValido = false;
    }else if(numero === 30 || numero <=100) {
        alert("Intentos fallidos: "+ numero
            +"\nNivel de riesgo : CRITICO");
        riesgo = "critico";
        numeroValido = false;
    }
}


//Fecha Revision

let diaRevision = prompt("Introduce el dia de la proxima revison");
let mesRevision = prompt("Introduce el mes del dia de la revision");
let fecha = new Date(2027, diaRevision ,mesRevision);
let dia = fecha.getDate();
//Aqui crearia un switch para poner dependiendo el numero que me de sea un dia.
console.log("PROXIMA REVISION \nFecha: "+fecha +"\nDia de la semana: "+ dia);


// Registro alertas

let alertas = sessionStorage.getItem("alertas");

if (alertas === null) {
    alertas = 1;
} else {
    alertas = parseInt(alertas) + 1;
}
sessionStorage.setItem("alertas", alertas);

// Registro Mayor ataque

let maxIntentos = localStorage.getItem("maxIntentos");

if (maxIntentos === null) {
    maxIntentos = 1;
}else if (maxIntentos > localStorage.getItem("maxIntentos")){
    localStorage.setItem("maxIntentos", maxIntentos);
    alert("¡NUEVO MÁXIMO DE INTENTOS DETECTADO!");
}else if (maxIntentos <= localStorage.getItem("maxIntentos")){
    localStorage.getItem("maxIntentos", maxIntentos);
}
//Mostrar informe
let referencia = String("ALERTA-" + numero);
console.log("===============================");
console.log("      CYBER DEFENSE 2027       ");
console.log("===============================");
console.log("Tecnico" + nombreTecnico);
console.log("Referencia: " + referencia);
console.log("Intentos fallidos: " + numero);
console.log("Nivel de riesgo: " + riesgo);
console.log("Alertas analizadas: " + alertas);
console.log("Máximo historico de intentos:" + maxIntentos);
console.log("Proxima revisión: "+ fecha);
console.log("Dia de la semana: " + dia);