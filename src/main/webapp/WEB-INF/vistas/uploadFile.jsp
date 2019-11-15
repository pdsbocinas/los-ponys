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
		<form method="post" action="./validar-formulario" enctype="multipart/form-data">
			<table>
				<tr>
					<td>Selecciona fichero: </td>
					<td><input type="file" name="fichero"></td>
				</tr>
				<tr>

				</tr>
				<tr><td colspan="2" align="center">
					<input type="submit" value="Subir fichero"></td>
				</tr>
			</table>
		</form>
		<p>${mensaje}</p>
		<a href="./vista" class="btn btn-primary">Volver</a>
	</jsp:body>
</t:base>
<script>

</script>

</body>
</html>
