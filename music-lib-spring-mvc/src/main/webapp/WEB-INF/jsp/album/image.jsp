<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage>
    <jsp:attribute name="title">${album.title}</jsp:attribute>
    <jsp:attribute name="body">

        <div class="column">
            <div class="row">
                <h1><fmt:message key="albums.changeImageOf"/><b>&nbsp;${album.title}</b></h1>

                <div class="col-md-3">
                    <img src="${pageContext.request.contextPath}/album/albumImage/${album.id}"
                         style="max-height:200px; max-width:200px">
                </div>

                <div class="col-md-4">

                    <form method="POST" enctype="multipart/form-data"
                          action="${pageContext.request.contextPath}/album/upload/${album.id}">
                        File to upload: <input type="file" name="file"><br />
                        <input type="submit" value="Upload"> Press here to upload the file!
                    </form>
                </div>

            </div>
        </div>

    </jsp:attribute>
</own:masterpage>
