let nombre = prompt("Introduce tu nombre:");

// Comprobar que el nombre no esté vacío
while (nombre === null || nombre.trim() === "") {
    alert("Error: el nombre de usuario no puede estar vacío.");
    nombre = prompt("Introduce de nuevo tu nombre:");
}

let contrasena = prompt("Introduce tu contraseña:");

// Comprobar que la contraseña sea correcta
while (contrasena !== "MeGustaProgramar") {
    alert("Error: contraseña incorrecta.");
    contrasena = prompt("Introduce de nuevo la contraseña:");
}

// Todo correcto
alert("La información se ha almacenado correctamente.");