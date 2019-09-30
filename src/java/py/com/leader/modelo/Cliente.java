/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.modelo;

import org.json.simple.JSONObject;
import py.com.leader.DAO.CobradorDAO;
import py.com.leader.DAO.PersonaDAO;
import py.com.leader.DAO.VendedorDAO;

/**
 *
 * @author Esteban
 */
public class Cliente {
    PersonaDAO personaDAO = new PersonaDAO();
    VendedorDAO vendedorDAO = new VendedorDAO();
    CobradorDAO cobradorDAO = new CobradorDAO();
    private int id_cliente;
    private String nombre_cliente;
    private Persona persona;
    private Vendedor vendedor;
    private Cobrador cobrador;
    private Usuario usuarioauditoria;

    public Cliente() {
        persona = new Persona();
        usuarioauditoria = new Usuario();
        vendedor = new Vendedor();
        cobrador = new Cobrador();
    }

    public Cliente(int id_cliente, String nombre_cliente, Persona persona, Vendedor vendedor, Cobrador cobrador, Usuario usuarioauditoria) {
        this.persona = personaDAO.buscarId(persona.getId_persona());
        this.vendedor = vendedorDAO.buscarId(vendedor.getId_vendedor());
        this.cobrador = new Cobrador();
        this.usuarioauditoria = new Usuario();
        this.id_cliente = id_cliente;
        this.nombre_cliente = nombre_cliente;
        this.usuarioauditoria = usuarioauditoria;
        this.vendedor = vendedor;
        this.cobrador = cobrador;
        
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Cobrador getCobrador() {
        return cobrador;
    }

    public void setCobrador(Cobrador cobrador) {
        this.cobrador = cobrador;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        if ( persona == null){
           persona = new Persona(); 
        }
        this.persona = personaDAO.buscarId(persona.getId_persona());
        
    }

    public Usuario getUsuarioauditoria() {
        return usuarioauditoria;
    }

    public void setUsuarioauditoria(Usuario usuarioauditoria) {
        this.usuarioauditoria = usuarioauditoria;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id_cliente", this.id_cliente);
        obj.put("id_persona_cliente", this.persona.getId_persona());
        obj.put("nombre_persona_cliente", this.persona.getNombre_persona());
        obj.put("nombre_fantasia_cliente", this.persona.getNombre_fantasia_persona());
        obj.put("ci_persona_cliente", this.persona.getCi_persona());
        obj.put("ci_persona_cliente", this.persona.getCi_persona());
        obj.put("dni_persona_cliente", this.persona.getDni_persona());
        obj.put("ruc_persona_cliente", this.persona.getRuc_persona());
        obj.put("telefono1_persona_cliente", this.persona.getTelefono1_persona());
        obj.put("telefono2_persona_cliente", this.persona.getTelefono2_persona());
        obj.put("web_persona_cliente", this.persona.getWeb_persona());
        
        obj.put("id_cobrador", cobrador.getId_cobrador());
        obj.put("nombre_cobrador", cobrador.getPersona().getNombre_persona());
        obj.put("id_persona_cobrador", cobrador.getPersona().getId_persona());
        obj.put("nombre_persona_cobrador", cobrador.getPersona().getNombre_persona());
        obj.put("nombre_fantasia_cobrador", cobrador.getPersona().getNombre_fantasia_persona());
        obj.put("ci_persona_cobrador", cobrador.getPersona().getCi_persona());
        obj.put("ci_persona_cobrador", cobrador.getPersona().getCi_persona());
        obj.put("dni_persona_cobrador", cobrador.getPersona().getDni_persona());
        obj.put("ruc_persona_cobrador", cobrador.getPersona().getRuc_persona());
        obj.put("telefono1_persona_cobrador", cobrador.getPersona().getTelefono1_persona());
        obj.put("telefono2_persona_cobrador", cobrador.getPersona().getTelefono2_persona());
        obj.put("web_persona_cobrador", cobrador.getPersona().getWeb_persona());
        
        obj.put("id_vendedor", vendedor.getId_vendedor());
        obj.put("nombre_vendedor", vendedor.getPersona().getNombre_persona());
        obj.put("matricula_vendedor", vendedor.getMatricula_vendedor());
        obj.put("id_persona_vendedor", vendedor.getPersona().getId_persona());
        obj.put("nombre_persona_vendedor", vendedor.getPersona().getNombre_persona());
        obj.put("nombre_fantasia_vendedor", vendedor.getPersona().getNombre_fantasia_persona());
        obj.put("ci_persona_vendedor", vendedor.getPersona().getCi_persona());
        obj.put("ci_persona_vendedor", vendedor.getPersona().getCi_persona());
        obj.put("dni_persona_vendedor", vendedor.getPersona().getDni_persona());
        obj.put("ruc_persona_vendedor", vendedor.getPersona().getRuc_persona());
        obj.put("telefono1_persona_vendedor", vendedor.getPersona().getTelefono1_persona());
        obj.put("telefono2_persona_vendedor", vendedor.getPersona().getTelefono2_persona());
        obj.put("web_persona_vendedor", vendedor.getPersona().getWeb_persona());
        
        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        //System.out.println(obj);
        return obj;
    }
}
