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
public class Sistema {
  private int id_sistema;
  private String nombre_sistema;
  private String url_sistema;
  private Usuario usuarioauditoria;

    public Sistema() {
        usuarioauditoria = new Usuario();
    }

    public Sistema(int id_sistema, String nombre_sistema, String url_sistema, Usuario usuarioauditoria) {
        this.id_sistema = id_sistema;
        this.nombre_sistema = nombre_sistema;
        this.url_sistema = url_sistema;
        this.usuarioauditoria = usuarioauditoria;
    }

    public int getId_sistema() {
        return id_sistema;
    }

    public void setId_sistema(int id_sistema) {
        this.id_sistema = id_sistema;
    }

    public String getNombre_sistema() {
        return nombre_sistema;
    }

    public void setNombre_sistema(String nombre_sistema) {
        this.nombre_sistema = nombre_sistema;
    }

    public String getUrl_sistema() {
        return url_sistema;
    }

    public void setUrl_sistema(String url_sistema) {
        this.url_sistema = url_sistema;
    }

    public Usuario getUsuarioauditoria() {
        return usuarioauditoria;
    }

    public void setUsuarioauditoria(Usuario usuarioauditoria) {
        this.usuarioauditoria = usuarioauditoria;
    }
  
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id_sistema", this.id_sistema);
        obj.put("nombre_sistema", this.nombre_sistema);
        obj.put("url_sistema", this.url_sistema);
        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        return obj;
    }

    public void setUsuarioauditoria(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
}
