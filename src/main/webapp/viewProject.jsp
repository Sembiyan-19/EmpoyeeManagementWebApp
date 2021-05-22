
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.ideas2it.EmployeeManagementProject.projectManagement.model.Project" %>
<%@ page import = "com.ideas2it.EmployeeManagementProject.employeeManagement.model.Employee" %>
<%@ page import = "java.util.List" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1">
    <title>View Project</title>
  </head>
  <body>
    <a href = "project?action=index"><button>Homeee</button></a>
    <br><br><br>
	<% 
	    Project project = (Project)request.getAttribute("project"); 
	    if (null != project) {
	    	List<Employee> employees = project.getEmployees();
	%>
	<table border="1px" width="50%">
	  <tr>
	    <th colspan = "2">PROJECT DETAILS</th>
	  </tr>
	  <tr>
	    <td>Id</td>
	    <td><%= project.getId() %></td>
	  </tr>
	  <tr>
	    <td>Name</td>
	    <td><%= project.getName() %></td>
	  </tr>
	  <tr>
	    <td>Manager</td>
	    <td><%= project.getManager() %></td>
	  </tr>
	  <tr>
	    <td>Department</td>
	    <td><%= project.getDepartment() %></td>
	  </tr>
	</table><br><br><br>
	  <% if (0 != employees.size()) { %>
	<table border="1px" width="50%">
	  <tr>
		<th colspan = "2">Employees Associated</th>
		<th>Unassign</th>
	  </tr>
	    <% for(Employee employee : employees) { %>
	  <tr>
	    <td><%= employee.getId() %></td>
	    <td><%= employee.getName() %></td>
	    <td>
	      <a href = "project?action=unassignEmployee&projectId=<%=
	          project.getId() %>&employeeId=<%= employee.getId() %>">
	        <button>Unassign</button>
	      </a>
	    </td>
	  </tr>
	    <% } %>
	</table>
	<% } %>
	<br><br><br>
	<a href = "project?action=showAvailableEmployees&id=<%= project.getId() %>">
	  <button>Assign employees</button>
	</a>
	<a href = "project?action=deleteProject&id=<%= project.getId() %>">
	  <button>Delete</button>
	</a>
	<a href = "project?action=editProject&id=<%= project.getId() %>">
	  <button>Edit</button>
	</a>
	<a href="project?action=showAllProjects"><button>Back</button></a>
    <br><br><br>
    <% 
        List<Employee> availableEmployees 
                = (List<Employee>)request.getAttribute("availableEmployees");
        if (null != availableEmployees) {
    %>
    <table border="1px" width="50%">
	  <tr>
		<th colspan = "2">Available employees</th>
		<th>Action</th>
	  </tr>
        <%  for (Employee employee : availableEmployees) { %>
      <tr>
	    <td><%= employee.getId() %></td>
	    <td><%= employee.getName() %></td>
	    <td>
	      <a href = "project?action=assignEmployee&projectId=<%=
	          project.getId() %>&employeeId=<%= employee.getId() %>">
	      	<button>Assign</button>
	      </a>
	    </td>
	  </tr>
        <% } %>
    </table>
      <% } %>
    <% } %>
  </body>
</html>