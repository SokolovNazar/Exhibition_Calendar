<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h3 class="text-center my-2 font-weight-bold"><u>Halls redactor</u></h3>
<p>Create new</p>
<form action="${pageContext.request.contextPath}/app/r/admin/halls">
    <input type="text" hidden name="command" value="add"/>
    <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" name="name" class="form-control" id="name" minlength="3" maxlength="250" required>
    </div>
    <div class="form-group">
        <label for="info">Information</label>
        <textarea class="form-control" name="information" maxlength="500" rows="5" id="info"></textarea>
    </div>
    <input class="btn btn-primary" type="submit" value="Submit">
</form>
<hr>
<p>Update or delete</p>
<c:forEach var="hall" items="${requestScope['halls']}">
    <hr>
    <h3><c:out value="${hall.name}"/></h3>
    <button data-toggle="collapse" class="btn btn-link mb-3" data-target="#demo${hall.id}">Update</button>
    <div id="demo${hall.id}" class="collapse">
        <form action="${pageContext.request.contextPath}/app/r/admin/halls">
            <input type="text" hidden name="command" value="update"/>
            <input type="text" hidden name="id" value="${hall.id}"/>
            <div class="form-group">
                <label for="nameUp">Name:</label>
                <input type="text" name="name" class="form-control" id="nameUp"
                 minlength="3" maxlength="250" value="${hall.name}" required>
            </div>
            <div class="form-group">
                <label for="infoUp">Information:</label>
                <textarea class="form-control" name="information" rows="5" id="infoUp"
                 maxlength="500"         required><c:out
                        value="${hall.information}"/></textarea>
            </div>
            <input class="btn btn-warning" type="submit" value="Update">
        </form>
        <form action="${pageContext.request.contextPath}/app/r/admin/halls"
              onsubmit="return confirm('Do you really want to submit the form?');">
            <input type="text" hidden name="command" value="delete"/>
            <input type="text" hidden name="id" value="${hall.id}"/>
            <input type="submit" class="btn btn-danger" value="Delete"/>
        </form>
    </div>
</c:forEach>
