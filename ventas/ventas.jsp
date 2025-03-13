<script>
    $(function () {
        $("#detalleVenta").load("ventas/detalle.jsp");
        $("#cajaCodigo").focus();
    });

    function buscarProducto(codigo) {
        if (codigo.length >= 3) {
            $.ajax({
                url: backend + "/ventas/buscarProducto",
                type: 'post',
                datatype: 'json',
                data: codigo,
                contentType: 'application/json',
                xhrFields: { withCredentials: true },

                success: function (respuesta) {
                    console.log(respuesta);


                    if (respuesta.exito == true) {
                        $("#cajaCodigo").val('');
                        $("#detalleVenta").load("ventas/detalle.jsp");
                    } else {
                        $("#cajaCodigo").val('');
                        $("#detalleVenta").load("ventas/detalle.jsp");
                        $.gritter.add({
                            title: respuesta.mensaje,
                            sticky: false,
                            image: "/images/cancelar.png",
                        });

                    }

                }
            });
        }
    }
</script>

<form class="p-5">
    <fieldset>
        <legend>Ventas</legend>
        <div class="form-group">
            <label for="username" class="form-label mt-0">Código del producto</label>
            <input type="text" class="form-control" id="cajaCodigo" placeholder="Captura el código del producto..."
                onkeyup="buscarProducto(this.value);">
        </div>
    </fieldset </form>

    <div id="detalleVenta" class="pt-5"></div>