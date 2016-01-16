<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Add YouTube link">
    <jsp:attribute name="body">
        <form:form method="POST" 
                   action="${pageContext.request.contextPath}/song/addYoutubeLink/${songAddYoutubeLink.songId}"
                   acceptCharset=""
                   modelAttribute="songAddYoutubeLink" 
                   cssClass="form-horizontal">

            <c:choose>
                <c:when test="${not empty youtubeLink}">
                    <h2><fmt:message key="songs.edit_youtube_link_for_song"/></h2>
                </c:when>
                <c:otherwise>
                    <h2><fmt:message key="songs.add_youtube_link_for_song"/></h2>
                </c:otherwise>
            </c:choose>

            <form:hidden path="songId" />
            <div class="form-group ${youtubeLink_error?'has-error':''}">
                
                <form:label path="youtubeLink" cssClass="col-sm-2 control-label"><fmt:message key="songs.youtube_link"/></form:label>
                    <div class="col-sm-8">
                        <div class="input-group">
                            <div class="input-group-addon">https://www.youtube.com/watch?v=</div>
                        <form:input path="youtubeLink" cssClass="form-control"/>
                    </div>
                    <form:errors path="youtubeLink" cssClass="help-block"/>
                </div>
                
                <c:choose>
                    <c:when test="${not empty youtubeLink}">
                        <button class="btn btn-primary col-sm-2" type="submit"><fmt:message key="edit"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-primary col-sm-2" type="submit"><fmt:message key="add"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </form:form>
    </jsp:attribute>
</own:masterpage>