function iniciarMarcoDigital() {
    const marco = document.querySelector(".seccionimagenes");
    const imagen = marco.querySelector("img");
    const pasoZoom = 30;
    const tamanoMinimo = 5;
    let colorActual = "negro";

    function cambiarZoom(paso) {
        const tamanoMaximo = Math.min(marco.clientWidth, marco.clientHeight);
        const tamanoActual = Math.min(Number(imagen.getAttribute("height")), tamanoMaximo);
        const tamano = Math.min(tamanoMaximo, Math.max(tamanoMinimo, tamanoActual + paso));

        imagen.setAttribute("width", tamano);
        imagen.setAttribute("height", tamano);
    }

    document.querySelectorAll(".logos img").forEach((miniatura) => {
      
      
        miniatura.addEventListener("click", () => {
            imagen.setAttribute("src", miniatura.getAttribute("src"));
            imagen.setAttribute("alt", miniatura.getAttribute("alt"));
        });
    });

    document.getElementById("zoom-in").addEventListener("click", () => {
        cambiarZoom(pasoZoom);
    });

    document.getElementById("zoom-out").addEventListener("click", () => {
        cambiarZoom(-pasoZoom);
    });

    document.querySelectorAll(".cambiarColor").forEach((cuadro) => {
        cuadro.addEventListener("mouseover", () => {
            marco.classList.replace(colorActual, cuadro.id);
            colorActual = cuadro.id;
        });
    });

    marco.classList.add(colorActual);
    cambiarZoom(0);
}

if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", iniciarMarcoDigital);
} else {
    iniciarMarcoDigital();
}
