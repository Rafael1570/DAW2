// Rafael Vega Librero
// Videojuego
console.log("============================================");
console.log("           Videojuego                       ");
console.log("============================================");
class Videojuego{
    constructor(titulo, plataforma, horasJugadas){
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.horasJugadas = horasJugadas;
    }
    mostrarInformacion(){
        console.log(this.titulo);
        console.log(this.plataforma);
        console.log(this.horasJugadas);
    }
    jugar(horas){
        this.horasJugadas = horas + this.horasJugadas;
    }
    cambiarPlataforma(nuevaPlataforma){
        this.plataforma = nuevaPlataforma;
    }
}

let Videojuego1 = new Videojuego("Fornite", "PS4", 2000);
let Videojuego2 = new Videojuego("Valorant", "PC", 1000);
Videojuego1.mostrarInformacion();
Videojuego2.mostrarInformacion();
Videojuego1.jugar(5);
Videojuego2.cambiarPlataforma("PS4");
Videojuego1.mostrarInformacion();
Videojuego2.mostrarInformacion();

//Disco
console.log("============================================");
console.log("                  Disco                     ");
console.log("============================================");

class Disco{
    constructor(){
        this.nombre = "";
        this.artista = "";
        this.anio = 0;
        this.tipo = "";
        this.localizacion = 0;
        this.prestado = false;
    }
}