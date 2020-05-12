package sample;

import Domaine.Medecin;
import Domaine.Patient;

public class Consultation {
    private int id;
    private String password;
    private Medecin medecin;
    private Patient patient;

    public Consultation() {

    }

    public Consultation(int id, String password, Medecin medecin, Patient patient) {
        this.id = id;
        this.password = password;
        this.medecin = medecin;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
