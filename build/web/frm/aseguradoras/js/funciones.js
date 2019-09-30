/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function inicializarFrmAseguradoras(){
    $("#s_nombre_formulario").text('Aseguradoras');
    $("#id_aseguradora").focus();
    $("#id_aseguradora").select();
    $("#id_aseguradora").on('change',function(){
       if ($("#id_aseguradora").val()==="0"){
            limpiarAseguradora();
       }else{
            buscarIdAseguradoraAjax();
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
       guardarAseguradoraAjax(); 
    });
    $("#botonEliminar").on('click',function(){
       confirmaEliminar(); 
    });
    siguienteCampo('#id_aseguradora', '#id_persona', false);
    siguienteCampo('#id_persona', '#nombre_persona', false);
}

function validarAseguradora(){
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




/*** GUARDAR ASEGURADORA ***/
function guardarAseguradoraAjax(){
    if (validarAseguradora()){
        var pDatosAseguradora = $("#formularioAseguradora").serialize();
        console.log(pDatosAseguradora);
        var pUrl= 'aseguradora/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarAseguradoraAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosAseguradora, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarAseguradoraAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Aseguradora Guardado","Aceptar","#id_aseguradora");
        limpiarAseguradora();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_aseguradora");
    }
}

/*** BUSCAR ID ASEGURADORA ***/
function buscarIdAseguradoraAjax(){
    var pDatosAseguradora = "id_aseguradora="+$("#id_aseguradora").val();
    //console.log(pDatosAseguradora);
    var pUrl= 'aseguradora/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdAseguradoraAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosAseguradora, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdAseguradoraAjaxSuccess(json){
    //console.log(json);
    $('#id_aseguradora').val(json.id_aseguradora);
    $('#nombre_persona').val(json.nombre_persona);
    $('#id_persona').val(json.id_persona);
}

function limpiarAseguradora(){
    $("#id_aseguradora").val(0);
    $("#nombre_persona").val('');
    $("#id_persona").val(0);
    $("#id_aseguradora").select();
}

/*** BUSCAR ASEGURADORA POR NOMBRE ***/
function BuscarAseguradoraNombreAjax(){
        var pDatosAseguradora = $("#form-buscar").serialize();
        //console.log(pDatosAseguradora);
        var pUrl= 'aseguradora/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarAseguradoraNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosAseguradora, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function BuscarAseguradoraNombreAjaxSuccess(json){
    var datos = "";
   //console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_aseguradora($(this))'>";
        datos += "<td>" + value.id_aseguradora + "</td>";
        datos += "<td>" + value.id_persona + "</td>";
        datos += "<td>" + value.nombre_persona + "</td>";
        datos += "<td>" + value.nombre_fantasia + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_aseguradoras').html(datos);
}
function seleccionar_aseguradora($this) {
    var id = $this.find('td').eq(0).text();
    var id_persona = $this.find('td').eq(1).text();
    var nombre = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_aseguradora").val(id);
    $("#id_persona").val(id);
    $("#nombre_persona").val(nombre);
    
    $("#nombre_persona").focus();
    $("#nombre_persona").select();
}
/*** ELIMINAR ASEGURADORA ***/
function confirmaEliminar(){
    estaSeguro("Se eliminara el registro. Esta seguro/a ?","eliminarAseguradoraAjax()","#id_aseguradora");
}
function eliminarAseguradoraAjax(){
    if ($("#id_aseguradora").val().trim()==="0" || $("#id_aseguradora").val().trim()===""){
        mensaje("Aseguradora invalido","Aceptar","#id_aseguradora");
    }else{
        var pDatosAseguradora = $("#formularioAseguradora").serialize();
        //console.log(pDatosAseguradora);
        var pUrl= 'aseguradora/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarAseguradoraAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosAseguradora, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarAseguradoraAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Aseguradora Eliminado","Aceptar","#id_aseguradora");
        limpiarAseguradora();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_aseguradora");
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
