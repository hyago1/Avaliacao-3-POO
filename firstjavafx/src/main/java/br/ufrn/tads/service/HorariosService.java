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
    
       public List<Horarios> buscarHorariosPeloDia(int d) throws SQLException{
        System.out.println("Chegou no ServiceHorario " + d);
         return HorariosDao.findAllPeloDia(d);
    }
       public List<Horarios> hrAgendadosParaTabelaDoAdmin(int d) throws SQLException{
         return HorariosDao.findAllAgendadosParaTabelaDoAdmin(d);
    }
       public List<Horarios> buscarHorariosId() throws SQLException{
        Long id = UserSession.getInstance().getId();
        System.out.println("hrService: "+id);
         return HorariosDao.findById(id);
    }
        public void confirmar(Horarios t, int d){
            HorariosDao.save(t,d );
    }
        public void cancelarHorario(Horarios t , int d){
            HorariosDao.delete(t, d);
    }
        public void deletarHorario(Horarios t){
            HorariosDao.deletarHorario(t);
    }
    public void updateHour(Horarios h){
        HorariosDao.update(h,null,h.getId());
}


}
