/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.controlador.propuesta;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import py.com.leader.DAO.AseguradoraDAO;
import py.com.leader.DAO.BienDAO;
import py.com.leader.DAO.ClienteDAO;
import py.com.leader.DAO.PropuestaDAO;
import py.com.leader.DAO.VendedorDAO;
import py.com.leader.modelo.Aseguradora;
import py.com.leader.modelo.Bien;
import py.com.leader.modelo.Cliente;
import py.com.leader.modelo.Propuesta;
import py.com.leader.modelo.Propuesta_bien;
import py.com.leader.modelo.Propuesta_cuota;
import py.com.leader.modelo.Vendedor;
//import py.com.leader.DAO.PropuestaDAO;
//import py.com.leader.modelo.Propuesta;
import static py.com.leader.util.util.nvl;
import static py.com.leader.util.util.parseDateSql;

/**
 *
 * @author Esteban
 */
@WebServlet(name = "PropuestaGuardar", urlPatterns = {"/propuesta/guardar"})
public class PropuestaGuardar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        boolean correcto = false;
        JSONObject obj = new JSONObject();
        try (PrintWriter out = response.getWriter()) {
            /*System.out.println(request.getParameter("propuesta"));
            obj.put("error", "ok");
            out.print(obj);
            out.flush();*/
            try {
                if (!request.getParameterMap().containsKey("propuesta")) {
                    obj.put("correcto", correcto);
                    obj.put("error", "No se encuentra Propuesta");
                    out.print(obj);
                    out.flush();
                } else {
                    String JsonPropuesta = request.getParameter("propuesta");
                    JsonParser parser = new JsonParser();
                    JsonElement datos = parser.parse(JsonPropuesta);
                    JsonObject objPropuesta = datos.getAsJsonObject();
                    //System.err.println("Yes - "+objPropuesta.get("id_propuesta").toString().replace("\"","").replace("\"",""));
                    
                    Propuesta propuesta = new Propuesta();
                    propuesta.setId_propuesta(Integer.parseInt(objPropuesta.get("id_propuesta").toString().replace("\"","")));
                    Cliente cliente;
                    ClienteDAO cd = new ClienteDAO();
                    cliente = cd.buscarId(Integer.parseInt(objPropuesta.get("id_cliente_propuesta").toString().replace("\"","")));
                    propuesta.setCliente(cliente);
                    Aseguradora aseguradora;
                    AseguradoraDAO ad = new AseguradoraDAO();
                    aseguradora = ad.buscarId(Integer.parseInt(objPropuesta.get("id_aseguradora_propuesta").toString().replace("\"","")));
                    propuesta.setAseguradora(aseguradora);
                    Vendedor vendedor;
                    VendedorDAO vd = new VendedorDAO();
                    vendedor = vd.buscarId(Integer.parseInt(objPropuesta.get("id_vendedor_propuesta").toString().replace("\"","")));
                    propuesta.setVendedor(vendedor);
                    propuesta.setFecha_propuesta(parseDateSql(objPropuesta.get("fecha_propuesta").toString().replace("\"",""),"yyyy-MM-dd"));
                    propuesta.setFecha_vencimiento_propuesta(parseDateSql(objPropuesta.get("fecha_vencimiento_propuesta").toString().replace("\"",""),"yyyy-MM-dd"));
                    propuesta.setVigendia_desde_propuesta(parseDateSql(objPropuesta.get("vigendia_desde_propuesta").toString().replace("\"",""),"yyyy-MM-dd"));
                    propuesta.setVigencia_hasta_propuesta(parseDateSql(objPropuesta.get("vigencia_hasta_propuesta").toString().replace("\"",""),"yyyy-MM-dd"));
                    propuesta.setSeccion_propuesta(objPropuesta.get("seccion_propuesta").toString().replace("\"",""));
                    propuesta.setPremio_propuesta(new BigDecimal(objPropuesta.get("premio_propuesta").toString().replace("\"","")));
                    //propuesta.setPrima_propuesta(new BigDecimal(request.getParameter("prima_propuesta")));
                    propuesta.setSuma_asegurada_propuesta(new BigDecimal(objPropuesta.get("suma_asegurada_propuesta").toString().replace("\"","")));
                    propuesta.setCant_cuotas_propuesta(Integer.parseInt(objPropuesta.get("cant_cuotas_propuesta").toString().replace("\"","")));
                    PropuestaDAO propuestaDAO = new PropuestaDAO();
                    // Bienes
                    ArrayList <Propuesta_bien> propuestas_bienes = new ArrayList <Propuesta_bien>();
                    Bien bien;
                    BienDAO bienDAO = new BienDAO();
                    String JsonBienes = objPropuesta.get("bienes").toString();
                    JsonElement datosBienes = parser.parse(JsonBienes);
                    JsonObject objBienes = datosBienes.getAsJsonObject();
                    int i = 0;
                    while (i >= 0) {
                        try {
                            if (objBienes.has(String.valueOf(i))){
                                //System.out.print(i+":");
                                //System.out.println(parser.parse(objBienes.get(String.valueOf(i)).toString()).getAsJsonObject().get("id_bien").toString().replace("\"",""));
                                propuestas_bienes.add(new Propuesta_bien());
                                bien = bienDAO.buscarId(Integer.parseInt(parser.parse(objBienes.get(String.valueOf(i)).toString()).getAsJsonObject().get("id_bien").toString().replace("\"","")));
                                propuestas_bienes.get(i).setBien(bien);
                                propuestas_bienes.get(i).setId_propuesta(propuesta.getId_propuesta());
                                i += 1;
                            }else{
                                i = -1;
                            }
                        }catch(Exception e){
                            i = -1;
                            e.printStackTrace();
                        }
                    }
                    propuesta.setPropuesta_bien(propuestas_bienes);
                    // Cuotas
                    ArrayList <Propuesta_cuota> propuestas_cuotas = new ArrayList <Propuesta_cuota>();
                    Propuesta_cuota propuesta_cuota;
                    String JsonCuotas = objPropuesta.get("cuotas").toString();
                    JsonElement datosCuotas = parser.parse(JsonCuotas);
                    JsonObject objCuotas = datosCuotas.getAsJsonObject();
                    i = 0;
                    while (i >= 0) {
                        try {
                            if (objCuotas.has(String.valueOf(i+1))){
                                //System.out.print(i+":");
                                //System.out.println(parser.parse(objBienes.get(String.valueOf(i)).toString()).getAsJsonObject().get("id_bien").toString().replace("\"",""));
                                propuestas_cuotas.add(new Propuesta_cuota());
                                propuestas_cuotas.get(i).setNro_cuota_propuesta_cuota(Integer.parseInt(parser.parse(objCuotas.get(String.valueOf(i+1)).toString()).getAsJsonObject().get("nro_cuota").toString().replace("\"","")));
                                propuestas_cuotas.get(i).setVencimiento_propuesta_cuota(parseDateSql(parser.parse(objCuotas.get(String.valueOf(i+1)).toString()).getAsJsonObject().get("vencimiento").toString().replace("\"",""),"yyyy-MM-dd"));
                                propuestas_cuotas.get(i).setMonto_cuota_propuesta_cuota(new BigDecimal(parser.parse(objCuotas.get(String.valueOf(i+1)).toString()).getAsJsonObject().get("monto").toString().replace("\"","")));
                                i += 1;
                            }else{
                                i = -1;
                            }
                        }catch(Exception e){
                            i = -1;
                            e.printStackTrace();
                        }
                    }
                    propuesta.setPropuesta_cuota(propuestas_cuotas);
                    // Guardar Cuotas
                    
                    correcto = propuestaDAO.guardar(propuesta);
                    obj.put("correcto", correcto);
                    out.print(obj);
                    out.flush();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                obj.put("correcto", correcto);
                obj.put("error", nvl(e.getMessage(), "No se encuentra mensaje de error"));
                out.print(obj);
                out.flush();
            }
        } catch (IOException hex) {
            System.out.println("Error: " + hex.getMessage());
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
