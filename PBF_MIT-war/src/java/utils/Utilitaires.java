package utils;

import entities.Acteur;
import entities.Mouchard;
import entities.Utilisateur;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
        return (Math.floor(x * pow) / pow);
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

        Integer nbjr = Days.daysBetween(dateDebut, dateFin).getDays();
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

    public static List<Receipient> extracEmail(List<Acteur> acteurs) {
        List<Receipient> list = new ArrayList<>();
        acteurs.forEach(a -> {
            try {
                if (a.getIdaddresse().getEmail() != null) {
                    list.add(new Receipient(a.getIdaddresse().getEmail(), a.getTitre()));
                }
            } catch (Exception e) {
            }
        });
        return list;
    }

    public static List<ReceipientSms> extracPhoneNumber(List<Acteur> acteurs) {

        return acteurs.stream()
                .filter((a) -> (a.getIdaddresse().getTelephone1() != null))
                .map(aa -> {
                    String contact = aa.getIdaddresse().getTelephone1().replaceAll(" ", "");
                    ReceipientSms rs = new ReceipientSms(contact);
                    rs.setActeur(aa);
                    return rs;
                }).collect(Collectors.toList());

        /* List<ReceipientSms> list = new ArrayList<>();
         acteurs.forEach((Acteur a) -> {
         try {
         if (a.getIdaddresse().getTelephone1() != null) {
         String contact = a.getIdaddresse().getTelephone1().replaceAll(" ", "");
         ReceipientSms rs = new ReceipientSms(contact);
         rs.setActeur(a);
         list.add(rs);
         }
         } catch (Exception e) {
         }
         });
         return list;*/
    }

    public static String removeFirstJsonChar(String jsonString, String regex) {
        jsonString = jsonString.replace("{\"" + regex + "\":", "");
        jsonString = jsonString.substring(0, (jsonString.length() - 1));
        return jsonString;
    }

    public static String removeCharInJson(String jsonString, String regex) {
        if (jsonString.contains(regex)) {
            jsonString = jsonString.replace(regex, "");
            jsonString = jsonString.substring(0, (jsonString.length() - 1));
        }
        return jsonString;
    }

    public static Map<String, Integer> countSms(String message) {
        Map<String, Integer> result = new HashMap();
        int nombrePages = 0;
        int nombreMots = 0;
        if (message != null && message.length() != 0) {
            Double val = Math.ceil(message.length() / 160.0D);
            nombrePages = val.intValue();
            nombreMots = message.length();
        }
        result.put("nombre_mots", nombreMots);
        result.put("nombre_pages", nombrePages);
        return result;
    }

    public static Map checkServeurSms(Integer api) {
        Map map = new HashMap();
        try {

            if (api.equals(1)) {
                String s = CheckAccount.returnDetailCompteJson(SessionMBean.getParametrage().getApiauthkey());
                try {
                    AllMySmsAccountCheck a = CheckAccount.getAccountDetail(s);
                    if (a != null) {
                        map = setSmsData(a.getNbSms(), "En marche");
                        map.put("all_data", a);
                    } else {
                        map = setSmsData(0, "Non disponible");
                    }
                } catch (Exception e) {
                    map = setSmsData(0, "Non disponible");
                }
            } else if (api.equals(2)) {
                map = getDetailOrangeApi(2);
                map.put("solde", ((int) map.get("solde")) + ((int) getDetailObmApi(3).get("solde")));
            } else if (api.equals(3)) {
                map = getDetailObmApi(3);
                map.put("solde", ((int) map.get("solde")) + ((int) getDetailOrangeApi(3).get("solde")));
            }
        } catch (Exception e) {
            map = setSmsData(0, "Non disponible");
        }
        return map;
    }

    public static Map getDetailOrangeApi(Integer api) {
        Map map = new HashMap();
        String access_token_json = OrangeSmsSender.auth();
        if (!access_token_json.equals("00001")) {
            try {
                OrangeAuthResponse authResponse = (OrangeAuthResponse) CheckAccount.fromJsonToObject(access_token_json, new OrangeAuthResponse());

                String balanceResponseJson = OrangeSmsSender.getBalance(authResponse.getAccess_token());
                balanceResponseJson = removeFirstJsonChar(balanceResponseJson, "partnerContracts");

                PartnerContract partnerContracts = (PartnerContract) CheckAccount.fromJsonToObject(balanceResponseJson, new PartnerContract());
                int nbre = 0;
                for (int i = 0; i < partnerContracts.getContracts().size(); i++) {
                    for (int j = 0; j < partnerContracts.getContracts().get(i).getServiceContracts().size(); j++) {
                        nbre += partnerContracts.getContracts().get(i).getServiceContracts().get(j).getAvailableUnits();
                    }
                }
                System.err.println("solde orange " + nbre);

                map = setSmsData(nbre, "En marche");
                map.put("access_token", authResponse.getAccess_token());
                map.put("all_data", partnerContracts);
            } catch (Exception e) {
                map = setSmsData(0, "Non disponible");
            }
        } else {
            map = setSmsData(0, "Non disponible");
        }
        return map;
    }

    public static Map getDetailObmApi(Integer api) {
        Map map = new HashMap();
        String access_token_json = ObmSms.auth();
        if (!access_token_json.contains("00001")) {
            try {
                ObmSmsAuthResponse authResponse = (ObmSmsAuthResponse) CheckAccount.fromJsonToObject(access_token_json, new ObmSmsAuthResponse());

                if (authResponse != null) {
                    String balance_json = ObmSms.getBalance(authResponse.getAccess_token());
                    if (!balance_json.contains("00001")) {
                        ObmsmsBalanceResponse balanceResponse = (ObmsmsBalanceResponse) CheckAccount.fromJsonToObject(balance_json, new ObmsmsBalanceResponse());
                        if (balanceResponse != null) {
                            map = setSmsData(Integer.valueOf(balanceResponse.getNumbersms()), "En marche");
                            map.put("access_token", authResponse.getAccess_token());
                            map.put("all_data", balance_json);
                        }
                    }
                }
            } catch (Exception e) {
                map = setSmsData(0, "Non disponible");
            }
        } else {
            map = setSmsData(0, "Non disponible");
        }
        return map;
    }

    private static Map setSmsData(int nb, String message) {
        Map map = new HashMap();
        map.put("solde", nb);
        map.put("etat", message);
        return map;
    }

    public static Map<String, List<ObjectContactActeur>> filterContact(List<ReceipientSms> list) {
        Map<String, List<ObjectContactActeur>> result = new HashMap<>();
        List<ObjectContactActeur> obms = new ArrayList<>();
        List<ObjectContactActeur> oranges = new ArrayList<>();

        for (ReceipientSms l : list) {
            String c = l.getActeur().getIdaddresse().getTelephone1().replaceAll(" ", "");

            if (c.startsWith("69") || c.startsWith("655") || c.startsWith("656") || c.startsWith("657") || c.startsWith("658") || c.startsWith("659")) {
                ObjectContactActeur o = new ObjectContactActeur(c, l.getActeur());
                oranges.add(o);
            } else {
                ObjectContactActeur o = new ObjectContactActeur(c, l.getActeur());
                obms.add(o);
            }
        }

        result.put("obm_mtn_nexttel", obms);
        result.put("orange_cm", oranges);
        return result;
    }

}
