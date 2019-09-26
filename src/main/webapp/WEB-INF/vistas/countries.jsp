<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<t:base>
    <jsp:body>
        <div id="root"></div>
        <c:forEach var="pais" items="${paises}">
            <h3 class="panel-title">${pais.getName()}</h3>
        </c:forEach>
    </jsp:body>
</t:base>

<script src="<c:url value="/js/jquery-1.11.3.min.js" />"></script>
<script src="dist/<ex:getManifestAssets value="test.js"/>"></script>
</body>
</html>