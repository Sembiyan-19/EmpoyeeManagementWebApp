//                                                                             ;
package com.ideas2it.EmployeeManagementProject.employeeManagement.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.ideas2it.EmployeeManagementProject.employeeManagement.dao.EmployeeDao;
import com.ideas2it.EmployeeManagementProject.employeeManagement.dao.impl.EmployeeDaoImpl;
import com.ideas2it.EmployeeManagementProject.employeeManagement.model.Address;
import com.ideas2it.EmployeeManagementProject.employeeManagement.model.Employee;
import com.ideas2it.EmployeeManagementProject.employeeManagement.service.EmployeeService;
import com.ideas2it.EmployeeManagementProject.employeeManagementException.EmployeeManagementException;
import com.ideas2it.EmployeeManagementProject.projectManagement.dao.ProjectDao;
import com.ideas2it.EmployeeManagementProject.projectManagement.model.Project;
import com.ideas2it.EmployeeManagementProject.projectManagement.service.impl.ProjectServiceImpl;
import com.ideas2it.EmployeeManagementProject.projectManagement.service.ProjectService;

/**
 * Class implementing service interface
 *
 * @version 1.1 21 April 2021
 * @author Sembiyan
 */
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;
    
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
    	this.employeeDao = employeeDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEmployee(Employee employee) 
            throws EmployeeManagementException {
        boolean isAdded = employeeDao.insertEmployee(employee);
        return isAdded;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public Employee retrieveEmployee(int id) 
            throws EmployeeManagementException {
    	Employee employee = employeeDao.retrieveEmployee(id);
    	List<Address> addresses = new ArrayList<Address>();
    	for (Address address : employee.getAddresses()) {
    		if (false == address.getIsDeleted()) {
    			addresses.add(address);
    		}
    	}
    	employee.setAddresses(addresses);
		return employee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEmployee(int id) throws EmployeeManagementException {
    	try {
	        Employee employee = employeeDao.retrieveEmployee(id);
	        employee.setIsDeleted(true);
			employee.setProjects(null);
	        return employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to delete employee");
    	}
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean restoreEmployee(int id) throws EmployeeManagementException {
    	try {
	        Employee employee = employeeDao.retrieveEmployee(id);
	        employee.setIsDeleted(false);
	        return employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to restore employee");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateEmployee(Employee updatedEmployee) 
            throws EmployeeManagementException {
    	try {
    		Employee employee = employeeDao.retrieveEmployee(updatedEmployee.getId());
    		List<Address> addresses = employee.getAddresses();
    		List<Project> projects = employee.getProjects();
    		updatedEmployee.setAddresses(addresses);
    		updatedEmployee.setProjects(projects);
	        return employeeDao.updateEmployee(updatedEmployee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to update employee");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addNewAddress(int id, Address address) 
            throws EmployeeManagementException {
    	try {
	        Employee employee = employeeDao.retrieveEmployee(id);
	        employee.getAddresses().add(address);
	        return employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to add address");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteExistingAddress(int id, int addressId) 
            throws EmployeeManagementException {
    	try {
	        Employee employee = employeeDao.retrieveEmployee(id);
	        List<Address> addresses = new ArrayList<Address>();
			for (Address address : employee.getAddresses()) {
				if (addressId != address.getId()) {
					addresses.add(address);
				}
			}
			employee.setAddresses(null);
			employee.setAddresses(addresses);
			return employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to delete address");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateExistingAddress(int id, Address updatedAddress) 
            throws EmployeeManagementException {
    	try {
			Employee employee = employeeDao.retrieveEmployee(id);
			for (Address address : employee.getAddresses()) {
				if (updatedAddress.getId() == address.getId()) {
					address.setDoorNumber(updatedAddress.getDoorNumber());
					address.setStreet(updatedAddress.getStreet());
					address.setCity(updatedAddress.getCity());
	            	address.setPincode(updatedAddress.getPincode());
	            	address.setState(updatedAddress.getState());
	            	address.setCountry(updatedAddress.getCountry());
				}
			}
	        return employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to update address");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignAProject(int projectId, int employeeId) 
            throws EmployeeManagementException {
        ProjectService projectService = new ProjectServiceImpl(null);
        try {
	        Employee employee = employeeDao.retrieveEmployee(employeeId);
	        List<Project> projects = employee.getProjects();
			projects.add(projectService.retrieveProject(projectId));
	        employee.setProjects(projects);
	        employeeDao.updateEmployee(employee);
        } catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to assign project for employee");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unassignAProject(int projectId, int employeeId) 
            throws EmployeeManagementException {
    	try {
	        Employee employee = employeeDao.retrieveEmployee(employeeId);
	        List<Project> projects = employee.getProjects();
	        int indexOfProject = 0;
	        int count=0;
	        for (Project project : projects) {
	            count++;
	            if (projectId == project.getId()) {
	                indexOfProject = count - 1;
	            }
	        }
	        projects.remove(indexOfProject);
	        employee.setProjects(projects);
	        employeeDao.updateEmployee(employee);
    	} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to unassign project for employee");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getAvailableProjects(int employeeId) 
            throws EmployeeManagementException {
        boolean isPresent;
        List<Project> availableProjects = new ArrayList<Project>();
        ProjectService projectService = new ProjectServiceImpl(null);
        try {
        	Employee employee = employeeDao.retrieveEmployee(employeeId);
			for (Project project : projectService.getAllProjects()) {
			    isPresent = false;
			    for (Project projectsInEmployee : employee.getProjects()) {
			        if (project.getId() == projectsInEmployee.getId()) {
			            isPresent = true;
			        }
			    }
			    if (false == isPresent) {
			        availableProjects.add(project);
			    }
			}
			return availableProjects;
        } catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to get available projects");
    	}
    }

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Employee> getAllEmployees() 
            throws EmployeeManagementException {
		return employeeDao.getAllEmployees(false);
	}
	
    /**
     * {@inheritDoc}
     */
	@Override
	public List<Employee> getDeletedEmployees() 
            throws EmployeeManagementException {
		try {
			return employeeDao.getAllEmployees(true);
		} catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to get deleted employees");
    	}
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkEmployeeIdPresence(int id) 
            throws EmployeeManagementException {
        boolean isPresent = false;
        try {
	        Employee employee = employeeDao.retrieveEmployee(id);
	        if (null != employee) {
	            if (false == employee.getIsDeleted()) {
	                isPresent = true;
	            }
	        }
	        return isPresent;
	    } catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to retrieve employee");
    	}
    }

    /**
     * {@inheritDoc}
     * @throws EmployeeManagementException 
     */
    @Override
    public boolean checkEmployeeIdPresenceIncludingDeleted(int id) 
            throws EmployeeManagementException {
        boolean isPresent = false;
        try {
	        if (null != employeeDao.retrieveEmployee(id)) {
	            isPresent = true;
	        }
	        return isPresent;
        } catch (EmployeeManagementException e) {
    		throw new EmployeeManagementException("Failed to add employee");
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateMobileNumber(String mobileNumber) {
        return (mobileNumber.matches("[6789]{1}[0-9]{9}")) ? true : false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getDateOfBirth(String dateOfBirth) 
            throws EmployeeManagementException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        dateFormat.setLenient(false);
        try {
			date = dateFormat.parse(dateOfBirth);
		} catch (ParseException e) {
			throw new EmployeeManagementException("Invalid date");
		}
        return date;
    }
}
