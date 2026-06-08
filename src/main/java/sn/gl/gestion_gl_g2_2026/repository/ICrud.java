package sn.gl.gestion_gl_g2_2026.repository;

import java.util.List;

public interface ICrud<T> {

    public List<T> getAll();
    public void insert(T t);
    public void delete(int id);
    public T get(int id);
    public void update(T t);


}
