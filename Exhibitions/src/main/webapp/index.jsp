<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Calendar of exhibitions</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/paint-board-and-brush.png"
          sizes="96x96">
    <jsp:include page="bootstrap.jsp"/>
    <fmt:setBundle var="link" basename="messages" scope="session"/>
    <fmt:setBundle basename="indexText" var="index" scope="page"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexStyle.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<!--  Top Content -->
<div class="container-fluid">
    <div class="container">
        <div class="row  justify-content-center">
            <div class="col-xs-12 col-sm-10 p-2" style="border-left: 1px solid grey; border-right: 1px solid grey;">
                <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner my-3" style="height: 300px; width: 100%">
                        <div class="carousel-item active">
                            <img class="slide-img" src="${pageContext.request.contextPath}/img/expo1.jpg"
                                 alt="First slide">
                            <div class="carousel-caption d-none d-md-block  align-items-center">
                                <h5><fmt:message key="index.slider.header.first" bundle="${link}"/></h5>
                                <p><fmt:message key="index.slider.text.first" bundle="${link}"/></p>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img class="slide-img" src="${pageContext.request.contextPath}/img/expo2.jpg"
                                 alt="Second slide">
                            <div class="carousel-caption d-none d-md-block">
                                <h5><fmt:message key="index.slider.header.second" bundle="${link}"/></h5>
                                <p><fmt:message key="index.slider.text.second" bundle="${link}"/></p>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img class="slide-img" src="${pageContext.request.contextPath}/img/expo3.jpg"
                                 alt="Third slide">
                            <div class="carousel-caption d-none d-md-block">
                                <h5><fmt:message key="index.slider.header.third" bundle="${link}"/></h5>
                                <p><fmt:message key="index.slider.text.third" bundle="${link}"/></p>
                            </div>
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
                <img src="${pageContext.request.contextPath}/img/mainImage.png" style="float: left;  height: 300px;"
                     class="m-3" alt="">
                <p style="text-align: left;">
                    <fmt:message key="text" bundle="${index}"/>
                <h3 style="clear: left;" class="text-center"><fmt:message key="index.map.find.us"
                                                                          bundle="${link}"/></h3>
                <!--The div element for the map -->
                <div id="map">
                    <c:set var="map_link"><fmt:message key="map.link" bundle="${index}"/></c:set>
                    <iframe src="${map_link}" width="640"
                            height="480"></iframe>
                </div>
                <div class="float-right mr-5 mt-3">
                    <p><b><fmt:message key="index.address" bundle="${link}"/>: </b><fmt:message key="about.us.address"
                                                                                                bundle="${index}"/></p>
                    <p><b><fmt:message key="index.city" bundle="${link}"/>: </b><fmt:message key="about.us.city"
                                                                                             bundle="${index}"/></p>
                    <p><b><fmt:message key="index.phone" bundle="${link}"/>: </b><fmt:message key="about.us.phone"
                                                                                              bundle="${index}"/></p>
                    <c:set var="mail"><fmt:message key="about.us.email" bundle="${index}"/></c:set>
                    <p><b><fmt:message key="index.email" bundle="${link}"/>: </b><a href="mailto:${mail}">${mail}</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<jsp:include page="footer.jsp"/>


</body>
</html>
