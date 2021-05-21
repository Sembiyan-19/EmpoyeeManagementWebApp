//                                                                             ;
package com.ideas2it.projectManagement.controller;

import java.util.List;

import com.ideas2it.projectManagement.service.impl.ProjectServiceImpl;
import com.ideas2it.projectManagement.service.ProjectService;

/**
 * Class that acts as a controller
 *
 * @version 2.1 18 March 2021
 * @author Sembiyan
 */
public class ProjectController {

    private ProjectService projectService = new ProjectServiceImpl();

    /**
     * Adds the employee details such as project Id, name, manager & department
     * @param projectId           Project Id
     * @param projectName          name of the project
     * @param projectManager          manager of the project
     * @param department          department to which the project is associated
     * @return         
     *     true if details are successfully added else returns false
     */
    public boolean addProject(int projectId, String projectName,
                String projectManager, String department) {
        return projectService.addProject(projectId, projectName,
                projectManager, department);
    }

    /**
     * Retrieves the project details such as project Id, name, manager,
     * department, and list of employees
     * @param projectId          Id of the project which has to be retrieved
     * @return         details of the project in string format
     */
    public String retrieveProject(int projectId) {
        return projectService.retrieveProjectDetails(projectId);
    }

   /**
     * Deletes a project details
     * @param projectId          Id of the project which has to be deleted
     * @return        
     *     true if details are successfully deleted else returns false
     */
    public boolean deleteProjectDetails(int projectId) {
        return projectService.deleteProjectDetails(projectId);
    }

    /**
     * Updates the project details such as project name, manager,
     * and department
     * @param projectId
     *      project Id to which the details have to be updated
     * @param projectName        updated name for the project
     * @param projectManager         updated manager for the project
     * @param department         updated department for the project
     * @return
     *     true if details are successfully updated else returns false
     */
    public boolean updateProject(int projectId, String projectName,
                String projectManager, String department) {
        return projectService.updateProject(projectId, projectName,
                projectManager, department);
    }

    /**
     * Assigns employeed to a project
     * @param projectId
     *     Id of the project to which the employees has to be assigned
     * @param employeeIds
     *     employee ids which has to be assigned for the project
     * @return  
     *     true if employees are succesfully assigned else returns false
     */
    public boolean assignEmployees(int projectId,
            List<Integer> employeeIds) {
        return projectService.assignEmployees(projectId, employeeIds);
    }

    /**
     * Unassigns employees from the project
     * @param projectId
     *     Id of the project from which the employees has to be unassigned
     * @param employeeIds
     *     employee ids which has to be unassigned from the project
     * @return  
     *     true if employees are succesfully unassigned else returns false
     */
    public boolean unassignEmployees(int projectId,
            List<Integer> employeeIds) {
        return projectService.unassignEmployees(projectId,
                employeeIds);
    }

    /**
     * Fetches the list of projects, converts them to string format,
     * adds them to a list and returns the list
     * @return         complete list of projects
     */
    public List<String> getAllProjects() {
        return projectService.getAllProjects();
    }

    /**
     * Checks the presence of project id
     * @param projectId          ID of project which has to be checked
     * @return
     *     true if project id is present else returns false
     */
    public boolean checkProjectIdPresence(int projectId) {
        return projectService.checkProjectIdPresence(projectId);
    }

    /**
     * Checks the presence of project id including deleted projects
     * @param projectId          ID of project which has to be checked
     * @return
     *     true if project id is present else returns false
     */
    public boolean checkProjectIdPresenceWithDeleted(int projectId) {
        return projectService.checkProjectIdPresenceWithDeleted(projectId);
    }

    /**
     * Checks the presence of employees in a project
     * @param employeeIds          ID of employees which has to be checked
     * @return
     *     Ids which are not present in the project
     */
    public List<Integer> checkEmployeesPresence(List<Integer> employeeIds) {
        return projectService.checkEmployeesPresence(employeeIds);
    }

    /**
     * Checks the presence of an employee in a project
     * @param projectId
     *     ID of project in which the employee has to be checked
     * @param employeeId        ID of the employee
     * @return
     *     true if employee is present else returns false
     */
    public List<List<Integer>> checkEmployeesInProject(int projectId,
            List<Integer> employeeIds) {
        return projectService.checkEmployeesInProject(projectId, employeeIds);
    }
}






























