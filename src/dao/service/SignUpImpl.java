package dao.service;

import Domaine.mapper.UserCompte;
import dao.basededonne.DB;

import java.sql.ResultSet;

public class SignUpImpl {

    private DB db = new DB();

    public int SignUp(UserCompte userCompte){


        String sql = "INSERT INTO USERCOMPTE (name, prenom, email, password, codeu)  values(?,?,?,?,?) ";

        try{
            db.initPrepar(sql);
            db.getPstm().setString(1,userCompte.getName());
            db.getPstm().setString(2,userCompte.getPrenom());
            db.getPstm().setString(3,userCompte.getEmail());
            db.getPstm().setString(4,userCompte.getPassword());
            db.getPstm().setString(5,userCompte.getCodeu());

            int rs = db.executeMaj();
            return rs;

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return 0;


    }
}
