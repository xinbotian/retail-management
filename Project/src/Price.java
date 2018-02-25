import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This price class holds all the information of a product relating to its price.
 */
public class Price {


  private float regularPrice; // current price of product
  private float salePrice; // sale price of this product
  private final float cost; // cost of this product
  private String salePeriod; //sale period of product ("2017/7/7 ~ 2017/7/7")
  private boolean onSale = false; // whether or not product is on sale
  private ArrayList<String> priceHistory = new ArrayList<String>();//Stores Price History of this Product


  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
  private LocalDate dateToday = LocalDate.now();
  private String dateTodayNow = dtf.format(dateToday);


  /**
   * The consturctor only requires the cost, and regular price of product. the other fields hold
   * information only when a product is put on sale
   * @param regularPrice float
   * @param cost float
   */
  Price(float regularPrice, float cost) {
    this.regularPrice = regularPrice;
    this.cost = cost;
  }


  /**
   * Helper method to update arraylist priceHistory every time there is a change in price of product
   *
   * @param price float
   */
  private void priceHistoryAltered(float price) {
    String priceString = String.valueOf(price);
    String addThis = dateTodayNow + " : " + priceString;
    this.priceHistory.add(addThis);
  }

  ArrayList<String> getArrayPriceHistory() {
    return priceHistory;
  }

  /**
   * Print out the full price history of this product.
   */
  void getPriceHistory() {
    for (int i = 0; i < priceHistory.size(); i++) {
      System.out.println((priceHistory).get(i));
    }
  }

  /**
   * Put item on sale at price salePrice
   *
   * @param salePrice float
   */
  void putOnSale(float salePrice, String salePeriod) {
    this.salePrice = salePrice;
    this.onSale = true;
    this.salePeriod = salePeriod;

    priceHistoryAltered(salePrice);
  }

  void putOnSaleReadIn(float salePrice, String salePeriod) {
    this.salePrice = salePrice;
    this.onSale = true;
    this.salePeriod = salePeriod;

  }

  /**
   * Take this product off sale
   */
  void takeOffSale() {
    this.onSale = false;
    priceHistoryAltered(regularPrice);
  }

  /**
   * Change the regular price of this product to price
   *
   * @param price float
   */
  void changeRegularPrice(float price) {
    this.regularPrice = price;
    priceHistoryAltered(price);
  }


  /**
   * Change the sale price of this product to price
   *
   * @param price float
   */
  void changeSalePrice(float price) {
    this.salePrice = price;
    priceHistoryAltered(price);
  }

  /**
   * Change the current sale period to newPeriod
   *
   * @param newPeriod String
   */
  void changeSalePeriod(String newPeriod) {
    this.salePeriod = newPeriod;
  }

  /**
   * Return if product is on sale
   *
   * @return boolean
   */
  boolean isOnSale() {
    return onSale;
  }

  /**
   * Return regular price of product
   *
   * @return float
   */
  float getRegularPrice() {
    return this.regularPrice;
  }

  /**
   * Return sale price of product
   *
   * @return float
   */
  float getSalePrice() {
    return this.salePrice;
  }

  /**
   * Return sale period of product
   *
   * @return String
   */
  String getSalePeriod() {
    return this.salePeriod;
  }

  float getCost() {
    return this.cost;
  }

  /**
   * Add an entry to the product's price history in the read in method
   * @param text the price history added
   */
  void addToPriceHistory(String text) {
    this.priceHistory.add(text);
  }

}