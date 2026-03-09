let listaProductos = [];
let listaCategorias = [];

export async function inicializar() {
    setDetalleVisible(false);
    await cargarCategorias();
    await cargarListaProductos();
}

async function cargarCategorias() {
    let url = '/fruitstore/api/categoria/getAll';
    let resp = await fetch(url);
    listaCategorias = await resp.json();
    let contenido = '<option value="0">Seleccione una categoría...</option>';
    for (let i = 0; i < listaCategorias.length; i++) {
        if (listaCategorias[i].status === 1) {
            contenido += '<option value="' + listaCategorias[i].id + '">' + listaCategorias[i].nombre + '</option>';
        }
    }
    document.getElementById("cmbCategoria").innerHTML = contenido;
}

async function cargarListaProductos() {
    let url = '/fruitstore/api/producto/getAll';
    let resp = await fetch(url);
    listaProductos = await resp.json();
    refrescarTablaProductos();
}

function refrescarTablaProductos() {
    let contenido = '';
    for (let i = 0; i < listaProductos.length; i++) {
        let p = listaProductos[i];
        
        let categoriaActiva = false;
        for (let c = 0; c < listaCategorias.length; c++) {
            if (p.categoria && listaCategorias[c].id === p.categoria.id) {
                if (listaCategorias[c].status === 1) {
                    categoriaActiva = true;
                }
                break;
            }
        }
        if (categoriaActiva === false) {
            continue;
        }
        
        let nombreCategoria = (p.categoria && p.categoria.nombre) ? p.categoria.nombre : 'Sin categoría';
        let textoStatus = (p.status === 1 || p.estatus === 1) ? '<span class="text-success fw-bold">Activo</span>' : '<span class="text-danger fw-bold">Inactivo</span>';

        contenido += '<tr>';
        contenido += '<td>' + p.nombre + '</td>';
        contenido += '<td>' + nombreCategoria + '</td>';
        contenido += '<td>' + p.precioCompra + '</td>';
        contenido += '<td>' + p.precioVenta + '</td>';
        contenido += '<td>' + p.existencia + '</td>';
        contenido += '<td>' + textoStatus + '</td>';
        contenido += '<td class="text-center"><button class="btn btn-outline-primary btn-sm" onclick="cm.mostrarDetalleProducto(' + i + ')">Ver Detalle</button></td>';
        contenido += '</tr>';
    }
    document.getElementById("tbodyProductos").innerHTML = contenido;
}

export async function save() {
    let producto = new Object();

    // Captura de datos del formulario
    if (document.getElementById("txtIdProducto").value.trim().length === 0)
        producto.id = 0;
    else
        producto.id = parseInt(document.getElementById("txtIdProducto").value.trim());

    producto.nombre = document.getElementById("txtNomProducto").value;
    producto.precioCompra = parseFloat(document.getElementById("txtPrecioCompra").value);
    producto.precioVenta = parseFloat(document.getElementById("txtPrecioVenta").value);
    producto.existencia = parseFloat(document.getElementById("txtExistencias").value);

    producto.categoria = new Object();
    producto.categoria.id = parseInt(document.getElementById("cmbCategoria").value);

    let estatusVal = document.getElementById("txtEstatus").value.trim();
    producto.status = (estatusVal === "") ? 0 : parseInt(estatusVal);
    
    let productoJSON = JSON.stringify(producto);

    let url = '../api/producto/save?producto=' + encodeURIComponent(productoJSON);

    let resp = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
    });

    let datos = await resp.json();

    if (datos.error != null) {
        Swal.fire("", "Error al guardar Producto.", "warning");
        return;
    }

    if (datos.exception != null) {
        Swal.fire("", datos.exception, "error");
        return;
    }

    document.getElementById("txtIdProducto").value = datos.id;
    Swal.fire('Movimiento realizado.', 'Datos de Productos guardados.', 'success');

    cargarListaProductos();
    limpiarFormulario();
    setDetalleVisible(false);
}

export async function deletE() {
    let id = 0;
    let idVal = document.getElementById("txtIdProducto").value.trim();

    if (idVal.length === 0) {
        Swal.fire("", "Debe seleccionar un producto para eliminar.", "warning");
        return;
    }

    id = parseInt(idVal);
    let url = '../api/producto/delete?id=' + id;

    let resp = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'}
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

    Swal.fire('Movimiento realizado.', 'Producto eliminado correctamente.', 'success');

    cargarListaProductos();
    limpiarFormulario();
    setDetalleVisible(false);
}

export function mostrarDetalleProducto(posicion) {
    let p = listaProductos[posicion];
    document.getElementById("txtIdProducto").value = p.id;
    document.getElementById("txtNomProducto").value = p.nombre;
    document.getElementById("cmbCategoria").value = p.categoria.id;
    document.getElementById("txtPrecioCompra").value = p.precioCompra;
    document.getElementById("txtPrecioVenta").value = p.precioVenta;
    document.getElementById("txtExistencias").value = p.existencia;
    if(p.status !== undefined){ document.getElementById("txtEstatus").value = p.status; }

    setDetalleVisible(true);
}

export function setDetalleVisible(valor) {
    if (valor === true) {
        document.getElementById("divFormProductos").style.display = '';
        document.getElementById("divTablaProductos").style.display = 'none';
    } else {
        document.getElementById("divTablaProductos").style.display = '';
        document.getElementById("divFormProductos").style.display = 'none';
    }
}

export function cerrarDetalle() {
    limpiarFormulario();
    setDetalleVisible(false);
}

export function limpiarFormulario() {
    document.getElementById("txtIdProducto").value = "";
    document.getElementById("txtNomProducto").value = "";
    document.getElementById("cmbCategoria").value = 0;
    document.getElementById("txtPrecioCompra").value = "";
    document.getElementById("txtPrecioVenta").value = "";
    document.getElementById("txtExistencias").value = "";
    document.getElementById("txtEstatus").value = "";
}