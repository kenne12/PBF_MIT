package controllers.suivi;

import com.google.common.io.Files;
import entities.Acteur;
import entities.Etape;
import entities.Etapeprojet;
import entities.Notification;
import entities.NotificationActeur;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.transaction.Transactional;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import utils.AllmySms;
import utils.EmailRequest;
import utils.MailThread;
import utils.ObjectContactActeur;
import utils.ObmSms;
import utils.ObmSmsSendRequest;
import utils.OrangeSmsSender;
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
        List<Projet> listProjets = new ArrayList<>();
        if (!list.isEmpty()) {
            list.stream().forEach((ps) -> {
                listProjets.add(ps.getIdprojet());
            });
        }
        return listProjets;
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
            selectedEtapes.stream().filter((e) -> (!findEtape(e))).map((e) -> {
                Etapeprojet et = new Etapeprojet();
                et.setIdetapeprojet(0L);
                et.setIdetape(e);
                return et;
            }).forEach((et) -> {
                etapeprojets.add(et);
            });
            RequestContext.getCurrentInstance().execute("PF('AddetapeDialog').hide()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void addServices() {
        try {
            selectedServices.stream().filter((s) -> (!findService(s))).map((s) -> {
                Projetservice ps = new Projetservice();
                ps.setIdprojetservice(0L);
                ps.setIdservice(s);
                return ps;
            }).forEach((ps) -> {
                projetservices.add(ps);
            });
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
            if (et.getIdetape().equals(e)) {
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
            return !acteur.equals(SessionMBean.getUserAccount().getIdacteur());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean renderedView(boolean active, boolean envoye) {
        return !envoye;
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

    public boolean renderedViewBtn(boolean valide, boolean observee, boolean observation_validee, int count) {
        if (count > 0 && !valide) {
            return true;
        }

        return valide && observee && !observation_validee;
    }

    public boolean renderedActivate(Programmation programmation, boolean envoye, boolean active) {
        if (active) {
            if (Utilitaires.isAccess(1L)) {
                if (envoye) {
                    return false;
                }
                return programmation.getIdetapeprojet().getNumero() != 1;
            }
            return true;
        }
        return true;
    }

    public boolean renderedViewObservation(boolean envoye, boolean valide, Programmation p) {
        try {
            if ((!valide && p.getConteur() > 0) == true) {
                if (Utilitaires.isAccess(1L)) {
                    return false;
                }

                if (SessionMBean.getUserAccount().getIdacteur().getIdservice().getRegional()) {
                    return false;
                }

                return !p.getIdacteur().equals(SessionMBean.getUserAccount().getIdacteur());
            }
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    public String checkFooterColor(boolean valide, boolean observee, boolean observation_validee, int conteur) {

        if ((valide && !observee && !observation_validee) == true) {
            return "green";
        }
        if ((conteur > 0 && !observee && !observation_validee && !valide) == true) {
            return "yellow";
        }

        if (valide == true && observee == true && observation_validee == false) {
            return "yellow";
        }

        if (conteur == 0 && valide == false) {
            return "red";
        }

        if ((!valide && !observee && !observation_validee && !valide) == true) {
            return "red";
        }

        if (valide == true && observee == true && observation_validee == true && valide == true) {
            return "green";
        }
        return "";
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

            servicesActeursAcv.clear();
            servicesActeursDs.clear();

            notification = new Notification();
            notification.setMail(p.isNotifEmailProgram());
            notification.setSms(p.isNotifSmsProgram());
            notification.setMessage("-");
            notification.setObjet("-");
            templateMessage = "0";

            acteurNotifiablesDuplicate.clear();
            acteurNotifiables.clear();
            if (listActeurCtn != null) {
                acteurNotifiables.addAll(listActeurCtn);
            }

            servicesActeursDs.add(p.getIdprojetservice().getIdservice());

            List<Acteur> listActeurAcv = new ArrayList<>();
            Service acv = serviceFacadeLocal.findByServiceParentAndRegion(p.getIdprojetservice().getIdservice().getIdparent(), true);
            if (acv != null) {
                servicesActeursAcv.add(acv);
                listActeurAcv.addAll(acteurFacadeLocal.findByIdservice(acv.getIdservice()));
                if (listActeurAcv != null) {
                    acteurNotifiables.addAll(listActeurAcv);
                }
            }

            List<Programmation> listProgrammations = programmationFacadeLocal.findByIdprojetIdservice(p.getIdprojetservice().getIdprojetservice());
            if (listProgrammations != null) {
                listProgrammations.forEach(obj -> {
                    if (!acteurNotifiables.contains(obj.getIdacteur())) {
                        acteurNotifiables.add(obj.getIdacteur());
                    }
                });
            }

            List<Acteur> listActeursDistrict = acteurFacadeLocal.findByIdservice(p.getIdprojetservice().getIdservice().getIdservice());
            if (!listActeursDistrict.isEmpty()) {
                acteurNotifiables.addAll(listActeursDistrict);
            }

            if (!acteurNotifiables.isEmpty()) {
                acteurNotifiablesDuplicate.addAll(acteurNotifiables);
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
        modeSearchActeur = 0;
        selectIdservice = 0;
        modeAcv = false;
        modeRegion = false;
        modeDistrict = false;
        RequestContext.getCurrentInstance().execute("PF('AddActeurDialog').show()");
    }

    public void addActeur() {
        RequestContext.getCurrentInstance().execute("PF('AddActeurDialog').hide()");
    }

    public void updateTemplate() {
        if (templateMessage.equals("0")) {
            notification.setMessage("RAS");
        } else if (templateMessage.equals("1")) {
            notification.setObjet("Validation");

            String smsMessage = "Bonjour, <<" + programmation.getIdetapeprojet().getIdprojet().getNom() + ">> : <<" + programmation.getIdetapeprojet().getIdetape().getNom() + ">>"
                    + " : <<Validé>> : <<" + sdf.format(programmation.getDaterealisation()) + ">> : <<" + programmation.getIdprojetservice().getIdprojet().getIdperiode().getNom() + ">>"
                    + " : <<" + programmation.getIdprojetservice().getIdservice().getNom() + ">>";

            /*String smsMessage = "Bonjour, La CTN Vous Informe que les documents transmis dans le cadre du projet : "
             + programmation.getIdetapeprojet().getIdprojet().getNom()
             + " Ont été acceptés.";*/
            notification.setMessage(smsMessage);

            String mailMessage = "Bonjour,\nLa CTN Vous Informe que les documents transmis dans le cadre du projet : "
                    + programmation.getIdetapeprojet().getIdprojet().getNom()
                    + " Ont été acceptés. \nEtape" + programmation.getIdetapeprojet().getIdetape().getNom() + ", Date de réalisation : " + sdf.format(programmation.getDaterealisation()) + "\nCe Message a été envoyé automatiquement, nous vous remercions de ne pas répondre.";
            notification.setMessageMail(mailMessage);
        } else if (templateMessage.equals("2")) {
            notification.setObjet("Rejet");

            String smsMessage = "Bonjour, <<" + programmation.getIdetapeprojet().getIdprojet().getNom() + ">> : <<" + programmation.getIdetapeprojet().getIdetape().getNom() + ">>"
                    + " : <<Rejet>> : Raison : <<" + programmation.getObservation() + ">> : <<" + sdf.format(programmation.getDaterealisation()) + ">> : <<" + programmation.getIdprojetservice().getIdprojet().getIdperiode().getNom() + ">>"
                    + " : <<" + programmation.getIdprojetservice().getIdservice().getNom() + ">>";

            /*String smsMessage = "Bonjour, La CTN vous informe que les documents transmis dans le cadre du projet : " + programmation.getIdetapeprojet().getIdprojet().getNom() + " "
             + "Structure : " + programmation.getIdprojetservice().getIdservice().getNom() + ", "
             + "Etape : " + programmation.getIdetapeprojet().getIdetape().getNom() + ", "
             + "Pour des raisons suivantes : "
             + programmation.getObservation() + ".";*/
            notification.setMessage(smsMessage);

            String mailMessage = "Bonjour,\nLa CTN vous informe que les documents transmis dans le cadre du projet : " + programmation.getIdetapeprojet().getIdprojet().getNom()
                    + "\nStructure : " + programmation.getIdprojetservice().getIdservice().getNom() + ", "
                    + "\nEtape : " + programmation.getIdetapeprojet().getIdetape().getNom() + ", "
                    + "ont été rejetés pour des raisons suivantes : " + programmation.getObservation() + ". "
                    + "\nCe Message a été envoyé automatiquement, nous vous remercions de ne pas répondre.";
            notification.setMessageMail(mailMessage);
        } else if (templateMessage.equals("3")) {
            notification.setObjet("Observation");

            String smsMessage = "Bonjour, <<" + programmation.getIdetapeprojet().getIdprojet().getNom() + ">> : <<" + programmation.getIdetapeprojet().getIdetape().getNom() + ">>"
                    + " : <<Acceptation avec retenu>> : Raison : <<" + programmation.getObservation() + ">> : <<" + sdf.format(programmation.getDaterealisation()) + ">> : <<" + programmation.getIdprojetservice().getIdprojet().getIdperiode().getNom() + ">>"
                    + " : <<" + programmation.getIdprojetservice().getIdservice().getNom() + ">>";
            /*String smsMessage = "Bonjour, \nLa CTN Vous informe que des rémarques ont été faites sur les documents envoyés dans le cadre du projet -> " + programmation.getIdetapeprojet().getIdprojet().getNom()
             + "Structure : " + programmation.getIdprojetservice().getIdservice().getNom() + ", "
             + "Etape : " + programmation.getIdetapeprojet().getIdetape().getNom() + ", "
             + "" + programmation.getObservation() + " "
             + "Veuillez-Vous connecter sur le portail pour apporter des corrections sollicitées en pièces jointes.";*/
            notification.setMessage(smsMessage);

            String mailMessage = "Bonjour, La CTN Vous informe que des remarques ont été faites sur les documents envoyés dans le cadre du projet : " + programmation.getIdetapeprojet().getIdprojet().getNom()
                    + "\nStructure : " + programmation.getIdprojetservice().getIdservice().getNom() + ", "
                    + "\nEtape : " + programmation.getIdetapeprojet().getIdetape().getNom() + ", "
                    + "" + programmation.getObservation() + " "
                    + "Veuillez-Vous connecter sur le portail pour apporter des corrections sollicitées en pièces jointes.\nCe Message a été envoyé automatiquement, nous vous remercions de ne pas répondre.";

            notification.setMessageMail(mailMessage);
        }
    }

    public void updateSearchFilter() {
        modeAcv = false;
        modeRegion = false;
        modeDistrict = false;

        if (modeSearchActeur == 0) {
            acteurNotifiables.clear();
            acteurNotifiables.addAll(acteurNotifiablesDuplicate);
        }

        if (modeSearchActeur == 1) {
            modeRegion = true;
        }

        if (modeSearchActeur == 2) {
            modeAcv = true;
        }

        if (modeSearchActeur == 3) {
            modeDistrict = true;
        }

        if (modeSearchActeur == 4) {
            acteurNotifiables.clear();
            acteurNotifiablesDuplicate.forEach(a -> {
                if (a.getIdservice().getCentral()) {
                    acteurNotifiables.add(a);
                }
            });
        }
    }

    public void filterActeurRegion() {

    }

    public void filterActeurAcv() {
        acteurNotifiables.clear();
        if (selectIdservice != 0) {
            acteurNotifiablesDuplicate.forEach(a -> {
                if (a.getIdservice().getIdservice().equals(selectIdservice)) {
                    acteurNotifiables.add(a);
                }
            });
        }
    }

    public void filterActeurDistrict() {
        acteurNotifiables.clear();
        if (selectIdservice != 0) {
            acteurNotifiablesDuplicate.forEach(a -> {
                if (a.getIdservice().getIdservice().equals(selectIdservice)) {
                    acteurNotifiables.add(a);
                }
            });
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
            try {
                programmation.setConteur(programmation.getConteur() + 1);
            } catch (Exception e) {
                programmation.setConteur(1);
            }

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
            boolean flagSms = notification.isSms();

            this.messageSms = notification.getMessage();

            if (notification.isMail() && !receipients.isEmpty()) {
                EmailRequest emailRequest = new EmailRequest();
                emailRequest.setSender("CTNPBF");
                emailRequest.setSubject(notification.getObjet());
                emailRequest.setText(notification.getMessageMail());
                emailRequest.setReceipients(receipients);
                sendMail(emailRequest);
            }

            List<ReceipientSms> receipientSmses = Utilitaires.extracPhoneNumber(selectedActeurNotifiables);
            notification.setSms(flagSms);
            if (!receipientSmses.isEmpty() && notification.isSms()) {
                SmsRequest smsRequest = new SmsRequest();
                smsRequest.setText(notification.getMessage());
                smsRequest.setReceipients(receipientSmses);
                sendSms(smsRequest);
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    private void sendMail(EmailRequest emailRequest) {
        MailThread mailThread = new MailThread(emailRequest);
        mailThread.start();

        if (!selectedActeurNotifiables.isEmpty()) {

            Notification n = new Notification();
            n.setDateEnvoi(Date.from(Instant.now()));
            n.setMessageMail(notification.getMessageMail());
            n.setObjet(notification.getObjet());
            n.setMail(true);
            n.setSms(false);
            n.setMessage("-");
            //n.getActeurs().addAll((Collection<Acteur>) selectedActeurNotifiables);
            n.setIdnotification(notificationFacadeLocal.nextVal());
            n.setIdperiode(programmation.getIdetapeprojet().getIdprojet().getIdperiode());
            notificationFacadeLocal.create(n);

            selectedActeurNotifiables.stream().map(a -> {
                NotificationActeur nTemp = new NotificationActeur(n.getIdnotification(), a.getIdacteur());
                return nTemp;
            }).forEach(object -> {
                notificationActeurFacadeLocal.create(object);
            });
        }
    }

    private void sendSms(SmsRequest smsRequest) {

        Integer api = SessionMBean.getParametrage().getIdSmsApi();

        SmsThread smsThread = new SmsThread(smsRequest);

        int nbpage = Utilitaires.countSms(smsRequest.getText()).get("nombre_pages");
        List<Acteur> listSuccess = new ArrayList<>();

        if (api.equals(1)) {
            smsRequest.getReceipients().forEach(s -> {
                SmsRequest sr = smsRequest;
                sr.setReceipientSms(s);

                String report = smsThread.runSingle(sr);

                Map m = AllmySms.treatResponse(report, api);
                if (((boolean) m.get("etat"))) {
                    this.updateSoldeOrgUnit(sr.getReceipientSms().getActeur().getIdservice(), nbpage);
                    listSuccess.add(sr.getReceipientSms().getActeur());
                }
            });
        }

        List<ObjectContactActeur> list = new ArrayList<>();

        if (api.equals(3) || api.equals(2)) {
            Map<String, List<ObjectContactActeur>> result = Utilitaires.filterContact(smsRequest.getReceipients());
            List<ObjectContactActeur> obms = result.get("obm_mtn_nexttel");
            if (!obms.isEmpty()) {
                String access_token = (String) Utilitaires.getDetailObmApi(3).get("access_token");
                for (ObjectContactActeur c : obms) {
                    if (!list.contains(c)) {
                        list.add(c);

                        ObmSmsSendRequest request = new ObmSmsSendRequest("CTN", notification.getMessage(), "237" + c.getContact());
                        String response = ObmSms.sendSms(access_token, request);

                        Map m = AllmySms.treatResponse(response, api);
                        if (((boolean) m.get("etat"))) {
                            this.updateSoldeOrgUnit(c.getActeur().getIdservice(), nbpage);
                            listSuccess.add(c.getActeur());
                        }
                    }
                }
            }

            List<ObjectContactActeur> oranges = result.get("orange_cm");
            if (!oranges.isEmpty()) {
                String access_token = (String) Utilitaires.getDetailOrangeApi(2).get("access_token");
                oranges.stream().filter((c) -> (!list.contains(c))).map((c) -> {
                    list.add(c);
                    return c;
                }).forEach((c) -> {
                    String response = OrangeSmsSender.send2(access_token, c.getContact(), notification.getMessage());
                    Map m = AllmySms.treatResponse(response, api);
                    if ((boolean) m.get("etat")) {
                        this.updateSoldeOrgUnit(c.getActeur().getIdservice(), nbpage);
                        listSuccess.add(c.getActeur());
                    }
                });
            }
        }

        if (!listSuccess.isEmpty()) {
            Notification n = new Notification();
            n.setMail(false);
            n.setSms(true);
            n.setDateEnvoi(Date.from(Instant.now()));
            n.setMessage(smsRequest.getText());
            n.setObjet(notification.getObjet());
            n.setMessageMail("-");
            //n.getActeurs().addAll((Collection<Acteur>) listSuccess);
            n.setIdnotification(notificationFacadeLocal.nextVal());
            n.setIdperiode(programmation.getIdetapeprojet().getIdprojet().getIdperiode());
            notificationFacadeLocal.create(n);

            listSuccess.stream().map(a -> {
                NotificationActeur nTemp = new NotificationActeur(n.getIdnotification(), a.getIdacteur());
                return nTemp;
            }).forEach(object -> {
                notificationActeurFacadeLocal.create(object);
            });
        }
    }

    private void updateSoldeOrgUnit(Service service, int nbPage) {
        if (service.getCentral()) {
            service = serviceFacadeLocal.find(service.getIdservice());
        } else {
            service = serviceFacadeLocal.find(service.getIdparent());
        }
        if (service != null) {
            service.setSoldeSms(service.getSoldeSms() - nbPage);
            serviceFacadeLocal.edit(service);
        }
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
            //programmation.setConteur(programmation.getConteur() + 1);
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
        for (String regex1 : regex) {
            switch (regex1) {
                case "é":
                    chaine = chaine.replaceAll(regex1, "e");
                    break;
                case "à":
                    chaine = chaine.replaceAll(regex1, "a");
                    break;
                default:
                    chaine = chaine.replaceAll(regex1, "_");
                    break;
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
            //programmation.setConteur(programmation.getConteur() + 1);
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
