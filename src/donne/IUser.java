package donne;

import sample.User;

public interface IUser {
    public User getConnection(String email, String password);
}
