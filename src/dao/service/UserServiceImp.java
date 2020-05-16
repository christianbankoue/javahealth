package dao.service;

import dao.basededonne.DB;
import dao.service.serviceI.IUserService;
import model.*;
import model.enums.PersonnelMedicalEnum;
import model.enums.RoleEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImp implements IUserService {

    private DB db = new DB();

    public int signUp(UserCompte userCompte){

        String sql = "INSERT INTO USERCOMPTES (name, prenom, email, password, codeunique, role_id, pmedical) values (?,?,?,?,?,?,?) ";

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1,userCompte.getName());
            pstm.setString(2,userCompte.getPrenom());
            pstm.setString(3,userCompte.getEmail());
            pstm.setString(4,userCompte.getPassword());
            pstm.setString(5,userCompte.getCodeUnique());


            if(userCompte.isAdmin()){
                pstm.setInt(6, RoleEnum.ADMIN.getIdentifiant());
                pstm.setString(7, null);
            }else if(userCompte.isPersonnelmedicale() ){
                pstm.setInt(6, RoleEnum.PERSONNEL_MEDICAL.getIdentifiant());
                pstm.setString(7, userCompte.getTypePersonnelMedical());
            }else if(userCompte.isFournisseur()){
                pstm.setInt(6, RoleEnum.FOURNISSEUR.getIdentifiant());
                pstm.setString(7, null);
            }else if(userCompte.isPharmatien()){
                pstm.setInt(6, RoleEnum.PHARMACIEN.getIdentifiant());
                pstm.setString(7, null);
            }else{
                pstm.setInt(6, RoleEnum.PATIENT.getIdentifiant());
                pstm.setString(7, null);
            }

            int rs = db.executeMaj();
            return rs;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public Utilisateur login(String name, String prenom, String email, String password){

        String sql = "SELECT usercompte_id, codeUnique, role_id, pmedical FROM USERCOMPTES WHERE name = ? and prenom = ? and email = ? and password = ? ";

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1, name);
            pstm.setString(2, prenom);
            pstm.setString(3, email);
            pstm.setString(4, password);
            // pstm.setString(5, codeUnique);
            ResultSet rs = db.executeSelect();
            //int id, String name, String prenom, String email, String password, String codeUnique
            Utilisateur utilisateur;
            while (rs.next()) {
                int id = rs.getInt("usercompte_id");
                String codeUnique = rs.getString("codeUnique");
                int roleId = rs.getInt("role_id");
                String typePersonnelMedical = rs.getString("pmedical");

                utilisateur = new Utilisateur(id, name, prenom, email, password, codeUnique);
                utilisateur.setRole(RoleEnum.getRoleById(roleId));
                if(typePersonnelMedical != null){
                    utilisateur.setPersonnelMedical(PersonnelMedicalEnum.valueOf(typePersonnelMedical));
                }
                return utilisateur;
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new Utilisateur();
    }

    public List<Utilisateur> getUtilisateurByTyPeMedical(String pmedical){

        String sql = "SELECT * FROM USERCOMPTES WHERE pmedical = ? ";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1, pmedical);


            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                int id = rs.getInt("usercompte_id");
                String codeUnique = rs.getString("codeUnique");
                int roleId = rs.getInt("role_id");
                String name = rs.getString("name");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String password = rs.getString("password");

                Utilisateur utilisateur = new Utilisateur(id, name, prenom, email, password, codeUnique);
                utilisateur.setRole(RoleEnum.getRoleById(roleId));
                utilisateur.setPersonnelMedical(PersonnelMedicalEnum.valueOf(pmedical));
                utilisateurs.add(utilisateur);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return utilisateurs;
    }

    public int addProgrammation(Programmation pg) {
        String sql = "INSERT INTO PROGRAMMATIONS (codePatient, namePatient, prenomPatient, " +
                "domaineMedical, date, hospital, medecinFullName) values (?,?,?,?,?,?,?) ";

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();

            pstm.setString(1,pg.getCodePatient());
            pstm.setString(2,pg.getNamePatient());
            pstm.setString(3,pg.getPrenomPatient());
            pstm.setString(4,pg.getDomaineMedical());

            // pstm.setObject(5, pg.getDate());
            // On va le recuperer ainsi:
            // LocalDate localDate = ResultSet.getObject( 1 , LocalDate.class );

            java.sql.Date sqlDate = java.sql.Date.valueOf( pg.getDate() );
            pstm.setDate(5, sqlDate);
            // On va le recuperer ainsi:
            // LocalDate localDate = sqlDate.toLocalDate();

            pstm.setString(6, pg.getHospital());
            pstm.setString(7, pg.getMedecinFullName());

            int rs = db.executeMaj();
            return rs;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Programmation> getAllProgrammation(String codePatient, String namePatient, String prenomPatient){

        String sql = "SELECT * FROM PROGRAMMATIONS WHERE" +
                " codePatient = ? and namePatient = ? and prenomPatient = ?";

        List<Programmation> programmations = new ArrayList<>();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1, codePatient);
            pstm.setString(2, namePatient);
            pstm.setString(3, prenomPatient);

            ResultSet rs = db.executeSelect();


            while (rs.next()) {
                int programmation_id = rs.getInt("programmation_id");
                String domaineMedical = rs.getString("domaineMedical");
                LocalDate date = (rs.getDate("date")).toLocalDate();
                String hospital = rs.getString("hospital");
                String medecinFullName = rs.getString("medecinFullName");
                int consultation_id = rs.getInt("consultation_id");

                Programmation programmation = new Programmation();
                programmation.setProgrammation_id(programmation_id);
                programmation.setCodePatient(codePatient);
                programmation.setNamePatient(namePatient);
                programmation.setPrenomPatient(prenomPatient);
                programmation.setDomaineMedical(domaineMedical);
                programmation.setDate(date);
                programmation.setHospital(hospital);
                programmation.setMedecinFullName(medecinFullName);
                programmation.setConsultation_id(consultation_id);

                programmations.add(programmation);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return programmations;
    }

    public Programmation getProgrammation(int id){

        String sql = "SELECT * FROM PROGRAMMATIONS WHERE programmation_id = ? ";

        Programmation programmation = new Programmation();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1, id);

            ResultSet rs = db.executeSelect();


            while (rs.next()) {
                String domaineMedical = rs.getString("domaineMedical");
                String namePatient = rs.getString("namePatient");
                String prenomPatient = rs.getString("prenomPatient");
                LocalDate date = (rs.getDate("date")).toLocalDate();
                String hospital = rs.getString("hospital");
                String medecinFullName = rs.getString("medecinFullName");
                String codePatient = rs.getString("codePatient");

                programmation.setDomaineMedical(domaineMedical);
                programmation.setDate(date);
                programmation.setHospital(hospital);
                programmation.setCodePatient(codePatient);
                programmation.setNamePatient(namePatient);
                programmation.setPrenomPatient(prenomPatient);
                programmation.setMedecinFullName(medecinFullName);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return programmation;
    }

    public List<Programmation>  getAllProgrammationByMedecin(String medecinFullName){

        String sql = "SELECT * FROM PROGRAMMATIONS WHERE medecinFullName = ? ";

        List<Programmation> programmations = new ArrayList<>();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1, medecinFullName);

            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                int programmation_id = rs.getInt("programmation_id");

                String codePatient = rs.getString("codePatient");
                String namePatient = rs.getString("namePatient");
                String prenomPatient = rs.getString("prenomPatient");
                String domaineMedical = rs.getString("domaineMedical");
                LocalDate date = (rs.getDate("date")).toLocalDate();
                String hospital = rs.getString("hospital");


                Programmation programmation = new Programmation();
                programmation.setProgrammation_id(programmation_id);
                programmation.setCodePatient(codePatient);
                programmation.setNamePatient(namePatient);
                programmation.setPrenomPatient(prenomPatient);
                programmation.setDomaineMedical(domaineMedical);
                programmation.setDate(date);
                programmation.setHospital(hospital);
                programmation.setMedecinFullName(medecinFullName);

                programmations.add(programmation);
            }

        }
            catch (Exception ex){
            ex.printStackTrace();
        }

        return programmations;
    }

    public int addConsultation(Consultation cs) {
        String sql = "INSERT INTO CONSULTATIONS " +
                "(codeUniquePatient, namePatient, prenomPatient, codeUniqueMedecin, nameMedecin, prenomMedecin, dateVisite)" +
                " values (?,?,?,?,?,?,?) ";

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1,cs.getCodeUniquePatient());
            pstm.setString(2,cs.getNamePatient());
            pstm.setString(3,cs.getPrenomPatient());
            pstm.setString(4,cs.getCodeUniqueMedecin());
            pstm.setString(5,cs.getNameMedecin());
            pstm.setString(6, cs.getPrenomMedecin());
            java.sql.Date sqlDate = java.sql.Date.valueOf(cs.getDateVisite());
            pstm.setDate(7, sqlDate);

            int rs = db.executeMaj();
            return rs;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public Consultation getCstByCodeUAndDate(String codeUniquePatient, String codeUniqueMedecin, java.sql.Date sqlDate){
        String sql = "SELECT * FROM CONSULTATIONS WHERE " +
                "codeUniquePatient = ? and codeUniqueMedecin = ? and date = ? ";

        Consultation consultation = new Consultation();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1,codeUniquePatient);
            pstm.setString(1,codeUniqueMedecin);
            pstm.setDate(1, sqlDate);

            ResultSet rs = db.executeSelect();

            while (rs.next()) {

                int consultation_id = rs.getInt("consultation_id");
                String namePatient = rs.getString("namePatient");
                String prenomPatient = rs.getString("prenomPatient");
                String nameMedecin = rs.getString("nameMedecin");
                String prenomMedecin = rs.getString("prenomMedecin");

                consultation = new Consultation(codeUniquePatient, namePatient, prenomPatient,
                        codeUniqueMedecin, nameMedecin, prenomMedecin);
                consultation.setConsultation_id(consultation_id);

            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return consultation;
    }

    public List<Consultation> getConsultationByCodeUnique(String codeUniqueMedecin) {

        String sql = "SELECT * FROM CONSULTATIONS WHERE codeUniqueMedecin = ? ";

        List<Consultation> consultations = new ArrayList<>();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1,codeUniqueMedecin);

            ResultSet rs = db.executeSelect();

            while (rs.next()) {

                int consultation_id = rs.getInt("consultation_id");
                String codeUniquePatient  = rs.getString("codeUniquePatient");
                String namePatient = rs.getString("namePatient");
                String prenomPatient = rs.getString("prenomPatient");
                String nameMedecin = rs.getString("nameMedecin");
                String prenomMedecin = rs.getString("prenomMedecin");

                LocalDate dateVisite = (rs.getDate("dateVisite")).toLocalDate();

                Consultation consultation = new Consultation(codeUniquePatient, namePatient, prenomPatient,
                        codeUniqueMedecin, nameMedecin, prenomMedecin);
                consultation.setConsultation_id(consultation_id);
                consultation.setDateVisite(dateVisite);

                consultations.add(consultation);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return consultations;
    }

    public Consultation getConsultationById(int consultation_id) {

        String sql = "SELECT * FROM CONSULTATIONS WHERE consultation_id = ? ";

        Consultation consultation = new Consultation();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1, consultation_id);

            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                String codeUniquePatient = rs.getString("codeUniquePatient");
                String namePatient = rs.getString("namePatient");
                String prenomPatient = rs.getString("prenomPatient");
                String nameMedecin = rs.getString("nameMedecin");
                String prenomMedecin = rs.getString("prenomMedecin");
                String codeUniqueMedecin = rs.getString("codeUniqueMedecin");

                LocalDate dateVisite = (rs.getDate("dateVisite")).toLocalDate();

                consultation = new Consultation(codeUniquePatient, namePatient, prenomPatient,
                        codeUniqueMedecin, nameMedecin, prenomMedecin);
                consultation.setConsultation_id(consultation_id);
                consultation.setDateVisite(dateVisite);

            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return consultation;
    }

    public int addRecette(Recette recette){
        String sql = "INSERT INTO RECETTES " +
                "(label, detail, medecin_id, pharmacien_id, date)" +
                " values (?,?,?,?,?) ";

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1,recette.getLabel());
            pstm.setString(2,recette.getDetail());
            pstm.setInt(3,recette.getMedecin_id());
            pstm.setInt(4,recette.getPharmacien_id());
            java.sql.Date sqlDate = java.sql.Date.valueOf(recette.getDate());
            pstm.setDate(5,sqlDate);

            int rs = db.executeMaj();
            return rs;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;

    }

    public Recette getRecetteByMedecinAndDateAndPharnacien(int medecinId, int pharmacienId, java.sql.Date sqlDate){

        String sql = "SELECT * FROM RECETTES WHERE " +
                "medecin_id = ? and pharmacien_id = ? and date = ? ";

        Recette recette = new Recette();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1,medecinId);
            pstm.setInt(1,pharmacienId);
            pstm.setDate(1, sqlDate);

            ResultSet rs = db.executeSelect();

            while (rs.next()) {

                int recette_id = rs.getInt("recette_id");
                String label = rs.getString("label");
                String detail = rs.getString("detail");

                recette = new Recette(label, detail, medecinId, pharmacienId, sqlDate.toLocalDate());
                recette.setRecette_id(recette_id);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return recette;
    }

    public int addMaladie(Maladie maladie){
        String sql = "INSERT INTO MALADIES " +
                "(nom, description, consultation_id, recette_id)" +
                " values (?,?,?,?) ";

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1,maladie.getNom());
            pstm.setString(2,maladie.getDescription());
            pstm.setInt(3,maladie.getConsultation_id());
            pstm.setInt(4,maladie.getRecette_id());

            int rs = db.executeMaj();
            return rs;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;

    }
}
