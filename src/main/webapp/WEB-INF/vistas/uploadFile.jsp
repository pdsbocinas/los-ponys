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

			<%--<table>
				<tr>
					<td>Selecciona fichero: </td>
					<td><input type="file" name="fichero"></td>
				</tr>
				<tr>

				</tr>
				<tr><td colspan="2" align="center">
					<input type="submit" value="Subir fichero"></td>
				</tr>
			</table>--%>
			<div class="container">
				<div class="row">
					<div class="col-12">
						<h1 class="display-4">Elegí una foto para subir al destino</h1>
					</div>
				</div>
				<c:choose>
					<c:when test="${mensaje == 'ok'}">
						<div class="row">
							<div class="col">
								La foto se subió correctamente
							</div>
						</div>

					</c:when>
					<c:otherwise>
						<form method="post" action="./validar-formulario" enctype="multipart/form-data">
							<div class="row">
								<div class="col">
									<div class="input-group">
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="inputGroupFile04" name="fichero">
											<label class="custom-file-label" for="inputGroupFile04">Selecciona una foto</label>
										</div>
										<div class="input-group-append">
											<button class="btn btn-outline-secondary" type="submit" id="inputGroupFileAddon04" value="Subir fichero">Subir</button>
										</div>
									</div>
								</div>
							</div>
						</form>
					</c:otherwise>

				</c:choose>

				<div class="row justify-content-start my-2">
					<div class="col-1">
						<a href="./vista" class="btn btn-primary">Volver</a>
					</div>
				</div>

			</div>



	</jsp:body>
</t:base>
<script>

</script>

</body>
</html>
