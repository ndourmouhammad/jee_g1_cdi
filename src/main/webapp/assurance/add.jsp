<%@ page import="java.util.List" %>
<%@ page import="sn.gl.gestion_gl_g2_2026.entity.TypeAssurance" %>
<%@ include file="../header.jsp"%>

<%
    List<TypeAssurance> types = (List<TypeAssurance>) request.getAttribute("types");
%>

<div class="container mt-5">
    <form action="?action=save" method="post">
        <label for="">Numero</label>
        <input type="text" name="numero" class="form-control" required>

        <label for="">Nom Client</label>
        <input type="text" name="nomClient" class="form-control" required>

        <label for="">Montant</label>
        <input type="number" step="0.01" name="montant" class="form-control" required>

        <label for="">Type d'Assurance</label>
        <select name="type_id" class="form-control" required>
            <% if (types != null) {
                for (TypeAssurance t : types) { %>
                <option value="<%= t.getId() %>"><%= t.getLibelle() %></option>
            <% }
            } %>
        </select>
        <br>

        <button type="submit" class="btn btn-success">Save</button>
    </form>
</div>
