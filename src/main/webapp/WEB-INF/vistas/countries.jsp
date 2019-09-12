<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="root"></div>
<c:forEach var="pais" items="${paises}">
    <h3 class="panel-title">${pais.getName()}</h3>
</c:forEach>
</body>
</html>