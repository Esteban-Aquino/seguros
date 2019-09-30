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
public class Aseguradora {
  private int id_aseguradora;
  private String nombre_aseguradora;
  private Persona persona;
  private Usuario usuarioauditoria;

    public Aseguradora() {
        persona = new Persona();
        usuarioauditoria = new Usuario();
    }

    public Aseguradora(int id_aseguradora, String nombre_aseguradora, Persona persona, Usuario usuarioauditoria) {
        this.id_aseguradora = id_aseguradora;
        this.nombre_aseguradora = nombre_aseguradora;
        this.persona = persona;
        this.usuarioauditoria = usuarioauditoria;
        //System.out.println("Aseguradora: "+nombre_aseguradora+" Sistema: "+sistema.getNombre_sistema()+" Submenu: "+submenu.getNombre_submenu());
    }

    public int getId_aseguradora() {
        return id_aseguradora;
    }

    public void setId_aseguradora(int id_aseguradora) {
        this.id_aseguradora = id_aseguradora;
    }

    public String getNombre_aseguradora() {
        return nombre_aseguradora;
    }

    public void setNombre_aseguradora(String nombre_aseguradora) {
        this.nombre_aseguradora = nombre_aseguradora;
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
        obj.put("id_aseguradora", this.id_aseguradora);
             
        obj.put("id_persona", persona.getId_persona());
        obj.put("nombre_persona", persona.getNombre_persona());
        obj.put("nombre_fantasia", persona.getNombre_fantasia_persona());
        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        //System.out.println(obj);
        return obj;
    }
  
}
