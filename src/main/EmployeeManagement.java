//                                                                             ;

import java.util.Scanner;

import com.ideas2it.employeeManagement.view.EmployeeView;
import com.ideas2it.projectManagement.view.ProjectView;

/**
 * Class to Perform Employee Management
 *
 * @version 1.1 19 March 2021
 * @author Sembiyan
 */
class EmployeeManagement {

    public static void main(String[] args) {
        int userOption;
		boolean isContinue = true;
        EmployeeView employeeView = new EmployeeView();
        ProjectView projectView = new ProjectView();
        Scanner scanner = new Scanner(System.in);
		String menuOptions = "1. Employee management\n2. Project management"
                + "\n3. Exit";
        do {
            System.out.println(menuOptions);
            userOption = scanner.nextInt();
            if (1 == userOption) {
                employeeView.homePage();
            } else if (2 == userOption) {
                projectView.homePage();
            } else if (3 == userOption) {
                isContinue = false;
            } else {
                System.out.println("Invalid option");
            }
        } while (isContinue);
    }
}