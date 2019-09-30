function inicializar_formulario() {
    $('#id_rol').focus();
    $('#id_rol').select();
    siguienteCampo('#id_rol', '#boton-buscar-rol', true);
}

function buscar_id_rol() {
    var id_rol = $('#id_rol').val();
    if (id_rol.trim() === '') {
        mensaje("Id no se puede dejar vacio", "Aceptar", "#id_rol");
    } else {
        buscarIdRolAjax(id_rol);
    }
}
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
    //console.log(json);
    $('#id_rol').val(json.id_rol);
    $('#nombre_rol').val(json.nombre_rol);
    $('#id_rol').select();
    buscarPermisosIdRolAjax();
}

function buscarPermisosIdRolAjax(){
    var pDatosFormulario = "id_rol="+$("#id_rol").val();
    //console.log(pDatosFormulario);
    var pUrl= 'permiso/buscar/rol/id';
    var pBeforeSend = "";
    var pSucces = "buscarPermisosIdRolAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function buscarPermisosIdRolAjaxSuccess(json){
    //console.log(json);
    var datos = '';
    datos += '';
    var datos = "";
    var total = 0;
    $.each(json, function (key, value) {
        var agregar = '<input type="checkbox"  >';
        var modificar = '<input type="checkbox"  >';
        var eliminar = '<input type="checkbox"  >';
        var consultar = '<input type="checkbox"  >';
        var listar = '<input type="checkbox"  >';
        var informes = '<input type="checkbox"  >';
        var exportar = '<input type="checkbox"  >';
        
        if (value.agregar_permiso) {
            agregar = '<input type="checkbox" checked="checked">';
        }
        if (value.modificar_permiso) {
            modificar = '<input type="checkbox" checked="checked">';
        }
        if (value.eliminar_permiso) {
            eliminar = '<input type="checkbox" checked="checked">';
        }
        if (value.consultar_permiso) {
            consultar = '<input type="checkbox" checked="checked">';
        }
        if (value.listar_permiso) {
            listar = '<input type="checkbox" checked="checked">';
        }
        if (value.informes_permiso) {
            informes = '<input type="checkbox" checked="checked">';
        }
        if (value.exportar_permiso) {
            exportar = '<input type="checkbox" checked="checked">';
        }
        
        datos += "<tr>";
        datos += "<td class='derecha oculto'>" + value.id_permiso + "</td>";
        datos += "<td class='derecha'>" + value.id_formulario + "</td>";
        datos += "<td>" + value.nombre_formulario + "</td>";
        datos += "<td>" + value.nombre_sistema + "</td>";
        datos += "<td>" + value.nombre_submenu + "</td>";
        datos += "<td class='centrado' onclick='seleccionar_check($(this))' >" + agregar + '</td>';
        datos += "<td class='centrado' onclick='seleccionar_check($(this))'>" + modificar + "</td>";
        datos += "<td class='centrado' onclick='seleccionar_check($(this))'>" + eliminar + "</td>";
        datos += "<td class='centrado' onclick='seleccionar_check($(this))'>" + consultar + "</td>";
        datos += "<td class='centrado' onclick='seleccionar_check($(this))'>" + listar + "</td>";
        datos += "<td class='centrado' onclick='seleccionar_check($(this))'>" + informes + "</td>";
        datos += "<td class='centrado' onclick='seleccionar_check($(this))'>" + exportar + "</td>";
        datos += "</tr>";
        total++; 
    });
    if (datos==='') {
        datos+="<tr><td colspan='5'>No existen registros ...</td></tr>";
    }
    $('#tbody_datos_permisos').html(datos);
    $('#tbody_datos_permisos input').on('click',function(){$(this).click();}); //Para que funcione el click superior seleccionar_check
    $('#permisos-totales').html("TOTAL REGISTROS: "+total);
}

function seleccionar_check($this){
        //console.log($this);
        $this.find("input").eq(0).click();
    }
