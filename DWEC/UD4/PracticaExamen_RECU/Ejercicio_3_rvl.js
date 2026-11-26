/*Crea un programa en JavaScript con dos clases.

Jugador
Constructor Jugador(nombre, dorsal, puntos)
- nombre — texto.
- dorsal — número.
- puntos — número de puntos anotados en el último partido.
Equipo
Constructor Equipo(nombre)
- nombre — texto.
- jugadores — array de objetos Jugador que empieza vacío.
La clase Equipo debe tener dos métodos:
- agregarJugador(jugador) recibe un objeto Jugador y lo añade al equipo.
- mostrarJugadores() recorre el array con for...of y muestra el nombre y el dorsal de cada jugador.
Prueba tu programa. Crea tres jugadores por separado con new, crea un equipo llamado "Halcones" y añade los 
tres al equipo. Muestra por consola el nombre del equipo, cuántos jugadores tiene, los puntos del segundo jugador 
y el listado de nombres y dorsales.*/

class Jugador{
    constructor(nombre, dorsal, puntos){
        this.nombre =  nombre;
        this.dorsal = dorsal;
        this.puntos = puntos;
    }
}
class Equipo{
    constructor(nombre){
        this.nombre = nombre;
        this.jugadores = [];
    }
    agregarJugador(Jugador){
        this.jugadores.push(Jugador);
    }
    mostrarJugadores(){
        for (const jugador of this.jugadores) {
            console.log(jugador.nombre, jugador.dorsal);
        }
    }
}

//Creacion de jugadores
let jugador1 = new Jugador("Rafael Vega Librero", 2, 23);
let jugador2 = new Jugador("Jose Maria", 10, 10);
let jugador3 = new Jugador("Alejandro", 19, 24);
//Crear Equipo
let equipo1 = new Equipo("Halcones");
//Meter jugadores en equipo
equipo1.agregarJugador(jugador1);
equipo1.agregarJugador(jugador2);
equipo1.agregarJugador(jugador3);
//Comprobaciones
console.log(equipo1.nombre);
console.log(equipo1.jugadores.length);
console.log(jugador2.puntos);
equipo1.mostrarJugadores();