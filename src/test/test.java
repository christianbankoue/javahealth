package test;

import donne.IUser;
import donne.UserImpl;

public class test {
    public static void main(String[] args) {
        IUser userdao = new UserImpl();

        userdao.getConnection("seniorbankoue@yahoo.ca","bankoue");
    }
}
