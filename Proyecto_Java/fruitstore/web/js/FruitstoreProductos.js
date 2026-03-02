let listaProductos = [];
let listaCategorias = [];

export async function inicializar(){
    cargarListaProductos();
    cargarListaCategorias();
}

async function cargarListaProductos(){
    let url = '../api/producto/getAll?filtro=';
    
    let resp = await fetch(url);
    
    listaProductos = await resp.json();
    
    refrescarTablaProductos();
}

function refrescarTablaProductos(){
    let contenido = "";
    for (let i = 0; i < listaProductos.length; i++){
        contenido += '<tr>' +
                '<td>' + listaProductos[i].nombre + '</td>' +
                '<td>' + listaProductos[i].categoria.nombre + '</td>' +
                '<td>' + listaProductos[i].precioVenta + '</td>' +
                '<td>' + listaProductos[i].status + '</td>' +
                '<td>' + '<a href="#" onclick="cm.mostrarDetalles('+ i +');">Ver Detalles</a>' + '</td' +
                '</tr>';
    }
    document.getElementById("tbodyProductos").innerHTML = contenido;
}

function fillComboBoxCategorias(){
    let contenido = '';
    for (let i = 0; listaCategorias.lenght; i++){
        contenido += '<option value=>"' + listaCategorias[i].id + '">' +
                     '</option>';
    }
    document.getElementById('cmbCategoria').innerHTML = contenido;
}

export async function save(){
    let url = 'api/producto/save';
    let producto = new Object();
    let params = null; // Parámetros del servicio
    let resp = null; // Respuesta del servicio
    let datos = null; // Datos JSON de respuesta
    let ctype = 'application/x-www-form-urlencoded;charset=UTF-8';
    
    // Revisamos si hay un ID de producto:
    if (document.getElementById("txtIdProducto").value.trim().length === 0){
        producto.id = 0;
    } else {
        producto.id = parseInt(document.getElementById("txtIdProducto").value.trim());
    }
    
    producto.nombre = document.getElementById("txtNomProducto").value;
    producto.precioCompra = parseFloat(document.getElementById("txtPrecioCompra").value);
    producto.precioVenta = parseFloat(document.getElementById("txtPrecioVenta").value);
    
    producto.categoria = new Object();
    producto.categoria.id = parseInt(document.getElementById("cmbCategoria").value);
    
    params = {
                producto : JSON.stringify(producto)
             };
    resp = await fetch( url,
                        {   method:"POST",
                            headers:{'Content-Type': ctype},
                            body: new URLSearchParams(params)
                        });
    datos = await resp.json();
    
    if (datos.error != null){
        Swal.fire("", "Error al guardar Producto.", "warning");
        return;
    }
    
    if (datos.exception != null){
        Swal.fire("", datos.exception, "danger");
        return;
    }
    
    // Si llegamos hasta aqui, significa que todo salió bien :)
    document.getElementById("txtIdProducto").value = datos.id;
    Swal.fire('Movimiento realizado.',
              'Datos de Producto guardados.',
              'success');
    consultarProductos();
}