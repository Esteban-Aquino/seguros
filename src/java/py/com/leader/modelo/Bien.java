/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.modelo;

import org.json.simple.JSONObject;

/**
 *
 * @author Esteban
 */
public class Bien {

    private int id_bien;
    private Tipo_bien tipo_bien;
    private Cliente cliente;
    private String marca_bien;
    private String modelo_bien;
    private String tip_bien;
    private String matricula_bien;
    private int anio_bien;
    private String color_bien;
    private String num_motor_bien;
    private String num_carroceria_bien;
    private String descripcion_bien;
    private Usuario usuarioauditoria;

    public Bien() {
        tipo_bien = new Tipo_bien();
        cliente = new Cliente();
        usuarioauditoria = new Usuario();
    }

    public Bien(int id_bien, Tipo_bien tipo_bien, Cliente cliente, String marca_bien, String modelo_bien, String tip_bien, String matricula_bien, int anio_bien, String color_bien, String num_motor_bien, String num_carroceria_bien, String descripcion_bien, Usuario usuarioauditoria) {
        if ( tipo_bien == null){
           tipo_bien = new Tipo_bien(); 
        }
        if ( cliente == null){
           cliente = new Cliente(); 
        }
        usuarioauditoria = new Usuario();
        this.id_bien = id_bien;
        this.tipo_bien = tipo_bien;
        this.cliente = cliente;
        this.marca_bien = marca_bien;
        this.modelo_bien = modelo_bien;
        this.tip_bien = tip_bien;
        this.matricula_bien = matricula_bien;
        this.anio_bien = anio_bien;
        this.color_bien = color_bien;
        this.num_motor_bien = num_motor_bien;
        this.num_carroceria_bien = num_carroceria_bien;
        this.descripcion_bien = descripcion_bien;
        this.usuarioauditoria = usuarioauditoria;
    }

    public int getId_bien() {
        return id_bien;
    }

    public void setId_bien(int id_bien) {
        this.id_bien = id_bien;
    }

    public Tipo_bien getTipo_bien() {
        return tipo_bien;
    }

    public void setTipo_bien(Tipo_bien tipo_bien) {
        this.tipo_bien = tipo_bien;
        if ( this.tipo_bien == null){
           this.tipo_bien = new Tipo_bien(); 
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if ( this.cliente == null){
           this.cliente = new Cliente(); 
        }
    }

    public String getMarca_bien() {
        return marca_bien;
    }

    public void setMarca_bien(String marca_bien) {
        this.marca_bien = marca_bien;
    }

    public String getModelo_bien() {
        return modelo_bien;
    }

    public void setModelo_bien(String modelo_bien) {
        this.modelo_bien = modelo_bien;
    }

    public String getTip_bien() {
        return tip_bien;
    }

    public void setTip_bien(String tip_bien) {
        this.tip_bien = tip_bien;
    }

    public String getMatricula_bien() {
        return matricula_bien;
    }

    public void setMatricula_bien(String matricula_bien) {
        this.matricula_bien = matricula_bien;
    }

    public int getAnio_bien() {
        return anio_bien;
    }

    public void setAnio_bien(int anio_bien) {
        this.anio_bien = anio_bien;
    }

    public String getColor_bien() {
        return color_bien;
    }

    public void setColor_bien(String color_bien) {
        this.color_bien = color_bien;
    }

    public String getNum_motor_bien() {
        return num_motor_bien;
    }

    public void setNum_motor_bien(String num_motor_bien) {
        this.num_motor_bien = num_motor_bien;
    }

    public String getNum_carroceria_bien() {
        return num_carroceria_bien;
    }

    public void setNum_carroceria_bien(String num_carroceria_bien) {
        this.num_carroceria_bien = num_carroceria_bien;
    }

    public String getDescripcion_bien() {
        return descripcion_bien;
    }

    public void setDescripcion_bien(String descripcion_bien) {
        this.descripcion_bien = descripcion_bien;
    }

    public Usuario getUsuarioauditoria() {
        return usuarioauditoria;
    }

    public void setUsuarioauditoria(Usuario usuarioauditoria) {
        this.usuarioauditoria = usuarioauditoria;
        if ( this.usuarioauditoria == null){
           this.usuarioauditoria = new Usuario(); 
        }
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id_bien", this.id_bien);
        obj.put("marca_bien", this.getMarca_bien());
        obj.put("modelo_bien", this.getModelo_bien());
        obj.put("tip_bien", this.tip_bien);
        obj.put("matricula_bien", this.matricula_bien);
        obj.put("anio_bien", this.getAnio_bien());
        obj.put("color_bien", this.getColor_bien());
        obj.put("num_motor_bien", this.getNum_motor_bien());
        obj.put("num_carroceria_bien", this.getNum_carroceria_bien());
        obj.put("descripcion_bien", this.getDescripcion_bien());

        obj.put("id_tipo_bien", this.tipo_bien.getId_tipo_bien());
        obj.put("nombre_tipo_bien", this.tipo_bien.getNombre_tipo_bien());

        obj.put("id_cliente", this.cliente.getId_cliente());
        obj.put("id_persona_cliente", this.cliente.getPersona().getId_persona());
        obj.put("nombre_persona_cliente", this.cliente.getPersona().getNombre_persona());
        obj.put("nombre_fantasia_cliente", this.cliente.getPersona().getNombre_fantasia_persona());
        obj.put("ci_persona_cliente", this.cliente.getPersona().getCi_persona());
        obj.put("ci_persona_cliente", this.cliente.getPersona().getCi_persona());
        obj.put("dni_persona_cliente", this.cliente.getPersona().getDni_persona());
        obj.put("ruc_persona_cliente", this.cliente.getPersona().getRuc_persona());
        obj.put("telefono1_persona_cliente", this.cliente.getPersona().getTelefono1_persona());
        obj.put("telefono2_persona_cliente", this.cliente.getPersona().getTelefono2_persona());
        obj.put("web_persona_cliente", this.cliente.getPersona().getWeb_persona());

        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        return obj;
    }

}
