/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function inicializarFrmPersonas(){
    $("#s_nombre_persona").text('Personas');
    $("#id_persona").focus();
    $("#id_persona").select();
    $("#fecha_nacimiento_persona").datepicker();
    $("#id_persona").on('change',function(){
       if ($("#id_persona").val()==="0"){
            limpiarPersona();
       }else{
            buscarIdPersonaAjax();
       }
    });
    $("#nombre_fantasia_persona").on('focus',function (){ 
        //console.log($("#nombre_persona").val());
        if($("#nombre_fantasia_persona").val().trim()===""){
            $("#nombre_fantasia_persona").val($("#nombre_persona").val());
        }
    });
    $("#botonGuardar").on('click',function(){
       guardarPersonaAjax(); 
    });
    $("#botonEliminar").on('click',function(){
       confirmaEliminar(); 
    });
    $("#verImagenPersona").on('click',function(){
       $('#imagenPersona').click(); 
    });
    $('#imagenPersona').on('change',function() {mostrar_preview(this);});
    siguienteCampo('#id_persona', '#nombre_persona', false);
    siguienteCampo('#nombre_persona', '#fisica_persona', false);
    siguienteCampo('#fisica_persona', '#nombre_fantasia_persona', false);
    siguienteCampo('#nombre_fantasia_persona', '#direccion_persona', false);
    siguienteCampo('#direccion_persona', '#telefono1_persona', false);
    siguienteCampo('#telefono1_persona', '#ci_persona', false);
    siguienteCampo('#ci_persona', '#telefono2_persona', false);
    siguienteCampo('#telefono2_persona', '#ruc_persona', false);
    siguienteCampo('#ruc_persona', '#email_persona', false);
    siguienteCampo('#email_persona', '#dni_persona', false);
    siguienteCampo('#dni_persona', '#web_persona', false);
    siguienteCampo('#web_persona', '#fecha_nacimiento_persona', false);
    siguienteCampo('#fecha_nacimiento_persona', '#botonGuardar', true);
    habilitar_agregar();
}

function validarPersona(){
    var ok = true;

    var nombre_persona = $("#nombre_persona").val();
    if(nombre_persona.trim() === ""){
        mensaje("Nombre vacio", "Aceptar", "#nombre_persona");
        ok = false;
    }
    return ok;
}
/*** GUARDAR PERSONA ***/
function guardarPersonaAjax(){
    if (validarPersona()){
        var pDatosPersona = $("#formularioPersona").serialize();
        //console.log(pDatosPersona);
        var pUrl= 'persona/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarPersonaAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosPersona, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarPersonaAjaxSuccess(json){
    //console.log(json);
    if(json.correcto){
        mensaje("Persona Guardada","Aceptar","#id_persona");
        limpiarPersona();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_persona");
    }
}

/*** BUSCAR ID PERSONA ***/
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
    $('#id_persona').val(json.id_persona);
    $('#nombre_persona').val(json.nombre_persona);
    $('#nombre_fantasia_persona').val(json.nombre_persona);
    $('#fisica_persona').prop( "checked", json.fisica_persona );
    $('#ci_persona').val(json.ci_persona);
    $('#ruc_persona').val(json.ruc_persona);
    $('#dni_persona').val(json.dni_persona);
    $('#telefono1_persona').val(json.telefono1_persona);
    $('#telefono2_persona').val(json.telefono2_persona);
    $('#email_persona').val(json.email_persona);
    $('#web_persona').val(json.web_persona);
    $('#direccion_persona').val(json.direccion_persona);
    $('#fecha_nacimiento_persona').val(json.fecha_nacimiento_persona);
    $('#nombre_persona').select();
    deshabilitar_agregar();
}

function limpiarPersona(){
    $("#id_persona").val(0);
    $("#nombre_persona").val('');
    $('#nombre_fantasia_persona').val('');
    $('#fisica_persona').attr("checked","checked");
    $('#ci_persona').val('');
    $('#ruc_persona').val('');
    $('#dni_persona').val('');
    $('#telefono1_persona').val('');
    $('#telefono2_persona').val('');
    $('#email_persona').val('');
    $('#web_persona').val('');
    $('#direccion_persona').val('');
    $('#fecha_nacimiento_persona').val('');
    $("#id_persona").select();
}
/*** BUSCAR ROL POR NOMBRE ***/
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
    $("#nombre_persona").focus();
    $("#nombre_persona").select();
    deshabilitar_agregar();
}
/*** ELIMINAR PERSONA ***/
function confirmaEliminar(){
    estaSeguro("Se eliminara el registro. Esta seguro/a ?","eliminarPersonaAjax()","#id_persona");
}
function eliminarPersonaAjax(){
    if ($("#id_persona").val().trim()==="0" || $("#id_persona").val().trim()===""){
        mensaje("Persona invalido","Aceptar","#id_persona");
    }else{
        var pDatosPersona = $("#formularioPersona").serialize();
        //console.log(pDatosPersona);
        var pUrl= 'persona/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarPersonaAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosPersona, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarPersonaAjaxSuccess(json){
    //console.log(json);
    if(json.correcto){
        mensaje("Persona Eliminada","Aceptar","#id_persona");
        limpiarPersona();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_persona");
    }
}
/***** IMAGEN PERSONA ******/
function mostrar_preview(input) {
    //console.log(input);
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $("#verImagenPersona").attr("src",e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

