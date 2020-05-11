package Base.donne;

import sample.Consultation;
import sample.Medecin;

import java.util.List;

public interface IMedecin {
    public List<Consultation> getConsultMedecin(int id);
    public Medecin get(int id);
    public int add(Medecin medecin);
    public int delecte(Medecin medecin);
}
