package model;

//STEP 2
public class Maladie {

    private int maladie_id;
    private String nom;
    private String description;
    private String consultationCode;

    //STEP 3
    //on attache une recette que l on sauvegarde
    private int recette_id;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConsultationCode() {
        return consultationCode;
    }

    public void setConsultationCode(String consultationCode) {
        this.consultationCode = consultationCode;
    }

    public int getMaladie_id() {
        return maladie_id;
    }

    public void setMaladie_id(int maladie_id) {
        this.maladie_id = maladie_id;
    }

    public int getRecette_id() {
        return recette_id;
    }

    public void setRecette_id(int recette_id) {
        this.recette_id = recette_id;
    }
}
