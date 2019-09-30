/********* INDEX LOGGIN *********/
function inicializaFormulario() {
    cerrarSessionAjax();
    $("#usuario_usuario").focus();
    siguienteCampo('#usuario_usuario', '#clave_usuario', false);
    siguienteCampo('#clave_usuario', '#btn-ingresar button', false);
}
;
function validarLoggin() {
    var vusuario = $("#usuario_usuario").val();
    var vclave = $("#clave_usuario").val();
    if (vusuario.trim() === '') {
        mensaje('Ingrese usuario', 'Aceptar', '#usuario_usuario');
        $("#usuario_usuario").focus();
    } else if (vclave.trim() === '') {
        mensaje('Contraseña Invalida', 'Aceptar', '#clase_usuario');
        $("#clave_usuario").focus();
    } else {
        validarAccesoAjax();
        //location.href = "menu.html";
    }
    ;
}
function validarAccesoAjax() {
    var pDatosFormulario = $('#formAcceso').serialize();
    //console.log(pDatosFormulario);
    var pUrl = 'usuario/validar';
    var pBeforeSend = "";
    var pSucces = "ValidarAccesoAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete) {
    eval(pBeforeSend);
    $.ajax({
        type: 'POST',
        url: pUrl,
        data: pDatosFormulario,
        dataType: 'json'
    }).done(function (json) {
        eval(pSucces);
    }).fail(function (e) {
        eval(pSucces);
    }).always(function (objeto, exito, error) {
        eval(pComplete);
    });
}

function ValidarAccesoAjaxSuccess(json) {
    //console.log(json);
    if (json.acceso) {
        location.href = "menu.html";
    } else {
        mensaje('Credenciales Invalidas', 'Aceptar', '#usuario_usuario');
    }
    ;
}

function ajax_error() {
    mensaje('No se puede conectar a la Base de datos', 'Aceptar', '');
}

function siguienteCampo(actual, siguiente, preventDefault) {
    $(actual).on('keydown', function (event) { // funcion callback , anonima
        //console.log("---> " + event.which);
        if (event.which === 13) {
            if (preventDefault) {
                event.preventDefault();
            }
            $(siguiente).focus();
            $(siguiente).select();
        }
    });
}
function mensaje(mensaje, textoBoton, focusCampo) {
    // 1. Agregar al final un div con id=mensajes al body
    $('body').append('<div id="mensajes"></div>');
    // 2. Se guarda el contenido del componente en una variable -> modal
    modal = '<div id="divModal" class="modal fade" tabindex="-1" role="dialog" ';
    modal += '     aria-labelledby="gridSystemModalLabel">';
    modal += '<div class="modal-dialog" role="document">';
    modal += '<div class="modal-content">';
    modal += '  <div class="modal-header">';
    modal += '    <button type="button" class="close" data-dismiss="modal" ';
    modal += '            aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    modal += '    <h4 class="modal-title" id="gridSystemModalLabel">Mensaje del Sistema</h4>';
    modal += '  </div>';
    modal += '  <div class="modal-body">';
    modal += '     ' + mensaje;
    modal += '  </div>';
    modal += '  <div class="modal-footer">';
    modal += '    <button id="botonAceptar" type="button" class="btn btn-primary"';
    modal += '            data-dismiss="modal">' + textoBoton + '</button>';
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
        $(focusCampo).focus();
        $(focusCampo).select();
        // 7. Remueve el div mensajes del body
        $('#mensajes').remove();
    });
}
function estaSeguro(mensaje, accion, focusCampo) {
    // 1. Agregar al final un div con id=mensajes al body
    $('body').append('<div id="estaSeguro"></div>');
    // 2. Se guarda el contenido del componente en una variable -> modal
    modal = '<div id="divModalEstaSeguro" class="modal fade" tabindex="-1" role="dialog" ';
    modal += '     aria-labelledby="gridSystemModalLabel">';
    modal += '<div class="modal-dialog" role="document">';
    modal += '<div class="modal-content">';
    modal += '  <div class="modal-header">';
    modal += '    <button type="button" class="close" data-dismiss="modal" ';
    modal += '            aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    modal += '    <h4 class="modal-title" id="gridSystemModalLabel">Mensaje del Sistema</h4>';
    modal += '  </div>';
    modal += '  <div class="modal-body">';
    modal += '     ' + mensaje;
    modal += '  </div>';
    modal += '  <div class="modal-footer">';
    modal += '    <button id="botonSi" type="button" class="btn btn-primary"';
    modal += '            data-dismiss="modal">Si</button>';
    modal += '    <button id="botonNo" type="button" class="btn btn-primary"';
    modal += '            data-dismiss="modal">No</button>';
    modal += '  </div>';
    modal += '</div>';
    modal += '</div>';
    modal += '</div>';
    // 3. Insertar el contenido de la variable modal en el div mensajes
    $('#estaSeguro').html(modal);
    // 4. Mostar el modal
    $('#divModalEstaSeguro').modal('show');
    // 5. Cuando se visualiza todo el modal lleva el cursor al boton Aceptar
    $('#divModalEstaSeguro').on('shown.bs.modal', function (e) {
        $('#botonNo').focus();
    });
    // 6. Cuando se oculta el modal se lleva el foco al campo que se recibio como parametro
    $('#divModalEstaSeguro').on('hidden.bs.modal', function (e) {
        $(focusCampo).focus();
        $(focusCampo).select();
        // 7. Remueve el div mensajes del body
        $('#estaSeguro').remove();
    });
    // eventos a si y no
    $('#botonSi').on('click', function () {
        eval(accion);
    });

}

