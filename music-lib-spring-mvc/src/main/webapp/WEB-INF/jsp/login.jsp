<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Login">
    <jsp:attribute name="body">

        <form:form method="POST"
                   action="j_spring_security_check"
                   acceptCharset=""
                   modelAttribute="user"
                   cssClass="form-horizontal">

            <h2 class="form-signin-heading">Please sign in</h2>

            <table>
                <tr>
                    <td>User:</td>
                    <td><input type='text' name='user' value=''></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type='password' name='pass' /></td>
                </tr>

            </table>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form:form>

    </jsp:attribute>
</own:masterpage>