async function cargarModuloInicio() {
    let contenido = `
        <div class="container-fluid">
            <div class="text-center py-4">
                <i class="fa-solid fa-basket-shopping fa-6x mb-4" style="color: #ff7a00; opacity: 0.8;"></i>
                <h2 class="fw-bold">Sistema de Gestión FruitStore</h2>
                <p>Seleccione una opción del menú superior para comenzar.</p>
            </div>
        </div>
    `;
    document.getElementById("divPrincipal").innerHTML = contenido;
    window.cm = null;
}

async function cargarModuloCategorias() {
    let url = "categoria.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;

    cm = await import('./categorias.js');
    window.cm = cm;
    cm.inicializar();
}

async function cargarModuloProductos() {
    let url = "producto.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;

    cm = await import('./productos.js');
    window.cm = cm;
    cm.inicializar();
}

async function cargarModuloProveedores() {
    let url = "proveedor.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;

    cm = await import('./proveedores.js');
    window.cm = cm;
    cm.inicializar();
}

async function cargarModuloVendedores() {
    let url = "vendedor.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;

    cm = await import('./vendedores.js');
    window.cm = cm;
    cm.inicializar();
}

loadLocalUser();