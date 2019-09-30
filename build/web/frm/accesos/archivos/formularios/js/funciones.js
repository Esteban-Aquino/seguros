/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function inicializarFrmFormularios(){
    $("#s_nombre_formulario").text('Formularios');
    $("#id_formulario").focus();
    $("#id_formulario").select();
    $("#id_formulario").on('change',function(){
       if ($("#id_formulario").val()==="0"){
            limpiarFormulario();
       }else{
            buscarIdFormularioAjax();
       }
    });
    $("#id_sistema").on('change',function(){
       if ($("#id_sistema").val()==="0"||$("#id_sistema").val().trim()===""){
            mensaje("Debe cargar un sistema", "Aceptar", "#id_sistema") ;
       }else{
            buscarIdSistemaAjax();
       }
    });
    $("#id_submenu").on('change',function(){
       if ($("#id_submenu").val()==="0"||$("#id_submenu").val().trim()===""){
            mensaje("Debe cargar un submenu", "Aceptar", "#id_submenu") ;
       }else{
            buscarIdSubmenuAjax();
       }
    });
    $("#botonGuardar").on('click',function(){
       guardarFormularioAjax(); 
    });
    $("#botonEliminar").on('click',function(){
       confirmaEliminar(); 
    });
    siguienteCampo('#id_formulario', '#nombre_formulario', false);
    siguienteCampo('#nombre_formulario', '#url_formulario', false);
    siguienteCampo('#url_formulario', '#id_sistema', false);
    siguienteCampo('#id_sistema', '#id_submenu', false);
    siguienteCampo('#id_submenu', '#botonGuardar', true);
}

function validarFormulario(){
    var ok = true;
    var nombre_formulario = $("#nombre_formulario").val();
    var url_formulario = $("#url_formulario").val();
    if(nombre_formulario.trim() === ""){
        mensaje("Nombre vacio", "Aceptar", "#nombre_formulario");
        ok = false;
    }else if(url_formulario.trim() === ""){
        mensaje("Url vacio", "Aceptar", "#url_formulario");
        ok = false;
    }
    return ok;
}




/*** GUARDAR FORMULARIO ***/
function guardarFormularioAjax(){
    if (validarFormulario()){
        var pDatosFormulario = $("#formularioFormulario").serialize();
        console.log(pDatosFormulario);
        var pUrl= 'formulario/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarFormularioAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarFormularioAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Formulario Guardado","Aceptar","#id_formulario");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_formulario");
    }
}

/*** BUSCAR ID FORMULARIO ***/
function buscarIdFormularioAjax(){
    var pDatosFormulario = "id_formulario="+$("#id_formulario").val();
    //console.log(pDatosFormulario);
    var pUrl= 'formulario/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdFormularioAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdFormularioAjaxSuccess(json){
    //console.log(json);
    $('#id_formulario').val(json.id_formulario);
    $('#nombre_formulario').val(json.nombre_formulario);
    $('#url_formulario').val(json.url_formulario);
    $('#id_sistema').val(json.id_sistema);
    $('#nombre_sistema').val(json.nombre_sistema);
    $('#url_sistema').val(json.url_sistema);
    $('#id_submenu').val(json.id_submenu);
    $('#nombre_submenu').val(json.nombre_submenu);
    $('#url_submenu').val(json.url_submenu);
    $('#nombre_formulario').select();
}

function limpiarFormulario(){
    $("#id_formulario").val(0);
    $("#nombre_formulario").val('');
    $("#url_formulario").val('');
    $('#id_sistema').val('');
    $('#nombre_sistema').val('');
    $('#url_sistema').val('');
    $('#id_submenu').val('');
    $('#nombre_submenu').val('');
    $('#url_submenu').val('');
    $("#id_formulario").select();
}

