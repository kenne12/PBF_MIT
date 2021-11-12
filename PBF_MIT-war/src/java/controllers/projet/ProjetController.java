package controllers.projet;

import com.google.common.base.Objects;
import entities.Acteur;
import entities.Etape;
import entities.Etapeprojet;
import entities.Notification;
import entities.NotificationActeur;
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
import utils.AllmySms;
import utils.EmailRequest;
import utils.JsfUtil;
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
            projet.setNotifMail(Boolean.TRUE);
            projet.setNotifSms(Boolean.FALSE);
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
        List<Service> listServices = new ArrayList<>();
        projetservices.stream().forEach((p) -> {
            listServices.add(p.getIdservice());
        });
        return listServices;
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
                List<Acteur> listActeurCtn = acteurFacadeLocal.findAllRange(true);

                Service acv = serviceFacadeLocal.findByServiceParentAndRegion(projetservices.get(0).getIdservice().getIdparent(), true);

                List<Acteur> listActeurAcv = new ArrayList<>();
                if (acv != null) {
                    listActeurAcv.addAll(acteurFacadeLocal.findByIdservice(acv.getIdservice()));
                }

                int i = 0;
                for (Projetservice p : projetservices) {

                    List<Acteur> listActeurDistrict = new ArrayList<>();

                    if (p.getIdservice().getIdparent() != 0 && !p.getIdservice().getRegional()) {
                        listActeurDistrict.addAll(acteurFacadeLocal.findByIdservice(p.getIdservice().getIdservice()));
                    }

                    if (!listActeurCtn.isEmpty()) {
                        listActeurDistrict.addAll(listActeurCtn);
                    }

                    listActeurDistrict.removeAll(listActeurAcv);
                    listActeurDistrict.addAll(listActeurAcv);

                    projetservices.get(i).getIdservice().getActeurList().addAll(listActeurDistrict);

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
                            p1.setNotifEmailProgram(etps.isNotifMail());
                            p1.setNotifEmailValidation(false);
                            p1.setNotifSmsProgram(etps.isNotifSms());
                            p1.setNotifSmsValidation(false);
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
                    if (projet.isNotifMail()) {
                        et.setNotifMail(e.isNotifMailDefault());
                    }

                    if (projet.isNotifSms()) {
                        et.setNotifSms(e.isNotifSmsDefault());
                    }
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
            selectedServices.stream().filter((s) -> (!findService(s))).map((s) -> {
                Projetservice ps = new Projetservice();
                ps.setIdprojetservice(0L);
                ps.setIdservice(s);
                return ps;
            }).forEach((ps) -> {
                projetservices.add(ps);
            });
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
                    String resultat = Utilitaires.cleanLinkProject(projet.getRepertoire());
                    projet.setRepertoire(resultat);
                }

                Map mapLinkProject = Utilitaires.completeLinkProject(projet.getRepertoire());
                String lienRepertoire = (String) mapLinkProject.get("lien");

                projet.setLienRepertoire(lienRepertoire);
                projetFacadeLocal.create(projet);

                projetservices.stream().map((ps) -> {
                    ps.setIdprojetservice(projetserviceFacadeLocal.nextVal());
                    return ps;
                }).map((ps) -> {
                    ps.setIdprojet(projet);
                    return ps;
                }).forEach((ps) -> {
                    projetserviceFacadeLocal.create(ps);
                });

                for (Etapeprojet etp : etapeprojets) {
                    etp.setIdetapeprojet(etapeprojetFacadeLocal.nextVal());
                    if (etp.getNumero() == 1) {
                        etp.setDelai(0);
                    } else {
                        etp.setDateetatinitial(null);
                    }
                    etp.setIdprojet(projet);
                    String s = lienRepertoire + "" + etp.getRepertoire();
                    if ((Integer) mapLinkProject.get("systeme") == 1) {
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

                projetservices.stream().filter((ps) -> (ps.getIdprojetservice() == 0L)).map((ps) -> {
                    ps.setIdprojetservice(projetserviceFacadeLocal.nextVal());
                    return ps;
                }).map((ps) -> {
                    ps.setIdprojet(projet);
                    return ps;
                }).forEach((ps) -> {
                    projetserviceFacadeLocal.create(ps);
                });

                etapeprojets.stream().map((etp) -> {
                    if (etp.getNumero() == 1) {
                        etp.setDelai(0);
                    } else {
                        etp.setDateetatinitial(null);
                    }
                    return etp;
                }).forEach((etp) -> {
                    etapeprojetFacadeLocal.edit(etp);
                });

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification du projet : " + projet.getNom(), SessionMBean.getUserAccount());
                ut.commit();

                projet = new Projet();

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

    public void programmer(Projetservice projetservice) {
        int i = 0;
        for (Programmation p : projetservice.getProgrammationList()) {
            if (p.getIdprogrammation() != 0L && p.getIdprogrammation() != null) {
                programmationFacadeLocal.edit(p);
            } else {
                p.setIdprogrammation(programmationFacadeLocal.nextVal());
                if (p.getIdetapeprojet().getDelai() == 0) {
                    p.setActive(true);
                    p.setNotifEmailValidation(true);
                    p.setNotifSmsValidation(true);
                }
                p.setEnvoye(false);
                p.setValide(false);
                p.setTypefichier("-");
                p.setChemin("-");
                p.setRetard(0);
                p.setObservation("-");
                p.setObservationarchivee("-");
                p.setObservee(false);
                p.setConteur(0);
                p.setObservationvalidee(false);
                p.setObservationutilisateur("-");
                programmationFacadeLocal.create(p);
            }
            projetservice.getProgrammationList().set(i, p);
            i++;
        }
        int index = projetservices.indexOf(projetservice);
        projetservices.set(index, projetservice);
    }

    public void programmer() {
        try {
            boolean sendMail = projet.isNotifMail();
            boolean sendSms = projet.isNotifSms();
            final List<Acteur> acteurMails = new ArrayList<>();
            projetservices.stream().forEach((p) -> {
                p.getProgrammationList().stream().forEach((pr) -> {
                    if (pr.getIdprogrammation() == 0L) {
                        pr.setIdprogrammation(programmationFacadeLocal.nextVal());
                        pr.setActive(false);
                        pr.setValide(false);
                        pr.setEnvoye(false);
                        pr.setTypefichier("-");
                        pr.setRetard(0);
                        pr.setObservee(false);
                        pr.setObservationvalidee(false);
                        pr.setConteur(0);
                        pr.setObservation("-");
                        pr.setObservationarchivee("-");
                        if (pr.getIdetapeprojet().getDelai() == 0) {
                            pr.setActive(true);
                            pr.setNotifEmailValidation(true);
                            pr.setNotifSmsValidation(true);
                            if ((sendMail || sendSms) == true) {
                                if (pr.getIdacteur() != null) {
                                    if (!acteurMails.contains(pr.getIdacteur())) {
                                        acteurMails.add(pr.getIdacteur());
                                    }
                                }
                            }
                        }

                        if (pr.getIdetapeprojet().getNumero() == 1) {
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
                        programmationFacadeLocal.create(pr);
                    } else {
                        programmationFacadeLocal.edit(pr);
                    }
                });
            });
            signalSuccess();
            if (sendMail) {
                if (!acteurMails.isEmpty()) {
                    this.sendMail(acteurMails);
                }
            }

            if (sendSms) {
                if (!acteurMails.isEmpty()) {
                    this.sendSms(acteurMails);
                }
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    private void sendMail(List<Acteur> acteurs) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setSubject("Information : " + projet.getNom());
        emailRequest.setText("Bonjour M. / Mme ;\nLa CTN Vous informe que vous etes concernés par le projet mensionné en objet ;"
                + "\nVeuillez vous connecter sur le portail pour fournir les documents exigés aux étapes vous concernant."
                + "\nCordialement.");
        List<Receipient> receipients = Utilitaires.extracEmail(acteurs);
        emailRequest.setReceipients(receipients);
        MailThread mailThread = new MailThread(emailRequest);
        mailThread.start();

        if (!acteurs.isEmpty()) {
            Notification notification = new Notification();
            notification.setIdnotification(notificationFacadeLocal.nextVal());
            notification.setMessage("-");
            notification.setMessageMail(emailRequest.getText());
            notification.setMail(true);
            notification.setSms(false);
            notification.setObjet(emailRequest.getSubject());
            notification.setDateEnvoi(Date.from(Instant.now()));
            //notification.getActeurs().addAll((Collection<Acteur>) acteurs);
            notification.setIdperiode(periode);
            notificationFacadeLocal.create(notification);
        }
    }

    private void sendSms(List<Acteur> acteurs) {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setSubject("Information : " + projet.getNom());
        smsRequest.setText("Bonjour, La CTN Vous informe que vous etes concernés par le projet mensionné en object;"
                + " Veuillez vous connecter sur le portail pour fournir les documents exigés aux étapes vous concernant."
                + " Cordialement.");

        List<Acteur> listSuccess = new ArrayList<>();
        Integer api = SessionMBean.getParametrage().getIdSmsApi();
        int nbpage = Utilitaires.countSms(smsRequest.getText()).get("nombre_pages");
        if (api.equals(1)) {

            List<ReceipientSms> receipients = Utilitaires.extracPhoneNumber(acteurs);
            smsRequest.setReceipients(receipients);
            SmsThread smsThread = new SmsThread(smsRequest);

            smsRequest.getReceipients().forEach(s -> {
                SmsRequest sr = smsRequest;
                sr.setReceipientSms(s);
                String report = smsThread.runSingle(sr);

                Map m = AllmySms.treatResponse(report, api);
                if ((boolean) m.get("etat")) {
                    this.updateSoldeOrgUnit(sr.getReceipientSms().getActeur().getIdservice(), nbpage);
                    listSuccess.add(s.getActeur());
                }
            });
        }

        List<ObjectContactActeur> list = new ArrayList<>();

        if (api.equals(3) || api.equals(2)) {
            Map<String, List<ObjectContactActeur>> result = Utilitaires.filterContact(smsRequest.getReceipients());
            List<ObjectContactActeur> obms = result.get("obm_mtn_nexttel");
            if (!obms.isEmpty()) {
                String access_token = (String) Utilitaires.getDetailObmApi(3).get("access_token");
                obms.stream().filter((c) -> (!list.contains(c))).map((c) -> {
                    list.add(c);
                    return c;
                }).forEach((c) -> {
                    ObmSmsSendRequest request = new ObmSmsSendRequest("CTN", smsRequest.getText(), "237" + c.getContact());
                    String response = ObmSms.sendSms(access_token, request);
                    Map m = AllmySms.treatResponse(response, api);
                    if ((boolean) m.get("etat")) {
                        this.updateSoldeOrgUnit(c.getActeur().getIdservice(), nbpage);
                        listSuccess.add(c.getActeur());
                    }
                });
            }

            List<ObjectContactActeur> oranges = result.get("orange_cm");
            if (!oranges.isEmpty()) {
                String access_token = (String) Utilitaires.getDetailOrangeApi(2).get("access_token");
                oranges.stream().filter((c) -> (!list.contains(c))).map((c) -> {
                    list.add(c);
                    return c;
                }).forEach((c) -> {
                    String response = OrangeSmsSender.send2(access_token, c.getContact(), smsRequest.getText());
                    Map m = AllmySms.treatResponse(response, api);
                    if ((boolean) m.get("etat")) {
                        this.updateSoldeOrgUnit(c.getActeur().getIdservice(), nbpage);
                        listSuccess.add(c.getActeur());
                    }
                });
            }
        }

        if (!listSuccess.isEmpty()) {
            Notification notification = new Notification();
            notification.setIdnotification(notificationFacadeLocal.nextVal());
            notification.setMessage(smsRequest.getText());
            notification.setMessageMail("-");
            notification.setMail(false);
            notification.setSms(true);
            notification.setObjet(smsRequest.getSubject());
            notification.setDateEnvoi(Date.from(Instant.now()));
            //notification.getActeurs().addAll((Collection<Acteur>) listSuccess);
            notification.setIdperiode(periode);
            notificationFacadeLocal.create(notification);

            listSuccess.stream().map(a -> {
                NotificationActeur nTemp = new NotificationActeur(notification.getIdnotification(), a.getIdacteur());
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
        return i == 0;
    }

    public boolean renderLastUnderLined(Projetservice p) {
        int i = projetservices.indexOf(p);
        return (i + 1) != projetservices.size();
    }

    public void prepareReplication() {
        selectedReplicationServices.clear();
        replicationServiceSource = new Projetservice();
        RequestContext.getCurrentInstance().execute("PF('ReplicationConfigCreerDialog').show()");
    }

    public void replicationConfig() {

        if (replicationServiceSource == null) {
            JsfUtil.addErrorMessage("Veuillez selectionner la stucture source");
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            return;
        }

        if (selectedReplicationServices.contains(replicationServiceSource)) {
            selectedReplicationServices.remove(replicationServiceSource);
        }

        if (selectedReplicationServices.isEmpty()) {
            JsfUtil.addErrorMessage("Veuillez selectionner les structure finales de replication");
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            return;
        }

        int indexSource = projetservices.indexOf(replicationServiceSource);
        Map<Integer, Acteur> map = this.putActeurByProgrammation(projetservices.get(indexSource).getProgrammationList());

        selectedReplicationServices.forEach(ps -> {
            int index = projetservices.indexOf(ps);
            int i = 0;
            for (Programmation p : projetservices.get(index).getProgrammationList()) {
                Acteur a = map.get(p.getIdetapeprojet().getIdetape().getIdetape());
                projetservices.get(index).getProgrammationList().get(i).setIdacteur(a);
                i++;
            }
        });

        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        RequestContext.getCurrentInstance().execute("PF('ReplicationConfigCreerDialog').hide()");
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

                Map<Integer, Acteur> map = this.putActeurByProgrammation(p.getProgrammationList());

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

    Map<Integer, Acteur> putActeurByProgrammation(List<Programmation> programmations) {
        Map<Integer, Acteur> map = new HashMap();
        programmations.forEach(p -> {
            try {
                if (p.getIdacteur() != null) {
                    map.put(p.getIdetapeprojet().getIdetape().getIdetape(), p.getIdacteur());
                } else {
                    map.put(p.getIdetapeprojet().getIdetape().getIdetape(), null);
                }
            } catch (Exception e) {
                map.put(p.getIdetapeprojet().getIdetape().getIdetape(), null);
            }
        });
        return map;
    }

    public void replicateData(Projetservice ps) {
        try {
            Projetservice p = ps;
            Map<Integer, Acteur> map = this.putActeurByProgrammation(p.getProgrammationList());

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

    public void deleteProgrammtion(Projetservice projetservice) {
        try {
            if (!Utilitaires.isAccess(23L)) {
                signalError("acces_refuse");
                return;
            }

            //ut.begin();
            piecejointesFacadeLocal.deleteByIdprojetservice(projetservice.getIdprojetservice());
            programmationFacadeLocal.deleteByIdprojetIdservice(projetservice.getIdprojetservice());
            projetserviceFacadeLocal.deleteByIdprojetService(projetservice.getIdprojetservice());
            projetservices.remove(projetservice);
            //ut.commit();

            Utilitaires.saveOperation(mouchardFacadeLocal, "Annulation de la programmation ; Projet : " + projetservice.getIdprojet().getNom() + " Unité d'organisation : " + projetservice.getIdservice().getNom(), SessionMBean.getUserAccount());
            //signalSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            //signalException(e);
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
