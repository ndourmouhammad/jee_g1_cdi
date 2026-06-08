<%@ page import="java.util.List" %>
<%@ page import="sn.gl.gestion_gl_g2_2026.entity.TypeAssurance" %>
<%@ include file="../header.jsp"%>

  <%
    List<TypeAssurance> types = (List<TypeAssurance>) request.getAttribute("types");
  %>

  <div class="container mt-5">
    <a href="?action=add" class="btn btn-success"> Add</a>

    <table class="table table-stripped">
      <tr>
        <td>Id</td>
        <td>Libelle</td>
        <td>Action(s)</td>

      </tr>

      <% for (TypeAssurance t : types){ %>
      <tr>
        <td><%= t.getId() %></td>
        <td><%= t.getLibelle() %></td>
        <td>
          <a class="btn btn-danger" href="?action=delete&id=<%= t.getId() %>">Delete</a>
          <a class="btn btn-primary" href="?action=edit&id=<%= t.getId() %>">Update</a>
        </td>
      </tr>

      <% } %>
    </table>

  </div>

