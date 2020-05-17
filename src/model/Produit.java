package model;

public class Produit {

    private int produit_id;
    private String label;
    private String detail;

    public Produit() {
    }

    public Produit(String label, String detail) {
        this.label = label;
        this.detail = detail;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
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
}
