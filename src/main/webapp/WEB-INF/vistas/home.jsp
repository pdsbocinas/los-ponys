<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ex" uri ="/WEB-INF/custom.tld" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html">
	<title>Home</title>
</head>
<body>
<t:base>
	<jsp:body>

<%--		<h2>Bienvenido ${email}</h2>--%>
		<h3>${error1}</h3>
		<h3>${exito}</h3>
		<div id="root"></div>

	</jsp:body>
</t:base>
<script>
	<%--var mensaje = "[[${error}]]";--%>
    var email = "${email}";
    var duplicado = "${duplicado}";
    var registroError = "${error1}";
    var registroExito = "${exito}";
	var errorLogin = "${errorLogin}";
	var login = "${login}";
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="js/bootstrap.min.js" type="application/javascript"></script>
<c:set var="home"><ex:getManifestAssets value="home.js"/></c:set>
<script src="<c:url value="${home}"/>"></script>

<!-- Placed at the end of the document so the pages load faster -->
<!--script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script-->
</body>
</html>
