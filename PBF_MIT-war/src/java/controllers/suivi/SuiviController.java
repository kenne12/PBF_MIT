package controllers.suivi;

import com.google.common.io.Files;
import entities.Acteur;
import entities.Etape;
import entities.Etapeprojet;
import entities.Notification;
import entities.Piecejointes;
import entities.Programmation;
import entities.Projet;
import entities.Projetservice;
import entities.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.Transactional;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import utils.EmailRequest;
import utils.MailThread;
import utils.Receipient;
import utils.ReceipientSms;
import utils.SessionMBean;
import utils.SmsRequest;
import utils.SmsThread;
import utils.Utilitaires;

@ManagedBean
@SessionScoped
public class SuiviController extends AbstractSuiviController implements Serializable {

    @PostConstruct
    private void init() {
        try {
            if (SessionMBean.getUserAccount().getIdacteur().getIdservice().getCentral()) {
                projets = projetFacadeLocal.findAllRange(true, false);
            } else if (SessionMBean.getUserAccount().getIdacteur().getIdservice().getRegional()) {
                List<Projetservice> list = projetserviceFacadeLocal.findByIdserviceparent(SessionMBean.getUserAccount().getIdacteur().getIdservice().getIdservice(), true, false);
                projets = exTractProject(list);
            } else {
                List<Projetservice> list = projetserviceFacadeLocal.findByIdservice(SessionMBean.getUserAccount().getIdacteur().getIdservice().getIdservice(), true, false);
                projets = exTractProject(list);
            }
            listActeurCtn = acteurFacadeLocal.findAllRange(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Projet> exTractProject(List<Projetservice> list) {
        List<Projet> projets = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Projetservice ps : list) {
                projets.add(ps.getIdprojet());
            }
        }
        return projets;
    }

    public void updateServiceData() {
        try {
            projetservices.clear();
            if (projet.getIdprojet() != null) {
                projetservices = projetserviceFacadeLocal.findByIdprojet(projet.getIdprojet(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateData() {
        try {
            projetservices.clear();
            if (projet.getIdprojet() != null) {
                if (SessionMBean.getUserAccount().getIdacteur().getIdservice().getCentral()) {
                    projetservices = projetserviceFacadeLocal.findByIdprojetRegional(projet.getIdprojet(), false, false, true, false);
                } else if (SessionMBean.getUserAccount().getIdacteur().getIdservice().getRegional()) {
                    int idService = 0;
                    if (SessionMBean.getUserAccount().getIdacteur().getIdservice().getIdparent() != 0) {
                        idService = SessionMBean.getUserAccount().getIdacteur().getIdservice().getIdparent();
                    } else {
                        idService = SessionMBean.getUserAccount().getIdacteur().getIdservice().getIdservice();
                    }
                    projetservices = projetserviceFacadeLocal.findByIdserviceparentVs(idService, projet.getIdprojet(), true, false);
                } else {
                    projetservices = projetserviceFacadeLocal.findByIdservice(SessionMBean.getUserAccount().getIdacteur().getIdservice().getIdservice(), projet.getIdprojet());
                }
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            } else {
                projetservices.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEtape() {
        try {
            for (Etape e : selectedEtapes) {
                if (!findEtape(e)) {
                    Etapeprojet et = new Etapeprojet();
                    et.setIdetapeprojet(0L);
                    et.setIdetape(e);
                    etapeprojets.add(et);
                }
            }
            RequestContext.getCurrentInstance().execute("PF('AddetapeDialog').hide()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void addServices() {
        try {
            for (Service s : selectedServices) {
                if (!findService(s)) {
                    Projetservice ps = new Projetservice();
                    ps.setIdprojetservice(0L);
                    ps.setIdservice(s);
                    projetservices.add(ps);
                }
            }
            RequestContext.getCurrentInstance().execute("PF('AddserviceDialog').hide()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    private boolean findService(Service s) {
        boolean result = false;
        for (Projetservice ps : projetservices) {
            if (ps.getIdservice().equals(s)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean findEtape(Etape e) {
        boolean result = false;
        for (Etapeprojet et : etapeprojets) {
            if (et.getIdetape().equals(et)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public List<Projetservice> findServiceP(int idprojet) {
        return projetserviceFacadeLocal.findByIdprojet(idprojet, false);
    }

    public List<Programmation> findProgrammation(Projetservice p) {
        try {
            return programmationFacadeLocal.findByIdprojetservice(p.getIdprojetservice());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void prepareAddDocument(Programmation p) {
        try {
            programmation = p;
            RequestContext.getCurrentInstance().execute("PF('DocumentAddDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public boolean renderedDownload(boolean active, boolean valide, Acteur acteur) {
        try {
            if (acteur.equals(SessionMBean.getUserAccount().getIdacteur())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    public boolean renderedView(boolean active, boolean envoye) {
        if (envoye) {
            return false;
        }
        return true;
    }

    public boolean renderedPiece(boolean active) {
        return false;
    }

    public boolean renderedDownloadSimulationBtn(Programmation p) {
        if (p.getActive()) {
            if (!p.getValide()) {
                if (!p.getEnvoye()) {
                    if (Utilitaires.isAccess(31L)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean renderedViewBtn(boolean valide, boolean observee) {
        if (valide && observee) {
            return true;
        }
        return false;
    }

    public boolean renderedActivate(Programmation programmation, boolean envoye, boolean active) {
        if (active) {
            if (Utilitaires.isAccess(1L)) {
                if (envoye) {
                    return false;
                }
                if (programmation.getIdetapeprojet().getNumero() == 1) {
                    return false;
                }
                return true;
            }
            return true;
        }
        return true;
    }

    public boolean renderedViewObservation(boolean envoye, boolean valide, Programmation p) {
        try {
            if (envoye && valide) {
                if (Utilitaires.isAccess(1L)) {
                    return false;
                }

                if (SessionMBean.getUserAccount().getIdacteur().getIdservice().getRegional()) {
                    return false;
                }

                if (p.getIdacteur().equals(SessionMBean.getUserAccount().getIdacteur())) {
                    return false;
                }
                return true;
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    public String checkFooterColor(boolean valide, boolean observee, boolean observation_validee) {
        if (!valide) {
            return "red";
        } else if (observee) {
            if (observation_validee) {
                return "Green";
            }
            return "yellow";
        }
        return "green";
    }

    public String returnRetard(boolean active, int retard) {
        String result = "";
        if (active) {
            result = "Nbre Jr rétard : [" + retard + "]";
            return result;
        }
        return null;
    }

    public void viewDocument(Programmation p) {
        try {
            programmation = p;
            File f1 = new File(Utilitaires.path + "" + Utilitaires.repertoire_document + "" + programmation.getChemin());
            boolean flag = true;
            if (!f1.exists()) {
                File f2 = new File(p.getIdetapeprojet().getLienRepertoire() + "" + programmation.getChemin());
                flag = false;
                if (f2.exists()) {
                    Files.copy(f2, f1);
                    flag = true;
                }
            }

            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            if (flag) {
                type_fichier = p.getTypefichier();
                filename = Utilitaires.repertoire_document + "" + p.getChemin();
                RequestContext.getCurrentInstance().execute("PF('ProjetImprimerDialog').show()");
            } else {
                signalError("notification.fichier_introuvable");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void viewPieces(Piecejointes p) {
        try {
            piecejointe = p;
            boolean flag = true;
            File f1 = new File(Utilitaires.path + "" + Utilitaires.repertoire_pieces_j + "" + piecejointe.getChemin());
            if (!f1.exists()) {
                flag = false;
                File f2 = new File(SessionMBean.getParametrage().getRepertoirePiece() + "" + piecejointe.getChemin());
                if (f2.exists()) {
                    Files.copy(f2, f1);
                    flag = true;
                }
            }

            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            if (flag) {
                type_fichier = p.getTypefichier();
                filename = Utilitaires.repertoire_pieces_j + "" + p.getChemin();
                RequestContext.getCurrentInstance().execute("PF('ProjetImprimerDialog').show()");
            } else {
                signalError("notification.fichier_introuvable");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void prepareAddPiece(Programmation p) {
        try {
            disabledDownPiece = true;
            programmation = p;
            piecejointes = piecejointesFacadeLocal.findByIdprogrammation(p.getIdprogrammation());

            piecejointe = new Piecejointes();
            piecejointe.setObservation("-");

            this.openJoinedFileDialog();

            if (SessionMBean.getUserAccount().getIdacteur().getIdservice().getRegional()) {
                disabledDownPiece = false;
                return;
            }

            if (p.getIdacteur().equals(SessionMBean.getUserAccount().getIdacteur())) {
                disabledDownPiece = false;
                return;
            }
        } catch (Exception e) {
            disabledDownPiece = true;
            this.openJoinedFileDialog();
        }
    }

    private void openJoinedFileDialog() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        RequestContext.getCurrentInstance().execute("PF('PieceAddDialog').show()");
    }

    public void activateStape(Programmation p) {
        try {
            notification = new Notification();
            notification.setMail(p.isNotifEmailProgram());
            notification.setSms(p.isNotifSmsProgram());
            notification.setMessage("-");
            notification.setObjet("-");
            templateMessage = "0";

            acteurNotifiables.clear();
            if (listActeurCtn != null) {
                acteurNotifiables.addAll(listActeurCtn);
            }

            List<Acteur> listActeurAcv = new ArrayList<>();
            Service acv = serviceFacadeLocal.findByServiceParentAndRegion(p.getIdprojetservice().getIdservice().getIdparent(), true);
            if (acv != null) {
                listActeurAcv.addAll(acteurFacadeLocal.findByIdservice(acv.getIdservice()));
                if (listActeurAcv != null) {
                    acteurNotifiables.addAll(listActeurAcv);
                }
            }

            List<Programmation> programmations = programmationFacadeLocal.findByIdprojetIdservice(p.getIdprojetservice().getIdprojetservice());
            if (programmations != null) {
                programmations.forEach(obj -> {
                    if (!acteurNotifiables.contains(obj.getIdacteur())) {
                        acteurNotifiables.add(obj.getIdacteur());
                    }
                });
            }
            selectedActeurNotifiables.clear();

            programmation = p;
            programmation.setValide(true);
            if (!p.getEnvoye()) {
                programmation.setDateTransfert(new Date());
                programmation.setDaterealisation(new Date());
            }
            programmation.setDateValidation(p.getDaterealisation());
            RequestContext.getCurrentInstance().execute("PF('ValidationCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void viewObservation(Programmation p) {
        try {
            programmation = p;
            RequestContext.getCurrentInstance().execute("PF('ObservationViewDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void updateMessage() {
        try {
            notification.setMessage(programmation.getObservation());
        } catch (Exception e) {
        }
    }

    public void openActeurDialog() {
        RequestContext.getCurrentInstance().execute("PF('AddActeurDialog').show()");
    }

    public void addActeur() {
        RequestContext.getCurrentInstance().execute("PF('AddActeurDialog').hide()");
    }

    public void updateTemplate() {
        if (templateMessage.equals("0")) {
            notification.setMessage("RAS");
        } else if (templateMessage.equals("1")) {
            String message = "Bonjour ! \nLa CTN Vous Informe que les documents transmis dans le cadre du projet : "
                    + programmation.getIdetapeprojet().getIdprojet().getNom()
                    + " Ont été acceptés.\nCe Message a été envoyé automatiquement, nous vous remercions de ne pas répondre.";
            notification.setObjet("Validation");
            notification.setMessage(message);
        } else if (templateMessage.equals("2")) {
            String message = "Bonjour ! \nLa CTN vous informe que les documents transmis dans le cadre du projet : " + programmation.getIdetapeprojet().getIdprojet().getNom() + "\n"
                    + "Structure : " + programmation.getIdprojetservice().getIdservice().getNom() + "\n"
                    + "Etape : " + programmation.getIdetapeprojet().getIdetape().getNom() + "\n"
                    + "Pour des raisons suivantes : \n"
                    + programmation.getObservation() + "\n"
                    + "Ce Message a été envoyé automatiquement, nous vous remercions de ne pas répondre.";
            notification.setMessage(message);
            notification.setObjet("Rejet");
        } else if (templateMessage.equals("3")) {
            String message = "Bonjour !\nLa CTN Vous informe que des rémarques ont été faites sur les documents envoyés dans le cadre du projet -> " + programmation.getIdetapeprojet().getIdprojet().getNom()
                    + "Structure : " + programmation.getIdprojetservice().getIdservice().getNom() + "\n"
                    + "Etape : " + programmation.getIdetapeprojet().getIdetape().getNom() + "\n"
                    + "" + programmation.getObservation() + "\n"
                    + "Veuillez-Vous connecter sur le portail pour apporter des corrections sollicitées en pièces jointes.\nCe Message a été envoyé automatiquement, nous vous remercions de ne pas répondre.";
            notification.setObjet("Observation");
            notification.setMessage(message);
        }
    }

    @Transactional
    public void validate() {
        try {
            if (programmation.getDateprevisionnel() == null) {
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                signalError("notification.valider_etape_precedente");
                return;
            }

            if (programmation.getDaterealisation().after(programmation.getDateFinPrevisionnel())) {
                programmation.setRetard(Utilitaires.duration(programmation.getDateFinPrevisionnel(), programmation.getDaterealisation()));
            } else if (programmation.getDaterealisation().before(programmation.getDaterealisation())) {
                programmation.setRetard(Utilitaires.duration(programmation.getDateprevisionnel(), programmation.getDaterealisation()));
            } else {
                programmation.setRetard(0);
            }

            programmation.setNotifEmailValidation(true);
            Programmation p1 = programmationFacadeLocal.findByIdprojetIdservice(programmation.getIdprojetservice().getIdprojetservice(), (programmation.getIdetapeprojet().getNumero() + 1));

            if (p1 != null) {
                p1.setActive(true);
                p1.setEnvoye(false);
                p1.setRetard(0);
                p1.setDateprevisionnel(programmation.getDaterealisation());
                p1.setDateFinPrevisionnel(Utilitaires.addDaysToDate(programmation.getDaterealisation(), p1.getIdetapeprojet().getDelai()));
                p1.setDaterealisation(null);
                p1.setDateValidation(null);
                p1.setValide(false);
                p1.setNotifEmailProgram(true);
                programmationFacadeLocal.edit(p1);
            }

            programmation.setConteur(programmation.getConteur() + 1);
            programmationFacadeLocal.edit(programmation);

            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            RequestContext.getCurrentInstance().execute("PF('ValidationCreerDialog').hide()");
            signalSuccess();

            if (programmation.getValide()) {
                if ((programmation.getConteur() - 1) == 0) {
                    if (p1 != null) {
                        EmailRequest emailRequest = new EmailRequest();
                        emailRequest.setSubject("Notification : " + p1.getIdetapeprojet().getIdprojet().getNom());
                        emailRequest.setText("Bonjour M / Mme " + p1.getIdacteur().getNom() + ";\nLa CTN Vous informe de la date initiale de transfert des document pour le projet en objet "
                                + "Structure -> " + p1.getIdprojetservice().getIdservice().getNom() + " Que la date de début de transfert des documents c'est le : "
                                + sdf.format(p1.getDateprevisionnel()) + " Et la date de fin c'est le : " + sdf.format(p1.getDateFinPrevisionnel())
                                + "\nVeuillez vous connecter sur la plateforme pour télécharger les documents sollicités.\nCe Message a été envoyé automatiquement, nous vous remercions de ne pas répondre.");
                        try {
                            emailRequest.getReceipients().add(new Receipient(p1.getIdacteur().getIdaddresse().getEmail(), p1.getIdacteur().getTitre()));
                            MailThread mailThread = new MailThread(emailRequest);
                            mailThread.start();
                        } catch (Exception e) {
                        }
                    }
                }
            }

            List<Receipient> receipients = Utilitaires.extracEmail(selectedActeurNotifiables);
            if (notification.isMail()) {
                if (!receipients.isEmpty()) {
                    EmailRequest emailRequest = new EmailRequest();
                    emailRequest.setSender("CTNPBF");
                    emailRequest.setSubject(notification.getObjet());
                    emailRequest.setText(notification.getMessage());
                    emailRequest.setReceipients(receipients);
                    if (!emailRequest.getReceipients().isEmpty()) {
                        sendMail(emailRequest);
                    }
                }
            }

            List<ReceipientSms> receipientSmses = Utilitaires.extracPhoneNumber(selectedActeurNotifiables);
            if (notification.isSms()) {
                if (!receipientSmses.isEmpty()) {
                    SmsRequest smsRequest = new SmsRequest();
                    smsRequest.setText(notification.getMessage());
                    smsRequest.setReceipients(receipientSmses);
                    if (!smsRequest.getReceipients().isEmpty()) {
                        sendSms(smsRequest);
                    }
                }
            }

            if ((!receipients.isEmpty() || !receipientSmses.isEmpty()) == true) {
                notification.setIdnotification(notificationFacadeLocal.nextVal());
                notification.setDateEnvoi(Date.from(Instant.now()));
                notificationFacadeLocal.create(notification);
                notification.getActeurs().addAll((Collection<Acteur>) selectedActeurNotifiables);
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    private void sendMail(EmailRequest emailRequest) {
        MailThread mailThread = new MailThread(emailRequest);
        mailThread.start();
    }

    private void sendSms(SmsRequest smsRequest) {
        SmsThread smsThread = new SmsThread(smsRequest);
        smsThread.setMode("MULTIPLE");
        smsThread.start();
    }

    public void validateUser() {
        try {
            programmationFacadeLocal.edit(programmation);
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            RequestContext.getCurrentInstance().execute("PF('ObservationViewDialog').hide()");
            signalSuccess();
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void create() {

    }

    public void removePiece(int index, Piecejointes p) {
        try {
            if (p.getIdpiecejointes() != 0L) {
                ut.begin();
                piecejointesFacadeLocal.remove(p);
                ut.commit();
            }

            piecejointes.remove(p);

            File f1 = new File(Utilitaires.path + "" + Utilitaires.repertoire_pieces_j + "" + p.getChemin());
            if (f1.exists()) {
                f1.delete();
            }

            File f2 = new File(SessionMBean.getParametrage().getRepertoirePiece() + "" + p.getChemin());
            if (f2.exists()) {
                f2.delete();
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void simulateFileUpload(Programmation programmation) {
        try {
            try {
                if (programmation.getIdacteur() == null) {
                    signalError("aucun_acteur_associe");
                    return;
                }
            } catch (Exception e) {
                signalError("aucun_acteur_associe");
                return;
            }

            programmation.setChemin("-");
            programmation.setConteur(programmation.getConteur() + 1);
            programmation.setTypefichier("-");
            programmation.setEnvoye(true);
            programmation.setDateTransfert(new Date(System.currentTimeMillis()));
            programmation.setDaterealisation(null);
            if (programmation.getIdetapeprojet().getNumero() == 0) {
                programmation.setDaterealisation(new Date(System.currentTimeMillis()));
            }
            programmationFacadeLocal.edit(programmation);
            routine.feedBack("information", "/resources/tool_images/success.png", routine.localizeMessage("operation_reussie"));
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        } catch (Exception ex) {
            routine.catchException(ex, routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    private String treatCharacter(String chaine, String[] regex) {
        for (int i = 0; i < regex.length; i++) {
            if (regex[i].equals("é")) {
                chaine = chaine.replaceAll(regex[i], "e");
            } else if (regex[i].equals("à")) {
                chaine = chaine.replaceAll(regex[i], "a");
            } else {
                chaine = chaine.replaceAll(regex[i], "_");
            }
        }
        return chaine;
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            if (event.getFile() == null || event.getFile().getFileName() == null || event.getFile().getFileName().equals("")) {
                routine.feedBack("avertissement", "/resources/tool_images/error.png", routine.localizeMessage("nom_image_incorrect"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }

            if (!Utilitaires.estExtensionImage(Utilitaires.getExtension(event.getFile().getFileName()))) {
                routine.feedBack("avertissement", "/resources/tool_images/error.png", routine.localizeMessage("fichier_non_pris_en_charge"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }

            try {
                if (programmation.getIdacteur() == null) {
                    signalError("aucun_acteur_associe");
                    return;
                }
            } catch (Exception e) {
                signalError("aucun_acteur_associe");
                return;
            }

            String[] regex = {"-", "\\+", "à", " ", "\\+", "__"};

            String file_name = treatCharacter(event.getFile().getFileName(), regex);

            String nom = "Document_";
            nom += treatCharacter(programmation.getIdacteur().getIdservice().getCode(), regex);

            file_name = file_name.replaceAll(Utilitaires.getExtension(file_name), "");
            nom += "_" + file_name + "_" + programmation.getIdprogrammation() + "_." + Utilitaires.getExtension(event.getFile().getFileName());

            nom = nom.toLowerCase();
            FileOutputStream out;
            out = new FileOutputStream(Utilitaires.path + "" + Utilitaires.repertoire_document + "" + nom, true);

            byte[] bytes = event.getFile().getContents();
            out.write(bytes);

            filename = nom;
            programmation.setChemin(nom);
            programmation.setConteur(programmation.getConteur() + 1);
            programmation.setTypefichier(event.getFile().getContentType());
            programmation.setEnvoye(true);
            programmation.setDateTransfert(new Date());
            programmation.setDaterealisation(null);
            if (programmation.getIdetapeprojet().getNumero() == 0) {
                programmation.setDaterealisation(new Date());
            }
            programmationFacadeLocal.edit(programmation);
            routine.feedBack("information", "/resources/tool_images/success.png", routine.localizeMessage("operation_reussie"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");

            File f1 = new File(Utilitaires.path + "" + Utilitaires.repertoire_document + "" + nom);
            File f2 = new File(programmation.getIdetapeprojet().getLienRepertoire() + "" + nom);
            Files.copy(f1, f2);
        } catch (IOException ex) {
            routine.catchException(ex, routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void handleFileUpload1(FileUploadEvent event) {
        try {
            if (event.getFile() == null || event.getFile().getFileName() == null || event.getFile().getFileName().equals("")) {
                routine.feedBack("avertissement", "/resources/tool_images/error.png", routine.localizeMessage("nom_image_incorrect"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }

            if (!Utilitaires.estExtensionImage(Utilitaires.getExtension(event.getFile().getFileName()))) {
                routine.feedBack("avertissement", "/resources/tool_images/error.png", routine.localizeMessage("fichier_non_pris_en_charge"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }

            try {
                if (programmation.getIdacteur() == null) {
                    signalError("aucun_acteur_associe");
                    return;
                }
            } catch (Exception e) {
                signalError("aucun_acteur_associe");
                return;
            }

            piecejointe.setIdpiecejointes(piecejointesFacadeLocal.nextVal());

            String nom = "Piece_j_" + programmation.getIdacteur().getIdservice().getCode() + "_" + event.getFile().getFileName().replaceAll(Utilitaires.getExtension(event.getFile().getFileName()), "") + "_" + piecejointe.getIdpiecejointes() + "_." + Utilitaires.getExtension(event.getFile().getFileName());

            FileOutputStream out;
            out = new FileOutputStream(Utilitaires.path + "" + Utilitaires.repertoire_pieces_j + "" + nom, true);

            byte[] bytes = event.getFile().getContents();
            out.write(bytes);

            filename = nom;

            piecejointe.setIdprogrammation(programmation);
            piecejointe.setTypefichier(event.getFile().getContentType());
            piecejointe.setChemin(nom);
            piecejointesFacadeLocal.create(piecejointe);

            piecejointes.add(piecejointe);
            routine.feedBack("information", "/resources/tool_images/success.png", routine.localizeMessage("operation_reussie"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");

            File f1 = new File(Utilitaires.path + "" + Utilitaires.repertoire_pieces_j + "" + nom);
            File f2 = new File(programmation.getIdetapeprojet().getLienRepertoire() + "" + nom);
            Files.copy(f1, f2);
            piecejointe = new Piecejointes();
        } catch (IOException ex) {
            routine.catchException(ex, routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void signalError(String chaine) {
        this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage(chaine));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalSuccess() {
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalException(Exception e) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
