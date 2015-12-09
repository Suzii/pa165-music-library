<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
<jsp:attribute name="body">

    <!-- title -->
    <c:if test="${not empty title}">
        <div class="page-header">
            <h1><c:out value="${title}"/></h1>
        </div>
    </c:if>
    
    <my:a href="${pageContext.request.contextPath}/genre/create" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        New genre
    </my:a>
    
    <div class="row">
        <table class="table">
        <thead>
        <tr>
            <th>Id</th>
            <th>Title</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${genres}" var="genre">
            <tr>
                <td>${genre.id}</td>
                <td><c:out value="${genre.title}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>

</jsp:attribute>
</own:masterpage>
