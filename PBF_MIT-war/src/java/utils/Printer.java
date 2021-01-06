package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class Printer {

    private JasperPrint jasperPrint;
    private static String user = "postgres";
    private static String password = "batrapi";

    public static String driver = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://localhost:5432/pbf_mit_db";
    public static Connection con;

    public void print(String path, Map parameters)
            throws Exception {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(path);

            parameters.put("REPORT_LOCALE", FacesContext.getCurrentInstance().getViewRoot().getLocale());

            this.jasperPrint = JasperFillManager.fillReport(reportPath, parameters, con);

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + path.substring(path.lastIndexOf("/"), path.lastIndexOf(".")) + ".pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(this.jasperPrint, servletOutputStream);

            FacesContext.getCurrentInstance().responseComplete();
            con.close();
        } catch (IOException e) {
            throw new IOException(e);
        } catch (JRException e) {
            throw new JRException(e);
        }
    }
}
