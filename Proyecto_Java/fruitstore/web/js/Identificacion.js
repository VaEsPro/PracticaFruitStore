async function login(event){
    event.preventDefault();
    let usuario = document.getElementById("txtUsuario").value;
    let contrasenia = document.getElementById("txtContrasenia").value;
    let url = "api/usuario/login";
    let params = {
                usuario     : usuario,
                contrasenia : contrasenia
                };
    let resp = await fetch (url, 
                            {
                                method: "POST",
                                headers: {
                                            'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'
                                         },
                                body: new URLSearchParams(params)
                            });
    let data = await resp.json();
    
    if(data.error != null){
        Swal.fire('Error', data.error, 'warning');
        return;
    } else if (data.exception != null){
        Swal.fire('Error en el servidor', data.exception, 'warning');
        return;
    } else {
        window.location.href = 'modulos/index.html';
    }
}

async function logout(){
    
}