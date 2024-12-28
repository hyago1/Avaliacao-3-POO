package br.ufrn.tads.service;

import java.sql.SQLException;
import java.util.List;

import br.ufrn.tads.controller.cadastroController;
import br.ufrn.tads.controller.loginController;
import br.ufrn.tads.model.Product;
import br.ufrn.tads.model.User;
import br.ufrn.tads.repository.DBconnection;
import br.ufrn.tads.repository.ProductDao;
import br.ufrn.tads.repository.UserDao;

public class UserService {
    
   
    UserDao userDao = new UserDao(); 
    
    public boolean testConnection() {
        if (DBconnection.getConnection() != null) return true;
        else return false;
    }

    // public List<User> getProducts() {
    //     userDao = new UserDao();
    //     return userDao.findAll();
    // }

    public void saveUser(String nome, Long CPF, String Senha) throws SQLException{
        User user = new User(nome, Senha, CPF);

        
            userDao.save(user, 0);
        
      
        
    }
    public Boolean verificarCpfExistente(Long cpf) throws SQLException{
         return userDao.verificarCpfExistente(cpf);
    }

    public String autenticarUsuario(Long cpf, String senha) throws SQLException {
        
        return userDao.autenticarUsuario(cpf, senha);
    }
    // public String buscarNomePeloCpf(Long cpf) throws SQLException {
        
    //     return userDao.buscarNomePeloCpf(cpf);
    // }

 
    
}
 
