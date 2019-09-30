/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function inicializarFrmClientes(){
    $("#s_nombre_formulario").text('Clientes');
    $("#id_cliente").focus();
    $("#id_cliente").select();
    $("#id_cliente").on('change',function(){
       if ($("#id_cliente").val()==="0"){
            limpiarCliente();
       }else{
            buscarIdClienteAjax();
       }
    });
    //Cliente
    $("#id_persona_cliente").on('change', function () {
        //console.log($("#id_persona").val());
        if ($("#id_persona_cliente").val().trim() === "") {
            $("#id_persona_cliente").val("0");
        }
        
        if ($("#id_persona_cliente").val() !== "0") {
            buscarIdPersonaClienteAjax();
        }
    });
    //Cobrador
    $("#id_persona_cobrador").on('change', function () {
        //console.log($("#id_persona").val());
        if ($("#id_persona_cobrador").val().trim() === "") {
            $("#id_persona_cobrador").val("0");
        }
        
        if ($("#id_persona_cobrador").val() !== "0") {
            buscarIdPersonaCobradorAjax();
        }
    });
    //Vendedor
    $("#id_persona_vendedor").on('change', function () {
        //console.log($("#id_persona").val());
        if ($("#id_persona_vendedor").val().trim() === "") {
            $("#id_persona_vendedor").val("0");
        }
        
        if ($("#id_persona_vendedor").val() !== "0") {
            buscarIdPersonaVendedorAjax();
        }
    });
    
    $("#botonGuardar").on('click',function(){
       guardarClienteAjax(); 
    });
    $("#botonEliminar").on('click',function(){
       confirmaEliminar(); 
    });
    
    siguienteCampo('#id_cliente', '#id_persona_cliente', false);
    siguienteCampo('#id_persona_cliente', '#id_persona_cobrador', false);
    siguienteCampo('#id_persona_cobrador', '#id_persona_vendedor', false);
    siguienteCampo('#id_persona_vendedor', '#botonGuardar', false);
}

function validarCliente(){
    var ok = true;
    var nombre_persona = $("#nombre_persona_cliente").val();
    var id_persona = $("#id_persona_cliente").val();
    if(nombre_persona.trim() === ""){
        mensaje("Nombre vacio", "Aceptar", "#nombre_persona_cliente");
        ok = false;
    }else if(id_persona.trim() === ""){
        mensaje("Debe seleccionar una persona", "Aceptar", "#id_persona_cliente");
        ok = false;
    }
    return ok;
}

/*** GUARDAR CLIENTE ***/
function guardarClienteAjax(){
    if (validarCliente()){
        var pDatosCliente = $("#formularioCliente").serialize();
        console.log(pDatosCliente);
        var pUrl= 'cliente/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarClienteAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosCliente, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarClienteAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Cliente Guardado","Aceptar","#id_cliente");
        limpiarCliente();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_cliente");
    }
}

/*** BUSCAR ID CLIENTE ***/
function buscarIdClienteAjax(){
    var pDatosCliente = "id_cliente="+$("#id_cliente").val();
    //console.log(pDatosCliente);
    var pUrl= 'cliente/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdClienteAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosCliente, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function buscarIdClienteAjaxSuccess(json){
    //console.log(json);
    //Cliente
    $('#id_cliente').val(json.id_cliente);
    $('#nombre_persona_cliente').val(json.nombre_persona_cliente);
    $('#id_persona_cliente').val(json.id_persona_cliente);
    //Vendedor
    $('#id_vendedor').val(json.id_vendedor);
    $('#nombre_persona_vendedor').val(json.nombre_persona_vendedor);
    //Cobrador
    $('#id_cobrador').val(json.id_cobrador);
    $('#nombre_persona_cobrador').val(json.nombre_persona_cobrador);
}

function limpiarCliente(){
    //Cliente
    $("#id_cliente").val(0);
    $("#nombre_persona_cliente").val('');
    //Cobrador
    $("#id_cobrador").val(0);
    $("#nombre_persona_cobrador").val('');
    //Vendedor
    $("#id_vendedor").val(0);
    $("#nombre_persona_vendedor").val('');
    
    $("#id_persona_cliente").val(0);
    $("#id_cliente").select();
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
    var id_persona_cliente = $this.find('td').eq(1).text();
    var nombre = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_cliente").val(id);
    $("#id_persona_cliente").val(id);
    $("#nombre_persona_cliente").val(nombre);
    buscarIdClienteAjax();
    $("#id_cliente").focus();
}
/*** ELIMINAR CLIENTE ***/
function confirmaEliminar(){
    estaSeguro("Se eliminara el registro. Esta seguro/a ?","eliminarClienteAjax()","#id_cliente");
}
function eliminarClienteAjax(){
    if ($("#id_cliente").val().trim()==="0" || $("#id_cliente").val().trim()===""){
        mensaje("Cliente invalido","Aceptar","#id_cliente");
    }else{
        var pDatosCliente = $("#formularioCliente").serialize();
        //console.log(pDatosCliente);
        var pUrl= 'cliente/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarClienteAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosCliente, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarClienteAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Cliente Eliminado","Aceptar","#id_cliente");
        limpiarCliente();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_cliente");
    }
}
/*** PERSONA CLIENTE ***/
function buscarIdPersonaClienteAjax(){
    var pDatosPersona = "id_persona="+$("#id_persona_cliente").val();
    //console.log(pDatosPersona);
    var pUrl= 'persona/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdPersonaClienteAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosPersona, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdPersonaClienteAjaxSuccess(json){
    //console.log(json);
    if (json.id_persona === 0 || json.id_persona === ""){
        mensaje("No existe persona seleccionada", "Aceptar", "#id_persona_cliente");
        $("#id_persona_cliente").val(0);
    }else{
        $("#nombre_persona_cliente").val(json.nombre_persona);
    }
}

/*** PERSONA COBRADOR ***/
function buscarIdPersonaCobradorAjax(){
    var pDatosPersona = "id_persona="+$("#id_persona_cobrador").val();
    //console.log(pDatosPersona);
    var pUrl= 'persona/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdPersonaCobradorAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosPersona, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdPersonaCobradorAjaxSuccess(json){
    //console.log(json);
    if (json.id_persona === 0 || json.id_persona === ""){
        mensaje("No existe persona seleccionada", "Aceptar", "#id_persona_cobrador");
        $("#id_persona_cobrador").val(0);
    }else{
        $("#nombre_persona_cobrador").val(json.nombre_persona);
    }
}
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
    //$("#id_persona_cobrador").val(id);
    $("#nombre_persona_cobrador").val(nombre);
    $("#id_cobrador").focus();
}

/*** PERSONA VENDEDOR ***/
function buscarIdPersonaVendedorAjax(){
    var pDatosPersona = "id_persona="+$("#id_persona_vendedor").val();
    //console.log(pDatosPersona);
    var pUrl= 'persona/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdPersonaVendedorAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosPersona, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdPersonaVendedorAjaxSuccess(json){
    //console.log(json);
    if (json.id_persona === 0 || json.id_persona === ""){
        mensaje("No existe persona seleccionada", "Aceptar", "#id_persona_vendedor");
        $("#id_persona_vendedor").val(0);
    }else{
        $("#nombre_persona_vendedor").val(json.nombre_persona);
    }
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
    //$("#id_persona").val(id);
    $("#nombre_persona_vendedor").val(nombre);
    //$('#matricula_vendedor').val(matricula_vendedor);
    $("#id_vendedor").focus();
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
    $("#id_persona_cliente").val(id);
    buscarIdPersonaClienteAjax();    
    $("#id_cliente").focus();
}