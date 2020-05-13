package dao.service;

import dao.service.serviceI.IMedecin;
import model.Consultation;
import model.Utilisateur;

import java.util.List;

public class MedecinImpl implements IMedecin {
    @Override
    public List<Consultation> getConsultMedecin(int id) {
        return null;
    }

    @Override
    public Utilisateur get(int id) {
        return null;
    }

    @Override
    public int add(Utilisateur medecin) {
        return 0;
    }

    @Override
    public int del(Utilisateur medecin) {
        return 0;
    }
}
