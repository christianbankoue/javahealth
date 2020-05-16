package dao.service;

import dao.basededonne.DB;
import dao.service.serviceI.IUserService;
import model.Programmation;
import model.UserCompte;
import model.Utilisateur;
import model.enums.RoleEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImp implements IUserService {

    private DB db = new DB();

    public int signUp(UserCompte userCompte){

        String sql = "INSERT INTO USERCOMPTES (name, prenom, email, password, codeunique, role_id) values (?,?,?,?,?,?) ";

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

            }else if(userCompte.isPersonnelmedicale() ){
                pstm.setInt(6, RoleEnum.PERSONNEL_MEDICAL.getIdentifiant());

            }else if(userCompte.isFournisseur()){
                pstm.setInt(6, RoleEnum.FOURNISSEUR.getIdentifiant());

            }else if(userCompte.isPharmatien()){
                pstm.setInt(6, RoleEnum.PHARMACIEN.getIdentifiant());

            }else{
                pstm.setInt(6, RoleEnum.PATIENT.getIdentifiant());

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

        String sql = "SELECT usercompte_id, codeUnique, role_id FROM USERCOMPTES WHERE name = ? and prenom = ? and email = ? and password = ? ";

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

                utilisateur = new Utilisateur(id, name, prenom, email, password, codeUnique);
                utilisateur.setRole(RoleEnum.getRoleById(roleId));
                return utilisateur;
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return new Utilisateur();
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
                LocalDate date = (rs.getDate("date")).toLocalDate();
                String hospital = rs.getString("hospital");
                String medecinFullName = rs.getString("medecinFullName");

                programmation.setDomaineMedical(domaineMedical);
                programmation.setDate(date);
                programmation.setHospital(hospital);
                programmation.setMedecinFullName(medecinFullName);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return programmation;
    }

    public int delProgrammation(Programmation pg) {
        return 0;
    }


}
