package dao.service.serviceI;

import model.Programmation;
import model.UserCompte;
import model.Utilisateur;

public interface IUserService {

    int signUp(UserCompte userCompte);

    Utilisateur login(String name, String prenom, String email, String password);

    int addProgrammation(Programmation pg);

    int delProgrammation(Programmation pg);
}
