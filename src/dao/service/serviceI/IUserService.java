package dao.service.serviceI;

import model.*;

import java.util.List;

public interface IUserService {

    int signUp(UserCompte userCompte);
    Utilisateur login(String name, String prenom, String email, String password);
    List<Utilisateur> getUtilisateurByTyPeMedical(String pmedical);

    int addProgrammation(Programmation pg);
    List<Programmation> getAllProgrammation(String codePatient, String namePatient, String prenomPatient);
    List<Programmation> getAllProgrammationByMedecin(String medecinFullName);
    Programmation getProgrammation(int id);

    int addConsultation(Consultation cs);
    List<Consultation> getConsultationByCodeUnique( String codeUniqueMedecin);
    Consultation getCstByCodeUAndDate(String codeUniquePatient, String codeUniqueMedecin, java.sql.Timestamp sqlDate);
    Consultation getConsultationById(int consultation_id);

    int addRecette(Recette recette);
    Recette getRecetteByMedecinAndDateAndPharnacien(int medecinId, int pharmacienId, java.sql.Timestamp sqlDate);
    int addMaladie(Maladie maladie);
    Maladie getMaladieByConsultationId(int consultation_id);

}
