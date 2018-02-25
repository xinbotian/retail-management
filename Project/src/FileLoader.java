import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class loads in the store's configuration from several text files.
 */
class FileLoader {


  /**
   * This methods calls all methods in this class. By calling it, the store will be configured.
   * @throws IOException
   */
  static void loadInFiles() throws IOException {

    loadProducts();
    readInEmployees();
    readInPriceHistory();
    readInMoney();
    readInSaleProducts();
    readInOrders();
  }

  /**
   * Load a products into Inventory. calls receiver method to receive the product to do so.
   *
   */
  private static void loadProducts() throws IOException {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader("InventoryStock.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (br != null) {
      for (String line; (line = br.readLine()) != null; ) {
        receiveProductHelper(line);
      }
    }
  }

  /**
   * "Receive" new Product from a text file. baiscally this is the file reader method
   * @param aProduct String
   */
  static void receiveProductHelper(String aProduct) throws IOException {
    String[] allInformation = aProduct.split(",");
    String productName = allInformation[0].trim();
    String aisle = allInformation[1].trim();
    String subSection = allInformation[2].trim();
    String section = allInformation[3].trim();
    int upc = Integer.parseInt(allInformation[4].trim());
    int quantity = Integer.parseInt(allInformation[5].trim());
    float cost = Float.parseFloat(allInformation[6].trim());
    float price = Float.parseFloat(allInformation[7].trim());
    int threshHold = Integer.parseInt(allInformation[8].trim());
    String distributor = allInformation[9].trim();
    Location newLocation = new Location(aisle, subSection,section);
    Price newPrice = new Price(price, cost);
    Product aNewProduct = new Product(productName, newLocation, upc, quantity,
        newPrice, threshHold, distributor);
    Inventory.Stock.add(aNewProduct);
    LoggerHelper.makeALog("Receiver  received product "+upc);
  }



  /**
   * Reads in employee information into class registered employees
   */
  private static void readInEmployees() throws IOException {
    BufferedReader br = null;
    {
      try {
        br = new BufferedReader(new FileReader("Employees.txt"));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      if (br != null) {
        for (String line; (line = br.readLine()) != null; ) {
          String[] input = line.split(" ");
          String employee = input[0].trim();
          Integer employeenum = Integer.parseInt(employee);
          RegisteredEmployees.listOfEmployees.add(employeenum);
        }
      }
    }
  }

  /**
   * Reads in price history information to class price
   * @throws IOException
   */
  private static void readInPriceHistory() throws IOException {
    BufferedReader br = null;
    {
      try {
        br = new BufferedReader(new FileReader("PriceHistory.txt"));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      if (br != null) {
        for (String line; (line = br.readLine()) != null; ) {
          String[] input = line.split(" ");
          int upc = Integer.valueOf(input[0].trim()); Product p = Inventory.getProduct(upc);
          String add = input[1].trim() + " " + input[2].trim() + " " + input[3].trim();
           p.price.addToPriceHistory(add);


        }
      }
    }
  }

  /**
   * Reads in all revenue/cost/profit related information
   * @throws IOException
   */
  private static void readInMoney() throws IOException {
    BufferedReader br = null;
    {
      try {
        br = new BufferedReader(new FileReader("RevenueLog.txt"));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      if (br != null) {
        for (String line; (line = br.readLine()) != null; ) {
          String[] input = line.split(" ");
          String date = input[0].trim();

          Float rev = Float.valueOf(input[2].trim());
          Float cost = Float.valueOf(input[3].trim());
          Float profit1 = Float.valueOf(input[4].trim());

          DailyOperations.revenue.put(date, rev);
          DailyOperations.costs.put(date, cost);
          DailyOperations.profit.put(date, profit1);
        }

      }
    }
  }

  private static void readInSaleProducts() throws IOException {
    BufferedReader br = null;
    {
      try {
        br = new BufferedReader(new FileReader("SaleItems.txt"));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      if (br != null) {
        for (String line; (line = br.readLine()) != null; ) {
          String[] input = line.split(" ");
          int UPC = Integer.valueOf(input[0].trim());
          float salePrice = Float.valueOf(input[1].trim());
          String period = input[2].trim();
          Product p = Inventory.getProduct(UPC);
          p.price.putOnSaleReadIn(salePrice, period);
        }
      }
    }
  }


  /**
   * Reads in all orders into inventory.
   * @throws IOException
   */
  private static void readInOrders() throws IOException {
    BufferedReader br = null;
    {
      try {
        br = new BufferedReader(new FileReader("Orders.txt"));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      if (br != null) {
        for (String line; (line = br.readLine()) != null; ) {
          String[] input = line.split(",");
          boolean processed= false;
          if (input[6].trim().equals("true")) {
            processed = true;
          }
          int orderNum = Integer.valueOf(input[0].trim());
          int UPC = Integer.valueOf(input[1].trim());
          int quantity = Integer.valueOf(input[2].trim());
          String productName = (input[3].trim());
          String distName = (input[4].trim());
          float cost = Float.valueOf(input[5].trim());

          Order addThis = new Order(orderNum, UPC, quantity, processed, productName,
              distName, cost);
          Inventory.orderHistory.add(addThis);
          Inventory.currentOrderNum++;
        }
      }
    }
  }


}


