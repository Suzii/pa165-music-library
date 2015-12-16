<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Song detail">
    <jsp:attribute name="body">
        <div class="row">
            
            <a href="${pageContext.request.contextPath}/song" class="btn btn-default" role="button">
                <span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>                
                <fmt:message key="back"/>
            </a>
            
            <div class="page-header">
                <h1>
                     <fmt:message key="songs.detail"/><b>&nbsp;${song.title}</b>
                </h1>
            </div>

            <div class="col-md-4">  
                <dl class="dl-horizontal">
                    <dt><fmt:message key="songs.artist"/> </dt>
                    <dd>${not empty song.musician ? song.musician.artistName : '-'}</dd>

                    <dt><fmt:message key="songs.genre"/> </dt>
                    <dd>${not empty song.genre ? song.genre.title : '-'}</dd>

                    <dt><fmt:message key="songs.album"/> </dt>
                    <dd>${not empty song.album ? song.album.title : '-'}</dd>

                    <dt><fmt:message key="songs.description"/> </dt>
                    <dd>${song.commentary}</dd>

                    <dt><fmt:message key="songs.bitrate"/> </dt>
                    <dd>${song.bitrate}</dd>
                </dl>
                <div class="col-md-2 col-md-offset-1 allow-vertical-space">
                    <a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/song/addYoutubeLink/${song.id}" role="button">
                        <c:choose>
                            <c:when test="${not empty song.youtubeLink}">
                                <fmt:message key="songs.edit_youtube_link"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="songs.add_youtube_link"/>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </div>
            </div>

            <div class="col-md-8">
                <c:if test="${song.youtubeLink != null}">
                    <iframe id="ytplayer" type="text/html" width="640" height="390"
                            src="http://www.youtube.com/embed/${song.youtubeLink}?autoplay=1&origin=http://example.com"
                            frameborder="0"/>
                </c:if>
            </div>
        </div>

    </jsp:attribute>
</own:masterpage>
