<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:masterpage title="Users">
<jsp:attribute name="body">

    <table class="table">
      <caption>Users</caption>
      <thead>
      <tr>
        <th>id</th>
        <th>first name</th>
        <th>surname</th>
        <th>email</th>
        <th>admin</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${users}" var="user">
        <tr>
          <td>${user.id}</td>
          <td><c:out value="${user.firstName}"/></td>
          <td><c:out value="${user.lastName}"/></td>
          <td><c:out value="${user.email}"/></td>
          <td><c:out value="${user.admin}"/></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>

</jsp:attribute>
</my:masterpage>