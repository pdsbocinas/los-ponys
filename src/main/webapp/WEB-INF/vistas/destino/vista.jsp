<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
  <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" type="text/css" rel="stylesheet">
    <title>Vista de destino</title>
</head>
<body>
<t:base>
    <jsp:body>
        <div class="container">
<%--            <form action="../../viaje/${viaje_id}/destino/${destino_id}/guardarFechas" method="POST">--%>
              <form action="guardarFechas" method="POST">

              <div class="row">
                <h1 class="display-4">Este es tu itinerario para <span class="font-italic font-weight-bold">${ciudad}</span></h1>
            </div>

                <div class="row">
                    <div class="col-12">
                        Estarás acá desde el <span class="font-weight-bold">${fechaInicio}</span>
                    </div>
                    <div class="col-12">
                        Hasta el <span class="font-weight-bold">${fechaHasta}</span>
                    </div>
                </div>

                <div class="row mt-3">
                    <div class="col-4">
                      <div class="card" style="width: 18rem;">
                        <div class="card-body">
                          <h5 class="card-title">Elegí tu alojamiento <i class="fas fa-home"></i></h5>
                          <h6 class="card-subtitle mb-2 text-muted">Rápido antes que ocupen tu lugar</h6>
                          <p class="card-text"></p>
                          <a href="./alojamiento" class="card-link">Buscá!</a>
                        </div>
                      </div>
                    </div>
                  <div class="col-4">
                    <div class="card" style="width: 18rem;">
                      <div class="card-body">
                        <h5 class="card-title">Tus momentos</h5>
                        <h6 class="card-subtitle mb-2 text-muted">Compartí fotos de este destino para que los demás puedan comentar</h6>
                        <p class="card-text"></p>
                        <a href="./alojamiento" class="btn btn-primary">Subí fotos</a>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-12">
                    <a
                      href="../../destino"
                      class="btn btn-secondary">Volver al viaje</a>
                  </div>
                </div>

            </form>
        </div>

    </jsp:body>
</t:base>
</body>
</html>
