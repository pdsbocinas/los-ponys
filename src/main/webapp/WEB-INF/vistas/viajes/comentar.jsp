<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Posteo</title>
</head>
<body>
<t:base>
    <jsp:body>
        <div class="banner">
            <h1 class="text-primary">Comentar Viaje</h1>
        </div>
        <div id="root"></div>
    </jsp:body>
</t:base>
<script>
  <%--var viaje_id = "${id}";--%>
  var usuario_email = "${usuario_email}";
  var viaje = "${viaje}";
  var viaje_id = "${viaje_id}";
  var viaje_fechaInicio = "${viaje_fechaInicio}";
  var viaje_fechaFin = "${viaje_fechaFin}";
  var viaje_titulo = "${viaje_titulo}";

</script>
<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>
<c:set var="posteo"><ex:getManifestAssets value="posteo.js"/></c:set>
<script src="<c:url value="${commons}"/>"></script>
<script src="<c:url value="${posteo}"/>"></script>
</body>
</html>
