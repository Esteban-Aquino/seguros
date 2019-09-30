/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import py.com.leader.modelo.Poliza_bien;

/**
 *
 * @author esteban
 */
public class Poliza_bienDAO {
    public Poliza_bien buscarPolizaBien(int id_poliza, int id_bien){
        Poliza_bien poliza_bien = new Poliza_bien();
        Conexion conexion = new Conexion();
        BienDAO bienDAO = new BienDAO();
        if (conexion.conectar()) {
            try {
                String sql = "select distinct 1 existe " +
                             "from polizas_bienes " +
                             "where id_bien_poliza_bien = ? " +
                             "and id_poliza_poliza_bien = ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_bien);
                    ps.setInt(2, id_poliza);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        poliza_bien.setBien(bienDAO.buscarId(id_bien));
                        poliza_bien.setId_poliza(id_poliza);
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error Poliza_bienDAO:--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        
        return poliza_bien;
    }
    
    public boolean buscarPolizaBienBool(int id_poliza, int id_bien){
        boolean existe = false;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select distinct 1 existe " +
                             "from polizas_bienes " +
                             "where id_bien_poliza_bien = ? " +
                             "and id_poliza_poliza_bien = ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_bien);
                    ps.setInt(2, id_poliza);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        existe = true;
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error Poliza_bienDAO:--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        
        return existe;
    }
}
