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
public class Permiso {
  private int id_permiso;
  private Rol rol;
  private Formulario formulario;
  private boolean agregar_permiso;
  private boolean modificar_permiso;
  private boolean eliminar_permiso;
  private boolean consultar_permiso;
  private boolean listar_permiso;
  private boolean informes_permiso;
  private boolean exportar_permiso;
  private Usuario usuarioauditoria;

    public Permiso() {
    }

    public Permiso(int id_permiso, Rol rol, Formulario formulario, boolean agregar_permiso, boolean modificar_permiso, boolean eliminar_permiso, boolean consultar_permiso, boolean listar_permiso, boolean informes_permiso, boolean exportar_permiso, Usuario usuarioauditoria) {
        this.id_permiso = id_permiso;
        this.rol = rol;
        this.formulario = formulario;
        this.agregar_permiso = agregar_permiso;
        this.modificar_permiso = modificar_permiso;
        this.eliminar_permiso = eliminar_permiso;
        this.consultar_permiso = consultar_permiso;
        this.listar_permiso = listar_permiso;
        this.informes_permiso = informes_permiso;
        this.exportar_permiso = exportar_permiso;
        this.usuarioauditoria = usuarioauditoria;
    }

    public int getId_permiso() {
        return id_permiso;
    }

    public void setId_permiso(int id_permiso) {
        this.id_permiso = id_permiso;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public boolean isAgregar_permiso() {
        return agregar_permiso;
    }

    public void setAgregar_permiso(boolean agregar_permiso) {
        this.agregar_permiso = agregar_permiso;
    }

    public boolean isModificar_permiso() {
        return modificar_permiso;
    }

    public void setModificar_permiso(boolean modificar_permiso) {
        this.modificar_permiso = modificar_permiso;
    }

    public boolean isEliminar_permiso() {
        return eliminar_permiso;
    }

    public void setEliminar_permiso(boolean eliminar_permiso) {
        this.eliminar_permiso = eliminar_permiso;
    }

    public boolean isConsultar_permiso() {
        return consultar_permiso;
    }

    public void setConsultar_permiso(boolean consultar_permiso) {
        this.consultar_permiso = consultar_permiso;
    }

    public boolean isListar_permiso() {
        return listar_permiso;
    }

    public void setListar_permiso(boolean listar_permiso) {
        this.listar_permiso = listar_permiso;
    }

    public boolean isInformes_permiso() {
        return informes_permiso;
    }

    public void setInformes_permiso(boolean informes_permiso) {
        this.informes_permiso = informes_permiso;
    }

    public boolean isExportar_permiso() {
        return exportar_permiso;
    }

    public void setExportar_permiso(boolean exportar_permiso) {
        this.exportar_permiso = exportar_permiso;
    }

    public Usuario getUsuarioauditoria() {
        return usuarioauditoria;
    }

    public void setUsuarioauditoria(Usuario usuarioauditoria) {
        this.usuarioauditoria = usuarioauditoria;
    }
  
  public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("id_permiso", this.id_permiso);
        //Rol
        obj.put("id_rol", this.getRol().getId_rol());
        obj.put("nombre_rol", this.getRol().getNombre_rol());
        //Formulario
        obj.put("id_formulario", this.getFormulario().getId_formulario());
        obj.put("nombre_formulario", this.getFormulario().getNombre_formulario());
        obj.put("url_formulario", this.getFormulario().getUrl_formulario());
        //Sistema
        obj.put("id_sistema", this.getFormulario().getSistema().getId_sistema());
        obj.put("nombre_sistema", this.getFormulario().getSistema().getNombre_sistema());
        obj.put("url_sistema", this.getFormulario().getSistema().getUrl_sistema());
        //Submenu
        obj.put("id_submenu", this.getFormulario().getSubmenu().getId_submenu());
        obj.put("nombre_submenu", this.getFormulario().getSubmenu().getNombre_submenu());
        obj.put("url_submenu", this.getFormulario().getSubmenu().getUrl_submenu());
        //Permisos
        obj.put("agregar_permiso", this.agregar_permiso);
        obj.put("modificar_permiso", this.modificar_permiso);
        obj.put("eliminar_permiso", this.eliminar_permiso);
        obj.put("consultar_permiso", this.consultar_permiso);
        obj.put("listar_permiso", this.listar_permiso);
        obj.put("informes_permiso", this.informes_permiso);
        obj.put("exportar_permiso", this.exportar_permiso);
                
        obj.put("id_usuarioauditoria", this.usuarioauditoria.getId_usuario());
        obj.put("nombre_usuarioauditoria", this.getUsuarioauditoria().getNombre_usuario());
        obj.put("usuario_usuarioauditoria", this.getUsuarioauditoria().getUsuario_usuario());
        return obj;
    }
}
