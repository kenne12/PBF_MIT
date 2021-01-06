--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.7
-- Dumped by pg_dump version 9.5.7

-- Started on 2020-04-03 15:28:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2336 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 181 (class 1259 OID 127809)
-- Name: acteur; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE acteur (
    idacteur integer NOT NULL,
    idaddresse bigint,
    nom character varying(254),
    prenom character varying(254),
    sexe character varying(254),
    datenaissance date,
    lieunaissance character varying(254),
    idservice integer,
    iddocument integer
);


--
-- TOC entry 182 (class 1259 OID 127815)
-- Name: addresse; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE addresse (
    idaddresse bigint NOT NULL,
    telephone1 character varying(254),
    telephone2 character varying(254),
    adresse character varying(254),
    email character varying(254),
    siteweb character varying(254),
    bp character varying(254)
);


--
-- TOC entry 183 (class 1259 OID 127821)
-- Name: alerte; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE alerte (
    idalerte bigint NOT NULL,
    idmodealerte integer,
    idtyperappel integer,
    message character varying(254),
    datealerte date,
    heurealerte date
);


--
-- TOC entry 184 (class 1259 OID 127824)
-- Name: document; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE document (
    iddocument integer NOT NULL,
    nom character varying(254),
    description character varying(254),
    code character varying
);


--
-- TOC entry 185 (class 1259 OID 127830)
-- Name: etape; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE etape (
    idetape integer NOT NULL,
    nom character varying,
    code character varying,
    iddocument integer,
    delai_default integer DEFAULT 0
);


--
-- TOC entry 186 (class 1259 OID 127837)
-- Name: etapeprojet; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE etapeprojet (
    idetapeprojet bigint NOT NULL,
    idprojet integer,
    numero integer,
    delai integer,
    dateetatinitial date,
    idetapeparent integer,
    idetape integer
);


--
-- TOC entry 187 (class 1259 OID 127840)
-- Name: menu; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE menu (
    idmenu integer NOT NULL,
    nom character varying(254),
    ressource character varying(254)
);


--
-- TOC entry 188 (class 1259 OID 127846)
-- Name: modealerte; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE modealerte (
    idmodealerte integer NOT NULL,
    nomfr character varying(254),
    nomen character varying(254)
);


--
-- TOC entry 189 (class 1259 OID 127852)
-- Name: mouchard; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE mouchard (
    idmouchard bigint NOT NULL,
    idutilisateur integer,
    action character varying(254),
    dateaction date,
    heure date
);


--
-- TOC entry 190 (class 1259 OID 127855)
-- Name: parametrage; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE parametrage (
    idparametrage integer NOT NULL,
    repertoire_document character varying,
    repertoire_piece character varying
);


--
-- TOC entry 191 (class 1259 OID 127861)
-- Name: periode; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE periode (
    idperiode integer NOT NULL,
    nom character varying(254),
    etat boolean,
    idparent integer,
    code character varying
);


--
-- TOC entry 192 (class 1259 OID 127867)
-- Name: piecejointes; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE piecejointes (
    idpiecejointes bigint NOT NULL,
    idprogrammation bigint,
    idtraitement bigint,
    datevalidation date,
    texte character varying(254),
    observation character varying(254),
    validee boolean,
    chemin character varying(254),
    typefichier character varying,
    vu boolean DEFAULT false
);


--
-- TOC entry 193 (class 1259 OID 127874)
-- Name: planningrappel; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE planningrappel (
    idplanningrappel bigint NOT NULL,
    idtyperappel integer,
    idmodealerte integer,
    nombrejr integer,
    occurence integer,
    intervalrappel integer
);


--
-- TOC entry 194 (class 1259 OID 127877)
-- Name: privilege; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE privilege (
    idprivilege integer NOT NULL,
    idmenu integer,
    idutilisateur integer
);


--
-- TOC entry 195 (class 1259 OID 127880)
-- Name: programmation; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE programmation (
    idprogrammation bigint NOT NULL,
    idetapeprojet bigint,
    iddocument integer,
    idacteur integer,
    dateprevisionnel date,
    daterealisation date,
    valide boolean,
    envoye boolean,
    chemin character varying,
    typefichier character varying,
    active boolean,
    date_validation date,
    retard integer DEFAULT 0,
    observation text,
    observationarchivee text,
    observee boolean DEFAULT false,
    observationvalidee boolean DEFAULT false,
    conteur integer DEFAULT 0,
    idprojetservice bigint,
    observationutilisateur text DEFAULT '-'::text
);


--
-- TOC entry 196 (class 1259 OID 127890)
-- Name: projet; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE projet (
    idprojet integer NOT NULL,
    idperiode integer,
    nom character varying(254),
    etat boolean,
    cloture boolean DEFAULT false
);


--
-- TOC entry 197 (class 1259 OID 127894)
-- Name: projetservice; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE projetservice (
    idprojetservice bigint NOT NULL,
    idprojet integer,
    idservice integer
);


--
-- TOC entry 198 (class 1259 OID 127897)
-- Name: service; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE service (
    idservice integer NOT NULL,
    idaddresse bigint,
    code character varying(254),
    nom character varying(254),
    datecreation date,
    responsable character varying(254),
    idparent integer,
    central boolean DEFAULT false,
    regional boolean DEFAULT false
);


--
-- TOC entry 199 (class 1259 OID 127904)
-- Name: traitement; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE traitement (
    idtraitement bigint NOT NULL,
    idacteur integer,
    idetapeprojet bigint,
    dateprevinitial date,
    dateenvoieffectif date,
    validee boolean
);


--
-- TOC entry 200 (class 1259 OID 127907)
-- Name: typerappel; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE typerappel (
    idtyperappel integer NOT NULL,
    nomfr character varying(254),
    nomen character varying(254)
);


--
-- TOC entry 201 (class 1259 OID 127913)
-- Name: utilisateur; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE utilisateur (
    idutilisateur integer NOT NULL,
    nom character varying(254),
    prenom character varying(254),
    login character varying(254),
    password character varying(254),
    datecreation date,
    etat boolean,
    photo character varying(254),
    idacteur integer
);


--
-- TOC entry 2309 (class 0 OID 127809)
-- Dependencies: 181
-- Data for Name: acteur; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO acteur (idacteur, idaddresse, nom, prenom, sexe, datenaissance, lieunaissance, idservice, iddocument) VALUES (5, 10, 'Spécialiste en Gestion Financiere', 'M. VEBEM Emmanuel', NULL, NULL, NULL, 3, 5);
INSERT INTO acteur (idacteur, idaddresse, nom, prenom, sexe, datenaissance, lieunaissance, idservice, iddocument) VALUES (1, 5, 'Manager ACV Littoral', 'Dr Nogbe', NULL, NULL, NULL, 28, 1);
INSERT INTO acteur (idacteur, idaddresse, nom, prenom, sexe, datenaissance, lieunaissance, idservice, iddocument) VALUES (3, 8, 'Manager Adjoint ACV Littoral', 'Manager Adjoint ACV Littoral', NULL, NULL, NULL, 4, 1);
INSERT INTO acteur (idacteur, idaddresse, nom, prenom, sexe, datenaissance, lieunaissance, idservice, iddocument) VALUES (2, 6, 'Administrateur National du Portail PBF', 'HENOCK', NULL, NULL, NULL, 3, 3);
INSERT INTO acteur (idacteur, idaddresse, nom, prenom, sexe, datenaissance, lieunaissance, idservice, iddocument) VALUES (4, 9, 'Administrateur Adjoint du Portail PBF', 'ABATE', NULL, NULL, NULL, 3, 3);
INSERT INTO acteur (idacteur, idaddresse, nom, prenom, sexe, datenaissance, lieunaissance, idservice, iddocument) VALUES (6, 36, 'RESPO', 'LITTORAL', NULL, NULL, NULL, 5, NULL);


