package controllers.programmation;

import entities.Acteur;
import entities.Etape;
import entities.Etapeprojet;
import entities.Programmation;
import entities.Projet;
import entities.Projetservice;
import entities.Service;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class ProgrammationController extends AbstractProgrammationController implements Serializable {

    @PostConstruct
    private void init() {

    }

    public void prepareCreate(Projetservice obj) {
        try {
            if (!Utilitaires.isAccess(29L)) {
                signalError("acces_refuse");
                return;
            }
            mode = "Create";

            programmations.clear();
            acteurs.clear();

            projet = obj.getIdprojet();
            projetservice = obj;
            if (projet.getIdprojet() != null) {
                if (projetservice.getIdprojetservice() != null) {

                    projetservices = projetserviceFacadeLocal.findByIdprojet(projet.getIdprojet(), false);

                    for (Projetservice ps : projetserviceFacadeLocal.findByIdprojet(projet.getIdprojet())) {
                        acteurs.addAll(acteurFacadeLocal.findByIdservice(ps.getIdservice().getIdservice()));
                    }

                    List<Acteur> list = new ArrayList<>();
                    try {
                        if (projetservice.getIdservice().getIdparent() != null) {
                            list = acteurFacadeLocal.findByIdservice(projetservice.getIdservice().getIdparent());
                        }
                    } catch (Exception e) {
                    }

                    if (!list.isEmpty()) {
                        list.removeAll(acteurs);
                        acteurs.addAll(list);
                    }

                    List<Programmation> pTemps = programmationFacadeLocal.findByIdprojetservice(obj.getIdprojetservice());
                    if (pTemps.isEmpty()) {
                        etapeprojets = etapeprojetFacadeLocal.findByIdprojet(projet.getIdprojet());
                        int i = 0;
                        for (Etapeprojet etps : etapeprojets) {
                            Programmation p = new Programmation();
                            p.setIdprogrammation(0L);
                            p.setIdetapeprojet(etps);
                            p.setIddocument(etps.getIdetape().getIddocument());
                            if (i == 0) {
                                p.setDateprevisionnel(etps.getDateetatinitial());
                                p.setActive(true);
                                programmations.add(p);
                            } else {
                                p.setActive(false);
                                p.setDateprevisionnel(null);
                                programmations.add(p);
                            }
                            i++;
                        }
                    } else {
                        programmations = pTemps;
                    }
                }
            }
            RequestContext.getCurrentInstance().execute("PF('ProjetCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void updateServiceData() {
        try {
            if (projet.getIdprojet() != null) {
                projetservices = projetserviceFacadeLocal.findByIdprojet(projet.getIdprojet(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterData() {
        try {
            if (projet.getIdprojet() != null) {
                if (SessionMBean.getUserAccount().getIdacteur().getIdservice().getCentral()) {
                    projetservices = projetserviceFacadeLocal.findByIdprojet(projet.getIdprojet(), false);
                } else if (SessionMBean.getUserAccount().getIdacteur().getIdservice().getRegional()) {
                    projetservices = projetserviceFacadeLocal.findByIdserviceparent(SessionMBean.getUserAccount().getIdacteur().getIdservice().getIdservice(), projet.getIdprojet());
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

    public void updateData() {
        try {
            programmations.clear();
            acteurs.clear();
            if (projet.getIdprojet() != null) {
                if (projetservice.getIdprojetservice() != null) {

                    projetservice = projetserviceFacadeLocal.find(projetservice.getIdprojetservice());

                    for (Projetservice ps : projetserviceFacadeLocal.findByIdprojet(projet.getIdprojet())) {
                        acteurs.addAll(acteurFacadeLocal.findByIdservice(ps.getIdservice().getIdservice()));
                    }

                    List<Programmation> pTemps = programmationFacadeLocal.findByIdprojetservice(projetservice.getIdprojetservice());
                    if (pTemps.isEmpty()) {
                        etapeprojets = etapeprojetFacadeLocal.findByIdprojet(projet.getIdprojet());
                        int i = 0;
                        int delai = 0;
                        for (Etapeprojet etps : etapeprojets) {
                            Programmation p = new Programmation();
                            p.setIdprogrammation(0L);
                            p.setIdetapeprojet(etps);
                            if (i == 0) {
                                p.setDateprevisionnel(etps.getDateetatinitial());
                                p.setActive(true);
                                programmations.add(p);
                            } else {
                                delai += etps.getDelai();

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                DateTime date_prev = new DateTime(etapeprojets.get(0).getDateetatinitial());
                                date_prev.plusDays(delai);
                                p.setDateprevisionnel(date_prev.toDate());
                                p.setActive(false);
                                programmations.add(p);
                            }
                            i++;
                        }
                        return;
                    }
                    programmations = pTemps;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public List<Programmation> findProgrammation(Projetservice p) {
        try {
            return programmationFacadeLocal.findByIdprojetservice(p.getIdprojetservice());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void create() {
        try {
            if (mode.equals("Create")) {

                ut.begin();

                projetservice = projetserviceFacadeLocal.find(projetservice.getIdprojetservice());

                for (Programmation p : programmations) {
                    if (p.getIdprogrammation() == 0L) {
                        p.setIdprogrammation(programmationFacadeLocal.nextVal());
                        p.setValide(false);
                        p.setEnvoye(false);
                        p.setChemin("-");
                        p.setTypefichier("-");
                        p.setConteur(0);
                        p.setRetard(0);
                        p.setObservation("-");
                        p.setObservationarchivee("-");
                        p.setObservee(false);
                        p.setObservationvalidee(false);
                        programmationFacadeLocal.create(p);
                    } else {
                        programmationFacadeLocal.edit(p);
                    }
                }

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Programmation des differentes étapes de la structure : " + projetservice.getIdservice().getNom() + " Projet : " + projetservice.getIdprojet().getNom(), SessionMBean.getUserAccount());
                ut.commit();

                this.detail = this.modifier = this.supprimer = true;
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('ProjetCreerDialog').hide()");
                signalSuccess();
            } else if (this.projet != null) {

                detail = modifier = supprimer = true;
                projet = new Projet();
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('ProjetCreerDialog').hide()");
                signalSuccess();
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            signalException(e);
        }
    }

    public void delete(Projetservice projetservice) {
        try {
            if (!Utilitaires.isAccess(23L)) {
                signalError("acces_refuse");
                return;
            }

            ut.begin();
            programmationFacadeLocal.deleteByIdprojetIdservice(projetservice.getIdprojetservice());
            piecejointesFacadeLocal.deleteByIdprojetservice(projetservice.getIdprojetservice());
            ut.commit();

            Utilitaires.saveOperation(mouchardFacadeLocal, "Annulation de la programmation ; Projet : " + projetservice.getIdprojet().getNom() + " Unité d'organisation : " + projetservice.getIdservice().getNom(), SessionMBean.getUserAccount());

            detail = modifier = supprimer = true;
            signalSuccess();
        } catch (Exception e) {
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
