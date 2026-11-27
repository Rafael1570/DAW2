// Navegación entregada por el profesor. No forma parte de las ocho tareas.
const pestanas = document.querySelectorAll(".tab");
for (const pestana of pestanas) {
    pestana.addEventListener("click", cambiarArea);
}
function cambiarArea(event) {
    const nombre = event.currentTarget.dataset.area;
    for (const pestana of pestanas) {
        pestana.classList.remove("activo");
    }
    event.currentTarget.classList.add("activo");
    const areas = document.querySelectorAll(".area");
    for (const area of areas) {
        area.classList.remove("activo");
    }
    document.querySelector("#area-" + nombre).classList.add("activo");
}
