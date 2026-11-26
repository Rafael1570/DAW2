class Pelicula {
    #codigo;
    #genero;
    #titulo;
    #anio;
    #edadMinima;

    constructor(codigo, genero, titulo, anio, edadMinima) {
        this.#codigo = codigo;
        this.#genero = genero;
        this.#titulo = titulo;
        this.#anio = anio;
        this.edadMinima = edadMinima;
    }

    get codigo() {
        return this.#codigo;
    }

    get genero() {
        return this.#genero;
    }

    get titulo() {
        return this.#titulo;
    }

    get anio() {
        return this.#anio;
    }

    get edadMinima() {
        return this.#edadMinima;
    }

    set edadMinima(edadMinima) {
        if (!Number.isInteger(edadMinima) || edadMinima < 0 || edadMinima > 18) {
            throw new Error("La edad mínima debe ser un número entero entre 0 y 18.");
        }

        this.#edadMinima = edadMinima;
    }

    mostrarInfo() {
        console.log(
            `${this.#codigo} | ${this.#titulo} | ${this.#genero} | ${this.#anio} | Edad mínima: ${this.#edadMinima}`
        );
    }

    esAntigua() {
        const anioActual = new Date().getFullYear();
        return anioActual - this.#anio > 20;
    }

    esParaAdultos() {
        return this.#edadMinima > 16;
    }
}

class Espectador {
    constructor(nombre, edad) {
        this.nombre = nombre;
        this.edad = edad;
        this.peliculaReservada = null;
    }

    reservarPelicula(pelicula) {
        if (!(pelicula instanceof Pelicula)) {
            throw new Error("La reserva debe ser un objeto de la clase Pelicula.");
        }

        this.peliculaReservada = pelicula;
    }

    puedeVer() {
        return this.peliculaReservada !== null &&
            this.edad >= this.peliculaReservada.edadMinima;
    }

    mostrarResumen() {
        console.log(`${this.nombre}, ${this.edad} años`);

        if (this.peliculaReservada !== null) {
            this.peliculaReservada.mostrarInfo();
        } else {
            console.log("No tiene ninguna película reservada.");
        }

        console.log("¿Puede verla?", this.puedeVer() ? "Sí" : "No");
    }
}

class Cartelera {
    constructor() {
        this.peliculas = [];
    }

    agregarPelicula(pelicula) {
        this.peliculas.push(pelicula);
    }

    mostrarCartelera() {
        for (const pelicula of this.peliculas) {
            pelicula.mostrarInfo();
        }
    }

    peliculasPorEdad(minEdad) {
        const peliculasFiltradas = [];

        for (const pelicula of this.peliculas) {
            if (pelicula.edadMinima >= minEdad) {
                peliculasFiltradas.push(pelicula);
            }
        }

        return peliculasFiltradas;
    }

    buscarPelicula(codigo) {
        for (const pelicula of this.peliculas) {
            if (pelicula.codigo === codigo) {
                return pelicula;
            }
        }

        return null;
    }

    eliminarPelicula(codigo) {
        for (let i = 0; i < this.peliculas.length; i++) {
            if (this.peliculas[i].codigo === codigo) {
                this.peliculas.splice(i, 1);
                return;
            }
        }
    }
}

// Programa principal
const pelicula1 = new Pelicula("PL101", "Ciencia ficción", "Matrix", 1999, 13);
const pelicula2 = new Pelicula("PL102", "Terror", "La noche oscura", 2022, 18);
const pelicula3 = new Pelicula("PL103", "Comedia", "Un verano loco", 2019, 7);

const espectador1 = new Espectador("Ana García", 20);
const espectador2 = new Espectador("Luis Pérez", 15);

const cartelera = new Cartelera();
cartelera.agregarPelicula(pelicula1);
cartelera.agregarPelicula(pelicula2);
cartelera.agregarPelicula(pelicula3);

espectador1.reservarPelicula(pelicula1);
espectador2.reservarPelicula(pelicula2);

console.log("\nCartelera completa");
cartelera.mostrarCartelera();

console.log("\nResumen de los espectadores");
espectador1.mostrarResumen();
espectador2.mostrarResumen();

console.log("\nPelículas con edad mínima de 16 años o más");
const peliculasFiltradas = cartelera.peliculasPorEdad(16);

for (const pelicula of peliculasFiltradas) {
    pelicula.mostrarInfo();
}

console.log("\nBúsquedas por código");
for (const codigo of ["PL102", "PL105"]) {
    const peliculaEncontrada = cartelera.buscarPelicula(codigo);

    if (peliculaEncontrada === null) {
        console.log(`No se encontró ninguna película con el código ${codigo}.`);
    } else {
        peliculaEncontrada.mostrarInfo();
    }
}

cartelera.eliminarPelicula("PL101");
console.log("\nCartelera después de eliminar PL101");
cartelera.mostrarCartelera();

console.log("\nComprobación de antigüedad y edad mínima");
for (const pelicula of [pelicula1, pelicula2, pelicula3]) {
    console.log(
        `${pelicula.titulo} | Antigua: ${pelicula.esAntigua()} | Para adultos: ${pelicula.esParaAdultos()}`
    );
}

console.log("\nPrueba con una edad mínima inválida");
try {
    pelicula1.edadMinima = 19;
} catch (error) {
    console.log(error.message);
}