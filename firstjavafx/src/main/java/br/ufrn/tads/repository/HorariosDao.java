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

            sql = "select id_horario,hora, nome_dia from agendamentos " +
                    "join horarios on horarios.id = id_horario " +
                    "join dias_semana on dias_semana.id_dia = agendamentos.id_dia " +
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
    public int save(Horarios t, int d) {
        String sql = "insert into agendamentos (id_dia, id_horario, vago, agendado_por) values (" + d + ",?,false,?);";
        Connection conn = null;
        // prepares a query
        PreparedStatement preparedStatement = null;
        Long userId = UserSession.getInstance().getId();
        try {
            conn = DBconnection.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, t.getId());
            preparedStatement.setInt(2, userId.intValue());

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
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;

    }

    @Override
    public boolean delete(Horarios t, int dia) {
        String nome = UserSession.getInstance().getUserName();
        String sql = "delete from agendamentos where id_dia = ? and id_horario = ?";

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = DBconnection.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, dia);
            preparedStatement.setLong(2, t.getId());
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

    @Override
    public boolean update(Horarios t, String[] params, int d) {
        String sql = "update horarios set hora = ?  where id = ?";

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = DBconnection.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTime(1, t.getHora());
            preparedStatement.setLong(2, d);

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
                } catch (Exception e) {
                e.printStackTrace();
            }
        }
                return false;
 
    }

    

    public void deletarHorario(Horarios t){
        String sql = "delete from horarios where id = ?";

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
                } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public List<Horarios> findAllAgendadosParaTabelaDoAdmin(int d) { // listAll (if the database is huge, consider the
                                                                     // use of pagination)
        List<Horarios> horarios = new ArrayList<Horarios>();
        System.out.println(d);
        String sql = "select id_horario,nome_dia, hora, usuarios.nome from agendamentos " +
                "join usuarios on usuarios.id = agendamentos.agendado_por " +
                "join horarios on horarios.id = id_horario " +
                "join dias_semana on agendamentos.id_dia = dias_semana.id_dia" +
                " where agendamentos.id_dia = " + d + " and agendado_por != 1";
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
                hour.setDiaDele(resultSet.getString("nome_dia"));
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

    public List<Horarios> findAllPeloDia(int d) {
        List<Horarios> horarios = new ArrayList<Horarios>();
        String sql = "SELECT h.id, h.hora FROM horarios h LEFT JOIN agendamentos a ON h.id = a.id_horario AND a.id_dia = "
                + d + " WHERE a.id IS NULL";
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
                hour.setId(resultSet.getInt("id")); // "id" is the column at postgres
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
