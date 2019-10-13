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
       <h1>Armá tu recorrido!!</h1>
    </jsp:body>
</t:base>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>
<c:set var="modal"><ex:getManifestAssets value="modal.js"/></c:set>
<script src="<c:url value="${commons}"/>"></script>
<script src="<c:url value="${modal}"/>"></script>
</body>
</html>
