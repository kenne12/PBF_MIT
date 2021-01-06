package controllers.acteur;

import entities.Addresse;
import entities.Acteur;
import entities.Addresse_;
import entities.Document;
import entities.Service;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class ActeurController extends AbstractActeurController implements Serializable {
    
    @PostConstruct
    private void init() {
        
    }
    
    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(22L)) {
                signalError("acces_refuse");
                return;
            }
            
            mode = "Create";
            acteur = new Acteur();
            addresse = new Addresse();
            addresse.setBp("-");
            addresse.setEmail("-");
            addresse.setAdresse("-");
            service = new Service();
            document = new Document();
            
            RequestContext.getCurrentInstance().execute("PF('ActeurCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void prepareEdit() {
        try {
            if (acteur != null) {
                
                if (!Utilitaires.isAccess(23L)) {
                    signalError("acces_refuse");
                    return;
                }
                
                addresse = acteur.getIdaddresse();
                service = acteur.getIdservice();
                this.mode = "Edit";
                RequestContext.getCurrentInstance().execute("PF('ActeurCreerDialog').show()");
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void create() {
        try {
            if (mode.equals("Create")) {
                
                ut.begin();
                acteur.setIdacteur(acteurFacadeLocal.nextVal());
                addresse.setIdaddresse(addresseFacadeLocal.nextVal());
                addresseFacadeLocal.create(addresse);
                
                acteur.setIdservice(service);
                acteur.setIdaddresse(addresse);
                acteurFacadeLocal.create(acteur);
                
                ut.commit();
                
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'acteur : " + acteur.getNom(), SessionMBean.getUserAccount());
                this.acteur = new Acteur();
                
                this.detail = this.modifier = this.supprimer = true;
                
                RequestContext.getCurrentInstance().execute("PF('ActeurCreerDialog').hide()");
                signalSuccess();
            } else if (this.acteur != null) {
                addresseFacadeLocal.edit(addresse);
                
                acteur.setIdservice(serviceFacadeLocal.find(service.getIdservice()));
                acteurFacadeLocal.edit(acteur);
                detail = modifier = supprimer = true;
                acteur = new Acteur();
                
                RequestContext.getCurrentInstance().execute("PF('ActeurCreerDialog').hide()");
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
            if (acteur != null) {
                
                if (!Utilitaires.isAccess(24L)) {
                    signalError("acces_refuse");
                    return;
                }
                
                ut.begin();
                
                this.acteurFacadeLocal.remove(this.acteur);
                addresseFacadeLocal.remove(acteur.getIdaddresse());
                
                ut.commit();
                
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de l'acteur " + this.acteur.getNom(), SessionMBean.getUserAccount());
                acteur = new Acteur();
                detail = this.modifier = this.supprimer = true;
                signalSuccess();
            } else {
                signalError("not_row_selected");
            }
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
