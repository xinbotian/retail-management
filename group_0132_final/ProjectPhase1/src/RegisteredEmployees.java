import java.util.ArrayList;

/**
 * This is a class managing all the current employees in the store. it is used by manger.
 */
class RegisteredEmployees {

  // array list holding all employees
  static ArrayList<Integer> listOfEmployees= new ArrayList<Integer>();


  /**
   * Checks that the ID is in fact in the array list.
   * @param ID the ID you are checking if it's in the system
   * @return boolean
   */
  static boolean loginCheck(Integer ID){
    for (int i=0;i<listOfEmployees.size();i++){
      Integer employee = listOfEmployees.get(i);
      if (employee.equals(ID.intValue())) {
        return true;
      }
    }
    return false;
  }

  /**
   * method to print to scrren all current employees
   */
   static void listAllEmployees(){
      for (int i=0;i<listOfEmployees.size();i++){
         Integer employee = listOfEmployees.get(i);
         System.out.println(employee);
      }
   }

  /**
   * Method do add a new empoloyee, thus adding him/her to the array list
   * @param newEmployee int ID
   */
  static void addEmployee(String newEmployee) {
     Integer newep = Integer.parseInt(newEmployee);
    if (!loginCheck(newep)) {

      listOfEmployees.add(newep);
    }
    else{
      System.out.println("this employee is already in the system!");
    }
   }

  /**
   * method to fire an employee, therefore removing him/her from the array list
   * @param firedEmployee int ID
   */
  static void removeEmployee(String firedEmployee) {
    Integer newep = Integer.parseInt(firedEmployee);
      if (loginCheck(newep)) {
        for (int i = 0; i < listOfEmployees.size(); i++) {
          Integer currentEmp = listOfEmployees.get(i);
          if (currentEmp.equals(newep.intValue())) {
            listOfEmployees.remove(i);
          }
        }
      }
      else{
        System.out.println("This employee is not in the system!");
      }
   }

  /**
   * Returns the array list of employees
   * @return Array list
   */
   static ArrayList<Integer> getEmployeeList(){
    return listOfEmployees;
   }

   }
