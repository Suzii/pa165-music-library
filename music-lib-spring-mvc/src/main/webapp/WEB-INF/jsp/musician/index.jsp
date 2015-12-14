<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Musicians">
<jsp:attribute name="body">

    <div class="jumbotron">
        <h1>${title}</h1>
        <p class="lead">Lorem ipsum </p>
        <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/musician/create"
              role="button">Create new</a></p>
    </div>
    
    <div class="row">
        <table class="table">
        <thead>
        <tr>
            <th>Id</th>
            <th>Artist name</th>
            <th>Sex</th>
            <th>Birth date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${musicians}" var="musician">
            <tr>
                <td class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${musician.id}.</td>
                <td class="col-lg-3 col-md-3 col-sm-3 col-xs-3"><c:out value="${musician.artistName}"/></td>
                <td class="col-lg-3 col-md-3 col-sm-3 col-xs-3"><c:out value="${musician.sex}"/></td>
                <td class="col-lg-3 col-md-3 col-sm-3 col-xs-3"><c:out value="${musician.dateOfBirth}"/></td>
                
                <form:form method="get" action="${pageContext.request.contextPath}/musician/update/${musician.id}" cssClass="form-horizontal">
                    <td class="col-lg-1 col-md-1 col-sm-1 col-xs-1 text-center">
                        <button class="btn btn-default" type="submit">
                            <span class="sr-only"><fmt:message key="edit"/></span>
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </button>
                    </td>
                </form:form> 
                    
                <form:form method="post" action="${pageContext.request.contextPath}/musician/remove/${musician.id}" cssClass="form-horizontal">
                    <td class="col-lg-1 col-md-1 col-sm-1 col-xs-1 text-center">
                        <button class="btn btn-default" type="submit">
                            <span class="sr-only"><fmt:message key="remove"/></span>
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </button>
                    </td>
                </form:form> 
                
            </tr>
        </c:forEach>
    </table>
    </div>

</jsp:attribute>
</own:masterpage>
