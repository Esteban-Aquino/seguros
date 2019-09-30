/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var cant_cuotas = 0;
function inicializarFrmPropuestas() {
    $("#s_nombre_formulario").text('Propuestas');
    $("#id_propuesta").focus();
    $("#id_propuesta").select();
    $("#id_propuesta").on('change', function () {
        if ($("#id_propuesta").val() === "0") {
            limpiarFormulario();
        } else {
            buscarIdPropuestaAjax();
        }
    });
    
    $("#fecha_propuesta").on('focus', function () {
        if ($("#fecha_propuesta").val() === "") {
            $('#fecha_propuesta').val(moment(new Date().getD).format("YYYY-MM-DD"));
        }
    });
    $("#botonGuardar").on('click', function () {
        guardarPropuestaAjax();
    });
    $("#botonEliminar").on('click', function () {
        confirmaEliminar();
    });
    formato();
    siguienteCampo('#id_propuesta', '#id_cliente', false);
    siguienteCampo('#id_cliente', '#id_tipo_propuesta', false);
    siguienteCampo('#id_tipo_propuesta', '#marca_propuesta', false);
    siguienteCampo('#marca_propuesta', '#matricula_propuesta', false);
    siguienteCampo('#matricula_propuesta', '#anio_propuesta', false);
    siguienteCampo('#anio_propuesta', '#color_propuesta', false);
    siguienteCampo('#color_propuesta', '#num_motor_propuesta', false);
    siguienteCampo('#num_motor_propuesta', '#num_carroceria_propuesta', false);
    siguienteCampo('#num_carroceria_propuesta', '#tip_propuesta', false);
    siguienteCampo('#tip_propuesta', '#botonGuardar', false);
    //formatea_datepicker();
}
/****DATE PICKER****/
function formatea_datepicker() {
    $(".date").datepicker();
}
/***Agregar bien***/
function agregar_bien() {
    $('body').append('<div id="modalBienes"></div>');
    $.get("frm/propuestas/buscar_bien_modal.html", function (htmlexterno) {
        $("#modalBienes").html(htmlexterno);
        //console.log((('Modal: ' + $("#modalBienes").html());
        $('#divModal').modal('show');
        $('#divModal').on('hidden.bs.modal', function (e) {
            $('#modalBienes').remove();
        });
    });
}
/*** BUSCAR BIEN POR NOMBRE ***/
function BuscarBienNombreAjax() {
    var pDatosFormulario = $("#form-buscar").serialize();
    //console.log(((pDatosFormulario);
    var pUrl = 'bien/buscar/nombre';
    var pBeforeSend = "";
    var pSucces = "BuscarBienNombreAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function BuscarBienNombreAjaxSuccess(json) {
    var datos = "";
    //console.log(((json);
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
    var tipo_bien = $this.find('td').eq(1).text();
    var cliente_bien = $this.find('td').eq(2).text();
    var marca_bien = $this.find('td').eq(3).text();
    var modelo_bien = $this.find('td').eq(4).text();
    var anio_bien = $this.find('td').eq(5).text();
    var matricula_bien = $this.find('td').eq(6).text();
    var datos = '';
    datos += `<tr>`;
    datos += `                    <td style="width: 50px;">` + id_bien + `</td> `;
    datos += `                    <td>` + tipo_bien + `</td> `;
    datos += `                    <td>` + marca_bien + `</td> `;
    datos += `                    <td>` + modelo_bien + `</td> `;
    datos += `                    <td>` + matricula_bien + `</td> `;
    datos += `                    <td>` + anio_bien + `</td> `;
    datos += `                    <td> <input `;
    datos += `                               onchange="suma_bienes()"`;
    datos += `                               min="0" `;
    datos += `                               max="999999999999999999999999999999999" `;
    datos += `                               value="0" `;
    datos += `                               id="suma_asegurada" style="text-align:right" `;
    datos += `                               onclick="$(this).select()" `;
    datos += `                               name="suma_asegurada"`;
    datos += `                               class="form-control input-sm gs"`;
    datos += `                               placeholder="Suma Asegurada"> </th>`;
    datos += `                    <td class="centrado" style="width: 50px;">`;
    datos += `                        <button onclick="eliminar_detalle($(this))" type="button" class="btn btn-danger btn-sm">`;
    datos += `                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>`;
    datos += `                        </button> `;
    datos += `                    </td> `;
    datos += `                </tr> `;
    $('#tbody_bienes_propuestas').append(datos);
    formato();
    $('#botonCancelar').click();
}
function formato() {
    $('.gs').priceFormat({
        prefix: '',
        centsSeparator: ',',
        thousandsSeparator: '.',
        centsLimit: 0,
        allowNegative: false
    });
}
function eliminar_detalle($this) {
    $this.parent().parent().remove();
    suma_bienes();
}
function suma_bienes() {
    var filas = $('#tbody_bienes_propuestas tr');
    $('#suma_asegurada_propuesta').val(0);
    $.each(filas, function (index, fila) {
        var monto = $(this).find('td').eq(6).children('#suma_asegurada').val();
        $('#suma_asegurada_propuesta').val(Number($('#suma_asegurada_propuesta').val()) + Number(monto.replace(',', '').replace('.', '').replace('.', '')));
    });
    formato();
    $('#vigendia_desde_propuesta').focus();
}

