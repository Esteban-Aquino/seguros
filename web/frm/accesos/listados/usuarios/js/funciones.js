function inicializar_formulario() {
    //$("#s_nombre_formulario").text('Listado de Usuarios');
    $('#desde_id').focus();
    $('#desde_id').select();
    siguienteCampo('#desde_id','#hasta_id',false);
    siguienteCampo('#hasta_id','#boton-imprimir',true);
}
function imprimir() {
    $("#form-formulario").submit();
}
