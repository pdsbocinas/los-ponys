<%--
  Created by IntelliJ IDEA.
  User: pds.gomez
  Date: 2019-09-06
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach var="pais" items="${paises}">
    <h3 class="panel-title">${pais.getName()}</h3>
</c:forEach>
</body>
</html>
