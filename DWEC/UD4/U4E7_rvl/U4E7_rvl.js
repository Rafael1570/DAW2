//Rafael Vega Librero
class Conductor{
    #nombre
    #licencia
    constructor(nombre, licencia){
        this.#nombre = nombre;
        this.#licencia= licencia;
    }
    get nombre(){
        return this.#nombre;
    }
    get licencia(){
        return this.#licencia;
    }

}
class Bus{
    #capacidad
    #pasajeros
    #conductor
    constructor(capacidad, pasajeros, conductor){
        this.#capacidad = capacidad;
        this.#pasajeros = 0;
        this.#conductor = null;
    }
    get capacidad(){
        return this.#capacidad;
    }
    get pasajeros(){
        return this.#pasajeros;
    }
    get conductor(){
        return this.#conductor
    }
    
    subir(cantidad) {
        let vacio = this.#capacidad - this.#pasajeros;

        if (cantidad <= vacio) {
            this.#pasajeros += cantidad;
            console.log("Han subido " + cantidad + " pasajeros");
        } else {
            this.#pasajeros += vacio;
            console.log("Han subido " + vacio + " pasajeros");
        }

        console.log("Total de pasajeros: " + this.#pasajeros);
    }
    
    bajar(cantidad) {
        let bajan = cantidad;

        if (this.#pasajeros >= bajan) {
            this.#pasajeros -= bajan;
            console.log("Han bajado " + cantidad + " pasajeros");
        } else {
            bajan = this.#pasajeros;
            this.#pasajeros = 0;
            console.log("Han bajado " + bajan + " pasajeros");
        }

        console.log("Total de pasajeros: " + this.#pasajeros);
    }

    asignarConductor(conductor){
        if(conductor instanceof Conductor){
            this.#conductor = conductor;
            console.log(`${conductor.nombre} con número de licencia ${conductor.numeroLicencia} ha sido asignado.`);
        }else {
            throw new Error("El objeto no es un conductor valido");
        }
    }
}
// PROGRAMA PRINCIPAL

const conductor1 = new Conductor("José", 1234);

const bus101 = new Bus(40);

bus101.asignarConductor(conductor1);

bus101.subir(25);
console.log("Comprobación: " + bus101.pasajeros);

bus101.subir(10);
console.log("Comprobación: " + bus101.pasajeros);

bus101.subir(10);
console.log("Comprobación: " + bus101.pasajeros);

bus101.bajar(15);
console.log("Comprobación: " + bus101.pasajeros);

bus101.bajar(30);
console.log("Comprobación: " + bus101.pasajeros);