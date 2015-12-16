<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
    <jsp:attribute name="title"><fmt:message key="musicians.update" /></jsp:attribute>
    <jsp:attribute name="scripts">
        <script>
            $(function () {
                $("#datepicker").datepicker({
                changeMonth: true,
                changeYear: true,
                yearRange: "-1000:+0",
                maxDate: "+0"});
            });
        </script>
    </jsp:attribute>
    <jsp:attribute name="body">
        
        <a href="${pageContext.request.contextPath}/musician" class="btn btn-default" role="button">
                <span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>
                <fmt:message key="back"/>
        </a>
        
        <form:form method="POST" 
                   action="${pageContext.request.contextPath}/musician/update/${musicianUpdate.id}"
                   acceptCharset=""
                   modelAttribute="musicianUpdate" 
                   cssClass="form-horizontal">

            <div class="page-header">
                <h1>
                    <fmt:message key="musicians.update" />
                </h1>
            </div>
                
            <div class="form-group ${artistName_error?'has-error':''}">
                <form:label path="artistName" cssClass="col-sm-2 control-label"><fmt:message key="musician.name"/></form:label>
                    <div class="col-sm-10">
                        <form:input path="artistName" cssClass="form-control"/>
                        <form:errors path="artistName" cssClass="help-block"/>
                    </div>
            </div>

            <div class="form-group ${dateOfBirth_error?'has-error':''}">
                <form:label path="dateOfBirth" cssClass="col-sm-2 control-label"><fmt:message key="musician.birthdate"/></form:label>
                    <div class="col-sm-10">
                        <fmt:formatDate var="fmtDate" value="${musicianUpdate.dateOfBirth}" pattern="MM/dd/yyyy"/>
                        <form:input path="dateOfBirth" id="datepicker" value="${fmtDate}" cssClass="form-control"/>
                        <form:errors path="dateOfBirth" cssClass="help-block"/>
                    </div>
            </div>

            <div class="row form-group ${sex_error?'has-error':''}">
                <form:label path="sex" cssClass="col-sm-2 control-label"><fmt:message key="musician.sex"/></form:label>
                    <div class="col-sm-6">                        
                        <form:errors path="sex" cssClass="help-block"/>
                    </div>
                    <div class="col-sm-2"><fmt:message key="musician.male"/><form:radiobutton path="sex" value="${male}" class="radio-inline" /></div>
                    <div class="col-sm-2"><fmt:message key="musician.female"/><form:radiobutton path="sex" value="${female}" class="radio-inline" /></div>
            </div>

            <button class="btn btn-primary updateBtn center-block allow-vertical-space" type="submit"><fmt:message key="edit"/></button>
        </form:form>
    </jsp:attribute>
</own:masterpage>