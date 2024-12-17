package br.ufrn.tads.service;

public class UserSession {
    private static UserSession instance;
    private String userName;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        System.out.println("Setando id do logado: "+ id);
        this.id = id;
    }

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        System.out.println("chegou no usersession "+getUserName());
        this.userName = userName;
    }
}