function seleccionar_todo($this){
    //console.log($this.parent().parent().children().index($this.parent()));
    var vindex = $this.parent().parent().children().index($this.parent())-4;
    var filas = $('#tbody_datos_permisos tr');
    $.each(filas, function (index, fila) {
        $(this).find('input').eq(vindex).prop('checked',$this.prop('checked'));        
     });
}
/*** GUARDAR ***/
function guardar(){
    var id_rol = $('#id_rol').val();
    var filas = $('#tbody_datos_permisos tr');
    var jsonString = '[';
    $.each(filas, function (index, fila) {
        var id_permiso = $(this).find('td').eq(0).text();
        var id_formulario = $(this).find('td').eq(1).text();
        var agregar_permiso = $(this).find('input').eq(0).prop('checked');
        var modificar_permiso = $(this).find('input').eq(1).prop('checked');
        var eliminar_permiso = $(this).find('input').eq(2).prop('checked');
        var consultar_permiso = $(this).find('input').eq(3).prop('checked');
        var listar_permiso = $(this).find('input').eq(4).prop('checked');
        var informes_permiso = $(this).find('input').eq(5).prop('checked');
        var exportar_permiso = $(this).find('input').eq(6).prop('checked');
        
        jsonString += '{';
        jsonString += '"id_permiso":"' + id_permiso + '",';
        jsonString += '"id_rol":"' + id_rol + '",';
        jsonString += '"id_formulario":"' + id_formulario + '",';
        jsonString += '"agregar_permiso":"' + agregar_permiso + '",';
        jsonString += '"modificar_permiso":"' + modificar_permiso + '",';
        jsonString += '"eliminar_permiso":"' + eliminar_permiso + '",';
        jsonString += '"consultar_permiso":"' + consultar_permiso + '",';
        jsonString += '"listar_permiso":"' + listar_permiso + '",';
        jsonString += '"informes_permiso":"' + informes_permiso + '",';
        jsonString += '"exportar_permiso":"' + exportar_permiso + '"';
        jsonString += '},';
    });
    jsonString = jsonString.substr(0, (jsonString.length - 1));
    jsonString += ']';
    
    //console.log(jsonString);
    guardarAjax(jsonString);
}

function guardarAjax(Json){
        var pDatosFormulario = "datos="+Json;
        //console.log(pDatosFormulario);
        var pUrl= 'permiso/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function guardarAjaxSuccess(json) {
    //console.log(json);
    if(json.guardado){
        mensaje("Guardado correctamente.","Aceptar","#id_rol");
        buscarPermisosIdRolAjax();
    }else if(!json.guardado){
        mensaje(json.error,"Aceptar","#id_rol");
    }
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
    $('#tbody_datos').html(datos);
}
function seleccionar_rol($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(1).text();
    var rol = $this.find('td').eq(2).text();
    salir_busqueda();
    $("#id_rol").val(id);
    $("#nombre_rol").val(nombre);
    buscarPermisosIdRolAjax();
}

