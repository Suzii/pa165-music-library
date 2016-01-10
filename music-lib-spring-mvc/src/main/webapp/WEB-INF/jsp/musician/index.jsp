<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
    <jsp:attribute name="title"><fmt:message key="musicians.heading"/></jsp:attribute>

    <jsp:attribute name="body">

        <div class="jumbotron">
            <h1><fmt:message key="musicians.heading"/></h1>
            <p class="lead"><fmt:message key="musicians.subheading"/></p>
            <c:if test="${isAdmin}">
                <p align="right">
                    <a class="btn btn-lg btn-success btn-jumbotron" href="${pageContext.request.contextPath}/musician/create" role="button">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    <fmt:message key="musician.createNew"/>
                    </a>
                </p>
            </c:if>
        </div>

        <div class="row">
            <table class="table">
                <thead>
                    <tr>
                        <th><fmt:message key="num"/></th>
                        <th><fmt:message key="musician.name"/></th>
                        <th class="text-center"><fmt:message key="musician.sex"/></th>
                        <th class="text-center"><fmt:message key="musician.birthdate"/></th>
                            <c:if test="${isAdmin}">
                            <th class="text-center"><fmt:message key="edit"/></th>
                            <th class="text-center"><fmt:message key="remove"/></th>
                            </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${musicians}" var="musician">
                        <c:set var="count" value="${count + 1}" scope="page"/>
                        <tr>
                            <td class="col-xs-1 lead-column">${count}.</td>
                            <td class="col-xs-3 lead-column">
                                <a href="${pageContext.request.contextPath}/musician/detail/${musician.id}" >
                                    <c:out value="${musician.artistName}"/>
                                </a>
                            </td>
                            <td class="col-xs-3 text-center"><fmt:message key="${musician.sex}"/></td>
                            <td class="col-xs-3 text-center"><fmt:formatDate value="${musician.dateOfBirth}"/></td>

                            <c:if test="${isAdmin}">
                                <form:form method="get" action="${pageContext.request.contextPath}/musician/update/${musician.id}" cssClass="form-horizontal">
                                    <td class="col-xs-1 text-center">
                                        <button class="btn btn-default" type="submit">
                                            <span class="sr-only"><fmt:message key="edit"/></span>
                                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                        </button>
                                    </td>
                                </form:form> 

                                <form:form method="post" action="${pageContext.request.contextPath}/musician/remove/${musician.id}" cssClass="form-horizontal">
                                    <td class="col-xs-1 text-center">
                                        <button class="btn btn-default" type="submit">
                                            <span class="sr-only"><fmt:message key="remove"/></span>
                                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                        </button>
                                    </td>
                                </form:form> 
                            </c:if>
                        </tr>
                    </c:forEach>
            </table>
        </div>

    </jsp:attribute>
</own:masterpage>
