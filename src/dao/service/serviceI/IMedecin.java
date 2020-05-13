package dao.service.serviceI;

import model.Consultation;
import model.Utilisateur;

import java.util.List;

public interface IMedecin {
    public List<Consultation> getConsultMedecin(int id);
    public Utilisateur get(int id);
    public int add(Utilisateur medecin);
    public int del(Utilisateur medecin);
}
