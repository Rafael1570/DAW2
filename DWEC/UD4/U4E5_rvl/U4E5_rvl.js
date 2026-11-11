// Rafael vega librero

class Socio {
    constructor(numeroSocio, DNI, nombre, apellido, fechaNacimiento, localidad){
        this.numeroSocio = numeroSocio;
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.localidad = localidad;
    }
    modificarLocalidad(nuevaLocalidad){
        this.localidad = nuevaLocalidad;
    }
    obtenerCategoria(){
        let fechaActual = new Date();
        let anioActual = fechaActual.getFullYear();

        let fechaNacimiento = new Date(this.fechaNacimiento);
        let anioNacimiento = fechaNacimiento.getFullYear();

        let edad = anioActual - anioNacimiento;

        if (edad == 8 || edad == 9) {
            return "Benjamín";
        } else if (edad == 10 || edad == 11) {
            return "Alevín";
        } else if (edad == 12 || edad == 13) {
            return "Infantil";
        } else {
            return "Sin categoría";
        }
    }
    mostrarInformacion(){
        console.log("===============INFORMACION================");
        console.log("NªSocio: "+this.numeroSocio);
        console.log("DNI :" +this.DNI);
        console.log("Nombre: "+this.nombre);
        console.log("Apellido :" +this.apellido);
        console.log("FechaNacimiento: " + this.fechaNacimiento);
        console.log("Localidad: "+ this.localidad);
        console.log("Categoria: " + this.obtenerCategoria());    
    }
}

const socios = [];

// DAR DE ALTA SOCIO
function altaSocio(numeroSocio, DNI, nombre, apellido, fechaNacimiento, localidad) {
    let socio1 = new Socio(numeroSocio, DNI, nombre, apellido, fechaNacimiento, localidad);
    socios.push(socio1);
}

// MOSTRAR TODOS LOS SOCIOS
function mostrarSocios() {
    for (let i = 0; i < socios.length; i++) {
        socios[i].mostrarInformacion();
    }
}

// BUSCAR SOCIO POR DNI
function buscarSocio(DNI) {
    let socioEncontrado = socios.find(socio => socio.DNI == DNI);

    if (socioEncontrado != undefined) {
        socioEncontrado.mostrarInformacion();
    } else {
        console.log("No se ha encontrado el socio");
    }
}

// BUSCAR SOCIOS POR LOCALIDAD
function buscarPorLocalidad(localidad) {
    let encontrados = socios.filter(socio => socio.localidad == localidad);

    if (encontrados.length > 0) {
        for (let socio of encontrados) {
            socio.mostrarInformacion();
        }
    } else {
        console.log("No hay socios en esa localidad");
    }
}

// 1. DAR DE ALTA SOCIOS
altaSocio(1, "12345678A", "Rafael", "Vega", "2007-05-01", "Sevilla");
altaSocio(2, "23456789B", "Antonio", "García", "2016-03-15", "Bormujos");
altaSocio(3, "34567890C", "Lucía", "Martínez", "2015-07-10", "Tomares");
altaSocio(4, "45678901D", "Pablo", "López", "2014-09-25", "Gines");
altaSocio(5, "56789012E", "María", "Fernández", "2013-02-12", "Sevilla");

// 2. MOSTRAR TODOS LOS SOCIOS
console.log("======= TODOS LOS SOCIOS =======");
mostrarSocios();

// 3. MODIFICAR LOCALIDAD
socios[0].modificarLocalidad("Bormujos");

// 4. MOSTRAR SOCIO MODIFICADO
console.log("======= SOCIO MODIFICADO =======");
socios[0].mostrarInformacion();

// 5. BUSCAR SOCIO POR DNI
console.log("======= BUSCAR SOCIO =======");
buscarSocio("23456789B");

// 6. BUSCAR DNI INEXISTENTE
console.log("======= DNI INEXISTENTE =======");
buscarSocio("99999999Z");

// 7. BUSCAR SOCIOS DE UNA LOCALIDAD
console.log("======= SOCIOS DE SEVILLA =======");
buscarPorLocalidad("Bormujos");

// 8. BUSCAR LOCALIDAD SIN SOCIOS
console.log("======= LOCALIDAD INEXISTENTE =======");
buscarPorLocalidad("Cádiz");




