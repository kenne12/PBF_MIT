/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.document;

import entities.Document;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.MouchardFacadeLocal;
import sessions.DocumentFacadeLocal;
import utils.Routine;

/**
 *
 * @author kenne
 */
public class AbstractDocumentController {

    @EJB
    protected DocumentFacadeLocal documentFacadeLocal;
    protected Document document = new Document();
    protected List<Document> documents = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected Routine routine = new Routine();

    protected String mode = "";

    public Boolean getDetail() {
        return detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public Boolean getModifier() {
        return modifier;
    }

    public void setModifier(Boolean modifier) {
        this.modifier = modifier;
    }

    public Boolean getConsulter() {
        return consulter;
    }

    public void setConsulter(Boolean consulter) {
        this.consulter = consulter;
    }

    public void setImprimer(Boolean imprimer) {
        this.imprimer = imprimer;
    }

    public Boolean getSupprimer() {
        return supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        this.supprimer = supprimer;
    }

    public Routine getRoutine() {
        return routine;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
        modifier = supprimer = detail = document == null;
    }

    public List<Document> getDocuments() {
        try {
            documents = documentFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

}
