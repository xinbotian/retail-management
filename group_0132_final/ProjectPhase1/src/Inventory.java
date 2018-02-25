import java.util.ArrayList;

/**
 * This is the inventory class. The inventory holds two arraylists one storing all the products
 * of the store (Stock), and the other, the order histories of the Orders (orderHistory).
 */
class Inventory {

  //list of stocks, storing the product objects
  static ArrayList<Product> Stock = new ArrayList<Product>();
  //list storing order objects
  static ArrayList<Order> orderHistory = new ArrayList<Order>();
  static int currentOrderNum = 1;

  /**
   * greturns the aray list stock
   * @return the stock
   */
  static ArrayList getStock() {
    return Stock;
  }

  /**
   * returnst the array list
   * @return the order history:
   */
  static ArrayList getOrderHistory() {
    return orderHistory;
  }

  /**
   * return if the product is in the inventory
   * return true if the product is in, false if not
   * @param upc - Universal Product Code of the product
   * @return      whether product is in inventory: should be true or false
   */
  static boolean productInInventory(int upc) {
    for (int i = 0; i < Inventory.Stock.size(); i++) {
      Product product = (Product) Inventory.Stock.get(i);
      if (product.getUpc() == upc) {  //check that this product is in inventory
        return true;
      }
    }
    return false;
  }

  /**
   * return the place the product is in the list of stock
   * if no such product in stock, return -1
   * @param upc - Universal Product Code of the product
   * @return      The index of the stock list: should be integer
   */
  private static int productLocationStock(int upc) {
    for (int i = 0; i < Inventory.Stock.size(); i++) {
      Product product = (Product) Inventory.Stock.get(i);
      if (product.getUpc() == upc) {  //check that this product is in inventory
        return i;
      }
    }
    return -1;
  }

  /**
   * return the product given the upc
   * @param upc - Universal Product Code of the product
   * @return      The product
   */
  static Product getProduct(int upc) {
    int location = productLocationStock(upc);
    Product returnProduct = (Product) Stock.get(location);
    return returnProduct;
  }

  /**
   * check if there exists an order with certain Order Number
   * if order is in return true, else return false
   * @param orderNum - the order number: should be an integer
   * @return           whether the order is in order history
   */
  static boolean orderInOrderHistory(int orderNum) {
    for (int i = 0; i < Inventory.orderHistory.size(); i++) {
      Order order = (Order) Inventory.orderHistory.get(i);
      if (order.getOrderNumber() == orderNum) {
        return true;
      }
    }
    return false;
  }

  /**
   * remove a product from inventory
   * if no such product, print no such product
   *
   * @param upc - universal product code: should be an integer
   */
  static void removeProductFromStock(int upc) {
    if (productInInventory(upc)) {
      Product product = (Product) getProduct(upc);
      Stock.remove(product);
      System.out.println("Product "+upc+ " removed!");
    } else {
      System.out.println("No such product");
    }
  }

}