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
            <h1>${userName}, welcome to your Music Library !</h1>
            <p>You are <c:if test="${!isAdmin}">not</c:if> an admin.</p>
            <p class="lead">Do you feel like listening to some music? You are at the right place! </p>
        </div>

        <div class="row">
            <table class="table">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Title</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${songs}" var="song">
                        <tr>
                            <td>${song.id}</td>
                            <td><c:out value="${song.title}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </jsp:attribute>
</own:masterpage>
