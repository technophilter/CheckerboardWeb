<%@page import="java.io.File"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
        <%
        		long versionId = java.lang.Math.round(java.lang.Math.random() * 5555);
            // String versionId = "2";
        	String jspPath = session.getServletContext().getRealPath("./");
/*             String txtFilePath = jspPath+ "/build.number";
            BufferedReader reader = new BufferedReader(new FileReader(txtFilePath));
            StringBuilder sb = new StringBuilder();
            String line;

            while((line = reader.readLine())!= null){
	            	if (line.contains("=")){
	            		versionId = line.substring(13, line.length());
	            	} 
            } */
            // debug
            // out.println(sb.toString());
        %>
<!doctype html>
<html>
  <head>
  	<meta name="viewport" content="initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Checkerboard</title>
    <script type="text/javascript" language="javascript" src="home/home.nocache.js"></script>
    <link type="text/css" rel="stylesheet" href="abba.css?ver=<%=versionId%>">
  </head>

  <body>
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0"></iframe>
	<div id="nav"></div>
  </body>
</html>