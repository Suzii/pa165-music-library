<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Musicians">
    <jsp:attribute name="body">

        <div class="jumbotron">
            <h1><fmt:message key="musicians.heading"/></h1>
            <p class="lead"><fmt:message key="musicians.subheading"/></p>
            <p align="right">
                <a class="btn btn-lg btn-success btn-jumbotron" href="${pageContext.request.contextPath}/musician/create" role="button"><fmt:message key="create"/></a>
            </p>
        </div>

        <div class="row">
            <table class="table">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th><fmt:message key="musician.name"/></th>
                        <th class="text-center"><fmt:message key="musician.sex"/></th>
                        <th class="text-center"><fmt:message key="musician.birthdate"/></th>
                        <th class="text-center"><fmt:message key="edit"/></th>
                        <th class="text-center"><fmt:message key="remove"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${musicians}" var="musician">
                        <tr>
                            <td class="col-xs-1">${musician.id}.</td>
                            <td class="col-xs-3"><c:out value="${musician.artistName}"/></td>
                            <td class="col-xs-3 text-center"><c:out value="${musician.sex}"/></td>
                            <td class="col-xs-3 text-center"><fmt:formatDate value="${musician.dateOfBirth}" pattern="yyyy-MM-dd"/></td>

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

                        </tr>
                    </c:forEach>
            </table>
        </div>

    </jsp:attribute>
</own:masterpage>
