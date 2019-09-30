/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var operacion = 'agregar';
var $fila;
function inicializarFrmUsuarios() {
    $("#s_nombre_formulario").text('Usuarios');
    $("#id_usuario").focus();
    $("#id_usuario").select();
    $("#id_usuario").on('change', function () {
        if ($("#id_usuario").val().trim() === "") {
            $("#id_usuario").val("0");
        }
        if ($("#id_usuario").val() === "0") {
            habilitar_agregar();
            limpiarFormulario();
        } else {
            buscarIdUsuarioAjax();
        }
        cambiaImagenUsuario($("#id_usuario").val());
    });
    
    $("#id_persona_usuario").on('change', function () {
        //console.log($("#id_persona_usuario").val());
        if ($("#id_persona_usuario").val().trim() === "") {
            $("#id_persona_usuario").val("0");
        }
        
        if ($("#id_persona_usuario").val() !== "0") {
            buscarIdPersonaAjax();
        }
    });
    
    $("#botonAgregar").on('click', function () {
        //agregarUsuarioAjax();
        guardar();
    });
    $("#botonModificar").on('click', function () {
        //modificarUsuarioAjax();
        guardar();
    });
    $("#botonEliminar").on('click', function () {
        confirmaEliminar();
    });
    siguienteCampo('#id_usuario', '#id_persona_usuario', false);
    siguienteCampo('#id_persona_usuario', '#usuario_usuario', false);
    
    siguienteCampo('#usuario_usuario', '#nombre_persona', false);
    siguienteCampo('#nombre_persona', '#clave_usuario', false);
    siguienteCampo('#clave_usuario', '#id_rol', false);
    siguienteCampo('#id_rol', '#guardarDetalle', true);
    habilitar_agregar();
}

