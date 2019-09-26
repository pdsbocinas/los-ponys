<%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ attribute name="header" fragment="true" %>
<%@ attribute name="footer" fragment="true" %>
<html>
<body>
<div id="pageheader">
    <p>Soy el header</p>
    <jsp:invoke fragment="header"/>
</div>
<div id="body">
    <jsp:doBody/>
</div>
<div id="pagefooter">
    <p>Soy el footer</p>
    <jsp:invoke fragment="footer"/>
</div>
</body>
</html>