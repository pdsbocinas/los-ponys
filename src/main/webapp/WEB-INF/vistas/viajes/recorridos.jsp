<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<style>
    /* Always set the map height explicitly to define the size of the div
     * element that contains the map. */
    #map {
        height: 100%;
    }
    /* Optional: Makes the sample page fill the window. */
    html, body {
        height: 100%;
        margin: 0;
        padding: 0;
    }
</style>
<t:base>
    <jsp:body>
       <h1>Armá tu recorrido!!</h1>
        <div id="root"></div>

    </jsp:body>
</t:base>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>
<c:set var="route"><ex:getManifestAssets value="route.js"/></c:set>

<script src="<c:url value="${commons}"/>"></script>
<script src="<c:url value="${route}"/>"></script>
</body>
</html>
