/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.modelo;

import org.json.simple.JSONObject;
import static py.com.leader.util.util.nvl;

/**
 *
 * @author Esteban
 */
public class Usuario {
    private int id_usuario;
    private String usuario_usuario;
    private String clave_usuario ;
    private String nombre_usuario;
    private Persona persona = new Persona();
    public Usuario() {
    }

    public Usuario(int id_usuario, String usuario_usuario, String nombre_usuario, String clave, Persona persona) {
        this.id_usuario = id_usuario;
        this.usuario_usuario = usuario_usuario;
        this.clave_usuario = clave;
        this.nombre_usuario = nombre_usuario;
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario_usuario() {
        return usuario_usuario;
    }

    public void setUsuario_usuario(String usuario_usuario) {
        this.usuario_usuario = usuario_usuario;
    }

    public String getClave_usuario() {
        return clave_usuario;
    }

    public void setClave_usuario(String clave) {
        this.clave_usuario = clave;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
    
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id_usuario", this.id_usuario);
        obj.put("id_persona_usuario", nvl(this.persona.getId_persona(),0));
        obj.put("usuario_usuario", this.usuario_usuario);
        //obj.put("nombre_usuario", this.nombre_usuario);
        obj.put("nombre_persona", nvl(persona.getNombre_persona(),this.nombre_usuario));
        obj.put("usuario_usuario", this.usuario_usuario);
        obj.put("clave_usuario", "464F5242494444454E");
        
        return obj;
    }
    
}
