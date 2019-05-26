<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<fmt:setLocale value = "ua_UA"/>--%>
<fmt:setBundle var="link" basename="messages" scope="session" />
<nav class="navbar navbar-expand-sm bg-dark navbar-dark" style="justify-content: space-between;">
    <a class="navbar-brand " href="${pageContext.request.contextPath}/index.jsp">
        <img src="${pageContext.request.contextPath}/img/paint-board-and-brush.svg" class="mx-5" alt="Logo" style="width:40px;">
        <fmt:message key="header.logo" bundle="${link}"/>
    </a>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/index.jsp#map" class="nav-link">
                    <fmt:message key="header.about.us" bundle="${link}"/>
                </a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/app/r/exposition" class="nav-link">
                    <fmt:message key="header.exposition" bundle="${link}"/>
                </a>
            </li>
            <c:if test="${sessionScope['role'] eq 'ADMIN'}">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}/app/r/admin/stat" class="nav-link">
                    <fmt:message key="header.admin" bundle="${link}"/>
                </a>
            </li>
            </c:if>

        </ul>
    </div>
    <div class="mr-5 d-flex loginirovanie" style="height: 45px;">
        <c:if test="${sessionScope['role'] eq 'USER'}">
        <a href="${pageContext.request.contextPath}/app/r/user/tickets">
            <button type="button" class="btn btn-primary mx-2 my-1 py-2 px-3"><i class="fas fa-shopping-cart"></i>
        </button></a>
        </c:if>
        <c:if test="${not empty sessionScope['role']}">
        <a href="${pageContext.request.contextPath}/app/logout"><button type="button" class="btn btn-success mx-2">
            <fmt:message key="header.log.out" bundle="${link}"/>
        </button></a>

        </c:if>
        <c:if test="${empty sessionScope['role']}">
        <a href="${pageContext.request.contextPath}/app/login"><button type="button" class="btn btn-outline-success mx-2">
            <fmt:message key="header.log.in" bundle="${link}"/>
        </button></a>
        <a href="${pageContext.request.contextPath}/app/registration"><button type="button" class="btn btn-success mx-2">
            <fmt:message key="header.registration" bundle="${link}"/>
        </button></a>
        </c:if>
        <div class="language mx-2" style="display: flex; flex-direction: column; justify-content: center; align-items: center">
                <div style="background-image: url('${pageContext.request.contextPath}/img/ua_icon.png');
                        background-size: cover; height: 17px; width: 23px;">
                    <a href="${pageContext.request.contextPath}/?lang=ua" style="display: block; height: 100%"></a>
                </div>
                <div style="background-image: url('${pageContext.request.contextPath}/img/gb_icon.png');
                        background-size: cover; height: 17px; width: 23px; margin-top: 2.5px">
                    <a href="${pageContext.request.contextPath}/?lang=en" style="display: block; height: 100%"></a>
                </div>
        </div>
    </div>
</nav>