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
        <div class="container">
            <h1>Detalle alojamiento!</h1>

            <div class="card" style="width: 18rem;" >
                <div class="card-body">
                    <h5 class="card-title">${alojamiento.getNombre()}</h5>
                    <p class="card-text">${alojamiento.getDescripcion()}</p>
                    <p class="card-text">${alojamiento.getDireccion()}</p>
                </div>
                <%--@elvariable id="reservaDto" type=""--%>
                <form:form commandName="reservaDto" action="confirm-alojamiento" method="GET" modelAttribute="reservaDto">
                    <form:input type="text" path="checkin" value="Tue Oct 01 2019 00:00:00 GMT-0300 (Argentina Standard Time)" />
                    <form:input type="text" path="checkout" value="Tue Oct 01 2019 00:00:00 GMT-0300 (Argentina Standard Time)" />
                    <form:hidden path="user_id" value="${usuario.getId()}" />
                    <form:hidden path="alojamiento_id" value="${alojamiento.getId()}" />
                    <button type="submit">Reservar</button>
                </form:form>
            </div>
        </div>
    </jsp:body>
</t:base>
<script>
  var usuario = ${usuario};
</script>
</body>
</html>
