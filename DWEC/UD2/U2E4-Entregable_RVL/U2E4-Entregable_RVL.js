let Opcion = prompt("¿Quieres una pizza vegetariana? Escribe Si o No:");

while (Opcion === null ||(Opcion !== "Si" && Opcion !== "si" && Opcion !== "No" && Opcion !== "no")) {
    Opcion = prompt("¿Quieres una pizza vegetariana? Escribe Si o No:");
}

switch (Opcion) {

    case "Si":
    case "si":

        let ingredienteV = prompt("Ingredientes disponibles: Tofu y Pimiento");
        if (ingredienteV === null){
            break;
        }
        while (
            ingredienteV !== "Tofu" &&
            ingredienteV !== "tofu" &&
            ingredienteV !== "Pimiento" &&
            ingredienteV !== "pimiento"
        ) {
            ingredienteV = prompt("Ingredientes disponibles: Tofu y Pimiento");
        }

        if (ingredienteV === "Tofu" || ingredienteV === "tofu") {
            alert("Pizza vegetariana. Ingredientes: Tomate, Mozzarella y Tofu");
        } else {
            alert("Pizza vegetariana. Ingredientes: Tomate, Mozzarella y Pimiento");
        }

        break;


    case "No":
    case "no":

        let ingredienteC = prompt("Ingredientes disponibles: Peperoni, Salmon, Jamon");
        if (ingredienteC === null){
            break;
        }
        while (
            ingredienteC !== "Peperoni" &&
            ingredienteC !== "peperoni" &&
            ingredienteC !== "Salmon" &&
            ingredienteC !== "salmon" &&
            ingredienteC !== "Jamon" &&
            ingredienteC !== "jamon"
        ) {
            ingredienteC = prompt("Ingredientes disponibles: Peperoni, Salmon, Jamon");
        }

        if (ingredienteC === "Jamon" || ingredienteC === "jamon") {
            alert("Pizza no vegetariana. Ingredientes: Tomate, Mozzarella y Jamon");
        } else if(ingredienteC === "Peperoni" || ingredienteC === "peperoni"){
            alert("Pizza no vegetariana. Ingredientes: Tomate, Mozzarella y Peperoni");
        } else {
            alert("Pizza no vegetariana. Ingredientes: Tomate, Mozzarella y Salmon")
        }

        break;
}