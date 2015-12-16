<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
    <jsp:attribute name="title"><fmt:message key="albums.create"/></jsp:attribute>
    <jsp:attribute name="scripts">
        <script>
            $(function () {
                $("#datepicker").datepicker();
            });
        </script>
    </jsp:attribute>
    <jsp:attribute name="body">

            <a href="${pageContext.request.contextPath}/album" class="btn btn-default" role="button">
                <span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>                
                <fmt:message key="back"/>
            </a>

            <!-- title -->
            <div class="page-header">
                <h1>
                   <fmt:message key="albums.create"/>
                </h1>
            </div>
                
        <form:form method="POST"
                   action="${pageContext.request.contextPath}/album/create"
                   acceptCharset=""
                   modelAttribute="albumCreate"
                   cssClass="form-horizontal">

            <div class="form-group ${title_error?'has-error':''}">
                <form:label path="title" cssClass="col-sm-2 control-label"><fmt:message key="albums.title"/></form:label>
                <div class="col-sm-10">
                    <form:input path="title" cssClass="form-control"/>
                    <form:errors path="title" cssClass="help-block"/>
                </div>
            </div>

            <div class="form-group ${title_error?'has-error':''}">
                <form:label path="commentary" cssClass="col-sm-2 control-label"><fmt:message key="albums.commentary"/></form:label>
                <div class="col-sm-10">
                    <form:input path="commentary" cssClass="form-control"/>
                    <form:errors path="commentary" cssClass="help-block"/>
                </div>
            </div>

            <div class="form-group ${title_error?'has-error':''}">
                <form:label path="dateOfRelease" cssClass="col-sm-2 control-label"><fmt:message key="albums.dateOfRelease"/></form:label>
                <div class="col-sm-10">
                    <form:input path="dateOfRelease" id="datepicker" cssClass="form-control"/>
                    <form:errors path="dateOfRelease" cssClass="help-block"/>
                </div>
            </div>

            <%--<div class="form-group ${title_error?'has-error':''}">--%>
                <%--<form:label path="albumArt" cssClass="col-sm-2 control-label"><fmt:message key="albums.albumArt"/></form:label>--%>
                <%--<div class="col-sm-10">--%>
                        <%--&lt;%&ndash;<form path="albumArt" action="UploadServlet" method="post"&ndash;%&gt;--%>
                        <%--&lt;%&ndash;enctype="multipart/form-data">&ndash;%&gt;--%>
                    <%--<form:input type="file" name="file" path="albumArt" cssClass="form-control"/>--%>
                        <%--&lt;%&ndash;</form>&ndash;%&gt;--%>
                <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group ${title_error?'has-error':''}">--%>
                <%--<form:label path="albumArtMimeType" cssClass="col-sm-2 control-label"><fmt:message key="albums.mimeType"/></form:label>--%>
                <%--<div class="col-sm-10">--%>
                    <%--<form:input path="albumArtMimeType" cssClass="form-control"/>--%>
                    <%--<form:errors path="albumArtMimeType" cssClass="help-block"/>--%>
                <%--</div>--%>
            <%--</div>--%>

            <button class="btn btn-primary createBtn center-block allow-vertical-space" type="submit"><fmt:message key="submit"/></button>
        </form:form>
    </jsp:attribute>
</own:masterpage>
