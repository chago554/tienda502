<script>

    $(function () {
        consultarProductos();
    });


    function consultarProductos() {
        $.ajax({
            url: backend + "/producto/listar",
            type: "post",
            dataType: "json",
            xhrFields: { withCredentials: true },
            success: function (respuesta) {
                console.log(respuesta);
                cargarProductos(respuesta);
            }
        });
    }

    function cargarProductos(productos) {
        var body = '';
        $.each(productos, function (i, producto) {
            body += '<tr class="table-' + ((i % 2 == 0) ? 'success' : 'secondary') + '">' +

                '<td scope="row">' + producto.codigo + '</td>' +
                '<td scope="row">' + producto.descripcion + '</td>' +
                '<td scope="row">$' + producto.precio + '</td>' +
                '<td scope="row">' + producto.existencias + '</td>' +
                '<td scope="row"><a class="bi bi-pencil me-3" href="#" onclick="buscarProducto(\'' + producto.uuid + '\');"><a class="bi bi-trash3-fill" onclick="eliminarProducto(\'' + producto.uuid + '\');"  href="#" ></a>' +
                '</tr>';
        });
        $('#tablaProductos > tbody').html(body);
    }

    function eliminarProducto(producto) {
        let confirmar = confirm("¿Deseas eliminar a este producto?");
        if (confirmar) {
            $.ajax({
                url: backend + "/producto/eliminarProducto",
                type: "post",
                datatype: "json",
                data: JSON.stringify(producto),
                contentType: 'application/json',
                xhrFields: { withCredentials: true },
                success: function (respuesta) {
                    console.log(respuesta);

                    if (respuesta === "¡Producto eliminado exitosamente!") {
                        $.gritter.add({
                            title: respuesta,
                            sticky: false,
                            image: "/images/correcto.png",
                        });
                    } else {
                        $.gritter.add({
                            title: respuesta,
                            sticky: false,
                            image: "/images/cancelar.png",
                        });
                    }

                    consultarProductos();
                }
            });
        }
    }

    function buscarProducto(uuid) {
        $.ajax({
            url: backend + "/producto/buscarProducto",
            type: "post",
            dataType: "json",
            data: JSON.stringify(uuid),
            contentType: 'application/json',
            xhrFields: { withCredentials: true },
            success: function (respuesta) {
                console.log(respuesta);
                $("#divModificarProducto").load("productos/modificarProducto.jsp", function () {
                    $("#modalModificarProducto").modal("show");
                    cargarUnProducto(respuesta);
                });
            }
        });
    }

    function cargarUnProducto(producto) {
        $("#uuidModificado").val(producto.uuid);
        $("#codigoModificado").val(producto.codigo);
        $("#descripcionModificada").val(producto.descripcion);
        $("#precioModificado").val(producto.precio);
        $("#existenciasModificado").val(producto.existencias);
    }

</script>

<!-- label de bienvenida -->
<div class="row">
    <div class="col m-5 text-dark">
        <h1 class="fw-bold text-center text-uppercase text-dark ">Gestion de Productos</h1>


        <h5 class="fw-bold mt-5 mb-5 text-center text-uppercase text-dark ">¿Qué desea hacer hoy?</h5>
        <button type="button" class="btn btn-success"
            onclick='$("#divNuevoProducto").load("productos/nuevoProducto.jsp")' data-bs-toggle="modal"
            data-bs-target="#modalNuevoProducto">Crear producto <i class="bi bi-plus-circle"></i></button>
        <hr><br>


        <table class="table table-hover " id="tablaProductos">
            <thead>
                <tr class="table-dark">
                    <th scope="col">Código</th>
                    <th scope="col">Descripción</th>
                    <th scope="col">Precio</th>
                    <th scope="col">Existencias</th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>


    </div>
</div>


<!-- Modal nuevo producto -->
<div class="modal fade modal-dialog-scrollable" id="modalNuevoProducto" data-bs-backdrop="static"
    data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="tituloModalAlerta">Crear producto </h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="divNuevoProducto">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" onclick="crearProducto();">Guardar producto</button>
            </div>
        </div>
    </div>
</div>


<!-- Modal modificar producto -->
<div class="modal fade modal-dialog-scrollable" id="modalModificarProducto" data-bs-backdrop="static"
    data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="tituloModalAlerta">Modificar producto </h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="divModificarProducto">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" onclick="modificarProducto();">Guardar producto</button>
            </div>
        </div>
    </div>
</div>