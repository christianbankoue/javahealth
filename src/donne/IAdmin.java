package donne;

import sample.*;

public interface IAdmin {
    public int add(Administrator administrator);
    public int add(Medecin medecin);
    public int add(Patient patient);
    public int add(Pharmacien pharmacien);
    public int add(Fournisseur fournisseur);

    public int delele(Medecin medecin);
    public int delele(Patient patient);
    public int delele(Pharmacien pharmacien);
    public int delele(Fournisseur fournisseur);


}
