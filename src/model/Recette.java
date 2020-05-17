package model;

import java.time.LocalDateTime;

//STEP 3
public class Recette {

    private int recette_id;
    private String label;
    private String detail;

    private LocalDateTime date;
    private int medecin_id;
    private int pharmacien_id;
    private int medicamentDelivrer;
    private int produit_id;

    public Recette() {
    }

    public Recette(String label, String detail, int medecin_id, int pharmacien_id, int produit_id, LocalDateTime date) {
        this.label = label;
        this.detail = detail;
        this.medecin_id = medecin_id;
        this.produit_id = produit_id;
        this.pharmacien_id = pharmacien_id;
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getRecette_id() {
        return recette_id;
    }

    public void setRecette_id(int recette_id) {
        this.recette_id = recette_id;
    }

    public int getMedecin_id() {
        return medecin_id;
    }

    public void setMedecin_id(int medecin_id) {
        this.medecin_id = medecin_id;
    }

    public int getPharmacien_id() {
        return pharmacien_id;
    }

    public void setPharmacien_id(int pharmacien_id) {
        this.pharmacien_id = pharmacien_id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getMedicamentDelivrer() {
        return medicamentDelivrer;
    }

    public void setMedicamentDelivrer(int medicamentDelivrer) {
        this.medicamentDelivrer = medicamentDelivrer;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }
}
