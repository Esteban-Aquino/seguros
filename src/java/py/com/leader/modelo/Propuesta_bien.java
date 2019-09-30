/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.modelo;

import java.text.ParseException;
import org.json.simple.JSONObject;


/**
 *
 * @author esteban
 */
public class Propuesta_bien {
    private int id_propuesta_bien;
    private int id_propuesta;
    private Bien bien;

    public Propuesta_bien() {
    }

    public Propuesta_bien(int id_propuesta_bien, int id_propuesta, Bien bien) {
        this.id_propuesta_bien = id_propuesta_bien;
        this.id_propuesta = id_propuesta;
        this.bien = bien;
    }

    public JSONObject getJSONObject() throws ParseException {
        JSONObject obj = new JSONObject();
        obj.put("id_propuesta_bien", this.id_propuesta_bien);
        obj.put("id_propuesta", this.getId_propuesta());
        obj.put("id_bien", this.bien.getId_bien());
        obj.put("marca_bien", this.bien.getMarca_bien());
        obj.put("modelo_bien", this.bien.getModelo_bien());
        obj.put("tip_bien", this.bien.getTip_bien());
        obj.put("matricula_bien", this.bien.getMatricula_bien());
        obj.put("anio_bien", this.bien.getAnio_bien());
        obj.put("color_bien", this.bien.getColor_bien());
        obj.put("num_motor_bien", this.bien.getNum_motor_bien());
        obj.put("num_carroceria_bien", this.bien.getNum_carroceria_bien());
        obj.put("descripcion_bien", this.bien.getDescripcion_bien());

        obj.put("id_tipo_bien", this.bien.getTipo_bien().getId_tipo_bien());
        obj.put("nombre_tipo_bien", this.bien.getTipo_bien().getNombre_tipo_bien());
        return obj;
    }
    
    public int getId_propuesta_bien() {
        return id_propuesta_bien;
    }

    public void setId_propuesta_bien(int id_propuesta_bien) {
        this.id_propuesta_bien = id_propuesta_bien;
    }

    public int getId_propuesta() {
        return id_propuesta;
    }

    public void setId_propuesta(int id_propuesta) {
        this.id_propuesta = id_propuesta;
    }


    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }
    
    
    
}
