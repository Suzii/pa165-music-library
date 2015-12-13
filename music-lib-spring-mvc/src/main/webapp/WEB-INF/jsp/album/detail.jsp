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
            <h1><fmt:message key="albums.detail"/><b>&nbsp;${album.title}</b></h1>

            <div class="col-md-3">
                <img src="${pageContext.request.contextPath}/album/albumImage/${album.id}"
                     style="max-height:200px; max-width:200px">
            </div>

            <div class="col-md-4">
                <dt><fmt:message key="albums.title"/></dt>
                <dd>${album.title}</dd>

                <dt><fmt:message key="albums.commentary"/></dt>
                <dd>${album.commentary}</dd>

                <dt><fmt:message key="albums.dateOfRelease"/></dt>
                <dd><fmt:formatDate value="${album.dateOfRelease}" type="date" dateStyle="long"/></dd>

                <dt><fmt:message key="albums.mimeType"/></dt>
                <dd>${album.albumArtMimeType}</dd>

                <dt><fmt:message key="albums.songs"/></dt>
                <dd><a class="btn btn-mg btn-primary"
                       href="${pageContext.request.contextPath}/song/index"
                       role="button"><fmt:message key="albums.songList"/></a></dd>

            </div>


        </div>

    </jsp:attribute>
</own:masterpage>
