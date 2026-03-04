let listaProveedores = [];

export async function inicializar(){
    setDetalleVisible(false);
    cargarListaProveedores();
}

async function cargarListaProveedores(){
    let url = '/fruitstore/api/proveedor/getAll';
    let resp = await fetch(url);
    listaProveedores = await resp.json();
    refrescarTablaProveedores();
}

function refrescarTablaProveedores() {
    let contenido = '';
    for (let i = 0; i < listaProveedores.length; i++) {
        let pr = listaProveedores[i];
        contenido += '<tr>';
        contenido += '<td>' + pr.nombre + '</td>';
        contenido += '<td>' + pr.razonSocial + '</td>';
        contenido += '<td>' + pr.rfc + '</td>';
        contenido += '<td>' + pr.direccion + '</td>';
        contenido += '<td>' + pr.email + '</td>';
        contenido += '<td>' + pr.telefonoFijo + '</td>';
        contenido += '<td>' + pr.telefonoMovil + '</td>';
        contenido += '<td>' + '<a href="#" onclick="cm.mostrarDetalleProveedor(' + i + ')">Ver Detalle</a>' + '</td>';
        contenido += '</tr>';
    }

    document.getElementById("tbodyProveedores").innerHTML = contenido;
}

export async function save() {
    let proveedor = new Object();

    if (document.getElementById("txtIdProveedor").value.trim().length === 0)
        proveedor.id = 0;
    else
        proveedor.id = parseInt(document.getElementById("txtIdProveedor").value.trim());

    proveedor.nombre = document.getElementById("txtNombre").value;
    proveedor.razonSocial = document.getElementById("txtRazonSocial").value;
    proveedor.rfc = document.getElementById("txtRFC").value;
    proveedor.direccion = document.getElementById("txtDireccion").value;
    proveedor.email = document.getElementById("txtEmail").value;
    proveedor.telefonoFijo = document.getElementById("txtTelFijo").value;
    proveedor.telefonoMovil = document.getElementById("txtTelMovil").value;

    let proveedorJSON = JSON.stringify(proveedor);

    // Se envía como QueryParam en la URL
    let url = '../api/proveedor/save?proveedor=' + encodeURIComponent(proveedorJSON);

    let resp = await fetch(url, {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' }
    });

    let datos = await resp.json();

    if (datos.error != null) {
        Swal.fire("", "Error al guardar Proveedor.", "warning");
        return;
    }

    if (datos.exception != null) {
        Swal.fire("", datos.exception, "error");
        return;
    }

    document.getElementById("txtIdProveedor").value = datos.id;
    Swal.fire('Movimiento realizado.', 'Datos del Proveedor guardados.', 'success');
    cargarListaProveedores();
}

export async function deletE() {
    let id = 0;
    let idVal = document.getElementById("txtIdProveedor").value.trim();

    if (idVal.length === 0) {
        Swal.fire("", "Debe seleccionar un proveedor para eliminar.", "warning");
        return;
    }

    id = parseInt(idVal);
    let url = '../api/proveedor/delete?id=' + id;

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

    Swal.fire('Movimiento realizado.', 'Proveedor eliminado correctamente.', 'success');
    
    cargarListaProveedores();
    limpiarFormulario();
}

export function mostrarDetalleProveedor(posicion) {
    let pr = listaProveedores[posicion];

    document.getElementById("txtIdProveedor").value = pr.id;
    document.getElementById("txtNombre").value = pr.nombre;
    document.getElementById("txtRazonSocial").value = pr.razonSocial;
    document.getElementById("txtRFC").value = pr.rfc;
    document.getElementById("txtDireccion").value = pr.direccion;
    document.getElementById("txtEmail").value = pr.email;
    document.getElementById("txtTelFijo").value = pr.telefonoFijo;
    document.getElementById("txtTelMovil").value = pr.telefonoMovil;
    
    setDetalleVisible(true);
}

export function setDetalleVisible(valor) {
    if (valor === true) {
        document.getElementById("divListaProveedores").style.display = '';
        document.getElementById("divDetalleProveedor").style.display = '';
    } else {
        document.getElementById("divDetalleProveedor").style.display = 'none';
        document.getElementById("divListaProveedores").style.display = '';
    }
}

export function cerrarDetalle() {
    limpiarFormulario();
    setDetalleVisible(false);
}

function limpiarFormulario() {
    document.getElementById("txtIdProveedor").value = "";
    document.getElementById("txtNombre").value = "";
    document.getElementById("txtRazonSocial").value = "";
    document.getElementById("txtRFC").value = "";
    document.getElementById("txtDireccion").value = "";
    document.getElementById("txtEmail").value = "";
    document.getElementById("txtTelFijo").value = "";
    document.getElementById("txtTelMovil").value = "";
}