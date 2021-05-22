package com.ideas2it.EmployeeManagementProject.employeeManagement.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.ideas2it.EmployeeManagementProject.employeeManagement.model.Address;
import com.ideas2it.EmployeeManagementProject.employeeManagement.model.Employee;
import com.ideas2it.EmployeeManagementProject.employeeManagement.service.EmployeeService;
import com.ideas2it.EmployeeManagementProject.employeeManagement.service.impl.EmployeeServiceImpl;
import com.ideas2it.EmployeeManagementProject.employeeManagementException.EmployeeManagementException;
import com.ideas2it.EmployeeManagementProject.projectManagement.model.Project;

/**
 * Servlet implementation class projectController
 * 
 * @version 1.1 21 April 2021
 * @author Sembiyan
 */
@Controller
public class EmployeeController extends HttpServlet {
	
	ApplicationContext appContext
    		= new ClassPathXmlApplicationContext("bean.xml");
	private EmployeeService employeeService 
			= (EmployeeService) appContext.getBean("employeeService");
      
    @RequestMapping("/")
	private String goToIndex() {
    	return "index";
	}

    @RequestMapping("/showAllEmployees")
    private ModelAndView showAllEmployees() {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		modelAndView.addObject("employees", 
    				employeeService.getAllEmployees());
    		modelAndView.setViewName("employeeHome");
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
    }
   
    @RequestMapping("/newEmployee")
	private ModelAndView createNewEmployee() {
    	ModelAndView modelAndView = new ModelAndView("employeeForm");
    	modelAndView.addObject("employee", new Employee());
       	modelAndView.addObject("action", "saveEmployee");
       	return modelAndView;
	}
	
    @RequestMapping(value="/saveEmployee", method = RequestMethod.POST)
    private ModelAndView addEmployee(@ModelAttribute Employee employee) {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		if(employeeService.checkEmployeeIdPresenceIncludingDeleted(employee.getId())) {
    			modelAndView.addObject("message", "Employee Id already used");
        		modelAndView.setViewName("displayMessage");
    		}  else {
   				employeeService.addEmployee(employee);
   	    		modelAndView.setViewName("redirect:/showAllEmployees");
   			}
    	} catch (EmployeeManagementException e) {
    		modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
    }
    
    @RequestMapping(value="/showEmployee")
    private ModelAndView showEmployee(@RequestParam  int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		if (employeeService.checkEmployeeIdPresence(id)) {
    			modelAndView.addObject("employee", 
    					employeeService.retrieveEmployee(id));
    			modelAndView.setViewName("viewEmployee");
    		} else {
    			modelAndView.addObject("message", "Employee Id not present");
        		modelAndView.setViewName("displayMessage");
    		}
    	} catch (EmployeeManagementException e) {
    		modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
    }

    @RequestMapping("/showDeletedEmployees")
    private ModelAndView showDeletedEmployees() {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		modelAndView.addObject("employees",
    				employeeService.getDeletedEmployees());
    		modelAndView.setViewName("deletedEmployees");
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
	}

    @RequestMapping(value="/deleteEmployee/{id}")
    private ModelAndView deleteEmployee(@PathVariable int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		employeeService.deleteEmployee(id);
    		modelAndView.setViewName("redirect:/showAllEmployees");
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
    }
    
    @RequestMapping(value="/restoreEmployee/{id}")
    private ModelAndView restoreProject(@PathVariable int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		employeeService.restoreEmployee(id);
    		modelAndView.setViewName("redirect:/showAllEmployees");
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
	}    
    
