<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
    <jsp:attribute name="title">${album.title}</jsp:attribute>
    <jsp:attribute name="body">

        <div class="row">
            <div class="col-md-3">
                <img src="${pageContext.request.contextPath}/album/albumImage/${album.id}"
                     style="max-height:200px; max-width:200px">
            </div>

            <div class="col-md-6">
                <h1>${album.title}</b></h1>
                <dt><fmt:message key="albums.commentary"/></dt>
                <dd>${album.commentary}</dd>

                <dt><fmt:message key="albums.dateOfRelease"/></dt>
                <dd><fmt:formatDate value="${album.dateOfRelease}" type="date" dateStyle="long"/></dd>

                <dt><fmt:message key="albums.mimeType"/></dt>
                <dd>${album.albumArtMimeType}</dd>

            </div>
            <div class="col-md-3">
                <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/song/create?albumId=${album.id}" role="button"><fmt:message key="albums.add_song"/></a></p>
            </div>
        </div>

        <div class="result"></div>

        <div class="row">
            <table class="table">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Title</th>
                        <th>Artist</th>
                        <th>Genre</th>        
                        <c:if test="${isAdmin}">
                            <th class="text-center"><fmt:message key="remove"/></th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${songs}" var="song">
                        <c:set var="count" value="${count + 1}" scope="page"/>
                        <tr>
                            <td>${count}.</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/song/detail/${song.id}">
                                    <b><c:out value="${song.title}"/></b>
                                </a>
                            </td>


                            <td>
                                <c:if test="${not empty song.musician}">
                                    <a href="${pageContext.request.contextPath}/musician/detail/${song.musician.id}">
                                        <c:out value="${song.musician.artistName}"/>
                                    </a>
                                </td>
                            </c:if>
                            <td>
                                <c:out value="${not empty song.genre ? song.genre.title : '-'}"/>
                            </td>

                            <c:if test="${isAdmin}">
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
