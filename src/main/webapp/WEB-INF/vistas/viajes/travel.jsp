<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <h1 class="text-primary">Viajes</h1>
        </div>
        <div class="container-fluid fix-padding">
            <div class="row justify-content-center mb-2">
                <div class="col-4">
                    <div id="root"></div>
                </div>
            </div>

            <div class="row">
                <c:forEach var="viaje" items="${viajes}">
                    <div class="col-4" style="display: flex;">
                        <div class="card" style="width: 18rem;" >
                            <div class="card-body">
                                <h5 class="card-title">${viaje.getTitulo()}</h5>
                                <p class="card-text">Crea y comparti tu experiencia de viajar.</p>
<%--                                <a href="viajes/${viaje.getId()}">${viaje.getTitulo()}</a>--%>
                                <a href="viajes/${viaje.getId()}/destino">Editá tu viaje</a>
                            </div>
                            <form:form action="eliminar-viaje" method="GET">
                                <input type="hidden" name="id" value="${viaje.getId()}">
                                <button type="submit">Borrar viaje</button>
                            </form:form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </jsp:body>
</t:base>
<script>
  var user_id = "${user_id}";
  var email = "${email}";
</script>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>
<c:set var="modal"><ex:getManifestAssets value="modal.js"/></c:set>
<script src="<c:url value="${commons}"/>"></script>
<script src="<c:url value="${modal}"/>"></script>
</body>
</html>