/*
function inicializar_formulario() {
    $('#id_rol').focus();
    $('#id_rol').select();
    siguienteCampo('#id_rol', '#boton-buscar-rol', true);
}

function buscar_id_rol() {
    var id_rol = $('#id_rol').val();
    if (id_rol.trim() === '') {
        mostrar_mensaje("Mensaje del Sistema", "Id no se puede dejar vacio",
                "Aceptar", "#id_rol");
    } else {
        buscar_id_rol_ajax(id_rol);
    }
}

function buscar_id_rol_ajax(id_rol) {
    var datosFormulario = 'id_rol=' + id_rol;
    console.log(datosFormulario);
    $.ajax({
        type: 'POST',
        url: 'rol/buscar/id',
        data: datosFormulario,
        dataType: 'json',
        beforeSend: function (objeto) {
        },
        success: function (json) {
            console.log(json);
            $("#id_rol").val(json.id_rol);
            $("#nombre_rol").val(json.nombre_rol);
            listar_formularios_ajax();
        },
        error: function (e) {
            mostrar_mensaje("Mensaje del Sistema", "ERROR: No se puede conectar al servidor",
                    "Aceptar", "#rol_rol");
        },
        complete: function (objeto, exito, error) {
        }
    });
}

function seleccionar_rol($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(1).text();
    
    salir_busqueda();
    
    $("#id_rol").val(id);
    $("#nombre_rol").val(nombre);

    $("#nombre_rol").focus();
    $("#nombre_rol").select();
    listar_formularios_ajax();
}

function buscar_nombre_rol_ajax() {
    var datosFormulario = $('#form-buscar').serialize();
    console.log(datosFormulario);
    $.ajax({
        type: 'POST',
        url: 'rol/buscar/nombre',
        data: datosFormulario,
        dataType: 'json',
        beforeSend: function (objeto) {
        },
        success: function (json) {
            console.log(json);
            mostrar_datos_rol(json);
        },
        error: function (e) {
            mostrar_mensaje("Mensaje del Sistema", "ERROR: No se puede conectar al servidor",
                    "Aceptar", "#buscar_texto");
        },
        complete: function (objeto, exito, error) {
        }
    });
}

function mostrar_datos_rol(json) {
    var datos = "";
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_rol($(this))'>";
        datos += "<td>" + value.id_rol + "</td>";
        datos += "<td>" + value.nombre_rol + "</td>";
        datos += "</tr>";
    });
    if (datos==='') {
        datos+="<tr><td colspan='2'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos').html(datos);
}

function listar_formularios_ajax() {
    var datosFormulario = 'id_rol='+$('#id_rol').val();
    $.ajax({
        type: 'POST',
        url: 'formulario/listar',
        data: datosFormulario,
        dataType: 'json',
        beforeSend: function (objeto) {
        },
        success: function (json) {
            console.log(json);
            mostrar_datos_formularios(json);
        },
        error: function (e) {
            mostrar_mensaje("Mensaje del Sistema", "ERROR: No se puede conectar al servidor",
                    "Aceptar", "#id_rol");
        },
        complete: function (objeto, exito, error) {
        }
    });
}

function mostrar_datos_formularios(json) {
    var datos = "";
    var total = 0;
    $.each(json, function (key, value) {
        var agregar = '<input type="checkbox">';
        var modificar = '<input type="checkbox">';
        var eliminar = '<input type="checkbox">';
        var consultar = '<input type="checkbox">';
        var listar = '<input type="checkbox">';
        var informes = '<input type="checkbox">';
        var exportar = '<input type="checkbox">';
        
        if (value.agregar_permiso) {
            agregar = '<input type="checkbox" checked="checked">';
        }
        if (value.modificar_permiso) {
            modificar = '<input type="checkbox" checked="checked">';
        }
        if (value.eliminar_permiso) {
            eliminar = '<input type="checkbox" checked="checked">';
        }
        if (value.consultar_permiso) {
            consultar = '<input type="checkbox" checked="checked">';
        }
        if (value.listar_permiso) {
            listar = '<input type="checkbox" checked="checked">';
        }
        if (value.informes_permiso) {
            informes = '<input type="checkbox" checked="checked">';
        }
        if (value.exportar_permiso) {
            exportar = '<input type="checkbox" checked="checked">';
        }
        
        datos += "<tr>";
        datos += "<td class='derecha'>" + value.id_permiso + "</td>";
        datos += "<td class='derecha'>" + value.id_formulario + "</td>";
        datos += "<td>" + value.nombre_formulario + "</td>";
        datos += "<td>" + value.nombre_sistema + "</td>";
        datos += "<td>" + value.nombre_submenu + "</td>";
        datos += "<td class='centrado'>" + agregar + "</td>";
        datos += "<td class='centrado'>" + modificar + "</td>";
        datos += "<td class='centrado'>" + eliminar + "</td>";
        datos += "<td class='centrado'>" + consultar + "</td>";
        datos += "<td class='centrado'>" + listar + "</td>";
        datos += "<td class='centrado'>" + informes + "</td>";
        datos += "<td class='centrado'>" + exportar + "</td>";
        datos += "</tr>";
        total++; 
    });
    if (datos==='') {
        datos+="<tr><td colspan='5'>No existen registros ...</td></tr>";
    }
    $('#tbody_datos_permisos').html(datos);
    $('#permisos-totales').html("TOTAL REGISTROS: "+total);
}

function guardar(){
    var id_rol = $('#id_rol').val();
    var filas = $('#tbody_datos_permisos tr');
    var jsonString = '[';
    $.each(filas, function (index, fila) {
        var id_permiso = $(this).find('td').eq(0).text();
        var id_formulario = $(this).find('td').eq(1).text();
        var agregar_permiso = $(this).find('input').eq(0).prop('checked');
        var modificar_permiso = $(this).find('input').eq(1).prop('checked');
        var eliminar_permiso = $(this).find('input').eq(2).prop('checked');
        var consultar_permiso = $(this).find('input').eq(3).prop('checked');
        var listar_permiso = $(this).find('input').eq(4).prop('checked');
        var informes_permiso = $(this).find('input').eq(5).prop('checked');
        var exportar_permiso = $(this).find('input').eq(6).prop('checked');
        
        jsonString += '{';
        jsonString += '"id_permiso":"' + id_permiso + '",';
        jsonString += '"id_rol":"' + id_rol + '",';
        jsonString += '"id_formulario":"' + id_formulario + '",';
        jsonString += '"agregar_permiso":"' + agregar_permiso + '",';
        jsonString += '"modificar_permiso":"' + modificar_permiso + '",';
        jsonString += '"eliminar_permiso":"' + eliminar_permiso + '",';
        jsonString += '"consultar_permiso":"' + consultar_permiso + '",';
        jsonString += '"listar_permiso":"' + listar_permiso + '",';
        jsonString += '"informes_permiso":"' + informes_permiso + '",';
        jsonString += '"exportar_permiso":"' + exportar_permiso + '"';
        jsonString += '},';
    });
    jsonString = jsonString.substr(0, (jsonString.length - 1));
    jsonString += ']';
    console.log(jsonString);
    guardar_ajax(jsonString);
}

function guardar_ajax(jsonString) {
    var datosFormulario = 'datos=' + jsonString;
    console.log(datosFormulario);
    $.ajax({
        type: 'POST',
        url: 'permiso/guardar',
        data: datosFormulario,
        dataType: 'json',
        beforeSend: function (objeto) {
        },
        success: function (json) {
            console.log(json);
            if (json.guardado === true) {
                $('#id_rol').focus();
                $('#id_rol').select();
            } else {
                mostrar_mensaje("Mensaje del Empresa", "ERROR: No se pudo guardar los permisos",
                        "Aceptar", "#id_rol");
            }
        },
        error: function (e) {
            mostrar_mensaje("Mensaje del Sistema", "ERROR: No se puede conectar al servidor",
                    "Aceptar", "#boton-agregar");
        },
        complete: function (objeto, exito, error) {
        }
    });
*/