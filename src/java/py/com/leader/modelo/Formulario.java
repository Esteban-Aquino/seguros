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
public class Formulario {
  private int id_formulario;
  private String nombre_formulario;
  private String url_formulario;
  private Sistema sistema;
  private Submenu submenu;
  private Usuario usuarioauditoria;

    public Formulario() {
        sistema = new Sistema();
        submenu = new Submenu();
        usuarioauditoria = new Usuario();
    }

    public Formulario(int id_formulario, String nombre_formulario, String url_formulario, Sistema sistema, Submenu submenu, Usuario usuarioauditoria) {
        this.id_formulario = id_formulario;
        this.nombre_formulario = nombre_formulario;
        this.url_formulario = url_formulario;
        this.sistema = sistema;
        this.submenu = submenu;
        this.usuarioauditoria = usuarioauditoria;
        //System.out.println("Formulario: "+nombre_formulario+" Sistema: "+sistema.getNombre_sistema()+" Submenu: "+submenu.getNombre_submenu());
    }

    public int getId_formulario() {
        return id_formulario;
    }

    public void setId_formulario(int id_formulario) {
        this.id_formulario = id_formulario;
    }

    public String getNombre_formulario() {
        return nombre_formulario;
    }

    public void setNombre_formulario(String nombre_formulario) {
        this.nombre_formulario = nombre_formulario;
    }

    public String getUrl_formulario() {
        return url_formulario;
    }

    public void setUrl_formulario(String url_formulario) {
        this.url_formulario = url_formulario;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        //System.out.println("Sistema:"+sistema.getNombre_sistema());
        this.sistema = sistema;
    }

    public Submenu getSubmenu() {
        return submenu;
    }

    public void setSubmenu(Submenu submenu) {
        //System.out.println("Submenu:"+submenu.getNombre_submenu());
        this.submenu = submenu;
    }

    public Usuario getUsuarioauditoria() {
        return usuarioauditoria;
    }

    public void setUsuarioauditoria(Usuario usuarioauditoria) {
        this.usuarioauditoria = usuarioauditoria;
    }
    
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id_formulario", this.id_formulario);
        obj.put("nombre_formulario", this.nombre_formulario);
        obj.put("url_formulario", this.url_formulario);
        
        obj.put("id_sistema", sistema.getId_sistema());
        obj.put("nombre_sistema", sistema.getNombre_sistema());
        obj.put("url_sistema", sistema.getUrl_sistema());
        
        obj.put("id_submenu", submenu.getId_submenu());
        obj.put("nombre_submenu", submenu.getNombre_submenu());
        obj.put("url_submenu", submenu.getUrl_submenu());
        
        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        //System.out.println(obj);
        return obj;
    }
  
}
