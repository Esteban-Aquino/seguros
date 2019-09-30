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
public class Poliza_cuota {
  private int id_poliza_cuota;
  private int id_poliza;
  private int nro_cuota_poliza_cuota;
  private java.sql.Date vencimiento_poliza_cuota;
  private BigDecimal monto_cuota_poliza_cuota;

    public Poliza_cuota() {
    }

    public Poliza_cuota(int id_poliza_cuota, int id_poliza, int nro_cuota_poliza_cuota, Date vencimiento_poliza_cuota, BigDecimal monto_cuota_poliza_cuota) {
        this.id_poliza_cuota = id_poliza_cuota;
        this.id_poliza = id_poliza;
        this.nro_cuota_poliza_cuota = nro_cuota_poliza_cuota;
        this.vencimiento_poliza_cuota = vencimiento_poliza_cuota;
        this.monto_cuota_poliza_cuota = monto_cuota_poliza_cuota;
    }
    public JSONObject getJSONObject() throws ParseException {
        JSONObject obj = new JSONObject();
        obj.put("id_poliza_cuota", this.getId_poliza_cuota());
        obj.put("id_poliza", this.getId_poliza());
        obj.put("nro_cuota_poliza_cuota", this.getNro_cuota_poliza_cuota());
        obj.put("vencimiento_poliza_cuota", util.parseStringDateJava(this.getVencimiento_poliza_cuota() , "yyyy-MM-dd"));
        obj.put("monto_cuota_poliza_cuota", this.getMonto_cuota_poliza_cuota());
        return obj;
    }
    public int getId_poliza_cuota() {
        return id_poliza_cuota;
    }

    public void setId_poliza_cuota(int id_poliza_cuota) {
        this.id_poliza_cuota = id_poliza_cuota;
    }

    public int getId_poliza() {
        return id_poliza;
    }

    public void setId_poliza(int id_poliza) {
        this.id_poliza = id_poliza;
    }


    public int getNro_cuota_poliza_cuota() {
        return nro_cuota_poliza_cuota;
    }

    public void setNro_cuota_poliza_cuota(int nro_cuota_poliza_cuota) {
        this.nro_cuota_poliza_cuota = nro_cuota_poliza_cuota;
    }

    public Date getVencimiento_poliza_cuota() {
        return vencimiento_poliza_cuota;
    }

    public void setVencimiento_poliza_cuota(Date vencimiento_poliza_cuota) {
        this.vencimiento_poliza_cuota = vencimiento_poliza_cuota;
    }

    public BigDecimal getMonto_cuota_poliza_cuota() {
        return monto_cuota_poliza_cuota;
    }

    public void setMonto_cuota_poliza_cuota(BigDecimal monto_cuota_poliza_cuota) {
        this.monto_cuota_poliza_cuota = monto_cuota_poliza_cuota;
    }
  
  
}
