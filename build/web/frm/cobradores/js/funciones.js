/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function inicializarFrmCobradores(){
    $("#s_nombre_formulario").text('Cobradores');
    $("#id_cobrador").focus();
    $("#id_cobrador").select();
    $("#id_cobrador").on('change',function(){
       if ($("#id_cobrador").val()==="0"){
            limpiarCobrador();
       }else{
            buscarIdCobradorAjax();
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
       guardarCobradorAjax(); 
    });
    $("#botonEliminar").on('click',function(){
       confirmaEliminar(); 
    });
    siguienteCampo('#id_cobrador', '#id_persona', false);
}

function validarCobrador(){
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




/*** GUARDAR COBRADOR ***/
function guardarCobradorAjax(){
    if (validarCobrador()){
        var pDatosCobrador = $("#formularioCobrador").serialize();
        console.log(pDatosCobrador);
        var pUrl= 'cobrador/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarCobradorAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosCobrador, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarCobradorAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Cobrador Guardado","Aceptar","#id_cobrador");
        limpiarCobrador();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_cobrador");
    }
}

/*** BUSCAR ID COBRADOR ***/
function buscarIdCobradorAjax(){
    var pDatosCobrador = "id_cobrador="+$("#id_cobrador").val();
    //console.log(pDatosCobrador);
    var pUrl= 'cobrador/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdCobradorAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosCobrador, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdCobradorAjaxSuccess(json){
    //console.log(json);
    $('#id_cobrador').val(json.id_cobrador);
    $('#nombre_persona').val(json.nombre_persona);
    $('#id_persona').val(json.id_persona);
}

function limpiarCobrador(){
    $("#id_cobrador").val(0);
    $("#nombre_persona").val('');
    $("#id_persona").val(0);
    $("#id_cobrador").select();
}

/*** BUSCAR COBRADOR POR NOMBRE ***/
function BuscarCobradorNombreAjax(){
        var pDatosCobrador = $("#form-buscar").serialize();
        //console.log(pDatosCobrador);
        var pUrl= 'cobrador/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarCobradorNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosCobrador, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function BuscarCobradorNombreAjaxSuccess(json){
    var datos = "";
   //console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_cobrador($(this))'>";
        datos += "<td>" + value.id_cobrador + "</td>";
        datos += "<td>" + value.id_persona + "</td>";
        datos += "<td>" + value.nombre_persona + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_cobradores').html(datos);
}
function seleccionar_cobrador($this) {
    var id = $this.find('td').eq(0).text();
    var id_persona = $this.find('td').eq(1).text();
    var nombre = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_cobrador").val(id);
    $("#id_persona").val(id);
    $("#nombre_persona").val(nombre);
    $("#id_cobrador").focus();
}
/*** ELIMINAR COBRADOR ***/
function confirmaEliminar(){
    estaSeguro("Se eliminara el registro. Esta seguro/a ?","eliminarCobradorAjax()","#id_cobrador");
}
function eliminarCobradorAjax(){
    if ($("#id_cobrador").val().trim()==="0" || $("#id_cobrador").val().trim()===""){
        mensaje("Cobrador invalido","Aceptar","#id_cobrador");
    }else{
        var pDatosCobrador = $("#formularioCobrador").serialize();
        //console.log(pDatosCobrador);
        var pUrl= 'cobrador/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarCobradorAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosCobrador, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarCobradorAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Cobrador Eliminado","Aceptar","#id_cobrador");
        limpiarCobrador();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_cobrador");
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