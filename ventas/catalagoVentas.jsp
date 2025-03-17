<script>
    $(function () {
        verVentas();
    });

    //listar ventas
    function verVentas() {
        $.ajax({
            url: backend + "/ventas/listarVentas",
            type: 'post',
            datatype: 'json',
            xhrFields: { withCredentials: true },
            success: function (respuesta) {
                mostrarVentas(respuesta);
            }
        });
    }

    //mostrar ventas
    function mostrarVentas(venta) {
        var body = '';
        let fecha;
        let hora;

        $.each(venta, function (i, venta) {
            fechaHora = venta.fecha;
            fecha = fechaHora.split('T')[0];
            hora = fechaHora.split('T')[1].split('.')[0];

            body += '<tr class="table-' + ((i % 2 == 0) ? 'success' : 'secondary') + '">' +
                '<td scope="row" class=" w-25">' + fecha + '</td>' +
                '<td scope="row" class=" w-25">' + hora + '</td>' +
                '<td scope="row" class=" w-25">$' + venta.total + '</td>' +
                '<td scope="row" class=" w-100">  <a class="btn btn-outline-dark me-5" href="#" onclick="verDetalles(\'' + venta.uuid + '\');"  >Ver detalles </a></td>' +
                '</tr>';
        });
        $('#tablaVentas > tbody').html(body);

    }

    //ver detalles
    function verDetalles(venta) {
        $('#uuidVentaAModificar').val(venta);
        $.ajax({
            url: backend + "/detalleVenta/verDetalles",
            type: "post",
            dataType: "json",
            data: JSON.stringify(venta),
            contentType: 'application/json',
            xhrFields: { withCredentials: true },
            success: function (respuesta) {
                mostrarDetallesVenta(respuesta);
                $("#modalModificarVenta").modal("show");
            }
        });
    }

    //mostrar los detalles en la tabla
    function mostrarDetallesVenta(detalles) {
        var body = '';
        $.each(detalles, function (i, detalle) {
            body += '<tr class="table-' + ((i % 2 == 0) ? 'success' : 'secondary') + '">' +
                '<td scope="row">' + detalle.producto.descripcion + '</td>' +
                '<td scope="row">$' + detalle.producto.precio + '</td>' +
                '<td scope="row" class="d-flex"><input type="number" class="form-control text-center w-50" value=' + detalle.cantidad + ' min="0" onchange= "modificarDetalleVenta(\'' + detalle.uuid + '\', this.value );"> <button class="btn btn-info txt-dark hover" onclick="sumar5(\'' + detalle.uuid + '\', \'' + detalle.cantidad + '\');" >+ 5</button></td>' +
                '<td scope="row">$' + detalle.subtotal + '</td>' +
                '<td scope="row"><a class="bi bi-trash3-fill" onclick="eleminarDetalleVenta(\'' + detalle.uuid + '\');"  href="#" ></a>' +
                '</tr>';
            $('#celdaTotalModificarVenta').html("$" + (parseFloat(detalle.total) || 0));
        });
        $('#tablaModificarVenta > tbody').html(body);
    }

    //eliminar venta
    function eliminarVenta(uuid) {

        $.ajax({
            url: backend + "/detalleVenta/eliminarVenta",
            type: 'post',
            dataType: "json",
            data: JSON.stringify(uuid),
            contentType: 'application/json',
            xhrFields: { withCredentials: true },
            success: function (respuesta) {
                console.log(respuesta);

                if (respuesta.exito == true) {
                    verVentas();
                    $("#modalModificarVenta").modal("hide");
                    $.gritter.add({
                        title: respuesta.mensaje,
                        sticky: false,
                        image: "/images/correcto.png",
                    });
                } else {
                    $.gritter.add({
                        title: respuesta.mensaje,
                        sticky: false,
                        image: "/images/cancelar.png",
                    });
                }

            }
        });


    }



    //eliminar detalle de venta
    function eleminarDetalleVenta(uuidDetalle) {
        console.log(uuidDetalle);

        let datos = {
            detalleVentaDTO: {
                uuid: uuidDetalle,
            },
            ventaDTO: {
                uuid: $("#uuidVentaAModificar").val()
            }
        };


        $.ajax({
            url: backend + "/detalleVenta/eliminarDetalle",
            type: 'post',
            datatype: 'json',
            data: JSON.stringify(datos),
            contentType: 'application/json',
            xhrFields: { withCredentials: true },

            success: function (respuesta) {
                $('#celdaTotalModificarVenta').html("$" + (parseFloat(respuesta.total) || 0));
                if (respuesta.exito == true) {
                    console.log(respuesta);
                    verDetalles($("#uuidVentaAModificar").val());
                    verVentas();
                    $.gritter.add({
                        title: respuesta.mensaje,
                        sticky: false,
                        image: "/images/correcto.png",
                    });
                } else {
                    $("#cajaCodigo").val('');
                    $.gritter.add({
                        title: respuesta.mensaje,
                        sticky: false,
                        image: "/images/cancelar.png",
                    });
                }

            }
        });
    }

    //cantidad de productos vendidos update
    function modificarDetalleVenta(uuidDetalle, nuevaCantidad) {
        if (nuevaCantidad != "" && nuevaCantidad > 0) {
            let datos = {
                detalleVentaDTO: {
                    uuid: uuidDetalle,
                    cantidad: nuevaCantidad
                },
                ventaDTO: {
                    uuid: $("#uuidVentaAModificar").val()
                }
            };

            $.ajax({
                url: backend + "/detalleVenta/modificarDetalleVenta",
                type: 'post',
                dataType: 'json',
                data: JSON.stringify(datos),
                contentType: 'application/json',
                xhrFields: { withCredentials: true },
                success: function (respuesta) {
                    if (respuesta.exito == true) {
                        console.log(respuesta);
                        verDetalles($("#uuidVentaAModificar").val());
                        verVentas();
                        $.gritter.add({
                            title: respuesta.mensaje,
                            sticky: false,
                            image: "/images/correcto.png",
                        });
                    } else {
                        $("#cajaCodigo").val('');
                        $.gritter.add({
                            title: respuesta.mensaje,
                            sticky: false,
                            image: "/images/cancelar.png",
                        });
                    }
                }
            });
        } else {

            $.gritter.add({
                title: "¡Ingresa un valor válido!",
                sticky: false,
                image: "/images/cancelar.png",
            });
        }
    }

    //sumar 5 productos
    function sumar5(uuid, cantidadActual) {
        let nuevaCantidad = parseInt(cantidadActual) + 5;
        modificarDetalleVenta(uuid, nuevaCantidad);
        verVentas();
    }


