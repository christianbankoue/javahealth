package model;

import model.enums.FournisseurEnum;
import model.enums.PersonnelMedicalEnum;
import model.enums.RoleEnum;

public class Utilisateur {

    protected int id;
    protected String name;
    protected String prenom;
    protected String email;
    protected String password;
    protected String codeUnique;
    protected int inactive;

    private RoleEnum role;
    private FournisseurEnum fournisseur;
    private PersonnelMedicalEnum personnelMedical;

    public Utilisateur() {
    }

    public Utilisateur(int id, String name, String prenom, String email, String password, String codeUnique) {
        this.id = id;
        this.name = name;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.codeUnique = codeUnique;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeUnique() {
        return codeUnique;
    }

    public void setCodeUnique(String codeUnique) {
        this.codeUnique = codeUnique;
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

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public FournisseurEnum getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(FournisseurEnum fournisseur) {
        this.fournisseur = fournisseur;
    }

    public PersonnelMedicalEnum getPersonnelMedical() {
        return personnelMedical;
    }

    public void setPersonnelMedical(PersonnelMedicalEnum personnelMedical) {
        this.personnelMedical = personnelMedical;
    }

    public int getInactive() {
        return inactive;
    }

    public void setInactive(int inactive) {
        this.inactive = inactive;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", codeUnique='" + codeUnique + '\'' +
                ", inactive=" + inactive +
                ", role=" + (role != null ? role.name() : "") +
                ", fournisseur=" + (fournisseur != null ? fournisseur.name() : "")  +
                ", personnelMedical=" + (personnelMedical != null ? personnelMedical.name() : "")  +
                '}';
    }
}
