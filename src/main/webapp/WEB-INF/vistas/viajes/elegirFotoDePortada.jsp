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


            <form:form action="submitForm" modelAttribute="foto">

              <img src="/Los_Ponys_war/images/destinos/${foto.name}" class="img-responsive" alt="">
              <button type="submit" class="btn btn-primary">Seleccionar</button>
              <form:hidden path="id" value="${foto.id}"/>

            </form:form>
          </c:forEach>


      </div>

    </jsp:body>
</t:base>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>

<script src="<c:url value="${commons}"/>"></script>
</body>
</html>
