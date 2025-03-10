<script>
    $("#formModificarProducto").validate({
        submitHandler: function () {

            let datos = {
                uuid: $("#uuidModificado").val(),
                descripcion: $("#descripcionModificada").val(),
                codigo: $("#codigoModificado").val(),
                precio: $("#precioModificado").val(),
                existencias: $("#existenciasModificado").val(),
            };

            $.ajax({
                url: backend + "/producto/modificarProducto",
                type: "post",
                datatype: "json",
                data: JSON.stringify(datos),
                contentType: 'application/json',
                xhrFields: { withCredentials: true },
                success: function (respuesta) {
                    console.log(respuesta);

                    if (respuesta.exito == true) {
                        $("#modalModificarProducto").modal("hide");
                        consultarProductos();
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
                },
            });


        }
    });

    function modificarProducto() {
        $("#formModificarProducto").submit();
    }

</script>



<form class="p-2 was-validated" id="formModificarProducto">
    <fieldset>
        <input type="hidden" id="uuidModificado" name="uuidModificado">
        <div class="form-group">
            <label for="descripcionModificada" class="form-label fw-bold mt-2">Descripción </label>
            <input type="text" class="form-control" id="descripcionModificada" name="descripcionModificada"
                placeholder="Ej. Pepsi 1l" required>
        </div>

        <div class="form-group">
            <label for="codigoModificado" class="form-label fw-bold mt-4">Código </label>
            <input type="text" class="form-control" id="codigoModificado" name="codigoModificado"
                placeholder="Escribe el código del producto" minlength="3" required>
        </div>

        <div class="form-group">
            <label for="precioModificado" class="form-label fw-bold mt-4">Precio </label>
            <input type="number" class="form-control" id="precioModificado" name="precioModificado"
                placeholder="Ingrese el precio " min="0" required>
        </div>

        <div class="form-group">
            <label for="existenciasModificado" class="form-label fw-bold mt-4">Existencias </label>
            <input type="number" class="form-control" id="existenciasModificado" name="existenciasModificado"
                placeholder="Escribe el numero de existencias" min="0" required>
        </div>

    </fieldset </form>