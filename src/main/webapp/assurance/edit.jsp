<%@ page import="java.util.List" %>
<%@ page import="sn.gl.gestion_gl_g2_2026.entity.TypeAssurance" %>
<%@ page import="sn.gl.gestion_gl_g2_2026.entity.Assurance" %>
<%@ include file="../header.jsp"%>

<%
    Assurance assurance = (Assurance) request.getAttribute("assurance");
    List<TypeAssurance> types = (List<TypeAssurance>) request.getAttribute("types");
%>

<div class="container mt-5">
    <form action="?action=update" method="post">
        <input type="hidden" name="id" value="<%= assurance.getId() %>">

        <label for="">Numero</label>
        <input type="text" name="numero" class="form-control" value="<%= assurance.getNumero() %>" required>

        <label for="">Nom Client</label>
        <input type="text" name="nomClient" class="form-control" value="<%= assurance.getNomClient() %>" required>

        <label for="">Montant</label>
        <input type="number" step="0.01" name="montant" class="form-control" value="<%= assurance.getMontant() %>" required>

        <label for="">Type d'Assurance</label>
        <select name="type_id" class="form-control" required>
            <% if (types != null) {
                for (TypeAssurance t : types) {
                    String selected = (assurance.getTypeAssurance() != null && assurance.getTypeAssurance().getId() == t.getId()) ? "selected" : "";
            %>
                <option value="<%= t.getId() %>" <%= selected %>><%= t.getLibelle() %></option>
            <% }
            } %>
        </select>
        <br>

        <button type="submit" class="btn btn-success">Update</button>
    </form>
</div>
