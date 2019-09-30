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
public class Poliza {
  private int  id_poliza;
  Cliente cliente;
  Aseguradora aseguradora;
  Vendedor vendedor;
  private java.sql.Date vigendia_desde_poliza;
  private java.sql.Date vigencia_hasta_poliza;
  //private BigDecimal prima_poliza;
  private BigDecimal premio_poliza;
  private BigDecimal suma_asegurada_poliza;
  private String seccion_poliza;
  private int cant_cuotas_poliza;
  private java.sql.Date fecha_poliza;
  private java.sql.Date fecha_vencimiento_poliza;
  ArrayList <Poliza_bien>  poliza_bien;
  ArrayList <Poliza_cuota>  poliza_cuota;
    public Poliza() {
    }

    public Poliza(int id_poliza, Cliente cliente, Aseguradora aseguradora, Vendedor vendedor, Date vigendia_desde_poliza, Date vigencia_hasta_poliza, BigDecimal premio_poliza, BigDecimal suma_asegurada_poliza, String seccion_poliza, int cant_cuotas_poliza, Date fecha_poliza, Date fecha_vencimiento_poliza, ArrayList <Poliza_bien> poliza_bien, ArrayList <Poliza_cuota>  poliza_cuota) {
        this.id_poliza = id_poliza;
        this.cliente = cliente;
        this.aseguradora = aseguradora;
        this.vendedor = vendedor;
        this.vigendia_desde_poliza = vigendia_desde_poliza;
        this.vigencia_hasta_poliza = vigencia_hasta_poliza;
        this.premio_poliza = premio_poliza;
        this.suma_asegurada_poliza = suma_asegurada_poliza;
        this.seccion_poliza = seccion_poliza;
        this.cant_cuotas_poliza = cant_cuotas_poliza;
        this.fecha_poliza = fecha_poliza;
        this.fecha_vencimiento_poliza = fecha_vencimiento_poliza;
        this.poliza_bien = poliza_bien;
        this.poliza_cuota = poliza_cuota;
    }

   
    public ArrayList getPoliza_bien() {
        return poliza_bien;
    }

    public void setPoliza_bien(ArrayList poliza_bien) {
        this.poliza_bien = poliza_bien;
    }

    public ArrayList <Poliza_cuota> getPoliza_cuota() {
        return poliza_cuota;
    }

    public void setPoliza_cuota(ArrayList <Poliza_cuota>  poliza_cuota) {
        this.poliza_cuota = poliza_cuota;
    }

    public int getId_poliza() {
        return id_poliza;
    }

    public void setId_poliza(int id_poliza) {
        this.id_poliza = id_poliza;
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

    public Date getVigendia_desde_poliza() {
        return vigendia_desde_poliza;
    }

    public void setVigendia_desde_poliza(Date vigendia_desde_poliza) {
        this.vigendia_desde_poliza = vigendia_desde_poliza;
    }

    public Date getVigencia_hasta_poliza() {
        return vigencia_hasta_poliza;
    }

    public void setVigencia_hasta_poliza(Date vigencia_hasta_poliza) {
        this.vigencia_hasta_poliza = vigencia_hasta_poliza;
    }

    /*public Number getPrima_poliza() {
        return prima_poliza;
    }*/

    /*public void setPrima_poliza(BigDecimal prima_poliza) {
        this.prima_poliza = prima_poliza;
    }*/

    public Number getPremio_poliza() {
        return premio_poliza;
    }

    public void setPremio_poliza(BigDecimal premio_poliza) {
        this.premio_poliza = premio_poliza;
    }

    public Number getSuma_asegurada_poliza() {
        return suma_asegurada_poliza;
    }

    public void setSuma_asegurada_poliza(BigDecimal suma_asegurada_poliza) {
        this.suma_asegurada_poliza = suma_asegurada_poliza;
    }

    public String getSeccion_poliza() {
        return seccion_poliza;
    }

    public void setSeccion_poliza(String seccion_poliza) {
        this.seccion_poliza = seccion_poliza;
    }

    public int getCant_cuotas_poliza() {
        return cant_cuotas_poliza;
    }

    public void setCant_cuotas_poliza(int cant_cuotas_poliza) {
        this.cant_cuotas_poliza = cant_cuotas_poliza;
    }

    public Date getFecha_poliza() {
        return fecha_poliza;
    }

    public void setFecha_poliza(Date fecha_poliza) {
        this.fecha_poliza = fecha_poliza;
    }

    public Date getFecha_vencimiento_poliza() {
        return fecha_vencimiento_poliza;
    }

    public void setFecha_vencimiento_poliza(Date fecha_vencimiento_poliza) {
        this.fecha_vencimiento_poliza = fecha_vencimiento_poliza;
    }
  
    public JSONObject getJSONObject() throws ParseException {
        JSONObject obj = new JSONObject();
        JSONObject objBienes = new JSONObject();
        JSONObject objCuotas = new JSONObject();
        obj.put("id_poliza", this.id_poliza);
        obj.put("id_cliente", nvl(this.cliente.getId_cliente(),0));
        obj.put("nombre_persona_cliente", this.cliente.getPersona().getNombre_persona());
        obj.put("id_aseguradora_poliza", this.aseguradora.getId_aseguradora());
        obj.put("nombre_persona_aseguradora", this.aseguradora.getPersona().getNombre_persona());
        obj.put("id_vendedor", nvl(this.vendedor.getId_vendedor(),0));
        obj.put("nombre_persona_vendedor", this.vendedor.getPersona().getNombre_persona());
        obj.put("vigendia_desde_poliza", util.parseStringDateJava(this.vigendia_desde_poliza , "yyyy-MM-dd"));
        obj.put("vigencia_hasta_poliza", util.parseStringDateJava(this.vigencia_hasta_poliza , "yyyy-MM-dd"));
        //obj.put("prima_poliza", this.prima_poliza);
        obj.put("premio_poliza", this.premio_poliza);
        obj.put("suma_asegurada_poliza", this.suma_asegurada_poliza);
        obj.put("seccion_poliza", this.seccion_poliza);
        obj.put("cant_cuotas_poliza", this.cant_cuotas_poliza);
        obj.put("fecha_poliza", util.parseStringDateJava(this.fecha_poliza , "yyyy-MM-dd"));
        obj.put("fecha_vencimiento_poliza", util.parseStringDateJava(this.fecha_vencimiento_poliza , "yyyy-MM-dd"));
        
        //Bienes
        for (int i = 0;i<poliza_bien.size();i++){
            objBienes.put(i, poliza_bien.get(i).getJSONObject());
        }
        obj.put("poliza_bien", objBienes);
        //Cuotas
        for (int i = 0;i<poliza_cuota.size();i++){
            objCuotas.put(i+1, poliza_cuota.get(i).getJSONObject());
        }
        obj.put("poliza_cuota", objCuotas);
        return obj;
    }
    
}
