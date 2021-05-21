//                                                                             ;
package com.ideas2it.projectManagement.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ideas2it.projectManagement.controller.ProjectController;

/**
 * Class that performs view operations
 *
 * @version 2.1 18 March 2021
 * @author Sembiyan
 */
public class ProjectView {

    private ProjectController projectController = new ProjectController();
    private Scanner scanner = new Scanner(System.in);

    /**
     * Shows the various operations that can be performed
     */
    public void homePage() {
        int userOption;
        String displayMenu = "\n\nSelect the operation to be performed.\n 1. "
                + "Add new project details\n 2. Retrieve project details\n "
                + "3. Delete a project details\n 4. Update a project details"
                + "\n 5. Show all projects\n 6. Back to home page";
        do {
            System.out.println(displayMenu);
            userOption = scanner.nextInt();
            decideOperation(userOption);
        } while (6 != userOption);
    }

    /**
     * Fetches the project details such as project Id, name, manager,
     * department, and the list of employees associated
     */
    private void addProject() {
        System.out.println("Enter project ID");
        int projectId = scanner.nextInt();
        if (projectController.checkProjectIdPresenceWithDeleted(projectId)) {
            System.out.println(getSpacing() + "  ---  Project id already "
            	    + "present  ---" + getSpacing());
        } else {
            System.out.println("Enter project name");
            scanner.skip("[\r\n]+");
            String projectName = scanner.nextLine();
            System.out.println("Enter project manager");
            String projectManager = scanner.nextLine();
            System.out.println("Enter department");
            String department = scanner.nextLine();
            if (projectController.addProject(projectId, projectName,
                    projectManager, department)) {
                System.out.println(getSpacing() + "  ---  Successfully added"
                        + " new project details  ---" + getSpacing());
            } else {
                System.out.println(getSpacing() + "  ---  Failed to add "
                	    + "project details  ---" + getSpacing());
            }
        }    
    }

    /**
     * Retrieves and displays a specific project details such as project Id,
     * name, manager, department, and list of employees
     */
    private void retrieveProject() {
        System.out.println("Enter project ID");
        int projectId = scanner.nextInt();
        if (projectController.checkProjectIdPresence(projectId)) {
            System.out.println(projectController.retrieveProject(projectId));
        } else {
            System.out.println(getSpacing() + "  ---  Project Id not present"
            	    + "  ---" + getSpacing());
        }
    }

    /**
     * Deletes a project details
     */
    private void deleteProject() {
        System.out.println("Enter project ID");
        int projectId = scanner.nextInt();
        if (projectController.checkProjectIdPresence(projectId)) {
            if (projectController.deleteProjectDetails(projectId)) {
                System.out.println(getSpacing() + "  ---  Successfully deleted"
                        + " a project details  ---" + getSpacing());
            } else {
                System.out.println(getSpacing() + "  ---  Failed to delete"
                        + " project details  ---" + getSpacing());
            }
        } else {
            System.out.println(getSpacing() + "  ---  Project Id not present"
            	    + "  ---" + getSpacing());
        }
    }

    /**
     * Display various update options such as update project details, and
     * assigning and unassigning an employee for the project
     */
    private void updateProject() {
        System.out.println("Enter project ID");
        int projectId = scanner.nextInt();
        if (projectController.checkProjectIdPresence(projectId)) {
            System.out.println("\n\n1. Update project details\n2. Assign "
                    + "employees\n3. Un-assign employees");
            int userOption = scanner.nextInt();
            switch (userOption) {
                case 1:
                    updateProjectDetails(projectId);
                    break;
                case 2:
                    assignEmployees(projectId);
                    break;
                case 3:
                    unassignEmployees(projectId);
                    break;
                default:
                    System.out.println(getSpacing() + "  ---  Invalid option"
                    	    + "  ---" + getSpacing());
                    break;
            }
        } else {
            System.out.println(getSpacing() + "  ---  Project Id not present"
            	    + "  ---" + getSpacing());
        }
    }

    /**
     * Fetches the new project details for updation
     */
    private void updateProjectDetails(int projectId) {
        int userOption;
        boolean isInvalid = false;
        String projectName = null;
        String projectManager = null;
        String department = null;
        String displayMenu = "\n\n1. Update project name\n2. Update project"
                + " manager\n3. Update project department";
        do {
            System.out.println(displayMenu);
            userOption = scanner.nextInt();
            switch (userOption) {
                case 1:
                    System.out.println("Enter the project name");
                    scanner.skip("[\r\n]+");
                    projectName = scanner.nextLine();
                    break;
                case 2:
                    System.out.println("Enter the project manager name");
                    scanner.skip("[\r\n]+");
                    projectManager = scanner.nextLine();
                    break;
                case 3:
                    System.out.println("Enter the project department");
                    scanner.skip("[\r\n]+");
                    department = scanner.nextLine();
                    break;
                default:
                    System.out.println(getSpacing() + "  ---  Invalid option"
                    	    + "  ---" + getSpacing());
                    isInvalid = true;
                    break;
            } 
            if ((1 == userOption) || (2 == userOption) || (3 == userOption)) {
                System.out.println("Update further project details?\n1. yes\n"
                        + "2. No");
                userOption = scanner.nextInt();
            } 
        } while ((1 == userOption) || isInvalid);
        if (projectController.updateProject(projectId, projectName,
                projectManager, department)) {
            System.out.println(getSpacing() + "  ---  Successfully Updated "
                    + "project details  ---" + getSpacing());
        } else {
            System.out.println(getSpacing() + "  ---  Failed to update "
                    + "employee details  ---" + getSpacing());
        }
    }

