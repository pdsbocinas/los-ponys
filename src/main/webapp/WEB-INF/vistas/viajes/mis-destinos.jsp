<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
  <title>Elegir Fechas</title>
</head>
<body>

<t:base>
  <jsp:body>
    <div class="container-fluid">
      <div class="row ">
        <div class="col-12 text-center text-uppercase text-primary ">
          <h1 class="display-4">${viajeTitulo}</h1>
        </div>
      </div>
      <div class="row justify-content-center">
        <div class="col-6 text-center">
          <h2>${errorFotoPortada}</h2>
          <img src="/Los_Ponys_war/images/destinos/${fotoPortada}" class="img-fluid" alt="">
        </div>
      </div>
      <div class="row justify-content-center">
        <div class="col-6">
          <a href="./fotodeportada" class="btn btn-block btn-warning">Elegir foto de portada</a>
        </div>
      </div>

      <div class="row justify-content-around">
        <div class="col-12 my-2 text-center">
          <h1 class="display-4">Estos son los destinos que elegiste!</h1>
        </div>
        <div class="col-12 text-center text-muted my-2">
           <a href="${contextPath}/viajes/${viajeId}" class="badge badge-primary">Podes agregar o quitar destinos desde acá</a>
        </div>

        <c:forEach items="${destinos}" var="destino">
          <div class="card mb-2 col-4 m-2 bg-dark">
            <div class="card-body bg-light">
              <h5 class="card-title">${destino.ciudad}</h5>
              <c:choose>
                <c:when test="${destino.fechaInicio != null && destino.fechaHasta != null}">
                  <p class="card-text"><b>${destino.fechaInicio}</b> - <b>${destino.fechaHasta}</b></p>
                  <div class="row justify-content-end">
                      <a href="./destino/${destino.id}/vista" class="btn btn-primary">Ver destino
                      </a>
                  </div>

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
        <div class="col-12 text-center text-muted">
          <h1 class="display-4">Cuando tengas todo listo mirá el recorrido  </h1>
          <a href="./recorridos" class="badge badge-primary">Ver Recorrido</a>
        </div>
      </div>
      <div id="puntos"></div>
    </div>
  </jsp:body>
</t:base>
<script>
  var destinosJson = ${destinosJson};
</script>
<c:set var="recomendaciones"><ex:getManifestAssets value="recomendaciones.js"/></c:set>
<script src="<c:url value="${recomendaciones}"/>"></script>
</body>
</html>
