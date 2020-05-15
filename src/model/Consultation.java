package model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//STEP 1
/* un medecin peut faire une Consultation */
public class Consultation {

    private int consultation_id;

    //--patient info--//
    // il regarde sur la programmation et reporte les info ici
    private String codeUniquePatient;
    private String namePatient;
    private String prenomPatient;

    //--medecin info--//
    private String codeUniqueMedecin;
    private String nameMedecin;
    private String prenomMedecin;

    //--//
    private Date dateVisite;

    //STEP 2
    //on valide la consultation puis on ajouter maladie ou ne rien faire
    private List<Maladie> maladies = new ArrayList<>();


    public String getCodeUniquePatient() {
        return codeUniquePatient;
    }

    public void setCodeUniquePatient(String codeUniquePatient) {
        this.codeUniquePatient = codeUniquePatient;
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

    public String getCodeUniqueMedecin() {
        return codeUniqueMedecin;
    }

    public void setCodeUniqueMedecin(String codeUniqueMedecin) {
        this.codeUniqueMedecin = codeUniqueMedecin;
    }

    public String getNameMedecin() {
        return nameMedecin;
    }

    public void setNameMedecin(String nameMedecin) {
        this.nameMedecin = nameMedecin;
    }

    public String getPrenomMedecin() {
        return prenomMedecin;
    }

    public void setPrenomMedecin(String prenomMedecin) {
        this.prenomMedecin = prenomMedecin;
    }

    public Date getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(Date dateVisite) {
        this.dateVisite = dateVisite;
    }

    public List<Maladie> getMaladies() {
        return maladies;
    }

    public void setMaladies(List<Maladie> maladies) {
        this.maladies = maladies;
    }

    public int getConsultation_id() {
        return consultation_id;
    }

    public void setConsultation_id(int consultation_id) {
        this.consultation_id = consultation_id;
    }
}
