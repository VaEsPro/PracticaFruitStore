let listaVendedores = [];

export async function inicializar(){
    setDetalleVisible(false);
    cargarListaVendedores();
}

async function cargarListaVendedores(){
    let url = '/fruitstore/api/vendedor/getAll';
    let resp = await fetch(url);
    listaVendedores = await resp.json();
    refrescarTablaVendedores();
}

function refrescarTablaVendedores() {
    let contenido = '';
    for (let i = 0; i < listaVendedores.length; i++) {
        let v = listaVendedores[i];
        contenido += '<tr>';
        contenido += '<td>' + v.nombre + '</td>';
        contenido += '<td>' + v.fechaNacimiento + '</td>';
        contenido += '<td>' + v.genero + '</td>';
        contenido += '<td>' + v.calle + '</td>';
        contenido += '<td>' + v.numeroExt + '</td>';
        contenido += '<td>' + v.numeroInt + '</td>';
        contenido += '<td>' + v.colonia + '</td>';
        contenido += '<td>' + v.cp + '</td>';
        contenido += '<td>' + v.ciudad + '</td>';
        contenido += '<td>' + v.estado + '</td>';
        contenido += '<td>' + v.pais + '</td>';
        contenido += '<td>' + v.telefono + '</td>';
        contenido += '<td>' + v.fechaAlta + '</td>';
        contenido += '<td>' + v.email + '</td>';
        contenido += '<td>' + '<a href="#" onclick="cm.mostrarDetalleVendedor(' + i + ')">Ver Detalle</a>' + '</td>';
        contenido += '</tr>';
    }
    document.getElementById("tbodyVendedores").innerHTML = contenido;
}

export async function save() {
    let vendedor = new Object();

    if (document.getElementById("txtIdVendedor").value.trim().length === 0)
        vendedor.id = 0;
    else
        vendedor.id = parseInt(document.getElementById("txtIdVendedor").value.trim());

    vendedor.nombre = document.getElementById("txtNombre").value;
    vendedor.fechaNacimiento = document.getElementById("txtFechaNacimiento").value;
    vendedor.genero = document.getElementById("cmbGenero").value;
    vendedor.telefono = document.getElementById("txtTelefono").value;
    vendedor.email = document.getElementById("txtEmail").value;
    vendedor.fechaAlta = document.getElementById("txtFechaAlta").value;
    vendedor.calle = document.getElementById("txtCalle").value;
    vendedor.numeroExt = document.getElementById("txtNumeroExterior").value;
    vendedor.numeroInt = document.getElementById("txtNumeroInterior").value;
    vendedor.colonia = document.getElementById("txtColonia").value;
    vendedor.cp = document.getElementById("txtCodigoPostal").value;
    vendedor.ciudad = document.getElementById("txtCiudad").value;
    vendedor.estado = document.getElementById("txtEstado").value;
    vendedor.pais = document.getElementById("txtPais").value;

    let vendedorJSON = JSON.stringify(vendedor);

    // Al igual que en productos, enviamos por QueryParam codificado en la URL
    let url = '../api/vendedor/save?vendedor=' + encodeURIComponent(vendedorJSON);

    let resp = await fetch(url, {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' }
    });

    let datos = await resp.json();

    if (datos.error != null) {
        Swal.fire("", "Error al guardar Vendedor.", "warning");
        return;
    }

    if (datos.exception != null) {
        Swal.fire("", datos.exception, "error");
        return;
    }

    document.getElementById("txtIdVendedor").value = datos.id;
    Swal.fire('Movimiento realizado.', 'Datos del Vendedor guardados.', 'success');
    cargarListaVendedores();
}

export async function deletE() {
    let id = 0;
    let idVal = document.getElementById("txtIdVendedor").value.trim();

    if (idVal.length === 0) {
        Swal.fire("", "Debe seleccionar un vendedor para eliminar.", "warning");
        return;
    }

    id = parseInt(idVal);
    let url = '../api/vendedor/delete?id=' + id;

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

    Swal.fire('Movimiento realizado.', 'Vendedor eliminado correctamente.', 'success');
    
    cargarListaVendedores();
    limpiarFormulario();
}

export function mostrarDetalleVendedor(posicion) {
    let v = listaVendedores[posicion];

    document.getElementById("txtIdVendedor").value = v.id;
    document.getElementById("txtNombre").value = v.nombre;
    document.getElementById("txtFechaNacimiento").value = v.fechaNacimiento;
    document.getElementById("cmbGenero").value = v.genero;
    document.getElementById("txtTelefono").value = v.telefono;
    document.getElementById("txtEmail").value = v.email;
    document.getElementById("txtFechaAlta").value = v.fechaAlta;
    document.getElementById("txtCalle").value = v.calle;
    document.getElementById("txtNumeroExterior").value = v.numeroExt;
    document.getElementById("txtNumeroInterior").value = v.numeroInt;
    document.getElementById("txtColonia").value = v.colonia;
    document.getElementById("txtCodigoPostal").value = v.cp;
    document.getElementById("txtCiudad").value = v.ciudad;
    document.getElementById("txtEstado").value = v.estado;
    document.getElementById("txtPais").value = v.pais;

    setDetalleVisible(true);
}

export function setDetalleVisible(valor) {
    if (valor === true) {
        document.getElementById("divCatalogoVendedores").style.display = '';
        document.getElementById("divDetalleVendedor").style.display = '';
    } else {
        document.getElementById("divDetalleVendedor").style.display = 'none';
        document.getElementById("divCatalogoVendedores").style.display = '';
    }
}

export function cerrarDetalle() {
    limpiarFormulario();
    setDetalleVisible(false);
}

function limpiarFormulario() {
    document.getElementById("txtIdVendedor").value = "";
    document.getElementById("txtNombre").value = "";
    document.getElementById("txtFechaNacimiento").value = "";
    document.getElementById("cmbGenero").value = "";
    document.getElementById("txtTelefono").value = "";
    document.getElementById("txtEmail").value = "";
    document.getElementById("txtFechaAlta").value = "";
    document.getElementById("txtCalle").value = "";
    document.getElementById("txtNumeroExterior").value = "";
    document.getElementById("txtNumeroInterior").value = "";
    document.getElementById("txtColonia").value = "";
    document.getElementById("txtCodigoPostal").value = "";
    document.getElementById("txtCiudad").value = "";
    document.getElementById("txtEstado").value = "";
    document.getElementById("txtPais").value = "";
}