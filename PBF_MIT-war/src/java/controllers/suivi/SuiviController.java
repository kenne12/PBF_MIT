package controllers.suivi;

import com.google.common.io.Files;
import entities.Acteur;
import entities.Etape;
import entities.Etapeprojet;
import entities.Piecejointes;
import entities.Programmation;
import entities.Projet;
import entities.Projetservice;
import entities.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import utils.SessionMBean;
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
                    projetservices = projetserviceFacadeLocal.findByIdserviceparentVs(SessionMBean.getUserAccount().getIdacteur().getIdservice().getIdservice(), projet.getIdprojet(), true, false);
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
            programmation.setConteur(programmation.getConteur() + 1);
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
                programmationFacadeLocal.edit(p1);
            }

            programmationFacadeLocal.edit(programmation);
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            RequestContext.getCurrentInstance().execute("PF('ValidationCreerDialog').hide()");
            signalSuccess();
        } catch (Exception e) {
            signalException(e);
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

            String nom = "Document_" + programmation.getIdacteur().getIdservice().getCode() + "_" + event.getFile().getFileName().replaceAll(Utilitaires.getExtension(event.getFile().getFileName()), "") + "_" + programmation.getIdprogrammation() + "_." + Utilitaires.getExtension(event.getFile().getFileName());

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
