package br.ufrn.tads.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.ufrn.tads.model.Horarios;
import br.ufrn.tads.model.Product;
import br.ufrn.tads.service.UserSession;

public class HorariosDao implements Dao<Horarios> {

    @Override
    public List<Horarios> findAll() { // listAll (if the database is huge, consider the use of pagination)
        List<Horarios> horarios = new ArrayList<Horarios>();
        String sql = "select * from Horarios";
        Connection conn = null;
        // prepares a query
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null; // stores the query result

        try {
            conn = DBconnection.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            // iterates the resultSet and stores in the object the column values from the
            // database
            while (resultSet.next()) {
                Horarios hour = new Horarios();
                hour.setId(resultSet.getInt("id")); // "id" is the column at postgres
                hour.setHora(resultSet.getTime("hora")); // "id" is the column at postgres
                hour.setVago(resultSet.getBoolean("vago")); // "id" is the column at postgres

                horarios.add(hour); // add the object filled with database data to products list
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close all connections
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return horarios;
    }

    @Override
    public List<Horarios>  findById(Long id) {
        List<Horarios> horarios = new ArrayList<Horarios>();
        String sql = "select * from horarios where agendado_por = ?"; // ? is a parameters for the prepared statement
        Connection conn = null;
        // prepares a query
        PreparedStatement preparedStatement = null;
        ResultSet resultSet= null; //stores the query result

        try {
            conn = DBconnection.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            // sending the parameter to sql execution
            preparedStatement.setInt(1, id.intValue()); // id is an object, not primitive (intValue required)
            resultSet = preparedStatement.executeQuery();
            // iterates the resultSet and stores in the object the column values from the database
            while (resultSet.next()){
                Horarios hour = new Horarios();
                hour.setId(resultSet.getInt("id")); // "id" is the column at postgres
                hour.setHora(resultSet.getTime("hora")); // "id" is the column at postgres
                hour.setVago(resultSet.getBoolean("vago")); // "id" is the column at postgres
                horarios.add(hour);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            // close all connections
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (conn != null) conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(horarios);
        return horarios;
        
    }

    @Override
    public int save(Horarios t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    

    @Override
    public boolean delete(Horarios t) {
            return false;
    
    }

    @Override
    public boolean update(Horarios t, String[] params) {
        // TODO Auto-generated method stub
    // TODO Auto-generated method stub
    String nome = UserSession.getInstance().getUserName();
    String sql = "update horarios set vago = true, agendado_por = (select id from usuarios where nome = '"+nome+"') where id = ?"; 
    Connection conn = null;
    // prepares a query
    PreparedStatement preparedStatement = null;
    
    try {
        conn = DBconnection.getConnection();
        preparedStatement = conn.prepareStatement(sql);
  
        preparedStatement.setLong(1, t.getId());
        
        preparedStatement.execute(); //it is not a query. It is an insert command
        
    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        // close all connections
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (conn != null) conn.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    return false;
        
    }

    
}
