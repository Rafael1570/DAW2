//Rafael Vega Librero
class Pelicula{
    #codigo
    #genero
    #titulo
    #anio
    #edadMinima
    constructor(codigo, genero, titulo, anio, edadMinima){
        this.#codigo = codigo;
        this.#genero = ["acción", "comedia", "terror"];
        this.#titulo = titulo;
        this.#anio = anio;
        this.#edadMinima = edadMinima; 
    }
    get codigo(){
        return this.#codigo;
    }
    get genero(){
        return this.#genero;
    }
    get titulo(){
        return this.#titulo;
    }
    get anio(){
        return this.#anio
    }
    get edadMinima(){
        return this.#edadMinima
    }
    set edadMinima(edad){
        if(edad <=18 && edad >= 0){
            this.#edadMinima = edadMinima;
        }else{
            throw new Error("valor no valido");
        }
    }

    mostrarInfo(titulo, codigo, genero, anio, edadMinima){
        return "La pelicula es " + this.#titulo + " con codigo " +this.#codigo+ " de genero " +this.#genero+ 
        "del año " + this.#anio + "y un minimo de " + this.#edadMinima;
    }
    esAntigua(){
        let fechaActual = new Date();
        let anioActual = fechaActual.getFullYear();

        let anioPeli = this.#anio;

        let esAntigua = anioPeli - anioActual;
        let Clasico = true
        if(esAntigua <= 20){
            Clasico = false;
            return Clasico;
        }else {
            return Clasico;
        }
    }
    esParaAdultos(){
        let mayores = true;
        if(this.#edadMinima > 16){
            return mayores;
        }else {
            mayores = false;
            return mayores;
        }
    }
}

//Clase Nueva

class Espectador{
    nombre
    edad
    peliculaReservada
    constructor(nombre, edad, peliculaReservada){
        this.nombre = nombre;
        this.edad = edad;
        this.peliculaReservada = null;
    }
    reservarPelicula(pelicula){
        if(pelicula instanceof Pelicula){
            this.peliculaReservada = "Reservada";
        }else{
            throw new Error("Esta pelicula no existe");
        }
    }
}
// Clase Cartelera

class Cartelera{
    peliculas
    constructor(){
        this.peliculas = [];
    }
    agregarPelicula(pelicula){    

    }
    mostrarCartelera(){
        Pelicula.mostrarInfo();
    }
    eliminarPelicula(codigo){

    }
}
function buscarPelicula(codigo){
    let peliEncontrada = peliculas.find(peli => Pelicula.codigo == codigo);

    if (peliEncontrada != undefined) {
        console.log(peliEncontrada.peli.mostrarInfo());
    } else {
        return null;
    }
}
//Pruebas

let Pelicula1 = new Pelicula("PL101", "Ciencia ficción", "Matrix", 1999, 13)
let Pelicula2 = new Pelicula("PL102", "Terror", "La noche oscura", 2022, 18)
let Pelicula3 = new Pelicula("PL103", "Comedia", "Un verano loco", 2019, 7)

let Espectador1 = new Espectador("Ana Garcia", 20)
let Espectador2 = new Espectador("Luis Pérez", 15)
let peliculas = [];
peliculas.push(Pelicula1);
peliculas.push(Pelicula2);
peliculas.push(Pelicula3);

Espectador1.reservarPelicula(Pelicula1);
Espectador2.reservarPelicula(Pelicula2);

buscarPelicula("PL101");
