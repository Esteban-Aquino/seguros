/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.controlador.cliente;

import py.com.leader.controlador.cliente.*;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import py.com.leader.DAO.ClienteDAO;
import py.com.leader.modelo.Cliente;
import py.com.leader.modelo.Cobrador;
import py.com.leader.modelo.Persona;
import py.com.leader.modelo.Usuario;
import py.com.leader.modelo.Vendedor;
import static py.com.leader.util.util.nvl;

/**
 *
 * @author Esteban
 */
@WebServlet(name = "ClienteGuardar", urlPatterns = {"/cliente/guardar"})
public class ClienteGuardar extends HttpServlet {

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
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        try (PrintWriter out = response.getWriter()) {
            if (usuario == null) {
                obj.put("correcto", correcto);
                obj.put("error", "No se encuentra logueado");
                out.print(obj);
                out.flush();
            } else if (!request.getParameterMap().containsKey("id_persona_cliente")) {
                obj.put("correcto", correcto);
                obj.put("error", "Debe ingresar persona");
                out.print(obj);
                out.flush();
            } else {
                if ("0".equals(request.getParameter("id_persona_cliente"))) {
                    obj.put("correcto", correcto);
                    obj.put("error", "Debe ingresar persona");
                    out.print(obj);
                    out.flush();
                } else {
                    Cliente cliente = new Cliente();
                    cliente.setId_cliente(Integer.parseInt(request.getParameter("id_cliente")));
                    cliente.setNombre_cliente(request.getParameter("nombre_persona"));
                    Persona persona = new Persona();
                    persona.setId_persona(Integer.parseInt(request.getParameter("id_persona_cliente")));
                    Cobrador cobrador = new Cobrador();
                    cobrador.setId_cobrador(Integer.parseInt(request.getParameter("id_cobrador")));
                    Vendedor vendedor = new Vendedor();
                    vendedor.setId_vendedor(Integer.parseInt(request.getParameter("id_vendedor")));
                    cliente.setPersona(persona);
                    cliente.setCobrador(cobrador);
                    cliente.setVendedor(vendedor);
                    ClienteDAO clienteDAO = new ClienteDAO();
                    correcto = clienteDAO.guardar(cliente, new Usuario());
                    obj.put("correcto", correcto);
                    out.print(obj);
                    out.flush();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            obj.put("correcto", correcto);
            obj.put("error", nvl(e.getMessage(), "No se encuentra mensaje de error"));
            out.print(obj);
            out.flush();
            e.printStackTrace();
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
