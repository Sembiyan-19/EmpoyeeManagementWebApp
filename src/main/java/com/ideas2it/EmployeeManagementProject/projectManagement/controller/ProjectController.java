//                                                                             ;
package com.ideas2it.EmployeeManagementProject.projectManagement.controller;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.ideas2it.EmployeeManagementProject.projectManagement.service.impl.ProjectServiceImpl;
import com.ideas2it.EmployeeManagementProject.employeeManagement.model.Employee;
import com.ideas2it.EmployeeManagementProject.employeeManagement.service.EmployeeService;
import com.ideas2it.EmployeeManagementProject.employeeManagementException.EmployeeManagementException;
import com.ideas2it.EmployeeManagementProject.projectManagement.model.Project;
import com.ideas2it.EmployeeManagementProject.projectManagement.service.ProjectService;

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

/**
 * Servlet implementation class that act as Controller
 * 
 * @version 1.1 21 April 2021
 * @author Sembiyan
 */
@Controller
public class ProjectController extends HttpServlet {
    
	ApplicationContext appContext
			= new ClassPathXmlApplicationContext("bean.xml");
	private ProjectService projectService 
			= (ProjectService) appContext.getBean("projectService");

    /**
     * Goes to the home page
     */
    @RequestMapping("/index")
	private String goHomePage() {
    	return "index";
	}
    
    @RequestMapping("/showAllProjects")
    private ModelAndView showAllProjects() {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		modelAndView.addObject("projects", projectService.getAllProjects());
    		modelAndView.setViewName("projectHome");
    	} catch (EmployeeManagementException e) {
    		modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
    }

    @RequestMapping("/newProject")
   	private ModelAndView createNewProject() {
    	ModelAndView modelAndView = new ModelAndView("projectForm");
    	modelAndView.addObject("command", new Project());
    	modelAndView.addObject("action", "saveProject");
       	return modelAndView;
    }

    @RequestMapping(value="/saveProject", method = RequestMethod.POST)  
    private ModelAndView addProject(@ModelAttribute Project project) {
    	ModelAndView modelAndView = new ModelAndView();
        try {
   			if (projectService.checkProjectIdPresenceWithDeleted(project.getId())) {
   				modelAndView.addObject("message", "Project Id already used");
   	    		modelAndView.setViewName("displayMessage");
   			} else {
   				projectService.addProject(project);
   	    		modelAndView.setViewName("redirect:/showAllProjects");
   			}
   		} catch (EmployeeManagementException e) {
   			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
   		}
        return modelAndView;
    }

    
    @RequestMapping(value="/showProject")
    private ModelAndView showProject(@RequestParam  int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		if (projectService.checkProjectIdPresence(id)) {
    			modelAndView.addObject("project", projectService.retrieveProject(id));
    			modelAndView.setViewName("viewProject");
    		} else {
    			modelAndView.addObject("message", "Project Id not present");
        		modelAndView.setViewName("displayMessage");
    		}
    	} catch (EmployeeManagementException e) {
    		modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
    }

    @RequestMapping("/showDeletedProjects")
    private ModelAndView showDeletedProjects() {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		modelAndView.addObject("projects",
    				projectService.getDeletedProjects());
    		modelAndView.setViewName("deletedProjects");
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
	}
    
    @RequestMapping(value="/deleteProject/{id}")
    private ModelAndView deleteProject(@PathVariable int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		projectService.deleteProject(id);
    		modelAndView.setViewName("redirect:/showAllProjects");
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
    }
    
    @RequestMapping(value="/restoreProject/{id}")
    private ModelAndView restoreProject(@PathVariable int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		projectService.restoreProject(id);
    		modelAndView.setViewName("redirect:/showAllProjects");
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
	}    

    @RequestMapping(value="/assignEmployee/{projectId}/{employeeId}")
	private ModelAndView assignAEmployee(@PathVariable int projectId,
			@PathVariable int employeeId) {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		projectService.assignAEmployee(projectId, employeeId);
    		modelAndView.setViewName("redirect:/showProject?id="+projectId);
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
		return modelAndView;
	}

    @RequestMapping(value="/showAvailableEmployees")
	private ModelAndView showAvailableEmployees(@RequestParam int id) {
    	ModelAndView modelAndView = new ModelAndView();
    	try {
    		modelAndView.addObject("project", 
    				projectService.retrieveProject(id));
    		modelAndView.addObject("availableEmployees", 
    				projectService.getAvailableEmployees(id));
    		modelAndView.setViewName("viewProject");
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
		return modelAndView;
	}

	@RequestMapping(value="/unassignEmployee/{projectId}/{employeeId}")
	private ModelAndView unnassignAEmployee(@PathVariable int projectId,
			@PathVariable int employeeId) {
		ModelAndView modelAndView = new ModelAndView();
    	try {
			projectService.unassignAEmployee(projectId, employeeId);
			modelAndView.setViewName("redirect:/showProject?id="+projectId);
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
		return modelAndView;
	}

	@RequestMapping("/editProject")
    private ModelAndView editProject(@RequestParam int id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("command", projectService.retrieveProject(id));
			modelAndView.addObject("action", "updateProject");
			modelAndView.addObject("id", id);
			modelAndView.setViewName("projectForm");
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
    	return modelAndView;
    }
    
	@RequestMapping(value="/updateProject", method = RequestMethod.POST)
    private ModelAndView updateProject(@ModelAttribute Project project) {
		ModelAndView modelAndView = new ModelAndView();
        try {
        	projectService.updateProject(project);
        	modelAndView.setViewName("redirect:/showProject?id="+project.getId());
		} catch (EmployeeManagementException e) {
			modelAndView.addObject("message", e.getMessage());
    		modelAndView.setViewName("displayMessage");
		}
        return modelAndView;
    }    
}