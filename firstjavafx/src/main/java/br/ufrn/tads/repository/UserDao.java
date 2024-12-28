package br.ufrn.tads.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import br.ufrn.tads.model.User;
import br.ufrn.tads.service.UserSession;

public class UserDao implements Dao<User> {

    @Override
    public Object findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    @Override
    public int save(User user, int d) {
        // TODO Auto-generated method stub
        
        Connection cnn = null;
        // prepares a query
        PreparedStatement preparedStatement = null;
        
        try {
            System.out.println("Tentando fazer conexao e insercao");
            cnn = DBconnection.getConnection();
            preparedStatement = cnn.prepareStatement("insert into usuarios (nome, cpf, senha)" + " values (?, ?, ?)");
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setLong(2, user.getCPF());
            preparedStatement.setString(3, user.getSenha());
            
            preparedStatement.execute(); //it is not a query. It is an insert command
            
        } catch(Exception e) {
            System.out.println("ERRO! ");
            e.printStackTrace();
        } finally {
            // close all connections
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (cnn != null) cnn.close();
                return 1;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return 0;


    }

    @Override
    public boolean update(User t, String[] params,int d) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
        
    }

    @Override
    public boolean delete(User t , int d) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public boolean verificarCpfExistente(Long cpf) throws SQLException {
    String sql = "SELECT COUNT(*) FROM usuarios WHERE cpf = ?";
    try (
        Connection conn = DBconnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, cpf);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Retorna true se já existir
        }
    }
    return false;
}

    
public String autenticarUsuario(Long cpf, String senha) throws SQLException {
        
    String query = "SELECT * FROM usuarios WHERE cpf = ? AND senha = ?";
        
    try (Connection connection = DBconnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        
        preparedStatement.setLong(1, cpf);
        preparedStatement.setString(2, senha);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {
          UserSession.getInstance().setId(resultSet.getLong("id"));
            return resultSet.getString("nome"); // Usuário autenticado com sucesso
        } else {
            return null; // Login ou senha inválidos
        }
}


    
}}
