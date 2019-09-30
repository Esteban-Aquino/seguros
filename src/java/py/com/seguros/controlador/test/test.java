/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.seguros.controlador.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author esteban
 */
@WebServlet(name = "test", urlPatterns = {"/test"})
@MultipartConfig
public class test extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        boolean agregado = false;
        String name = "";
        try (PrintWriter out = response.getWriter()) {
            String dato = request.getParameter("dato");
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {
                // Create a factory for disk-based file items
                FileItemFactory factory = new DiskFileItemFactory();
                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                try {
                    String carpeta = getServletContext().getRealPath("/");
                    String UPLOAD_DIRECTORY = carpeta + "img/usuarios";
                    System.out.println("Ubicacion -> " + UPLOAD_DIRECTORY);
                    //List<FileItem> multiparts = upload.parseRequest(request);

                    Part filePart = request.getPart("file");
                    if (filePart != null) {
                        // prints out some information for debugging
                        System.out.println(filePart.getName());
                        System.out.println(filePart.getSize());
                        System.out.println(filePart.getContentType());
                    }else{
                        System.out.println("Nulo");
                    }
                    
                    /*for (FileItem item : multiparts) {
                        if (!item.isFormField()) {
                            name = new File(item.getName()).getName();
                            long tamanio = item.getSize();
                            String extencion = name.substring(name.length() - 3, name.length());
                            System.out.println("extencion -> " + extencion);
                            System.out.println("tamaño    -> " + tamanio);
                            if (extencion.equalsIgnoreCase("JPG") || extencion.equalsIgnoreCase("PNG")) {
                                item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
                                agregado = true;
                            }
                        }else{
                            System.out.println("---> "+item.getFieldName());
                        }                            
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String resp = "";
            if (agregado) {
                resp = name + "Guardado";
            } else {
                resp = "No se guardo";
            }
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Foto:" + resp + "</h1>");
            out.println("<h2>dato:" + dato + "</h2>");
            out.println("</body>");
            out.println("</html>");
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
