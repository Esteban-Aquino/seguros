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
public class Tipo_bien {
  private int id_tipo_bien;
  private String nombre_tipo_bien;
  private Usuario usuarioauditoria;

    public Tipo_bien() {
        usuarioauditoria = new Usuario();
    }

    public Tipo_bien(int id_tipo_bien, String nombre_tipo_bien, Usuario usuarioauditoria) {
        this.usuarioauditoria = new Usuario();
        this.id_tipo_bien = id_tipo_bien;
        this.nombre_tipo_bien = nombre_tipo_bien;
        this.usuarioauditoria = usuarioauditoria;
    }

    public int getId_tipo_bien() { 
        return id_tipo_bien;
    }

    public void setId_tipo_bien(int id_tipo_bien) {
        this.id_tipo_bien = id_tipo_bien;
    }

    public String getNombre_tipo_bien() {
        return nombre_tipo_bien;
    }

    public void setNombre_tipo_bien(String nombre_tipo_bien) {
        this.nombre_tipo_bien = nombre_tipo_bien;
    }


    public Usuario getUsuarioauditoria() {
        return usuarioauditoria;
    }

    public void setUsuarioauditoria(Usuario usuarioauditoria) {
        this.usuarioauditoria = usuarioauditoria;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id_tipo_bien", this.id_tipo_bien);
        obj.put("nombre_tipo_bien", this.getNombre_tipo_bien());
        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        return obj;
    }
  
}
