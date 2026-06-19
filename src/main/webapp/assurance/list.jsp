<%@ page import="java.util.List" %>
<%@ page import="sn.gl.gestion_gl_g2_2026.entity.Assurance" %>
<%@ include file="../header.jsp"%>

<%
    List<Assurance> assurances = (List<Assurance>) request.getAttribute("assurances");
%>

<div class="container mt-5">
    <a href="?action=add" class="btn btn-success"> Add</a>

    <table class="table table-stripped">
        <tr>
            <td>Id</td>
            <td>Numero</td>
            <td>Client</td>
            <td>Montant</td>
            <td>Type</td>
            <td>Action(s)</td>
        </tr>

        <% if (assurances != null) {
            for (Assurance a : assurances) { %>
        <tr>
            <td><%= a.getId() %></td>
            <td><%= a.getNumero() %></td>
            <td><%= a.getNomClient() %></td>
            <td><%= a.getMontant() %></td>
            <td><%= a.getTypeAssurance() != null ? a.getTypeAssurance().getLibelle() : "-" %></td>
            <td>
                <a class="btn btn-danger" href="?action=delete&id=<%= a.getId() %>">Delete</a>
                <a class="btn btn-primary" href="?action=edit&id=<%= a.getId() %>">Update</a>
            </td>
        </tr>
        <% }
        } %>
    </table>
</div>
