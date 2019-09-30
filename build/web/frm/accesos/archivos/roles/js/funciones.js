/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function inicializarFrmRoles(){
    $("#s_nombre_formulario").text('Roles');
    $("#id_rol").focus();
    $("#id_rol").select();
    $("#id_rol").on('change',function(){
       if ($("#id_rol").val()==="0"){
            habilitar_agregar();
            limpiarFormulario();
       }else{
            buscarIdRolAjax();
       }
    });
    $("#botonAgregar").on('click',function(){
       agregarRolAjax(); 
    });
    $("#botonModificar").on('click',function(){
       modificarRolAjax(); 
    });
    $("#botonEliminar").on('click',function(){
       confirmaEliminar(); 
    });
    siguienteCampo('#id_rol', '#rol_rol', false);
    siguienteCampo('#rol_rol', '#nombre_rol', false);
    siguienteCampo('#nombre_rol', '#clave_rol', false);
    siguienteCampo('#nombre_rol', '#clave_rol', false);
    siguienteCampo('#clave_rol', '#botonAgregar', false);
    habilitar_agregar();
}

function validarRol(){
    var ok = true;

    var nombre_rol = $("#nombre_rol").val();
    if(nombre_rol.trim() === ""){
        mensaje("Nombre vacio", "Aceptar", "#nombre_rol");
        ok = false;
    }
    return ok;
}
/*** AGREGAR USUARIO ***/
function agregarRolAjax(){
    if (validarRol()){
        var pDatosFormulario = $("#formularioRol").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'rol/agregar';
        var pBeforeSend = "";
        var pSucces = "agregarRolAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function agregarRolAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Rol Agregado","Aceptar","#id_rol");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_rol");
    }
}
/*** BUSCAR ID ROL ***/
function buscarIdRolAjax(){
    var pDatosFormulario = "id_rol="+$("#id_rol").val();
    //console.log(pDatosFormulario);
    var pUrl= 'rol/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdRolAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdRolAjaxSuccess(json){
    console.log(json);
    $('#id_rol').val(json.id_rol);
    $('#nombre_rol').val(json.nombre_rol);
    $('#nombre_rol').select();
}
/*** MODIFICAR USUARIO ***/
function modificarRolAjax(){
    if (validarRol()){
        var pDatosFormulario = $("#formularioRol").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'rol/modificar';
        var pBeforeSend = "";
        var pSucces = "modificarRolAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function modificarRolAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Rol Modificado","Aceptar","#id_rol");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_rol");
    }
}

function limpiarFormulario(){
    $("#id_rol").val(0);
    $("#nombre_rol").val('');
    $("#id_rol").select();
}
/*** BUSCAR ROL POR NOMBRE ***/
function BuscarRolNombreAjax(){
        var pDatosFormulario = $("#form-buscar").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'rol/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarRolNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function BuscarRolNombreAjaxSuccess(json){
    var datos = "";
    console.log(json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_rol($(this))'>";
        datos += "<td>" + value.id_rol + "</td>";
        datos += "<td>" + value.nombre_rol + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_roles').html(datos);
}
function seleccionar_rol($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(1).text();
    var rol = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_rol").val(id);
    $("#nombre_rol").val(nombre);
    $("#nombre_rol").focus();
    $("#nombre_rol").select();
    deshabilitar_agregar();
}
/*** ELIMINAR USUARIO ***/
function confirmaEliminar(){
    estaSeguro("Se eliminara el registro. Esta seguro/a ?","eliminarRolAjax()","#id_rol");
}
function eliminarRolAjax(){
    if ($("#id_rol").val().trim()==="0" || $("#id_rol").val().trim()===""){
        mensaje("Rol invalido","Aceptar","#id_rol");
    }else{
        var pDatosFormulario = $("#formularioRol").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'rol/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarRolAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarRolAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Rol Eliminado","Aceptar","#id_rol");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_rol");
    }
}
