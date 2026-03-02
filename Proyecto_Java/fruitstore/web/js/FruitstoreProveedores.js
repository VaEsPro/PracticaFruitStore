let listaProveedores = [];

export async function inicializar(){
    cargarListaProveedores();
}

async function cargarListaProveedores(){
    let url = '../api/proveedor/getAll?filtro=';
    
    let resp = await fetch(url);
    
    listaProveedores = await resp.json();
    
    refrescarTablaProveedores();
}

function refrescarTablaProveedores(){
    let contenido = "";
    for (let i = 0; i < listaProveedores.length; i++){
        let p = listaProveedores[i];
        contenido += '<tr>' +
                '<td>' + p.id + '</td>' +
                '<td>' + p.nombre + '</td>' +
                '<td>' + (p.rfc ? p.rfc : 'N/A') + '</td>' +
                '<td>' + (p.telefonoMovil ? p.telefonoMovil : 'N/A') + '</td>' +
                '<td>' + p.status + '</td>' +
                '<td>' + '<a href="#" onclick="cm.mostrarDetalles('+ i +');">Ver Detalles</a>' + '</td' +
                '</tr>';
    }
    document.getElementById("tbodyProveedores").innerHTML = contenido;
}