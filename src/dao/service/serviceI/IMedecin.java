package dao.service.serviceI;

import sample.Consultation;
import Domaine.Medecin;

import java.util.List;

public interface IMedecin {
    public List<Consultation> getConsultMedecin(int id);
    public Medecin get(int id);
    public int add(Medecin medecin);
    public int del(Medecin medecin);
}
