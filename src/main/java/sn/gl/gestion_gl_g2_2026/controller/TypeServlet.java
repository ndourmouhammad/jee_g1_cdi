package sn.gl.gestion_gl_g2_2026.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sn.gl.gestion_gl_g2_2026.entity.TypeAssurance;
import sn.gl.gestion_gl_g2_2026.repository.ICrud;
import sn.gl.gestion_gl_g2_2026.repository.impl.TypeAssuranceRepository;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "type", value = "/type")
public class TypeServlet extends HttpServlet {

    private ICrud<TypeAssurance> typeRepository;

    @Override
    public void init() throws ServletException {
        this.typeRepository = new TypeAssuranceRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action")!=null ? req.getParameter("action") : "";
        RequestDispatcher requestDispatcher;
        switch (action){
            case "add":
               requestDispatcher = req.getRequestDispatcher("type/add.jsp");
                requestDispatcher.forward(req,resp);
                break;
            case "delete":
                int id = Integer.parseInt(req.getParameter("id"));
                typeRepository.delete(id);
                resp.sendRedirect("?action=");
                break;
            case "edit":
                TypeAssurance typeAssurance = typeRepository.get(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("type",typeAssurance);
                requestDispatcher = req.getRequestDispatcher("type/edit.jsp");
                requestDispatcher.forward(req,resp);

            default:
                List<TypeAssurance> list = this.typeRepository.getAll();
                req.setAttribute("types", list);
                requestDispatcher = req.getRequestDispatcher("type/list.jsp");
                requestDispatcher.forward(req,resp);

        }




    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action")!=null ? req.getParameter("action") : "";
        TypeAssurance typeAssurance;
        switch (action){
            case "update":
                typeAssurance = typeRepository.get(Integer.parseInt(req.getParameter("id")));
                typeAssurance.setLibelle(req.getParameter("libelle"));
                typeRepository.update(typeAssurance);
                break;
            case "save":
                typeAssurance = new TypeAssurance();
                typeAssurance.setLibelle(req.getParameter("libelle"));
                typeRepository.insert(typeAssurance);
                break;
        }
        resp.sendRedirect("?action=");


    }
}
