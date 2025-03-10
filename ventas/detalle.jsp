<script>
    $(function () {
        cargaCarrito();

    });

    //cargar carrito
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

    //mostrar carrito
    function mostrarCarrito(venta) {
        var body = '';
        let contador = 0;
        $.each(venta.detalles, function (i, detalles) {
            body += '<tr class="table-' + ((i % 2 == 0) ? 'success' : 'secondary') + '">' +

                '<td scope="row">' + detalles.producto.descripcion + '</td>' +
                '<td scope="row">$' + detalles.producto.precio + '</td>' +
                '<td scope="row"><input type="number" class="form-control text-center w-50" value=' + detalles.cantidad + ' min="0" onchange= "modificarDetalle(\'' + detalles.uuid + '\', this.value );" ></td>' +
                '<td scope="row">$' + detalles.subtotal + '</td>' +
                '<td scope="row"><a class="bi bi-trash3-fill" href="#" onclick="eliminarDetalle(\'' + detalles.uuid + '\');"></a></td>' +
                '</tr>';
            contador = contador + 1;
        });
        $('#tablaCarrito > tbody').html(body);
        $('#celdaTotal').html("$" + venta.total);
    }

    //limpiar venta
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

    //realizar venta
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

    //imprimir
    function imprimir() {
        window.print();
    }

    //eliminar detalle de venta
    function eliminarDetalle(uuid) {
        $.ajax({
            url: backend + "/ventas/eliminarDetalle",
            type: 'post',
            datatype: 'json',
            data: JSON.stringify(uuid),
            contentType: 'application/json',
            xhrFields: { withCredentials: true },

            success: function (respuesta) {
                console.log(respuesta);
                $("#detalleVenta").load("ventas/detalle.jsp");
            }
        });
    }

    //cantidad de productos vendidos update
    function modificarDetalle(uuid, nuevaCantidad) {
        if (nuevaCantidad != "" && nuevaCantidad > 0) {

            let datos = {
                uuid: uuid,
                cantidad: nuevaCantidad
            };

            $.ajax({
                url: backend + "/ventas/modificarDetalle",
                type: 'post',
                dataType: 'json',
                data: JSON.stringify(datos),
                contentType: 'application/json',
                xhrFields: { withCredentials: true },
                success: function (respuesta) {
                    console.log(respuesta);
                    $("#detalleVenta").load("ventas/detalle.jsp");
                }

            });
        }else{

          $.gritter.add({
            title: "¡Ingresa un valor válido!",
            sticky: false,
            image: "/images/cancelar.png",
          });
        }

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