function mostrar_foto($this) {
    //console.log($this.attr("src"));
    mostrar_imagen($this.attr("src"), "Imagen de Usuario");
}

function mostrar_imagen(pfoto, pDesc) {
    // 1. Agregar al final un div con id=mensajes al body
    $('body').append('<div id="mensajes"></div>');
    // 2. Se guarda el contenido del componente en una variable -> modal
    modal = '<div id="divModal" class="modal fade" tabindex="-1" role="dialog" ';
    modal += '     aria-labelledby="gridSystemModalLabel">';
    modal += '<div class="modal-dialog" role="document">';
    modal += '<div class="modal-content">';
    modal += '  <div class="modal-header">';
    modal += '    <button type="button" class="close" data-dismiss="modal" ';
    modal += '            aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    modal += '    <h4 class="modal-title" id="gridSystemModalLabel">' + pDesc + '</h4>';
    modal += '  </div>';
    modal += '  <div class="modal-body centrado">';
    modal += '      <img src="' + pfoto + '" height="350" alt="" class="img-circle"/>';
    modal += '  </div>';
    modal += '  <div class="modal-footer">';
    modal += '    <button id="botonAceptar" type="button" class="btn btn-primary"';
    modal += '            data-dismiss="modal">Cerrar</button>';
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
/********* SESSION *********/
function validarSessionAjax() {
    var pDatosFormulario = "";
    //console.log(pDatosFormulario);
    var pUrl = 'session/validar';
    var pBeforeSend = "";
    var pSucces = "SessionValidarAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function SessionValidarAjaxSuccess(json) {
    //console.log("SessionValidarAjaxSuccess");
    console.log(json);
    if (json.logueado) {
        $("#s_usuario_usuario").html(json.usuario.usuario_usuario);
    } else {
        location.href = "./";
    }
}
function cerrarSessionAjax() {
    var pDatosFormulario = "";
    //console.log(pDatosFormulario);
    var pUrl = 'session/cerrar';
    var pBeforeSend = "";
    var pSucces = "SessionCerrarAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function SessionCerrarAjaxSuccess(json) {
    console.log(json);
}

// /********* MENU *********/
var ocultar = false;
function inicializar_menu() {
    validarSessionAjax();
    $('#boton-menu').on('click', function () {
        $('nav').toggleClass('ocultar');
        $('main').toggleClass('ocultar');
        if (ocultar === false) {
            ocultar = true;
            $(this).removeClass('glyphicon-menu-left').addClass('glyphicon-menu-right');
        } else {
            ocultar = false;
            $(this).removeClass('glyphicon-menu-right').addClass('glyphicon-menu-left');
        }
    });
    $('#boton-logout').on('click', function () {
        location.href = './';
    });
    $('#boton-refrescar').on('click', function () {
        location.href = './menu.html';
    });
    generar_menu_ajax();    
}

function generar_menu_ajax() {
    var pDatosFormulario = "id_rol=1";
    var pUrl = "permiso/buscar/rol/id";
    var pBeforeSend = "";
    var pSuccess = "generar_menu_ajax_success(json)"; 
    //var pSuccess = "menu_acordeon(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSuccess, pError, pComplete);
}

function generar_menu_ajax_success(json) {
    //console.log(json);
    var menu = '';
    var g_id_sistema = 0;
    var id_sistema = 0;
    var g_id_submenu = 0;
    var id_submenu = 0;
    //console.log(json);
    $.each(json, function (key, value) {
        //console.log(key);
        id_sistema = value.id_sistema;
        id_submenu = value.id_submenu;
        if (g_id_sistema !== id_sistema) {
            if (g_id_sistema === 0) {
                menu = '<li>MENU PRINCIPAL\n';
                menu += "<ul>\n";
            } else {
                g_id_submenu = 0;
                menu += "</ul>\n";
                menu += "</li>\n";
                menu += "</ul>\n";
                menu += "</li>\n";
            }
            menu += "<li><h4>" + value.nombre_sistema + "</h4>\n";
            menu += "<ul>\n";
            g_id_sistema = id_sistema;
        }
        if (g_id_submenu !== id_submenu) {
            if (g_id_submenu !== 0) {
                menu += "</ul>\n";
                menu += "</li>\n";
            }
            menu += "<li>" + value.nombre_submenu + "\n";
            menu += "<ul>\n";
            g_id_submenu = id_submenu;
        }
        menu += "<li><a onclick=\"cargar_formulario('" + value.url_formulario + "')\" href=\"#\">" +
                value.nombre_formulario + "</a></li>\n";
    });
    if (menu !== '') {
        menu += "</ul>\n";
        menu += "</li>\n";
        menu += "</ul>\n";
        menu += "</li>\n";
    }
    $('#menu').html(menu);
    ver_menu();
}

function ver_menu() {
    //$("#menu ul").hide();modulo
    $("#menu li").each(function () {
        var handleSpan = $("<span></span>");
        handleSpan.addClass("handle");
        handleSpan.prependTo(this);
        if ($(this).has("ul").length > 0) {
            handleSpan.addClass("collapsed");
            handleSpan.click(function () {
                var clicked = $(this);
                clicked.toggleClass("collapsed expanded");
                clicked.siblings("ul").toggle();
            });
        } else {
            handleSpan.click(function () {
                var clicked = $(this);
                //console.log('---> ' + clicked.parent().html());
            });
        }
    });
}

function menu_acordeon(json){
    //No se como usar....sdkhfbsdlpkjgf
    //console.log(json);
    var menu = '';
    var g_id_sistema = 0;
    var id_sistema = 0;
    var g_id_submenu = 0;
    var id_submenu = 0;
    var cont_sistema = 0;
    //console.log(json);
    $.each(json, function (key, value) {
        id_sistema = value.id_sistema;
        id_submenu = value.id_submenu;
        if(g_id_sistema===0){
            menu +='<div class="accordion">\n';
        }
        if(g_id_sistema !== id_sistema){
            if (g_id_sistema !==0){
                menu +='    </div>\n';
                menu +='   </div>\n';
                menu +='\n</div>\n';
                cont_sistema = 0;                
            }
            menu += '<h3>'+value.nombre_sistema+'</h3>\n<div>\n';
            menu += '   <div class="accordion">\n';
            g_id_sistema = id_sistema; 
            
        }
        if(g_id_submenu !== id_submenu){
            if (cont_sistema === 1){
                menu +='        </div>\n';
                cont_sistema = 0;
            }
            menu += '       <h3>'+value.nombre_submenu+'</h3>\n       <div>\n';
            cont_sistema++;
            g_id_submenu = id_submenu;
        }
        menu += "<a onclick=\"cargar_formulario('" + value.url_formulario + "')\" href=\"#\">" +
                value.nombre_formulario + "</a>\n";
    });
    menu +='        </div>\n';
    menu +='    </div>\n';
    menu +='  </div>\n';
    menu +='</div>\n';
    console.log(menu);
    $('#menu').html(menu);
    $( ".accordion" ).accordion();
}

function cargar_formulario(frm) {
    //$('#boton-menu').click();
    //"restrict mode";
    $("#busquedas").remove();
    $("#formularios").fadeOut(0, function () {
        $("#formularios").load(frm);
        $("#formularios").fadeIn(800, function () {
        });
    });
    /*
     $("#busquedas").remove();
     $("#formularios").load(frm);
     */
}

function cargar_busqueda(frm) {
    "restrict mode";
    $('main').append('<div id="busquedas" class="oculto"></div>');
    $("#busquedas").load(frm);
    $("#panel-formulario").fadeOut(500, function () {
        $("#busquedas").fadeIn(500, function () { // callback - funcion anonima
            $("#buscar_texto").focus();
        });
    });
}

function salir_busqueda() {
    $("#busquedas").fadeOut(500, function () {
        $("#busquedas").remove();
        $("#panel-formulario").fadeIn(500, function () {
            //$("#boton-buscar-usuario").focus();
        });
    });
}

function salir_formulario() {
    $("#formularios").html("");
}

function habilitar_agregar() {
    $('#botonAgregar').prop('disabled', false);
    $('#botonModificar').prop('disabled', true);
    $('#botonEliminar').prop('disabled', true);
}

function deshabilitar_agregar() {
    $('#botonAgregar').prop('disabled', true);
    $('#botonModificar').prop('disabled', false);
    $('#botonEliminar').prop('disabled', false);
}

function siguiente(funcion) {
    var pag = Number($("#pagina").val());
    $("#pagina").val(pag + 1);
    eval(funcion);
}
function anterior(funcion) {
    var pag = Number($("#pagina").val());
    if (pag - 1 > 0) {
        console.log(pag - 1);
        $("#pagina").val(pag - 1);
    }
    eval(funcion);
}
    