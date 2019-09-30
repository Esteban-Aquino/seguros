/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function inicializarFrmSistemas(){
    $("#s_nombre_formulario").text('Sistemas');
    $("#id_sistema").focus();
    $("#id_sistema").select();
    $("#id_sistema").on('change',function(){
       if ($("#id_sistema").val()==="0"){
            limpiarFormulario();
       }else{
            buscarIdSistemaAjax();
       }
    });
    $("#botonGuardar").on('click',function(){
       guardarSistemaAjax(); 
    });
    $("#botonEliminar").on('click',function(){
       confirmaEliminar(); 
    });
    siguienteCampo('#id_sistema', '#nombre_sistema', false);
    siguienteCampo('#nombre_sistema', '#url_sistema', false);
    siguienteCampo('#url_sistema', '#botonGuardar', false);
}

function validarSistema(){
    var ok = true;
    var nombre_sistema = $("#nombre_sistema").val();
    var url_sistema = $("#url_sistema").val();
    if(nombre_sistema.trim() === ""){
        mensaje("Nombre vacio", "Aceptar", "#nombre_sistema");
        ok = false;
    }else if(url_sistema.trim() === ""){
        mensaje("Url vacio", "Aceptar", "#url_sistema");
        ok = false;
    }
    return ok;
}




/*** GUARDAR USUARIO ***/
function guardarSistemaAjax(){
    if (validarSistema()){
        var pDatosFormulario = $("#formularioSistema").serialize();
        console.log(pDatosFormulario);
        var pUrl= 'sistema/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarSistemaAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarSistemaAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Sistema Guardado","Aceptar","#id_sistema");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_sistema");
    }
}

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
    $('#nombre_sistema').select();
}

function limpiarFormulario(){
    $("#id_sistema").val(0);
    $("#nombre_sistema").val('');
    $("#url_sistema").val('');
    $("#id_sistema").select();
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
    $("#nombre_sistema").focus();
    $("#nombre_sistema").select();
}
/*** ELIMINAR USUARIO ***/
function confirmaEliminar(){
    estaSeguro("Se eliminara el registro. Esta seguro/a ?","eliminarSistemaAjax()","#id_sistema");
}
function eliminarSistemaAjax(){
    if ($("#id_sistema").val().trim()==="0" || $("#id_sistema").val().trim()===""){
        mensaje("Sistema invalido","Aceptar","#id_sistema");
    }else{
        var pDatosFormulario = $("#formularioSistema").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'sistema/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarSistemaAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarSistemaAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Sistema Eliminado","Aceptar","#id_sistema");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_sistema");
    }
}
