<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<%--<own:masterpage title="Create genre">--%>
<own:masterpage>
    <jsp:attribute name="body">
        <div class="genresCreate">

            <a href="${pageContext.request.contextPath}/genre" class="btn btn-default" role="button">
                <span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>                
                <fmt:message key="back"/>
            </a>

            <!-- title -->
            <div class="page-header">
                <h1>
                    <fmt:message key="genres.create"/>
                </h1>
            </div>

            <form:form method="post" 
                       action="${pageContext.request.contextPath}/genre/create"
                       modelAttribute="genreCreate" 
                       cssClass="form-horizontal">

                <div class="form-group ${title_error?'has-error':''}">
                    <form:label path="title" cssClass="col-sm-2 control-label"><fmt:message key="genres.name"/></form:label>
                        <div class="col-sm-10">
                        <form:input path="title" cssClass="form-control"/>
                        <form:errors path="title" cssClass="help-block"/>
                        <span class="bg-danger"><fmt:message key="genres.unique_name_warning"/></span>
                    </div>
                </div>

                <button class="btn btn-primary createBtn center-block allow-vertical-space" type="submit"><fmt:message key="submit"/></button>

            </form:form>
        </div>
    </jsp:attribute>
</own:masterpage>
