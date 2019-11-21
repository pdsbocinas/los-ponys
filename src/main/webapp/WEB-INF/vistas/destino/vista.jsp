<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
  <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" type="text/css" rel="stylesheet">

    <title>Vista de destino</title>
</head>
<style>
  .grid {
    background: #fff;
  }

  /* clear fix */
  .grid:after {
    content: '';
    display: block;
    clear: both;
  }

  /* ---- .grid-item ---- */

  .grid-sizer,
  .grid-item {
    width: 33.333%;
  }

  .grid-item {
    float: left;
  }

  .grid-item img {
    display: block;
    max-width: 100%;
  }

</style>
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
                  <div class="col-12">
                    <c:choose>
                      <c:when test="${permisoUsuario == true}">
                      <a href="./fecha" class="badge badge-primary">Modificar fecha</a>
                      </c:when>
                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>
                  </div>
                </div>
                <c:choose>
                  <c:when test="${permisoUsuario == true}">
                    <div class="row mt-3">
                      <div class="col-4">
                        <div class="card mb-2"  style="min-height: 290px; max-height: 290px">
                          <div class="card-body">
                            <h5 class="card-title">Elegí tu alojamiento <i class="fas fa-home"></i></h5>
                            <h6 class="card-subtitle mb-2 text-muted">Rápido antes que ocupen tu lugar</h6>
                            <p class="card-text"></p>
                            <a href="./alojamiento" class="card-link">Buscá!</a>
                          </div>
                        </div>
                      </div>
                      <div class="col-4">
                        <div class="card mb-2" style="min-height: 290px; max-height: 290px">
                          <div class="card-body">
                            <h5 class="card-title">Tus momentos</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Compartí fotos de este destino para que los demás puedan comentar</h6>
                            <p class="card-text"></p>
                            <c:choose>
                              <c:when test="${permisoUsuario == true}">
                                <a href="./subirFoto" class="btn btn-primary">Subí fotos</a>
                              </c:when>
                              <c:otherwise>

                              </c:otherwise>
                            </c:choose>

                            <div class="row mt-2">
                              <c:choose>
                                <c:when test="${fotos != null}">
                                  <c:forEach items="${fotos}" var="foto">
                                    <div class="col-3 mt-1">
                                      <img
                                        class="img-thumbnail"
                                        src="/Los_Ponys_war/images/destinos/${foto.name}"
                                        alt=""
                                      >
                                    </div>
                                  </c:forEach>


                                </c:when>
                                <c:otherwise>
                                  <div class="col-12">Todavia no subiste fotos</div>
                                </c:otherwise>
                              </c:choose>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </c:when>
                  <c:otherwise>

                  </c:otherwise>
                </c:choose>


                <c:choose>
                  <c:when test="${permisoUsuario == true}">
                    <div class="row">
                      <div class="col-12">
                        <a
                          href="../../destino"
                          class="btn btn-secondary">Volver al viaje</a>
                      </div>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <div class="row">
                      <div class="col-12">
                        <a
                          href="../../../../"
                          class="btn btn-secondary">Volver al Home</a>
                      </div>
                    </div>
                  </c:otherwise>
                </c:choose>


            </form>


            <div class="grid">
              <div class="grid-sizer"></div>
              <c:forEach items="${fotos}" var="foto">
                <div class="grid-item">
                  <img src="/Los_Ponys_war/images/destinos/${foto.name}" />
                </div>
              </c:forEach>
            </div>

        </div>

    </jsp:body>
</t:base>
<script src="https://unpkg.com/masonry-layout@4/dist/masonry.pkgd.min.js"></script>
<script src="https://unpkg.com/imagesloaded@4/imagesloaded.pkgd.min.js"></script>
<script>
  // external js: masonry.pkgd.js, imagesloaded.pkgd.js

  var grid = document.querySelector('.grid');
  var msnry;

  imagesLoaded( grid, function() {
    // init Isotope after all images have loaded
    msnry = new Masonry( grid, {
      itemSelector: '.grid-item',
      columnWidth: '.grid-sizer',
      percentPosition: true
    });
  });

</script>
</body>
</html>
