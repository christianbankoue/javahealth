package dao.service.serviceI;

import model.Consultation;

import java.util.List;

public interface IConsult {
    public int add(Consultation rv);
    public int update(Consultation rv);
    public List<Consultation> getAll();
}
