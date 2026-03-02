let listaCategorias = [];

export async function inicializar(){
    cargarListaCategorias();
}

async function cargarListaCategorias(){
    let url = '../api/categoria/getAll?filtro=';
    
    let resp = await fetch(url);
    
    listaCategorias = await resp.json();
    
    refrescarTablaCategorias();
}

function refrescarTablaCategorias(){
    let contenido = "";
    for (let i = 0; i < listaCategorias.length; i++){
        contenido += '<tr>' +
                '<td>' + listaCategorias[i].id + '</td>' +
                '<td>' + listaCategorias[i].nombre + '</td>' +
                '<td>' + listaCategorias[i].status + '</td>' +
                '<td>' + '<a href="#" onclick="cm.mostrarDetalles('+ i +');">Ver Detalles</a>' + '</td>' +
                '</tr>';
    }
    document.getElementById("tbodyCategorias").innerHTML = contenido;
}