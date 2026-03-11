let cm = null;

async function login() {
    let usuario = document.getElementById("txtUsuario").value;
    let contrasenia = document.getElementById("txtContrasenia").value;
    let url = "/fruitstore/api/usuario/login";
    let params = {
        usuario: usuario,
        contrasenia: contrasenia
    };
    let resp = await fetch(url,
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
                },
                body: new URLSearchParams(params)
            });
    let data = await resp.json();

    if (data.error != null) {
        Swal.fire('Error', data.error, 'warning');
        return;
    } else if (data.exception != null) {
        Swal.fire('Error en el Servidor', data.exception, 'warning');
        return;
    } else {
        // Se guardan los datos de la credencial de usuario
        localStorage.setItem("datosUsuario", JSON.stringify(data));
        
        window.location.href = "modulos/index.html";
    }
}

function loadLocalUser() {
    let usuario = null;
    let nombreCompleto = null;

    if (localStorage.getItem("datosUsuario") === null) {
        window.location.href = "../index.html";
        return;
    } else {
        usuario = JSON.parse(localStorage.getItem("datosUsuario"));
        document.getElementById("spnNombreCompletoUsuario").innerHTML = usuario.vendedor.nombre;
    }
}


async function logout() {
    localStorage.removeItem("datosUsuario");
    window.location.href = "../index.html";
}
