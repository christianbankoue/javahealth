package dao.service.serviceI;

import Domaine.Patient;

public interface IPatient {
    public int add(Patient patient);
    public int del(Patient patient);
    public Patient get(int id);
}
