import java.io.IOException;

/**
 * This is a grocery store product. Holds two objects as fields: location, and price.
 * A product can be put on sale, and taken off sale. When it is, the regular/sale prices update.
 * A product holds the the following information: sale period, sale price, regular price,
 * distributor, section, subsection, quantity, and threshold. These fields have getters and setters.
 */
class Product {
  private final String productName;// name of the product
  private String distributor; // distributor of product
  private final int upc; // unique identifier for the product ordered
  private int quantity;// current quantity in stock
  private int threshold; // once lower than this quantity, an order will automatically be generated
  private boolean shortage = IsAShortage(); // whether or not there is a shortage for the product (quantity below
  // threshold

  private Location location; //Location object holding location related info
  Price price; //Price object holding price related info

  Product(String productName, Location location, int upc, int quantity, Price price, int threshold, String Distributor) throws IOException {
    this.productName = productName;
    this.upc = upc;
    this.quantity = quantity;
    this.threshold = threshold;
    this.distributor = Distributor;
    this.location = location;
    this.price = price;

    LoggerHelper
        .makeALog("Product " + this.getProductName() + " created, quantity: " + this.getQuantity());}

  /**
   * Set new threshold of amount
   *
   * @param amount int
   */
  void changeThreshold(int amount) {
    this.threshold = amount;
  }

  /**
   * Return quantity of product
   *
   * @return int
   */
  int getQuantity() {
    return this.quantity;
  }

  /**
   * Return threshold of product
   *
   * @return int threshold
   */
  int getThreshold() {
    return this.threshold;
  }

  /**
   * Return UPC of product
   *
   * @return int
   */
  int getUpc() {
    return this.upc;
  }

  /**
   * Return cost of product
   *
   * @return float cost
   */

  String getProductName() {
    return this.productName;
  }


  /**
   * Check if there is a Shortgae ot this product. it will return if the quantity is three
   * times the threshold or not.
   * @return boolean
   */
  boolean IsAShortage() {
    return quantity > threshold * 3;
  }

  String getDistributor() {
    return this.distributor;
  }

  /**
   * Increase the quantity of the product by amount
   *
   * @param amount int
   */
  void increaseQuantity(int amount) {
    this.quantity += amount;
  }

  /**
   * Decrease the quantity by amount
   *
   * @param amount int
   */
  void decreaseQuantity(int amount) {
    this.quantity -= amount;
  }


  String getProductInformation() throws IOException {

    LoggerHelper.makeALog("Product " + this.getProductName() + "information requested by manager");

    return "UPC: " + getUpc() + " Product Name: " + getProductName() + " Price: " +
        this.price.getRegularPrice()+ "Location :"+location.getLocationInfo()+ " Distributor: "
        + getDistributor() +
        " Quantity in stock: " + getQuantity() + " Cost: " + price.getCost() + " Threshold: "
        + getThreshold() +
        " OnSale:" + price.isOnSale() + " Quantity Below Threshold: " + shortage;

  }

  public Location getLocation() {
    return location;
  }


}
