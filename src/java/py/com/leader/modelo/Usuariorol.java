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
public class Usuariorol {
  private int id_usuariorol;
  private Usuario usuario;
  private Rol rol;
  private Usuario usuarioauditoria = new Usuario();

    public Usuariorol() {
    }

    public Usuariorol(int id_usuariorol, Usuario usuario, Rol rol, Usuario usuarioauditoria) {
        this.id_usuariorol = id_usuariorol;
        this.usuario = usuario;
        this.rol = rol;
        this.usuarioauditoria = usuarioauditoria;
    }

    public int getId_usuariorol() {
        return id_usuariorol;
    }

    public void setId_usuariorol(int id_usuariorol) {
        this.id_usuariorol = id_usuariorol;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Usuario getUsuarioauditoria() {
        return usuarioauditoria;
    }

    public void setUsuarioauditoria(Usuario usuarioauditoria) {
        this.usuarioauditoria = usuarioauditoria;
    }
  public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id_usuariorol", this.id_usuariorol);
        obj.put("id_usuario", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuario", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuario", this.getUsuarioauditoria().getUsuario_usuario());
        
        obj.put("id_rol", this.getRol().getId_rol());
        obj.put("nombre_rol", this.getRol().getNombre_rol());
        
        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        return obj;
    }
}
