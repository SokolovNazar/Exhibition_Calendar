<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Calendar of exhibitions</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/paint-board-and-brush.png"
          sizes="96x96">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signinup.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <fmt:setBundle var="link" basename="messages" scope="session"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>
<div class="container">
    <div class="col-md-6">
        <div id="logbox">
            <form id="signup" method="post" action="${pageContext.request.contextPath}/app/registration">
                <h1><fmt:message key="page.registration" bundle="${link}"/></h1>
                <c:if test="${not empty requestScope['message']}">
                    <h6 style="color: red; text-align: center"><fmt:message key="${requestScope['message']}"
                                                                            bundle="${link}"/></h6>
                    <c:remove var="message" scope="request"/>
                </c:if>
                <input name="name" type="text" placeholder="<fmt:message key="enter.name" bundle="${link}"/>"
                       autofocus="autofocus" required="required" value="${requestScope['name']}"
                       minlength="${requestScope['name_min']}" maxlength="${requestScope['name_max']}"
                       title="<fmt:message key="registration.title.name" bundle="${link}"/>" class="input pass"/>
                <input name="surname" type="text" placeholder="<fmt:message key="enter.surname" bundle="${link}"/>"
                       autofocus="autofocus" required="required" value="${requestScope['surname']}"
                       minlength="${requestScope['surname_min']}" maxlength="${requestScope['surname_max']}"
                       title="<fmt:message key="registration.title.name" bundle="${link}"/>" class="input pass"/>
                <input name="email" type="email" placeholder="<fmt:message key="enter.email" bundle="${link}"/>"
                       value="${requestScope['email']}" class="input pass"/>
                <input name="login" type="text" placeholder="<fmt:message key="enter.login" bundle="${link}"/>"
                       required="required" pattern="${requestScope['login_ptrn']}"
                       minlength="${requestScope['login_min']}" maxlength="${requestScope['login_max']}"
                       title="<fmt:message key="registration.title.login" bundle="${link}"/>"
                       value="${requestScope['login']}" class="input pass"/>
                <input name="password" type="password"
                       placeholder="<fmt:message key="enter.password" bundle="${link}"/>"
                       required="required" class="input pass"
                       minlength="${requestScope['password_min']}" maxlength="${requestScope['password_max']}"/>
                <input type="submit" value="Sign me up!" class="inputButton"/>
                <div class="text-center">
                    <fmt:message key="have.account" bundle="${link}"/>
                    <a href="${pageContext.request.contextPath}/app/login" id="login_id">
                        <fmt:message key="header.log.in" bundle="${link}"/>
                    </a>
                </div>
                <div class="text-center">
                    <a href="${pageContext.request.contextPath}/index.jsp">
                        <fmt:message key="page.main" bundle="${link}"/>
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>