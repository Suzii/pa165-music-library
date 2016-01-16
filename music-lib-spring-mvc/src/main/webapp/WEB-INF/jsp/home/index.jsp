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

        <div class="jumbotron homepage">
            <c:choose>
                <c:when test="${not empty userData}">
                    <h1>
                        <fmt:message key="home.hi">
                            <fmt:param value="${userData.firstName}"/>
                        </fmt:message>
                    </h1>
                    <p class="lead"><fmt:message key="home.subheading"/></p>
                    
                    <div class="row homepagePanels">
                    
                        <div class=" col-lg-4 col-md-4 col-sm-4 panelAlbums">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                  <h3 class="panel-title">
                                      <img class="icon vinyl" src="${pageContext.request.contextPath}/images/vinyl.png" />
                                      <span><fmt:message key="home.random.albums"/></span>
                                  </h3>
                                </div>
                                <div class="panel-body">
                                    <c:forEach items="${albums}" var="album">
                                        <a href="${pageContext.request.contextPath}/album/detail/${album.id}">
                                                <img src="${pageContext.request.contextPath}/album/albumImage/${album.id}"
                                                     style="max-height:50px; max-width:50px">
                                                <span>${album.title}</span>
                                        </a>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                        <div class=" col-lg-4 col-md-4 col-sm-4 panelMusicians">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                     <h3 class="panel-title">
                                        <img class="icon microphone" src="${pageContext.request.contextPath}/images/mic.png" />
                                        <span><fmt:message key="home.random.musicians"/></span>
                                    </h3>
                                </div>
                                <div class="panel-body">
                                    <c:forEach items="${musicians}" var="musician">
                                        <a href="${pageContext.request.contextPath}/musician/detail/${musician.id}">
                                                <span>${musician.artistName}</span>
                                        </a>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>

                        <div class=" col-lg-4 col-md-4 col-sm-4 panelSongs">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                  <h3 class="panel-title">
                                      <span class="icon glyphicon glyphicon-music" aria-hidden="true"></span>
                                      <fmt:message key="home.random.songs"/>
                                  </h3>
                                </div>
                                <div class="panel-body">
                                    <c:forEach items="${songs}" var="song">
                                        <a href="${pageContext.request.contextPath}/song/detail/${song.id}">
                                                <span>${song.title}</span>
                                        </a>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    
                    </div>
                    
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

    </jsp:attribute>
</own:masterpage>
