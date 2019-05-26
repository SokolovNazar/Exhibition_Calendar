<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Calendar of exhibitions</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/paint-board-and-brush.png"
          sizes="96x96">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signinup.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <fmt:setBundle var="link" basename="messages" scope="session"/>
</head>
<body>
<div class="container">
    <div class="col-md-6">
        <div id="logbox">
            <form id="signup" method="post" action="${pageContext.request.contextPath}/app/login">
                <h1><fmt:message key="page.log.in" bundle="${link}"/></h1>
                <c:if test="${not empty requestScope['message']}">
                    <h6 style="color: red; text-align: center"><fmt:message key="${requestScope['message']}"
                                                                            bundle="${link}"/></h6>
                    <c:remove var="message" scope="request"/>
                </c:if>

                <input name="login" type="text" placeholder="<fmt:message key="enter.login" bundle="${link}"/>"
                       class="input pass"/>
                <input name="password" type="password"
                       placeholder="<fmt:message key="enter.password" bundle="${link}"/>"
                       required="required" class="input pass"/>
                <input type="submit" value="Sign me in!" class="inputButton"/>
                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/app/registration"
                       id=""><fmt:message key="page.registration" bundle="${link}"/></a>
                </div>
                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="page.main"
                                                                                        bundle="${link}"/></a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>