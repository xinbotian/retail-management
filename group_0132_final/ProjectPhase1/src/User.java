import java.awt.SystemTray;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This is a general employee user account, which specific employees will inherit from.
 * An employee is either a cashier, manager, reshelver, or a reciever.
 */
public class User {

  //employee ID
   private final int employeeId;


  /**
   * constructor, takes in ID as parameter.
   * @param id
   * @throws IOException
   */
  public User(int id) throws IOException {
    this.employeeId = id;

    LoggerHelper.makeALog(getID() + " logged in");
    System.out.println("You are now logged in as ID " + getID());


  }

  /**
   * Allows employees to add a memo to their memo file.
   * @param input text that you want to add
   * @param user Type of user
   * @throws IOException
   */
    void addMemo(String input, String user) throws  IOException{
    String x = String.valueOf(this.employeeId);
    FileReWriter.writeToMemo("User ID: "+x+" "+"Memo "+input,user);
    }

  /**
   * Allows employees to view memos from their memo fiel .
   * @param user type of user
   * @throws IOException
   */
    void viewMemo(String user) throws  IOException{
    PrintDisplay.printMemos(user);
    }


  /**
   * Allows employees to accees location information of a product. accesses the location
   * object held by the product.
   * @param upc
   */
   void getProductLocation(int upc) throws  IOException{
     if (Inventory.productInInventory(upc)) {
       Product product = Inventory.getProduct(upc);
       product.getLocation().locationDisplay();
     }
     else {
       System.out.println("Product not in inventory!");
     }
     LoggerHelper.makeALog(getID() + " retrieved product location for product"+ upc);

   }


  /**
   * Return this employee ID
   *
   * @return id: int
   */
  int getID() {
    return this.employeeId;

  }

  /**
   * Logout method for employee to end his/her shift
   */
  void logMeOut() throws IOException {
    LoggerHelper.makeALog(getID() + " logged out");
    System.out.println("You are logged out! Thanks for your hard work. We value Employees such as "
        + "you.");

  }


  /**
   * Get order history information according to order number.
   * @param orderNum int
   */
  void getOrderHistoryOrderNum(int orderNum) throws IOException {
    System.out.println("The order information of the order number: " + orderNum);

    ArrayList<Order> list = Inventory.getOrderHistory();

    int i = 0;
    if (Inventory.orderInOrderHistory(orderNum)) {
      System.out.println(list.get(i+1).toString());
      LoggerHelper.makeALog(getID() + " retrieved order information"
          + "for order no. "+orderNum);
    }
    else {
      System.out.println("Not a valid order number!");

    }

  }





  /**
   * Get order history according to upc.
   * @param upc int
   */
  void getOrderHistoryByUpc(int upc)throws IOException {
    if (Inventory.productInInventory(upc)) {
      int x = 0;
      System.out.println("The order history of the order number: " + upc);
      for (int i = 0; i < Inventory.getOrderHistory().size(); i++ ) {
        if (((Order) (Inventory.getOrderHistory().get(i))).getUpc() == upc) {
          System.out.println(((Inventory.getOrderHistory().get(i))).toString());
          x++;
          LoggerHelper.makeALog(getID() + " retrieved order information"
              + "for all orders of product "+upc);
        }
      if (x==0){
        System.out.println("This product has no order history!");
      }
      }
    }
    else{
      System.out.println("This product is not in store inventory!");
    }
  }

  /**
   * Price check the product with its UPC. returns -1 if the upc is invalid. Used by other classes.
   *
   * @param upc Product's upc int
   */
  void priceCheck(int upc) throws IOException {
    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      LoggerHelper.makeALog(getID() + " requested price check of Product"
          + " " + upc);
      if (product.price.isOnSale()) {
        System.out.println("Product " + upc + "(" + product.getProductName() + ") is on sale, " + " the price is "
            + product.price.getSalePrice());
      }
      else {
        System.out.println("Product " + upc + "(" + product.getProductName() + ")" + " price is "
            + product.price.getRegularPrice());
      }
    }
    else {
      System.out.println("Product not in inventory!");
    }
  }


  /**
   * Get price history of product with its upc
   */
  void getPriceHistory(int upc) throws IOException {
    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      System.out.println("Product"+upc+"("+product.getProductName()+")"+" "
          + " Price History:");
      product.price.getPriceHistory();
      LoggerHelper.makeALog(getID() + " requested price history of Product"
          + " " + upc);


    }
    else{
      System.out.println("This product is not in store inventory!");
    }
  }



  /**
   * Print out product information.
   * @param upc int
   */
  void getProductInformation(int upc)throws IOException{

    if (Inventory.productInInventory(upc)){

      Product product = Inventory.getProduct(upc);
      System.out.println(product.getProductInformation());
      LoggerHelper.makeALog(getID() + " retrieved Product "+upc+" Information"
      );

    }
    else{
      System.out.println("This product is not in store inventory!");
    }
  }


  /**
   * Get product per unit cost.
   *
   * @param upc int
   */
  void getCostPerUnit(int upc) {
    if (Inventory.productInInventory(upc)) {
      System.out.println((Inventory.getProduct(upc)).price.getCost());
    } else {
      System.out.println("Product with upc: " + upc + " is not in inventory");
    }
  }


  /**
   * generate order given a upc, print out the quantity needed, product name
   * and distributor of the product
   * the quantity is three times more than the threshold, can be changed by manager
   * the status of whether processed is flagged as false
   *
   * @param upc - the universal product code: should be an integer
   */
  void generateOrder(int upc) throws IOException {
    Product product = (Product)Inventory.getProduct(upc);
    int orderNum = Inventory.currentOrderNum;
    int quantity = product.getThreshold() * 3;
    boolean processed = false;
    String productName = product.getProductName();
    String distributorName = product.getDistributor();
    float cost = product.price.getCost();
    Order thisOrder = new Order(orderNum, upc, quantity, processed, productName,
        distributorName, cost);
    ArrayList<Order> list = Inventory.getOrderHistory();
    list.add(thisOrder);
    Inventory.currentOrderNum++;
    LoggerHelper.makeALog("Order generated: No."+ orderNum);

  }

  /**
   * Price check the product with its UPC. returns -1 if the upc is invalid. This is a helper
   * function that is called only when the UPC is definetly valid.
   * @param upc Product's upc int
   * @return price of product
   */
   float priceCheckHelper(int upc) {
    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      if (product.price.isOnSale()) {
        return product.price.getSalePrice();
      } else {
        return product.price.getRegularPrice();
      }
    }
    return -1; //product is not in the inventory: invalid upc
  }

}
