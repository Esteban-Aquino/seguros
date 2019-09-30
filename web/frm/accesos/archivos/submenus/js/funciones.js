/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function inicializarFrmSubmenus(){
    $("#s_nombre_formulario").text('Submenus');
    $("#id_submenu").focus();
    $("#id_submenu").select();
    $("#id_submenu").on('change',function(){
       if ($("#id_submenu").val()==="0"){
            limpiarFormulario();
       }else{
            buscarIdSubmenuAjax();
       }
    });
    $("#botonGuardar").on('click',function(){
       guardarSubmenuAjax(); 
    });
    $("#botonEliminar").on('click',function(){
       confirmaEliminar(); 
    });
    siguienteCampo('#id_submenu', '#nombre_submenu', false);
    siguienteCampo('#nombre_submenu', '#url_submenu', false);
    siguienteCampo('#url_submenu', '#botonGuardar', false);
}

function validarSubmenu(){
    var ok = true;
    var nombre_submenu = $("#nombre_submenu").val();
    var url_submenu = $("#url_submenu").val();
    if(nombre_submenu.trim() === ""){
        mensaje("Nombre vacio", "Aceptar", "#nombre_submenu");
        ok = false;
    }else if(url_submenu.trim() === ""){
        mensaje("Url vacio", "Aceptar", "#url_submenu");
        ok = false;
    }
    return ok;
}




/*** GUARDAR USUARIO ***/
function guardarSubmenuAjax(){
    if (validarSubmenu()){
        var pDatosFormulario = $("#formularioSubmenu").serialize();
        console.log(pDatosFormulario);
        var pUrl= 'submenu/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarSubmenuAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarSubmenuAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Submenu Guardado","Aceptar","#id_submenu");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_submenu");
    }
}

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
    $('#nombre_submenu').select();
}

function limpiarFormulario(){
    $("#id_submenu").val(0);
    $("#nombre_submenu").val('');
    $("#url_submenu").val('');
    $("#id_submenu").select();
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
    $("#nombre_submenu").focus();
    $("#nombre_submenu").select();
}
/*** ELIMINAR USUARIO ***/
function confirmaEliminar(){
    estaSeguro("Se eliminara el registro. Esta seguro/a ?","eliminarSubmenuAjax()","#id_submenu");
}
function eliminarSubmenuAjax(){
    if ($("#id_submenu").val().trim()==="0" || $("#id_submenu").val().trim()===""){
        mensaje("Submenu invalido","Aceptar","#id_submenu");
    }else{
        var pDatosFormulario = $("#formularioSubmenu").serialize();
        //console.log(pDatosFormulario);
        var pUrl= 'submenu/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarSubmenuAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarSubmenuAjaxSuccess(json){
    console.log(json);
    if(json.correcto){
        mensaje("Submenu Eliminado","Aceptar","#id_submenu");
        limpiarFormulario();
    }else if(!json.agregado){
        mensaje(json.error,"Aceptar","#id_submenu");
    }
}
