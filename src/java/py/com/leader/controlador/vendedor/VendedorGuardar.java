/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.controlador.vendedor;

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
import py.com.leader.DAO.VendedorDAO;
import py.com.leader.modelo.Vendedor;
import py.com.leader.modelo.Persona;
import py.com.leader.modelo.Usuario;
import static py.com.leader.util.util.nvl;

/**
 *
 * @author Esteban
 */
@WebServlet(name = "VendedorGuardar", urlPatterns = {"/vendedor/guardar"})
public class VendedorGuardar extends HttpServlet {

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
            } else if (!request.getParameterMap().containsKey("id_persona")) {
                obj.put("correcto", correcto);
                obj.put("error", "Debe ingresar persona");
                out.print(obj);
                out.flush();
            } else {
                if ("0".equals(request.getParameter("id_persona"))) {
                    obj.put("correcto", correcto);
                    obj.put("error", "Debe ingresar persona");
                    out.print(obj);
                    out.flush();
                } else {
                    Vendedor vendedor = new Vendedor();
                    vendedor.setId_vendedor(Integer.parseInt(request.getParameter("id_vendedor")));
                    vendedor.setNombre_vendedor(request.getParameter("nombre_persona"));
                    vendedor.setMatricula_vendedor(request.getParameter("matricula_vendedor"));
                    Persona persona = new Persona();
                    persona.setCi_persona(request.getParameter("ci_persona"));
                    persona.setDireccion_persona(request.getParameter("direccion_persona"));
                    persona.setDni_persona(request.getParameter("dni_persona"));
                    persona.setEmail_persona(request.getParameter("email_persona"));
                    persona.setFisica_persona(!nvl(request.getParameter("fisica_persona"),"").isEmpty());
                    persona.setId_persona(Integer.parseInt(request.getParameter("id_persona")));
                    persona.setImagen_persona(request.getParameter("imagen_persona"));
                    persona.setNombre_fantasia_persona(request.getParameter("nombre_fantasia_persona"));
                    persona.setNombre_persona(request.getParameter("nombre_persona"));
                    persona.setRuc_persona(request.getParameter("ruc_persona"));
                    persona.setTelefono1_persona(request.getParameter("telefono1_persona"));
                    persona.setTelefono2_persona(request.getParameter("telefono2_persona"));
                    persona.setWeb_persona(request.getParameter("web_persona"));
                    vendedor.setPersona(persona);
                    VendedorDAO vendedorDAO = new VendedorDAO();
                    correcto = vendedorDAO.guardar(vendedor, new Usuario());
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
