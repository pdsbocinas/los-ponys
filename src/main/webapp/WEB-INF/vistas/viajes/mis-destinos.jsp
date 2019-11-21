<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <div class="container-fluid">
      <div class="d-flex bd-highlight">
        <div class="p-2 flex-grow-1 bd-highlight">
          <h1 class="display-4">Estos son los destinos que elegiste!</h1>
        </div>
        <div class="p-2 bd-highlight align-self-center">
          <a href="../${viaje_id}" class="btn btn-secondary">Agregar o quitar destinos</a>
          <a href="./recorridos" class="btn btn-primary">Ver recorrido</a>
        </div>
      </div>
      <div class="d-flex bd-highlight">
        <h4>${fotoNombre}</h4>
        <a href="./fotodeportada" class="btn btn-warning">Elegir foto de portada</a>
        <h2>${errorFotoPortada}</h2>
        <img src="/Los_Ponys_war/images/destinos/${fotoPortada}" class="img-fluid" alt="Responsive image">
      </div>
      <div class="d-flex bd-highlight">
        <c:forEach items="${destinos}" var="destino">
          <div class="card mb-2" style="min-height: 290px; max-height: 290px; margin-right: 15px;">
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
        </c:forEach>
      </div>
      <div id="puntos"></div>
    </div>
  </jsp:body>
</t:base>
<c:set var="recomendaciones"><ex:getManifestAssets value="recomendaciones.js"/></c:set>
<script src="<c:url value="${recomendaciones}"/>"></script>
</body>
</html>
