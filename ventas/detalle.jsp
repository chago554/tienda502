<script>
    $(function () {
        cargaCarrito();

    });

    function cargaCarrito() {
        $.ajax({
            url: backend + "/ventas/cargaCarrito",
            type: 'post',
            datatype: 'json',
            xhrFields: { withCredentials: true },

            success: function (respuesta) {
                console.log(respuesta);
                mostrarCarrito(respuesta);
                if (!respuesta.realizada) {
                    $("#btnImprimir").hide();

                    if (respuesta.detalles.length > 0) {
                        $("#btnVenta").show();
                        $("#btnLimpiar").show();
                    } else {
                        $("#btnVenta").hide();
                        $("#btnLimpiar").hide();
                    }
                } else {
                    $("#btnImprimir").show();
                    $("#btnVenta").hide();

                }
            }
        });
    }
    function mostrarCarrito(venta) {
        var body = '';
        $.each(venta.detalles, function (i, detalles) {
            body += '<tr class="table-' + ((i % 2 == 0) ? 'success' : 'secondary') + '">' +

                '<td scope="row">' + detalles.producto.descripcion + '</td>' +
                '<td scope="row">$' + detalles.producto.precio + '</td>' +
                '<td scope="row">' + detalles.cantidad + '</td>' +
                '<td scope="row">$' + detalles.subtotal + '</td>' +
                '<td scope="row"><a class="bi bi-bag-x" href="#" onclick="eliminarDetalle(\'' + detalles.uuid + '\');"></a></td>' +
                '</tr>';
        });
        $('#tablaCarrito > tbody').html(body);
        $('#celdaTotal').html("$" + venta.total);
    }

    function limpiarVenta() {
        $.ajax({
            url: backend + "/ventas/limpiarVenta",
            type: 'post',
            datatype: 'json',
            xhrFields: { withCredentials: true },

            success: function (respuesta) {
                console.log(respuesta);
                cargaCarrito();
            }
        });
    }

    function realizarVenta() {
        $("#btnVenta").hide();

        $.ajax({
            url: backend + "/ventas/realizarVenta",
            type: 'post',
            datatype: 'json',
            xhrFields: { withCredentials: true },
            success: function (respuesta) {
                console.log(respuesta);

                $.gritter.add({
                    title: respuesta,
                    sticky: false,
                    image: '/images/correcto.png'
                });
                $("#detalleVenta").load("ventas/detalle.jsp");
                // limpiarVenta();
            }
        });
    }

    function imprimir() {
        window.print();
    }

    function eliminarDetalle(uuid) {
        $.ajax({
            url: backend + "/ventas/eliminarDetalle",
            type: 'post',
            datatype: 'json',
            data: JSON.stringify(uuid) ,
            contentType: 'application/json',
            xhrFields: { withCredentials: true },

            success: function (respuesta) {
                console.log(respuesta);
                $("#detalleVenta").load("ventas/detalle.jsp");
            }
        });
    }
</script>


<table class="table table-hover " id="tablaCarrito">
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
            <th scope="col" id="celdaTotal"></th>
            <th scope="col"></th>
        </tr>
    </tfoot>
</table>

<center>
    <button type="button" id="btnVenta" class="btn btn-success bi bi-cart-check-fill" onclick="realizarVenta();">
        Realizar venta</button>
    <button type="button" id="btnImprimir" class="btn btn-info bi bi-printer" onclick="imprimir();"> Imprimir
        ticket</button>
    <button type="button" id="btnLimpiar" class="btn btn-danger bi bi-stars" onclick="limpiarVenta();"> Limpiar
        venta</button>
</center>