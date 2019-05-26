<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3 class="text-center mt-2 mb-4 font-weight-bold"><u>Statistics</u></h3>
<div class="container-fluid">
    <div class="container">
        <div class="row  justify-content-center">
            <div class="col-md-6 col-sm-12 px-5">
                <ul class="list-group">
                    <li class="list-group-item active">Users online</li>
                    <c:forEach var="user" items="${applicationScope['loggedUsers']}">
                        <li class="list-group-item">
                            <c:out value="${user}"/>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-md-6 col-sm-12">
                <h4><b>Total number of expositions:</b> <c:out value="${requestScope['totalExpositions']}"/></h4>
                <h4><b>Total number of halls:</b> <c:out value="${requestScope['totalHalls']}"/></h4>
            </div>
        </div>
    </div>
</div>