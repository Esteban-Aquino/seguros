/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function inicializarFrmVendedores(){
    $("#s_nombre_formulario").text('Vendedores');
    $("#id_vendedor").focus();
    $("#id_vendedor").select();
    $("#id_vendedor").on('change',function(){
       if ($("#id_vendedor").val()==="0"){
            limpiarVendedor();
       }else{
            buscarIdVendedorAjax();
       }
    });
    $("#id_persona").on('change', function () {
        //console.log($("#id_persona").val());
        if ($("#id_persona").val().trim() === "") {
            $("#id_persona").val("0");
        }
        
        if ($("#id_persona").val() !== "0") {
            buscarIdPersonaAjax();
        }
    });
    $("#botonGuardar").on('click',function(){
       guardarVendedorAjax(); 
    });
    $("#botonEliminar").on('click',function(){
       confirmaEliminar(); 
    });
    siguienteCampo('#id_vendedor', '#id_persona', false);
    siguienteCampo('#id_persona', '#matricula_vendedor', false);
}

function validarVendedor(){
    var ok = true;
    var nombre_persona = $("#nombre_persona").val();
    var id_persona = $("#id_persona").val();
    if(nombre_persona.trim() === ""){
        mensaje("Nombre vacio", "Aceptar", "#nombre_persona");
        ok = false;
    }else if(id_persona.trim() === ""){
        mensaje("Debe seleccionar una persona", "Aceptar", "#id_persona");
        ok = false;
    }
    return ok;
}




/*** GUARDAR VENDEDOR ***/
function guardarVendedorAjax(){
    if (validarVendedor()){
        var pDatosVendedor = $("#formularioVendedor").serialize();
        console.log(pDatosVendedor);
        var pUrl= 'vendedor/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarVendedorAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosVendedor, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarVendedorAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Vendedor Guardado","Aceptar","#id_vendedor");
        limpiarVendedor();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_vendedor");
    }
}

/*** BUSCAR ID VENDEDOR ***/
function buscarIdVendedorAjax(){
    var pDatosVendedor = "id_vendedor="+$("#id_vendedor").val();
    //console.log(pDatosVendedor);
    var pUrl= 'vendedor/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdVendedorAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosVendedor, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdVendedorAjaxSuccess(json){
    //console.log(json);
    $('#id_vendedor').val(json.id_vendedor);
    $('#nombre_persona').val(json.nombre_persona);
    $('#matricula_vendedor').val(json.matricula_vendedor);
    $('#id_persona').val(json.id_persona);
}

function limpiarVendedor(){
    $("#id_vendedor").val(0);
    $("#nombre_persona").val('');
    $('#matricula_vendedor').val('');
    $("#id_persona").val(0);
    $("#id_vendedor").select();
}

/*** BUSCAR VENDEDOR POR NOMBRE ***/
function BuscarVendedorNombreAjax(){
        var pDatosVendedor = $("#form-buscar").serialize();
        //console.log(pDatosVendedor);
        var pUrl= 'vendedor/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarVendedorNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosVendedor, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function BuscarVendedorNombreAjaxSuccess(json){
    var datos = "";
   //console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_vendedor($(this))'>";
        datos += "<td>" + value.id_vendedor + "</td>";
        datos += "<td>" + value.id_persona + "</td>";
        datos += "<td>" + value.nombre_persona + "</td>";
        datos += "<td>" + value.matricula_vendedor + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_vendedores').html(datos);
}
function seleccionar_vendedor($this) {
    var id = $this.find('td').eq(0).text();
    var id_persona = $this.find('td').eq(1).text();
    var nombre = $this.find('td').eq(2).text();
    var matricula_vendedor = $this.find('td').eq(3).text();
    salir_busqueda();
    $("#id_vendedor").val(id);
    $("#id_persona").val(id);
    $("#nombre_persona").val(nombre);
    $('#matricula_vendedor').val(matricula_vendedor);
    $("#id_vendedor").focus();
}
/*** ELIMINAR VENDEDOR ***/
function confirmaEliminar(){
    estaSeguro("Se eliminara el registro. Esta seguro/a ?","eliminarVendedorAjax()","#id_vendedor");
}
function eliminarVendedorAjax(){
    if ($("#id_vendedor").val().trim()==="0" || $("#id_vendedor").val().trim()===""){
        mensaje("Vendedor invalido","Aceptar","#id_vendedor");
    }else{
        var pDatosVendedor = $("#formularioVendedor").serialize();
        //console.log(pDatosVendedor);
        var pUrl= 'vendedor/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarVendedorAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosVendedor, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarVendedorAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Vendedor Eliminado","Aceptar","#id_vendedor");
        limpiarVendedor();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_vendedor");
    }
}
/*** PERSONA ***/
function buscarIdPersonaAjax(){
    var pDatosPersona = "id_persona="+$("#id_persona").val();
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
        mensaje("No existe persona seleccionada", "Aceptar", "#id_persona");
        $("#id_persona").val(0);
    }else{
        $("#nombre_persona").val(json.nombre_persona);
        $("#matricula_vendedor").val(json.matricula_vendedor);
    }
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
    console.log(json);
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
    $("#id_persona").val(id);
    buscarIdPersonaAjax();    
    $("#usuario_usuario").focus();
    $("#usuario_usuario").select();
    deshabilitar_agregar();
}