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

            }
        });
    }


    function mostrarCarrito(venta) {
        var body = '';
        $.each(venta.detalles, function (i, detalles) {
            body += '<tr class="table-' + ((i % 2 == 0) ? 'success' : 'secondary') + '">' +
               
                '<td scope="row">'+ detalles.producto.descripcion +'</td>' +
                '<td scope="row">$'+ detalles.producto.precio +'</td>' +
                '<td scope="row">'+ detalles.cantidad +'</td>' +
                '<td scope="row">$'+ detalles.subtotal +'</td>' +

                '</tr>';
        });
        $('#tablaCarrito > tbody').html(body);
        $('#celdaTotal').html("$"+ venta.total); 
    }

</script>


<table class="table table-hover " id="tablaCarrito">
    <thead>
        <tr class="table-dark">
            <th scope="col">Descripción</th>
            <th scope="col">Precio</th>
            <th scope="col">Cantidad</th>
            <th scope="col">Subtotal</th>
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
        </tr>
    </tfoot>
</table>

