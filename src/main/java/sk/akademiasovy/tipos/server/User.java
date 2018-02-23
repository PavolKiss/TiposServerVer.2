package sk.akademiasovy.tipos.server;

import java.util.Random;

public class User {
    private String firstname;
    private String lastname;
    private String login;
    private String email;
    private String token;

    public User(String firstname, String lastname, String login, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.email = email;
        generetaToken();
    }

    private void generetaToken() {
        char[] text=new char[40];
        Random random=new Random();

        for(int i=0;i<40;i++){
            text[i]=(char) (random.nextInt(26)+'A');
        }
        token=String.valueOf(text);

        System.out.println(token);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}