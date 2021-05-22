<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
  
        <h1>Enter the Project Details</h1>  
       <form:form method="post" action="${action}">    
        <table > 
        <tr>    
          <td>id : </td>   
          <td><form:input path="id" required="required" /></td>  
         </tr>   
         <tr>    
          <td>Name : </td>   
          <td><form:input path="name" required="required" /></td>  
         </tr>    
         <tr>    
          <td>Manager :</td>    
          <td><form:input path="manager" required="required" /></td>  
         </tr>   
         <tr>    
          <td>Department :</td>    
          <td><form:input path="department" required="required" /></td>  
         </tr>   
         <tr>    
          <td> </td>    
          <td><input type="submit"/></td>    
         </tr>    
        </table>    
       </form:form>    
       <br><br><br>
       <c:if test="${action == 'saveProject'}">
       <a href="showAllProjects"><button>Back</button></a>
       </c:if>
       <c:if test="${action == 'updateProject'}">
       <a href="showProject?id=${id}"><button>Back</button></a>
       </c:if>