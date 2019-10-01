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
    </jsp:body>
</t:base>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>
<c:set var="travel"><ex:getManifestAssets value="travel.js"/></c:set>
<script src="<c:url value="${commons}"/>"></script>
<script src="<c:url value="${travel}"/>"></script>
</body>
</html>