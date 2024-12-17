package br.ufrn.tads.service;

import java.sql.SQLException;
import java.util.List;

import br.ufrn.tads.model.Horarios;
import br.ufrn.tads.repository.HorariosDao;
import br.ufrn.tads.repository.UserDao;

public class HorariosService {
    HorariosDao HorariosDao = new HorariosDao(); 
    
       public List<Horarios> buscarHorarios() throws SQLException{
         return HorariosDao.findAll();
    }
       public List<Horarios> buscarHorariosParaTabelaDoAdmin() throws SQLException{
         return HorariosDao.findAllParaTabelaDoAdmin();
    }
       public List<Horarios> buscarHorariosId() throws SQLException{
        Long id = UserSession.getInstance().getId();
        System.out.println("hrService: "+id);
         return HorariosDao.findById(id);
    }
        public void confirmar(Horarios t){
            HorariosDao.update(t, null);
    }

}
