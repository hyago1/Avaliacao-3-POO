package br.ufrn.tads.model;

public class User {
    private int id;
    private String nome;
    private String senha;
    private Long CPF;
    public User(){

    }
    public User(String nome, String senha, long cPF) {
        this.nome = nome;
        this.senha = senha;
        this.CPF = cPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public  String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getCPF() {
        return CPF;
    }

    public void setCPF(Long cPF) {
        CPF = cPF;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    

    
}
