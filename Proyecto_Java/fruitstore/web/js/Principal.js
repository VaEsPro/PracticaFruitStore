async function cargarModuloCategorias(){
    let url = "categoria.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;
    
    let cm = null;
    
    cm = await import('./FruitstoreCategorias.js');
    cm.inicializar();
}

async function cargarModuloProductos(){
    let url = "producto.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;
    
    let cm = null;
    
    cm = await import('./FruitstoreProductos.js');
    cm.inicializar();
}

async function cargarModuloProveedores(){
    let url = "proveedor.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;
    
    let cm = null;
    
    cm = await import('./FruitstoreProveedores.js');
    cm.inicializar();
}

async function cargarModuloVendedores(){
    let url = "vendedor.html";
    let respuesta = await fetch(url);
    let contenido = await respuesta.text();
    document.getElementById("divPrincipal").innerHTML = contenido;
    
    let cm = null;
    
    cm = await import('./FruitstoreVendedores.js');
    cm.inicializar();
}