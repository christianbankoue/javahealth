package dao.service.serviceI;

import model.Programmation;
import model.UserCompte;
import model.Utilisateur;

import java.util.List;

public interface IUserService {

    int signUp(UserCompte userCompte);

    Utilisateur login(String name, String prenom, String email, String password);

    int addProgrammation(Programmation pg);

    List<Programmation> getAllProgrammation(String codePatient, String namePatient, String prenomPatient);

    Programmation getProgrammation(int id);

    int delProgrammation(Programmation pg);
}
