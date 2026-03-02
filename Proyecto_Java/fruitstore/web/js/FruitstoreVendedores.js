let listaVendedores = [];

export async function inicializar(){
    cargarListaVendedores();
}

async function cargarListaVendedores(){
    let url = '../api/vendedor/getAll?filtro=';
    
    let resp = await fetch(url);
    
    listaVendedores = await resp.json();
    
    refrescarTablaVendedores();
}

function refrescarTablaVendedores(){
    let contenido = "";
    for (let i = 0; i < listaVendedores.length; i++){
        let v = listaVendedores[i];
        contenido += '<tr>' +
                '<td>' + v.id + '</td>' +
                '<td>' + v.nombre + '</td>' +
                '<td>' + (v.telefono ? v.telefono : 'N/A') + '</td>' +
                '<td>' + (v.email ? v.email : 'N/A') + '</td>' +
                '<td>' + v.status + '</td>' +
                '<td>' + '<a href="#" onclick="cm.mostrarDetalles('+ i +');">Ver Detalles</a>' + '</td' +
                '</tr>';
    }
    document.getElementById("tbodyVendedores").innerHTML = contenido;
}