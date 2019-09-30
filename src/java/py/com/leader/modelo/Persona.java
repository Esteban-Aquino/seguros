/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.modelo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.simple.JSONObject;
import py.com.leader.util.*;

/**
 *
 * @author esteban
 */
public class Persona {
    private int id_persona;
    private String nombre_persona;
    private String nombre_fantasia_persona;
    private boolean fisica_persona;
    private String ci_persona;
    private String ruc_persona;
    private String dni_persona;
    private String telefono1_persona;
    private String telefono2_persona;
    private String email_persona;
    private String web_persona;
    private String imagen_persona;
    private String direccion_persona;
    private java.sql.Date fecha_nacimiento_persona;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    public Persona() {
    }

    public Persona(int id_persona, String nombre_persona, String nombre_fantasia_persona, boolean fisica_persona, String ci_persona, String ruc_persona, String dni_persona, String telefono1_persona, String telefono2_persona, String email_persona, String web_persona, String imagen_persona, String direccion_persona, java.sql.Date fecha_nacimiento_persona) {
        this.id_persona = id_persona;
        this.nombre_persona = nombre_persona;
        this.nombre_fantasia_persona = nombre_fantasia_persona;
        this.fisica_persona = fisica_persona;
        this.ci_persona = ci_persona;
        this.ruc_persona = ruc_persona;
        this.dni_persona = dni_persona;
        this.telefono1_persona = telefono1_persona;
        this.telefono2_persona = telefono2_persona;
        this.email_persona = email_persona;
        this.web_persona = web_persona;
        this.imagen_persona = imagen_persona;
        this.direccion_persona = direccion_persona;
        this.fecha_nacimiento_persona = fecha_nacimiento_persona;
    }

    

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombre_persona() {
        return nombre_persona;
    }

    public void setNombre_persona(String nombre_persona) {
        this.nombre_persona = nombre_persona;
    }

    public String getNombre_fantasia_persona() {
        return nombre_fantasia_persona;
    }

    public void setNombre_fantasia_persona(String nombre_fantasia_persona) {
        this.nombre_fantasia_persona = nombre_fantasia_persona;
    }

    public boolean Fisica_persona() {
        return fisica_persona;
    }

    public void setFisica_persona(boolean fisica_persona) {
        this.fisica_persona = fisica_persona;
    }

    public String getCi_persona() {
        return ci_persona;
    }

    public void setCi_persona(String ci_persona) {
        this.ci_persona = ci_persona;
    }

    public String getRuc_persona() {
        return ruc_persona;
    }

    public void setRuc_persona(String ruc_persona) {
        this.ruc_persona = ruc_persona;
    }

    public String getDni_persona() {
        return dni_persona;
    }

    public void setDni_persona(String dni_persona) {
        this.dni_persona = dni_persona;
    }

    public String getTelefono1_persona() {
        return telefono1_persona;
    }

    public void setTelefono1_persona(String telefono1_persona) {
        this.telefono1_persona = telefono1_persona;
    }

    public String getTelefono2_persona() {
        return telefono2_persona;
    }

    public void setTelefono2_persona(String telefono2_persona) {
        this.telefono2_persona = telefono2_persona;
    }

    public String getEmail_persona() {
        return email_persona;
    }

    public void setEmail_persona(String email_persona) {
        this.email_persona = email_persona;
    }

    public String getWeb_persona() {
        return web_persona;
    }

    public void setWeb_persona(String web_persona) {
        this.web_persona = web_persona;
    }

    public String getImagen_persona() {
        return imagen_persona;
    }

    public void setImagen_persona(String imagen_persona) {
        this.imagen_persona = imagen_persona;
    }

    public String getDireccion_persona() {
        return direccion_persona;
    }

    public void setDireccion_persona(String direccion_persona) {
        this.direccion_persona = direccion_persona;
    }

    public java.sql.Date getFecha_nacimiento_persona() {
        return fecha_nacimiento_persona;
    }

    public void setFecha_nacimiento_persona(java.sql.Date fecha_nacimiento_persona) {
        this.fecha_nacimiento_persona = fecha_nacimiento_persona;
    }
    
    public JSONObject getJSONObject() throws ParseException {
        JSONObject obj = new JSONObject();
        obj.put("id_persona", this.id_persona);
        obj.put("nombre_persona", this.nombre_persona);
        obj.put("nombre_fantasia_persona", this.nombre_fantasia_persona);
        obj.put("fisica_persona", this.fisica_persona);
        obj.put("ci_persona", this.ci_persona);
        obj.put("ruc_persona", this.ruc_persona);
        obj.put("dni_persona", this.dni_persona);
        obj.put("telefono1_persona", this.telefono1_persona);
        obj.put("telefono2_persona", this.telefono2_persona);
        obj.put("email_persona", this.email_persona);
        obj.put("web_persona", this.web_persona);
        obj.put("imagen_persona", this.imagen_persona);
        obj.put("direccion_persona", this.direccion_persona);
        obj.put("fecha_nacimiento_persona", util.parseStringDateJava(this.fecha_nacimiento_persona, "dd/MM/yyyy"));
        return obj;
    }
}
