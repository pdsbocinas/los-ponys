<%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ attribute name="header" fragment="true" %>
<%@ attribute name="footer" fragment="true" %>
<%@ attribute name="scripts" fragment="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Bootstrap theme -->
        <link href="<c:url value="/css/bootstrap-theme.min.css"/>" rel="stylesheet">
        <link href="<c:url value="/css/footer.css"/>" rel="stylesheet">
        <link href="<c:url value="/css/banner.css"/>" rel="stylesheet">
    </head>
    <body>
        <header id="pageheader">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <a class="navbar-brand" href="#">Travel Ponies</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="#">Mi perfil <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Link</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                        </li>
                    </ul>
                    <ul>
                        <a href="viajes" title="viaje">
                            <img src="<c:url value="/images/003-backpack.svg"/>" width="30" height="auto" alt="crear viaje">
                        </a>
                    </ul>
                </div>
            </nav>
            <!--jsp:invoke fragment="header"/-->
        </header>
        <main id="body">
            <jsp:doBody/>
        </main>
        <footer id="pagefooter">
            <div class="container-fluid">
                Never ponies travelling
            </div>
            <!--jsp:invoke fragment="footer"/-->
        </footer>
    </body>
</html>