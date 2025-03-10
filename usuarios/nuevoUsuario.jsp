<script>


    $("#formNuevoUsuario").validate({
        submitHandler: function () {
            if (($("#password1").val()) === ($("#password2").val())) {

                let datos = {
                    nombre: $("#nombre").val(),
                    paterno: $("#paterno").val(),
                    materno: $("#materno").val(),
                    perfil: $("#comboPerfiles").val(),
                    password: $("#password1").val(),
                    username: $("#username").val()
                };

                $.ajax({
                    url: backend + "/Usuarios/crearUsuario",
                    type: "post",
                    datatype: "json",
                    data: JSON.stringify(datos),
                    contentType: 'application/json',
                    xhrFields: { withCredentials: true },
                    success: function (respuesta) {
                        console.log(respuesta);

                        if (respuesta.exito == true) {
                            $("#modalNuevoUsuario").modal("hide");
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

            } else {
                $.gritter.add({
                    title: "Contraseñas no coinciden",
                    sticky: false,
                    image: '/images/cancelar.png'
                });
            }
        }
    });

    function crearUsuario() {
        $("#formNuevoUsuario").submit();
    }


</script>

<form class="p-2 was-validated" id="formNuevoUsuario">
    <fieldset>
        <div class="form-group">
            <label for="nombre" class="form-label fw-bold mt-2">Nombre(s) </label>
            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Escribe el nombre" required>
        </div>

        <div class="form-group">
            <label for="paterno" class="form-label fw-bold mt-4">Apellido paterno </label>
            <input type="text" class="form-control" id="paterno" name="paterno"
                placeholder="Escribe el apellido paterno" required>
        </div>

        <div class="form-group">
            <label for="materno" class="form-label fw-bold mt-4">Apellido materno</label>
            <input type="text" class="form-control" id="materno" name="materno"
                placeholder="Escribe el apellido materno" required>
        </div>

        <div class="form-group">
            <label for="perfil" class="form-label fw-bold mt-4">Selecciona el perfil </label>
            <div id="divPerfilesCombo"></div>
        </div>

        <div class="form-group">
            <label for="username" class="form-label fw-bold mt-4">Crear username </label>
            <input type="text" class="form-control" id="username" name="username" placeholder="Escribe el username"
                required minlength="3">
        </div>

        <div class="form-group">
            <label for="password" class="form-label fw-bold mt-4">Password</label>
            <input type="password" class="form-control" name="password1" id="password1" placeholder="Password"
                autocomplete="off" required minlength="8">
        </div>

        <div class="form-group">
            <label for="password2" class="form-label fw-bold mt-4">Verifica tu password</label>
            <input type="password" class="form-control" id="password2" name="password2" placeholder="Password"
                autocomplete="off" required minlength="8">
        </div>
    </fieldset </form>