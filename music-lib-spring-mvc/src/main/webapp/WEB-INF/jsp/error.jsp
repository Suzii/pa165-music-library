<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Error">
    <jsp:attribute name="body">

        <h1><fmt:message key="error.heading"/></h1>
        
        <c:choose>
            <c:when test="${not empty errMsg}">
                <p>${errMsg}</p>
            </c:when>
            <c:otherwise>
                <fmt:message key="error.general"/>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</own:masterpage>