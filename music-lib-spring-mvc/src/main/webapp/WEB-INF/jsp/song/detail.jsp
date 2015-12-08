<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Song detail">
    <jsp:attribute name="body">

        <div class="row">
            <h1>${song.title}</h1>

            <div class="col-md-4">  
                <dt>Artist: </dt>
                <dd>${song.musician.artistName}</dd>

                <dt>Genre: </dt>
                <dd>${song.genre.title}</dd>

                <dt>Album </dt>
                <dd>${song.album.title}</dd>

                <dt>Description: </dt>
                <dd>${song.commentary}</dd>

                <dt>Bitrate: </dt>
                <dd>${song.bitrate}</dd>

                <div class="col-md-2 col-md-offset-1 allow-vertical-space">
                    <a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/song/addYoutubeLink/${song.id}" role="button">
                        <c:choose>
                            <c:when test="${song.youtubeLink != null}">
                                Edit YouTube link
                            </c:when>
                            <c:otherwise>
                                Add YouTube link
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
