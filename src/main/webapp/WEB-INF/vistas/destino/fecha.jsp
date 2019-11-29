<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Vista de destino</title>
</head>
<body>
<t:base>
    <jsp:body>
        <div class="container">
<%--            <form action="../../viaje/${viaje_id}/destino/${destino_id}/guardarFechas" method="POST">--%>
              <form action="guardarFechas" method="POST">

               <div class="row justify-content-center">
                  <h1 class="display-4">Hablemos de fechas</h1>
               </div>
                <div class="row">
                    <div class="col-12 text-center">
                        <h4>¿Cuánto tiempo te quedarás en <span class="text-primary">${ciudad}</span>?</h4>
                    </div>
                </div>
                <div class="row">
                  <div class="col-12 text-center" >
                    <small>
                      Recordá que tu viaje comienza el: <b>${fechaDesdeViaje}</b> y finaliza el: <b>${fechaHastaViaje}</b>
                    </small>
                  </div>
                </div>

                <div class="row mt-4">
                  <div class="col-6">
                    <div class="form-group">
                      <label for="fechaDesde">Empieza</label>
                      <input
                        type="date"
                        id="fechaDesde"
                        name="fechaInicio"
                        class="form-control"
                      value="${fechaInicio}">
                    </div>
                  </div>
                  <div class="col-6">
                    <div class="form-group">
                      <label for="fechaHasta">Termina</label>
                      <input
                        type="date"
                        id="fechaHasta"
                        name="fechaHasta"
                        class="form-control"
                      value="${fechaHasta}">
                    </div>
                  </div>
                </div>
                <span class="badge badge-pill badge-danger">${error}</span>
                <div class="row mt-2">
                  <div class="col-12 text-right">
                    <a href="../" class="btn btn-secondary">Volver</a>
                    <button type="submit" class="btn btn-primary">Guardar cambios!</button>
                  </div>
                </div>
            </form>
        </div>

    </jsp:body>
</t:base>
</body>
</html>
