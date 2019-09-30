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
public class Cobrador {
  private int id_cobrador;
  private String nombre_cobrador;
  private Persona persona;
  private Usuario usuarioauditoria;

    public Cobrador() {
        persona = new Persona();
        usuarioauditoria = new Usuario();
    }

    public Cobrador(int id_cobrador, String nombre_cobrador, Persona persona, Usuario usuarioauditoria) {
        this.persona = new Persona();
        this.usuarioauditoria = new Usuario();
        this.id_cobrador = id_cobrador;
        this.nombre_cobrador = nombre_cobrador;
        this.persona = persona;
        this.usuarioauditoria = usuarioauditoria;
    }

    public int getId_cobrador() {
        return id_cobrador;
    }

    public void setId_cobrador(int id_cobrador) {
        this.id_cobrador = id_cobrador;
    }

    public String getNombre_cobrador() {
        return nombre_cobrador;
    }

    public void setNombre_cobrador(String nombre_cobrador) {
        this.nombre_cobrador = nombre_cobrador;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Usuario getUsuarioauditoria() {
        return usuarioauditoria;
    }

    public void setUsuarioauditoria(Usuario usuarioauditoria) {
        this.usuarioauditoria = usuarioauditoria;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id_cobrador", this.id_cobrador);
        obj.put("id_persona", persona.getId_persona());
        obj.put("nombre_persona", persona.getNombre_persona());
        obj.put("nombre_fantasia", persona.getNombre_fantasia_persona());
        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        return obj;
    }
  
}
