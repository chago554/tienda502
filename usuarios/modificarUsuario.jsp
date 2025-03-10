<script>

    $("#formModificarUsuario").validate({
        submitHandler: function () {

            let datos = {
                uuid: $("#uuidUsuario").val(),
                nombre: $("#nombreModificado").val(),
                paterno: $("#paternoModificado").val(),
                materno: $("#maternoModificado").val(),
                username: $("#usernameModificado").val(),
            };

            $.ajax({
                url: backend + "/Usuarios/modificarUsuario",
                type: "post",
                datatype: "json",
                data: JSON.stringify(datos),
                contentType: 'application/json',
                xhrFields: { withCredentials: true },
                success: function (respuesta) {
                    console.log(respuesta);

                    if (respuesta.exito == true) {
                        $("#modalModificarUsuario").modal("hide");
                        consultarUsuarios();
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

    function modificarUsuario() {
        $("#formModificarUsuario").submit();
    }


</script>

<form class="p-2 was-validated" id="formModificarUsuario">
    <fieldset>
        <input type="hidden" id="uuidUsuario" name="uuidUsuario">
        <div class="form-group">
            <label for="nombreModificado" class="form-label fw-bold mt-2">Nombre(s) </label>
            <input type="text" class="form-control" id="nombreModificado" name="nombreModificado"
                placeholder="Escribe el nombre" required>
        </div>

        <div class="form-group">
            <label for="paternoModificado" class="form-label fw-bold mt-4">Apellido paterno </label>
            <input type="text" class="form-control" id="paternoModificado" name="paternoModificado"
                placeholder="Escribe el apellido paterno" required>
        </div>

        <!-- <label for="comboPerfilesModificar" class="form-label fw-bold mt-4">Selecciona un perfil</label>
        <div id="comboPerfilesModificar">
            
        </div> -->
        </select>

        <div class="form-group">
            <label for="maternoModificado" class="form-label fw-bold mt-4">Apellido materno</label>
            <input type="text" class="form-control" id="maternoModificado" name="materno"
                placeholder="Escribe el apellido materno" required>
        </div>

        <div class="form-group">
            <label for="usernameModificado" class="form-label fw-bold mt-4">Crear username </label>
            <input type="text" class="form-control" id="usernameModificado" name="username"
                placeholder="Escribe el username" required minlength="3">
        </div>

    </fieldset </form>