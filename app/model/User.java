package model;

import io.ebean.Finder;

import javax.persistence.Entity;

@Entity
public class User extends BaseModel {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static final Finder<Long, User> find = new Finder<>(User.class);

}
