/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.modelo;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import org.json.simple.JSONObject;
import py.com.leader.util.util;

/**
 *
 * @author esteban
 */
public class Propuesta_cuota {
  private int id_propuesta_cuota;
  private int id_propuesta;
  private int nro_cuota_propuesta_cuota;
  private java.sql.Date vencimiento_propuesta_cuota;
  private BigDecimal monto_cuota_propuesta_cuota;

    public Propuesta_cuota() {
    }

    public Propuesta_cuota(int id_propuesta_cuota, int id_propuesta, int nro_cuota_propuesta_cuota, Date vencimiento_propuesta_cuota, BigDecimal monto_cuota_propuesta_cuota) {
        this.id_propuesta_cuota = id_propuesta_cuota;
        this.id_propuesta = id_propuesta;
        this.nro_cuota_propuesta_cuota = nro_cuota_propuesta_cuota;
        this.vencimiento_propuesta_cuota = vencimiento_propuesta_cuota;
        this.monto_cuota_propuesta_cuota = monto_cuota_propuesta_cuota;
    }
    public JSONObject getJSONObject() throws ParseException {
        JSONObject obj = new JSONObject();
        obj.put("id_propuesta_cuota", this.getId_propuesta_cuota());
        obj.put("id_propuesta", this.getId_propuesta());
        obj.put("nro_cuota_propuesta_cuota", this.getNro_cuota_propuesta_cuota());
        obj.put("vencimiento_propuesta_cuota", util.parseStringDateJava(this.getVencimiento_propuesta_cuota() , "yyyy-MM-dd"));
        obj.put("monto_cuota_propuesta_cuota", this.getMonto_cuota_propuesta_cuota());
        return obj;
    }
    public int getId_propuesta_cuota() {
        return id_propuesta_cuota;
    }

    public void setId_propuesta_cuota(int id_propuesta_cuota) {
        this.id_propuesta_cuota = id_propuesta_cuota;
    }

    public int getId_propuesta() {
        return id_propuesta;
    }

    public void setId_propuesta(int id_propuesta) {
        this.id_propuesta = id_propuesta;
    }


    public int getNro_cuota_propuesta_cuota() {
        return nro_cuota_propuesta_cuota;
    }

    public void setNro_cuota_propuesta_cuota(int nro_cuota_propuesta_cuota) {
        this.nro_cuota_propuesta_cuota = nro_cuota_propuesta_cuota;
    }

    public Date getVencimiento_propuesta_cuota() {
        return vencimiento_propuesta_cuota;
    }

    public void setVencimiento_propuesta_cuota(Date vencimiento_propuesta_cuota) {
        this.vencimiento_propuesta_cuota = vencimiento_propuesta_cuota;
    }

    public BigDecimal getMonto_cuota_propuesta_cuota() {
        return monto_cuota_propuesta_cuota;
    }

    public void setMonto_cuota_propuesta_cuota(BigDecimal monto_cuota_propuesta_cuota) {
        this.monto_cuota_propuesta_cuota = monto_cuota_propuesta_cuota;
    }
  
  
}
