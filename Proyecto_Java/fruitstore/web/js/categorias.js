let listaCategorias = [];

export async function inicializar(){
    setDetalleVisible(false);
    await cargarListaCategorias();
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
        let cat = listaCategorias[i];
        let textoStatus = (cat.status === 1) ? '<span class="text-success fw-bold">Activo</span>' : '<span class="text-danger fw-bold">Inactivo</span>';
        
        contenido += '<tr>';
        contenido += '<td>' + cat.id + '</td>'; 
        contenido += '<td>' + cat.nombre + '</td>'; 
        contenido += '<td>' + textoStatus + '</td>'; 
        contenido += '<td class="text-center"><button class="btn btn-outline-primary btn-sm" onclick="cm.mostrarDetalleCategoria(' + i + ')">Ver Detalle</button></td>';
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
    
    categoria.nombre = document.getElementById("txtNomCategoria").value;
    
    let estatusVal = document.getElementById("txtStatus").value.trim();
    categoria.status = (estatusVal === "") ? 0 : parseInt(estatusVal);
    
    let categoriaJSON = JSON.stringify(categoria);
    
    // Se envía por QueryParam según el REST proporcionado
    let url = '../api/categoria/save?categoria=' + encodeURIComponent(categoriaJSON);
             
    let resp = await fetch(url, {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' }
    });

    let datos = await resp.json();
    
    if (datos.error != null) {
        Swal.fire("", "Error al guardar categoría.", "warning");
        return;
    }
    
    if (datos.exception != null) {
        Swal.fire("", datos.exception, "error");
        return;
    }
                
    document.getElementById("txtIdCategoria").value = datos.id;
    Swal.fire('Movimiento realizado.', 'Datos de categoría guardados.', 'success');
    
    cargarListaCategorias();
    limpiarFormulario();
    setDetalleVisible(false);
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
    setDetalleVisible(false);
}

export function mostrarDetalleCategoria(posicion) {
    let c = listaCategorias[posicion];
    document.getElementById("txtIdCategoria").value = c.id;
    document.getElementById("txtNomCategoria").value = c.nombre;
    if(c.status !== undefined) {
        document.getElementById("txtStatus").value = c.status;
    }
    setDetalleVisible(true);
}

export function setDetalleVisible(valor) {
    if (valor === true) {
        document.getElementById("divFormCategorias").style.display = '';
        document.getElementById("divTablaCategorias").style.display = 'none';
    } else {
        document.getElementById("divTablaCategorias").style.display = '';
        document.getElementById("divFormCategorias").style.display = 'none';
    }
}

export function cerrarDetalle() {
    limpiarFormulario();
    setDetalleVisible(false);
}

export function limpiarFormulario() {
    document.getElementById("txtIdCategoria").value = "";
    document.getElementById("txtNomCategoria").value = "";
    document.getElementById("txtStatus").value = "";
}