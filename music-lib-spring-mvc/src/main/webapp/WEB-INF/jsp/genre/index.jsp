<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
<jsp:attribute name="body" >
<div class="genresList">
    <!-- title -->
    <div class="page-header">
        <h1><fmt:message key="genres.title"/></h1>
    </div>
    
    
    <a href="${pageContext.request.contextPath}/genre/create" class="btn btn-primary">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
        <fmt:message key="genres.create"/>
    </a>
    
    <div class="row">
        <table class="table">
        <thead>
        <tr>
            <th>Id</th>
            <th><fmt:message key="genres.title"/></th>
            <th class="text-center"><fmt:message key="edit"/></th>
            <th class="text-center"><fmt:message key="remove"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${genres}" var="genre">
            <tr>
                <td class="col-lg-1 col-md-1 col-sm-1 col-xs-1">${genre.id}.</td>
                <td class="col-lg-9 col-md-9 col-sm-9 col-xs-9"><c:out value="${genre.title}"/></td>
                
                <form:form method="get" action="${pageContext.request.contextPath}/genre/update/${genre.id}" cssClass="form-horizontal">
                    <td class="col-lg-1 col-md-1 col-sm-1 col-xs-1 text-center">
                        <button class="btn btn-default" type="submit">
                            <span class="sr-only"><fmt:message key="edit"/></span>
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </button>
                    </td>
                </form:form> 
                    
                <form:form method="post" action="${pageContext.request.contextPath}/genre/remove/${genre.id}" cssClass="form-horizontal">
                    <td class="col-lg-1 col-md-1 col-sm-1 col-xs-1 text-center">
                        <button class="btn btn-default" type="submit">
                            <span class="sr-only"><fmt:message key="remove"/></span>
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </button>
                    </td>
                </form:form> 
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</div>
</jsp:attribute>
</own:masterpage>
