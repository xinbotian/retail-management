import java.io.IOException;
import java.time.LocalDate;
import java.time.format.*;

/**
 * This is a manager account. A manager is identified by his/her employeeId. Every time the manager
 * login, the manager can lookup all pending orders, total inventory, total cost, and check today's
 * daily revenue, daily cost and daily profit.
 * The manager can also manage whether the product should be on sale or not, and the sale period.
 * The manager can change the price of a product, and get all information of a selected product.
 */
class Manager extends User {

  //Today's date in a string
  private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
  private static LocalDate localDate = LocalDate.now();
  private static String todayDate = dtf.format(localDate);

  public Manager(int id) throws IOException {
    super(id);
  }

  /**
   * Method to print out all empployee IDs
   */
  void viewAllEmployees() {
    System.out.println("List of all current employees:");
    RegisteredEmployees.listAllEmployees();
  }

  /**
   * Method to add a new empoyee
   * @param ID: string the ID of the new employee you want to add
   */
  void addNewEmployee(String ID) {
    System.out.println("New employee "+ID+ " added!");
    RegisteredEmployees.addEmployee(ID);
  }

  /**
   * Method to fire an employee and remove them from the employee list
   * @param fired string the ID of employee you want to fire
   */
  void fireEmployee(String fired) {
    System.out.println("Employee "+fired+ " removed!");
    RegisteredEmployees.removeEmployee(fired);
  }



  /**
   * View the proift of a specific date
   * @param date string in format year/month/day
   */
  void viewProfit(String date) throws IOException {
    DailyOperations.profit.get(date);
    System.out.println("Date" + date + ":" + " Profit is: " + DailyOperations.profit.get(date));
    LoggerHelper.makeALog("Manager " + getID() + " requested daily profit");

  }

  /**
   * View the revenue of a specific date
   * @param date string in format year/month/day
   */
  void viewRevenue(String date) throws IOException {
    DailyOperations.revenue.get(date);
    System.out.println("Date" + date + ":" + " Revenue is: " + DailyOperations.revenue.get(date));
    LoggerHelper.makeALog("Manager " + getID() + " requested daily revenue");
  }


  /**
   * View costs of a specific date
   * @param date string in format year/month/day
   */
  void viewCost(String date) throws IOException {
    DailyOperations.costs.get(date);
    System.out.println("Date" + date + ":" + " Cost is: " + DailyOperations.costs.get(date));
    LoggerHelper.makeALog("Manager " + getID() + " requested daily cost");

  }


  /**
   * Allows manager to create a new order. Sends the new order to the order list for reciever to process
   */
  void makeANewOrder(String productName, String aisle, String subsection, String  section , int upc,
      int quantity, float price, float cost, int threshold, String Distributor) throws IOException {

    Location newLocation = new Location(aisle, subsection, section);
    Price newPrice = new Price(price, cost);
    Product newProduct = new Product(productName, newLocation, upc, quantity, newPrice, threshold,
        Distributor);

    //add this new product to inventory
    Inventory.Stock.add(newProduct);
    this.generateOrder(upc);

    //
    LoggerHelper.makeALog("Manger"+ getID() + "created a new order for new product " + upc);
    System.out.println("Order for new product created");
  }



  /**
   * Prints out all pending orders (unprocessed orders). goes through orderhistory array list to check
   */
  void getPendingOrders() {

    int x = 0;
    int i = 0;
    if (i == Inventory.orderHistory.size()) {
      System.out.println("No pending orders");
    }

    else {
      System.out.println("current pending orders: ");
      for (i = 0; i < Inventory.orderHistory.size(); i++) {
        Order order = (Order) Inventory.orderHistory.get(i);
        if (!order.isProcessed()) {
          System.out.println(order.toString());
          x++;
        }
      }
      if (x==0){
        System.out.println("No pending orders");
      }
    }
  }


