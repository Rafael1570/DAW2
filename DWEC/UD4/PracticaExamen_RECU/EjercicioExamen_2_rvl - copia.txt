class Pelicula{
    #codigo
    #genero
    #titulo
    #anio
    #edadMinima
    constructor(codigo,genero,titulo,anio,edadMinima){
        this.#codigo = codigo;
        this.#genero = ["acción", "comedia", "terror"];
        this.#titulo= titulo;
        this.#anio= anio;
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
        return this.#anio;
    }
    get edadMinima(){
        return this.#edadMinima;
    }
    set edadMinima(edadMinima){
        if(edadMinima <= 18 && edadMinima >= 0){
            this.#edadMinima = edadMinima;
        }else{
            throw new Error("valor no valido");
        }
    }

    mostrarInfo(){
        console.log(this.#codigo, this.#genero, this.#titulo, this.#anio, this.#edadMinima);
    }
    esAntigua(){
        let fechaActual = new Date();
        let anioActual = fechaActual.getFullYear;
        let anioPeli = this.#anio;
        let esAntigua = anioActual - anioPeli;
        if(esAntigua>20){
            return true;
        }else{
            return false;
        }
    }
    esParaAdultos(){
        if(this.edadMinima >= 16){
            return true;
        }else {
            return false;
        }
    }
}
class Espectador{
    constructor(nombre, edad){
        this.nombre = nombre;
        this.edad = edad;
        this.peliculasReservada = null;
    }
    reservarPelicula(pelicula){
        if(pelicula instanceof Pelicula){
            this.peliculasReservada = pelicula;
        }else {
            throw new Error("No es una pelicula valida");
        }
    }
    puedeVer(){
        if(edad >= this.peliculaReservada.edadMinima){
            return true;
        }else{
            return false;
        }
    }
    mostrarResumen(){
        console.log(this.nombre, this.edad, this.peliculaReservada.mostrarInfo, Espectador.puedeVer)
    }
}
class Cartelera{
    constructor(){
        this.peliculas = [];
    }
    agregarPelicula(pelicula){
        this.peliculas.push(pelicula);
    }
    mostrarCartelera(){
        for (const pelicula of this.peliculas) {
            console.log(pelicula.mostrarInfo);
        }
    }
    peliculasPorEdad(minEdad){
        const arrayFiltrado = [];
        for (const pelicula of this.peliculas) {
            if(minEdad<=pelicula.edadMinima){
                arrayFiltrado.push(pelicula);
            }
        }
    }
    buscarPelicula(codigo){
        for (const pelicula of this.peliculas) {
            if(codigo === pelicula.codigo){
                console.log(pelicula.mostrarInfo);
            }else {
                return null;
            }
        }
    }
    /*eliminarPelicula(codigo){
        for (const pelicula of this.peliculas) {
            if(codigo === pelicula.codigo){
            }
        }
    }*/
}
//Crear Peliculas
let pelicula1 = new Pelicula("PL101", "Ciencia ficción", "Matrix", 1999, 13);
let pelicula2 = new Pelicula("PL102", "Terror", "La noche oscura", 2022, 18);
let pelicula3 = new Pelicula("PL103", "Comedia", "Un verano loco", 2019, 7);
//Crear Espectadores
let espectador1 = new Espectador("Ana García", 20);
let espectador2 = new Espectador("Luis Pérez", 15);
//Crear Cartelera Megaocio y añadir las peliculas
let cartelera1 = new Cartelera("Megaocio");
cartelera1.agregarPelicula(pelicula1);
cartelera1.agregarPelicula(pelicula2);
cartelera1.agregarPelicula(pelicula3);
//reservar una pelicula para cada espectador
espectador1.reservarPelicula(pelicula1);
espectador2.reservarPelicula(pelicula2);
//mostrar cartelera
cartelera1.mostrarCartelera();
//resumen espectadores
espectador1.mostrarResumen();
espectador2.mostrarResumen();
//utilizar peliculasporedad(16)
cartelera1.peliculasPorEdad(16);
//buscar pelicula por codigo
cartelera1.buscarPelicula("PL101");
cartelera1.buscarPelicula("PL105");
//eliminar una pelicula
//cartelera1.eliminarPelicula("PL101");
//cartelera1.mostrarCartelera();
//comprobar esAntigua() y esParaAdultos()
pelicula2.esAntigua();
pelicula2.esParaAdultos();
//cambiar datos
pelicula1.edadMinima = 120;
