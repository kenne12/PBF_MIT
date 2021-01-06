package controllers.duplication_projet;

import entities.Acteur;
import entities.Etape;
import entities.Etapeprojet;
import entities.Programmation;
import entities.Projet;
import entities.Projetservice;
import entities.Service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class DupliqerProjetController extends AbstractDupliqerProjetController implements Serializable {

    @PostConstruct
    private void init() {

    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(21L)) {
                signalError("acces_refuse");
                return;
            }
            showAddServiceBtn = false;
            showAddEtapeBtn = false;

            mode = "Create";

            projet = new Projet();
            projet.setEtat(true);
            projet.setCloture(false);

            projet_d = new Projet();

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

                if (!Utilitaires.isAccess(22L)) {
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

                if (!Utilitaires.isAccess(22L)) {
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

    public void filterService() {
        try {
            services.clear();
            selectedServices.clear();
            if (service.getIdservice() != null) {

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
                return;
            }
            if (projet != null) {

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
                            p1.setIddocument(etps.getIdetape().getIddocument());
                            if (count == 0) {
                                p1.setDateprevisionnel(etps.getDateetatinitial());
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
            int i = 1;
            for (Etape e : selectedEtapes) {
                if (!findEtape(e)) {
                    Etapeprojet et = new Etapeprojet();
                    et.setIdetapeprojet(0L);
                    et.setIdetape(e);
                    et.setDelai(e.getDelaiDefault());
                    et.setNumero(i);
                    etapeprojets.add(et);
                }
                i += 1;
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
            if (et.getIdetape().equals(et)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void duplicateProject() {
        try {

            projet.setIdprojet(projetFacadeLocal.nextVal());
            projet.setIdperiode(periode);
            projet.setCloture(false);
            projet.setEtat(true);
            projet.setDatecreation(new Date());

            if (!projet.getRepertoire().isEmpty()) {
                if (projet.getRepertoire().contains(" ")) {
                    String resultat = projet.getRepertoire().replaceAll(" ", "_");
                    resultat = resultat.replaceAll("-", "_");
                    resultat = resultat.toLowerCase();
                    projet.setRepertoire(resultat);
                }
            }

            projetFacadeLocal.create(projet);

            List<Etapeprojet> etapeprojets = etapeprojetFacadeLocal.findByIdprojet(projet_d.getIdprojet());
            List<Projetservice> projetservices = projetserviceFacadeLocal.findByIdprojet(projet_d.getIdprojet());

            List<Programmation> programmations = programmationFacadeLocal.findByIdprojet(projet_d.getIdprojet());
            List<Etapeprojet> etapeprojets_1 = new ArrayList<>();

            int i = 0;
            for (Etapeprojet etp : etapeprojets) {

                Etapeprojet obj = new Etapeprojet();
                obj.setIdetapeprojet(etapeprojetFacadeLocal.nextVal());
                if (i == 0) {
                    obj.setDateetatinitial(dateDebut);
                    obj.setDelai(0);
                } else {
                    obj.setDelai(obj.getDelai());
                    obj.setDateetatinitial(null);
                }

                obj.setIdetape(etp.getIdetape());
                obj.setIdprojet(projet);
                obj.setNumero(etp.getNumero());
                etapeprojetFacadeLocal.create(obj);
                etapeprojets_1.add(obj);
                i++;
            }

            for (Projetservice ps : projetservices) {

                Projetservice proj_s = new Projetservice();
                proj_s.setIdprojetservice(projetserviceFacadeLocal.nextVal());
                proj_s.setIdprojet(projet);
                proj_s.setIdservice(ps.getIdservice());
                projetserviceFacadeLocal.create(proj_s);

                List<Programmation> pr_temps = filterProgrammation(programmations, ps.getIdservice());
                for (Programmation pr : pr_temps) {
                    Programmation obj_1 = new Programmation();
                    obj_1.setIdprogrammation(programmationFacadeLocal.nextVal());
                    obj_1.setChemin("-");
                    obj_1.setConteur(0);

                    if (pr.getIdetapeprojet().getNumero() == 1) {
                        obj_1.setDateprevisionnel(pr.getDateprevisionnel());
                        obj_1.setActive(true);
                    } else {
                        obj_1.setActive(false);
                        obj_1.setDateprevisionnel(null);
                    }
                    obj_1.setDateValidation(null);
                    obj_1.setDaterealisation(null);
                    obj_1.setIddocument(pr.getIddocument());
                    try {
                        obj_1.setIdacteur(pr.getIdacteur());
                    } catch (Exception e) {
                    }

                    obj_1.setIdetapeprojet(filterEtape(etapeprojets_1, pr.getIdetapeprojet().getIdetape()));
                    obj_1.setIdprojetservice(proj_s);
                    obj_1.setValide(false);
                    obj_1.setEnvoye(false);
                    obj_1.setObservee(false);
                    obj_1.setObservationvalidee(false);
                    obj_1.setTypefichier("-");
                    obj_1.setObservation("-");
                    obj_1.setObservationutilisateur("-");
                    obj_1.setObservationarchivee("-");
                    obj_1.setRetard(0);
                    programmationFacadeLocal.create(obj_1);
                }
            }
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            signalSuccess();
        } catch (Exception e) {
            signalException(e);
        }
    }

    private List<Programmation> filterProgrammation(List<Programmation> programmations, Service s) {
        List<Programmation> results = new ArrayList<>();
        for (Programmation p : programmations) {
            if (p.getIdprojetservice().getIdservice().equals(s)) {
                results.add(p);
            }
        }
        return results;
    }

    private Etapeprojet filterEtape(List<Etapeprojet> etapeprojets, Etape e) {
        for (Etapeprojet temp : etapeprojets) {
            if (temp.getIdetape().equals(e)) {
                return temp;
            }
        }
        return null;
    }

    public void create() {
        try {
            if (mode.equals("Create")) {

                ut.begin();

                projet.setIdprojet(projetFacadeLocal.nextVal());
                projet.setIdperiode(periode);
                projet.setEtat(true);
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

                this.projet = null;

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

                if (!Utilitaires.isAccess(23L)) {
                    signalError("acces_refuse");
                    return;
                }

                ut.begin();

                if (programmationFacadeLocal.findByIdprojet(projet.getIdprojet()).isEmpty()) {
                    etapeprojetFacadeLocal.deleteByIdprojet(projet.getIdprojet());
                    projetserviceFacadeLocal.deleteByIdprojet(projet.getIdprojet());
                }

                projetFacadeLocal.remove(projet);

                ut.commit();
                Utilitaires.saveOperation(mouchardFacadeLocal, "Suppresion du projet " + projet.getNom(), SessionMBean.getUserAccount());
                projet = new Projet();
                detail = modifier = supprimer = true;
                signalSuccess();
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
            for (Projetservice p : projetservices) {
                for (Programmation pr : p.getProgrammationList()) {
                    if (pr.getIdprogrammation() == 0L) {
                        pr.setIdprogrammation(programmationFacadeLocal.nextVal());
                        pr.setActive(false);
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
                        programmationFacadeLocal.create(pr);
                    } else {
                        programmationFacadeLocal.edit(pr);
                    }
                }
            }
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");

            signalSuccess();
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void prepareAddService() {
        service = new Service();
        try {
            services = serviceFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean renderReplicationBtn(Projetservice p) {
        int i = projetservices.indexOf(p);
        if (i == 0) {
            return true;
        }
        return false;
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
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            signalException(e);
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
        this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
