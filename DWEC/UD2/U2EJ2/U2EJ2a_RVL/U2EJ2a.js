let nombreAlumno = String(prompt ("Introduce el nombre del alumno "));
let notasAlumnos = Number(prompt ("Dime la nota de el alumno"));

let suma = 0;
for (let i = 0; i < 5; i++) {
    let nota = Number(prompt("Introduce la nota del alumno " + (i + 1) + ":"));
    suma += nota;
}
let media = suma / 5;
console.log("La nota media de los alumnos es: " + media);
