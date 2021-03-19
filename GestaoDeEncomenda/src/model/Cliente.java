package model;

import java.util.Date;

/**
 *
 * @author Tembe
 */
public class Cliente extends Pessoa {
    // atributos
    private String contacto;
    private String localizacao;
    private int numEncomendas;
    private String nota; // sera usado para observacoes caso seja necessario
    
    // construtores

    public Cliente(String contacto,  String nome, String genero) {
        super(nome, genero);
        this.contacto = contacto;
    }
    
    public Cliente(String contacto,  String nome, String genero, String localizacao) {
        super(nome, genero);
        this.localizacao = localizacao;
        this.contacto = contacto;
    }

    public Cliente(String contacto, String nome) {
        super(nome);
        this.contacto = contacto;
    }

    public Cliente() {
    }
    
    // Setters and getters

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public int getNumEncomendas() {
        return numEncomendas;
    }

    public void setNumEncomendas(int numEncomendas) {
        this.numEncomendas = numEncomendas;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
    
    
    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        return super.toString()+" Cliente{" + "contacto=" + contacto + ", numEncomendas=" + numEncomendas + ", nota=" + nota + '}';
    }

    
    
}
