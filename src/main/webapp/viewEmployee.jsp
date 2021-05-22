<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.ideas2it.EmployeeManagementProject.projectManagement.model.Project" %>
<%@ page import = "com.ideas2it.EmployeeManagementProject.employeeManagement.model.Employee" %>
<%@ page import = "com.ideas2it.EmployeeManagementProject.employeeManagement.model.Address" %>
<%@ page import = "java.util.List" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="ISO-8859-1">
    <title>View employee</title>
  </head>
  <body>
    <a href = "index"><button>Home</button></a><br><br><br>
	<% 
	  Employee employee = (Employee)request.getAttribute("employee"); 
	  if (null != employee) {
	    List<Project> projects = employee.getProjects();
	    List<Address> addresses = employee.getAddresses();
	%>
	<table border="1px" width="50%">
	  <tr>
	    <th colspan = "2">EMPLOYEE DETAILS</th>
	  </tr>
	  <tr>
	    <td>Id</td>
	    <td><%= employee.getId() %></td>
	  </tr>
	  <tr>
	    <td>Name</td>
	    <td><%= employee.getName() %></td>
	  </tr>
	  <tr>
	    <td>Salary</td>
	    <td><%= employee.getSalary() %></td>
	  </tr>
	  <tr>
	    <td>mobile Number</td>
	    <td><%= employee.getMobileNumber() %></td>
	  </tr>
	  <tr>
	    <td>Date of Birth</td>
	    <td><%= employee.getDateOfBirth() %></td>
	  </tr>
	</table>
    <br><br><br>
      <% if (0 != addresses.size()) { %>
    <table border="1px" width="50%">
	  <tr>
		<th colspan = "10">Address(es)</th>
	  </tr>
	  <tr>
		<th>Door Number</th>
		<th>Street</th>
		<th>City</th>
		<th>Pincode</th>
		<th>State</th>
		<th>Country</th>
		<th>Type</th>
		<th colspan = "2">Actions</th>
	  </tr>
	    <% int i = 0;
	      for(Address address : addresses) { 
	    %>
	  <tr>
	    <td><%= address.getDoorNumber() %></td>
	    <td><%= address.getStreet() %></td>
	    <td><%= address.getCity() %></td>
	    <td><%= address.getPincode() %></td>
	    <td><%= address.getState() %></td>
	    <td><%= address.getCountry() %></td>
	    <td><%= address.getType() %></td>
	      <% i++; %>
	    <td>
	      <a href = "editAddress?option=<%= i - 1 %>&employeeId=<%= employee.getId() %>">
	    	<button>Edit</button>
	      </a>
	    </td>
	    <td>
	    	<a href = "deleteAddress?addressId=<%= address.getId() %>&id=<%= employee.getId() %>">
	    	  <button>Delete</button>
	    	</a>
	    </td>
	  </tr>
	    <% } %>
	</table>
	
	  <% } %>
	  
	<br><br><br>
	<% if (0 != projects.size()) { %>
	<table border="1px" width="50%">
	  <tr>
		<th colspan = "2">Projects Associated</th>
		<th>Unassign</th>
	  </tr>
	    <% for(Project project : projects) { %>
	  <tr>
	    <td><%= project.getId() %></td>
	    <td><%= project.getName() %></td>
	    <td>
	      <a href = "unassignProject/<%= project.getId() %>/<%= employee.getId() %>">
	    	<button>Unassign</button>
	      </a>
	    </td>
	  </tr>
	    <% } %>
	</table>
	<% } %>
	<br><br>
	<a href = "newAddress?employeeId=<%= employee.getId() %>">
	  Add new address
	</a>
	<br><br><br>
	<a href = "showAvailableProjects?id=<%= employee.getId() %>">
	  <button>Assign projects</button>
	</a>
	<a href = "deleteEmployee/<%= employee.getId() %>">
	  <button>Delete</button>
	</a>
	<a href = "editEmployee?id=<%= employee.getId() %>">
	  <button>Edit</button>
	</a>
	<a href="showAllEmployees"><button>Back</button></a>
	<br><br><br>
	  <% 
        List<Project> availableProjects 
                = (List<Project>)request.getAttribute("availableProjects");
        if (null != availableProjects) { 
      %>
    <table border="1px" width="50%">
	  <tr>
		<th colspan = "2">Available projects</th>
		<th>Unassign</th>
      </tr>
	  <tr>
		<th>Id</th>
		<th>Name</th>
		<th>Action</th>
      </tr>
        <%  for (Project project : availableProjects) { %>
      <tr>
	    <td><%= project.getId() %></td>
	    <td><%= project.getName() %></td>
	    <td>
	      <a href = "assignProject/<%= project.getId() %>/<%= employee.getId() %>">
	        <button>Assign</button>
	      </a>
	    </td>
	  </tr>
        <% } %>
    </table><br><br>
    <a href = "showEmployee?id=<%= employee.getId() %>">
      <button>Cancel</button>
    </a>
      <% } %>
    <% } %>
  </body>
</html>