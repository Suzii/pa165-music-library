<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="own" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<own:masterpage title="Musician detail">
    <jsp:attribute name="body">
        <div class="row">
        
            <a href="${pageContext.request.contextPath}/musician" class="btn btn-default" role="button">
                <span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>                
                <fmt:message key="back"/>
            </a>

            <div class="page-header">
                <h1>
                    <fmt:message key="musician.detail"/><b>&nbsp;${musician.artistName}</b>
                </h1>
            </div>

            <div class="col-md-4">  
                <dl class="dl-horizontal">
                    <dt><fmt:message key="musician.name"/></dt>
                    <dd>${musician.artistName}</dd>

                    <dt><fmt:message key="musician.sex"/></dt>
                    <dd><fmt:message key="${musician.sex}"/></dd>

                    <dt><fmt:message key="musician.birthdate"/></dt>
                    <dd><fmt:formatDate value="${musician.dateOfBirth}" pattern="yyyy-MM-dd"/></dd>
                </dl>
            </div>
        </div>

    </jsp:attribute>
</own:masterpage>
