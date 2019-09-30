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

<script src="dist/<ex:getManifestAssets value="commons.js"/>"></script>
<script src="dist/<ex:getManifestAssets value="travel.js"/>"></script>
</body>
</html>