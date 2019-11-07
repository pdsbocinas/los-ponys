<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Recorrido del viaje</title>
</head>
<body>
<style>
    /* Always set the map height explicitly to define the size of the div
     * element that contains the map. */
    #map {
        height: 100%;
    }
    /* Optional: Makes the sample page fill the window. */
    html, body {
        height: 100%;
        margin: 0;
        padding: 0;
    }
</style>
<t:base>
    <jsp:body>
      <h1 class="display-4">Aquí está tu recorrido</h1>
      <div id="map" ></div>
        <a href="./destino" class="btn btn-secondary">Volver</a>
      <script>

        function initMap() {
          var directionsService = new google.maps.DirectionsService();
          var directionsRenderer = new google.maps.DirectionsRenderer();
          var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 7,
            center: {placeId : '${origen}' }
          });

          directionsRenderer.setMap(map);


          calculateAndDisplayRoute(directionsService, directionsRenderer);


        }

        function calculateAndDisplayRoute(directionsService, directionsRenderer) {
          //var origen = new google.maps.Place('${ciudad}');

          directionsService.route(
            {
              origin: {placeId : '${origen}' },
              destination: {placeId: '${destino}'},
              travelMode: 'DRIVING',
              waypoints: [
                <c:forEach var="waypoint" items="${waypoint}">
                {location: {
                    placeId: '${waypoint}'
                  }
                },
                </c:forEach>
              ]
            },
            function(response, status) {
              if (status === 'OK') {
                directionsRenderer.setDirections(response);
              } else {
                window.alert('Directions request failed due to ' + status);
              }
            });
        }
      </script>
      <script async defer
              src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD8feo0IzBJZWjmAEhc2PIPRvBqWhBk2Jg&callback=initMap">
      </script>
    </jsp:body>
</t:base>
<%--<c:set var="commons"><ex:getManifestAssets value="commons.js"/></c:set>
<c:set var="route"><ex:getManifestAssets value="route.js"/></c:set>

<script src="<c:url value="${commons}"/>"></script>
<script src="<c:url value="${route}"/>"></script>--%>
<script>

</script>
</body>
</html>