/*** BUSCAR FORMULARIO POR NOMBRE ***/
function BuscarFormularioNombreAjax(){
        var pDatosFormulario = $("#form-buscar").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'formulario/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarFormularioNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function BuscarFormularioNombreAjaxSuccess(json){
    var datos = "";
    console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_formulario($(this))'>";
        datos += "<td>" + value.id_formulario + "</td>";
        datos += "<td>" + value.nombre_formulario + "</td>";
        datos += "<td>" + value.url_formulario + "</td>";
        datos += '<td class="oculto">' + value.id_sistema + '</td>';
        datos += "<td>" + value.nombre_sistema + "</td>";
        datos += '<td class="oculto">' + value.url_sistema + '</td>';
        datos += '<td class="oculto">' + value.id_submenu + '</td>';
        datos += "<td>" + value.nombre_submenu + "</td>";
        datos += '<td class="oculto">' + value.url_submenu + '</td>';
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_formularios').html(datos);
}
function seleccionar_formulario($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(1).text();
    var url = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_formulario").val(id);
    $("#nombre_formulario").val(nombre);
    $("#url_formulario").val(url);
    
    $("#id_sistema").val($this.find('td').eq(3).text());
    $("#nombre_sistema").val($this.find('td').eq(4).text());
    $("#url_sistema").val($this.find('td').eq(5).text());
    
    $("#id_submenu").val($this.find('td').eq(6).text());
    $("#nombre_submenu").val($this.find('td').eq(7).text());
    $("#url_submenu").val($this.find('td').eq(8).text());
    
    $("#nombre_formulario").focus();
    $("#nombre_formulario").select();
}
/*** ELIMINAR FORMULARIO ***/
function confirmaEliminar(){
    estaSeguro("Se eliminara el registro. Esta seguro/a ?","eliminarFormularioAjax()","#id_formulario");
}
function eliminarFormularioAjax(){
    if ($("#id_formulario").val().trim()==="0" || $("#id_formulario").val().trim()===""){
        mensaje("Formulario invalido","Aceptar","#id_formulario");
    }else{
        var pDatosFormulario = $("#formularioFormulario").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'formulario/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarFormularioAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarFormularioAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Formulario Eliminado","Aceptar","#id_formulario");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_formulario");
    }
}
/*** SISTEMA ***/

/*** BUSCAR ID SISTEMA ***/
function buscarIdSistemaAjax(){
    var pDatosFormulario = "id_sistema="+$("#id_sistema").val();
    //console.log(pDatosFormulario);
    var pUrl= 'sistema/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdSistemaAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdSistemaAjaxSuccess(json){
    //console.log(json);
    $('#id_sistema').val(json.id_sistema);
    $('#nombre_sistema').val(json.nombre_sistema);
    $('#url_sistema').val(json.url_sistema);
}
/*** BUSCAR SISTEMA POR NOMBRE ***/
function BuscarSistemaNombreAjax(){
        var pDatosFormulario = $("#form-buscar").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'sistema/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarSistemaNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function BuscarSistemaNombreAjaxSuccess(json){
    var datos = "";
    console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_sistema($(this))'>";
        datos += "<td>" + value.id_sistema + "</td>";
        datos += "<td>" + value.nombre_sistema + "</td>";
        datos += "<td>" + value.url_sistema + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_sistemas').html(datos);
}
function seleccionar_sistema($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(1).text();
    var url = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_sistema").val(id);
    $("#nombre_sistema").val(nombre);
    $("#url_sistema").val(url);
    $("#id_sistema").focus();
}
/*** SUBMENU ***/
/*** BUSCAR ID SISTEMA ***/
function buscarIdSubmenuAjax(){
    var pDatosFormulario = "id_submenu="+$("#id_submenu").val();
    //console.log(pDatosFormulario);
    var pUrl= 'submenu/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdSubmenuAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdSubmenuAjaxSuccess(json){
    //console.log(json);
    $('#id_submenu').val(json.id_submenu);
    $('#nombre_submenu').val(json.nombre_submenu);
    $('#url_submenu').val(json.url_submenu);
    $('#id_submenu').focus();
    $('#id_submenu').select();
}
/*** BUSCAR SUBMENU POR NOMBRE ***/
function BuscarSubmenuNombreAjax(){
        var pDatosFormulario = $("#form-buscar").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'submenu/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarSubmenuNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function BuscarSubmenuNombreAjaxSuccess(json){
    var datos = "";
    console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_submenu($(this))'>";
        datos += "<td>" + value.id_submenu + "</td>";
        datos += "<td>" + value.nombre_submenu + "</td>";
        datos += "<td>" + value.url_submenu + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_submenus').html(datos);
}
function seleccionar_submenu($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(1).text();
    var url = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_submenu").val(id);
    $("#nombre_submenu").val(nombre);
    $("#url_submenu").val(url);
    $("#id_submenu").focus();
    $("#id_submenu").select();
}
