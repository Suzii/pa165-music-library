<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Add YouTube link">
    <jsp:attribute name="body">

        <form:form method="post" 
                   action="${pageContext.request.contextPath}/song/addYoutubeLink/${songAddYoutubeLink.songId}"
                   acceptCharset=""
                   modelAttribute="songAddYoutubeLink" 
                   cssClass="form-horizontal">

            <h2>Add YouTube link for song ${songTitle}</h2>

            <form:hidden path="songId" />
            <div class="form-group ${youtubeLink_error?'has-error':''}">
                <form:label path="youtubeLink" cssClass="col-sm-2 control-label">YouTube link: </form:label>
                    <div class="col-sm-8">
                        <div class="input-group">
                            <div class="input-group-addon">https://www.youtube.com/watch?v=</div>
                            <form:input path="youtubeLink" cssClass="form-control"/>
                        </div>
                    <form:errors path="youtubeLink" cssClass="help-block"/>
                </div>
                <button class="btn btn-primary col-sm-2" type="submit">Add</button>
            </div>

        </form:form>


    </jsp:attribute>
</own:masterpage>