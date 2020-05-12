package donne;

import sample.User;

import java.sql.ResultSet;

public class UserImpl implements IUser{
    private DB db = new DB();
    @Override
    public User getConnection(String email, String password) {
        User user = null;
        String sql = "SELECT* FROM USER WHERE EMAIL = ? AND Password = ?";
        try{
            db.initPrepar(sql);
            db.getPstm().setString(1,email);
            db.getPstm().setString(2,password);
            ResultSet rs = db.executeSelect();
            if(rs.next()){
            user = new User();
            user.setId(rs.getInt(1));
            user.setEmail(rs.getString(2));
            user.setPassword(rs.getString(3));

            Object medecin = rs.getObject(4);
            user.setMedecin(null);
            user.setPatient(null);

                if(medecin != null){
                    System.out.println("Medecin "+(Integer)medecin);
                }

                Object patient = rs.getObject(5);
                if(patient != null){
                    System.out.println("Patient"+(Integer)patient);
                }

            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}

