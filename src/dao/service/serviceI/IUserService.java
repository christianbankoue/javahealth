package dao.service.serviceI;

import model.*;

import java.util.List;

public interface IUserService {

    int signUp(UserCompte userCompte);
    Utilisateur login(String name, String prenom, String email, String password);
    List<Utilisateur> getUtilisateurByTyPeMedical(String pmedical);
    List<Utilisateur> getUtilisateurByRoleId(int roleId);
    Utilisateur getUtilisateurById(int id);
    List<Utilisateur> getAllUser();
    int deleteUtilisateur(int usercompte_id);

    int addProgrammation(Programmation pg);
    List<Programmation> getAllProgrammation(String codePatient, String namePatient, String prenomPatient);
    List<Programmation> getAllProgrammationByMedecin(String medecinFullName);
    Programmation getProgrammation(int id);

    int addConsultation(Consultation cs, int hour, int min);
    List<Consultation> getConsultationByCodeUnique( String codeUniqueMedecin);
    Consultation getCstByCodeUAndDate(String codeUniquePatient, String codeUniqueMedecin, java.sql.Timestamp sqlDate);
    Consultation getConsultationById(int consultation_id);

    int addRecette(Recette recette);
    Recette getRecetteByMedecinAndDateAndPharnacien(int medecinId, int pharmacienId, java.sql.Timestamp sqlDate);
    List<Recette> getRecettesByPharmacien(int pharmacienId);
    int updateRecettesById(int recette_id);
    int addMaladie(Maladie maladie);
    Maladie getMaladieByConsultationId(int consultation_id);

    List<Produit> getAllProduit();
    int deleteProduit(int produit_id);

    Recette getRecetteById(int recette_id);
}
