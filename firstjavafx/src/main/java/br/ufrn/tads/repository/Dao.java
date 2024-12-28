package br.ufrn.tads.repository;
import java.util.List;

public interface Dao<T> { //generics
    Object findById(Long id);
    List<T> findAll();
    int save(T t, int d);
    boolean update(T t, String[] params,int d);
    boolean delete(T t, int d);
}

