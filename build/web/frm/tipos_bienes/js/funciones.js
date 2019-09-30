/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function inicializarFrmTipos_bienes(){
    $("#s_nombre_formulario").text('Tipos_bienes');
    $("#id_tipo_bien").focus();
    $("#id_tipo_bien").select();
    $("#id_tipo_bien").on('change',function(){
       if ($("#id_tipo_bien").val()==="0"){
            habilitar_agregar();
            limpiarFormulario();
       }else{
            buscarIdTipo_bienAjax();
       }
    });
    $("#botonGuardar").on('click',function(){
       guardarTipo_bienAjax(); 
    });
    $("#botonEliminar").on('click',function(){
       confirmaEliminar(); 
    });
    siguienteCampo('#id_tipo_bien', '#nombre_tipo_bien', false);
    siguienteCampo('#nombre_tipo_bien', '#botonGuardar', true);
}

function validarTipo_bien(){
    var ok = true;

    var nombre_tipo_bien = $("#nombre_tipo_bien").val();
    if(nombre_tipo_bien.trim() === ""){
        mensaje("Nombre vacio", "Aceptar", "#nombre_tipo_bien");
        ok = false;
    }
    return ok;
}
/*** GUARDAR TIPO_BIEN ***/
function guardarTipo_bienAjax(){
    if (validarTipo_bien()){
        var pDatosFormulario = $("#formularioTipo_bien").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'tipo_bien/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarTipo_bienAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarTipo_bienAjaxSuccess(json){
    //console.log(json);
    if(json.correcto){
        mensaje("Tipo de bien guardado","Aceptar","#id_tipo_bien");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_tipo_bien");
    }
}
/*** BUSCAR ID TIPO_BIEN ***/
function buscarIdTipo_bienAjax(){
    var pDatosFormulario = "id_tipo_bien="+$("#id_tipo_bien").val();
    //console.log(pDatosFormulario);
    var pUrl= 'tipo_bien/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdTipo_bienAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdTipo_bienAjaxSuccess(json){
    console.log(json);
    $('#id_tipo_bien').val(json.id_tipo_bien);
    $('#nombre_tipo_bien').val(json.nombre_tipo_bien);
    $('#nombre_tipo_bien').select();
}

function limpiarFormulario(){
    $("#id_tipo_bien").val(0);
    $("#nombre_tipo_bien").val('');
    $("#id_tipo_bien").select();
}
/*** BUSCAR TIPO_BIEN POR NOMBRE ***/
function BuscarTipo_bienNombreAjax(){
        var pDatosFormulario = $("#form-buscar").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'tipo_bien/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarTipo_bienNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function BuscarTipo_bienNombreAjaxSuccess(json){
    var datos = "";
    console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_tipo_bien($(this))'>";
        datos += "<td>" + value.id_tipo_bien + "</td>";
        datos += "<td>" + value.nombre_tipo_bien + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_tipos_bienes').html(datos);
}
function seleccionar_tipo_bien($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(1).text();
    var tipo_bien = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_tipo_bien").val(id);
    $("#nombre_tipo_bien").val(nombre);
    $("#nombre_tipo_bien").focus();
    $("#nombre_tipo_bien").select();
    BuscarTipo_bienNombreAjax();
}
/*** ELIMINAR USUARIO ***/
function confirmaEliminar(){
    estaSeguro("Se eliminara el registro. Esta seguro/a ?","eliminarTipo_bienAjax()","#id_tipo_bien");
}
function eliminarTipo_bienAjax(){
    if ($("#id_tipo_bien").val().trim()==="0" || $("#id_tipo_bien").val().trim()===""){
        mensaje("Tipo de bien invalido","Aceptar","#id_tipo_bien");
    }else{
        var pDatosFormulario = $("#formularioTipo_bien").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'tipo_bien/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarTipo_bienAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarTipo_bienAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Tipo de bien Eliminado","Aceptar","#id_tipo_bien");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_tipo_bien");
    }
}
