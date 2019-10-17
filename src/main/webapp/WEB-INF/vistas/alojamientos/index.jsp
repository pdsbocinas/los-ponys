<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <h1>Busca alojamientos!!</h1>
            <div class="row">
                <c:forEach var="alojamiento" items="${alojamientos}">
                    <div class="col-4" style="display: flex;">
                        <div class="card" style="width: 18rem;" >
                            <div class="card-body">
                                <a href="alojamientos/${alojamiento.getId()}">
                                    <h5 class="card-title">${alojamiento.getNombre()}</h5>
                                </a>
                            </div>
                            <div>Fecha desde: ${alojamiento.getDesde()}</div>
                            <div>Fecha hasta: ${alojamiento.getHasta()}</div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${empty alojamientos}">
                    No se encontramos alojamientos!!
                </c:if>
            </div>
        </div>
    </jsp:body>
</t:base>
</body>
</html>
