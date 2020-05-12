package Domaine.mapper;

import Domaine.Utilisateur;

public class UserCompte {
    protected int id;
    private String name;
    private String prenom;
    private String email;
    private String password;
    private String codeu;


    public String getCodeu() {
        return codeu;
    }

    public void setCodeu(String codeu) {
        this.codeu = codeu;
    }

    public UserCompte(Utilisateur utilisateur) {
        this.setName(utilisateur.getName());
        this.setPrenom(utilisateur.getPrenom());
        this.setEmail(utilisateur.getEmail());
        this.setPassword(utilisateur.getPassword());
        this.setCodeu(utilisateur.getCodeu());



    }



    public UserCompte(String name, String prenom, String email, String password, String codeu) {
        this.setName(name);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setPassword(password);
        this.setCodeu(codeu);




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
}
