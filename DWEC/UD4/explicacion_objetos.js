class Alumno{
    #nombre;
    #nota;
    constructor(nombre, nota){
        this.#nombre=nombre;
        this.nota=nota;
    }
    get nombre(){
        return this.#nombre;
    }
    set nombre(nombre){
        this.#nombre=nombre;
    }
    get nota(){
        return this.#nota;
    }
    set nota(nota){
        if(nota>=0 && nota<=10){
            this.#nota=nota;
        }else{
            throw new Error("valor no valido");
        }
    }
    saludar() {
        console.log("Hola soy el alumno/a : " + this.#nombre);
    }
}
// class Grupo{
//     constructor(nombre){
//         this.nombre= nombre;
//         this.alumnos= [];
//     }
//     agregar_alumnos(alumno){
//         this.alumnos.push(alumno);
//     }
// }
let alumno1=new Alumno("Martin", 20);
alumno1.saludar();
console.log(alumno1.nombre);
alumno1.nombre="Paco";
console.log(alumno1.nombre);
alumno1.nota=20;
console.log(alumno1.nota);
// let grupo1= new Grupo("Daw2m");
// grupo1.agregar_alumnos(alumno1);
// console.log(grupo1.alumnos);
// console.log(alumno1.nombre);
// console.log(alumno1.nota);
// let alumno2=new Alumno(3, "perro");
// console.log(alumno2.nombre);
// console.log(alumno2.nota);
// alumno2.edad=25;
// console.log(alumno2.edad);
