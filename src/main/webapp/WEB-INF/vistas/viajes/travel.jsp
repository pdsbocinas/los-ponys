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
                <div class="col" style="display: flex;">
                    <div id="root"></div>
                    <c:forEach var="viaje" items="${viajes}">
                        <div class="card" style="width: 18rem;" >
                            <div class="card-body">
                                <h5 class="card-title">${viaje.getTitulo()}</h5>
                                <p class="card-text">Crea y comparti tu experiencia de viajar.</p>
                                <a href="viajes/${viaje.getId()}">${viaje.getTitulo()}</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:base>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>
<c:set var="modal"><ex:getManifestAssets value="modal.js"/></c:set>
<script src="<c:url value="${commons}"/>"></script>
<script src="<c:url value="${modal}"/>"></script>
</body>
</html>
