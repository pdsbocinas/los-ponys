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
        <div class="banner">
            <h1>Viajes</h1>
        </div>
        <div class="container-fluid fix-padding">
            <div class="row">
                <div class="col">
                    <div class="card" style="width: 18rem;">
                        <div class="card-body">
                            <h5 class="card-title">Crear viaje</h5>
                            <p class="card-text">Crea y comparti tu experiencia de viajar.</p>
                            <a href="<c:url value="viajes/crear"/>" class="btn btn-primary">Empezar</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
</body>
</html>
