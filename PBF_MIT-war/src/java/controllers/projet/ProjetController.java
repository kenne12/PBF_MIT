package controllers.projet;

import com.google.common.base.Objects;
import entities.Acteur;
import entities.Etape;
import entities.Etapeprojet;
import entities.Programmation;
import entities.Projet;
import entities.Projetservice;
import entities.Service;
import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import utils.EmailRequest;
import utils.MailThread;
import utils.Receipient;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class ProjetController extends AbstractProjetController implements Serializable {
    
    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(25L)) {
                signalError("acces_refuse");
                return;
            }
            showAddServiceBtn = false;
            showAddEtapeBtn = false;
            
            mode = "Create";
            
            projet = new Projet();
            projet.setEtat(true);
            projet.setCloture(false);
            projet.setNotifMail(true);
            projet.setNotifSms(false);
            selectedEtapes.clear();
            selectedServices.clear();
            
            etapeprojets.clear();
            projetservices.clear();
            
            services = serviceFacadeLocal.findAllRange();
            RequestContext.getCurrentInstance().execute("PF('ProjetCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void prepareView() {
        try {
            if (projet != null) {
                
                if (!Utilitaires.isAccess(28L)) {
                    signalError("acces_refuse");
                    return;
                }
                
                periode = projet.getIdperiode();
                etapeprojets = etapeprojetFacadeLocal.findByIdprojet(projet.getIdprojet());
                projetservices = projetserviceFacadeLocal.findByIdprojet(projet.getIdprojet());
                this.mode = "Edit";
                RequestContext.getCurrentInstance().execute("PF('ProjetDetailDialog').show()");
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
        
    }
    
    public void prepareEdit() {
        try {
            if (projet != null) {
                
                if (!Utilitaires.isAccess(26L)) {
                    signalError("acces_refuse");
                    return;
                }
                showAddServiceBtn = false;
                showAddEtapeBtn = false;
                
                periode = projet.getIdperiode();
                etapeprojets = etapeprojetFacadeLocal.findByIdprojet(projet.getIdprojet());
                projetservices = projetserviceFacadeLocal.findByIdprojet(projet.getIdprojet());
                
                selectedServices.clear();
                selectedServices.addAll(filterService(this.projetservices));
                
                List<Programmation> programmations = programmationFacadeLocal.findByIdprojet(projet.getIdprojet());
                if (!programmations.isEmpty()) {
                    showAddEtapeBtn = true;
                }
                
                this.mode = "Edit";
                RequestContext.getCurrentInstance().execute("PF('ProjetCreerDialog').show()");
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    private List<Service> filterService(List<Projetservice> projetservices) {
        List<Service> services = new ArrayList<>();
        for (Projetservice p : projetservices) {
            services.add(p.getIdservice());
        }
        return services;
    }
    
    public void selectServices() {
        if (addFlag) {
            if (services.size() != selectedServices.size()) {
                services.forEach(s -> {
                    if (!selectedServices.contains(s)) {
                        selectedServices.add(s);
                    }
                });
            }
        } else {
            selectedServices.clear();
            if (mode.equals("Edit")) {
                projetserviceFacadeLocal.findByIdprojet(projet.getIdprojet())
                        .forEach(s -> {
                            selectedServices.add(s.getIdservice());
                        });
            }
        }
    }
    
    public void selectServices2() {
        if (addFlagP) {
            if (projetservices.size() != selectedProjetservices.size()) {
                projetservices.forEach(s -> {
                    if (!selectedProjetservices.contains(s)) {
                        selectedProjetservices.add(s);
                    }
                });
            }
        } else {
            selectedProjetservices.clear();
        }
    }
    
    public void filterService() {
        try {
            services.clear();
            selectedServices.clear();
            if (service.getIdservice() != null) {
                addFlag = true;
                if (mode.equals("Create")) {
                    if (service.getIdservice() == 0) {
                        services = serviceFacadeLocal.findAllRange();
                        selectedServices = services;
                    } else {
                        service = serviceFacadeLocal.find(service.getIdservice());
                        services = serviceFacadeLocal.findByServiceParent(service.getIdservice());
                        services.addAll(serviceFacadeLocal.findAllRange(true));
                        
                        if (service.getCentral()) {
                            if (!services.contains(service)) {
                                services.add(service);
                            }
                        }
                    }
                    selectedServices = services;
                    return;
                }
                
                if (service.getIdservice() == 0) {
                    services.addAll(serviceFacadeLocal.findAllRange());
                    selectedServices = filterService(this.projetservices);
                    services.removeAll(selectedEtapes);
                } else {
                    service = serviceFacadeLocal.find(service.getIdservice());
                    services = serviceFacadeLocal.findByServiceParent(service.getIdservice());
                    services.addAll(serviceFacadeLocal.findAllRange(true));
                    
                    if (service.getCentral()) {
                        if (!services.contains(service)) {
                            services.add(service);
                        }
                    }
                    
                    selectedServices = filterService(this.projetservices);
                    services.removeAll(selectedEtapes);
                }
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void prepareProgrammation() {
        try {
            if (mode.equals("")) {
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                return;
            }
            if (projet != null) {
                
                if (!Utilitaires.isAccess(29L)) {
                    signalError("acces_refuse");
                    return;
                }
                
                projetservices = projetserviceFacadeLocal.findByIdprojet(projet.getIdprojet(), false);
                
                int i = 0;
                for (Projetservice p : projetservices) {
                    
                    List<Acteur> acteurs = new ArrayList<>();
                    try {
                        if (p.getIdservice().getIdparent() != null) {
                            acteurs = acteurFacadeLocal.findByIdservice(p.getIdservice().getIdparent());
                        }
                    } catch (Exception e) {
                    }
                    
                    projetservices.get(i).getIdservice().getActeurList().addAll(acteurFacadeLocal.findAllRange(true));
                    if (!acteurs.isEmpty()) {
                        acteurs.removeAll(projetservices.get(i).getIdservice().getActeurList());
                        projetservices.get(i).getIdservice().getActeurList().addAll(acteurs);
                    }
                    
                    List<Programmation> programmations = new ArrayList<>();
                    
                    List<Programmation> pTemps = programmationFacadeLocal.findByIdprojetservice(p.getIdprojetservice());
                    if (pTemps.isEmpty()) {
                        etapeprojets = etapeprojetFacadeLocal.findByIdprojet(projet.getIdprojet());
                        int count = 0;
                        for (Etapeprojet etps : etapeprojets) {
                            Programmation p1 = new Programmation();
                            p1.setIdprogrammation(0L);
                            p1.setIdetapeprojet(etps);
                            p1.setIdprojetservice(p);
                            p1.setNotifEmailProgram(false);
                            p1.setNotifEmailValidation(false);
                            p1.setIddocument(etps.getIdetape().getIddocument());
                            if (count == 0) {
                                p1.setDateprevisionnel(etps.getDateetatinitial());
                                p1.setDateFinPrevisionnel(etps.getDateetatinitial());
                                if (etps.getDelai() != null && etps.getDelai() > 0) {
                                    p1.setDateFinPrevisionnel(Utilitaires.addDaysToDate(etps.getDateetatinitial(), etps.getDelai()));
                                }
                                p1.setActive(true);
                                programmations.add(p1);
                            } else {
                                p1.setActive(false);
                                p1.setDateprevisionnel(null);
                                programmations.add(p1);
                            }
                            count++;
                        }
                        projetservices.get(i).setProgrammationList(programmations);
                    } else {
                        projetservices.get(i).setProgrammationList(pTemps);
                    }
                    i++;
                }
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('ProgrammationAddDialog').show()");
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void addEtape() {
        try {
            if (selectedEtapes.isEmpty()) {
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                signalError("notification.aucun_element_selectionne");
                return;
            }
            int i = 1;
            for (Etape e : selectedEtapes) {
                if (!findEtape(e)) {
                    Etapeprojet et = new Etapeprojet();
                    et.setIdetapeprojet(0L);
                    String chaine = e.getRepertoire().toLowerCase();
                    chaine = chaine.replaceAll(" ", "_");
                    chaine = chaine.replaceAll("é", "e");
                    chaine = chaine.replaceAll("__", "_");
                    chaine = chaine.replaceAll("/", "_");
                    chaine = chaine.replaceAll("'\'", "_");
                    et.setRepertoire(chaine);
                    et.setIdetape(e);
                    et.setDelai(e.getDelaiDefault());
                    et.setNumero(i);
                    etapeprojets.add(et);
                }
                i += 1;
            }
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            RequestContext.getCurrentInstance().execute("PF('AddetapeDialog').hide()");
        } catch (Exception e) {
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            signalException(e);
        }
    }
    
    public void addServices() {
        try {
            if (selectedServices.isEmpty()) {
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                signalError("notification.aucun_element_selectionne");
                return;
            }
            for (Service s : selectedServices) {
                if (!findService(s)) {
                    Projetservice ps = new Projetservice();
                    ps.setIdprojetservice(0L);
                    ps.setIdservice(s);
                    projetservices.add(ps);
                }
            }
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            RequestContext.getCurrentInstance().execute("PF('AddserviceDialog').hide()");
        } catch (Exception e) {
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            signalException(e);
        }
    }
    
    public boolean findService(Service s) {
        boolean result = false;
        for (Projetservice ps : projetservices) {
            if (ps.getIdservice().equals(s)) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    public boolean findEtape(Etape e) {
        boolean result = false;
        for (Etapeprojet et : etapeprojets) {
            if (et.getIdetape().equals(e)) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    public void create() {
        try {
            if (mode.equals("Create")) {
                
                if (projetservices.isEmpty()) {
                    this.signalError("liste_service_vide");
                    return;
                }
                
                if (etapeprojets.isEmpty()) {
                    this.signalError("liste_etape_vide");
                    return;
                }
                
                if (Objects.equal(etapeprojets.get(0).getDateetatinitial(), null)) {
                    this.signalError("veuillez_definir_etape_initiale");
                    return;
                }
                
                ut.begin();
                
                projet.setIdprojet(projetFacadeLocal.nextVal());
                projet.setIdperiode(periode);
                projet.setEtat(true);
                projet.setDatecreation(Date.from(Instant.now()));
                
                if (!projet.getRepertoire().isEmpty()) {
                    String resultat = projet.getRepertoire().replaceAll(" ", "_");
                    resultat = resultat.replaceAll("-", "_");
                    resultat = resultat.replaceAll("/", "_");
                    resultat = resultat.replaceAll("'\'", "_");
                    resultat = resultat.toLowerCase();
                    projet.setRepertoire(resultat);
                }
                
                File file = new File(SessionMBean.getParametrage().getRepertoire());
                String lienRepertoire = "";
                int linux = 0;
                if (file.exists()) {
                    File fileRepertoireProjet = new File(SessionMBean.getParametrage().getRepertoire() + "" + projet.getRepertoire());
                    if (!fileRepertoireProjet.exists()) {
                        fileRepertoireProjet.mkdir();
                    }
                    lienRepertoire = SessionMBean.getParametrage().getRepertoire() + "" + projet.getRepertoire();
                    
                    if (SessionMBean.getParametrage().getRepertoire().contains("/")) {
                        lienRepertoire += "/";
                        linux = 1;
                    } else {
                        linux = 0;
                        lienRepertoire += '\\';
                    }
                }
                projet.setLienRepertoire(lienRepertoire);
                projetFacadeLocal.create(projet);
                
                for (Projetservice ps : projetservices) {
                    ps.setIdprojetservice(projetserviceFacadeLocal.nextVal());
                    ps.setIdprojet(projet);
                    projetserviceFacadeLocal.create(ps);
                }
                
                for (Etapeprojet etp : etapeprojets) {
                    etp.setIdetapeprojet(etapeprojetFacadeLocal.nextVal());
                    if (etp.getNumero() == 1) {
                        etp.setDelai(0);
                    } else {
                        etp.setDateetatinitial(null);
                    }
                    etp.setIdprojet(projet);
                    String s = lienRepertoire + "" + etp.getRepertoire();
                    if (linux == 1) {
                        s += "/";
                    } else {
                        s += '\\';
                    }
                    File f = new File(s);
                    f.mkdir();
                    s = s.toLowerCase();
                    etp.setLienRepertoire(s);
                    etapeprojetFacadeLocal.create(etp);
                }
                
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du projet : " + projet.getNom(), SessionMBean.getUserAccount());
                ut.commit();
                
                RequestContext.getCurrentInstance().execute("PF('ProjetCreerDialog').hide()");
                this.prepareProgrammation();
                this.detail = this.modifier = this.supprimer = true;
                
            } else if (this.projet != null) {
                
                ut.begin();
                
                projet.setIdperiode(periode);
                projetFacadeLocal.edit(projet);
                
                for (Projetservice ps : projetservices) {
                    if (ps.getIdprojetservice() == 0L) {
                        ps.setIdprojetservice(projetserviceFacadeLocal.nextVal());
                        ps.setIdprojet(projet);
                        projetserviceFacadeLocal.create(ps);
                    }
                }
                
                for (Etapeprojet etp : etapeprojets) {
                    if (etp.getNumero() == 1) {
                        etp.setDelai(0);
                    } else {
                        etp.setDateetatinitial(null);
                    }
                    etapeprojetFacadeLocal.edit(etp);
                }
                
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification du projet : " + projet.getNom(), SessionMBean.getUserAccount());
                ut.commit();
                
                this.projet = new Projet();
                
                detail = modifier = supprimer = true;
                projet = new Projet();
                
                RequestContext.getCurrentInstance().execute("PF('ProjetCreerDialog').hide()");
                signalSuccess();
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void delete() {
        try {
            if (projet != null) {
                
                if (!Utilitaires.isAccess(27L)) {
                    signalError("acces_refuse");
                    return;
                }
                
                ut.begin();
                
                piecejointesFacadeLocal.deleteByIdprojet(projet.getIdprojet());
                programmationFacadeLocal.deleteByIdprojet(projet.getIdprojet());
                etapeprojetFacadeLocal.deleteByIdprojet(projet.getIdprojet());
                projetserviceFacadeLocal.deleteByIdprojet(projet.getIdprojet());
                
                projetFacadeLocal.remove(projet);
                
                ut.commit();
                
                Utilitaires.saveOperation(mouchardFacadeLocal, "Suppresion du projet " + projet.getNom(), SessionMBean.getUserAccount());
                projet = new Projet();
                detail = modifier = supprimer = true;
                signalSuccess();
                
                try {
                    File fileRepertoireProjet = new File(SessionMBean.getParametrage().getRepertoire() + "" + projet.getRepertoire());
                    if (fileRepertoireProjet.exists()) {
                        fileRepertoireProjet.delete();
                    }
                } catch (Exception e) {
                }
                projet = new Projet();
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void removeService(int index, Projetservice p) {
        try {
            if (p.getIdprojetservice() != 0L) {
                ut.begin();
                piecejointesFacadeLocal.deleteByIdprojetservice(p.getIdprojetservice());
                programmationFacadeLocal.deleteByIdprojetIdservice(p.getIdprojetservice());
                projetserviceFacadeLocal.deleteByIdprojetService(p.getIdprojetservice());
                projetserviceFacadeLocal.remove(p);
                ut.commit();
            }
            projetservices.remove(index);
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void removeEtape(int index, Etapeprojet e) {
        try {
            if (e.getIdetapeprojet() != 0L) {
                ut.begin();
                etapeprojetFacadeLocal.remove(e);
                ut.commit();
            }
            etapeprojets.remove(index);
        } catch (Exception ex) {
            signalException(ex);
        }
    }
    
    public void programmer() {
        try {
            List<Acteur> acteurMails = new ArrayList<>();
            for (Projetservice p : projetservices) {
                for (Programmation pr : p.getProgrammationList()) {
                    if (pr.getIdprogrammation() == 0L) {
                        pr.setIdprogrammation(programmationFacadeLocal.nextVal());
                        pr.setActive(false);
                        if (pr.getIdetapeprojet().getDelai() == 0) {
                            pr.setActive(true);
                        }
                        
                        if (pr.getIdetapeprojet().getNumero() == 1) {
                            if (!acteurMails.contains(pr.getIdacteur())) {
                                acteurMails.add(pr.getIdacteur());
                            }
                            if (pr.getIdetapeprojet().getDelai() != 0) {
                                pr.setDateFinPrevisionnel(Utilitaires.addDaysToDate(pr.getIdetapeprojet().getDateetatinitial(), pr.getIdetapeprojet().getDelai()));
                            }
                        }
                        
                        pr.setConteur(0);
                        pr.setRetard(0);
                        pr.setValide(false);
                        pr.setEnvoye(false);
                        pr.setChemin("-");
                        pr.setTypefichier("-");
                        pr.setObservation("-");
                        pr.setObservationarchivee("-");
                        pr.setObservee(false);
                        pr.setObservationvalidee(false);
                        pr.setNotifEmailProgram(true);
                        programmationFacadeLocal.create(pr);
                    } else {
                        programmationFacadeLocal.edit(pr);
                    }
                }
            }
            signalSuccess();
            if (projet.isNotifMail()) {
                this.sendMail(acteurs);
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    private void sendMail(List<Acteur> acteurs) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setSubject("Information : " + projet.getNom());
        emailRequest.setText("Bonjour Mme / M. La CTN Vous informe que vous etes concernés par l'étape initiale du projet mensionnée en object; \n"
                + "Veuillez vous connecter sur le portail du suivi des facture pour fournir les documents exigés;"
                + "Cordiallement.");
        List<Receipient> receipients = new ArrayList<>();
        for (Acteur a : acteurs) {
            receipients.add(new Receipient(a.getIdaddresse().getEmail(), a.getTitre()));
        }
        emailRequest.setReceipients(receipients);
        
        MailThread mailThread = new MailThread(emailRequest);
        mailThread.start();
    }
    
    public void prepareAddService() {
        try {
            services = serviceFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void prepareAddEtape() {
        
    }
    
    public boolean renderReplicationBtn(Projetservice p) {
        int i = projetservices.indexOf(p);
        if (i == 0) {
            return true;
        }
        return false;
    }
    
    public void prepareReplication() {
        projetservice = new Projetservice();
        selectedProjetservices.clear();
        projetservices_1 = projetserviceFacadeLocal.findByIdprojet(projet.getIdprojet(), false);;
        RequestContext.getCurrentInstance().execute("PF('DuplicationCreerDialog').show()");
    }
    
    public void replicateData() {
        try {
            if (projetservice.getIdprojetservice() != null) {
                projetservice = projetserviceFacadeLocal.find(projetservice.getIdprojetservice());
                if (selectedProjetservices.contains(projetservice)) {
                    selectedProjetservices.remove(projetservice);
                }
                int index = projetservices.indexOf(projetservice);
                Projetservice p = projetservices.get(index);
                
                Map map = new HashMap();
                for (int count_t = 0; count_t < p.getProgrammationList().size(); count_t++) {
                    try {
                        if (p.getProgrammationList().get(count_t).getIdacteur().getIdacteur() != null) {
                            map.put(p.getProgrammationList().get(count_t).getIdetapeprojet().getIdetape().getIdetape(), p.getProgrammationList().get(count_t).getIdacteur());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                for (int i = 0; i < projetservices.size(); i++) {
                    if (selectedProjetservices.contains(projetservices.get(i))) {
                        for (int count_t = 0; count_t < projetservices.get(i).getProgrammationList().size(); count_t++) {
                            try {
                                Acteur a = (Acteur) map.get(projetservices.get(i).getProgrammationList().get(count_t).getIdetapeprojet().getIdetape().getIdetape());
                                projetservices.get(i).getProgrammationList().get(count_t).setIdacteur(a);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('DuplicationCreerDialog').hide()");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void replicateData(Projetservice ps) {
        try {
            Projetservice p = ps;
            Map map = new HashMap();
            
            for (int count_t = 0; count_t < p.getProgrammationList().size(); count_t++) {
                try {
                    if (p.getProgrammationList().get(count_t).getIdacteur().getIdacteur() != null) {
                        map.put(p.getProgrammationList().get(count_t).getIdetapeprojet().getIdetape().getIdetape(), p.getProgrammationList().get(count_t).getIdacteur());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            for (int i = 0; i < projetservices.size(); i++) {
                if (i != 0) {
                    for (int count_t = 0; count_t < projetservices.get(i).getProgrammationList().size(); count_t++) {
                        try {
                            Acteur a = (Acteur) map.get(projetservices.get(i).getProgrammationList().get(count_t).getIdetapeprojet().getIdetape().getIdetape());
                            projetservices.get(i).getProgrammationList().get(count_t).setIdacteur(a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void signalError(String chaine) {
        this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage(chaine));
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
    
    public void signalSuccess() {
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
    
    public void signalException(Exception e) {
        this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
