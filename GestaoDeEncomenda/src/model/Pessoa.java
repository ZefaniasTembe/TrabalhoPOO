package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Tembe
 */
public class Pessoa implements Serializable {
    // atributos da classe
    private String nome;
    private Date nascimento;
    private String genero;
    private int id;
    
    // Construtores
    public Pessoa(String nome, Date nascimento, String genero) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.genero = genero;
    }
    
    // para o caso de clientes nao e obrigatorio ter sua data de nascimento
    public Pessoa(String nome, String genero) {
        this.nome = nome;
        this.genero = genero;
    }
    
    public Pessoa(String nome) {
        this.nome = nome;
    }
    
    // esse construtor sera muito mais para testes
    public Pessoa() {
    }
    
    // Metodos Setters e Getters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    // metodos especiais como to string

    @Override
    public String toString() {
        return "Pessoa{" + "nome: " + nome + ", nascimento: " + nascimento + ", genero: " + genero + '}';
    }

}
