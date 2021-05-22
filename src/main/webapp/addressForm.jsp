<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
  
        <h1>Enter the Address Details</h1>  
       <form:form method="post" action="${action}">    
        <table >    
         <tr>    
          <td><form:input path="doorNumber" required="required" placeholder="Door Number"/></td>  
         </tr>
         <tr>    
          <td><form:input path="street" required="required" placeholder="Street"/></td>  
         </tr>
         <tr>       
          <td><form:input path="city" required="required" placeholder="City"/></td>  
         </tr>
         <tr>     
          <td><form:input path="pincode" required="required" placeholder="Pincode"/></td>  
         </tr>
         <tr>     
          <td><form:input path="state" required="required" placeholder="State"/></td>  
         </tr>
         <tr>    
          <td><form:input path="country" required="required" placeholder="Country"/></td>  
         </tr>
         <tr>
          <td><input type="submit"/></td>    
         </tr>    
        </table>    
       </form:form>
       <br><br><br>
       <a href="showEmployee?id=${id}"><button>Back</button></a>
       