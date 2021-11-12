package controllers.duplication_projet;

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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.transaction.Transactional;
import org.primefaces.context.RequestContext;
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

            mode = "Create";

            projet = new Projet();
            projet.setEtat(true);
            projet.setCloture(false);
            projet.setNotifSms(Boolean.FALSE);
            projet.setNotifMail(Boolean.TRUE);

            projet_d = new Projet();

            RequestContext.getCurrentInstance().execute("PF('DuplicationCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    @Transactional
    public void duplicateProject() {
        try {

            Projet oldProject = projetFacadeLocal.find(projet_d.getIdprojet());

            projet.setIdprojet(projetFacadeLocal.nextVal());
            projet.setIdperiode(periode);
            projet.setCloture(false);
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
                obj.setRepertoire(etp.getRepertoire());
                obj.setDelai(etp.getDelai());
                obj.setNotifMail(etp.isNotifMail());
                obj.setNotifSms(etp.isNotifSms());

                String s = lienRepertoire + "" + etp.getRepertoire();
                if ((Integer) mapLinkProject.get("systeme") == 1) {
                    s += "/";
                } else {
                    s += '\\';
                }
                File f = new File(s);
                f.mkdir();
                s = s.toLowerCase();
                obj.setLienRepertoire(s);

                etapeprojetFacadeLocal.create(obj);
                etapeprojets_1.add(obj);
                i++;
            }

            boolean sendMail = projet.isNotifMail();
            boolean sendSms = projet.isNotifSms();
            List<Acteur> acteurMails = new ArrayList<>();

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
                    obj_1.setActive(false);
                    obj_1.setDateprevisionnel(null);
                    obj_1.setDateprevisionnel(null);

                    if (pr.getIdetapeprojet().getNumero() == 1) {
                        obj_1.setDateprevisionnel(dateDebut);
                        if (pr.getIdetapeprojet().getDelai().equals(0)) {
                            obj_1.setDateFinPrevisionnel(dateDebut);
                        } else {
                            obj_1.setDateFinPrevisionnel(Utilitaires.addDaysToDate(dateDebut, pr.getIdetapeprojet().getDelai()));
                        }
                        obj_1.setActive(true);
                    }

                    if (pr.getIdetapeprojet().getDelai() == 0) {
                        pr.setActive(true);
                        pr.setNotifEmailProgram(true);
                        pr.setNotifEmailValidation(true);
                        if (sendMail || sendSms) {
                            if (pr.getIdacteur() != null) {
                                if (!acteurMails.contains(pr.getIdacteur())) {
                                    acteurMails.add(pr.getIdacteur());
                                }
                            }
                        }
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
                    obj_1.setNotifEmailProgram(pr.isNotifEmailProgram());
                    obj_1.setNotifEmailValidation(false);
                    programmationFacadeLocal.create(obj_1);
                }
            }
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            RequestContext.getCurrentInstance().execute("PF('DuplicationCreerDialog').hide()");
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
        List<Receipient> receipients = new ArrayList<>();
        final List<Acteur> listSuccess = new ArrayList<>();
        acteurs.stream().forEach((a) -> {
            try {
                if (a.getIdaddresse().getEmail() != null) {
                    receipients.add(new Receipient(a.getIdaddresse().getEmail(), a.getTitre()));
                    listSuccess.add(a);
                }
            } catch (Exception e) {
            }
        });
        emailRequest.setReceipients(receipients);
        MailThread mailThread = new MailThread(emailRequest);
        mailThread.start();

        Notification n = new Notification();
        n.setDateEnvoi(Date.from(Instant.now()));
        n.setMessageMail(emailRequest.getText());
        n.setMessage("-");
        n.setObjet(emailRequest.getSubject());
        n.setMail(true);
        n.setSms(false);
        n.setMessage("-");
        //n.getActeurs().addAll((Collection<Acteur>) listSuccess);
        n.setIdnotification(notificationFacadeLocal.nextVal());
        n.setIdperiode(projet.getIdperiode());
        notificationFacadeLocal.create(n);

        listSuccess.stream().map(a -> {
            NotificationActeur nTemp = new NotificationActeur(n.getIdnotification(), a.getIdacteur());
            return nTemp;
        }).forEach(object -> {
            notificationActeurFacadeLocal.create(object);
        });
    }

    private void sendSms(List<Acteur> acteurs) {

        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setSubject("Information : " + projet.getNom());
        smsRequest.setText("Bonjour, La CTN Vous informe que vous etes concernés par le projet mensionnée en object;"
                + " Veuillez vous connecter sur le portail pour fournir les documents exigés aux étapes vous concernant."
                + " Cordialement.");
        List<ReceipientSms> receipients = new ArrayList<>();
        acteurs.stream().forEach((a) -> {
            try {
                if (a.getIdaddresse().getTelephone1() != null) {
                    ReceipientSms r = new ReceipientSms(a.getIdaddresse().getTelephone1());
                    r.setActeur(a);
                    receipients.add(r);
                }
            } catch (Exception e) {
            }
        });
        smsRequest.setReceipients(receipients);
        SmsThread smsThread = new SmsThread(smsRequest);
        Integer api = SessionMBean.getParametrage().getIdSmsApi();

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

                        ObmSmsSendRequest request = new ObmSmsSendRequest("CTN", smsRequest.getText(), "237" + c.getContact());
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
            Notification n = new Notification();
            n.setMail(false);
            n.setSms(true);
            n.setDateEnvoi(Date.from(Instant.now()));
            n.setMessage(smsRequest.getText());
            n.setMessageMail("-");
            n.setObjet(smsRequest.getSubject());
            n.setMessageMail("-");
            //n.getActeurs().addAll((Collection<Acteur>) listSuccess);
            n.setIdnotification(notificationFacadeLocal.nextVal());
            n.setIdperiode(projet.getIdperiode());
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

    private List<Programmation> filterProgrammation(List<Programmation> programmations, Service s) {
        List<Programmation> results = new ArrayList<>();
        programmations.stream().filter((p) -> (p.getIdprojetservice().getIdservice().equals(s))).forEach((p) -> {
            results.add(p);
        });
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
