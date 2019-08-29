package pl.packagemanagement.security;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private String login;
    private String password;

    public JwtRequest(){}

    public JwtRequest(String login, String password){
        this.setLogin(login);
        this.setPassword(password);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
// A gdie masz?
