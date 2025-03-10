<script>
    $(function () {
        consultarUsuarios();
    });

    function consultarPerfiles() {
        $.ajax({
            url: backend + "/auth/consultarPerfiles",
            type: "post",
            dataType: "json",
            xhrFields: { withCredentials: true },
            success: function (respuesta) {
                comboPerfiles(respuesta);

               
                return respuesta;

            }
        });
    }

    function comboPerfiles(perfiles) {
        let combo = '<select class="form-select" id="comboPerfiles">';
        $.each(perfiles, function (i, perfil) {
            combo += '<option value="' + i + '">' + perfil + '</option>';
        });
        combo += '</select>';
        $("#divPerfilesCombo").html(combo);
    }

    function consultarUsuarios() {
        $.ajax({
            url: backend + "/Usuarios/listar",
            type: "post",
            dataType: "json",
            xhrFields: { withCredentials: true },
            success: function (respuesta) {
                console.log(respuesta);
                cargarUsuarios(respuesta);
            }
        });
    }

    function cargarUsuarios(usuarios) {
        var body = '';
        $.each(usuarios, function (i, user) {
            body += '<tr class="table-' + ((i % 2 == 0) ? 'success' : 'secondary') + '">' +
                '<td scope="row">' + user.nombre + '</td>' +
                '<td scope="row">' + user.paterno + '</td>' +
                '<td scope="row">' + user.materno + '</td>' +
                '<td scope="row">' + user.perfil + '</td>' +
                '<td scope="row">' + user.username + '</td>' +
                '<td scope="row"> <a class="bi bi-pencil me-3" href="#" onclick="buscarUser(\'' + user.uuid + '\');"></a> <a class="bi bi-trash3-fill me-2" href="#" onclick="eliminarUser(\'' + user.uuid + '\');"></a>' +
                '</tr>';
        });
        $('#tablaUsuarios > tbody').html(body);
    }

   function comboPerfilesUsuario(user, perfiles) {
    


        let combo = '<select class="form-select" onchange="cambiarPerfil(\'' + user.uuid + '\', this.value);">';
        $.each(perfiles, function (i, perfil) {
            combo += '<option value="' + perfil + '" ' + (user == user.perfil ? 'selected' : '') + '>' + perfil + '</option>';
        });
        combo += '</select>';
        $("#comboPerfilesModificar").html(combo);
    }

    /*function cambiarPerfil(uuid, nuevoPerfil) {
        let datos = {
            uuid: uuid,
            perfil: nuevoPerfil
        };

        $.ajax({
            url: backend + "/Usuarios/cambiarPerfil",
            type: "post",
            dataType: "json",
            data: JSON.stringify(datos),
            contentType: 'application/json',
            xhrFields: { withCredentials: true },
            success: function (respuesta) {
                if (respuesta.exito === true) {
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
                consultarUsuarios();
            }
        });
    }
*/
   
    function eliminarUser(uuid) {
        let confirmar = confirm("¿Deseas eliminar a este usuario?");
        if (confirmar) {
            $.ajax({
                url: backend + "/Usuarios/eliminarUser",
                type: "post",
                datatype: "json",
                data: JSON.stringify(uuid),
                contentType: 'application/json',
                xhrFields: { withCredentials: true },
                success: function (respuesta) {
                    if (respuesta.exito == true) {
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
                    consultarUsuarios();
                }
            });
        }
    }

    function buscarUser(uuid) {
        $.ajax({
            url: backend + "/Usuarios/buscarUser",
            type: "post",
            dataType: "json",
            data: JSON.stringify(uuid),
            contentType: 'application/json',
            xhrFields: { withCredentials: true },
            success: function (respuesta) {
                console.log(respuesta);
                $("#divModificarUsuario").load("usuarios/modificarUsuario.jsp", function () {
                    let perfil = consultarPerfiles();
                    //console.log('Pefilees en e buscar user', perfil);
                    comboPerfilesUsuario(respuesta, perfil);
                    
                    $("#modalModificarUsuario").modal("show");
                    cargarUnUsuario(respuesta);
                    
                });
            }
        });
    }

    function cargarUnUsuario(user, perfiles) {
        $("#nombreModificado").val(user.nombre);
        $("#paternoModificado").val(user.paterno);
        $("#maternoModificado").val(user.materno);
        $("#usernameModificado").val(user.username);
        $("#comboPerfilesModificar").val(user.perfil);
        $("#uuidUsuario").val(user.uuid);
    }

    function modificarUsuario() {
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
</script>

<!-- label de bienvenida -->
<div class="row">
    <div class="col m-5 text-dark">
        <h1 class="fw-bold text-center text-uppercase text-dark ">Hola, bienvenido a tu gestor de usuarios</h1>
        <h5 class="fw-bold mt-5 mb-5 text-center text-uppercase text-dark ">¿Qué desea hacer hoy?</h5>
        <button type="button" class="btn btn-success"
            onclick='$("#divNuevoUsuario").load("usuarios/nuevoUsuario.jsp", function() { consultarPerfiles(); })'
            data-bs-toggle="modal" data-bs-target="#modalNuevoUsuario">Crear usuario <i
                class="bi bi-plus-circle"></i></button><br><br>
        <hr>
        <table class="table table-hover " id="tablaUsuarios">
            <thead>
                <tr class="table-dark">
                    <th scope="col">Nombre</th>
                    <th scope="col">Apellido paterno</th>
                    <th scope="col">Apellido materno</th>
                    <th scope="col">Perfil</th>
                    <th scope="col">Username</th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<!-- Modal nuevo usuario -->
<div class="modal fade modal-dialog-scrollable" id="modalNuevoUsuario" data-bs-backdrop="static"
    data-bs-keyboard="false" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5">Crear usuario </h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="divNuevoUsuario">
                <div id="divPerfilesCombo"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" onclick="crearUsuario();">Guardar usuario</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal modificar usuario -->
<div class="modal fade modal-dialog-scrollable" id="modalModificarUsuario" data-bs-backdrop="static"
    data-bs-keyboard="false" tabindex="-1" aria-labelledby="modal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5">Modificar usuario </h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="divModificarUsuario">
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" onclick="modificarUsuario();">Modificar usuario</button>
            </div>
        </div>
    </div>
</div>