class Alumno{
    constructor(nombre, nota){
        this.nombre=nombre;
        this.nota=nota;
    }
    saludar() {
        console.log("Hola soy el alumno/a : " + this.nombre);
    }
}
class Grupo{
    constructor(nombre){
        this.nombre= nombre;
        this.alumnos= [];
    }
    agregar_alumnos(alumno){
        this.alumnos.push(alumno);
    }
}
let alumno1=new Alumno("Ana", 10);
alumno1.saludar();
let grupo1= new Grupo("Daw2m");
grupo1.agregar_alumnos(alumno1);
console.log(grupo1.alumnos);
// console.log(alumno1.nombre);
// console.log(alumno1.nota);
// let alumno2=new Alumno(3, "perro");
// console.log(alumno2.nombre);
// console.log(alumno2.nota);
// alumno2.edad=25;
// console.log(alumno2.edad);
