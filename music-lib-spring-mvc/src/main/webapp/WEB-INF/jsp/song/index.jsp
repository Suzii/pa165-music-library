<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Songs">
    <jsp:attribute name="body">

        <div class="jumbotron">
            <h1>${title}</h1>
            <p class="lead">Whatever dummy text! </p>
            <p><a class="btn btn-lg btn-success" href="${pageContext.request.contextPath}/song/create" role="button">Create new</a></p>
        </div>

        <div class="row">
            <table class="table">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Title</th>
                        <th>Artist</th>
                        <th>Album</th>
                        <th>Genre</th>            
                        <th class="text-center"><fmt:message key="edit"/></th>
                        <th class="text-center"><fmt:message key="remove"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${songs}" var="song">
                        <tr>
                            <td>${song.id}</td>
                            <td><c:out value="${song.title}"/></td>
                            <td><c:out value="${not empty song.musician ? song.musician.artistName : '-'}"/></td>
                            <td><c:out value="${not empty song.album ? song.album.title : '-'}"/></td>
                            <td><c:out value="${not empty song.genre ? song.genre.title : '-'}"/></td>

                            <form:form method="get" action="${pageContext.request.contextPath}/song/update/${song.id}" cssClass="form-horizontal">
                                <td class="col-xs-1 text-center">
                                    <button class="btn btn-default" type="submit">
                                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                        <span class="sr-only"><fmt:message key="edit"/></span>
                                    </button>
                                </td>
                            </form:form> 

                            <form:form method="post" action="${pageContext.request.contextPath}/song/remove/${song.id}" cssClass="form-horizontal">
                                <td class="col-xs-1 text-center">
                                    <button class="btn btn-default" type="submit">
                                        <span class="sr-only"><fmt:message key="remove"/></span>
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                    </button>
                                </td>
                            </form:form> 
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </jsp:attribute>
</own:masterpage>