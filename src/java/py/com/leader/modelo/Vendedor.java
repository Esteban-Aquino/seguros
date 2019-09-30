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
public class Vendedor {
  private int id_vendedor;
  private String nombre_vendedor;
  private String matricula_vendedor;
  private Persona persona;
  private Usuario usuarioauditoria;

    public Vendedor() {
        persona = new Persona();
        usuarioauditoria = new Usuario();
    }

    public Vendedor(int id_vendedor, String nombre_vendedor, String matricula_vendedor, Persona persona, Usuario usuarioauditoria) {
        this.persona = new Persona();
        this.usuarioauditoria = new Usuario();
        this.id_vendedor = id_vendedor;
        this.nombre_vendedor = nombre_vendedor;
        this.matricula_vendedor = matricula_vendedor;
        this.persona = persona;
        this.usuarioauditoria = usuarioauditoria;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getNombre_vendedor() {
        return nombre_vendedor;
    }

    public void setNombre_vendedor(String nombre_vendedor) {
        this.nombre_vendedor = nombre_vendedor;
    }

    public String getMatricula_vendedor() {
        return matricula_vendedor;
    }

    public void setMatricula_vendedor(String matricula_vendedor) {
        this.matricula_vendedor = matricula_vendedor;
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
        obj.put("id_vendedor", this.id_vendedor);
        obj.put("id_persona", persona.getId_persona());
        obj.put("nombre_persona", persona.getNombre_persona());
        obj.put("nombre_fantasia", persona.getNombre_fantasia_persona());
        obj.put("matricula_vendedor", this.getMatricula_vendedor());
        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        //System.out.println(obj);
        return obj;
    }
  
}
