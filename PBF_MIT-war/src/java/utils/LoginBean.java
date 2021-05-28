/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Menu;
import entities.Mouchard;
import entities.Privilege;
import entities.Utilisateur;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import sessions.UtilisateurFacadeLocal;

/**
 *
 * @author romuald
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean extends AbstractLoginBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    @EJB
    private UtilisateurFacadeLocal utilisateurFacadeLocal;
    private Utilisateur utilisateur = new Utilisateur();
    private Utilisateur utilisateurConnected;

    String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

    @PostConstruct
    public void init() {
        utilisateur = new Utilisateur();
    }

    public LoginBean() {

    }

    public void login() {
        try {
            utilisateur = utilisateurFacadeLocal.login(utilisateur.getLogin(), new ShaHash().hash(utilisateur.getPassword()));
            if (utilisateur != null) {

                if (utilisateur.getEtat()) {

                    HttpSession session = SessionMBean.getSession();

                    session.setAttribute("compte", utilisateur);
                    session.setAttribute("session", false);

                    parametrage = parametrageFacadeLocal.findAll().get(0);
                    session.setAttribute("parametre", parametrage);
                    List<Privilege> privilegesTemp = privilegeFacadeLocal.findByUser(utilisateur.getIdutilisateur());
                    List<Long> accesses = new ArrayList<>();
                    List<String> access = new ArrayList<>();

                    for (Privilege p : privilegesTemp) {
                        accesses.add(p.getIdmenu().getIdmenu().longValue());
                        String[] menus = p.getIdmenu().getRessource().split(";");
                        for (String temp : menus) {
                            if (!access.contains(temp)) {
                                access.add(temp);
                            }
                        }
                    }
                    session.setAttribute("accesses", accesses);
                    session.setAttribute("access", access);

                    List<String> allAccess = new ArrayList<>();

                    for (Menu m : menuFacadeLocal.findAll()) {
                        String[] menus = m.getRessource().split(";");
                        for (String temp : menus) {
                            if (!allAccess.contains(temp)) {
                                allAccess.add(temp);
                            }
                        }
                    }

                    session.setAttribute("allAccess", allAccess);

                    String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                    FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/index.html");

                    //EmailRequest emailRequest = new EmailRequest();
                    //emailRequest.getReceipients().add(new Receipient("kennegervais@beininfoplus.com", "ACV LITTORAL"));
                    //emailRequest.setText("Bonjour monsieur gervais , Veuillez prendre note des differentes stratégie à appliquer");
                    //emailRequest.setSubject("Réunion de coordination");

                    //JavaMailUtils.sendMail(emailRequest);
                } else {
                    JsfUtil.addWarningMessage("Compte bloqué ! contactez l'administrateur");
                }
            } else {
                utilisateur = new Utilisateur();
                JsfUtil.addErrorMessage("Login ou mot de passe incorrect");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            utilisateur = new Utilisateur();
            JsfUtil.addErrorMessage("Echec système");
        }
    }

    public void initSession() {
        try {
            showSessionPanel = false;
            //HttpSession session = SessionMBean.getSession();
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Echec");
        }
    }

    public void closeSession() {
        try {

        } catch (Exception e) {
            routine.catchException(e, routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    //methode qui permet de deconnecter un utilisateur
    public void deconnexion() {
        mouchard = new Mouchard();
        Utilisateur user = SessionMBean.getUserAccount();
        Utilitaires.saveOperation(mouchardFacadeLocal, "Déconnexion", user);
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

            UtilitaireSession.getInstance().setuser(null);
            FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //methode qui permet de changer les données de session
    public void change_session() {
        try {
            showSessionPanel = true;
            String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/index.html?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update_theme() {
        try {
            utilisateurFacadeLocal.edit(utilisateur);
        } catch (Exception e) {
            e.printStackTrace();
            //utilisateur.setTheme("bootstrap");
            utilisateurFacadeLocal.edit(utilisateur);
        }
    }

    //methode qui met en veille un utilisateur
    public void hibbernate() {
        try {
            showHibernatePanel = true;
            hibernatePassword = "";
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void unHibbernate() {
        try {
            if (new ShaHash().hash(hibernatePassword).equals(SessionMBean.getUserAccount().getPassword())) {
                showHibernatePanel = false;
            } else {
                showHibernatePanel = true;
                JsfUtil.addErrorMessage(routine.localizeMessage("mot_passe_incorrect"));
            }
        } catch (Exception e) {
            e.getMessage();
            JsfUtil.addErrorMessage(routine.localizeMessage("erreur"));
        }
    }

    public Utilisateur getUserconnected() {
        utilisateurConnected = UtilitaireSession.getInstance().getuser();
        String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if (utilisateurConnected == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Uitlisateur déconnecté +++++++++++++++++++ ");
        }
        return utilisateurConnected;
    }

    public void setPriv() {
        watcher();
    }

    public static void watcher() {
        try {
            if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("compte")) {
                String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCompte() {
        try {
            if (!utilisateur.getPassword().equals("") || !utilisateur.getPassword().equals(null)) {
                if (utilisateur.getPassword().equals(confirmPassword)) {
                    utilisateur.setPassword(new ShaHash().hash(confirmPassword));
                    utilisateurFacadeLocal.edit(utilisateur);
                    confirmPassword = "";
                    RequestContext.getCurrentInstance().execute("PF('Modifier_compteDialog').hide()");
                    routine.feedBack("information", "/resources/tool_images/success.png", routine.localizeMessage("operation_reussie"));
                    RequestContext.getCurrentInstance().execute("PF('NotifyDialog').show()");
                } else {
                    utilisateur = utilisateurFacadeLocal.find(utilisateur.getIdutilisateur());
                    confirmPassword = "";
                    routine.feedBack("avertissement", "/resources/tool_images/error.png", routine.localizeMessage("mot_de_passe_non_identic"));
                    RequestContext.getCurrentInstance().execute("PF('NotifyDialog').show()");
                }
            } else {
                utilisateur = utilisateurFacadeLocal.find(utilisateur.getIdutilisateur());
                confirmPassword = "";
                routine.feedBack("avertissement", "/resources/tool_images/error.png", routine.localizeMessage("saisir_mot_de_passe"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog').show()");
            }
        } catch (Exception e) {
            utilisateur = utilisateurFacadeLocal.find(utilisateur.getIdutilisateur());
            confirmPassword = "";
            routine.catchException(e, routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog').show()");
        }
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Object getUser() {
        return utilisateur;
    }

    public void setUser(Object user) {
        this.utilisateur = (Utilisateur) user;
    }

}
