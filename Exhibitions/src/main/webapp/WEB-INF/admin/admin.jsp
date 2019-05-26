<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: andrii
  Date: 02.04.19
  Time: 0:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Panel</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/paint-board-and-brush.png"
          sizes="96x96">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/abminStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <jsp:include page="../../bootstrap.jsp"/>
    <fmt:setBundle var="link" basename="messages" scope="session"/>
</head>
<body>
<div class="container-fluid px-0">
    <div class="row collapse show no-gutters d-flex h-100 position-relative">
        <div class="col-2 p-0 h-100 w-sidebar navbar-collapse collapse d-none d-md-flex sidebar">
            <!-- fixed sidebar -->
            <div class="navbar-dark bg-dark text-white position-fixed h-100 align-self-start w-sidebar myclass">
                <div>
                    <h6 class="px-3 pt-3">
                        <a href="${pageContext.request.contextPath}/index.jsp">
                            <img src="${pageContext.request.contextPath}/img/paint-board-and-brush.svg" class="mx-5"
                                 alt="Logo" style="width:40px;">
                        </a>
                        <fmt:message key="admin.page" bundle="${link}"/>
                        <a data-toggle="collapse" class="px-1 d-inline d-md-none text-white" href="#"
                           data-target=".collapse">
                            <i class="fa fa-bars"></i>
                        </a>
                    </h6>
                    <ul class="nav flex-column flex-nowrap text-truncate">
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/app/r/admin/stat">
                                <fmt:message key="admin.statistics" bundle="${link}"/>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/r/admin/halls">
                                <fmt:message key="admin.halls" bundle="${link}"/>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/app/r/admin/expositions">
                                <fmt:message key="admin.expositions" bundle="${link}"/>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="logout">
                    <a href="${pageContext}/app/logout">
                        <fmt:message key="header.log.out" bundle="${link}"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/index.jsp">
                        <fmt:message key="page.return" bundle="${link}"/>
                    </a>
                </div>
            </div>
        </div>
        <div class="col-10 p-0 pt-3">
            <jsp:include page="${adminPage}"/>
        </div>
    </div>
</div>

</body>
</html>

