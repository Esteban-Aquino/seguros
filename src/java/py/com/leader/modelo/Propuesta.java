/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.modelo;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import py.com.leader.util.util;
import static py.com.leader.util.util.nvl;

/**
 *
 * @author esteban
 */
public class Propuesta {
  private int  id_propuesta;
  Cliente cliente;
  Aseguradora aseguradora;
  Vendedor vendedor;
  private java.sql.Date vigendia_desde_propuesta;
  private java.sql.Date vigencia_hasta_propuesta;
  //private BigDecimal prima_propuesta;
  private BigDecimal premio_propuesta;
  private BigDecimal suma_asegurada_propuesta;
  private String seccion_propuesta;
  private int cant_cuotas_propuesta;
  private java.sql.Date fecha_propuesta;
  private java.sql.Date fecha_vencimiento_propuesta;
  ArrayList <Propuesta_bien>  propuesta_bien;
  ArrayList <Propuesta_cuota>  propuesta_cuota;
    public Propuesta() {
    }

    public Propuesta(int id_propuesta, Cliente cliente, Aseguradora aseguradora, Vendedor vendedor, Date vigendia_desde_propuesta, Date vigencia_hasta_propuesta, BigDecimal premio_propuesta, BigDecimal suma_asegurada_propuesta, String seccion_propuesta, int cant_cuotas_propuesta, Date fecha_propuesta, Date fecha_vencimiento_propuesta, ArrayList <Propuesta_bien> propuesta_bien, ArrayList <Propuesta_cuota>  propuesta_cuota) {
        this.id_propuesta = id_propuesta;
        this.cliente = cliente;
        this.aseguradora = aseguradora;
        this.vendedor = vendedor;
        this.vigendia_desde_propuesta = vigendia_desde_propuesta;
        this.vigencia_hasta_propuesta = vigencia_hasta_propuesta;
        this.premio_propuesta = premio_propuesta;
        this.suma_asegurada_propuesta = suma_asegurada_propuesta;
        this.seccion_propuesta = seccion_propuesta;
        this.cant_cuotas_propuesta = cant_cuotas_propuesta;
        this.fecha_propuesta = fecha_propuesta;
        this.fecha_vencimiento_propuesta = fecha_vencimiento_propuesta;
        this.propuesta_bien = propuesta_bien;
        this.propuesta_cuota = propuesta_cuota;
    }

   
    public ArrayList getPropuesta_bien() {
        return propuesta_bien;
    }

    public void setPropuesta_bien(ArrayList propuesta_bien) {
        this.propuesta_bien = propuesta_bien;
    }

    public ArrayList <Propuesta_cuota> getPropuesta_cuota() {
        return propuesta_cuota;
    }

    public void setPropuesta_cuota(ArrayList <Propuesta_cuota>  propuesta_cuota) {
        this.propuesta_cuota = propuesta_cuota;
    }

    public int getId_propuesta() {
        return id_propuesta;
    }

    public void setId_propuesta(int id_propuesta) {
        this.id_propuesta = id_propuesta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Aseguradora getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(Aseguradora aseguradora) {
        this.aseguradora = aseguradora;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Date getVigendia_desde_propuesta() {
        return vigendia_desde_propuesta;
    }

    public void setVigendia_desde_propuesta(Date vigendia_desde_propuesta) {
        this.vigendia_desde_propuesta = vigendia_desde_propuesta;
    }

    public Date getVigencia_hasta_propuesta() {
        return vigencia_hasta_propuesta;
    }

    public void setVigencia_hasta_propuesta(Date vigencia_hasta_propuesta) {
        this.vigencia_hasta_propuesta = vigencia_hasta_propuesta;
    }

    /*public Number getPrima_propuesta() {
        return prima_propuesta;
    }*/

    /*public void setPrima_propuesta(BigDecimal prima_propuesta) {
        this.prima_propuesta = prima_propuesta;
    }*/

    public Number getPremio_propuesta() {
        return premio_propuesta;
    }

    public void setPremio_propuesta(BigDecimal premio_propuesta) {
        this.premio_propuesta = premio_propuesta;
    }

    public Number getSuma_asegurada_propuesta() {
        return suma_asegurada_propuesta;
    }

    public void setSuma_asegurada_propuesta(BigDecimal suma_asegurada_propuesta) {
        this.suma_asegurada_propuesta = suma_asegurada_propuesta;
    }

    public String getSeccion_propuesta() {
        return seccion_propuesta;
    }

    public void setSeccion_propuesta(String seccion_propuesta) {
        this.seccion_propuesta = seccion_propuesta;
    }

    public int getCant_cuotas_propuesta() {
        return cant_cuotas_propuesta;
    }

    public void setCant_cuotas_propuesta(int cant_cuotas_propuesta) {
        this.cant_cuotas_propuesta = cant_cuotas_propuesta;
    }

    public Date getFecha_propuesta() {
        return fecha_propuesta;
    }

    public void setFecha_propuesta(Date fecha_propuesta) {
        this.fecha_propuesta = fecha_propuesta;
    }

    public Date getFecha_vencimiento_propuesta() {
        return fecha_vencimiento_propuesta;
    }

    public void setFecha_vencimiento_propuesta(Date fecha_vencimiento_propuesta) {
        this.fecha_vencimiento_propuesta = fecha_vencimiento_propuesta;
    }
  
    public JSONObject getJSONObject() throws ParseException {
        JSONObject obj = new JSONObject();
        JSONObject objBienes = new JSONObject();
        JSONObject objCuotas = new JSONObject();
        obj.put("id_propuesta", this.id_propuesta);
        obj.put("id_cliente", nvl(this.cliente.getId_cliente(),0));
        obj.put("nombre_persona_cliente", this.cliente.getPersona().getNombre_persona());
        obj.put("id_aseguradora_propuesta", this.aseguradora.getId_aseguradora());
        obj.put("nombre_persona_aseguradora", this.aseguradora.getPersona().getNombre_persona());
        obj.put("id_vendedor", nvl(this.vendedor.getId_vendedor(),0));
        obj.put("nombre_persona_vendedor", this.vendedor.getPersona().getNombre_persona());
        obj.put("vigendia_desde_propuesta", util.parseStringDateJava(this.vigendia_desde_propuesta , "yyyy-MM-dd"));
        obj.put("vigencia_hasta_propuesta", util.parseStringDateJava(this.vigencia_hasta_propuesta , "yyyy-MM-dd"));
        //obj.put("prima_propuesta", this.prima_propuesta);
        obj.put("premio_propuesta", this.premio_propuesta);
        obj.put("suma_asegurada_propuesta", this.suma_asegurada_propuesta);
        obj.put("seccion_propuesta", this.seccion_propuesta);
        obj.put("cant_cuotas_propuesta", this.cant_cuotas_propuesta);
        obj.put("fecha_propuesta", util.parseStringDateJava(this.fecha_propuesta , "yyyy-MM-dd"));
        obj.put("fecha_vencimiento_propuesta", util.parseStringDateJava(this.fecha_vencimiento_propuesta , "yyyy-MM-dd"));
        
        //Bienes
        for (int i = 0;i<propuesta_bien.size();i++){
            objBienes.put(i, propuesta_bien.get(i).getJSONObject());
        }
        obj.put("propuesta_bien", objBienes);
        //Cuotas
        for (int i = 0;i<propuesta_cuota.size();i++){
            objCuotas.put(i+1, propuesta_cuota.get(i).getJSONObject());
        }
        obj.put("propuesta_cuota", objCuotas);
        return obj;
    }
    
}
