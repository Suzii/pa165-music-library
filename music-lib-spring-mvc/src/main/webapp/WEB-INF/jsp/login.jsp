<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Login">
    <jsp:attribute name="body">

        <form:form method="POST" 
                   action="${pageContext.request.contextPath}/login"
                   acceptCharset=""
                   modelAttribute="user" 
                   cssClass="form-horizontal">

            <h2 class="form-signin-heading">Please sign in</h2>
            <div class="form-group ${email_error?'has-error':''}">
                <form:label path="email" cssClass="col-sm-2 control-label">Email</form:label>
                    <div class="col-sm-10">
                    <form:input path="email" cssClass="form-control"/>
                    <form:errors path="email" cssClass="help-block"/>
                </div>
            </div>
                <div class="form-group ${password_error?'has-error':''}">
                <form:label path="password" cssClass="col-sm-2 control-label">Password</form:label>
                    <div class="col-sm-10">
                    <form:input path="password" type="password" cssClass="form-control"/>
                    <form:errors path="password" cssClass="help-block"/>
                </div>
            </div>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form:form>

    </jsp:attribute>
</own:masterpage>