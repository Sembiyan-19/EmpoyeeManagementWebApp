<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
  
        <h1>Enter the Employee Details</h1>  
       <form:form method="post" action="${action}" modelAttribute="employee">    
        <table > 
        <tr>    
          <td>Id : </td>   
          <td><form:input path="id" required="required" /></td>  
         </tr>   
         <tr>    
          <td>Name : </td>   
          <td><form:input path="name" required="required" /></td>  
         </tr>    
         <tr>    
          <td>Salary :</td>    
          <td><form:input path="salary" required="required" /></td>  
         </tr>
         <tr>    
          <td>Mobile Number :</td>    
          <td><form:input path="mobileNumber" required="required" pattern="[6789][0-9]{9}"/></td>  
         </tr>
         <tr>    
          <td>Date of birth :</td>    
          <td><form:input path="dateOfBirth" type="date" required="required"/></td>  
         </tr>
         
         <c:if test="${action == 'saveEmployee'}">
         
         <tr>    
          <td>Permanent Address :</td>    
          <td><form:input path="addresses[0].doorNumber" required="required" placeholder="Door Number"/></td>  
         </tr>
         <tr>    
          <td></td>    
          <td><form:input path="addresses[0].street" required="required" placeholder="Street"/></td>  
         </tr>
         <tr>    
          <td></td>    
          <td><form:input path="addresses[0].city" required="required" placeholder="City"/></td>  
         </tr>
         <tr>    
          <td></td>    
          <td><form:input path="addresses[0].pincode" required="required" placeholder="Pincode"/></td>  
         </tr>
         <tr>    
          <td></td>    
          <td><form:input path="addresses[0].state" required="required" placeholder="State"/></td>  
         </tr>
         <tr>    
          <td></td>    
          <td><form:input path="addresses[0].country" required="required" placeholder="Country"/></td>  
         </tr>
         </c:if>
         <tr>
          <td> </td>    
          <td><input type="submit"/></td>    
         </tr>    
        </table>    
       </form:form>    
       <br><br><br>
       <c:if test="${action == 'saveEmployee'}">
       <a href="showAllEmployees"><button>Back</button></a>
       </c:if>
       <c:if test="${action == 'updateEmployee'}">
       <a href="showEmployee?id=${id}"><button>Back</button></a>
       </c:if>