package sn.gl.gestion_gl_g2_2026.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sn.gl.gestion_gl_g2_2026.entity.Assurance;
import sn.gl.gestion_gl_g2_2026.entity.TypeAssurance;
import sn.gl.gestion_gl_g2_2026.repository.ICrud;
import sn.gl.gestion_gl_g2_2026.repository.impl.AssuranceRepository;
import sn.gl.gestion_gl_g2_2026.repository.impl.TypeAssuranceRepository;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "assurance", value = "/assurance")
@RequestScoped
public class AssuranceServlet extends HttpServlet {

    @Inject
    private ICrud<Assurance> assuranceRepository;

    @Inject
    private ICrud<TypeAssurance> typeRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") != null ? req.getParameter("action") : "";
        RequestDispatcher requestDispatcher;

        switch (action) {
            case "add":
                req.setAttribute("types", typeRepository.getAll());
                requestDispatcher = req.getRequestDispatcher("/assurance/add.jsp");
                requestDispatcher.forward(req, resp);
                break;
            case "delete":
                int id = Integer.parseInt(req.getParameter("id"));
                assuranceRepository.delete(id);
                resp.sendRedirect(req.getContextPath() + "/assurance?action=");
                break;
            case "edit":
                Assurance assurance = assuranceRepository.get(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("assurance", assurance);
                req.setAttribute("types", typeRepository.getAll());
                requestDispatcher = req.getRequestDispatcher("/assurance/edit.jsp");
                requestDispatcher.forward(req, resp);
                break;
            default:
                List<Assurance> list = this.assuranceRepository.getAll();
                req.setAttribute("assurances", list);
                requestDispatcher = req.getRequestDispatcher("/assurance/list.jsp");
                requestDispatcher.forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") != null ? req.getParameter("action") : "";
        Assurance assurance;

        switch (action) {
            case "update":
                assurance = assuranceRepository.get(Integer.parseInt(req.getParameter("id")));
                assurance.setNumero(req.getParameter("numero"));
                assurance.setNomClient(req.getParameter("nomClient"));
                assurance.setMontant(Double.parseDouble(req.getParameter("montant")));
                assurance.setTypeAssurance(typeRepository.get(Integer.parseInt(req.getParameter("type_id"))));
                assuranceRepository.update(assurance);
                break;
            case "save":
                assurance = new Assurance();
                assurance.setNumero(req.getParameter("numero"));
                assurance.setNomClient(req.getParameter("nomClient"));
                assurance.setMontant(Double.parseDouble(req.getParameter("montant")));
                assurance.setTypeAssurance(typeRepository.get(Integer.parseInt(req.getParameter("type_id"))));
                assuranceRepository.insert(assurance);
                break;
        }
        resp.sendRedirect(req.getContextPath() + "/assurance?action=");
    }
}
