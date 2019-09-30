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
public class Rol {
  private int id_rol;
  private String nombre_rol;
  private Usuario usuarioauditoria = new Usuario();

    public Rol() {
    }

    public Rol(int id_rol, String nombre_rol, Usuario id_usuarioauditoria) {
        this.id_rol = id_rol;
        this.nombre_rol = nombre_rol;
        this.usuarioauditoria = id_usuarioauditoria;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }

    public Usuario getUsuarioauditoria() {
        return usuarioauditoria;
    }

    public void setUsuarioauditoria(Usuario usuarioauditoria) {
        this.usuarioauditoria = usuarioauditoria;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id_rol", this.id_rol);
        obj.put("nombre_rol", this.nombre_rol);
        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        return obj;
    }
  
}
