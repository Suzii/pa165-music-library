<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<own:masterpage title="Create a Musician">
    <jsp:attribute name="head">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <script>
        $(function() {
        $( "#dateOfBirth" ).datepicker();
        });
        </script>
    </jsp:attribute>
    <jsp:attribute name="body">

        <form:form method="POST" 
                   action="${pageContext.request.contextPath}/musician/create"
                   acceptCharset=""
                   modelAttribute="musicianCreate" 
                   cssClass="form-horizontal">

            <h2>Create a new Musician</h2>

            <div class="form-group ${artistName_error?'has-error':''}">
                <form:label path="artistName" cssClass="col-sm-2 control-label">Artist Name</form:label>
                    <div class="col-sm-10">
                    <form:input path="artistName" cssClass="form-control"/>
                    <form:errors path="artistName" cssClass="help-block"/>
                </div>
            </div>

            <div class="form-group ${dateOfBirth_error?'has-error':''}">
                <form:label path="dateOfBirth" cssClass="col-sm-2 control-label">Date of birth</form:label>
                    <div class="col-sm-10">
                    <form:input path="dateOfBirth" cssClass="form-control"/>
                    <form:errors path="dateOfBirth" cssClass="help-block"/>
                </div>
            </div>

            <button class="btn btn-primary col-sm-2 pull-right allow-vertical-space" type="submit">Create</button>
        </form:form>
    </jsp:attribute>
</own:masterpage>