/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var cant_cuotas = 0;
function inicializarFrmPolizas() {
    $("#s_nombre_formulario").text('Polizas');
    $("#id_poliza").focus();
    $("#id_poliza").select();
    $("#id_poliza").on('change', function () {
        if ($("#id_poliza").val() === "0") {
            limpiarFormulario();
        } else {
            buscarIdPolizaAjax();
        }
    });
    
    $("#fecha_poliza").on('focus', function () {
        if ($("#fecha_poliza").val() === "") {
            $('#fecha_poliza').val(moment(new Date().getD).format("YYYY-MM-DD"));
        }
    });
    $("#botonGuardar").on('click', function () {
        guardarPolizaAjax();
    });
    $("#botonEliminar").on('click', function () {
        confirmaEliminar();
    });
    formato();
    siguienteCampo('#id_poliza', '#id_cliente', false);
    siguienteCampo('#id_cliente', '#id_tipo_poliza', false);
    siguienteCampo('#id_tipo_poliza', '#marca_poliza', false);
    siguienteCampo('#marca_poliza', '#matricula_poliza', false);
    siguienteCampo('#matricula_poliza', '#anio_poliza', false);
    siguienteCampo('#anio_poliza', '#color_poliza', false);
    siguienteCampo('#color_poliza', '#num_motor_poliza', false);
    siguienteCampo('#num_motor_poliza', '#num_carroceria_poliza', false);
    siguienteCampo('#num_carroceria_poliza', '#tip_poliza', false);
    siguienteCampo('#tip_poliza', '#botonGuardar', false);
    //formatea_datepicker();
}
/****DATE PICKER****/
function formatea_datepicker() {
    $(".date").datepicker();
}
/***Agregar bien***/
function agregar_bien() {
    $('body').append('<div id="modalBienes"></div>');
    $.get("frm/polizas/buscar_bien_modal.html", function (htmlexterno) {
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
    $('#tbody_bienes_polizas').append(datos);
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
    var filas = $('#tbody_bienes_polizas tr');
    $('#suma_asegurada_poliza').val(0);
    $.each(filas, function (index, fila) {
        var monto = $(this).find('td').eq(6).children('#suma_asegurada').val();
        $('#suma_asegurada_poliza').val(Number($('#suma_asegurada_poliza').val()) + Number(monto.replace(',', '').replace('.', '').replace('.', '')));
    });
    formato();
    $('#vigendia_desde_poliza').focus();
}

function validarPoliza() {
    var ok = true;

    var id_cliente = $("#id_cliente_poliza").val();
    if (id_cliente.trim() === "" || id_cliente.trim() === "0") {
        mensaje("Debe ingresar cliente", "Aceptar", "#id_cliente_poliza");
        ok = false;
    }
    return ok;
}

/*** GUARDAR POLIZA ***/
function generar_cuotas() {
    monto_total = $('#premio_poliza').val().replace(',', '').replace('.', '').replace('.', '');
    cuotas = $('#cant_cuotas_poliza').val().replace(',', '').replace('.', '').replace('.', '');
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


/*** GUARDAR POLIZA ***/
function guardarPolizaAjax() {
    var filas = $('#tbody_cuotas tr');
    //console.log((($('#tbody_cuotas tr'));
    var monto;
    var nro_cuota;
    var vencimiento;
    var poliza = {};
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
    filas = $('#tbody_bienes_polizas tr');
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
    
    
    poliza['id_poliza'] = $('#id_poliza').val();
    poliza['id_cliente_poliza'] = $('#id_cliente_poliza').val();
    poliza['nombre_persona_cliente'] = $('#nombre_persona_cliente').val();
    poliza['id_aseguradora_poliza'] = $('#id_aseguradora_poliza').val();
    poliza['nombre_persona_aseguradora'] = $('#nombre_persona_aseguradora').val();
    poliza['id_vendedor_poliza'] = $('#id_vendedor_poliza').val();
    poliza['nombre_persona_vendedor'] = $('#nombre_persona_vendedor').val();
    poliza['fecha_poliza'] = $('#fecha_poliza').val();
    poliza['fecha_vencimiento_poliza'] = $('#fecha_vencimiento_poliza').val();
    poliza['vigendia_desde_poliza'] = $('#vigendia_desde_poliza').val();
    poliza['vigencia_hasta_poliza'] = $('#vigencia_hasta_poliza').val();
    poliza['seccion_poliza'] = $('#seccion_poliza').val();
    monto = $('#premio_poliza').val();
    poliza['premio_poliza'] = Number(monto.replace(',', '').replace('.', '').replace('.', ''));
    poliza['suma_asegurada_poliza'] = Number($('#suma_asegurada_poliza').val().replace(',', '').replace('.', '').replace('.', ''));
    
    poliza['cant_cuotas_poliza'] = $('#cant_cuotas_poliza').val();
    poliza['venc_inicial'] = $('#venc_inicial').val();
    poliza['intervalo_cuotas'] = $('#intervalo_cuotas').val();
    poliza['bienes'] = bienes;
    poliza['cuotas'] = cuotas;
    //polizas['poliza']=poliza;
    //console.log(((JSON.stringify(poliza));
    
    
    //var pDatosFormulario = $("#formularioPoliza").serialize();
        //console.log(((pDatosFormulario);
    if (validarPoliza()) {
        var pDatosFormulario = 'poliza='+JSON.stringify(poliza);
        //console.log((($("#formularioPoliza").serialize());
        //console.log(((pDatosFormulario);
        var pUrl = 'poliza/guardar';
        var pBeforeSend = "";
        var pSucces = "guardarPolizaAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function guardarPolizaAjaxSuccess(json) {
    //console.log(((json);
    if (json.correcto) {
        mensaje("Poliza guardado", "Aceptar", "#id_poliza");
        limpiarFormulario();
    } else if (!json.agregado) {
        mensaje(json.error, "Aceptar", "#id_poliza");
    }
}
/*** BUSCAR ID POLIZA ***/
function buscarIdPolizaAjax() {
    var pDatosFormulario = "id_poliza=" + $("#id_poliza").val();
    //console.log(((pDatosFormulario);
    var pUrl = 'poliza/buscar/id';
    var pBeforeSend = "";
    var pSucces = "buscarIdPolizaAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function buscarIdPolizaAjaxSuccess(json) {
    //console.log(((json);
    $('#id_poliza').val(json.id_poliza);
    $('#id_cliente_poliza').val(json.id_cliente);
    $('#nombre_persona_cliente').val(json.nombre_persona_cliente);
    $('#id_aseguradora_poliza').val(json.id_aseguradora_poliza);
    $('#nombre_persona_aseguradora').val(json.nombre_persona_aseguradora);
    $('#id_vendedor_poliza').val(json.id_vendedor);
    $('#nombre_persona_vendedor').val(json.nombre_persona_vendedor);
    $('#fecha_poliza').val(json.fecha_poliza);
    $('#fecha_vencimiento_poliza').val(json.fecha_vencimiento_poliza);
    $('#vigendia_desde_poliza').val(json.vigendia_desde_poliza);
    $('#vigencia_hasta_poliza').val(json.vigencia_hasta_poliza);
    $('#seccion_poliza').val(json.seccion_poliza);
    $('#premio_poliza').val(json.premio_poliza);
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
    jsonPolizaBien = json.poliza_bien;
    $.each(jsonPolizaBien, function (key, value) {
        //console.log(((jsonPolizaBien[i]);
        datos = '';
        id_bien = jsonPolizaBien[i].id_bien;
        tipo_bien = jsonPolizaBien[i].tip_bien;
        marca_bien = jsonPolizaBien[i].marca_bien;
        modelo_bien = jsonPolizaBien[i].modelo_bien;
        anio_bien = jsonPolizaBien[i].anio_bien;
        matricula_bien = jsonPolizaBien[i].matricula_bien;
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
        $('#tbody_bienes_polizas').append(datos);
        formato();
        i+=1;
    });
    
    
    //Cuotas
    var i = 1;
    //console.log((json.poliza_cuota);
    jsonPolizaCuota = json.poliza_cuota;
    
    $.each(jsonPolizaCuota, function (key, value) {
        trow = ' ';
        //console.log((i+" "+jsonPolizaCuota[i].nro_cuota_poliza_cuota);
        trow += ' <tr> ';
        trow += '      <td id="nro_cuota">' + jsonPolizaCuota[i].nro_cuota_poliza_cuota + '</td> ';
        trow += '      <td> ';
        trow += '          <input type="date" id="venc' + jsonPolizaCuota[i].nro_cuota_poliza_cuota  + '" name="venc' + jsonPolizaCuota[i].nro_cuota_poliza_cuota  + '" class="form-control input-sm"> ';
        trow += '      </td>';
        trow += '      <td colspan="2"><input type="text" ';
        trow += '                             id="monto' + jsonPolizaCuota[i].nro_cuota_poliza_cuota  + '" ';
        trow += '                             name="monto' + jsonPolizaCuota[i].nro_cuota_poliza_cuota  + '" ';
        trow += '                             class="form-control input-sm gs" ';
        trow += '                             placeholder="monto' + jsonPolizaCuota[i].nro_cuota_poliza_cuota  + '"></td> ';
        trow += '    <th> ';
        trow += '     <button onclick="borrar_cuota($(this))" type="button" class="btn btn-danger btn-sm"> ';
        trow += '       <span class="glyphicon glyphicon-minus" aria-hidden="true"></span> ';
        trow += '     </button> ';
        trow += '   </th> ';
        trow += ' </tr> ';
        $('#tbody_cuotas').append(trow);
        $('#venc' + jsonPolizaCuota[i].nro_cuota_poliza_cuota + '').val(jsonPolizaCuota[i].vencimiento_poliza_cuota);
        $('#monto' + jsonPolizaCuota[i].nro_cuota_poliza_cuota + '').val(jsonPolizaCuota[i].monto_cuota_poliza_cuota);
        suma_cuotas();
        i+=1;
    });
    
    $('#id_poliza').focus();
    $('#id_poliza').select();
}

function limpiarFormulario() {
    $('#id_poliza').val(0);
    $('#id_cliente').val(0);
    $('#nombre_persona_cliente').val("");
    $('#id_tipo_poliza').val("");
    $('#nombre_tipo_poliza').val("");
    $('#marca_poliza').val("");
    $('#modelo_poliza').val("");
    $('#matricula_poliza').val("");
    $('#anio_poliza').val(0);
    $('#color_poliza').val("");
    $('#num_motor_poliza').val("");
    $('#num_carroceria_poliza').val("");
    $('#tip_poliza').val("");
    $('#descripcion_poliza').val("");
    $('#id_poliza').focus();
    $('#id_poliza').select();
}

/*** ELIMINAR POLIZA ***/
function confirmaEliminar() {
    estaSeguro("Se eliminara el registro. Esta seguro/a ?", "eliminarPolizaAjax()", "#id_poliza");
}
function eliminarPolizaAjax() {
    if ($("#id_poliza").val().trim() === "0" || $("#id_poliza").val().trim() === "") {
        mensaje("Tipo de poliza invalido", "Aceptar", "#id_poliza");
    } else {
        var pDatosFormulario = $("#formularioPoliza").serialize();
        //console.log(((pDatosFormulario);
        var pUrl = 'poliza/eliminar';
        var pBeforeSend = "";
        var pSucces = "eliminarPolizaAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
    }
}

function eliminarPolizaAjaxSuccess(json) {
    //console.log((json);
    if (json.correcto) {
        mensaje("Tipo de poliza Eliminado", "Aceptar", "#id_poliza");
        limpiarFormulario();
    } else if (!json.agregado) {
        mensaje(json.error, "Aceptar", "#id_poliza");
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
    $("#id_cliente_poliza").val(id);
    $("#nombre_persona_cliente").val(nombre);
    $("#id_cliente").focus();
    $("#id_cliente").select();
}

/*** BUSCAR TIPO_POLIZA POR NOMBRE ***/
function BuscarTipo_polizaNombreAjax() {
    var pDatosFormulario = $("#form-buscar").serialize();
    //console.log(((pDatosFormulario);
    var pUrl = 'tipo_poliza/buscar/nombre';
    var pBeforeSend = "";
    var pSucces = "BuscarTipo_polizaNombreAjaxSuccess(json)";
    var pError = "ajax_error()";
    var pComplete = "";
    ajax(pDatosFormulario, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function BuscarTipo_polizaNombreAjaxSuccess(json) {
    var datos = "";
    //console.log((json);
    $.each(json, function (key, value) {
        datos += "<tr onclick='seleccionar_tipo_poliza($(this))'>";
        datos += "<td>" + value.id_tipo_poliza + "</td>";
        datos += "<td>" + value.nombre_tipo_poliza + "</td>";
        datos += "</tr>";
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_tipos_polizas').html(datos);
}
function seleccionar_tipo_poliza($this) {
    var id = $this.find('td').eq(0).text();
    var nombre = $this.find('td').eq(1).text();
    salir_busqueda();
    $("#id_tipo_poliza").val(id);
    $("#nombre_tipo_poliza").val(nombre);
    $("#nombre_tipo_poliza").focus();
    $("#nombre_tipo_poliza").select();
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
    $("#id_aseguradora_poliza").val(id);
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
    $("#id_vendedor_poliza").val(id);
    $("#nombre_persona_vendedor").val(nombre);
}

/*** BUSCAR POLIZA POR NOMBRE ***/
function BuscarPolizaNombreAjax(){
        var pDatosPoliza = $("#form-buscar").serialize();
        //console.log((("Dato: "+pDatosPoliza);
        var pUrl= 'poliza/buscar/nombre';
        var pBeforeSend = "";
        var pSucces = "BuscarPolizaNombreAjaxSuccess(json)";
        var pError = "ajax_error()";
        var pComplete = "";
        ajax(pDatosPoliza, pUrl, pBeforeSend, pSucces, pError, pComplete);
}
function BuscarPolizaNombreAjaxSuccess(json){
    var datos = "";
    var i = 0;
    
    $.each(json, function (key, value) {
        //console.log(((json[i]);
        datos += "<tr onclick='seleccionar_Poliza($(this))'>";
        datos += "<td>" + json[i].id_poliza + "</td>";
        datos += "<td>" + json[i].nombre_persona_cliente+" ( "+value.id_cliente + " ) </td>";
                datos += "<td>" + json[i].nombre_persona_vendedor+" ( "+value.id_vendedor + " ) </td>";
        datos += "<td>" + json[i].nombre_persona_aseguradora+" ( "+value.id_aseguradora_poliza + " ) </td>";
        datos += "<td>" + json[i].seccion_poliza+ "</td>";
        datos += '<td class="gs">' + json[i].suma_asegurada_poliza + '</td>';
        datos += '<td class="gs">' + json[i].premio_poliza + '</td>';
        datos += "</tr>";
        i += 1;
    });
    if (datos === '') {
        datos += "<tr><td colspan='4'>No existen mas registros ...</td></tr>";
    }
    $('#tbody_datos_polizas').html(datos);
    formato();
}
function seleccionar_Poliza($this) {
    var id = $this.find('td').eq(0).text();
    //console.log((($this.find('td').eq(0).text());
    salir_busqueda();
    $("#id_poliza").val(id);
    buscarIdPolizaAjax();    
    $("#id_poliza").focus();
    $("#id_poliza").select();
    deshabilitar_agregar();
}