function validarPropuesta() {
    var ok = true;

    var id_cliente = $("#id_cliente_propuesta").val();
    if (id_cliente.trim() === "" || id_cliente.trim() === "0") {
        mensaje("Debe ingresar cliente", "Aceptar", "#id_cliente_propuesta");
        ok = false;
    }
    return ok;
}

/*** GUARDAR PROPUESTA ***/
function generar_cuotas() {
    monto_total = $('#premio_propuesta').val().replace(',', '').replace('.', '').replace('.', '');
    cuotas = $('#cant_cuotas_propuesta').val().replace(',', '').replace('.', '').replace('.', '');
    venc_inicial = $('#venc_inicial').val().replace(',', '').replace('.', '').replace('.', '');
    intervalo = $('#intervalo_cuotas').val().replace(',', '').replace('.', '').replace('.', '');
    monto_cuota = Math.trunc(Number(monto_total) / Number(cuotas));
    resto = Math.round(Number(monto_total) - (monto_cuota * Number(cuotas)));
    dt = new Date();
    trow = ' ';
    cant_cuotas = 0;
    $('#tbody_cuotas').html(trow);
    dt.setDate(dt.getDate() + Number(venc_inicial));
    for (i = 1; i <= cuotas; i++) {
        trow += ' <tr> ';
        trow += '      <td id="nro_cuota">' + i + '</td> ';
        trow += '      <td> ';
        trow += '          <input type="date" id="venc' + i + '" name="venc' + i + '" class="form-control input-sm"> ';
        trow += '      </td>';
        trow += '      <td colspan="2"><input type="text" ';
        trow += '                             id="monto' + i + '" ';
        trow += '                             name="monto' + i + '" ';
        trow += '                             class="form-control input-sm gs" ';
        trow += '                             placeholder="monto' + i + '"></td> ';
        trow += '    <th> ';
        trow += '     <button onclick="borrar_cuota($(this))" type="button" class="btn btn-danger btn-sm"> ';
        trow += '       <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> ';
        trow += '     </button> ';
        trow += '   </th> ';
        trow += ' </tr> ';
        $('#tbody_cuotas').append(trow);
        cant_cuotas+= 1;
        //console.log(((moment(dt).format("DD/MM/YYYY"));
        $('#venc' + i + '').val(moment(dt).format("YYYY-MM-DD"));
        trow = ' ';
        if (i === 1) {
            $('#monto' + i + '').val((monto_cuota + resto));
        } else {
            $('#monto' + i + '').val((monto_cuota));
        }
        dt.setDate(dt.getDate() + Number(intervalo));

    }
    suma_cuotas();
    formato();
}
function nueva_cuota(){
    cant_cuotas+= 1;
    var trow = '';
    trow += ' <tr> ';
        trow += '      <td id="nro_cuota">' + cant_cuotas + '</td> ';
        trow += '      <td> ';
        trow += '          <input type="date" id="venc' + cant_cuotas + '" name="venc' + cant_cuotas + '" class="form-control input-sm"> ';
        trow += '      </td>';
        trow += '      <td colspan="2"><input type="text" ';
        trow += '                             id="monto' + cant_cuotas + '" ';
        trow += '                             name="monto' + cant_cuotas + '" ';
        trow += '                             class="form-control input-sm gs" ';
        trow += '                             placeholder="monto"></td> ';
        trow += '    <th> ';
        trow += '     <button onclick="borrar_cuota($(this))" type="button" class="btn btn-danger btn-sm"> ';
        trow += '       <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> ';
        trow += '     </button> ';
        trow += '   </th> ';
        trow += ' </tr> ';
        $('#tbody_cuotas').append(trow);
        $(".gs").on('change', function () {
           suma_cuotas(); 
        });
        formato();
}
function borrar_cuota($this) {
    //console.log((($this.parent().parent());
    $this.parent().parent().remove();
    //cant_cuotas-= 1;
    reenumera_cuotas();
    suma_cuotas(); 
}
function reenumera_cuotas() {
    var filas = $('#tbody_cuotas tr');
    cant_cuotas= 0;
    $.each(filas, function (index, fila) {
        cant_cuotas+= 1;
        $(this).find('td').eq(0).html(cant_cuotas);
        $(this).find('td').eq(1).children('input').attr('id', 'venc' + cant_cuotas);
        $(this).find('td').eq(1).children('input').attr('name', 'venc' + cant_cuotas);
        $(this).find('td').eq(2).children('input').attr('id', 'monto' + cant_cuotas);
        $(this).find('td').eq(2).children('input').attr('name', 'monto' + cant_cuotas);
    });
}
function suma_cuotas() {
    var filas = $('#tbody_cuotas tr');
    $('#total_cuotas').val(0);
    $.each(filas, function (index, fila) {
        var monto = $(this).find('td').eq(2).children('.gs').val();
        $('#total_cuotas').val(Number($('#total_cuotas').val()) + Number(monto.replace(',', '').replace('.', '').replace('.', '')));
    });
    formato();
}


