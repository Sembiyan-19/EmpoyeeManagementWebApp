//                                                                             ;
package com.ideas2it.EmployeeManagementProject.employeeManagement.service;

import java.util.Date;
import java.util.List;

import com.ideas2it.EmployeeManagementProject.employeeManagement.model.Address;
import com.ideas2it.EmployeeManagementProject.employeeManagement.model.Employee;
import com.ideas2it.EmployeeManagementProject.employeeManagementException.EmployeeManagementException;
import com.ideas2it.EmployeeManagementProject.projectManagement.model.Project;

/**
 * Interface to provide services for employee management
 *
 * @version 1.1 25 March 2021
 * @author Sembiyan
 */
public interface EmployeeService {

    /**
     * Adds the address details such as door number, street, city, pincode,
     * state, country and address list
     * @param id          id of the employee
     * @param name         name of the employee
     * @param salary          salary of the employee
     * @param mobileNumber          mobile number of the employee
     * @param dateOfBirth          date of birth of the employee
     * @param addresses         list containing addresses
     * @return         true is details are successfully added else returns false
     * @throws EmployeeManagementException 
     */
    public boolean addEmployee(Employee employee) 
            throws EmployeeManagementException;

    /**
     * Retrieves the employee object
     * @param id          employee id whose details should be retrieved
     * @return            returns employee object
     * @throws EmployeeManagementException 
     */
    public Employee retrieveEmployee(int id) throws EmployeeManagementException;

     /**
     * Removes a employee's employees
     * @param id          ID of employee whose details should be deleted
     * @return
     *     true if employee's details are successfully deleted
     * else returns false
     * @throws EmployeeManagementException 
     */
    public boolean deleteEmployee(int id) throws EmployeeManagementException;

    /**
     * Restores a deleted employee
     * @param id          Id of the employee who has to be restored
     * @return        
     *     true if employee is successfully restored else returns false
     * @throws EmployeeManagementException 
     */
    public boolean restoreEmployee(int id) throws EmployeeManagementException;
    
    /**
     * Updates the employee details such as  name, salary, mobile number,
     * date of birth
     * @param id           Id of the employee whose details should be updated
     * @param name          name of the employee
     * @param salary          salary of the employee
     * @param mobileNumber          mobile number of the employee
     * @param dateOfBirth          date of birth of the employee
     * @return         
     *     true if details are successfully updated else returns false
     * @throws EmployeeManagementException 
     */
    public boolean updateEmployee(Employee employee) 
            throws EmployeeManagementException;

    /**
     * Adds new address to the employee
     * @param id       ID of the employee to whom the address should be added
     * @param addressDetails        list containg an address' details
     * @return
     *     true if a new address is successfully added else returns false
     * throws EmployeeManagementException 
     */
    public boolean addNewAddress(int id, Address address) 
            throws EmployeeManagementException;

    /**
     * Deletes a particular address of a employee
     * @param id         ID of the employee whose address should be deleted
     * @param selectedAddressOption 
     *         Option that points the address which has to be deleted
     * @return
     *     true if a address is successfully deleted else returns false
     * @throws EmployeeManagementException 
     */
    public boolean deleteExistingAddress(int id, int selectedAddressOption) 
            throws EmployeeManagementException;

    /**
     * Updates the mobile number of the employee
     * @param id
     *         ID of the employee to whom the details has to be updated
     * @param selectedAddressOption
     *         Option that points the address to which the details
     * has to be updated
     * @param doorNumber          door number of the employee
     * @param street         street of the employee
     * @param city          city of the employee
     * @param pincode          pincode of the employee
     * @param state          state of the employee
     * @param country          country of the employee
     * @param addressType        type of address
     * @throws EmployeeManagementException 
     */
    public boolean updateExistingAddress(int id, Address address) 
            throws EmployeeManagementException;

    /**
     * Retrieves all the addresses of an employee
     * @param id         ID of the employee whose addresses should be retrieved
     * @return           list of address
     * @throws EmployeeManagementException 
     */
    public List<Employee> getAllEmployees() throws EmployeeManagementException;

    /**
     * Fetches the list of employees which are deleted
     * @return         complete list of employees which are deleted
     * @throws EmployeeManagementException 
     */
    public List<Employee> getDeletedEmployees() 
            throws EmployeeManagementException;
    
    /**
     * Assigns project for a employee
     * @param projectId
     *     Id of project which has to be assigned for a employee
     * @param employeeId
     *     Id of the employee to whom the project should be assigned
     * @return         
     *     true if project is successfully assigned for a employee,
     * else returns false
     * @throws EmployeeManagementException 
     */
    public void assignAProject(int projectId, int employeeId) 
            throws EmployeeManagementException;

    /**
     * Unassigns project for a employee
     * @param projectId
     *     Id of project which has to be unassigned
     * @param employeeId
     *     Id of the employee to whom the project should be unassigned
     * @return         
     *     true if project is successfully unassigned for a employee,
     * else returns false
     * @throws EmployeeManagementException 
     */
    public void unassignAProject(int projectId, int employeeId) 
            throws EmployeeManagementException;

    /**
     * Gets the projects which can be assigned for a employee
     * @param employeeId
     *     Id of employee
     * @return         
     *     list of projects which can be assigned for the employee
     * @throws EmployeeManagementException 
     */
    public List<Project> getAvailableProjects(int employeeId) 
            throws EmployeeManagementException;

    /**
     * Checks the presence of employee
     * @param id          ID of employee which has to be checked
     * @return
     *     true if Id is present else returns false
     * @throws EmployeeManagementException 
     */
    public boolean checkEmployeeIdPresence(int id) 
            throws EmployeeManagementException;

    /**
     * Checks the presence of employee icluding the ids which are 
     * deleted
     * @param id          ID of employee which has to be checked
     * @return
     *     true if Id is present else returns false
     * @throws EmployeeManagementException 
     */
    public boolean checkEmployeeIdPresenceIncludingDeleted(int id) 
            throws EmployeeManagementException;

    /**
     * Checks whether the mobile number is valid.
     * @param mobileNumber           mobile number that has to be validated
     * @return          true if the mobile number is valid, else returns false
     */
    public boolean validateMobileNumber(String mobileNumber);

    /**
     * Checks the date format and returns the date if it is valid.
     * @param dateOfBirth             date of birth in string format
     * @return      
     *     the date if it is valid, else return null if the date is not valid
     * @throws EmployeeManagementException 
     */
    public Date getDateOfBirth(String dateOfBirth) 
            throws EmployeeManagementException;
}