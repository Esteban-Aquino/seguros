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
public class Submenu {
  private int id_submenu;
  private String nombre_submenu;
  private String url_submenu;
  private Usuario usuarioauditoria;

    public Submenu() {
        usuarioauditoria = new Usuario();
    }

    public Submenu(int id_submenu, String nombre_submenu, String url_submenu, Usuario usuarioauditoria) {
        this.id_submenu = id_submenu;
        this.nombre_submenu = nombre_submenu;
        this.url_submenu = url_submenu;
        this.usuarioauditoria = usuarioauditoria;
    }

    public int getId_submenu() {
        return id_submenu;
    }

    public void setId_submenu(int id_submenu) {
        this.id_submenu = id_submenu;
    }

    public String getNombre_submenu() {
        return nombre_submenu;
    }

    public void setNombre_submenu(String nombre_submenu) {
        //System.out.println("Submenu:"+nombre_submenu);
        this.nombre_submenu = nombre_submenu;
    }

    public String getUrl_submenu() {
        return url_submenu;
    }

    public void setUrl_submenu(String url_submenu) {
        this.url_submenu = url_submenu;
    }

    public Usuario getUsuarioauditoria() {
        return usuarioauditoria;
    }

    public void setUsuarioauditoria(Usuario usuarioauditoria) {
        this.usuarioauditoria = usuarioauditoria;
    }
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id_submenu", this.id_submenu);
        obj.put("nombre_submenu", this.nombre_submenu);
        obj.put("url_submenu", this.url_submenu);
        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        return obj;
    }
  
}
