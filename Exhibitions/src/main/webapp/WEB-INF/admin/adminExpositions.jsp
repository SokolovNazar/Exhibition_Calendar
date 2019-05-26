<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h3 class="text-center my-2 font-weight-bold"><u>Expositions editor</u></h3>
<p>Create new exposition</p>
<c:if test="${not empty sessionScope['expo_message']}">
    <h3><c:out value="${sessionScope['expo_message']}"/></h3>
    <c:remove var="expo_message" scope="session"/>
</c:if>
<form action="${pageContext.request.contextPath}/app/r/admin/expositions">
    <input type="text" hidden name="command" value="add"/>
    <div class="form-group" style="display: flex; align-items: center;">
        <label for="theme" style="margin: 0 25px">Theme:</label>
        <input type="text" name="theme" class="form-control" id="theme" minlength="3" maxlength="250" required>
    </div>
    <div class="form-group" style="display: flex">
        <div>
            <label for="price">Price (<fmt:message key="price.currency" bundle="${link}"/>):</label>
            <input type="number" name="price" class="form-control" id="price" min="0" max="1000000000"
                   value="0" required>
        </div>
        <div style="margin: 0 25px">
            <label for="date">Date:</label>
            <input type="date" name="date" class="form-control" id="date"
                   data-date-format="DD MM YYYY" min="${requestScope['date_from']}" max="${requestScope['date_to']}"
                   required>
        </div>
        <div>
            <label for="date_to">Date to:</label>
            <input type="date" name="date_to" class="form-control" id="date_to"
                   data-date-format="DD MM YYYY" min="${requestScope['date_from']}" max="${requestScope['date_to']}"
                   value="${expo.date_to}" required>
        </div>
    </div>
    <div class="form-group">
        <label for="short">Short description:</label>
        <textarea class="form-control" name="short" maxlength="1000" rows="2" id="short" required></textarea>
    </div>
    <div class="form-group">
        <label for="full">Full description:</label>
        <textarea class="form-control" name="full" maxlength="10000" rows="3" id="full"></textarea>
    </div>
    <div class="input-group mb-3">
        <div class="input-group-prepend">
            <label class="input-group-text" for="inputGroupSelect01">Hall</label>
        </div>
        <select class="custom-select" name="hall_id" id="inputGroupSelect01" required>
            <c:forEach items="${halls}" var="hall">
                <option value="${hall.id}"><c:out value="${hall.name}"/></option>
            </c:forEach>
        </select>
    </div>
    <input class="btn btn-primary" type="submit" value="Submit">
</form>
<hr>
<p>Update or delete</p>
<c:forEach var="expo" items="${requestScope['expositions']}">
    <hr>
    <h3><c:out value="${expo.theme}"/></h3>
    <button data-toggle="collapse" class="btn btn-link mb-3" data-target="#demo${expo.id}">Update</button>
    <div id="demo${expo.id}" class="collapse">
        <form action="${pageContext.request.contextPath}/app/r/admin/expositions">
            <input type="text" hidden name="command" value="update"/>
            <input type="text" hidden name="expo_id" value="${expo.id}"/>
            <div class="form-group" style="display: flex">
                <label style="margin: 0 25px" for="theme${expo.id}">Theme:</label>
                <input type="text" name="theme" class="form-control" id="theme${expo.id}"
                       minlength="3" maxlength="250" value="${expo.theme}" required>
            </div>
            <div class="form-group" style="display: flex">
                <div>
                    <label for="price${expo.id}">Price (<fmt:message key="price.currency" bundle="${link}"/>):</label>
                    <input type="number" name="price" class="form-control" id="price${expo.id}"
                           min="0" max="1000000000" value="${expo.price}" required>
                </div>
                <div style="margin: 0 25px">
                    <label for="date${expo.id}">Date:</label>
                    <input type="date" name="date" class="form-control" id="date${expo.id}"
                           data-date-format="DD MM YYYY" min="2000-01-01" max="2020-01-01"
                           value="${expo.date}" required>
                </div>
                <div>
                    <label for="date${expo.id}">Date to:</label>
                    <input type="date" name="date_to" class="form-control" id="date${expo.id}"
                           data-date-format="DD MM YYYY" min="2000-01-01" max="2020-01-01"
                           value="${expo.date_to}" required>
                </div>
            </div>
            <div class="form-group">
                <label for="short${expo.id}">Short description:</label>
                <textarea class="form-control" name="short" maxlength="1000"
                          rows="2" id="short${expo.id}" required><c:out value="${expo.shortDescription}"/></textarea>
            </div>
            <div class="form-group">
                <label for="full${expo.id}">Full description:</label>
                <textarea class="form-control" name="full" maxlength="10000"
                          rows="3" id="full${expo.id}"><c:out value="${expo.fullDescription}"/></textarea>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <label class="input-group-text" for="inputGroupSelect${expo.id}">Hall</label>
                </div>
                <select class="custom-select" name="hall_id" id="inputGroupSelect${expo.id}" required>
                    <c:forEach items="${halls}" var="hall">
                        <option <c:if test="${expo.hall.id eq hall.id}"> selected="selected" </c:if>
                                value="${hall.id}"><c:out value="${hall.name}"/></option>
                    </c:forEach>
                </select>
            </div>
            <input class="btn btn-warning" type="submit" value="Submit">
        </form>
        <form action="${pageContext.request.contextPath}/app/r/admin/expositions"
              onsubmit="return confirm('Do you really want to submit the form?');">
            <input type="text" hidden name="command" value="delete"/>
            <input type="text" hidden name="expo_id" value="${expo.id}"/>
            <input type="submit" class="btn btn-danger" value="Delete"/>
        </form>
    </div>
</c:forEach>
