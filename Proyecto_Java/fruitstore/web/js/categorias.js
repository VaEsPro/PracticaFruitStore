let listaCategorias = [];

export async function inicializar(){
    setDetalleVisible(false);
    cargarListaCategorias();
}

async function cargarListaCategorias(){
    let url = '/fruitstore/api/categoria/getAll';
    let resp = await fetch(url);
    listaCategorias = await resp.json();
    refrescarTablaCategorias();
}

function refrescarTablaCategorias() {
    let contenido = '';
    for (let i = 0; i < listaCategorias.length; i++) {
        contenido += '<tr>';
        contenido += '<td>' + listaCategorias[i].id + '</td>'; 
        contenido += '<td>' + listaCategorias[i].nombre + '</td>'; 
        contenido += '<td>' + '<a href="#" onclick="cm.mostrarDetalleCategoria(' + i + ')">Ver Detalle</a>' + '</td>';
        contenido += '</tr>';
    }
    document.getElementById("tbodyCategorias").innerHTML = contenido;
}


export async function save() {
    let categoria = new Object();
    if (document.getElementById("txtIdCategoria").value.trim().length === 0)
        categoria.id = 0;
    else
        categoria.id = parseInt(document.getElementById("txtIdCategoria").value.trim());
    
    categoria.nombre = document.getElementById("txtNombreCategoria").value;
    
    let categoriaJSON = JSON.stringify(categoria);
    
    // Se envía por QueryParam según el REST proporcionado
    let url = '../api/categoria/save?categoria=' + encodeURIComponent(categoriaJSON);
             
    let resp = await fetch(url, {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' }
    });

    let datos = await resp.json();
    
    if (datos.error != null) {
        Swal.fire("", "Error al guardar Categoría.", "warning");
        return;
    }
    
    if (datos.exception != null) {
        Swal.fire("", datos.exception, "error");
        return;
    }
                
    document.getElementById("txtIdCategoria").value = datos.id;
    Swal.fire('Movimiento realizado.', 'Datos de Categoría guardados.', 'success');
    cargarListaCategorias();
}

export async function deletE() {
    let id = 0;
    let idVal = document.getElementById("txtIdCategoria").value.trim();

    if (idVal.length === 0) {
        Swal.fire("", "Debe seleccionar una categoría para eliminar.", "warning");
        return;
    }

    id = parseInt(idVal);
    let url = '../api/categoria/delete?id=' + id;

    let resp = await fetch(url, {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' }
    });

    let datos = await resp.json();

    if (datos.error != null) {
        Swal.fire("", datos.error, "warning");
        return;
    }

    if (datos.exception != null) {
        Swal.fire("", datos.exception, "error");
        return;
    }

    Swal.fire('Movimiento realizado.', 'Categoría eliminada correctamente.', 'success');
    
    cargarListaCategorias();
    limpiarFormulario();
}

export function mostrarDetalleCategoria(posicion) {
    let c = listaCategorias[posicion];
    document.getElementById("txtIdCategoria").value = c.id;
    document.getElementById("txtNombreCategoria").value = c.nombre;
    setDetalleVisible(true);
}

export function setDetalleVisible(valor) {
    if (valor === true) {
        document.getElementById("divControlCategorias").style.display = '';
        document.getElementById("divDetalleCategoria").style.display = '';
    } else {
        document.getElementById("divDetalleCategoria").style.display = 'none';
        document.getElementById("divControlCategorias").style.display = '';
    }
}

export function cerrarDetalle() {
    limpiarFormulario();
    setDetalleVisible(false);
}

function limpiarFormulario() {
    document.getElementById("txtIdCategoria").value = "";
    document.getElementById("txtNombreCategoria").value = "";
}