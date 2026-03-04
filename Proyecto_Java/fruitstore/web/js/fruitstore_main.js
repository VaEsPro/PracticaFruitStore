async function cargarModuloCategorias(){
    let url = "categoria.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;
    
    cm = await import('./categorias.js');
    window.cm = cm;
    cm.inicializar();
}

async function cargarModuloProductos(){
    let url = "producto.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;
    
    cm = await import('./productos.js');
    window.cm = cm;
    cm.inicializar();
}

async function cargarModuloProveedores(){
    let url = "proveedor.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;
    
    cm = await import('./proveedores.js');
    window.cm = cm;
    cm.inicializar();
}

async function cargarModuloVendedores(){
    let url = "vendedor.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;
    
    cm = await import('./vendedores.js');
    window.cm = cm;
    cm.inicializar();
}

loadLocalUser();