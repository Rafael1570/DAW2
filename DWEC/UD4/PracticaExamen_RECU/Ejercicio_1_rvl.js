/* Ejercicio de examen · Cartelera de cine
Crea un programa en JavaScript con dos clases:
- Pelicula. Guarda el título, el género y la duración en minutos.
- Cartelera. Guarda el nombre del cine y un array de películas que 
empieza vacío. Incluye un método agregarPelicula que recibe un objeto Pelicula y lo añade al array.
Prueba tu programa creando tres películas por separado con new. Después, crea una cartelera, añade las 
tres películas usando el método y muestra por consola el nombre del cine, cuántas películas hay en la cartelera y el título de la segunda. */

class Pelicula {
    #titulo;
    #genero;
    #duracion;

    constructor(titulo, genero, duracion) {
        this.#titulo = titulo;
        this.#genero = genero;
        this.#duracion = duracion;
    }

    get titulo() {
        return this.#titulo;
    }

    get genero() {
        return this.#genero;
    }

    get duracion() {
        return this.#duracion;
    }
}

class Cartelera {
    constructor(nombreCine) {
        this.nombreCine = nombreCine;
        this.peliculas = [];
    }

    agregarPelicula(pelicula) {
        this.peliculas.push(pelicula);
    }
}

const pelicula1 = new Pelicula("Shrek", "comedia", 90);
const pelicula2 = new Pelicula("Los tres cerditos", "terror", 85);
const pelicula3 = new Pelicula("Regreso al futuro", "ciencia ficción", 116);

const cartelera = new Cartelera("Cine Central");
cartelera.agregarPelicula(pelicula1);
cartelera.agregarPelicula(pelicula2);
cartelera.agregarPelicula(pelicula3);

console.log("Cine:", cartelera.nombreCine);
console.log("Número de películas:", cartelera.peliculas.length);
console.log("Segunda película:", cartelera.peliculas[1].titulo);
