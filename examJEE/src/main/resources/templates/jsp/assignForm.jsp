<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Assign Employe to Project</title>
</head>
<body>
<nav>
    <a href="/employes">Employes</a>
    <a href="/home">Back to Home</a>
</nav>
<h1>Assign Employe to Project</h1>
<form action="/assign" method="post">
    <label for="employe">Employe:</label>
    <select name="employeId" id="employe">
        <c:forEach var="employe" items="${employes}">
            <option value="${employe.id}">${employe.name}</option>
        </c:forEach>
    </select>
    <br>
    <label for="project">Project:</label>
    <select name="projectId" id="project">
        <c:forEach var="project" items="${projects}">
            <option value="${project.id}">${project.name}</option>
        </c:forEach>
    </select>
    <br>
    <label for="implication">Implication (%):</label>
    <input type="number" name="implication" id="implication" min="0" max="100">
    <br>
    <button type="submit">Assign Project</button>
</form>
</body>
</html>
