/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function inicializarFrmBienes(){
    $("#s_nombre_formulario").text('Bienes');
    $("#id_bien").focus();
    $("#id_bien").select();
    $("#id_bien").on('change',function(){
       if ($("#id_bien").val()==="0"){
            limpiarFormulario();
       }else{
            buscarIdBienAjax();
       }
    });
    $("#botonGuardar").on('click',function(){
       guardarBienAjax(); 
    });
    $("#botonEliminar").on('click',function(){
       confirmaEliminar(); 
    });
    siguienteCampo('#id_bien', '#id_cliente', false);
    siguienteCampo('#id_cliente', '#id_tipo_bien', false);
    siguienteCampo('#id_tipo_bien', '#marca_bien', false);
    siguienteCampo('#marca_bien', '#matricula_bien', false);
    siguienteCampo('#matricula_bien', '#anio_bien', false);
    siguienteCampo('#anio_bien', '#color_bien', false);
    siguienteCampo('#color_bien', '#num_motor_bien', false);
    siguienteCampo('#num_motor_bien', '#num_carroceria_bien', false);
    siguienteCampo('#num_carroceria_bien', '#tip_bien', false);
    siguienteCampo('#tip_bien', '#botonGuardar', false);    
    
}

function validarBien(){
    var ok = true;

    var id_cliente = $("#id_cliente").val();
    if(id_cliente.trim() === "" || id_cliente.trim() === "0"){
        mensaje("Debe ingresar cliente", "Aceptar", "#id_cliente");
        ok = false;
    }else if($("#id_tipo_bien").val() === "" || $("#id_tipo_bien").val() === "0") {
        mensaje("Debe ingresar tipo de bien", "Aceptar", "#id_tipo_bien");
        ok = false;
    }
    return ok;
}
/*** GUARDAR BIEN ***/
function guardarBienAjax(){
    if (validarBien()){
        var pDatosFormulario = $("#formularioBien").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'bien/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarBienAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarBienAjaxSuccess(json){
    //console.log(json);
    if(json.correcto){
        mensaje("Bien guardado","Aceptar","#id_bien");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_bien");
    }
}
/*** BUSCAR ID BIEN ***/
function buscarIdBienAjax(){
    var pDatosFormulario = "id_bien="+$("#id_bien").val();
    //console.log(pDatosFormulario);
    var pUrl= 'bien/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdBienAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdBienAjaxSuccess(json){
    console.log(json);
    $('#id_bien').val(json.id_bien);
    $('#id_cliente').val(json.id_cliente);
    $('#nombre_persona_cliente').val(json.nombre_persona_cliente);
    $('#id_tipo_bien').val(json.id_tipo_bien);
    $('#nombre_tipo_bien').val(json.nombre_tipo_bien);
    $('#marca_bien').val(json.marca_bien);
    $('#modelo_bien').val(json.modelo_bien);
    $('#matricula_bien').val(json.matricula_bien);
    $('#anio_bien').val(json.anio_bien);
    $('#color_bien').val(json.color_bien);
    $('#num_motor_bien').val(json.num_motor_bien);
    $('#num_carroceria_bien').val(json.num_carroceria_bien);
    $('#tip_bien').val(json.tip_bien);
    $('#descripcion_bien').val(json.descripcion_bien);
    $('#id_bien').focus();
    $('#id_bien').select();
}

function limpiarFormulario(){
    $('#id_bien').val(0);
    $('#id_cliente').val(0);
    $('#nombre_persona_cliente').val("");
    $('#id_tipo_bien').val("");
    $('#nombre_tipo_bien').val("");
    $('#marca_bien').val("");
    $('#modelo_bien').val("");
    $('#matricula_bien').val("");
    $('#anio_bien').val(0);
    $('#color_bien').val("");
    $('#num_motor_bien').val("");
    $('#num_carroceria_bien').val("");
    $('#tip_bien').val("");
    $('#descripcion_bien').val("");
    $('#id_bien').focus();
    $('#id_bien').select();
}

/*** BUSCAR BIEN POR NOMBRE ***/
function BuscarBienNombreAjax(){
        var pDatosFormulario = $("#form-buscar").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'bien/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarBienNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function BuscarBienNombreAjaxSuccess(json){
    var datos = "";
    console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_bien($(this))'>";
        datos += "<td>" + value.id_bien + "</td>";
        datos += "<td>" + value.nombre_tipo_bien + "</td>";
        datos += "<td>" + value.nombre_persona_cliente + "</td>";
        datos += "<td>" + value.marca_bien + "</td>";
        datos += "<td>" + value.modelo_bien + "</td>";
        datos += "<td>" + value.anio_bien + "</td>";
        datos += "<td>" + value.matricula_bien + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_bienes').html(datos);
}
function seleccionar_bien($this) {
    var id_bien = $this.find('td').eq(0).text();
    salir_busqueda();
    $("#id_bien").val(id_bien);
    $("#id_bien").focus();
    $("#id_bien").select();
    buscarIdBienAjax();
}
/*** ELIMINAR BIEN ***/
function confirmaEliminar(){
    estaSeguro("Se eliminara el registro. Esta seguro/a ?","eliminarBienAjax()","#id_bien");
}
function eliminarBienAjax(){
    if ($("#id_bien").val().trim()==="0" || $("#id_bien").val().trim()===""){
        mensaje("Tipo de bien invalido","Aceptar","#id_bien");
    }else{
        var pDatosFormulario = $("#formularioBien").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'bien/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarBienAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarBienAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Tipo de bien Eliminado","Aceptar","#id_bien");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_bien");
    }
}

/*** BUSCAR CLIENTE POR NOMBRE ***/
function BuscarClienteNombreAjax(){
        var pDatosCliente = $("#form-buscar").serialize();
        //console.log(pDatosCliente);
        var pUrl= 'cliente/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarClienteNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosCliente, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function BuscarClienteNombreAjaxSuccess(json){
    var datos = "";
   //console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_cliente($(this))'>";
        datos += "<td>" + value.id_cliente + "</td>";
        datos += "<td>" + value.id_persona_cliente + "</td>";
        datos += "<td>" + value.nombre_persona_cliente + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_clientes').html(datos);
}
function seleccionar_cliente($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_cliente").val(id);
    $("#nombre_persona_cliente").val(nombre);
    $("#id_cliente").focus();
    $("#id_cliente").select();
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
    salir_busqueda();
    $("#id_tipo_bien").val(id);
    $("#nombre_tipo_bien").val(nombre);
    $("#nombre_tipo_bien").focus();
    $("#nombre_tipo_bien").select();
}
