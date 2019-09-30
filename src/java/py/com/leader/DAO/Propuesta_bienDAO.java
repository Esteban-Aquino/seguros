/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import py.com.leader.modelo.Propuesta_bien;

/**
 *
 * @author esteban
 */
public class Propuesta_bienDAO {
    public Propuesta_bien buscarPropuestaBien(int id_propuesta, int id_bien){
        Propuesta_bien propuesta_bien = new Propuesta_bien();
        Conexion conexion = new Conexion();
        BienDAO bienDAO = new BienDAO();
        if (conexion.conectar()) {
            try {
                String sql = "select distinct 1 existe " +
                             "from propuestas_bienes " +
                             "where id_bien_propuesta_bien = ? " +
                             "and id_propuesta_propuesta_bien = ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_bien);
                    ps.setInt(2, id_propuesta);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        propuesta_bien.setBien(bienDAO.buscarId(id_bien));
                        propuesta_bien.setId_propuesta(id_propuesta);
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error Propuesta_bienDAO:--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        
        return propuesta_bien;
    }
    
    public boolean buscarPropuestaBienBool(int id_propuesta, int id_bien){
        boolean existe = false;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select distinct 1 existe " +
                             "from propuestas_bienes " +
                             "where id_bien_propuesta_bien = ? " +
                             "and id_propuesta_propuesta_bien = ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_bien);
                    ps.setInt(2, id_propuesta);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        existe = true;
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error Propuesta_bienDAO:--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        
        return existe;
    }
}
