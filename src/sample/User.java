package sample;

public class User {
    private int id;
    private String email;
    private String password;
    private Medecin medecin = new Medecin();
    private Patient patient = new Patient();
    private Administrator administrator = new Administrator();
    private Pharmacien pharmacien = new Pharmacien();
    private Fournisseur fournisseur = new Fournisseur();

    public User() {
    }

    public User(int id, String email, String password, Medecin medecin, Patient patient, Administrator administrator, Pharmacien pharmacien, Fournisseur fournisseur) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.medecin = medecin;
        this.patient = patient;
        this.administrator = administrator;
        this.pharmacien = pharmacien;
        this.fournisseur = fournisseur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public Pharmacien getPharmacien() {
        return pharmacien;
    }

    public void setPharmacien(Pharmacien pharmacien) {
        this.pharmacien = pharmacien;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }
}
