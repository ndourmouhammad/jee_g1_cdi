package sn.gl.gestion_gl_g2_2026.repository;



import sn.gl.gestion_gl_g2_2026.entity.Assurance;

import java.util.List;

public interface IAssurance {
    public List<Assurance> getAssurances();
    public void insertAssurance(Assurance assurance);
    public void deleteAssurance(int id);
    public Assurance getAssurance(int id);
    public void updateAssurance(Assurance assurance);
}
