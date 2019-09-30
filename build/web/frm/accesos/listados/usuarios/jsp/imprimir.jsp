<%@page import="net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.export.JRXlsExporter"%>
<%@page import="py.com.leader.modelo.Usuario"%>
<%@page import="py.com.leader.DAO.Conexion"%>
<%@page import="net.sf.jasperreports.engine.JasperExportManager"%>
<%@page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Connection"%>
<%
    int desde_id = Integer.parseInt(request.getParameter("desde_id"));
    int hasta_id = Integer.parseInt(request.getParameter("hasta_id"));

    String carpeta = application.getRealPath("/");
    System.out.println("--> "+carpeta);
    
    String jasperReport = carpeta+"rpt/usuarios.jasper";
    
    JasperPrint print = null;    
    Connection conn = null;
    
    try {
        Conexion conexion = new Conexion();
        conexion.conectar();
        conn = conexion.getCon();
        
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        Map parameters = new HashMap();
        parameters.put("DESDE_ID", desde_id);
        parameters.put("HASTA_ID", hasta_id);
        parameters.put("USUARIO", usuario.getUsuario_usuario());
        parameters.put("P_EMPRESA", "LEADER BROKERS");
        
        print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        response.setContentType("application/pdf");
        JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
        
        JRXlsExporter exporter = new JRXlsExporter();

        String xls = carpeta+"rpt/usuarios.xls";
        exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,print.toString());
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, xls);
        exporter.exportReport();      
        
        response.getOutputStream().flush();
        response.getOutputStream().close();
    } catch (Exception ex) {
        ex.printStackTrace();
        System.out.println(ex.getMessage());
    } finally {
        if (conn != null) {
            conn.close();
        }
    }
%>
