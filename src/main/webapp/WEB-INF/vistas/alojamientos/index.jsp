<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<t:base>
    <jsp:body>
        <div class="container">
            <div id="root"></div>
        </div>
    </jsp:body>
</t:base>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>
<c:set var="alojamientos"><ex:getManifestAssets value="alojamientos.js"/></c:set>
<script src="<c:url value="${commons}"/>"></script>
<script src="<c:url value="${alojamientos}"/>"></script>
</body>
</html>
