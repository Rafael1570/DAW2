// Rafael Vega Librero
let StringTexto = "Si quiere vivir, venga conmigo";
let resultado = "";
for (let i = 0; i < StringTexto.length; i++) {
    let char = StringTexto[i];
    if (char !== " ") {
        if (Math.random() < 0.5) {
            char = char.toUpperCase();
        } else {
            char = char.toLowerCase();
        }
    }
    resultado += char;
}
resultado = resultado.replaceAll(" ", "-");
console.log(resultado);