    @RequestMapping(value="/assignProject/{projectId}/{employeeId}")
	private ModelAndView assignAProject(@PathVariable int projectId,
			@PathVariable int employeeId) {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		employeeService.assignAProject(projectId, employeeId);
    		modelAndView.setViewName("redirect:/showEmployee?id="+employeeId);
    	} catch (EmployeeManagementException e) {
    		modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
    	}
    	return modelAndView;
    }

    @RequestMapping(value="/showAvailableProjects")
	private ModelAndView showAvailableProjects(@RequestParam int id) {
    	ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("employee",
					employeeService.retrieveEmployee(id));
			modelAndView.addObject("availableProjects",
					employeeService.getAvailableProjects(id));
			modelAndView.setViewName("viewEmployee");
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
		return modelAndView;
	}

    @RequestMapping(value="/unassignProject/{projectId}/{employeeId}")
	private ModelAndView unnassignAProject(@PathVariable int projectId,
			@PathVariable int employeeId) {
    	ModelAndView modelAndView = new ModelAndView();
		try {
			employeeService.unassignAProject(projectId, employeeId);
			modelAndView.setViewName("redirect:/showEmployee?id="+employeeId);
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
		return modelAndView;
	}

    @RequestMapping("/editEmployee")
	private ModelAndView editEmployee(@RequestParam int id) {
    	ModelAndView modelAndView = new ModelAndView();
		try {
	    	modelAndView.addObject("command", 
	    			employeeService.retrieveEmployee(id));
	    	modelAndView.addObject("action", "updateEmployee");
	    	modelAndView.addObject("id", id);
	    	modelAndView.setViewName("employeeForm");
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
		return modelAndView;
	}

	@RequestMapping(value="/updateEmployee", method = RequestMethod.POST)
    private ModelAndView updateEmployee(@ModelAttribute Employee employee) {
		ModelAndView modelAndView = new ModelAndView();
        try {
        	employeeService.updateEmployee(employee);
        	modelAndView.setViewName("redirect:/showEmployee?id="+employee.getId());
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
        return modelAndView;
    }
    
    @RequestMapping("/newAddress")
	private ModelAndView createNewAddress(@RequestParam int employeeId) {
    	ModelAndView modelAndView = new ModelAndView("addressForm");
    	modelAndView.addObject("command", new Address());
       	modelAndView.addObject("action", 
       			"saveAddress?employeeId="+employeeId);
       	modelAndView.addObject("id", employeeId);
       	return modelAndView;
	}
	
    @RequestMapping(value="/saveAddress", method = RequestMethod.POST)
    private ModelAndView addNewAddress(@RequestParam int employeeId, 
    		@ModelAttribute Address address) {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		employeeService.addNewAddress(employeeId, address);
    		modelAndView.setViewName("redirect:/showEmployee?id="+employeeId);
    	} catch (EmployeeManagementException e) {
    		modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
    }
	
    @RequestMapping("/deleteAddress")
	private ModelAndView deleteExistingAddress(@RequestParam int id, 
			@RequestParam int addressId) {
    	ModelAndView modelAndView = new ModelAndView();
		try {
			employeeService.deleteExistingAddress(id, addressId);
			modelAndView.setViewName("redirect:/showEmployee?id="+id);
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
		return modelAndView;
	}

    @RequestMapping("/editAddress")
    private ModelAndView editAddress(@RequestParam int employeeId, @RequestParam int option) {
    	ModelAndView modelAndView = new ModelAndView();
    	Address address = null;
    	try {
    		address = employeeService.retrieveEmployee(employeeId).getAddresses().get(option);
    		modelAndView.addObject("command", address);
           	modelAndView.addObject("action", 
           			"updateAddress?employeeId="+employeeId+"&addressId="+address.getId());
           	modelAndView.addObject("id", employeeId);
           	modelAndView.setViewName("addressForm");
    	} catch (Exception e ) {
    		modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
    	}
       	return modelAndView;
    }
	
    @RequestMapping("/updateAddress")
    private ModelAndView updateAddress(@RequestParam int addressId, 
    		@RequestParam int employeeId, @ModelAttribute Address address) {
    	address.setId(addressId);
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		employeeService.updateExistingAddress(employeeId, address);
    		modelAndView.setViewName("redirect:/showEmployee?id="+employeeId);
    	} catch (EmployeeManagementException e) {
    		modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
    }
}