  /**
   * Put item on sale at price salePrice
   *
   * @param salePrice float
   */
  void putOnSale(int upc, float salePrice, String salePeriod) throws IOException {
    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      product.price.putOnSale(salePrice, salePeriod);
      System.out.println("Product" + upc + "(" + product.getProductName() + ")" + " "
          + " put on sale during " + salePeriod);
      LoggerHelper.makeALog("Manager " + getID() + " put Product " + upc + " on sale");

    } else {
      System.out.println("This product is not in store inventory!");
    }
  }

  /**
   * Take this product off sale
   */
  void takeOffSale(int upc) throws IOException {

    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      product.price.takeOffSale();
      System.out.println("Product" + upc + "(" + product.getProductName() + ")" + " "
          + " taken off sale");
      LoggerHelper.makeALog("Manager " + getID() + " took Product " + upc + " off sale");

    } else {
      System.out.println("This product is not in store inventory!");
    }
  }

  /**
   * Change the regular price of this product to price
   *
   * @param price float
   */
  void changeRegularPrice(int upc, float price) throws IOException {

    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      product.price.changeRegularPrice(price);
      System.out.println(
          "Product" + upc + "(" + product.getProductName() + ")" + " " + "Price Changed to: "
              + price);
      LoggerHelper.makeALog("Manager " + getID() + " changed Product " + upc + " Regular "
          + "price");

    } else {
      System.out.println("This product is not in store inventory!");
    }
  }


  /**
   * Change the sale price of this product to price
   *
   * @param price float
   */
  void changeSalePrice(int upc, float price) throws IOException {
    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      product.price.changeSalePrice(price);
      System.out.println(
          "Product" + upc + "(" + product.getProductName() + ")" + " " + "OnSalePrice Changed to"
              + price);

      LoggerHelper.makeALog("Manager " + getID() + " changed Product " + upc + " Sale "
          + "price");
    } else {
      System.out.println("This product is not in store inventory!");
    }
  }

  /**
   * Set new threshold of amount.
   *
   * @param upc int
   * @param amount int
   */
  void changeThreshold(int upc, int amount) throws IOException {

    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      product.changeThreshold(amount);
      System.out.println("Product " + upc + "(" + product.getProductName() + ")" + " "
          + "Threshold changed to " + amount);
      LoggerHelper.makeALog("Manager " + getID() + " changed Product " + upc + " Threshold"
      );
    } else {
      System.out.println("This product is not in store inventory!");
    }
  }


  /**
   * Change the current sale period to newPeriod.
   *
   * @param newPeriod String
   */
  void changeSalePeriod(int upc, String newPeriod) throws IOException {

    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      product.price.changeSalePeriod(newPeriod);
      System.out.println("Product" + upc + "(" + product.getProductName() + ")" + " "
          + " sale period changed to " + newPeriod);
      LoggerHelper.makeALog("Manager " + getID() + " changed Product " + upc + " Sale "
          + "Period");
    } else {
      System.out.println("This product is not in store inventory!");
    }
  }

  void viewOnSaleProduct() throws IOException {

    int i = 0;
    if (i == Inventory.Stock.size()) {
      System.out.println("No inventory in stock!");
    }
    else {
      for (i=0; i < Inventory.Stock.size();i++){
        Product product = (Product) Inventory.Stock.get(i);
        if (product.price.isOnSale()){
          System.out.println("Product :"+product.getProductName() + " Sale Period: "
              +product.price.getSalePeriod()+ " Sale Price: "+ product.price.getSalePrice());
        }
      }
    }
  }

  void viewAllProduct() throws IOException{
    int i = 0;
    if (i == Inventory.Stock.size()) {
      System.out.println("No stock in inventory!");
    }
    else{

      System.out.println("List of current products in stock:");

      for (i=0; i < Inventory.Stock.size();i++){
        Product product = (Product) Inventory.Stock.get(i);
        System.out.println(product.getProductName()+" "+product.getUpc());
      }
    }
  }
}
