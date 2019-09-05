<%--
  Created by IntelliJ IDEA.
  User: pds.gomez
  Date: 2019-06-10
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title>Mail Enviado</title>
    <link href="../css/bootstrap.min.css" rel="stylesheet" >
    <!-- Bootstrap theme -->
    <link href="../css/bootstrap-theme.min.css" rel="stylesheet">
</head>
<body>
    <a href="torneo/${torneo}" class="btn btn-info pull-right" role="button">Volver a Torneos</a>
    <c:if test="${not empty error}">
        <h4 class="alert alert-danger"><span>${error}</span></h4>
        <br>
    </c:if>
    <c:if test="${not empty enviado}">
        <h4 class="alert alert-success"><span>${enviado}</span></h4>
        <br>
    </c:if>
</body>
</html>
