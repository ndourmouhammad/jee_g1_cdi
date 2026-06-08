<%@ page import="sn.gl.gestion_gl_g2_2026.entity.TypeAssurance" %>
<%@ include file="../header.jsp"%>

<%
    TypeAssurance typeAssurance = (TypeAssurance) request.getAttribute("type");
%>
    <div class="container mt-5">
        <form action="?action=update" method="post">
            <input type="text" name="id" hidden value="<%= typeAssurance.getId() %>">
            <label for="">Libelle</label>
            <input type="text" name="libelle" class="form-control" value="<%= typeAssurance.getLibelle() %>">
            <br>

            <button type="submit" class="btn btn-success">Update</button>
        </form>
    </div>
