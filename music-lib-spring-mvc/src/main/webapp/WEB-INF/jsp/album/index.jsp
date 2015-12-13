<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
    <jsp:attribute name="title"><fmt:message key="albums.mainTitle"/></jsp:attribute>

    <jsp:attribute name="body">

    <div class="jumbotron">
        <h1><fmt:message key="albums.mainTitle"/></h1>

        <p class="leDad"><fmt:message key="albums.subTitle"/></p>

        <p align="right"><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/album/create"
                            role="button"><fmt:message key="albums.create"/></a></p>
    </div>
    
    <div class="row">
        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="albums.num"/></th>
                <th><fmt:message key="albums.albumArt"/></th>
                <th><fmt:message key="albums.title"/></th>
                <th><fmt:message key="albums.commentary"/></th>
                <th><fmt:message key="albums.dateOfRelease"/></th>
                <th><fmt:message key="albums.mimeType"/></th>
                <th><fmt:message key="albums.songs"/></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="count" value="0" scope="page"/>
            <c:forEach items="${albums}" var="album">
                <c:set var="count" value="${count + 1}" scope="page"/>
                <tr>
                    <td>${count}.</td>
                    <td>
                        <img src="${pageContext.request.contextPath}/album/albumImage/${album.id}"
                             style="max-height:50px; max-width:50px">
                    </td>
                    <td><b><a href="${pageContext.request.contextPath}/album/detail/${album.id}" >
                        <c:out value="${album.title}"/></a></b></td>
                    <td>
                        <c:if test="${empty album.commentary}">
                            <fmt:message key="n_a"/>
                        </c:if>
                        <c:if test="${not empty album.commentary}">
                            <c:out value="${album.commentary}"/>
                        </c:if>
                    </td>
                    <td><fmt:formatDate value="${album.dateOfRelease}" pattern="yyyy-MM-dd"/></td>
                    <td><c:out value="${album.albumArtMimeType}"/></td>
                    <td><a class="btn btn-mg btn-primary"
                           href="${pageContext.request.contextPath}/album/songs/${album.id}"
                           role="button"><fmt:message key="albums.songList"/></a></td>

                    <form:form method="get" action="${pageContext.request.contextPath}/album/update/${album.id}" cssClass="form-horizontal">
                        <td class="col-xs-1 text-center">
                            <button class="btn btn-default" type="submit">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                <span class="sr-only"><fmt:message key="edit"/></span>
                            </button>
                        </td>
                    </form:form>

                    <form:form method="post" action="${pageContext.request.contextPath}/album/remove/${album.id}" cssClass="form-horizontal">
                        <td class="col-xs-1 text-center">
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

</jsp:attribute>
</own:masterpage>
