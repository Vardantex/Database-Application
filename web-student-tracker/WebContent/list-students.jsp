<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student Tracker App</title>

	<link type="text/css" rel="stylesheet" href="css/style.css" />


</head>
<body>

	<div id="wrapper">
		<div id="header">
			<h2>Collection of Information</h2>
		</div>
	</div>
	
	<div id="container">
		<div id="content">
			
			<!--  Add new button for Student -->
			<input type="button" value="Add Student" 
			onclick="window.location.href='add-student-form.jsp'; return false;"
			 class="add-student-button"/>
			
			
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
			
			<c:forEach var="tempStudent" items="${STUDENT_LIST}" >
			
			
			<!-- set a link for each student  -->
			<c:url var="tempLink" value="StudentControllerServlet">
				<c:param name="command" value="LOAD" />
				<c:param name="studentId" value="${tempStudent.id}"/>
			</c:url>

			<!-- set a link for deleting a student  -->
			<c:url var="deleteLink" value="StudentControllerServlet">
				<c:param name="command" value="DELETE" />
				<c:param name="studentId" value="${tempStudent.id}"/>
			</c:url>
			
			<tr>
				<td>  ${ tempStudent.firstName } </td>
				<td> ${ tempStudent.lastName }</td>
				<td> ${ tempStudent.email }</td>
				<!-- Create a link from JSTL core to update students -->
				<td><a href="${tempLink}">Update</a> 
				|
				<a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this user?')))
				return false">Delete</a></td>
			</tr>
			
			</c:forEach>
			</table>
			
			
		</div>
	</div>

</body>
</html>