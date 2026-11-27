
document.addEventListener("DOMContentLoaded", () => {
    const languageSelector = document.getElementById("langSelect");
    if (!languageSelector) {
        return;
    }

    languageSelector.addEventListener("change", () => {
        // Cambiamos solo lang para conservar la vista, la página y la ordenación de la URL actual.
        const url = new URL(window.location.href);
        url.searchParams.set("lang", languageSelector.value);
        window.location.assign(url.toString());
    });
});
