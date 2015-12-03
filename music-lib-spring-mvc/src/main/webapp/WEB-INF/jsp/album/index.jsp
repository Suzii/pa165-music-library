<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
<jsp:attribute name="body">

    <div class="jumbotron">
        <h1>${title}</h1>
        <p class="lead">Whatever dummy text! </p>
        <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/album/create"
              role="button">Create new</a></p>
    </div>
    
    <div class="row">
        <table class="table">
        <thead>
        <tr>
            <th>Id</th>
            <th>Title</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${albums}" var="album">
            <tr>
                <td>${album.id}</td>
                <td><c:out value="${album.title}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>

</jsp:attribute>
</own:masterpage>