/*** GUARDAR PROPUESTA ***/
function guardarPropuestaAjax() {
    var filas = $('#tbody_cuotas tr');
    //console.log((($('#tbody_cuotas tr'));
    var monto;
    var nro_cuota;
    var vencimiento;
    var propuesta = {};
    var cuota = {};
    var cuotas = {};    
    $.each(filas, function (index, fila) {
        cuota = {};
        nro_cuota = $(this).find('td').eq(0).text();
        vencimiento = $(this).find('td').eq(1).children('input').val();      
        monto = $(this).find('td').eq(2).children('.gs').val();
        cuota['nro_cuota'] = nro_cuota;
        cuota['vencimiento'] = vencimiento;
        cuota['monto'] = Number(monto.replace(',', '').replace('.', '').replace('.', ''));
        cuotas[nro_cuota] = cuota;
    });
    //myJSON = JSON.stringify(cuotas);
    //console.log(((cuotas);
    //Bienes
    filas = $('#tbody_bienes_propuestas tr');
    var id_bien;
    var tipo_bien;
    var marca_bien;
    var modelo_bien;
    var anio_bien;
    var matricula_bien;
    var suma_asegurada;
    var bien = {};
    var bienes = {};
    var nro = 0;
    $.each(filas, function (index, fila) {
        id_bien = $(this).find('td').eq(0).text();
        tipo_bien = $(this).find('td').eq(1).text();
        marca_bien  = $(this).find('td').eq(2).text();
        modelo_bien  = $(this).find('td').eq(3).text();
        matricula_bien = $(this).find('td').eq(4).text();
        anio_bien  = $(this).find('td').eq(5).text();
        suma_asegurada = $(this).find('td').eq(6).children('input').val();
        bien['id_bien'] = id_bien;
        bien['tipo_bien'] = tipo_bien;
        bien['marca_bien'] = marca_bien;
        bien['modelo_bien'] = modelo_bien;
        bien['anio_bien'] = anio_bien;
        bien['matricula_bien'] = matricula_bien;
        bien['suma_asegurada'] = Number(suma_asegurada.replace(',', '').replace('.', '').replace('.', ''));
        nro += nro;
        bienes[nro] = bien;
        //console.log(((JSON.stringify(bien));
    });
    
    //console.log(((filas);
    
    
    propuesta['id_propuesta'] = $('#id_propuesta').val();
    propuesta['id_cliente_propuesta'] = $('#id_cliente_propuesta').val();
    propuesta['nombre_persona_cliente'] = $('#nombre_persona_cliente').val();
    propuesta['id_aseguradora_propuesta'] = $('#id_aseguradora_propuesta').val();
    propuesta['nombre_persona_aseguradora'] = $('#nombre_persona_aseguradora').val();
    propuesta['id_vendedor_propuesta'] = $('#id_vendedor_propuesta').val();
    propuesta['nombre_persona_vendedor'] = $('#nombre_persona_vendedor').val();
    propuesta['fecha_propuesta'] = $('#fecha_propuesta').val();
    propuesta['fecha_vencimiento_propuesta'] = $('#fecha_vencimiento_propuesta').val();
    propuesta['vigendia_desde_propuesta'] = $('#vigendia_desde_propuesta').val();
    propuesta['vigencia_hasta_propuesta'] = $('#vigencia_hasta_propuesta').val();
    propuesta['seccion_propuesta'] = $('#seccion_propuesta').val();
    monto = $('#premio_propuesta').val();
    propuesta['premio_propuesta'] = Number(monto.replace(',', '').replace('.', '').replace('.', ''));
    propuesta['suma_asegurada_propuesta'] = Number($('#suma_asegurada_propuesta').val().replace(',', '').replace('.', '').replace('.', ''));
    
    propuesta['cant_cuotas_propuesta'] = $('#cant_cuotas_propuesta').val();
    propuesta['venc_inicial'] = $('#venc_inicial').val();
    propuesta['intervalo_cuotas'] = $('#intervalo_cuotas').val();
    propuesta['bienes'] = bienes;
    propuesta['cuotas'] = cuotas;
    //propuestas['propuesta']=propuesta;
    //console.log(((JSON.stringify(propuesta));
    
    
    //var pDatosFormulario = $("#formularioPropuesta").serialize();
        //console.log(((pDatosFormulario);
    if (validarPropuesta()) {
        var pDatosFormulario = 'propuesta='+JSON.stringify(propuesta);
        //console.log((($("#formularioPropuesta").serialize());
        //console.log(((pDatosFormulario);
        var pUrl = 'propuesta/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarPropuestaAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarPropuestaAjaxSuccess(json) {
    //console.log(((json);
    if (json.correcto) {
        mensaje("Propuesta guardado", "Aceptar", "#id_propuesta");
        limpiarFormulario();
    } else if (!json.agregado) {
        mensaje(json.error, "Aceptar", "#id_propuesta");
    }
}
/*** BUSCAR ID PROPUESTA ***/
function buscarIdPropuestaAjax() {
    var pDatosFormulario = "id_propuesta=" + $("#id_propuesta").val();
    //console.log(((pDatosFormulario);
    var pUrl = 'propuesta/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdPropuestaAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdPropuestaAjaxSuccess(json) {
    //console.log(((json);
    $('#id_propuesta').val(json.id_propuesta);
    $('#id_cliente_propuesta').val(json.id_cliente);
    $('#nombre_persona_cliente').val(json.nombre_persona_cliente);
    $('#id_aseguradora_propuesta').val(json.id_aseguradora_propuesta);
    $('#nombre_persona_aseguradora').val(json.nombre_persona_aseguradora);
    $('#id_vendedor_propuesta').val(json.id_vendedor);
    $('#nombre_persona_vendedor').val(json.nombre_persona_vendedor);
    $('#fecha_propuesta').val(json.fecha_propuesta);
    $('#fecha_vencimiento_propuesta').val(json.fecha_vencimiento_propuesta);
    $('#vigendia_desde_propuesta').val(json.vigendia_desde_propuesta);
    $('#vigencia_hasta_propuesta').val(json.vigencia_hasta_propuesta);
    $('#seccion_propuesta').val(json.seccion_propuesta);
    $('#premio_propuesta').val(json.premio_propuesta);
    // Cargar bienes
    var id_bien;
    var tipo_bien;
    var cliente_bien;
    var marca_bien;
    var modelo_bien;
    var anio_bien;
    var matricula_bien;
    var datos;
    var i = 0;
    jsonPropuestaBien = json.propuesta_bien;
    $.each(jsonPropuestaBien, function (key, value) {
        //console.log(((jsonPropuestaBien[i]);
        datos = '';
        id_bien = jsonPropuestaBien[i].id_bien;
        tipo_bien = jsonPropuestaBien[i].tip_bien;
        marca_bien = jsonPropuestaBien[i].marca_bien;
        modelo_bien = jsonPropuestaBien[i].modelo_bien;
        anio_bien = jsonPropuestaBien[i].anio_bien;
        matricula_bien = jsonPropuestaBien[i].matricula_bien;
        datos += `<tr>`;
        datos += `                    <td style="width: 50px;">` + id_bien + `</td> `;
        datos += `                    <td>` + tipo_bien + `</td> `;
        datos += `                    <td>` + marca_bien + `</td> `;
        datos += `                    <td>` + modelo_bien + `</td> `;
        datos += `                    <td>` + matricula_bien + `</td> `;
        datos += `                    <td>` + anio_bien + `</td> `;
        datos += `                    <td> <input `;
        datos += `                               onchange="suma_bienes()"`;
        datos += `                               min="0" `;
        datos += `                               max="999999999999999999999999999999999" `;
        datos += `                               value="0" `;
        datos += `                               id="suma_asegurada" style="text-align:right" `;
        datos += `                               onclick="$(this).select()" `;
        datos += `                               name="suma_asegurada"`;
        datos += `                               class="form-control input-sm gs"`;
        datos += `                               placeholder="Suma Asegurada"> </th>`;
        datos += `                    <td class="centrado" style="width: 50px;">`;
        datos += `                        <button onclick="eliminar_detalle($(this))" type="button" class="btn btn-danger btn-sm">`;
        datos += `                            <span class="glyphicon glyphicon-minus" aria-hidden="true"></span>`;
        datos += `                        </button> `;
        datos += `                    </td> `;
        datos += `                </tr> `;
        $('#tbody_bienes_propuestas').append(datos);
        formato();
        i+=1;
    });
    
    
    //Cuotas
    var i = 1;
    //console.log((json.propuesta_cuota);
    jsonPropuestaCuota = json.propuesta_cuota;
    
    $.each(jsonPropuestaCuota, function (key, value) {
        trow = ' ';
        //console.log((i+" "+jsonPropuestaCuota[i].nro_cuota_propuesta_cuota);
        trow += ' <tr> ';
        trow += '      <td id="nro_cuota">' + jsonPropuestaCuota[i].nro_cuota_propuesta_cuota + '</td> ';
        trow += '      <td> ';
        trow += '          <input type="date" id="venc' + jsonPropuestaCuota[i].nro_cuota_propuesta_cuota  + '" name="venc' + jsonPropuestaCuota[i].nro_cuota_propuesta_cuota  + '" class="form-control input-sm"> ';
        trow += '      </td>';
        trow += '      <td colspan="2"><input type="text" ';
        trow += '                             id="monto' + jsonPropuestaCuota[i].nro_cuota_propuesta_cuota  + '" ';
        trow += '                             name="monto' + jsonPropuestaCuota[i].nro_cuota_propuesta_cuota  + '" ';
        trow += '                             class="form-control input-sm gs" ';
        trow += '                             placeholder="monto' + jsonPropuestaCuota[i].nro_cuota_propuesta_cuota  + '"></td> ';
        trow += '    <th> ';
        trow += '     <button onclick="borrar_cuota($(this))" type="button" class="btn btn-danger btn-sm"> ';
        trow += '       <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> ';
        trow += '     </button> ';
        trow += '   </th> ';
        trow += ' </tr> ';
        $('#tbody_cuotas').append(trow);
        $('#venc' + jsonPropuestaCuota[i].nro_cuota_propuesta_cuota + '').val(jsonPropuestaCuota[i].vencimiento_propuesta_cuota);
        $('#monto' + jsonPropuestaCuota[i].nro_cuota_propuesta_cuota + '').val(jsonPropuestaCuota[i].monto_cuota_propuesta_cuota);
        suma_cuotas();
        i+=1;
    });
    
    $('#id_propuesta').focus();
    $('#id_propuesta').select();
}

function limpiarFormulario() {
    $('#id_propuesta').val(0);
    $('#id_cliente').val(0);
    $('#nombre_persona_cliente').val("");
    $('#id_tipo_propuesta').val("");
    $('#nombre_tipo_propuesta').val("");
    $('#marca_propuesta').val("");
    $('#modelo_propuesta').val("");
    $('#matricula_propuesta').val("");
    $('#anio_propuesta').val(0);
    $('#color_propuesta').val("");
    $('#num_motor_propuesta').val("");
    $('#num_carroceria_propuesta').val("");
    $('#tip_propuesta').val("");
    $('#descripcion_propuesta').val("");
    $('#id_propuesta').focus();
    $('#id_propuesta').select();
}

/*** ELIMINAR PROPUESTA ***/
function confirmaEliminar() {
    estaSeguro("Se eliminara el registro. Esta seguro/a ?", "eliminarPropuestaAjax()", "#id_propuesta");
}
function eliminarPropuestaAjax() {
    if ($("#id_propuesta").val().trim() === "0" || $("#id_propuesta").val().trim() === "") {
        mensaje("Tipo de propuesta invalido", "Aceptar", "#id_propuesta");
    } else {
        var pDatosFormulario = $("#formularioPropuesta").serialize();
        //console.log(((pDatosFormulario);
        var pUrl = 'propuesta/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarPropuestaAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarPropuestaAjaxSuccess(json) {
    //console.log((json);
    if (json.correcto) {
        mensaje("Tipo de propuesta Eliminado", "Aceptar", "#id_propuesta");
        limpiarFormulario();
    } else if (!json.agregado) {
        mensaje(json.error, "Aceptar", "#id_propuesta");
    }
}

/*** BUSCAR CLIENTE POR NOMBRE ***/
function BuscarClienteNombreAjax() {
    var pDatosCliente = $("#form-buscar").serialize();
    //console.log(((pDatosCliente);
    var pUrl = 'cliente/buscar/nombre';
    var pBeforeSend = "";
    var pSucces = "BuscarClienteNombreAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosCliente, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function BuscarClienteNombreAjaxSuccess(json) {
    var datos = "";
    //console.log(((json);
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
    $("#id_cliente_propuesta").val(id);
    $("#nombre_persona_cliente").val(nombre);
    $("#id_cliente").focus();
    $("#id_cliente").select();
}

/*** BUSCAR TIPO_PROPUESTA POR NOMBRE ***/
function BuscarTipo_propuestaNombreAjax() {
    var pDatosFormulario = $("#form-buscar").serialize();
    //console.log(((pDatosFormulario);
    var pUrl = 'tipo_propuesta/buscar/nombre';
    var pBeforeSend = "";
    var pSucces = "BuscarTipo_propuestaNombreAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function BuscarTipo_propuestaNombreAjaxSuccess(json) {
    var datos = "";
    //console.log((json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_tipo_propuesta($(this))'>";
        datos += "<td>" + value.id_tipo_propuesta + "</td>";
        datos += "<td>" + value.nombre_tipo_propuesta + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_tipos_propuestas').html(datos);
}
function seleccionar_tipo_propuesta($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(1).text();
    salir_busqueda();
    $("#id_tipo_propuesta").val(id);
    $("#nombre_tipo_propuesta").val(nombre);
    $("#nombre_tipo_propuesta").focus();
    $("#nombre_tipo_propuesta").select();
}
/*** BUSCAR ASEGURADORA POR NOMBRE ***/
function BuscarAseguradoraNombreAjax(){
        var pDatosAseguradora = $("#form-buscar").serialize();
        //console.log(((pDatosAseguradora);
        var pUrl= 'aseguradora/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarAseguradoraNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosAseguradora, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function BuscarAseguradoraNombreAjaxSuccess(json){
    var datos = "";
   //console.log(((json);
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
    $("#id_aseguradora_propuesta").val(id);
    $("#nombre_persona_aseguradora").val(nombre);
}
/*** BUSCAR VENDEDOR POR NOMBRE ***/
function BuscarVendedorNombreAjax(){
        var pDatosVendedor = $("#form-buscar").serialize();
        //console.log(((pDatosVendedor);
        var pUrl= 'vendedor/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarVendedorNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosVendedor, pUrl, pBeforeSend, pSucces, pError, pComplete);
}

function BuscarVendedorNombreAjaxSuccess(json){
    var datos = "";
   //console.log(((json);
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
    $("#id_vendedor_propuesta").val(id);
    $("#nombre_persona_vendedor").val(nombre);
}

/*** BUSCAR PROPUESTA POR NOMBRE ***/
function BuscarPropuestaNombreAjax(){
        var pDatosPropuesta = $("#form-buscar").serialize();
        //console.log((("Dato: "+pDatosPropuesta);
        var pUrl= 'propuesta/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarPropuestaNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosPropuesta, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function BuscarPropuestaNombreAjaxSuccess(json){
    var datos = "";
    var i = 0;
    
    $.each(json, function (key, value) {
        //console.log(((json[i]);
        datos += "<tr onclick='seleccionar_Propuesta($(this))'>";
        datos += "<td>" + json[i].id_propuesta + "</td>";
        datos += "<td>" + json[i].nombre_persona_cliente+" ( "+value.id_cliente + " ) </td>";
                datos += "<td>" + json[i].nombre_persona_vendedor+" ( "+value.id_vendedor + " ) </td>";
        datos += "<td>" + json[i].nombre_persona_aseguradora+" ( "+value.id_aseguradora_propuesta + " ) </td>";
        datos += "<td>" + json[i].seccion_propuesta+ "</td>";
        datos += '<td class="gs">' + json[i].suma_asegurada_propuesta + '</td>';
        datos += '<td class="gs">' + json[i].premio_propuesta + '</td>';
        datos += "</tr>";
        i += 1;
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_propuestas').html(datos);
    formato();
}
function seleccionar_Propuesta($this) {
    var id = $this.find('td').eq(0).text();
    //console.log((($this.find('td').eq(0).text());
    salir_busqueda();
    $("#id_propuesta").val(id);
    buscarIdPropuestaAjax();    
    $("#id_propuesta").focus();
    $("#id_propuesta").select();
    deshabilitar_agregar();
}