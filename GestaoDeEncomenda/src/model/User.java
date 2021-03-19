package model;

// optei por criar essa classe como filha do cliente uma vez que o usuario pode tambem ser cliente...

import java.io.Serializable;

public class User extends Cliente implements Serializable{
    // Atributos dos usuarios
    private String login;
    private String ultimoAcesso;

    // o cadastro do usuario pode exigir que ele coloque todos os dados do no sistema
    public User(String login, String contacto, String nome, String genero) {
        super(contacto, nome, genero);
        this.login = login;
    }
    
    //getters & setters

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(String ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    @Override
    public String toString() {
        return super.toString()+ " user{" + "login=" + login + ", ultimoAcesso=" + ultimoAcesso + '}';
    }
    
    
    
}
