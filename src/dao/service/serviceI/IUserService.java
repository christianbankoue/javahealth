package dao.service.serviceI;

import model.UserCompte;
import model.Utilisateur;

public interface IUserService {

    int signUp(UserCompte userCompte);

    Utilisateur login(String name, String prenom, String email, String password,String roles);
}
