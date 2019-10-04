<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<t:base>
    <jsp:body>
        <div id="root"></div>
        <%--        <ul class="list-group">
                    <c:forEach var="pais" items="${paises}">
                        <li class="list-group-item">
                            <a href="countries/${pais.getName()}">${pais.getName()}</a>
                        </li>
                    </c:forEach>
                </ul>--%>
    </jsp:body>
</t:base>

<script src="<c:url value="/js/jquery-1.11.3.min.js" />"></script>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>
<c:set var="countries"><ex:getManifestAssets value="countries.js"/></c:set>
<script src="<c:url value="${commons}"/>"></script>
<script src="<c:url value="${countries}"/>"></script>
</body>
</html>