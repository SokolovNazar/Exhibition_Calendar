<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: andrii
  Date: 02.04.19
  Time: 0:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Calendar of exhibitions</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/img/paint-board-and-brush.png"
          sizes="96x96">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/exposition.css">
    <fmt:setBundle var="link" basename="messages" scope="session"/>
    <jsp:include page="../../bootstrap.jsp"/>
</head>
<body>
<jsp:include page="../../header.jsp"/>

<div class="container-fluid">
    <div class="container">
        <div class="row  justify-content-center">
            <div class="col-xs-12 col-sm-2 p-2">
                <div class="hall">
                    <c:forEach var="hall" items="${requestScope['halls']}">
                        <a href="${pageContext.request.contextPath}/app/r/exposition?hall=${hall.id}">
                            <i class="fas fa-circle mr-1 circle"></i><c:out value="${hall.name}"/>
                        </a>
                    </c:forEach>
                    <a href="${pageContext.request.contextPath}/app/r/exposition">
                        </i><fmt:message key="exposition.show.all" bundle="${link}"/>
                    </a>
                </div>
            </div>
            <div class="col-xs-12 col-sm-8 exposition-list">

                <c:forEach items="${requestScope['expositionsList']}" var="elem">
                    <div class="card my-1">
                        <div class="card-body">
                            <div class="cart-head">
                                <h4 class="card-title" style="text-align: left;"><c:out
                                        value="${elem.theme}"/></h4>
                                <c:set var="datefmt"><fmt:message key="date.format" bundle="${link}"/></c:set>
                                <p class="card-text">
                                    <fmt:formatDate value="${elem.date}" pattern="${datefmt}"/> - <fmt:formatDate
                                        value="${elem.date_to}" pattern="${datefmt}"/>
                                </p>
                            </div>
                            <p class="card-text"><c:out value="${elem.shortDescription}"/></p>
                            <div class="cart-buy">
                                <p class="card-text"><fmt:message key="exposition.price" bundle="${link}"/>: <c:out
                                        value="${elem.price}"/><fmt:message key="price.currency" bundle="${link}"/></p>
                                <c:if test="${sessionScope['role'] eq 'USER'}">
                                    <form method="post" action="${pageContext.request.contextPath}/app/r/user/buy">
                                        <input type="text" hidden name="expo_id" value="${elem.id}"/>
                                        <input type="submit" class="btn btn-secondary"
                                               value="<fmt:message key="button.buy" bundle="${link}"/>">
                                    </form>
                                </c:if>
                            </div>
                            <button class="btn details" type="button" data-toggle="collapse"
                                    data-target="#collapseExample${elem.id}" aria-expanded="false"
                                    aria-controls="collapseExample">
                                <fmt:message key="exposition.show.more" bundle="${link}"/>
                            </button>
                            <div class="collapse" id="collapseExample${elem.id}">
                                <div class="card card-body">
                                    <c:out value="${elem.fullDescription}"/>
                                    <b><fmt:message key="exposition.place" bundle="${link}"/>:</b>
                                    <c:out value="${elem.hall.name}"/>
                                    <c:out value="${elem.hall.information}"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage != 1}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/app/r/exposition?page=${currentPage-1}">
                                    <fmt:message key="pagination.previous" bundle="${link}"/>
                                </a>
                            </li>
                        </c:if>
                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <li class="page-item active">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/app/r/exposition?page=${i}">
                                            <c:out value="${i}"/><span class="sr-only">(current)</span>
                                        </a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/app/r/exposition?page=${i}">
                                            <c:out value="${i}"/>
                                        </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${currentPage lt noOfPages}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/app/r/exposition?page=${currentPage+1}">
                                    <fmt:message key="pagination.next" bundle="${link}"/>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
            <div class="col-xs-12 col-sm-2">
            </div>
        </div>
    </div>

</div>
<jsp:include page="../../footer.jsp"/>
</body>
</html>
