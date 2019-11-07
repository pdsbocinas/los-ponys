<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Elegir Fechas</title>
</head>
<body>

<t:base>
    <jsp:body>
       <h1 class="display-4">Estos son los destinos que elegiste!</h1>
<%--        <div id="root"></div>--%>
      <div class="row mx-3">
        <c:forEach items="${destinos}" var="destino">
             <div class="col-4"  >
                <div class="card mb-2"  style="min-height: 290px; max-height: 290px">
                    <div class="card-body">
                        <h5 class="card-title">${destino.ciudad}</h5>

                      <c:choose>
                        <c:when test="${destino.fechaInicio != null && destino.fechaHasta != null}">
                          <h6 class="card-subtitle mb-2 text-muted">Aquí estarás...</h6>
                          <p class="card-text">Desde: <b>${destino.fechaInicio}</b></p>
                          <p class="card-text">Hasta: <b>${destino.fechaHasta}</b></p>
                          <a href="./destino/${destino.id}/vista" class="btn btn-primary">Ver destino
                          </a>
                        </c:when>
                        <c:otherwise>
                          <h6 class="card-subtitle mb-2 text-muted">Cuántos días te quedarás aqui?</h6>
                          <a href="./destino/${destino.id}/fecha" class="btn btn-primary">Elegir fechas
                          </a>
                        </c:otherwise>
                      </c:choose>
                    </div>
                </div>
            </div>
        </c:forEach>
      </div>
        <div class="row mx-3">
            <div class="col-12">
                <a href="./recorridos" class="btn btn-primary">Ver recorrido</a>
            </div>
        </div>
    </jsp:body>
</t:base>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>
<%--<c:set var="elegirFechas"><ex:getManifestAssets value="elegirFechas.js"/></c:set>--%>

<script>

  //var viaje_id = "${viaje_id}";


</script>
<script src="<c:url value="${commons}"/>"></script>
<%--<script src="<c:url value="${elegirFechas}"/>"></script>--%>
</body>
</html>
