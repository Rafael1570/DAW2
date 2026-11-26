/*Simulacro de examen · Biblioteca
Crea un programa en JavaScript con dos clases:
- Libro. Guarda el título, el autor y el número de páginas.
- Biblioteca. Guarda el nombre de la biblioteca y un array de libros que empieza vacío. 
Incluye un método agregarLibro(libro) para añadir un libro y un método mostrarLibros() 
que recorra el array con for...of y muestre el título y el autor de cada libro.
Crea tres libros por separado con new, añádelos a una biblioteca y muestra por consola el 
nombre de la biblioteca, el número total de libros y el listado de títulos y autores.*/

class Libro {
    constructor(titulo, autor, paginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
    }
}
class Biblioteca {
    constructor(nombre) {
        this.nombre = nombre;
        this.libros = [];
    }
    agregarLibro(libro) {
        this.libros.push(libro)
    }
    mostrarLibros(){
        for (const libro of this.libros) {
            console.log(libro.titulo, libro.autor)
        }
    }
}
//Creacion primer Libro
let libro1 = new Libro("El principito", "Antonio de Saint-Exupéry", 96);
console.log(libro1.titulo);

//Creacion de la biblioteca
let biblioteca1 = new Biblioteca("Biblioteca Central");
biblioteca1.agregarLibro(libro1);

//mostrar cantidad de libros en la biblioteca
console.log(biblioteca1.libros.length); // Debe salir 1

//muestra los libros de la biblioteca
biblioteca1.mostrarLibros();

//añadimos mas libros
let libro2 = new Libro("Los tres cerditos", "PEPITO", 100);
let libro3 = new Libro("HarryPotter", "ns", 200);

// lso añadimos a biblioteca

biblioteca1.agregarLibro(libro2);
biblioteca1.agregarLibro(libro3);

// mostramos la informacion
biblioteca1.mostrarLibros();