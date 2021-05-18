/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.analyse.retart_projet;

import entities.Etapeprojet;
import entities.Programmation;
import entities.Projetservice;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author USER
 */
@ManagedBean
@SessionScoped
public class AnalseRetardProjetController extends AbstractAnalyseRetardProjetController implements Serializable {

    /**
     * Creates a new instance of AnalseRetardProjetController
     */
    public AnalseRetardProjetController() {
        projet.setProjetserviceList(new ArrayList<>());
    }

    @PostConstruct
    private void init() {
        projets = projetFacadeLocal.findAllRange(true, false);
    }

    public void searchData() {
        if (projet.getIdprojet() == null) {
            projet.getProjetserviceList().clear();
            projet.getEtapeprojetList().clear();
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            return;
        }
        projet = projetFacadeLocal.find(projet.getIdprojet());
        projet.setProjetserviceList(projetserviceFacadeLocal.findByIdprojetRegional(projet.getIdprojet(), false, false));
        projet.setEtapeprojetList(etapeprojetFacadeLocal.findByIdprojet(projet.getIdprojet()));
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
    }

    public String loadValue(long idProjetService, Etapeprojet etapeprojet) {
        Programmation p = null;
        try {
            p = programmationFacadeLocal.findByIdprojetIdservice(idProjetService, etapeprojet.getNumero());
            if (p != null) {
                return "" + p.getRetard();
            }
        } catch (Exception e) {

        }
        return "";
    }

    public double agregate(Projetservice projetservice) {
        return programmationFacadeLocal.getRetardByIdService(projetservice.getIdprojetservice());
    }

    public double agregateRetardByEtape(Etapeprojet etapeprojet) {
        return programmationFacadeLocal.getRetardByIdEtapeProjet(etapeprojet.getIdetapeprojet());
    }

    public double agregateRetardByProjet() {
        return projet.getIdprojet() == null ? 0 : programmationFacadeLocal.getRetardByIdprojet(projet.getIdprojet());
    }

}
