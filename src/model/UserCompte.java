package model;

public class UserCompte {

    protected int id;
    private String name;
    private String prenom;
    private String email;
    private String password;
    private String codeUnique;
    private String typePersonnelMedical;

    private boolean admin;
    private boolean fournisseur;
    private boolean pharmatien;
    private boolean patient;
    private boolean personnelmedicale;

    public UserCompte(String name, String prenom, String email, String password, String codeUnique) {
        this.setName(name);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setPassword(password);
        this.setCodeUnique(codeUnique);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodeUnique() {
        return codeUnique;
    }

    public void setCodeUnique(String codeUnique) {
        this.codeUnique = codeUnique;
    }


    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(boolean fournisseur) {
        this.fournisseur = fournisseur;
    }

    public boolean isPharmatien() {
        return pharmatien;
    }

    public void setPharmatien(boolean pharmatien) {
        this.pharmatien = pharmatien;
    }

    public boolean isPatient() {
        return patient;
    }

    public void setPatient(boolean patient) {
        this.patient = patient;
    }

    public boolean isPersonnelmedicale() {
        return personnelmedicale;
    }

    public void setPersonnelmedicale(boolean personnelmedicale) {
        this.personnelmedicale = personnelmedicale;
    }

    public String getTypePersonnelMedical() {
        return typePersonnelMedical;
    }

    public void setTypePersonnelMedical(String typePersonnelMedical) {
        this.typePersonnelMedical = typePersonnelMedical;
    }
}
