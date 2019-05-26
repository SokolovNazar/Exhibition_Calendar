<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andrii
  Date: 07.04.19
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Calendar of exhibitions</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/paint-board-and-brush.png" sizes="96x96">
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/signinup.css">
    <jsp:include page="../../bootstrap.jsp"/>
</head>
<body>
    <div class="container">
        <div class="col-md-6">
            <div id="logbox">
                <form  method="post" action="${pageContext.request.contextPath}/app/r/user/buy/payment/conf">
                    <h1>Payment</h1>
                    <input name="card" type="number" placeholder="enter card numbers" class="input pass"/>
                    <div class="row">
                        <div class="col">
                            <input type="number" name="date" class="form-control " placeholder="date">
                        </div>
                        <div class="col">
                            <input type="number" name="cvv" class="form-control" placeholder="cvv">
                        </div>
                    </div>
                    <input type="submit" value="Pay" class="inputButton"/>
                </form>
            </div>
        </div>
    </div>
    <%--<h2>Payment</h2>--%>
    <%--<form action="${pageContext.request.contextPath}/app/r/user/buy/payment/conf">--%>
        <%--<input type="text" name="pay">--%>
        <%--<input type="submit" value="Pay">--%>
    <%--</form>--%>
</body>
</html>
