<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
    <jsp:attribute name="title"><fmt:message key="genres.heading"/></jsp:attribute>

    <jsp:attribute name="body" >
        <div class="jumbotron">
            <!-- title -->
            <div class="page-header">
                <h1><fmt:message key="genres.heading"/></h1>
                <p class="lead"><fmt:message key="genres.subheading"/></p>
            </div>

            <c:if test="${isAdmin}">
                <p align="right">
                    <a class="btn btn-lg btn-success btn-jumbotron" href="${pageContext.request.contextPath}/genre/create">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        <fmt:message key="create_new"/>
                    </a>
                </p>
            </c:if>
        </div>

        <div class="row">
            <table class="table">
                <thead>
                    <tr>
                        <th class=${isAdmin? "col-xs-1":"col-xs-3"}><fmt:message key="num"/></th>
                        <th class=${isAdmin? "col-xs-3":"col-xs-9"}><fmt:message key="genres.name"/></th>
                            <c:if test="${isAdmin}">
                            <th class="text-center"><fmt:message key="edit"/></th>
                            <th class="text-center"><fmt:message key="remove"/></th>
                            </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${genres}" var="genre">
                        <c:set var="count" value="${count + 1}" scope="page"/>
                        <tr>
                            <td class=${isAdmin? "col-xs-1":"col-xs-3"}>${count}.</td>
                            <td class=${isAdmin? "col-xs-3 lead-column":"col-xs-9 lead-column"}><c:out value="${genre.title}"/></td>
                            <c:if test="${isAdmin}">
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
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:attribute>
</own:masterpage>
