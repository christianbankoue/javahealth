package application;

import dao.basededonne.DB;
import model.UserCompte;
import model.enums.RoleEnum;

import java.sql.PreparedStatement;

public class Util {


    public static DB initDb() {
        DB db = new DB();
        return db;
    }

    public static void closeConnection(DB db) {
         db.closeConnection();
    }

    public static int initRoles(DB db){

        //String sql = "INSERT INTO ROLE ( id, label ) values (?, ?), (?, ?), (?, ?), (?, ?);";

        StringBuilder mySql = new StringBuilder("INSERT INTO ROLES ( role_id, role_label ) values (?, ?)");
        RoleEnum[] roleEnums = RoleEnum.values();
        for (int i = 1; i < roleEnums.length; i++) {
            mySql.append(", (?, ?)");
        }

        try{
            db.initPrepar(mySql.toString());
            PreparedStatement pstm = db.getPstm();
            for (int i = 0; i < roleEnums.length; i++) {
                pstm.setInt((2 * i) + 1, roleEnums[i].getIdentifiant());
                pstm.setString((2 * i) + 2, roleEnums[i].getLabel());
            }
            int rs = db.executeMaj();
            return rs;

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return 0;

    }

    public static int ajoutAdmin(DB db){

        String name = "merlin";
        String prenom = "merlin";
        String email = "merlin";
        String password = "merlin";
        String codeUnique = "merlin";


        UserCompte userCompte = new UserCompte(name, prenom, email, password, codeUnique);

        String sql = "INSERT INTO USERCOMPTES (name, prenom, email, password, codeunique, role_id)  values(?,?,?,?,?,?);";

        try{
            db.initPrepar(sql);
            PreparedStatement pstm = db.getPstm();
            pstm.setString(1,userCompte.getName());
            pstm.setString(2,userCompte.getPrenom());
            pstm.setString(3,userCompte.getEmail());
            pstm.setString(4,userCompte.getPassword());
            pstm.setString(5,userCompte.getCodeUnique());
            pstm.setInt(6, RoleEnum.ADMIN.getIdentifiant());


            int rs = db.executeMaj();
            return rs;

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return 0;

    }

}
