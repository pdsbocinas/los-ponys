<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
       <h1 class="display-4">Elegí la foto de portada del viaje!</h1>
<%--        <div id="root"></div>--%>
      <div class="row mx-3">

          <c:forEach items="${fotos}" var="foto">
            <div class="col-2">
              <form:form action="submitForm" modelAttribute="foto">
                  <div class="row border rounded p-1" >
                    <div class="col-12 position-relative">
                      <div class="row justify-content-center align-items-center" style="min-height: 80px; max-height: 80px">
                        <div class="col-8">
                          <img src="/Los_Ponys_war/images/destinos/${foto.name}" class="img-fluid" alt="">
                        </div>
                      </div>
                    </div>
                    <div class="col-12 text-center">
                      <button type="submit" class="btn btn-primary" >Seleccionar</button>
                    </div>
                  </div>
                <form:hidden path="id" value="${foto.id}"/>

              </form:form>
            </div>

          </c:forEach>


      </div>

    </jsp:body>
</t:base>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>

<script src="<c:url value="${commons}"/>"></script>
</body>
</html>
