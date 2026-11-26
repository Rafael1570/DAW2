class Videojuego {
    #codigo
    #titulo
    #genero
    #anio
    #edadMinima
    constructor(codigo, titulo, genero, anio, edadMinima) {
        this.#codigo = codigo;
        this.#titulo = titulo;
        this.#genero = genero;
        this.#anio = anio;
        this.#edadMinima = edadMinima;
    }
    get codigo(){
        return this.#codigo;
    }
    get titulo(){
        return this.#titulo;
    }
    get genero(){
        return this.#genero;
    }
    get anio(){
        return this.#anio;
    }
    get edadMinima(){
        return this.#edadMinima;
    }
    set edadMinima(edadMinima){
        if(edadMinima>=0 && edadMinima <18){
            this.edadMinima = edadMinima;
        }else{
            throw new Error("Este valor no es valido");
        }
    }
    mostrarInfo(){
        console.log(this.codigo, this.titulo, this.genero, this.anio, this.edadMinima);
    }
    esClasico(){
        let fechaActual = new Date().getFullYear();
        let anioJuego = this.anio;
        let clasico = fechaActual - anioJuego;
        if(clasico>20){
            return true;
        }else {
            return false;
        }
    }
    esParaAdultos(){
        if(this.edadMinima > 16){
            return true;
        }
    }
}
class Catalogo{
    constructor(nombre){
        this.nombre=nombre;
        this.videojuegos= [];
    }
    agregarVideojuego(videojuego){
        this.videojuegos.push(videojuego);
    }
    mostrarCatalogo(){
        for (const videojuego of this.videojuegos) {
            videojuego.mostrarInfo();
        }
    }
    videojuegosPorEdad(minEdad){
        const videojuegosFiltrados = [];
        for (const videojuego of this.videojuegos) {
            if(videojuego.edadMinima >= minEdad){
                videojuegosFiltrados.push(videojuego);
            }
        }
        return videojuegosFiltrados
    }
    buscarVideojuego(codigo) {
        for (const videojuego of this.videojuegos) {
            if (codigo === videojuego.codigo) {
                return videojuego;
            }
        }
        return null;
    }
}

const juego1 = new Videojuego("VJ101", "Carrera Extrema", "Carreras", 2003, 7);
const juego2 = new Videojuego("VJ102", "Ciudad Oscura", "Acción", 2021, 18);
const juego3 = new Videojuego("VJ103", "Reino Perdido", "Aventura", 2018, 16);

const catalogo = new Catalogo("Game Zone");
catalogo.agregarVideojuego(juego1);
catalogo.agregarVideojuego(juego2);
catalogo.agregarVideojuego(juego3);

console.log(catalogo.nombre);
catalogo.mostrarCatalogo();

for (const juego of catalogo.videojuegosPorEdad(16)) {
    juego.mostrarInfo();
}

catalogo.buscarVideojuego("VJ102").mostrarInfo();
console.log(catalogo.buscarVideojuego("VJ999")); // null

console.log(juego1.esClasico());      // true
console.log(juego2.esParaAdultos());  // true

try {
    juego1.edadMinima = 19;
} catch (error) {
    console.log(error.message);
}