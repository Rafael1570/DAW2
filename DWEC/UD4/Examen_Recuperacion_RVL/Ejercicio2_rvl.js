class Instrumento{
    #codigo
    #tipo
    #marca
    #anio
    constructor(codigo, tipo, marca, anio){
        this.#codigo = codigo;
        this.#tipo = tipo;
        this.#marca = marca;
        this.anio = anio;
    }
    get codigo(){
        return this.#codigo;
    }
    get tipo(){
        return this.#tipo;
    }
    get marca(){
        return this.#marca;
    }
    get anio(){
        return this.anio;
    }
    set anio(anio){
        if(!Number.isInteger(anio >= 1 && anio <= 2026)){
            this.#anio = anio
        }else{
            throw new Error("El valor no es valido");
        }
    }
    mostrarInfo(){
        console.log(this.#codigo, this.#tipo, this.#marca, this.#anio)
    }
    esAntiguo(){
        let fechaActual = new Date().getFullYear();
        let anioInstrumento = this.#anio;
        let esAntiguo = fechaActual - anioInstrumento;
        if (esAntiguo >= 20){
            return true;
        }else {
            return false;
        }
    }
}
class Inventario{
    constructor(){
        this.instrumentos = [];
    }
    agregarIntrumento(instrumento){
        this.instrumentos.push(instrumento);
    }
    mostrarInventario(){
        for (const instrumento of this.instrumentos) {
            instrumento.mostrarInfo();
        }
    }
    buscarInstrumento(codigo){
        for (const instrumento of this.instrumentos) {
            if(instrumento.codigo === codigo){
                return instrumento;
            }
        }
        return null;
    }
}
//Creacion de instrumentos
let instrumento1 = new Instrumento("VL123", "cuerda", "Stradivarius", 1990);
let instrumento2 = new Instrumento("FL456", "viento", "Yamaha", 2018);
let instrumento3 = new Instrumento("TB789", "percusión", "Pearl", 2022);
//Crear objeto inventario y  añadir instrumentos
const inventario1 = new Inventario();
inventario1.agregarIntrumento(instrumento1);
inventario1.agregarIntrumento(instrumento2);
inventario1.agregarIntrumento(instrumento3);
//Mostrar Inventario
console.log("=====MOSTRAR INFO===");
inventario1.mostrarInventario();
//Buscar instrumento por codigo y mostrar la info
console.log("====BUSCAR POR CODIGO====");
let instrumentoEncontrado = inventario1.buscarInstrumento("VL123");
if(instrumentoEncontrado !== null){
    instrumentoEncontrado.mostrarInfo();
}else{
    console.log("Instrumento no encontrado");
}
//Buscar instrumento que no exista
console.log("=====INSTRUMENTO QUE NO EXISTE=====");
instrumentoEncontrado = inventario1.buscarInstrumento("VL200");
if(instrumentoEncontrado !== null){
    instrumentoEncontrado.mostrarInfo();
}else{
    console.log("Instrumento no encontrado");
}
//Asignar un año no valido
console.log("=====MODIFICACION DEL AÑO======");
instrumento1.anio = 2030;