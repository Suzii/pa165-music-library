<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
<jsp:attribute name="body">

    <div class="jumbotron">
        <h1>${title}</h1>

        <p class="lead">Whatever dummy text! </p>

        <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/album/create"
              role="button">Create new</a></p>
    </div>
    
    <div class="row">
        <table class="table">
            <thead>
            <tr>
                <th>Num</th>
                <th>Album art</th>
                <th>Title</th>
                <th>Commentary</th>
                <th>Date of release</th>
                <th>Album art mime type</th>
                <th>Songs</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="count" value="0" scope="page"/>
            <c:forEach items="${albums}" var="album">
                <c:set var="count" value="${count + 1}" scope="page"/>
                <tr>
                    <td>${count}.</td>
                    <td>
                        <img src="${pageContext.request.contextPath}/album/albumImage/${album.id}"
                             style="max-height:30%; max-width:30%">
                    </td>
                    <td><b><c:out value="${album.title}"/></b></td>
                    <td>
                        <c:if test="${empty album.commentary}">
                            <c:out value="N/A"/>
                        </c:if>
                        <c:if test="${not empty album.commentary}">
                            <c:out value="${album.commentary}"/>
                        </c:if>
                    </td>
                    <td><fmt:formatDate value="${album.dateOfRelease}" pattern="yyyy-MM-dd"/></td>
                    <td><c:out value="${album.albumArtMimeType}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</jsp:attribute>
</own:masterpage>
