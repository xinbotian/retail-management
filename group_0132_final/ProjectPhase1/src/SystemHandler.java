import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemHandler {


  /**
   *Method checking if the input string is an interger or a string
   * @param str input string
   * @return boolean
   */
  static boolean isInteger(String str) {
    if (str == null) {
      return false;
    }
    int length = str.length();
    if (length == 0) {
      return false;
    }
    int i = 0;
    if (str.charAt(0) == '-') {
      if (length == 1) {
        return false;
      }
      i = 1;
    }
    for (; i < length; i++) {
      char c = str.charAt(i);
      if (c < '0' || c > '9') {
        return false;
      }
    }
    return true;
  }


  public static void main(String[] args) throws IOException {
    FileLoader.loadInFiles();


    Cashier currentCashier;
    Manager currentManager;
    Receiver currentReciever;
    Reshelver currentReshelver;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    System.out.println(" Welcome to the Store! Please enter your employee ID");
    String input = br.readLine();
    while (!input.equals("exit")) {

      while (isInteger(input)) {
        int intTxt = Integer.valueOf(input);

        if (RegisteredEmployees.loginCheck(Integer.valueOf(intTxt))) {
          char checkFirst = input.charAt(0);
          String checkFirstString = String.valueOf(checkFirst);

          if (checkFirstString.equalsIgnoreCase("1")) {
            currentCashier = new Cashier(intTxt);
            PrintDisplay.printUser("Cashier");
            String outPut = br.readLine();
            switch (outPut) {
              case "1": {
                System.out.println("Enter the upc of the product :");
                String enteredUpc = br.readLine();
                System.out.println("Enter the quantity of the product :");
                String enteredQuantity = br.readLine();
                currentCashier
                    .sellProduct(Integer.parseInt(enteredUpc), Integer.parseInt(enteredQuantity));
                break;
              }
              case "2": {
                System.out.println("Enter the upc of the product :");
                String enteredUpc = br.readLine();
                System.out.println("Enter the quantity of the product :");
                String enteredQuantity = br.readLine();
                currentCashier
                    .returnProduct(Integer.parseInt(enteredUpc), Integer.parseInt(enteredQuantity));

                break;
              }
              case "3": {
                System.out.println("Enter the upc of the product :");
                String enteredUpc = br.readLine();
                currentCashier.priceCheck(Integer.parseInt(enteredUpc));
                break;
              }
              case "4": {
                System.out.println("Enter the upc of the product :");
                String enteredUpc = br.readLine();
                currentCashier.saleCheck(Integer.parseInt(enteredUpc));
                break;
              }
              case "5": {
                System.out.println("Please enter your memo :");
                String memo = br.readLine();
                currentCashier.addMemo(memo, "Cashier");
                break;
              }
              case "6": {
                currentCashier.viewMemo("Cashier");
                break;
              }
              case "*":
                currentCashier.logMeOut();
                FileReWriter.writeToFiles();
                return;
            }

          } else if (checkFirstString.equalsIgnoreCase("2")) {

            currentReciever = new Receiver(intTxt);
            PrintDisplay.printUser("Receiver");
            String outPut = br.readLine();
            switch (outPut) {
              case "1":
                Receiver.loadNewOrders();
                System.out.println("Pending orders loading complete!");
                break;
              case "2":
                System.out.println("Enter the upc of the product :");
                String enteredInfo = br.readLine();
                currentReciever.getProductLocation(Integer.parseInt(enteredInfo));
                break;
              case "3": {
                System.out.println("Please Entered the upc :");
                String givenUpc = br.readLine();
                currentReciever.getCostPerUnit(Integer.parseInt(givenUpc));
                break;
              }
              case "4": {
                System.out.println("Please Entered the upc :");
                String givenUpc = br.readLine();
                currentReciever.priceCheck(Integer.parseInt(givenUpc));

                break;
              }
              case "5": {
                System.out.println("Please Entered the upc :");
                String givenUpc = br.readLine();
                currentReciever.getPriceHistory(Integer.parseInt(givenUpc));

                break;
              }

              case "6": {
                System.out.println("Please enter your memo : ");
                String givenInfo = br.readLine();
                currentReciever.addMemo(givenInfo, "Receiver");

                break;
              }
              case "7": {
                currentReciever.viewMemo("Receiver");

                break;
              }
              case "*": {
                currentReciever.logMeOut();
                FileReWriter.writeToFiles();

                return;
              }
              }
          } else if (checkFirstString.equalsIgnoreCase("3")) {
            currentReshelver = new Reshelver(intTxt);
            PrintDisplay.printUser("Reshelver");
            String outPut = br.readLine();
            switch (outPut) {
              case "1":         {
                System.out.println("Enter the upc of the product :");
                String enteredUpc = br.readLine();
                System.out.println("Enter the new aisle location of the product :");
                String enteredAisle = br.readLine();
                System.out.println("Enter the new subsection location of the product :");
                String enteredSubsection = br.readLine();
                currentReshelver.changeAisle(Integer.parseInt(enteredUpc), enteredAisle);
                currentReshelver.changeSubSection(Integer.parseInt(enteredUpc), enteredSubsection);
                break;
              }

              case "2": {
                System.out.println("Enter the upc of the product :");
                String enteredUpc = br.readLine();
                currentReshelver.getQuantity(Integer.parseInt(enteredUpc));

                break;
              }
              case "3": {
                System.out.println("Enter the upc of the product :");
                String enteredUpc = br.readLine();
                currentReshelver.getOrderHistoryByUpc(Integer.parseInt(enteredUpc));
                break;
              }

              case "4": {
                System.out.println("Enter a order number :");
                String enteredOrder = br.readLine();
                currentReshelver.getOrderHistoryOrderNum(Integer.parseInt(enteredOrder));
                break;
              }

              case "5": {
                System.out.println("Please enter your memo :");
                String memo = br.readLine();
                currentReshelver.addMemo(memo, "Reshelver");
                break;
              }

              case "6": {
                currentReshelver.viewMemo("Reshelver");
                break;
              }
              case "*": {
                currentReshelver.logMeOut();
                FileReWriter.writeToFiles();

                return;
              }

            }

          }else if (checkFirstString.equalsIgnoreCase("4")) {
            currentManager = new Manager(intTxt);
            PrintDisplay.printUser("Manager");
            String outPut = br.readLine();
            switch (outPut) {
              case "1": {
                currentManager.getPendingOrders();
                break;
              }

              case "2": {
                System.out.println("Enter a date(yyyy/MM/dd) :");
                String enteredDate = br.readLine();
                currentManager.viewProfit(enteredDate);
                currentManager.viewRevenue(enteredDate);
                currentManager.viewCost(enteredDate);
                break;
              }

              case "3": {
                System.out.println("Enter a Upc :");
                String enteredUpc = br.readLine();
                currentManager.getPriceHistory(Integer.parseInt(enteredUpc));
                break;
              }

              case "4": {
                currentManager.viewAllProduct();
                break;
              }

              case "5": {
                currentManager.viewOnSaleProduct();
                break;
              }

              case "6": {
                System.out.println("Enter a Upc :");
                String enteredUpc = br.readLine();
                System.out.println("Enter a sale price :");
                String enteredPrice = br.readLine();
                System.out.println("Enter a sale Period(yyyy/MM/dd - yyyy/MM/dd) :");
                String enteredPeriod = br.readLine();
                currentManager.putOnSale(Integer.parseInt(enteredUpc), Float.parseFloat(enteredPrice), enteredPeriod);
                break;
              }

              case "7": {
                System.out.println("Enter a Upc :");
                String enteredUpc = br.readLine();
                currentManager.takeOffSale(Integer.parseInt(enteredUpc));
                break;
              }

              case "8": {
                System.out.println("Enter a Upc :");
                String enteredUpc = br.readLine();
                System.out.println("Enter a sale Period(yyyy/MM/dd - yyyy/MM/dd) :");
                String enteredPeriod = br.readLine();
                currentManager.changeSalePeriod(Integer.parseInt(enteredUpc), enteredPeriod);
                break;
              }

              case "9": {
                System.out.println("Enter a upc :");
                String enteredUpc = br.readLine();
                System.out.println("Enter a sale price :");
                String enteredPrice = br.readLine();
                currentManager.changeSalePrice(Integer.parseInt(enteredUpc), Float.parseFloat(enteredPrice));
                break;
              }

              case "10": {
                System.out.println("Enter a upc :");
                String enteredUpc = br.readLine();
                System.out.println("Enter a new threshold :");
                String enteredThreshold = br.readLine();
                currentManager.changeThreshold(Integer.parseInt(enteredUpc), Integer.parseInt(enteredThreshold));
                break;
              }

              case "11": {
                System.out.println("Enter a upc :");
                String enteredUpc = br.readLine();
                System.out.println("Enter a new price :");
                String enteredPrice = br.readLine();
                currentManager.changeRegularPrice(Integer.parseInt(enteredUpc), Float.parseFloat(enteredPrice));
                break;
              }

              case "12": {
                System.out.println("Enter a upc :");
                String enteredUpc = br.readLine();
                currentManager.getProductInformation(Integer.parseInt(enteredUpc));
                break;
              }

              case "13": {
                System.out.println("Enter a order number :");
                String enteredOrder = br.readLine();
                currentManager.getOrderHistoryOrderNum(Integer.parseInt(enteredOrder));
                break;
              }

              case "14": {
                System.out.println("Enter a upc :");
                String enteredUpc = br.readLine();
                currentManager.getOrderHistoryByUpc(Integer.parseInt(enteredUpc));
                break;
              }

              case "15": {
                currentManager.viewAllEmployees();
                break;
              }
              case "16": {
                System.out.println("Please enter your memo :");
                String memo = br.readLine();
                currentManager.addMemo(memo, "Manager");
                break;
              }
              case "17": {
                System.out.println("Please enter one of Manager, Receiver, Reshelver, Cashier :");
                String givenUser = br.readLine();
                currentManager.viewMemo(givenUser);
                break;

              }

              case "18": {
                System.out.println("Enter the upc :");
                String enteredUpc = br.readLine();
                System.out.println("Enter the product name :");
                String enteredName = br.readLine();
                System.out.println("Enter the aisle :");
                String enteredAisle = br.readLine();
                System.out.println("Enter the subsection :");
                String enteredSubsection = br.readLine();
                System.out.println("Enter the section :");
                String enteredSection = br.readLine();
                System.out.println("Enter the quantity :");
                String enteredQuantity = br.readLine();
                System.out.println("Enter the price :");
                String enteredPrice = br.readLine();
                System.out.println("Enter the cost :");
                String enteredCost = br.readLine();
                System.out.println("Enter the threshold :");
                String enteredThreshold = br.readLine();
                System.out.println("Enter the distributor :");
                String enteredDistributor = br.readLine();
                currentManager.makeANewOrder(enteredName, enteredAisle, enteredSubsection,
                    enteredSection, Integer.parseInt(enteredUpc), Integer.parseInt(enteredQuantity),
                    Float.parseFloat(enteredPrice), Float.parseFloat(enteredCost),
                    Integer.parseInt(enteredThreshold), enteredDistributor);
                break;
              }

              case "19": {
                System.out.println("Enter the employee ID you want to add :");
                String enteredId = br.readLine();
                currentManager.addNewEmployee(enteredId);
                System.out.println("You added an new employee :" + enteredId);
                break;
              }

              case "20": {
                System.out.println("Enter the employee you want to fire :");
                String enteredId = br.readLine();
                currentManager.fireEmployee(enteredId);
                System.out.println("You fired an employee :" + enteredId);
                break;
              }

              case "21": {
                System.out.println("Enter Product UPC :");
                String enteredinput = br.readLine();
                Inventory.removeProductFromStock( Integer.parseInt(enteredinput));
                break;
              }

              case "*": {
                currentManager.logMeOut();
                FileReWriter.writeToFiles();
                return;

              }




            }
          }

        }else {
          System.out.println("Invalid Login!");
          System.out.println("Please enter your employee ID: ");
          input = br.readLine();


        }


      }


      System.out.println("You ID should be a number!");
      System.out.println("Please enter your employee ID: ");
      input = br.readLine();

    }




  }
}