function validarUsuario() {
    var ok = true;
    var usuario_usuario = $("#usuario_usuario").val();
    //var nombre_usuario = $("#nombre_usuario").val();
    //var nombre_persona = $("#nombre_persona").val();
    var clave_usuario = $("#clave_usuario").val();
    if (usuario_usuario.trim() === "") {
        mensaje("Usuario vacio", "Aceptar", "#usuario_usuario");
        ok = false;
    /*} else if (nombre_persona.trim() === "") {
        mensaje("Nombre vacio", "Aceptar", "#nombre_usuario");
        ok = false;*/
    } else if (clave_usuario.trim() === "" && usuario_usuario.trim() === "0") {
        mensaje("Clave vacia", "Aceptar", "#clave_usuario");
        ok = false;
    }
    return ok;
}
/*** GUARDAR ***/
function guardar() {
    var filas = $('#tbody_detalle_roles tr');
    var detalle = '[';
    $.each(filas, function (index, fila) {
        var id_rol = $(this).find('td').eq(1).text();
        detalle += '{"id_rol":' + id_rol + '},';
    });
    if (detalle !== "[") {
        detalle = detalle.substr(detalle, detalle.length - 1);
    }
    detalle += "]";
    guardarUsuarioAjax(detalle);
    //console.log(detalle);
}
/*** GUARDAR USUARIO ***/
function guardarUsuarioAjax(detalle) {
    if (validarUsuario()) {
        var pDatosFormulario = $("#formularioUsuario").serialize();
        pDatosFormulario += "&detalle=" + detalle;
        //console.log(pDatosFormulario);
        var pUrl = 'usuario/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarUsuarioAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarUsuarioAjaxSuccess(json) {
    //console.log(json);
    if (json.correcto) {
        mensaje("Usuario Guardado", "Aceptar", "#id_usuario");
        limpiarFormulario();
    } else if (!json.agregado) {
        mensaje(json.error, "Aceptar", "#id_usuario");
    }
}

/*** AGREGAR USUARIO ***/
function agregarUsuarioAjax() {
    if (validarUsuario()) {
        var pDatosFormulario = $("#formularioUsuario").serialize();
        //console.log(pDatosFormulario);
        var pUrl = 'usuario/agregar';
        var pBeforeSend = "";
        var pSucces = "agregarUsuarioAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function agregarUsuarioAjaxSuccess(json) {
    //console.log(json);
    if (json.correcto) {
        mensaje("Usuario Agregado", "Aceptar", "#id_usuario");
        limpiarFormulario();
    } else if (!json.agregado) {
        mensaje(json.error, "Aceptar", "#id_usuario");
    }
}
/*** BUSCAR ID USUARIO ***/
function buscarIdUsuarioAjax() {
    var pDatosFormulario = "id_usuario=" + $("#id_usuario").val();
    //console.log(pDatosFormulario);
    var pUrl = 'usuario/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdUsuarioAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdUsuarioAjaxSuccess(json) {
    //console.log(json);
    $('#id_usuario').val(json.id_usuario);
    //$('#nombre_usuario').val(json.nombre_usuario);
    $('#id_persona_usuario').val(json.id_persona_usuario);
    $('#nombre_persona').val(json.nombre_persona);
    $('#usuario_usuario').val(json.usuario_usuario);
    $('#clave_usuario').val('');
    $('#nombre_usuario').select();
    buscar_id_usuario_usuariosroles_ajax(json.id_usuario);
    deshabilitar_agregar();
}
/*** BUSCAR ID PERSONA ***/
function buscarIdPersonaAjax(){
    var pDatosPersona = "id_persona="+$("#id_persona_usuario").val();
    //console.log(pDatosPersona);
    var pUrl= 'persona/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdPersonaAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosPersona, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdPersonaAjaxSuccess(json){
    //console.log(json);
    if (json.id_persona === 0 || json.id_persona === ""){
        mensaje("No existe persona seleccionada", "Aceptar", "#id_persona_usuario");
        $("#id_persona_usuario").val(0);
    }else{
        $("#nombre_persona").val(json.nombre_persona);
    }
}

/*** TRAER DETALLES ***/
function buscar_id_usuario_usuariosroles_ajax(id_usuario) {
    var pDatosFormulario = 'id_usuario=' + id_usuario;
    //console.log(pDatosFormulario);
    var pUrl = "usuariorol/buscar/usuario/id";
    var pBeforeSend = "";
    var pSuccess = "buscar_id_usuario_usuariosroles_ajax_success(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSuccess, pError, pComplete);
}

function buscar_id_usuario_usuariosroles_ajax_success(json) {
    //console.log(json);
    var datos = '';
    $.each(json, function (key, value) {
        datos += `<tr>`;
        datos += `   <td>` + value.id_usuariorol + `</td>`;
        datos += `            <td>` + value.id_rol + `</td>`;
        datos += `            <td></td>`;
        datos += `            <td>` + value.nombre_rol + `</td>`;
        datos += `            <td class="centrado">`;
        datos += `                <button onclick="editar_detalle($(this))" type="button" class="btn btn-success btn-sm">`;
        datos += `                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>`;
        datos += `                </button>`;
        datos += `                <button onclick="eliminar_detalle($(this))" type="button" class="btn btn-danger btn-sm">`;
        datos += `                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>`;
        datos += `                </button>`;
        datos += `            </td>`;
        datos += `</tr>`;
    });
    $('#tbody_detalle_roles').html(datos);
}
/*** MODIFICAR USUARIO ***/
function modificarUsuarioAjax() {
    if (validarUsuario()) {
        var pDatosFormulario = $("#formularioUsuario").serialize();
        //console.log(pDatosFormulario);
        var pUrl = 'usuario/modificar';
        var pBeforeSend = "";
        var pSucces = "modificarUsuarioAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function modificarUsuarioAjaxSuccess(json) {
    //console.log(json);

    if (json.correcto) {
        mensaje("Usuario Modificado", "Aceptar", "#id_usuario");
        limpiarFormulario();
    } else if (!json.agregado) {
        mensaje(json.error, "Aceptar", "#id_usuario");
    }
}

function limpiarFormulario() {
    $("#id_usuario").val(0);
    $("#id_persona_usuario").val(0);
    $("#nombre_persona").val('');
    $("#usuario_usuario").val('');
    $("#clave_usuario").val('');
    $("#id_usuario").focus();
    $("#id_usuario").select();
    cambiaImagenUsuario("0");
    $('#tbody_detalle_roles').html("");
}
/*** BUSCAR USUARIO POR NOMBRE ***/
function BuscarUsuarioNombreAjax() {
    var pDatosFormulario = $("#form-buscar").serialize();
    //console.log(pDatosFormulario);
    var pUrl = 'usuario/buscar/nombre';
    var pBeforeSend = "";
    var pSucces = "BuscarUsuarioNombreAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function BuscarUsuarioNombreAjaxSuccess(json) {
    var datos = "";
    //console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr>";
        datos += "<td onclick='seleccionar_usuario($(this))'>" + value.id_usuario + "</td>";
        datos += "<td onclick='seleccionar_usuario($(this))'>" + value.nombre_persona + "</td>";
        datos += "<td onclick='seleccionar_usuario($(this))'>" + value.usuario_usuario + "</td>";
        datos += "<td onclick='seleccionar_usuario($(this))'>" + value.clave_usuario + "</td>";
        datos += `<td class="centrado" onclick="mostrar_foto($(this).children('img'))"><img src="img/usuarios/` + value.id_usuario + `.jpg" height="35" alt="" class="img-circle"/></td>`;
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_usuarios').html(datos);
}
function seleccionar_usuario($this) {
    /*var id = $this.find('td').eq(0).text();
     var nombre = $this.find('td').eq(1).text();
     var usuario = $this.find('td').eq(2).text();*/
    //console.log($this.toString());
    var id = $this.parent().find('td').eq(0).text();
    var nombre = $this.parent().find('td').eq(1).text();
    var usuario = $this.parent().find('td').eq(2).text();
    salir_busqueda();
    $("#id_usuario").val(id);
    $("#nombre_usuario").val(nombre);
    $("#usuario_usuario").val(usuario);
    $("#clave_usuario").val("");
    $("#nombre_usuario").focus();
    $("#nombre_usuario").select();
    cambiaImagenUsuario($("#id_usuario").val());
    deshabilitar_agregar();
}
/*** ELIMINAR USUARIO ***/
function confirmaEliminar() {
    estaSeguro("Se eliminara el registro. Esta seguro/a ?", "eliminarUsuarioAjax()", "#id_usuario");
}
function eliminarUsuarioAjax() {
    if ($("#id_usuario").val().trim() === "0" || $("#id_usuario").val().trim() === "") {
        mensaje("Usuario invalido", "Aceptar", "#id_usuario");
    } else {
        var pDatosFormulario = $("#formularioUsuario").serialize();
        //console.log(pDatosFormulario);
        var pUrl = 'usuario/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarUsuarioAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarUsuarioAjaxSuccess(json) {
    //console.log(json);
    if (json.correcto) {
        mensaje("Usuario Eliminado", "Aceptar", "#id_usuario");
        limpiarFormulario();
    } else if (!json.agregado) {
        mensaje(json.error, "Aceptar", "#id_usuario");
    }
}

function cambiaImagenUsuario(pIdUSuario) {
    $("#imagenUsuario").fadeOut(500, function () {
        var vfoto = "img/usuarios/" + pIdUSuario + ".jpg";
        //console.log(vfoto);
        $("#imagenUsuario").attr("src", vfoto);
        $("#imagenUsuario").fadeIn(500, function () {
        });
    });
}
// DETALLES
function agregar_detalle() {
    operacion = 'agregar';
    $('#id_usuariorol').val(0);
    $('#id_rol').val(0);
    $('#nombre_rol').val('');

    $('#id_rol').focus();
    $('#id_rol').select();
}

function guardar_detalle() {
    var id_usuariorol = $('#id_usuariorol').val();
    var id_rol = $('#id_rol').val();
    if (id_rol === "" || id_rol === "0") {
        mensaje("Debe ingresar un rol", "Aceptar", '#id_rol');
    } else {
        var nombre_rol = $('#nombre_rol').val();
        //console.log(operacion);
        if (operacion === 'agregar') {
            var linea = `<tr>`;
            linea += `   <td>` + id_usuariorol + `</td>`;
            linea += `            <td>` + id_rol + `</td>`;
            linea += `            <td></td>`;
            linea += `            <td>` + nombre_rol + `</td>`;
            linea += `            <td class="centrado">`;
            linea += `                <button onclick="editar_detalle($(this))" type="button" class="btn btn-success btn-sm">`;
            linea += `                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>`;
            linea += `                </button>`;
            linea += `                <button onclick="eliminar_detalle($(this))" type="button" class="btn btn-danger btn-sm">`;
            linea += `                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>`;
            linea += `                </button>`;
            linea += `            </td>`;
            linea += `</tr>`;
            $('#tbody_detalle_roles').append(linea);
            agregar_detalle();
        } else {
            $fila.parent().parent().find('td').eq(0).text(id_usuariorol);
            $fila.parent().parent().find('td').eq(1).text(id_rol);
            $fila.parent().parent().find('td').eq(3).text(nombre_rol);
        }
    }
}

function editar_detalle($this) {
    $fila = $this;
    operacion = 'modificar';
    var id_usuariorol = $this.parent().parent().find('td').eq(0).text();
    var id_rol = $this.parent().parent().find('td').eq(1).text();
    var nombre_rol = $this.parent().parent().find('td').eq(3).text();

    $('#id_usuariorol').val(id_usuariorol);
    $('#id_rol').val(id_rol);
    $('#nombre_rol').val(nombre_rol);

    $('#id_rol').focus();
    $('#id_rol').select();
}

function eliminar_detalle($this) {
    $this.parent().parent().remove();
}
// ROLES
function buscar_id_rol() {
    var id_rol = $('#id_rol').val();
    if (id_rol.trim() === '' || id_rol.trim() === '0') {
        mensaje("Id no se puede dejar vacio", "Aceptar", "#id_rol");
    } else {
        buscar_id_rol_ajax(id_rol);
    }
}

function buscar_id_rol_ajax() {
    var pDatosFormulario = 'id_rol=' + $('#id_rol').val();
    //console.log(pDatosFormulario);
    var pUrl = "rol/buscar/id";
    var pBeforeSend = "";
    var pSuccess = "buscar_id_rol_ajax_success(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSuccess, pError, pComplete);
}

function buscar_id_rol_ajax_success(json) {
    //console.log(json);
    $('#id_rol').val(json.id_rol);
    $('#nombre_rol').val(json.nombre_rol);
    //$('#guardarDetalle').select();
}

/*** BUSCAR ROLES ***/
function BuscarRolNombreAjax() {
    var pDatosFormulario = $("#form-buscar").serialize();
    //console.log(pDatosFormulario);
    var pUrl = 'rol/buscar/nombre';
    var pBeforeSend = "";
    var pSucces = "BuscarRolNombreAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function BuscarRolNombreAjaxSuccess(json) {
    var datos = "";
    //console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_rol($(this))'>";
        datos += "<td>" + value.id_rol + "</td>";
        datos += "<td>" + value.nombre_rol + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_roles').html(datos);
}
function seleccionar_rol($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(1).text();
    var rol = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_rol").val(id);
    $("#nombre_rol").val(nombre);
    $("#nombre_rol").focus();
    $("#nombre_rol").select();
}
/***** IMAGEN ******/

function mostrar_foto_usuario($this) {
    //console.log($this.attr("src"));
    imagen_usuario($this.attr("src"), "Imagen de Usuario");
}

function imagen_usuario(pfoto, pDesc) {
    // 1. Agregar al final un div con id=mensajes al body
    $('body').append('<div id="mensajes"></div>');
    // 2. Se guarda el contenido del componente en una variable -> modal
    modal = '<div id="divModal" class="modal fade" tabindex="-1" role="dialog" ';
    modal += '     aria-labelledby="gridSystemModalLabel">';
    modal += '<div class="modal-dialog" role="document">';
    modal += '<div class="modal-content">';
    modal += '  <div class="modal-header" id="modal-header-usuario">';

    modal += '    <div class="col-md-5">';
    modal += '          <h4 class="modal-title" id="gridSystemModalLabel">' + pDesc + '</h4>';
    modal += '    </div>';
    modal += '    <div id="notiFoto" class="col-md-6"></div>';
    modal += '    <div class="col-md-1">';
    modal += '          <button type="button" class="close" data-dismiss="modal" ';
    modal += '            aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    modal += '    </div>';
    
    modal += '  </div>';
    modal += '  <div class="modal-body centrado">';
    modal += '      <img id = "foto_foto" src="' + pfoto + '" height="350" alt="" class="img-circle"/>';
    modal += '  </div>';
    modal += '  <div class="modal-footer">';
    //modal += '    <div class="alert alert-danger col-md-6" role="alert" id="notiFoto">Debe seleccionar un usuario</div>';
    //modal += '    <div class="col-md-12">';
    modal += '      <button id="botonCambiarImagen" type="button" class="btn btn-primary" onclick="selecciona_foto()"';
    modal += '          > <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Modificar</button>';
    //modal += '      <button id="botonGuardarImagen" type="button" class="btn btn-primary" onclick="guardarImagen()"';
    //modal += '          > <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> Guardar</button>';
    modal += '      <button id="botonAceptar" type="button" class="btn btn-primary"';
    modal += '            data-dismiss="modal">Cerrar</button>';
    //modal += '      </div>';
    modal += '  </div>';
    modal += '</div>';
    modal += '</div>';
    modal += '</div>';
// 3. Insertar el contenido de la variable modal en el div mensajes
    $('#mensajes').html(modal);
    // 4. Mostar el modal
    $('#divModal').modal('show');
    // 5. Cuando se visualiza todo el modal lleva el cursor al boton Aceptar
    $('#divModal').on('shown.bs.modal', function (e) {
        $('#botonAceptar').focus();
    });
    // 6. Cuando se oculta el modal se lleva el foco al campo que se recibio como parametro
    $('#divModal').on('hidden.bs.modal', function (e) {
        // 7. Remueve el div mensajes del body
        $('#mensajes').remove();
    });
}

function selecciona_foto() {
    var id = $("#id_usuario").val();
    if (id === '0') {
        //$("#notiFoto").prepend('<div class="alert alert-danger" role="alert" id="notiFoto">Debe seleccionar un usuario</div>');
        $("#notiFoto").addClass("alert alert-danger");
        $("#notiFoto").attr("role", "alert");
        $("#notiFoto").text("Debe seleccionar un usuario");
        setTimeout(function () {
            $("#notiFoto").fadeOut(500, function () {
                $("#notiFoto").removeClass("alert alert-danger");
                $("#notiFoto").attr("role", "");
                $("#notiFoto").text("");
                $("#notiFoto").fadeIn(0, function () {});
            });
        }, 1500);
    } else {
        $('#fotos').click();
        if($('#fotos').val().trim() === ''){
            //console.log('Imagen no seleccionada');
        }else{
            //console.log($('#fotos').val());
            agregar();
            //$('#foto_foto').attr("src",$('#fotos').val());  
        }
    }
}

function agregar() {
    var id_usuario = $("#id_usuario").val();
    var pDatos = new FormData(document.getElementById("formulario-foto"));
    pDatos.append("id_usuario="+id_usuario,id_usuario);
    //console.log(pDatos);

    $.ajax({
        type: 'POST',
        url: '/usuario/foto/agregar',
        data: pDatos,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        beforeSend: function (objeto) {

        },
        success: function (json) {
            cambiaImagenUsuario($("#id_usuario").val());
        },
        error: function (e) {

        },
        complete: function (objeto, exito, error) {

        }
    });
}
/*** BUSCAR PERSONA POR NOMBRE ***/
function BuscarPersonaNombreAjax(){
        var pDatosPersona = $("#form-buscar").serialize();
        //console.log(pDatosPersona);
        var pUrl= 'persona/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarPersonaNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosPersona, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function BuscarPersonaNombreAjaxSuccess(json){
    var datos = "";
    //console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_persona($(this))'>";
        datos += "<td>" + value.id_persona + "</td>";
        datos += "<td>" + value.nombre_persona + "</td>";
        datos += "<td>" + value.nombre_fantasia_persona + "</td>";
        datos += "<td>" + value.ci_persona + "</td>";
        datos += "<td>" + value.ruc_persona + "</td>";
        datos += "<td>" + value.dni_persona + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_personas').html(datos);
}
function seleccionar_persona($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(1).text();
    var persona = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_persona_usuario").val(id);
    buscarIdPersonaAjax();    
    $("#usuario_usuario").focus();
    $("#usuario_usuario").select();
    deshabilitar_agregar();
}