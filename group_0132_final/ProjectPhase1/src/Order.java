import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

class Order {
  /**
   * This is an Order object that will be created when some object is Stock need to be
   * reordered(below thresh_hold), to Create an order Object, 7 params need to be passed into
   * constructor in order.
   */

  private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
  private static LocalDate localDate = LocalDate.now();
  private static  String todayDate = dtf.format(localDate);

  private final int upc;// unique identifier for the product ordered
  private final int quantity;// quantity ordered
  private int orderNumber;// a reference number for the order
  private boolean processed;// order status
  private final String productName; // name of the product ordered
  private final String distributorName; // name of the distributor
  private final float cost;// cost of the ordered good

  private static Logger logger = Logger.getLogger("Order");

  public Order(int orderNumber, int upc, int quantity, boolean processed, String productName,
      String distributorName, float cost) throws IOException {
    this.productName = productName;
    this.upc = upc;
    this.orderNumber = orderNumber;
    this.quantity = quantity;
    this.processed = processed;
    this.distributorName = distributorName;
    this.cost = cost;

    LoggerHelper.makeALog("new order created: " + this.toString());
  }

  /**
   * Return whether this order is processed(false for a pending order
   * @return boolean
   */
  boolean isProcessed() {
    return processed;
  }

  /**
   * set the processed status of the order
   */
  void setProcessed() {
    this.processed = true;
  }

  /**
   * Return the order number
   * @return integer order number
   */
  int getOrderNumber() {return orderNumber;}

  /**
   * Return the quantity ordered
   * @return integer number of quantity
   */
  int getQuantity() {return this.quantity;}

  /**
   * Return the upc number
   * @return integer number of upc
   */
  int getUpc() {return this.upc;}

  /**
   * Return the distributor name
   * @return distributor name
   */
  String getDistributorName() {
    return this.distributorName;
  }

  /**
   * returns the product name
   * @return string
   */
  String getProductName() {return this.productName; }

  /**
   * Return the cost of the order
   * @return float number of cost
   */
  float getCost() {
    return this.cost;
  }

  /**
   * Return the order in string form
   * @return a string of order
   */
  public String toString() {
    return  "Order Number: " + orderNumber  + " " +"Product Name: "+ productName + " UPC: "
        + upc
        + " quantity: " + quantity + " cost: " + cost + " Distributor: "
        + distributorName;
  }
}
