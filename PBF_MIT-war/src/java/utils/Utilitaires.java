package utils;

import com.sun.javafx.collections.MappingChange;
import entities.Mouchard;
import entities.Utilisateur;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import sessions.MouchardFacadeLocal;

public class Utilitaires {

    private static final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public static final String path = servletContext.getRealPath("");
    public static final String chemin = servletContext.getContextPath();
    public static final String repertoire_document = "/fichiers/documents/";
    public static final String repertoire_pieces_j = "/fichiers/pieces/";

    public static void saveOperation(MouchardFacadeLocal mouchardFacadeLocal, String action, Utilisateur utilisateur) {
        try {
            Mouchard mouchard = new Mouchard();
            mouchard.setIdmouchard(mouchardFacadeLocal.nextVal());
            mouchard.setAction(action);
            mouchard.setIdutilisateur(utilisateur);
            mouchard.setDateaction(new Date());
            mouchard.setHeure(new Date());
            mouchardFacadeLocal.create(mouchard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getExtension(String nomFichier) {
        int taille = nomFichier.length();
        String extension = "";
        for (int i = 0; i < taille; i++) {
            if (nomFichier.charAt(i) == '.') {
                extension = "";
            } else {
                extension = extension + nomFichier.charAt(i);
            }
        }
        return extension;
    }

    public static boolean estExtensionImage(String extension) {
        if ((extension == null) || (extension.equals(""))) {
            return false;
        }
        String ext = extension.toUpperCase();
        if (ext.equals("PDF")) {
            return true;
        }
        if (ext.equals("DOCX")) {
            return true;
        }
        if (ext.equals("JPG")) {
            return true;
        }
        if (ext.equals("JPEG")) {
            return true;
        }
        if (ext.equals("GIF")) {
            return true;
        }
        if (ext.equals("PNG")) {
            return true;
        }
        if (ext.equals("BMP")) {
            return true;
        }
        if (ext.equals("DOCX")) {
            return true;
        }
        if (ext.equals("XLS")) {
            return true;
        }
        return false;
    }

    public static boolean estFichierImage(String nom) {
        String extension = getExtension(nom);
        if ((extension == null) || (extension.equals(""))) {
            return false;
        }
        String ext = extension.toUpperCase();
        if (ext.equals("PDF")) {
            return true;
        }
        if (ext.equals("JPG")) {
            return true;
        }
        if (ext.equals("JPEG")) {
            return true;
        }
        if (ext.equals("GIF")) {
            return true;
        }
        if (ext.equals("PNG")) {
            return true;
        }
        if (ext.equals("BMP")) {
            return true;
        }
        return false;
    }

    public static boolean handleFileUpload(FileUploadEvent event, String absoluteSavePath) {
        try {
            OutputStream saveFile = new FileOutputStream(new File(absoluteSavePath));
            InputStream in = event.getFile().getInputstream();
            byte[] buff = new byte[8];
            int n;
            while ((n = in.read(buff)) >= 0) {
                saveFile.write(buff);
                buff = new byte[8];
            }
        } catch (IOException ex) {
            FacesMessage message = new FacesMessage("Error", "Error While uploading " + event.getFile().getFileName());
            FacesContext.getCurrentInstance().addMessage(null, message);
            Logger.getLogger(Utilitaires.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        OutputStream saveFile;
        return true;
    }

    public static boolean handleFileUpload(UploadedFile file, String absoluteSavePath) {
        try {
            OutputStream saveFile = new FileOutputStream(new File(absoluteSavePath));
            InputStream in = file.getInputstream();
            byte[] buff = new byte[8];
            int n;
            while ((n = in.read(buff)) >= 0) {
                saveFile.write(buff);
                buff = new byte[8];
            }
        } catch (IOException ex) {
            FacesMessage message = new FacesMessage("Error", "Error While uploading " + file.getFileName());
            FacesContext.getCurrentInstance().addMessage(null, message);
            Logger.getLogger(Utilitaires.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        OutputStream saveFile;
        return true;
    }

    public static boolean CopierFichier(File Source, File Destination) {
        boolean resultat = false;

        FileInputStream filesource = null;
        FileOutputStream fileDestination = null;
        try {
            filesource = new FileInputStream(Source);
            fileDestination = new FileOutputStream(Destination);
            byte[] buffer = new byte[1000];
            int nblecture;
            while ((nblecture = filesource.read(buffer)) != -1) {
                fileDestination.write(buffer, 0, nblecture);
                buffer = new byte[8];
            }
            resultat = true;
        } catch (FileNotFoundException nf) {
            nf.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            try {
                filesource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                fileDestination.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultat;
    }

    public static Double arrondiNDecimales(double x, int n) {
        double pow = Math.pow(10.0D, n);
        return Double.valueOf(Math.floor(x * pow) / pow);
    }

    public static String formatPrenomMaj(String prenom) {
        if (prenom.isEmpty()) {
            return " ";
        }
        char prenLettre = '0';
        prenLettre = prenom.charAt(0);

        String leReste = prenom.substring(1, prenom.length());

        String lettre1 = String.valueOf(prenLettre);

        leReste = leReste.toLowerCase();

        return lettre1.toUpperCase() + leReste;
    }

    public static Date addDayToDate(Date date, int nbre) {
        DateTime date_time = new DateTime("" + (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate());
        date_time.plusDays(nbre);
        return date_time.toDate();
    }

    public static Integer duration(Date date1, Date date2) {
        DateTime dateDebut = new DateTime("" + (date1.getYear() + 1900) + "-" + (date1.getMonth() + 1) + "-" + date1.getDate() + "");
        DateTime dateFin = new DateTime("" + (date2.getYear() + 1900) + "-" + (date2.getMonth() + 1) + "-" + date2.getDate() + "");

        Integer nbjr = Integer.valueOf(Days.daysBetween(dateDebut, dateFin).getDays());
        return nbjr;
    }

    public static boolean isAccess(Long menu) {
        if (SessionMBean.getAccess().isEmpty()) {
            return false;
        } else {
            if (SessionMBean.getAccess().contains(1L)) {
                return true;
            } else {
                if (SessionMBean.getAccess().contains(menu)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public static boolean isAccess2(Long menu) {
        if (SessionMBean.getAccess().isEmpty()) {
            return false;
        } else {
            if (SessionMBean.getAccess().contains(menu)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static Date addDaysToDate(Date date, Integer days) {
        try {
            Date df = date;
            GregorianCalendar calendar = new java.util.GregorianCalendar();
            calendar.setTime(df);
            calendar.add(Calendar.DAY_OF_MONTH, days);
            return calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String cleanLinkProject(String lien) {
        lien = lien.replaceAll(" ", "_");
        lien = lien.replaceAll("-", "_");
        lien = lien.replaceAll("/", "_");
        lien = lien.replaceAll("'\'", "_");
        lien = lien.replaceAll("\\+", "_");
        lien = lien.toLowerCase();
        return lien;
    }

    public static Map completeLinkProject(String repertoire) {
        String lienRepertoire = "";

        File file = new File(SessionMBean.getParametrage().getRepertoire());
        int linux = 0;
        if (file.exists()) {
            File fileRepertoireProjet = new File(SessionMBean.getParametrage().getRepertoire() + "" + repertoire);
            if (!fileRepertoireProjet.exists()) {
                fileRepertoireProjet.mkdir();
            }
            lienRepertoire = SessionMBean.getParametrage().getRepertoire() + "" + repertoire;

            if (SessionMBean.getParametrage().getRepertoire().contains("/")) {
                lienRepertoire += "/";
                linux = 1;
            } else {
                linux = 0;
                lienRepertoire += '\\';
            }
        }

        Map m = new HashMap();
        m.put("systeme", linux);
        m.put("lien", lienRepertoire);
        return m;
    }

}
