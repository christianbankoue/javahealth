package dao.service;

import dao.basededonne.DB;
import dao.service.serviceI.IUserService;
import model.*;
import model.enums.PersonnelMedicalEnum;
import model.enums.RoleEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    public List<Utilisateur> getUtilisateurByRoleId(int roleId){

        String sql = "SELECT * FROM USERCOMPTES WHERE role_id = ? ";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1, roleId);

            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                int id = rs.getInt("usercompte_id");
                String codeUnique = rs.getString("codeUnique");
                String pmedical = rs.getString("pmedical");
                String name = rs.getString("name");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int inactive = rs.getInt("inactive");


                Utilisateur utilisateur = new Utilisateur(id, name, prenom, email, password, codeUnique);
                utilisateur.setInactive(inactive);
                utilisateur.setRole(RoleEnum.getRoleById(roleId));
                if(pmedical != null){
                    utilisateur.setPersonnelMedical(PersonnelMedicalEnum.valueOf(pmedical));
                }
                utilisateurs.add(utilisateur);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return utilisateurs;
    }

    public Utilisateur getUtilisateurById(int id){

        String sql = "SELECT * FROM USERCOMPTES WHERE usercompte_id = ? ";

        Utilisateur utilisateur = new Utilisateur();
        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1, id);

            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                String codeUnique = rs.getString("codeUnique");
                String pmedical = rs.getString("pmedical");
                int roleId = rs.getInt("role_id");
                String name = rs.getString("name");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String password = rs.getString("password");

                utilisateur = new Utilisateur(id, name, prenom, email, password, codeUnique);
                utilisateur.setRole(RoleEnum.getRoleById(roleId));
                if(pmedical != null){
                    utilisateur.setPersonnelMedical(PersonnelMedicalEnum.valueOf(pmedical));
                }
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return utilisateur;
    }

    public List<Utilisateur> getAllUser(){
        String sql = "SELECT * FROM USERCOMPTES";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try{
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                int id = rs.getInt("usercompte_id");
                int roleId = rs.getInt("role_id");
                String codeUnique = rs.getString("codeUnique");
                String pmedical = rs.getString("pmedical");
                String name = rs.getString("name");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String password = rs.getString("password");

                Utilisateur utilisateur = new Utilisateur(id, name, prenom, email, password, codeUnique);
                utilisateur.setRole(RoleEnum.getRoleById(roleId));
                if(pmedical != null){
                    utilisateur.setPersonnelMedical(PersonnelMedicalEnum.valueOf(pmedical));
                }
                utilisateurs.add(utilisateur);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return utilisateurs;
    }

    public int deleteUtilisateur(int usercompte_id){

        String sql = "DELETE FROM USERCOMPTES WHERE " +
                "usercompte_id = ? ";

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1,usercompte_id);
            int rs = db.executeMaj();
            return rs;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
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

            //java.sql.Date sqlDate = java.sql.Date.valueOf( pg.getDate() );
            java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf( pg.getDate() );
            pstm.setTimestamp(5, sqlDate);
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
                LocalDateTime date = (rs.getTimestamp("date")).toLocalDateTime();
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
                LocalDateTime date = (rs.getTimestamp("date")).toLocalDateTime();
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
                LocalDateTime date = (rs.getTimestamp("date")).toLocalDateTime();
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

    public int addConsultation(Consultation cs, int hour, int min) {
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

            java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(cs.getDateVisite().atTime(hour, min));
            pstm.setTimestamp(7, sqlDate);

            int rs = db.executeMaj();
            return rs;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public Consultation getCstByCodeUAndDate(String codeUniquePatient, String codeUniqueMedecin, java.sql.Timestamp sqlDate){
        String sql = "SELECT * FROM CONSULTATIONS WHERE " +
                "codeUniquePatient = ? and codeUniqueMedecin = ? and dateVisite = ? ";

        Consultation consultation = new Consultation();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1,codeUniquePatient);
            pstm.setString(2,codeUniqueMedecin);
            pstm.setTimestamp(3, sqlDate);

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

                LocalDateTime dateVisite = (rs.getTimestamp("dateVisite")).toLocalDateTime();

                Consultation consultation = new Consultation(codeUniquePatient, namePatient, prenomPatient,
                        codeUniqueMedecin, nameMedecin, prenomMedecin);
                consultation.setConsultation_id(consultation_id);
                consultation.setDateVisite(dateVisite.toLocalDate());

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

                LocalDateTime dateVisite = (rs.getTimestamp("dateVisite")).toLocalDateTime();

                consultation = new Consultation(codeUniquePatient, namePatient, prenomPatient,
                        codeUniqueMedecin, nameMedecin, prenomMedecin);
                consultation.setConsultation_id(consultation_id);
                consultation.setDateVisite(dateVisite.toLocalDate());

            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return consultation;
    }

    public int addRecette(Recette recette){
        String sql = "INSERT INTO RECETTES " +
                "(label, detail, medecin_id, pharmacien_id, produit_id, date)" +
                " values (?,?,?,?,?,?) ";

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1,recette.getLabel());
            pstm.setString(2,recette.getDetail());
            pstm.setInt(3,recette.getMedecin_id());
            pstm.setInt(4,recette.getPharmacien_id());
            pstm.setInt(5,recette.getProduit_id());
            java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(recette.getDate());
            pstm.setTimestamp(6,sqlDate);

            int rs = db.executeMaj();
            return rs;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;

    }

    public Recette getRecetteByMedecinAndDateAndPharnacien(int medecinId, int pharmacienId, java.sql.Timestamp sqlDate){

        String sql = "SELECT * FROM RECETTES WHERE " +
                "medecin_id = ? and pharmacien_id = ? and date = ? ";

        Recette recette = new Recette();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1,medecinId);
            pstm.setInt(2,pharmacienId);
            pstm.setTimestamp(3, sqlDate);

            ResultSet rs = db.executeSelect();

            while (rs.next()) {

                int recette_id = rs.getInt("recette_id");
                int produit_id = rs.getInt("produit_id");
                String label = rs.getString("label");
                String detail = rs.getString("detail");

                recette = new Recette(label, detail, medecinId, pharmacienId, produit_id, sqlDate.toLocalDateTime());
                recette.setRecette_id(recette_id);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return recette;
    }

    public List<Recette> getRecettesByPharmacien(int pharmacienId){

        String sql = "SELECT * FROM RECETTES WHERE " +
                "pharmacien_id = ? ";

        List<Recette> recettes = new ArrayList<>();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1,pharmacienId);
            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                int recette_id = rs.getInt("recette_id");
                int produit_id = rs.getInt("produit_id");
                int medicamentDelivrer = rs.getInt("medicamentDelivrer");
                int medecinId = rs.getInt("medecin_id");
                String label = rs.getString("label");
                String detail = rs.getString("detail");
                Timestamp dateTime = rs.getTimestamp("date");

                LocalDateTime date = dateTime.toLocalDateTime();

                Recette recette = new Recette(label, detail, medecinId, pharmacienId, produit_id, date);
                recette.setRecette_id(recette_id);
                recette.setMedicamentDelivrer(medicamentDelivrer);

                recettes.add(recette);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }


        return recettes;
    }

    public int updateRecettesById(int recette_id){

        String sql = "UPDATE RECETTES SET medicamentDelivrer = 1 WHERE " +
                "recette_id = ? ";

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1,recette_id);
            int rs = db.executeMaj();
            return rs;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
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

    public Maladie getMaladieByConsultationId(int consultation_id) {

        String sql = "SELECT * FROM MALADIES WHERE consultation_id = ? ";

        Maladie maladie = new Maladie();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1, consultation_id);

            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                int maladie_id = rs.getInt("maladie_id");
                int recette_id = rs.getInt("recette_id");

                maladie = new Maladie(nom, description, consultation_id, recette_id);
                maladie.setMaladie_id(maladie_id);

            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return maladie;
    }

    public List<Produit> getAllProduit(){

        String sql = "SELECT * FROM PRODUITS ";

        List<Produit> produits = new ArrayList<>();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                int produit_id = rs.getInt("produit_id");
                String label = rs.getString("label");
                String detail = rs.getString("detail");


                Produit produit = new Produit(label, detail);
                produit.setProduit_id(produit_id);
                produits.add(produit);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return produits;
    }

    public int deleteProduit(int produit_id){

        String sql = "DELETE FROM PRODUITS WHERE " +
                "produit_id = ? ";

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1,produit_id);
            int rs = db.executeMaj();
            return rs;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public Recette getRecetteById(int recette_id) {
        String sql = "SELECT * FROM RECETTES WHERE recette_id = ? ";

        Recette recette = new Recette();

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setInt(1, recette_id);

            ResultSet rs = db.executeSelect();

            while (rs.next()) {
                String label = rs.getString("label");
                String detail = rs.getString("detail");
                int medecin_id = rs.getInt("medecin_id");
                int pharmacien_id = rs.getInt("pharmacien_id");
                int produit_id = rs.getInt("produit_id");
                int medicamentDelivrer = rs.getInt("medicamentDelivrer");

                recette = new Recette(label, detail, medecin_id, pharmacien_id, produit_id, null);
                recette.setMedicamentDelivrer(medicamentDelivrer);

            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return recette;
    }
}
