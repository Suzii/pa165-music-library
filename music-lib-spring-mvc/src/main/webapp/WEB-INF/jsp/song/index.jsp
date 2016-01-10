<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
    <jsp:attribute name="title"><fmt:message key="songs.heading"/></jsp:attribute>

    <jsp:attribute name="body">
        <div class="jumbotron">
            <h1><fmt:message key="songs.heading"/></h1>
            <p class="lead"><fmt:message key="songs.subheading"/></p>

            <c:if test="${isAdmin}">
                <p align="right">
                    <a class="btn btn-lg btn-success btn-jumbotron" href="${pageContext.request.contextPath}/song/create" role="button">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                        <fmt:message key="create_new"/>
                    </a>
                </p>
            </c:if>
        </p>
    </div>

    <div class="row">
        <table class="table">
            <thead>
                <tr>
                    <th><fmt:message key="num"/></th>
                    <th><fmt:message key="songs.title"/></th>
                    <th><fmt:message key="songs.artist"/></th>
                    <th><fmt:message key="songs.album"/></th>
                    <th class="text-center">Genre</th>     
                        <c:if test="${isAdmin}">
                        <th class="text-center"><fmt:message key="edit"/></th>
                        <th class="text-center"><fmt:message key="remove"/></th>
                        </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${songs}" var="song">
                    <c:set var="count" value="${count + 1}" scope="page"/>
                    <tr>
                        <td class="lead-column">${count}.</td>
                        <td class="lead-column">
                            <a href="${pageContext.request.contextPath}/song/detail/${song.id}">
                                <c:out value="${song.title}"/>
                            </a>
                        </td>
                        <td>
                            <c:if test="${not empty song.musician}">
                                <a href="${pageContext.request.contextPath}/musician/detail/${song.musician.id}">
                                    <c:out value="${song.musician.artistName}"/>    
                                </a>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${not empty song.album}">
                                <a href="${pageContext.request.contextPath}/album/detail/${song.album.id}">
                                    <c:out value="${song.album.title}"/>    
                                </a>
                            </c:if>
                        </td>
                        <td class="text-center"><c:out value="${not empty song.genre ? song.genre.title : '-'}"/></td>

                        <c:if test="${isAdmin}">
                            <form:form method="get" action="${pageContext.request.contextPath}/song/update/${song.id}" cssClass="form-horizontal">
                                <td class="col-xs-1 text-center">
                                    <button class="btn btn-default" type="submit">
                                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                        <span class="sr-only"><fmt:message key="edit"/></span>
                                    </button>
                                </td>
                            </form:form> 

                            <form:form method="post" action="${pageContext.request.contextPath}/song/remove/${song.id}" cssClass="form-horizontal">
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
            </tbody>
        </table>
    </div>
</jsp:attribute>
</own:masterpage>