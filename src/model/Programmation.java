package model;

import model.enums.DomaineMedical;

import java.util.Date;

/* un patient peut faire une programmation*/
public class Programmation {

    private int programmation_id;
    private String code;

    //--patient info--//
    private String codePatient;
    private String namePatient;
    private String prenomPatient;

    //--//
    private DomaineMedical domaineMedical;
    private Date date;

    //affichage des 2 du bas a l action du select sur les 2 du haut
    //on va simuller une recherche des hospitaux et medecin
    private String hospital;
    private String medecinFullName;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodePatient() {
        return codePatient;
    }

    public void setCodePatient(String codePatient) {
        this.codePatient = codePatient;
    }

    public String getNamePatient() {
        return namePatient;
    }

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }

    public String getPrenomPatient() {
        return prenomPatient;
    }

    public void setPrenomPatient(String prenomPatient) {
        this.prenomPatient = prenomPatient;
    }

    public DomaineMedical getDomaineMedical() {
        return domaineMedical;
    }

    public void setDomaineMedical(DomaineMedical domaineMedical) {
        this.domaineMedical = domaineMedical;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getMedecinFullName() {
        return medecinFullName;
    }

    public void setMedecinFullName(String medecinFullName) {
        this.medecinFullName = medecinFullName;
    }

    public int getProgrammation_id() {
        return programmation_id;
    }

    public void setProgrammation_id(int programmation_id) {
        this.programmation_id = programmation_id;
    }
}
