import java.io.IOException;
import java.time.LocalDate;
import java.time.format.*;

/**
 * This is a Cashier user. A cashier is identified by his/her employee ID.
 * When a cashier logs in, a new dictionary entry in daily operations is created if he/she
 * is the first cashier to log in. This keeps track of revenue, profit, and cost with every
 * product sold and returned.
 * A cashier and sell, return, and perform price/sale checks on products.
 * When a product is sold by a cashier, there is a check performed to see if there the  product
 * quantity is still above threshold. If it's not, it calls a method in inventory to generate
 * an order for this product.
 */
class Cashier extends User{

  //Date today
  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
  private LocalDate dateToday = LocalDate.now();
  private String dateTodayNow = dtf.format(dateToday);

  /**
   * Constructor for Creating a New Cashier. When called, it checks if it's the first cashier of the
   * day, and if it is, it creates a new entry for cost, profit, and recvenue in daily operations.
   * @param id int
   */
  public Cashier(int id) throws IOException {
    super(id);
    addDayToOperations();
  }

  /**
   * Method to add today to the dictionary of daily costs/revenues IF he/she is the first cashier
   * of the day.o
   */
  private void addDayToOperations() {
    if (!DailyOperations.costs.containsKey(dateTodayNow)) {
      Float cost = 0.0f;
      Float revenue = 0.0f;
      Float profit = 0.0f;
      DailyOperations.costs.put(dateTodayNow, cost);
      DailyOperations.revenue.put(dateTodayNow, revenue);
      DailyOperations.profit.put(dateTodayNow, profit);
    }
  }


  /**
   * Sells a product. Products are identified by UPC. Once a product is sold,
   * the quantity in inventory stock decreases, and total revenue + cost is updated.
   * If after the sale, threshold > quantity, a new order is made of the product.
   *
   * @param upc Product's UPC int
   * @param amount int
   */
  void sellProduct(int upc, int amount) throws IOException {

    //First step is to check that the product UPC is in our Inventory.
    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);

      //There is enough stock to sell!
      if (product.getQuantity() >= amount) {
        product.decreaseQuantity(amount);

        //adjust revenue and cost of today
        DailyOperations
            .addToDailyCost(
                priceCheckHelper(upc));//once an item is sold, daily cost increases
        DailyOperations
            .addToDailyRevenue(
                product.price.getCost());//once an item is old, daily revenue increases

        LoggerHelper.makeALog("Cashier " + getID() + " sold:" + product.getUpc() +
            "(" + product.getProductName() + ")"
            + " Quantity sold: " + amount);
        System.out.println("Product "
            + product.getUpc() + "(" + product.getProductName() + ")"
            + " Quantity sold: " + amount);

        //Check that after sale, there's still enough stock. if not, make a new order.
        if (product.IsAShortage()) {
          generateOrder(upc);

        }
      }
      else {

          //There isn't enough stock.

          if (product.getQuantity() == 0) {
            generateOrder(upc); //an order is generated for this below-threshold product.
            System.out.println("Product " + product.getProductName() + " stock low! Current stock"
                + "is " + product.getQuantity());
          }

          // amount is greater than quantity in stock. amountAbleToSell is the all the quantity
          // that is in stock
          else {

            int amountAbleToSell = product.getQuantity();


            product.decreaseQuantity(amountAbleToSell);

            DailyOperations
                .addToDailyCost(priceCheckHelper(upc)
                    * amountAbleToSell);//once an item is sold, daily cost increases
            DailyOperations
                .addToDailyRevenue(product.price.getCost()
                    * amountAbleToSell);//once an item is old, daily revenue increases

            generateOrder(upc); //an order is generated for this below-threshold product.

            System.out.println(product.getUpc() + "(" + product.getProductName() + ")" + " sold."
                + " Quantity sold: " + amountAbleToSell + ". Was only able to sell "
                + amountAbleToSell +
                "units as product is now in low stock");
            LoggerHelper.makeALog("Cashier " + getID() + " sold: " + product.getUpc() +
                "(" + product.getProductName() + ")" + " Quantity sold: " + amountAbleToSell);

          }
        }
      }

      //this UPC does not match to any product in inventory
      else {
        System.out.println("Invalid UPC -this product is not in our store's inventory.");
      }
    }


  /**
   * Returns this product to the store's inventory. adjust the revenue and costs accordingly
   *
   * @param upc Product's upc int
   * @param amount int
   */
  void returnProduct(int upc, int amount) throws IOException {

    //Check that product with UPC is in Inventory
    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      product.increaseQuantity(amount); //put the product back in stock

      DailyOperations.subtractFromDailyCost(product.price.getCost());//adjust revenue to decrease
      DailyOperations.subtractFromDailyRevenue(priceCheckHelper(upc));// adjust cost to decrease

      System.out.println(
          "Product " + upc + "(" + product.getProductName() + ")" + " returned by quantity: "
              + amount);
      LoggerHelper.makeALog("Cashier " + getID()+ " returned: "+
          product.getProductName() + " returned by quantity: " + amount);


    }

    //this UPC does not match to any product in inventory
    else {
      System.out.println("Invalid UPC -this product is not in our store's inventory.");
    }
  }



  /**
   * Perform a sale check on the product- will display whether product is on sale and sale period.
   *
   * @param upc int
   */
  void saleCheck(int upc) throws IOException {
    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      LoggerHelper.makeALog("Sale check performed on Product " +
          product.getProductName() + " "
          + " Cashier ID: " + getID());
      if (product.price.isOnSale()) {
        System.out.println("Yes!" + product.getProductName() + "is on sale from " +
            product.price.getSalePeriod());


      } else {
        System.out.println("Product " + upc + "(" + product.getProductName() + ")" + " "
            + " is not currently on sale");

      }
    } else {
      System.out.println("Product is not in inventory");
    }


  }

  /**
   * Logout method for employee to end his/her shift. Calculate profit and revenue
   * before logging out.
   */
  @Override
  void logMeOut() throws IOException {

    float c= DailyOperations.costs.get(dateTodayNow);
    float r =DailyOperations.revenue.get(dateTodayNow);
    DailyOperations.profit.put(dateTodayNow,c-r);


    LoggerHelper.makeALog(getID() + " logged out");
    System.out.println("You are logged out! Thanks for your hard work. We value Employees such as "
        + "you.");

  }




}