    /**
     * Fetches various employee Ids to be assigned for the project
     * @param projectId            
     *     Id of the project to which the employees has to be assigned
     */
    private void assignEmployees(int projectId) {
        System.out.println("Enter the number of employees to be assigned");
        int numberOfEmployeeIds = scanner.nextInt();
        List<Integer> employeeIds = new ArrayList<Integer>();
        for (int i = 1; i <= numberOfEmployeeIds; i++) {
            System.out.println("Enter employee Id (" + i + ")");
            employeeIds.add(scanner.nextInt());
        }
        List<Integer> invalidIds 
                = projectController.checkEmployeesPresence(employeeIds);
        if (0 == invalidIds.size()) {
            List<List<Integer>> list = projectController.checkEmployeesInProject(projectId,
            	    employeeIds);
            List<Integer> idsPresent = list.get(0);
            List<Integer> idsNotPresent = list.get(1);
            if (null != idsNotPresent) {
                if (projectController.assignEmployees(projectId, idsNotPresent)) {
                    System.out.println(getSpacing() + "  ---  Successfully "
                    	    + "assigned employees " + idsNotPresent + " for the"
                    	    + " project  ---" + getSpacing());
                    if (0 != idsPresent.size()) {
                        System.out.println(getSpacing() + "  ---  Employees " 
                        	    + idsPresent + " are already assigned for the"
                        	    + " project  ---" + getSpacing());
                    }
                } else {
                    System.out.println(getSpacing() + "  ---  Failed to assign "
                    	    + "employees for the project  ---" + getSpacing());
                }
            } else {
                System.out.println(getSpacing() + "  ---  The employees are "
                	    + "already assigned for the project  ---" + getSpacing());
            }
        } else {
            System.out.println(getSpacing() + "  ---  Employee Ids " + invalidIds 
                    + " are not present. Please provide valid Ids  ---" + getSpacing());
        }
    }

    /**
     * Fetches various employee Ids to be usassigned from the project
     * @param projectId            
     *     Id of the project from which the employees has to be unassigned
     */
    private void unassignEmployees(int projectId) {
        System.out.println("Enter the number of employees to be unassigned");
        int numberOfEmployeeIds = scanner.nextInt();
        List<Integer> employeeIds = new ArrayList<Integer>();
        for (int i = 1; i <= numberOfEmployeeIds; i++) {
            System.out.println("Enter employee Id (" + i + ")");
            employeeIds.add(scanner.nextInt());
        }
        List<List<Integer>> list = projectController.checkEmployeesInProject(projectId,
        	    employeeIds);
        List<Integer> idsPresent = list.get(0);
        if (null != idsPresent) {
            if (projectController.unassignEmployees(projectId, idsPresent)) {
                System.out.println(getSpacing() + "  ---  Successfully "
                	    + "unassigned employees " + idsPresent 
                	    + " from the project  ---" + getSpacing());
            }
        } else {
            System.out.println(getSpacing() + "  ---  Employees are not present"
            	    + " in the project  ---" + getSpacing());
        }
        List<Integer> invalidIds = list.get(1);
        if (0 != invalidIds.size()) {
            System.out.println(getSpacing() + "  ---  Following employees are"
            	    + " not present in the project  ---\n" + invalidIds 
            	    + getSpacing());
        }
    }

    /**
     * Retrieves all employees' details and display them
     */
    private void showAllProjects() {
        List<String> projects = projectController.getAllProjects();

        for (String project : projects) {
            System.out.println(project);
        }   
    }

    private void decideOperation(int userOption) {
        switch(userOption) {
            case 1:
                addProject();
                break;
            case 2:
                retrieveProject();
                break;
            case 3:
                deleteProject();
                break;
            case 4:
                updateProject();
                break;
            case 5:
                showAllProjects();
                break;
            case 6:
                System.out.println(getSpacing() + "  ---  Thank you  ---"
                        + getSpacing());
                break;
            default:             
                System.out.println(getSpacing() + "  ---  Please provide"
                        + " a valid input  ---" + getSpacing());
                break;
        }
    }

    private String getSpacing() {
        return ("\n\n--------------------------------------------" 
                + "------------------------------------------\n\n");
    }
}




























