<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andrii
  Date: 07.04.19
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Buy</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/paint-board-and-brush.png"
          sizes="96x96">
    <jsp:include page="../../bootstrap.jsp"/>
    <fmt:setBundle var="link" basename="messages" scope="session"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/buyStyle.css">
</head>
<body>
<jsp:include page="../../header.jsp"/>
<c:set var="expo" value="${sessionScope['expo_buy']}"/>
<div class="container-fluid">
    <div class="container">
        <div class="row  justify-content-center">
            <div class="col-xs-12 col-sm-8 p-3">
                <h2><c:out value="${expo.theme}"/></h2>
                <div class="card">
                    <div class="card-body"><c:out value="${expo.shortDescription}"/></div>
                </div>
                <div style="font-size: x-large">
                    <b><fmt:message key="price.per.unit" bundle="${link}"/>:</b>
                    <i><c:out value="${expo.price}"/>$</i>
                </div>
                <form action="${pageContext.request.contextPath}/app/r/user/buy">
                    <label for="tickets"><fmt:message key="tickets.max.count" bundle="${link}"/></label>
                    <input type="number" id="tickets" name="tickets_count"
                           min="1" max="25" step="1" required>
                    <input type="submit" class="btn btn-dark"
                           value="<fmt:message key='buy.continue' bundle='${link}'/>">
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