--
-- TOC entry 2310 (class 0 OID 127815)
-- Dependencies: 182
-- Data for Name: addresse; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (1, '673564186', NULL, '-', '-', NULL, '-');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (2, '673564186', NULL, '-', '-', NULL, '-');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (3, '673564186', NULL, '-', '-', NULL, '-');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (4, '673564186', NULL, '-', '-', NULL, '-');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (7, '673564186', NULL, '-', '-', NULL, '-');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (12, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (13, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (14, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (15, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (16, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (17, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (18, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (19, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (20, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (21, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (22, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (23, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (24, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (25, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (26, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (27, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (28, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (29, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (30, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (31, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (32, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (33, '', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (11, '-', NULL, '-', '-', NULL, '-');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (34, '699975351', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (35, '671752964', NULL, '', '', NULL, '');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (10, '675199710', NULL, '-', '-', NULL, '-');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (5, '699975351', NULL, '-', '-', NULL, '-');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (8, '676047664', NULL, '-', '-', NULL, '-');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (6, '655256488', NULL, '-', '-', NULL, '-');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (9, '694841138', NULL, '-', '-', NULL, '-');
INSERT INTO addresse (idaddresse, telephone1, telephone2, adresse, email, siteweb, bp) VALUES (36, '673564186', NULL, '-', '-', NULL, '-');


--
-- TOC entry 2311 (class 0 OID 127821)
-- Dependencies: 183
-- Data for Name: alerte; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2312 (class 0 OID 127824)
-- Dependencies: 184
-- Data for Name: document; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO document (iddocument, nom, description, code) VALUES (1, 'FACTURE CONSOLIDEE +PV DE VALIDATION +Annexes', 'Docuement issus du comité de validation.
Les annexes sont cosntitués d''un rapport d''analyse des crietres de contractualisation des FOSA + la fiche de présence +...', 'Doc.1');
INSERT INTO document (iddocument, nom, description, code) VALUES (2, '---', '---', '2');
INSERT INTO document (iddocument, nom, description, code) VALUES (4, '----', '----', '4');
INSERT INTO document (iddocument, nom, description, code) VALUES (5, 'BORDEREAU DE VIREMENT', '-', 'Doc.2');
INSERT INTO document (iddocument, nom, description, code) VALUES (3, 'PV DE CONTROLE / VERIFICATION DES FACTURES', '-', '3');


--
-- TOC entry 2313 (class 0 OID 127830)
-- Dependencies: 185
-- Data for Name: etape; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO etape (idetape, nom, code, iddocument, delai_default) VALUES (3, 'Vérification', '3', 3, 3);
INSERT INTO etape (idetape, nom, code, iddocument, delai_default) VALUES (2, 'Envoi des factures consolidées', '2', 1, 5);
INSERT INTO etape (idetape, nom, code, iddocument, delai_default) VALUES (1, 'Validation par le  comité', '1', 2, 0);
INSERT INTO etape (idetape, nom, code, iddocument, delai_default) VALUES (4, 'Payement', '4', 5, 8);


--
-- TOC entry 2314 (class 0 OID 127837)
-- Dependencies: 186
-- Data for Name: etapeprojet; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO etapeprojet (idetapeprojet, idprojet, numero, delai, dateetatinitial, idetapeparent, idetape) VALUES (1, 1, 1, 0, '2019-10-31', NULL, 1);
INSERT INTO etapeprojet (idetapeprojet, idprojet, numero, delai, dateetatinitial, idetapeparent, idetape) VALUES (2, 1, 2, 5, NULL, NULL, 2);
INSERT INTO etapeprojet (idetapeprojet, idprojet, numero, delai, dateetatinitial, idetapeparent, idetape) VALUES (3, 1, 3, 3, NULL, NULL, 3);
INSERT INTO etapeprojet (idetapeprojet, idprojet, numero, delai, dateetatinitial, idetapeparent, idetape) VALUES (4, 1, 4, 8, NULL, NULL, 4);
INSERT INTO etapeprojet (idetapeprojet, idprojet, numero, delai, dateetatinitial, idetapeparent, idetape) VALUES (5, 2, 1, 0, '2019-11-30', NULL, 1);
INSERT INTO etapeprojet (idetapeprojet, idprojet, numero, delai, dateetatinitial, idetapeparent, idetape) VALUES (6, 2, 2, 5, NULL, NULL, 2);
INSERT INTO etapeprojet (idetapeprojet, idprojet, numero, delai, dateetatinitial, idetapeparent, idetape) VALUES (7, 2, 3, 3, NULL, NULL, 3);
INSERT INTO etapeprojet (idetapeprojet, idprojet, numero, delai, dateetatinitial, idetapeparent, idetape) VALUES (8, 2, 4, 8, NULL, NULL, 4);


--
-- TOC entry 2315 (class 0 OID 127840)
-- Dependencies: 187
-- Data for Name: menu; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO menu (idmenu, nom, ressource) VALUES (1, 'SUPER ADMIN', '-');
INSERT INTO menu (idmenu, nom, ressource) VALUES (2, 'CREER UN UTILISATEURS', '/PBF_MIT-war/securite/utilisateur/utilisateur.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (3, 'MODIFIER UN UTILISATEURS', '/PBF_MIT-war/securite/utilisateur/utilisateur.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (4, 'SUPPRIMER UN UTILISATEUR', '/PBF_MIT-war/securite/utilisateur/utilisateur.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (5, 'REINITIALISER LES COMPTES UTILISATEURS', '/PBF_MIT-war/securite/reset-compte/reset-compte.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (6, 'ACTIVER UN COMPTES UTILISATEURS', '/PBF_MIT-war/securite/activercompte/activercompte.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (7, 'DESACTIVER UN COMPTE UTILISATEUR', '/securite/desactivercompte/desactivercompte.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (8, 'EDITER LE MOUCHARD', '/PBF_MIT-war/securite/mouchard/mouchard.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (9, 'AJOUT / RETRAIT DES PRIVILEGES', '/PBF_MIT-war/securite/privilege/privilege.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (10, 'CREER UNE UNITES D''ORGANISATION', '/PBF_MIT-war/parametrage/unite-organisation/unite-organisation.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (11, 'MODIFIER UNE UNITE D''ORGANISATION', '/PBF_MIT-war/parametrage/unite-organisation/unite-organisation.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (12, 'SUPPRIMER UNE UNITE D''ORGANISATION', '/PBF_MIT-war/parametrage/unite-organisation/unite-organisation.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (13, 'CREER UNE PERIODE', '/PBF_MIT-war/parametrage/periode/periode.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (14, 'MODIFIER UNE PERIODE', '/PBF_MIT-war/parametrage/periode/periode.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (15, 'SUPPRIMER UNE PERIODE', '/PBF_MIT-war/parametrage/periode/periode.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (16, 'ENREGISTRER UN DOCUMENT', '/PBF_MIT-war/parametrage/document/document.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (17, 'MODIFIER UN DOCUMENT', '/PBF_MIT-war/parametrage/document/document.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (18, 'SUPPRIMER UN DOCUMENT', '/PBF_MIT-war/parametrage/document/document.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (19, 'ENREGISTRER LES ETAPES', '/PBF_MIT-war/parametrage/etape/etape.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (20, 'MODIFIER LES ETAPES', '/PBF_MIT-war/parametrage/etape/etape.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (21, 'SUPPRIMER LES ETAPES', '/PBF_MIT-war/parametrage/etape/etape.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (22, 'ENREGISTRER UN ACTEUR', '/PBF_MIT-war/parametrage/acteur/acteur.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (23, 'MODIFIER UN ACTEUR', '/PBF_MIT-war/parametrage/acteur/acteur.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (24, 'SUPPRIMER UN ACTEUR', '/PBF_MIT-war/parametrage/acteur/acteur.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (25, 'CREER UN PROJET', '/PBF_MIT-war/operation/projet/projet.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (26, 'MODIFIER UN PROJET', '/PBF_MIT-war/operation/projet/projet.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (27, 'SUPPRIMER UN PROJET', '/PBF_MIT-war/operation/projet/projet.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (28, 'VISUALISER LE DETAIL D''UN PROJET', '/PBF_MIT-war/operation/projet/projet.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (30, 'DUPLIQUER LES PROJET ', '/PBF_MIT-war/operation/duplication_projet/duplication_projet.html');
INSERT INTO menu (idmenu, nom, ressource) VALUES (29, 'PROGRAMMER LES DOCUMENTS D''UN PROJET', '/PBF_MIT-war/operation/projet/projet.html;/PBF_MIT-war/operation/programmation/programmation.html');


--
-- TOC entry 2316 (class 0 OID 127846)
-- Dependencies: 188
-- Data for Name: modealerte; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2317 (class 0 OID 127852)
-- Dependencies: 189
-- Data for Name: mouchard; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (1, 1, 'Enregistrement de l''unité d''organisation : NORD', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (2, 1, 'Enregistrement de l''unité d''organisation : DISTRICT_1', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (3, 1, 'Enregistrement de l''unité d''organisation : KENNE', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (4, 1, 'Suppresion de l''unité d''organisation : KENNE', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (5, 1, 'Enregistrement de la période : 2019', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (6, 1, 'Enregistrement de la période : 2019', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (7, 1, 'Enregistrement de la période : 2019', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (8, 1, 'Enregistrement de la période : Février 2019', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (9, 1, 'Enregistrement de l''étape  : Validation par le  comité', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (10, 1, 'Enregistrement du document  : Docuement issu lors du comité de validation', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (11, 1, 'Enregistrement de l''étape  : Envoi des factures', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (12, 1, 'Enregistrement du document  : Factures', '2019-06-19', '2019-06-19');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (13, 1, 'Enregistrement du projet : Traitement des factures de février 2019 ', '2019-06-25', '2019-06-25');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (14, 1, 'Enregistrement du projet : Traitement des factures de février 2019', '2019-06-25', '2019-06-25');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (15, 1, 'Enregistrement de l''acteur : KENNE', '2019-06-26', '2019-06-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (16, 1, 'Enregistrement de l''unité d''organisation : CTN PBF', '2019-06-26', '2019-06-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (17, 1, 'Suppresion du projet Traitement des factures de février 2019', '2019-06-26', '2019-06-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (18, 1, 'Enregistrement de l''acteur : ACTEUR_DISTRICT_1', '2019-06-26', '2019-06-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (19, 1, 'Enregistrement de l''acteur : ACTEUR_CTN_1', '2019-06-26', '2019-06-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (20, 1, 'Enregistrement du projet : Traitement des fatures du mois de février 2019', '2019-06-26', '2019-06-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (21, 1, 'Suppresion du projet Traitement des fatures du mois de février 2019', '2019-06-26', '2019-06-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (22, 1, 'Enregistrement du projet : Traitement des factures de février 2019', '2019-06-26', '2019-06-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (23, 1, 'Programmation des differentes etapes de la structure : DISTRICT_1 Projet : Traitement des factures de février 2019', '2019-06-26', '2019-06-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (24, 1, 'Enregistrement du projet : Taitement des facture de janvier 2019 - Region du littoral', '2019-07-01', '2019-07-01');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (25, 1, 'Modification du projet : Traitement des factures de février 2019', '2019-07-01', '2019-07-01');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (26, 1, 'Programmation des differentes etapes de la structure : DISTRICT_1 Projet : Taitement des facture de janvier 2019 - Region du littoral', '2019-07-01', '2019-07-01');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (27, 1, 'Déconnexion', '2019-07-01', '2019-07-01');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (28, 1, 'Déconnexion', '2019-07-03', '2019-07-03');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (29, 1, 'Déconnexion', '2019-07-04', '2019-07-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (30, 1, 'Enregistrement de l''utilisateur : null null', '2019-07-04', '2019-07-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (31, 1, 'Déconnexion', '2019-07-04', '2019-07-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (32, 1, 'Enregistrement de la période : MARS 2019', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (33, 1, 'Enregistrement du projet : Projet de traitement des factures de M3_2019', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (34, 1, 'Enregistrement de l''unité d''organisation : DISTRICT_2', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (35, 1, 'Enregistrement de l''acteur : RESPO_D2', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (36, 1, 'Suppresion du projet Projet de traitement des factures de M3_2019', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (37, 1, 'Enregistrement de l''étape  : Vérification', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (38, 1, 'Enregistrement de l''étape  : Payement', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (39, 1, 'Enregistrement du document  : PV de control', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (40, 1, 'Enregistrement du document  : Facture de payement', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (41, 1, 'Enregistrement de l''acteur : NJUOM', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (42, 1, 'Enregistrement de l''acteur : COMPTA', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (43, 1, 'Enregistrement du projet : Traitement des factures de M3_2019', '2019-07-15', '2019-07-15');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (44, 1, 'Enregistrement de l''étape  : Etape 5', '2019-07-17', '2019-07-17');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (45, 1, 'Suppresion de létape : Etape 5', '2019-07-17', '2019-07-17');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (46, 1, 'Enregistrement du projet : Projet 2019', '2019-07-18', '2019-07-18');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (47, 1, 'Déconnexion', '2019-07-18', '2019-07-18');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (48, 1, 'Enregistrement du projet : PROJET dsitrict 2', '2019-07-22', '2019-07-22');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (49, 1, 'Enregistrement du projet : Traitement des factures M1 ', '2019-07-22', '2019-07-22');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (50, 1, 'Enregistrement du projet : Traitement des facture de M3 / 2019', '2019-07-22', '2019-07-22');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (51, 1, 'Déconnexion', '2019-07-22', '2019-07-22');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (52, 1, 'ATTRIBUTION DU PRIVILEGE : SUPER ADMINISTRATEUR à l''utilisateur -> null null', '2019-07-22', '2019-07-22');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (53, 1, 'Déconnexion', '2019-07-22', '2019-07-22');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (54, 2, 'Enregistrement de l''utilisateur : null null', '2019-07-22', '2019-07-22');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (55, 2, 'Déconnexion', '2019-07-22', '2019-07-22');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (56, 3, 'Déconnexion', '2019-07-22', '2019-07-22');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (57, 1, 'Enregistrement du projet : Traitement des facture de M2 2019', '2019-07-22', '2019-07-22');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (58, 1, 'Déconnexion', '2019-07-22', '2019-07-22');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (59, 1, 'Enregistrement du projet : Troisième traitement des facture', '2019-07-24', '2019-07-24');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (60, 1, 'Enregistrement de la période : 2020', '2019-08-06', '2019-08-06');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (61, 1, 'Enregistrement de la période : Janvier 2020', '2019-08-06', '2019-08-06');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (62, 1, 'Enregistrement du projet : Projet de traitement 2020', '2019-08-06', '2019-08-06');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (63, 1, 'Modification du projet : Projet de traitement 2020', '2019-08-06', '2019-08-06');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (64, 1, 'Enregistrement de l''acteur : kenne', '2019-08-06', '2019-08-06');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (65, 1, 'Suppresion de l''acteur kenne', '2019-08-06', '2019-08-06');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (66, 1, 'Déconnexion', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (67, 1, 'Enregistrement de l''unité d''organisation : ACV Littoral', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (68, 1, 'Enregistrement de l''unité d''organisation : DS-LT-ABO', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (69, 1, 'Enregistrement de l''unité d''organisation : DS-LT-BOKO', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (70, 1, 'Enregistrement de l''unité d''organisation : DS-LT-BONASAMA', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (71, 1, 'Enregistrement de l''unité d''organisation : DS-LT-CITE PALMIERS', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (72, 1, 'Enregistrement de l''unité d''organisation : DS-LT-DIBOMBARI', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (73, 1, 'Enregistrement de l''unité d''organisation : DS-LT-EDEA', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (74, 1, 'Enregistrement de l''unité d''organisation : DS-LT-JAPOMA', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (75, 1, 'Enregistrement de l''unité d''organisation : DS-LT-LOGBABA', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (76, 1, 'Enregistrement de l''unité d''organisation : DS-LT-LOUM', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (77, 1, 'Enregistrement de l''unité d''organisation : DS-LT-MANJO', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (78, 1, 'Enregistrement de l''unité d''organisation : DS-LT-MBANGA', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (79, 1, 'Enregistrement de l''unité d''organisation : DS-LT-MBANGUE', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (80, 1, 'Enregistrement de l''unité d''organisation : DS-LT-MELONG', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (81, 1, 'Enregistrement de l''unité d''organisation : DS-LT-NDOM', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (82, 1, 'Enregistrement de l''unité d''organisation : DS-LT-NEW BEL', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (83, 1, 'Enregistrement de l''unité d''organisation : DS-LT-NGAMBE', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (84, 1, 'Enregistrement de l''unité d''organisation : DS-LT-NJOMBE PENJA', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (85, 1, 'Enregistrement de l''unité d''organisation : DS-LT-NKONDJOCK', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (86, 1, 'Enregistrement de l''unité d''organisation : DS-LT-NKONGSAMBA', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (87, 1, 'Enregistrement de l''unité d''organisation : DS-LT-NYLON', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (88, 1, 'Enregistrement de l''unité d''organisation : DS-LT-POUMA', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (89, 1, 'Enregistrement de l''unité d''organisation : DS-LT-YABASSI', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (90, 1, 'Enregistrement de la période : Janvier 2019', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (91, 1, 'Enregistrement du document  : BORDEREAU DE VIREMENT', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (92, 1, 'Suppresion de la periode : Janvier 2019', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (93, 1, 'Enregistrement de l''unité d''organisation : ACV Littoral', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (94, 1, 'Enregistrement de l''unité d''organisation : Spécialiste Gestion Financiere (CTN-PBF)', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (95, 1, 'Suppresion de l''unité d''organisation : Spécialiste Gestion Financiere (CTN-PBF)', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (96, 1, 'Enregistrement du projet : TRAITEMENT FACTURES CONSOLIDEES DES DISTRICTS DE SANTE DE LA REGION  DU LITTORAL - Mois de Janvier 2019', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (97, 1, 'Programmation des differentes etapes de la structure : DS-LT-ABO Projet : TRAITEMENT FACTURES CONSOLIDEES DES DISTRICTS DE SANTE DE LA REGION  DU LITTORAL - Mois de Janvier 2019', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (98, 1, 'Programmation des differentes etapes de la structure : DS-LT-ABO Projet : TRAITEMENT FACTURES CONSOLIDEES DES DISTRICTS DE SANTE DE LA REGION  DU LITTORAL - Mois de Janvier 2019', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (99, 1, 'Modification du projet : TRAITEMENT FACTURES CONSOLIDEES DES DISTRICTS DE SANTE DE LA REGION  DU LITTORAL - Mois de Janvier 2019', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (100, 1, 'Modification du projet : Projet de traitement 2020', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (101, 1, 'Modification du projet : Traitement des facture de M2 2019', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (102, 1, 'Modification du projet : Traitement des facture de M3 / 2019', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (103, 1, 'Modification du projet : Troisième traitement des facture', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (104, 1, 'Déconnexion', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (105, 1, 'Enregistrement de la période : Juin 2019', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (106, 1, 'Enregistrement du projet : TRAITEMENT FACTURES CONSOLIDEES DES DISTRICTS DE SANTE DE LA REGION  DU LITTORAL - Mois de Juin 2019', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (107, 1, 'Programmation des differentes etapes de la structure : DS-LT-ABO Projet : TRAITEMENT FACTURES CONSOLIDEES DES DISTRICTS DE SANTE DE LA REGION  DU LITTORAL - Mois de Juin 2019', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (108, 1, 'Déconnexion', '2019-09-04', '2019-09-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (109, 1, 'Déconnexion', '2019-09-05', '2019-09-05');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (110, 1, 'Déconnexion', '2019-09-25', '2019-09-25');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (111, 1, 'Déconnexion', '2019-09-25', '2019-09-25');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (112, 1, 'Déconnexion', '2019-09-26', '2019-09-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (113, 3, 'Déconnexion', '2019-09-26', '2019-09-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (114, 2, 'Déconnexion', '2019-09-26', '2019-09-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (115, 2, 'Déconnexion', '2019-09-26', '2019-09-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (116, 2, 'Enregistrement de l''acteur : RESPO', '2019-09-26', '2019-09-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (117, 2, 'Enregistrement de l''utilisateur : null null', '2019-09-26', '2019-09-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (118, 2, 'Déconnexion', '2019-09-26', '2019-09-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (119, 4, 'Déconnexion', '2019-09-26', '2019-09-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (120, 3, 'Déconnexion', '2019-09-26', '2019-09-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (121, 1, 'Déconnexion', '2019-09-26', '2019-09-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (122, 3, 'Déconnexion', '2019-09-26', '2019-09-26');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (123, 1, 'Déconnexion', '2019-09-27', '2019-09-27');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (124, 3, 'Déconnexion', '2019-09-27', '2019-09-27');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (125, 1, 'Enregistrement du projet : Projet', '2019-10-01', '2019-10-01');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (126, 1, 'Enregistrement de la période : Octobre 2019', '2019-10-02', '2019-10-02');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (127, 1, 'Déconnexion', '2019-10-02', '2019-10-02');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (128, 1, 'Suppresion du projet Projet de traitement des factures : Littoral Octobre 2019', '2019-10-02', '2019-10-02');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (129, 1, 'Enregistrement du projet : Traitement littoral Octobre 2019', '2019-10-02', '2019-10-02');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (130, 1, 'Enregistrement de la période : Novembre 2019', '2019-10-02', '2019-10-02');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (131, 1, 'Annulation de la programmation ; Projet : Projet Unité d''organisation : ACV Littoral', '2019-10-04', '2019-10-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (132, 3, 'Déconnexion', '2019-10-04', '2019-10-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (133, 3, 'Déconnexion', '2019-10-04', '2019-10-04');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (134, 1, 'Enregistrement du projet : Projet de traitement des factures Littoral Octobre 2019', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (135, 1, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (136, 1, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (137, 4, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (138, 1, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (139, 1, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (140, 4, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (141, 1, 'Enregistrement du projet : Prjet octobrae Littoral Octobre 2019', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (142, 1, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (143, 4, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (144, 4, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (145, 1, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (146, 1, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (147, 4, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (148, 1, 'Enregistrement du projet : Projet littoral Nov 2019', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (149, 1, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (150, 4, 'Déconnexion', '2019-10-10', '2019-10-10');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (151, 1, 'Modification du projet : Projet Traitement des Factures : Littoral Octobre 2019', '2019-10-11', '2019-10-11');
INSERT INTO mouchard (idmouchard, idutilisateur, action, dateaction, heure) VALUES (152, 1, 'Modification du projet : Projet Traitement des Factures : Littoral Novembre 2019', '2019-10-11', '2019-10-11');


--
-- TOC entry 2318 (class 0 OID 127855)
-- Dependencies: 190
-- Data for Name: parametrage; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO parametrage (idparametrage, repertoire_document, repertoire_piece) VALUES (1, 'D:\pbf_mit\factures\', 'D:\pbf_mit\pieces_jointes\');


--
-- TOC entry 2319 (class 0 OID 127861)
-- Dependencies: 191
-- Data for Name: periode; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO periode (idperiode, nom, etat, idparent, code) VALUES (1, '2019', true, 0, '2019');
INSERT INTO periode (idperiode, nom, etat, idparent, code) VALUES (3, 'Février 2019', true, 1, 'M_2_2019');
INSERT INTO periode (idperiode, nom, etat, idparent, code) VALUES (4, 'MARS 2019', true, 1, 'M_3_2019');
INSERT INTO periode (idperiode, nom, etat, idparent, code) VALUES (5, '2020', true, 0, '2020');
INSERT INTO periode (idperiode, nom, etat, idparent, code) VALUES (6, 'Janvier 2020', true, 5, 'J_1_2020');
INSERT INTO periode (idperiode, nom, etat, idparent, code) VALUES (7, 'Janvier 2019', true, 1, '2019,01');
INSERT INTO periode (idperiode, nom, etat, idparent, code) VALUES (8, 'Juin 2019', true, 1, '2019,06');
INSERT INTO periode (idperiode, nom, etat, idparent, code) VALUES (9, 'Octobre 2019', true, 1, 'M_10_2019');
INSERT INTO periode (idperiode, nom, etat, idparent, code) VALUES (10, 'Novembre 2019', true, 1, 'M_11_2019');


--
-- TOC entry 2320 (class 0 OID 127867)
-- Dependencies: 192
-- Data for Name: piecejointes; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2321 (class 0 OID 127874)
-- Dependencies: 193
-- Data for Name: planningrappel; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2322 (class 0 OID 127877)
-- Dependencies: 194
-- Data for Name: privilege; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO privilege (idprivilege, idmenu, idutilisateur) VALUES (1, 1, 1);
INSERT INTO privilege (idprivilege, idmenu, idutilisateur) VALUES (2, 1, 2);


--
-- TOC entry 2323 (class 0 OID 127880)
-- Dependencies: 195
-- Data for Name: programmation; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (1, 1, 2, 1, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 1, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (2, 2, 1, 1, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 1, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (3, 3, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 1, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (4, 4, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 1, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (8, 4, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 2, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (13, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 4, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (14, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 4, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (15, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 4, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (16, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 4, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (17, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 5, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (18, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 5, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (19, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 5, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (20, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 5, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (21, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 6, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (22, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 6, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (23, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 6, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (24, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 6, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (25, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 7, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (26, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 7, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (27, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 7, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (28, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 7, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (29, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 8, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (30, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 8, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (31, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 8, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (32, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 8, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (33, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 9, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (34, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 9, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (35, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 9, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (36, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 9, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (37, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 10, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (38, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 10, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (39, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 10, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (40, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 10, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (41, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 11, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (42, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 11, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (43, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 11, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (44, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 11, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (45, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 12, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (46, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 12, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (47, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 12, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (48, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 12, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (49, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 13, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (50, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 13, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (51, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 13, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (52, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 13, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (53, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 14, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (54, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 14, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (55, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 14, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (56, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 14, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (57, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 15, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (58, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 15, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (59, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 15, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (60, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 15, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (61, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 16, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (62, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 16, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (63, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 16, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (64, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 16, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (65, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 17, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (66, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 17, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (67, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 17, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (68, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 17, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (69, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 18, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (70, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 18, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (71, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 18, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (72, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 18, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (73, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 19, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (74, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 19, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (75, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 19, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (76, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 19, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (77, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 20, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (78, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 20, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (79, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 20, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (80, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 20, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (81, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 21, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (82, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 21, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (83, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 21, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (84, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 21, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (85, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 22, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (86, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 22, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (87, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 22, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (88, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 22, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (7, 3, 3, 2, NULL, '2019-10-10', false, true, 'Document_CTN__decompte1._7_.pdf', 'application/pdf', false, NULL, 0, '-', '-', false, false, 1, 2, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (11, 3, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 3, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (12, 4, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 3, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (9, 1, 2, 6, '2019-10-31', '2019-10-10', true, true, 'Document_Region du Littoral__cloture_cycle._9_.pdf', 'application/pdf', true, '2019-10-10', -21, '-', '-', false, false, 1, 3, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (89, 1, 2, NULL, '2019-10-31', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 23, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (90, 2, 1, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 23, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (91, 3, 3, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 23, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (92, 4, 5, NULL, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 23, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (5, 1, 2, 6, '2019-10-31', '2019-10-10', true, true, 'Document_Region du Littoral__TitreFichIndiProg._5_.pdf', 'application/pdf', true, '2019-10-10', 0, 'Tout document envoyé', '-', true, false, 1, 2, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (6, 2, 1, 6, NULL, '2019-10-10', true, true, 'Document_Region du Littoral__etat_fond_secours._6_.pdf', 'application/pdf', false, '2019-10-10', 0, 'Clean', '-', true, true, 1, 2, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (10, 2, 1, 6, '2019-10-15', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 3, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (94, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 25, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (95, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 25, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (96, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 25, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (98, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 26, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (99, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 26, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (100, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 26, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (101, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 27, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (102, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 27, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (103, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 27, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (104, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 27, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (105, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 28, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (106, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 28, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (107, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 28, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (108, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 28, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (109, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 29, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (110, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 29, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (111, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 29, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (112, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 29, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (113, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 30, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (114, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 30, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (115, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 30, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (116, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 30, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (117, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 31, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (118, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 31, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (119, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 31, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (120, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 31, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (121, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 32, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (122, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 32, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (123, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 32, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (124, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 32, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (125, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 33, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (126, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 33, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (127, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 33, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (128, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 33, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (129, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 34, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (130, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 34, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (131, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 34, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (132, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 34, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (133, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 35, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (134, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 35, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (135, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 35, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (136, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 35, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (137, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 36, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (138, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 36, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (139, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 36, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (140, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 36, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (141, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 37, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (142, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 37, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (143, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 37, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (144, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 37, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (145, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 38, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (146, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 38, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (147, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 38, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (148, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 38, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (149, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 39, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (150, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 39, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (151, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 39, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (152, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 39, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (153, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 40, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (154, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 40, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (155, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 40, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (156, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 40, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (157, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 41, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (158, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 41, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (159, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 41, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (160, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 41, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (161, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 42, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (162, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 42, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (163, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 42, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (164, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 42, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (165, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 43, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (166, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 43, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (167, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 43, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (168, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 43, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (169, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 44, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (97, 5, 2, 6, '2019-11-30', '2019-10-10', false, true, 'Document_Region du Littoral__ListeSyPTAAn._97_.pdf', 'application/pdf', true, NULL, 0, '-', '-', false, false, 1, 26, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (170, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 44, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (171, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 44, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (172, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 44, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (173, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 45, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (174, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 45, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (175, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 45, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (176, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 45, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (177, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 46, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (178, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 46, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (179, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 46, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (180, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 46, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (181, 5, 2, 6, '2019-11-30', NULL, false, false, '-', '-', true, NULL, 0, '-', '-', false, false, 0, 47, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (182, 6, 1, 6, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 47, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (183, 7, 3, 2, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 47, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (184, 8, 5, 5, NULL, NULL, false, false, '-', '-', false, NULL, 0, '-', '-', false, false, 0, 47, NULL);
INSERT INTO programmation (idprogrammation, idetapeprojet, iddocument, idacteur, dateprevisionnel, daterealisation, valide, envoye, chemin, typefichier, active, date_validation, retard, observation, observationarchivee, observee, observationvalidee, conteur, idprojetservice, observationutilisateur) VALUES (93, 5, 2, 6, '2019-11-30', '2019-10-10', false, true, 'Document_Region du Littoral__fiche_presence (1)._93_.pdf', 'application/pdf', true, NULL, 0, '-', '-', false, false, 1, 25, NULL);


--
-- TOC entry 2324 (class 0 OID 127890)
-- Dependencies: 196
-- Data for Name: projet; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO projet (idprojet, idperiode, nom, etat, cloture) VALUES (1, 9, 'Projet Traitement des Factures : Littoral Octobre 2019', true, false);
INSERT INTO projet (idprojet, idperiode, nom, etat, cloture) VALUES (2, 10, 'Projet Traitement des Factures : Littoral Novembre 2019', true, false);


--
-- TOC entry 2325 (class 0 OID 127894)
-- Dependencies: 197
-- Data for Name: projetservice; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (1, 1, 28);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (2, 1, 6);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (3, 1, 7);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (4, 1, 8);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (5, 1, 9);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (6, 1, 10);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (7, 1, 11);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (8, 1, 12);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (9, 1, 13);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (10, 1, 14);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (11, 1, 15);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (12, 1, 16);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (13, 1, 17);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (14, 1, 18);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (15, 1, 19);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (16, 1, 20);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (17, 1, 21);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (18, 1, 22);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (19, 1, 23);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (20, 1, 24);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (21, 1, 25);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (22, 1, 26);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (23, 1, 27);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (24, 1, 3);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (25, 2, 28);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (26, 2, 6);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (27, 2, 7);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (28, 2, 8);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (29, 2, 9);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (30, 2, 10);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (31, 2, 11);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (32, 2, 12);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (33, 2, 13);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (34, 2, 14);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (35, 2, 15);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (36, 2, 16);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (37, 2, 17);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (38, 2, 18);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (39, 2, 19);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (40, 2, 20);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (41, 2, 21);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (42, 2, 22);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (43, 2, 23);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (44, 2, 24);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (45, 2, 25);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (46, 2, 26);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (47, 2, 27);
INSERT INTO projetservice (idprojetservice, idprojet, idservice) VALUES (48, 2, 3);


--
-- TOC entry 2326 (class 0 OID 127897)
-- Dependencies: 198
-- Data for Name: service; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (2, 3, 'DS_1', 'DISTRICT_1', '2019-06-12', 'RESPONSABLE DISTRICT_1', 1, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (1, 2, 'R_NORD', 'NORD', '2019-06-19', 'Mr MAMADOU', 0, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (3, 4, 'CTN', 'CTN PBF', '2019-06-27', 'NDIFOR', 0, true, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (4, 7, 'DISTRICT_2', 'DISTRICT_2', '2019-07-31', 'Responsable D2', 1, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (6, 12, 'DS-LT-ABO', 'DS-LT-ABO', '2019-09-05', 'Directeur HD Abo', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (7, 13, 'DS-LT-BOKO', 'DS-LT-BOKO', '2019-09-06', 'DS-LT- Directeur HD BOKO', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (8, 14, 'DS-LT-BONASAMA', 'DS-LT-BONASAMA', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (9, 15, 'DS-LT-CITE PALMIERS', 'DS-LT-CITE PALMIERS', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (10, 16, 'DS-LT-DIBOMBARI', 'DS-LT-DIBOMBARI', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (11, 17, 'DS-LT-EDEA', 'DS-LT-EDEA', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (12, 18, 'DS-LT-JAPOMA', 'DS-LT-JAPOMA', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (13, 19, 'DS-LT-LOGBABA', 'DS-LT-LOGBABA', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (14, 20, 'DS-LT-LOUM', 'DS-LT-LOUM', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (15, 21, 'DS-LT-MANJO', 'DS-LT-MANJO', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (16, 22, 'DS-LT-MANOKA', 'DS-LT-MBANGA', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (17, 23, 'DS-LT-MBANGUE', 'DS-LT-MBANGUE', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (18, 24, 'DS-LT-MELONG', 'DS-LT-MELONG', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (19, 25, 'DS-LT-NDOM', 'DS-LT-NDOM', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (20, 26, 'DS-LT-NEW BELL', 'DS-LT-NEW BELL', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (21, 27, 'DS-LT-NGAMBE', 'DS-LT-NGAMBE', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (22, 28, 'DS-LT-NJOMBE PENJA', 'DS-LT-NJOMBE PENJA', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (23, 29, 'DS-LT-NKONDJOCK', 'DS-LT-NKONDJOCK', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (24, 30, 'DS-LT-NKONGSAMBA', 'DS-LT-NKONGSAMBA', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (25, 31, 'DS-LT-NYLON', 'DS-LT-NYLON', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (26, 32, 'DS-LT-POUMA', 'DS-LT-POUMA', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (27, 33, 'DS-LT-YABASSI', 'DS-LT-YABASSI', '2019-09-05', '-', 5, false, false);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (28, 34, 'ACV-LT', 'ACV Littoral', '2019-09-05', 'Dr Nogbé', 5, false, true);
INSERT INTO service (idservice, idaddresse, code, nom, datecreation, responsable, idparent, central, regional) VALUES (5, 11, 'Region du Littoral', 'Region du Littoral', '2019-09-05', '-', 0, false, true);


--
-- TOC entry 2327 (class 0 OID 127904)
-- Dependencies: 199
-- Data for Name: traitement; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2328 (class 0 OID 127907)
-- Dependencies: 200
-- Data for Name: typerappel; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2329 (class 0 OID 127913)
-- Dependencies: 201
-- Data for Name: utilisateur; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO utilisateur (idutilisateur, nom, prenom, login, password, datecreation, etat, photo, idacteur) VALUES (1, 'Kenne ', 'Gervais', 'admin', '0DPiKuNIrrVmD8IUCuw1hQxNqZc=', NULL, true, 'user_avatar.png', 2);
INSERT INTO utilisateur (idutilisateur, nom, prenom, login, password, datecreation, etat, photo, idacteur) VALUES (2, NULL, NULL, 'district1', 'CT75BzR37R/hLBqYTu5zZZgFpro=', '2019-07-04', true, 'user_avatar.png', 1);
INSERT INTO utilisateur (idutilisateur, nom, prenom, login, password, datecreation, etat, photo, idacteur) VALUES (3, NULL, NULL, 'district2', 'KE0GmAzgCy793rpVbDz8MMEh5eM=', '2019-07-22', true, 'user_avatar.png', 3);
INSERT INTO utilisateur (idutilisateur, nom, prenom, login, password, datecreation, etat, photo, idacteur) VALUES (4, NULL, NULL, 'littoral', '14T5ZeBbKLK3wAJM9kFUCOnS7nQ=', '2019-09-26', true, 'user_avatar.png', 6);


--
-- TOC entry 2087 (class 2606 OID 127920)
-- Name: pk_acteur; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY acteur
    ADD CONSTRAINT pk_acteur PRIMARY KEY (idacteur);


--
-- TOC entry 2090 (class 2606 OID 127922)
-- Name: pk_addresse; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY addresse
    ADD CONSTRAINT pk_addresse PRIMARY KEY (idaddresse);


--
-- TOC entry 2095 (class 2606 OID 127924)
-- Name: pk_alerte; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY alerte
    ADD CONSTRAINT pk_alerte PRIMARY KEY (idalerte);


--
-- TOC entry 2098 (class 2606 OID 127926)
-- Name: pk_document; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY document
    ADD CONSTRAINT pk_document PRIMARY KEY (iddocument);


--
-- TOC entry 2102 (class 2606 OID 127928)
-- Name: pk_etape; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY etape
    ADD CONSTRAINT pk_etape PRIMARY KEY (idetape);


--
-- TOC entry 2107 (class 2606 OID 127930)
-- Name: pk_etapeprojet; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY etapeprojet
    ADD CONSTRAINT pk_etapeprojet PRIMARY KEY (idetapeprojet);


--
-- TOC entry 2110 (class 2606 OID 127932)
-- Name: pk_menu; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT pk_menu PRIMARY KEY (idmenu);


--
-- TOC entry 2113 (class 2606 OID 127934)
-- Name: pk_modealerte; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY modealerte
    ADD CONSTRAINT pk_modealerte PRIMARY KEY (idmodealerte);


--
-- TOC entry 2117 (class 2606 OID 127936)
-- Name: pk_mouchard; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mouchard
    ADD CONSTRAINT pk_mouchard PRIMARY KEY (idmouchard);


--
-- TOC entry 2119 (class 2606 OID 127938)
-- Name: pk_parametrage; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY parametrage
    ADD CONSTRAINT pk_parametrage PRIMARY KEY (idparametrage);


--
-- TOC entry 2122 (class 2606 OID 127940)
-- Name: pk_periode; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY periode
    ADD CONSTRAINT pk_periode PRIMARY KEY (idperiode);


--
-- TOC entry 2127 (class 2606 OID 127942)
-- Name: pk_piecejointes; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY piecejointes
    ADD CONSTRAINT pk_piecejointes PRIMARY KEY (idpiecejointes);


--
-- TOC entry 2131 (class 2606 OID 127944)
-- Name: pk_planningrappel; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY planningrappel
    ADD CONSTRAINT pk_planningrappel PRIMARY KEY (idplanningrappel);


--
-- TOC entry 2136 (class 2606 OID 127946)
-- Name: pk_privilege; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege
    ADD CONSTRAINT pk_privilege PRIMARY KEY (idprivilege);


--
-- TOC entry 2143 (class 2606 OID 127948)
-- Name: pk_programmation; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY programmation
    ADD CONSTRAINT pk_programmation PRIMARY KEY (idprogrammation);


--
-- TOC entry 2147 (class 2606 OID 127950)
-- Name: pk_projet; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY projet
    ADD CONSTRAINT pk_projet PRIMARY KEY (idprojet);


--
-- TOC entry 2152 (class 2606 OID 127952)
-- Name: pk_projetservice; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY projetservice
    ADD CONSTRAINT pk_projetservice PRIMARY KEY (idprojetservice);


--
-- TOC entry 2156 (class 2606 OID 127954)
-- Name: pk_service; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY service
    ADD CONSTRAINT pk_service PRIMARY KEY (idservice);


--
-- TOC entry 2161 (class 2606 OID 127956)
-- Name: pk_traitement; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY traitement
    ADD CONSTRAINT pk_traitement PRIMARY KEY (idtraitement);


--
-- TOC entry 2164 (class 2606 OID 127958)
-- Name: pk_typerappel; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY typerappel
    ADD CONSTRAINT pk_typerappel PRIMARY KEY (idtyperappel);


--
-- TOC entry 2168 (class 2606 OID 127960)
-- Name: pk_utilisateur; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY utilisateur
    ADD CONSTRAINT pk_utilisateur PRIMARY KEY (idutilisateur);


--
-- TOC entry 2082 (class 1259 OID 127961)
-- Name: acteur_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX acteur_pk ON acteur USING btree (idacteur);


--
-- TOC entry 2088 (class 1259 OID 127962)
-- Name: addresse_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX addresse_pk ON addresse USING btree (idaddresse);


--
-- TOC entry 2091 (class 1259 OID 127963)
-- Name: alerte_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX alerte_pk ON alerte USING btree (idalerte);


--
-- TOC entry 2138 (class 1259 OID 127964)
-- Name: association10_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association10_fk ON programmation USING btree (iddocument);


--
-- TOC entry 2139 (class 1259 OID 127965)
-- Name: association12_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association12_fk ON programmation USING btree (idetapeprojet);


--
-- TOC entry 2140 (class 1259 OID 127966)
-- Name: association13_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association13_fk ON programmation USING btree (idacteur);


--
-- TOC entry 2128 (class 1259 OID 127967)
-- Name: association14_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association14_fk ON planningrappel USING btree (idtyperappel);


--
-- TOC entry 2129 (class 1259 OID 127968)
-- Name: association15_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association15_fk ON planningrappel USING btree (idmodealerte);


--
-- TOC entry 2092 (class 1259 OID 127969)
-- Name: association16_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association16_fk ON alerte USING btree (idtyperappel);


--
-- TOC entry 2093 (class 1259 OID 127970)
-- Name: association17_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association17_fk ON alerte USING btree (idmodealerte);


--
-- TOC entry 2133 (class 1259 OID 127971)
-- Name: association18_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association18_fk ON privilege USING btree (idmenu);


--
-- TOC entry 2134 (class 1259 OID 127972)
-- Name: association19_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association19_fk ON privilege USING btree (idutilisateur);


--
-- TOC entry 2103 (class 1259 OID 127973)
-- Name: association1_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association1_fk ON etapeprojet USING btree (idprojet);


--
-- TOC entry 2123 (class 1259 OID 127974)
-- Name: association20_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association20_fk ON piecejointes USING btree (idprogrammation);


--
-- TOC entry 2124 (class 1259 OID 127975)
-- Name: association21_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association21_fk ON piecejointes USING btree (idtraitement);


--
-- TOC entry 2114 (class 1259 OID 127976)
-- Name: association22_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association22_fk ON mouchard USING btree (idutilisateur);


--
-- TOC entry 2145 (class 1259 OID 127977)
-- Name: association3_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association3_fk ON projet USING btree (idperiode);


--
-- TOC entry 2149 (class 1259 OID 127978)
-- Name: association4_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association4_fk ON projetservice USING btree (idprojet);


--
-- TOC entry 2150 (class 1259 OID 127979)
-- Name: association5_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association5_fk ON projetservice USING btree (idservice);


--
-- TOC entry 2154 (class 1259 OID 127980)
-- Name: association6_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association6_fk ON service USING btree (idaddresse);


--
-- TOC entry 2083 (class 1259 OID 127981)
-- Name: association7_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association7_fk ON acteur USING btree (idaddresse);


--
-- TOC entry 2158 (class 1259 OID 127982)
-- Name: association8_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association8_fk ON traitement USING btree (idetapeprojet);


--
-- TOC entry 2159 (class 1259 OID 127983)
-- Name: association9_fk; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX association9_fk ON traitement USING btree (idacteur);


--
-- TOC entry 2096 (class 1259 OID 127984)
-- Name: document_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX document_pk ON document USING btree (iddocument);


--
-- TOC entry 2099 (class 1259 OID 127985)
-- Name: etape_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX etape_pk ON etape USING btree (idetape);


--
-- TOC entry 2104 (class 1259 OID 127986)
-- Name: etapeprojet_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX etapeprojet_pk ON etapeprojet USING btree (idetapeprojet);


--
-- TOC entry 2166 (class 1259 OID 127987)
-- Name: fki_acteur_utilisateur; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_acteur_utilisateur ON utilisateur USING btree (idacteur);


--
-- TOC entry 2084 (class 1259 OID 127988)
-- Name: fki_document_acteur; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_document_acteur ON acteur USING btree (iddocument);


--
-- TOC entry 2100 (class 1259 OID 127989)
-- Name: fki_document_etape; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_document_etape ON etape USING btree (iddocument);


--
-- TOC entry 2105 (class 1259 OID 127990)
-- Name: fki_etape_projet; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_etape_projet ON etapeprojet USING btree (idetape);


--
-- TOC entry 2141 (class 1259 OID 127991)
-- Name: fki_projetservice_programmation; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_projetservice_programmation ON programmation USING btree (idprojetservice);


--
-- TOC entry 2085 (class 1259 OID 127992)
-- Name: fki_service_acteur; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX fki_service_acteur ON acteur USING btree (idservice);


--
-- TOC entry 2108 (class 1259 OID 127993)
-- Name: menu_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX menu_pk ON menu USING btree (idmenu);


--
-- TOC entry 2111 (class 1259 OID 127994)
-- Name: modealerte_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX modealerte_pk ON modealerte USING btree (idmodealerte);


--
-- TOC entry 2115 (class 1259 OID 127995)
-- Name: mouchard_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX mouchard_pk ON mouchard USING btree (idmouchard);


--
-- TOC entry 2120 (class 1259 OID 127996)
-- Name: periode_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX periode_pk ON periode USING btree (idperiode);


--
-- TOC entry 2125 (class 1259 OID 127997)
-- Name: piecejointes_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX piecejointes_pk ON piecejointes USING btree (idpiecejointes);


--
-- TOC entry 2132 (class 1259 OID 127998)
-- Name: planningrappel_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX planningrappel_pk ON planningrappel USING btree (idplanningrappel);


--
-- TOC entry 2137 (class 1259 OID 127999)
-- Name: privilege_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX privilege_pk ON privilege USING btree (idprivilege);


--
-- TOC entry 2144 (class 1259 OID 128000)
-- Name: programmation_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX programmation_pk ON programmation USING btree (idprogrammation);


--
-- TOC entry 2148 (class 1259 OID 128001)
-- Name: projet_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX projet_pk ON projet USING btree (idprojet);


--
-- TOC entry 2153 (class 1259 OID 128002)
-- Name: projetservice_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX projetservice_pk ON projetservice USING btree (idprojetservice);


--
-- TOC entry 2157 (class 1259 OID 128003)
-- Name: service_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX service_pk ON service USING btree (idservice);


--
-- TOC entry 2162 (class 1259 OID 128004)
-- Name: traitement_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX traitement_pk ON traitement USING btree (idtraitement);


--
-- TOC entry 2165 (class 1259 OID 128005)
-- Name: typerappel_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX typerappel_pk ON typerappel USING btree (idtyperappel);


--
-- TOC entry 2169 (class 1259 OID 128006)
-- Name: utilisateur_pk; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX utilisateur_pk ON utilisateur USING btree (idutilisateur);


--
-- TOC entry 2170 (class 2606 OID 128007)
-- Name: fk_acteur_associati_addresse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY acteur
    ADD CONSTRAINT fk_acteur_associati_addresse FOREIGN KEY (idaddresse) REFERENCES addresse(idaddresse) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2194 (class 2606 OID 128012)
-- Name: fk_acteur_utilisateur; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY utilisateur
    ADD CONSTRAINT fk_acteur_utilisateur FOREIGN KEY (idacteur) REFERENCES acteur(idacteur);


--
-- TOC entry 2172 (class 2606 OID 128017)
-- Name: fk_alerte_associati_modealer; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY alerte
    ADD CONSTRAINT fk_alerte_associati_modealer FOREIGN KEY (idmodealerte) REFERENCES modealerte(idmodealerte) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2173 (class 2606 OID 128022)
-- Name: fk_alerte_associati_typerapp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY alerte
    ADD CONSTRAINT fk_alerte_associati_typerapp FOREIGN KEY (idtyperappel) REFERENCES typerappel(idtyperappel) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2174 (class 2606 OID 128027)
-- Name: fk_document_etape; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY etape
    ADD CONSTRAINT fk_document_etape FOREIGN KEY (iddocument) REFERENCES document(iddocument);


--
-- TOC entry 2175 (class 2606 OID 128032)
-- Name: fk_etape_projet; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY etapeprojet
    ADD CONSTRAINT fk_etape_projet FOREIGN KEY (idetape) REFERENCES etape(idetape);


--
-- TOC entry 2176 (class 2606 OID 128037)
-- Name: fk_etapepro_associati_projet; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY etapeprojet
    ADD CONSTRAINT fk_etapepro_associati_projet FOREIGN KEY (idprojet) REFERENCES projet(idprojet) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2177 (class 2606 OID 128042)
-- Name: fk_mouchard_associati_utilisat; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mouchard
    ADD CONSTRAINT fk_mouchard_associati_utilisat FOREIGN KEY (idutilisateur) REFERENCES utilisateur(idutilisateur) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2178 (class 2606 OID 128047)
-- Name: fk_piecejoi_associati_programm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY piecejointes
    ADD CONSTRAINT fk_piecejoi_associati_programm FOREIGN KEY (idprogrammation) REFERENCES programmation(idprogrammation) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2179 (class 2606 OID 128052)
-- Name: fk_piecejoi_associati_traiteme; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY piecejointes
    ADD CONSTRAINT fk_piecejoi_associati_traiteme FOREIGN KEY (idtraitement) REFERENCES traitement(idtraitement) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2180 (class 2606 OID 128057)
-- Name: fk_planning_associati_modealer; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY planningrappel
    ADD CONSTRAINT fk_planning_associati_modealer FOREIGN KEY (idmodealerte) REFERENCES modealerte(idmodealerte) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2181 (class 2606 OID 128062)
-- Name: fk_planning_associati_typerapp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY planningrappel
    ADD CONSTRAINT fk_planning_associati_typerapp FOREIGN KEY (idtyperappel) REFERENCES typerappel(idtyperappel) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2182 (class 2606 OID 128067)
-- Name: fk_privileg_associati_menu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege
    ADD CONSTRAINT fk_privileg_associati_menu FOREIGN KEY (idmenu) REFERENCES menu(idmenu) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2183 (class 2606 OID 128072)
-- Name: fk_privileg_associati_utilisat; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY privilege
    ADD CONSTRAINT fk_privileg_associati_utilisat FOREIGN KEY (idutilisateur) REFERENCES utilisateur(idutilisateur) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2184 (class 2606 OID 128077)
-- Name: fk_programm_associati_acteur; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY programmation
    ADD CONSTRAINT fk_programm_associati_acteur FOREIGN KEY (idacteur) REFERENCES acteur(idacteur) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2185 (class 2606 OID 128082)
-- Name: fk_programm_associati_document; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY programmation
    ADD CONSTRAINT fk_programm_associati_document FOREIGN KEY (iddocument) REFERENCES document(iddocument) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2186 (class 2606 OID 128087)
-- Name: fk_programm_associati_etapepro; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY programmation
    ADD CONSTRAINT fk_programm_associati_etapepro FOREIGN KEY (idetapeprojet) REFERENCES etapeprojet(idetapeprojet) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2188 (class 2606 OID 128092)
-- Name: fk_projet_associati_periode; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY projet
    ADD CONSTRAINT fk_projet_associati_periode FOREIGN KEY (idperiode) REFERENCES periode(idperiode) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2189 (class 2606 OID 128097)
-- Name: fk_projetse_associati_projet; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY projetservice
    ADD CONSTRAINT fk_projetse_associati_projet FOREIGN KEY (idprojet) REFERENCES projet(idprojet) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2190 (class 2606 OID 128102)
-- Name: fk_projetse_associati_service; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY projetservice
    ADD CONSTRAINT fk_projetse_associati_service FOREIGN KEY (idservice) REFERENCES service(idservice) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2187 (class 2606 OID 128107)
-- Name: fk_projetservice_programmation; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY programmation
    ADD CONSTRAINT fk_projetservice_programmation FOREIGN KEY (idprojetservice) REFERENCES projetservice(idprojetservice);


--
-- TOC entry 2171 (class 2606 OID 128112)
-- Name: fk_service_acteur; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY acteur
    ADD CONSTRAINT fk_service_acteur FOREIGN KEY (idservice) REFERENCES service(idservice);


--
-- TOC entry 2191 (class 2606 OID 128117)
-- Name: fk_service_associati_addresse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY service
    ADD CONSTRAINT fk_service_associati_addresse FOREIGN KEY (idaddresse) REFERENCES addresse(idaddresse) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2192 (class 2606 OID 128122)
-- Name: fk_traiteme_associati_acteur; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY traitement
    ADD CONSTRAINT fk_traiteme_associati_acteur FOREIGN KEY (idacteur) REFERENCES acteur(idacteur) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2193 (class 2606 OID 128127)
-- Name: fk_traiteme_associati_etapepro; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY traitement
    ADD CONSTRAINT fk_traiteme_associati_etapepro FOREIGN KEY (idetapeprojet) REFERENCES etapeprojet(idetapeprojet) ON UPDATE RESTRICT ON DELETE RESTRICT;


-- Completed on 2020-04-03 15:28:16

--
-- PostgreSQL database dump complete
--

