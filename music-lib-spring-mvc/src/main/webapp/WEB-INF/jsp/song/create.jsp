<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Create Song">
    <jsp:attribute name="body">

        <form:form method="POST" 
                   action="${pageContext.request.contextPath}/song/create"
                   acceptCharset=""
                   modelAttribute="songCreate" 
                   cssClass="form-horizontal">

            <h2><fmt:message key="songs.create_new_song"/></h2>
            <c:if test="${not empty album}">
                <p class="lead"> This song will be part of an album ${album.title}</p>
                <form:hidden path="albumId"/>
            </c:if>

            <div class="form-group ${title_error?'has-error':''}">
                <form:label path="title" cssClass="col-sm-2 control-label"><fmt:message key="songs.title"/></form:label>
                    <div class="col-sm-10">
                    <form:input path="title" cssClass="form-control"/>
                    <form:errors path="title" cssClass="help-block"/>
                </div>
            </div>

            <div class="form-group ${musicianId_error?'has-error':''}">
                <form:label path="musicianId" cssClass="col-sm-2 control-label"><fmt:message key="songs.artist"/></form:label>
                    <div class="col-sm-10">
                    <form:select path="musicianId" cssClass="form-control">
                        <c:forEach items="${musicians}" var="m">
                            <form:option value="${m.id}">${m.artistName}</form:option>
                        </c:forEach>
                    </form:select>
                    <p class="help-block"><form:errors path="musicianId" cssClass="error"/></p>
                </div>
            </div>

            <div class="form-group ${genreId_error?'has-error':''}">
                <form:label path="genreId" cssClass="col-sm-2 control-label"><fmt:message key="songs.genre"/></form:label>
                    <div class="col-sm-10">
                    <form:select path="genreId" cssClass="form-control">
                        <c:forEach items="${genres}" var="g">
                            <form:option value="${g.id}">${g.title}</form:option>
                        </c:forEach>
                    </form:select>
                    <p class="help-block"><form:errors path="genreId" cssClass="error"/></p>
                </div>
            </div>

            <div class="form-group ${commentary_error?'has-error':''}">
                <form:label path="commentary" cssClass="col-sm-2 control-label"><fmt:message key="songs.description"/></form:label>
                    <div class="col-sm-10">
                    <form:textarea cols="80" rows="5" path="commentary" cssClass="form-control"/>
                    <form:errors path="commentary" cssClass="help-block"/>
                </div>
            </div>

            <div class="form-group ${bitrate_error?'has-error':''}" >
                <form:label path="bitrate" cssClass="col-sm-2 control-label"><fmt:message key="songs.bitrate"/></form:label>
                    <div class="col-sm-10">
                    <form:input path="bitrate" cssClass="form-control"/>
                    <form:errors path="bitrate" cssClass="help-block"/>
                </div>
            </div>

            <button class="btn btn-primary col-sm-2 pull-right allow-vertical-space" type="submit"><fmt:message key="create"/></button>
        </form:form>
    </jsp:attribute>
</own:masterpage>
