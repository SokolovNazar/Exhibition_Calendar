
<%@ page language="java" isErrorPage="true"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>



<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error Page</title>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:700,900" rel="stylesheet">

    <!-- Font Awesome Icon -->
    <%--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />--%>

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">

    <!-- Custom stlylesheet -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/error-style.css" />
</head>
    <body>
        <div id="notfound">
            <div class="notfound-bg"></div>
            <div class="notfound">
                <div class="notfound-404">
                    <h1>404</h1>
                </div>
                <h2>we are sorry, but the page you requested was not found</h2>
                <a href="${pageContext.request.contextPath}/index.jsp" class="home-btn">Go Home</a>
                <a href="${pageContext.request.contextPath}/index.jsp#map" class="contact-btn">Contact us</a>
                <div class="notfound-social">
                    <a href="https://www.facebook.com/mrhurniak"><i class="fab fa-facebook"></i></a>
                    <a href="https://twitter.com/HolyBozhenko"><i class="fab fa-twitter"></i></a>
                    <a href="https://github.com/MrHurniak"><i class="fab fa-google-plus"></i></a>
                </div>
            </div>
            <i style="color: white">Error <%= exception %></i>
        </div>
    </body>
</html>