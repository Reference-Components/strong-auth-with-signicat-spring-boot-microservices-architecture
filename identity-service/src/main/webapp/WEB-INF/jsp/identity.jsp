<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Java Test Service Provider</title>
        <link href="https://fonts.googleapis.com/css?family=Oswald|PT+Sans" rel="stylesheet"/>
        <link href="/resources/css/style.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <div class="container">

		<header>
		  <h1>Authenticated</h1>
		</header>
		
		<h2>Identification information</h2>
		<p>Token: ${token}</p><br>

		<pre>${identity.identityRawData}</pre>  

        </div>
    </body>
</html>

