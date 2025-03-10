<script>
    $("#formNuevoProducto").validate({
        submitHandler: function () {

            let datos = {
                descripcion: $("#descripcion").val(),
                codigo: $("#codigo").val(),
                precio: $("#precio").val(),
                existencias: $("#existencias").val(),
            };

            $.ajax({
                url: backend + "/producto/crearProducto",
                type: "post",
                datatype: "json",
                data: JSON.stringify(datos),
                contentType: 'application/json',
                xhrFields: { withCredentials: true },
                success: function (respuesta) {
                    console.log(respuesta);

                    if (respuesta.exito == true) {
                        $("#modalNuevoProducto").modal("hide");
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

    function crearProducto() {
        $("#formNuevoProducto").submit();
    }


</script>

<form class="p-2 was-validated" id="formNuevoProducto">
    <fieldset>
        <div class="form-group">
            <label for="descripcion" class="form-label fw-bold mt-2">Descripción </label>
            <input type="text" class="form-control" id="descripcion" name="descripcion" placeholder="Ej. Pepsi 1l"
                required>
        </div>

        <div class="form-group">
            <label for="codigo" class="form-label fw-bold mt-4">Código </label>
            <input type="text" class="form-control" id="codigo" name="codigo"
                placeholder="Escribe el código del producto" minlength="3" required>
        </div>

        <div class="form-group">
            <label for="precio" class="form-label fw-bold mt-4">Precio </label>
            <input type="number" class="form-control" id="precio" name="precio" placeholder="Ingrese el precio " min="0"
                required>
        </div>

        <div class="form-group">
            <label for="existencias" class="form-label fw-bold mt-4">Existencias </label>
            <input type="number" class="form-control" id="existencias" name="existencias"
                placeholder="Escribe el numero de existencias" min="0" required>
        </div>

    </fieldset </form>