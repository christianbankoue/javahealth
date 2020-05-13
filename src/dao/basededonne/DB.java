package dao.basededonne;

import java.sql.*;

public class DB {

    private Connection cnx;

    private ResultSet rs;

    private PreparedStatement pstm;

    private int ok;

    private void getConnection(){
        String url = "jdbc:mysql://localhost:3306/gestionconsult";
        String user = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnx = DriverManager.getConnection(url,user,password);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initPrepar(String sql){

        try {
            getConnection();
            pstm = cnx.prepareStatement(sql);
        }catch (Exception ex){
            ex.printStackTrace();;
        }
    }

    public ResultSet executeSelect() {

        try{
            rs = pstm.executeQuery();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return rs;
    }
    public int executeMaj(){
        try {
            ok = pstm.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ok;
    }

    public void closeConnection() {
        try {
            if(cnx != null)
                cnx.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public PreparedStatement getPstm() throws SQLException{
        return pstm;
    }
}
