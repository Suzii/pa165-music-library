<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
    <jsp:attribute name="scripts">
        <!--    This is how you include page-specific javascript files to the page. 
            This section will be rendered at the very bottom pg the page after script tags
            that include Javascript libraries like jQuery and jQuery UI,
            so there is no need to include then on every page.-->
        <script src="${pageContext.request.contextPath}/js/test.js"></script>
    </jsp:attribute>
    <jsp:attribute name="body">

        <div class="jumbotron">
            <c:choose>
                <c:when test="${not empty userData}">
                    <h1>
                        <fmt:message key="home.hi">
                            <fmt:param value="${userData.firstName}"/>
                        </fmt:message>
                    </h1>
                    <!-- TODO remove this line -->
                    <p>You are <c:if test="${!isAdmin}">not</c:if> an admin.</p>
                    <p class="lead"><fmt:message key="home.subheading"/></p>
                </c:when>
                <c:otherwise>
                    <h1><fmt:message key="home.heading"/></h1>
                    <p class="lead"><fmt:message key="home.subheading"/></p>
                    <p><fmt:message key="home.log_in_notice"/></p>
                    <p align="right">
                        <a class="btn btn-lg btn-success btn-jumbotron" href="${pageContext.request.contextPath}/login" role="button">
                            <fmt:message key="sign_in"/>
                        </a>
                    </p>
                </c:otherwise>
            </c:choose> 

        </div>

        TODO: display songs, albums, artists...

    </jsp:attribute>
</own:masterpage>
