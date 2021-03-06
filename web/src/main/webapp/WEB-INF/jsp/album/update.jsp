<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
    <jsp:attribute name="title"><fmt:message key="albums.update" /></jsp:attribute>
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
        
        <form:form method="POST"
                   action="${pageContext.request.contextPath}/album/update/${albumUpdate.id}"
                   acceptCharset=""
                   modelAttribute="albumUpdate"
                   cssClass="form-horizontal">

            <div class="page-header">
                <h1>
                    <fmt:message key="albums.update"/>
                </h1>
            </div>

            <div class="form-group ${title_error?'has-error':''}">
                <form:label path="title" cssClass="col-sm-2 control-label"><fmt:message key="albums.title"/></form:label>
                    <div class="col-sm-10">
                    <form:input path="title" cssClass="form-control"/>
                    <form:errors path="title" cssClass="help-block"/>
                </div>
            </div>

            <div class="form-group ${commentary_error?'has-error':''}">
                <form:label path="commentary" cssClass="col-sm-2 control-label"><fmt:message key="albums.commentary"/></form:label>
                <div class="col-sm-10">
                    <form:input path="commentary" cssClass="form-control"/>
                    <form:errors path="commentary" cssClass="help-block"/>
                </div>
            </div>

            <div class="form-group ${dateOfRelease_error?'has-error':''}">
                <form:label path="dateOfRelease" cssClass="col-sm-2 control-label"><fmt:message key="albums.dateOfRelease"/></form:label>
                <div class="col-sm-10">
                    <fmt:formatDate var="fmtDate" value="${albumUpdate.dateOfRelease}" pattern="MM/dd/yyyy"/>
                    <form:input path="dateOfRelease" id="datepicker" value="${fmtDate}" cssClass="form-control"/>
                    <form:errors path="dateOfRelease" cssClass="help-block"/>
                </div>
            </div>

            <button class="btn btn-primary updateBtn center-block allow-vertical-space" type="submit"><fmt:message key="edit"/></button>
        </form:form>
    </jsp:attribute>
</own:masterpage>
