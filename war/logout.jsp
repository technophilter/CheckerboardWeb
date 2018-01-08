<%@page import="java.io.File"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
 
        <%
	        session.invalidate();
	        response.sendRedirect("login.jsp");
        %>
<!doctype html>
<html>
  <head>
  	<meta name="viewport" content="initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Checkerboard</title>
  </head>

  <body>
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
	<!--  header nav -->
	<div id="nav"></div>

  </body>
</html>