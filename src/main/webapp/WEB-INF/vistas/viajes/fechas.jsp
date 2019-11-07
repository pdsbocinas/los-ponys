<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Fechas</title>
</head>
<body>

<t:base>
    <jsp:body>
       <h1>fechas</h1>
<%--        <div id="root"></div>--%>
        <div class="container">
          <p>${fechaDesde}</p>

          <p>${fechaHasta}</p>

        </div>

    </jsp:body>
</t:base>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>
<%--<c:set var="route"><ex:getManifestAssets value="route.js"/></c:set>--%>

<script src="<c:url value="${commons}"/>"></script>
<%--<script src="<c:url value="${route}"/>"></script>--%>
</body>
</html>