</script>

<table class="table table-hover " id="tablaVentas">
    <thead>
        <tr class="table-dark">
            <th scope="col">Fecha</th>
            <th scope="col">Hora</th>
            <th scope="col">Total</th>
            <th scope="col"></th>
        </tr>
    </thead>

    <tbody>

    </tbody>

    <tfoot>
        <tr class="table-dark">
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
    </tfoot>
</table>

<!-- Modal modificar venta -->
<div class="modal fade modal-dialog-scrollable modal-xl" id="modalModificarVenta" data-bs-backdrop="static"
    data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="tituloModalAlerta">Modificar venta </h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="divModificarVenta">
                <table class="table table-hover " id="tablaModificarVenta">
                    <input type="text" id="uuidVentaAModificar" hidden readonly>
                    <thead>
                        <tr class="table-dark">
                            <th scope="col">Descripción</th>
                            <th scope="col">Precio</th>
                            <th scope="col">Cantidad</th>
                            <th scope="col">Subtotal</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>

                    <tbody>

                    </tbody>

                    <tfoot>
                        <tr class="table-dark">
                            <th scope="col"></th>
                            <th scope="col"></th>
                            <th scope="col">Total</th>
                            <th scope="col" id="celdaTotalModificarVenta"></th>
                            <th scope="col"></th>
                        </tr>
                    </tfoot>
                </table>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secundary" data-bs-dismiss="modal">Salir</button>
                <button type="button" class="btn btn-danger"
                    onclick="eliminarVenta(document.getElementById('uuidVentaAModificar').value);">Eliminar
                    venta</button>

            </div>
        </div>
    </div>
</div>