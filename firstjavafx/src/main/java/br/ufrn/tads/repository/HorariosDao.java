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
    public List<Horarios> findById(Long id) {
        List<Horarios> horarios = new ArrayList<Horarios>();
        String sql = "select * from horarios"; // ? is a parameters for the prepared statement
        if (id != 1) {

            sql = "select id_horario,hora, nome_dia from dias_horarios " +
                    "join horarios on horarios.id = id_horario " +
                    "join dias_semana on dias_semana.id_dia = dias_horarios.id_dia " +
                    "where agendado_por = ?"; // ? is a parameters for the prepared statement
        }
        Connection conn = null;
        // prepares a query
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null; // stores the query result

        try {
            conn = DBconnection.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            // sending the parameter to sql execution
            if (id != 1)
                preparedStatement.setInt(1, id.intValue()); // id is an object, not primitive (intValue required)
            resultSet = preparedStatement.executeQuery();
            // iterates the resultSet and stores in the object the column values from the
            // database
            while (resultSet.next()) {
                Horarios hour = new Horarios();
                hour.setId(resultSet.getInt("id_horario")); // "id" is the column at postgres
                hour.setHora(resultSet.getTime("hora")); // "id" is the column at postgres
                hour.setDiaDele(resultSet.getString("nome_dia")); // "id" is the column at postgres

                horarios.add(hour);
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
    public boolean update(Horarios t, String[] params, String d) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        String nome = UserSession.getInstance().getUserName();
        // String sql = "update horarios set vago = false, agendado_por = (select id
        // from usuarios where nome = '"+nome+"') where id = ?";
        String sql = "update dias_horarios " +
                " set agendado_por = (select id from usuarios where nome = ? ) , vago = false " +
                "where id_dia = (select id_dia from dias_semana where nome_dia = ?) and id_horario = ? ";
        Connection conn = null;
        // prepares a query
        PreparedStatement preparedStatement = null;

        try {
            conn = DBconnection.getConnection();
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, d);
            preparedStatement.setLong(3, t.getId());
            preparedStatement.execute(); // it is not a query. It is an insert command

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close all connections
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (conn != null)
                    conn.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    // Concertar
    public List<Horarios> findAllParaTabelaDoAdmin(String d) { // listAll (if the database is huge, consider the use of pagination)
        List<Horarios> horarios = new ArrayList<Horarios>();
        System.out.println(d);
        String sql = "select id_horario, hora, usuarios.nome from dias_horarios "+
                    "join usuarios on usuarios.id = dias_horarios.agendado_por "+
                    "join horarios on horarios.id = id_horario"+
                    " where id_dia = (select id_dia from dias_semana where nome_dia = '"+d +"') ";
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
                System.out.println(resultSet);
                Horarios hour = new Horarios();
                hour.setId(resultSet.getInt("id_horario"));
                hour.setHora(resultSet.getTime("hora"));
                hour.setAgendadoPor(resultSet.getString("nome"));
                horarios.add(hour);
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

    // Concertar
    public boolean cancelarHorario(Horarios t,  String d) {
        String nome = UserSession.getInstance().getUserName();
        String sql = "update dias_horarios set agendado_por = 1, vago = true "+
                    "where id_dia = (select id_dia from dias_semana where nome_dia = '"+d+"') and id_horario = ? ";

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = DBconnection.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, t.getId());
            preparedStatement.execute(); // it is not a query. It is an insert command

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close all connections
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (conn != null)
                    conn.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    public List<Horarios> findAllPeloDia(String d) {
        List<Horarios> horarios = new ArrayList<Horarios>();
        String sql = "select id_horario,hora from dias_horarios join horarios on id_horario = horarios.id where id_dia = (select id_dia from dias_semana where nome_dia = ?) and vago = true";
        Connection conn = null;
        // prepares a query
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null; // stores the query result

        try {
            conn = DBconnection.getConnection();
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, d);

            resultSet = preparedStatement.executeQuery();
            // iterates the resultSet and stores in the object the column values from the
            // database
            while (resultSet.next()) {
                System.out.println(resultSet);
                Horarios hour = new Horarios();
                hour.setId(resultSet.getInt("id_horario")); // "id" is the column at postgres
                hour.setHora(resultSet.getTime("hora")); // "id" is the column at postgres // "id" is the column at
                                                         // postgres// "id" is the column at postgres
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

}
