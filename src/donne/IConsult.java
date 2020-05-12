package donne;

import sample.Consultation;

import java.util.List;

public interface IConsult {
    public int add(Consultation rv);
    public int  update(Consultation rv);
    public List<Consultation> getAll();
}
