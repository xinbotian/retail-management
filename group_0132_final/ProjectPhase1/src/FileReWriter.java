import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class FileReWriter {


  //date and time writer
  private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
  private static LocalDate localDate = LocalDate.now();
  private static String todayDate = dtf.format(localDate);

  //path to products in inventory
  private static String path = "InventoryStock.txt";
  //path to employee IDs
  private static String path2 = "Employees.txt";
  //path to price histories of products
  private static String path3 = "PriceHistory.txt";
  //path to items on sale and sale information
  private static String path4 = "SaleItems.txt";
  //path to all orders in order history
  private static String path5 = "Orders.txt";
  //path to revenue/profit/cost file
  private static String path6 = "RevenueLog.txt";

  private static boolean append = false;
  private static String textLine;

  //calls all methods in this class to write current configuraiton of the store to files
  static void writeToFiles() throws IOException {

    writeToFilePriceHistory();
    writeToFileEmployees();
    writeToFileInventory();
    writeToSaleItems();
    writeToOrder();
    writeToFileInventory();
    writeToRevenueLog();
  }

  //retrieves ordres in orderhistory array list and writes them to file
   private static void writeToOrder() throws IOException {
    FileWriter write = new FileWriter(path5, append);
    PrintWriter print_line = new PrintWriter(write);
    ArrayList<Order> orders = Inventory.getOrderHistory();
    for (int i = 0; i < orders.size(); i++) {
      Order o = (Order) orders.get(i);

      String orderNum = String.valueOf(o.getOrderNumber());
      String UPC = String.valueOf(o.getUpc());
      String productName = o.getProductName();
      String distName = o.getDistributorName();
      String cost = String.valueOf(o.getCost());
      String quantity = String.valueOf(o.getQuantity());
      String isProcesed = String.valueOf(o.isProcessed());

      textLine =
          orderNum + ", " + UPC + ", " + quantity + "," + productName + ", " + distName + ", " + cost
              + ", " + isProcesed;
      print_line.printf("%s" + "%n", textLine);

    }
    print_line.close();

  }


  /**
   * Writes to memo files according to the user's position in store
   * @param input memo you want to write
   * @param user Cashier, manager, reshelver, reciever
   * @throws IOException
   */
   static void writeToMemo(String input, String user) throws IOException {
    String fileMemo = null;
    String FileName1 = "CashierMemos.txt";
    String FileName2 = "ManagerMemos.txt";
    String FileName3 = "ReceiverMemos.txt";
    String FileName4 = "ReshelverMemos.txt";

    if (user.equalsIgnoreCase("Cashier")) {
      fileMemo = FileName1;
    } else if (user.equalsIgnoreCase("Manager")) {
      fileMemo = FileName2;
    } else if (user.equalsIgnoreCase("Receiver")) {
      fileMemo = FileName3;
    } else if (user.equalsIgnoreCase("Reshelver")) {
      fileMemo = FileName4;
    }
    FileWriter write = new FileWriter(fileMemo, true);
    PrintWriter print_line = new PrintWriter(write);
    textLine = todayDate + " " + input;
     print_line.printf("%n");
     print_line.printf(textLine);
    print_line.close();
  }

  /**
   * Writes current list of employees to employee data file
   * @throws IOException
   */
  private static void writeToFileEmployees() throws IOException {
    FileWriter write = new FileWriter(path2, append);
    PrintWriter print_line = new PrintWriter(write);
    for (int i = 0; i < RegisteredEmployees.listOfEmployees.size(); i++) {
      ArrayList<Integer> list = RegisteredEmployees.getEmployeeList();
      Integer ID = list.get(i);
      textLine = String.valueOf(ID);
      print_line.printf("%s" + "%n", textLine);
    }
    print_line.close();
  }

  /**
   * Writes current price history of products to file by retrieveing price history of products
   * from the pricehistory array in the price class
   * @throws IOException
   */
  private static void writeToFilePriceHistory() throws IOException {
    FileWriter write = new FileWriter(path3, append);
    PrintWriter print_line = new PrintWriter(write);
    ArrayList<Product> stock = Inventory.getStock();
    for (int i = 0; i < stock.size(); i++) {
      Product product = (Product) stock.get(i);
      int UPC = product.getUpc();
      ArrayList<String> priceH = product.price.getArrayPriceHistory();
      for (int j = 0; j < priceH.size(); j++) {
        String price = priceH.get(j);
        textLine = UPC + " " + price;
        print_line.printf("%s" + "%n", textLine);
      }
    }
    print_line.close();
  }


  /**
   * Write product information to file. retrieves products from inventory stock array and
   * writes out all the informatio held by the product.
   * @throws IOException
   */
  private static void writeToFileInventory() throws IOException {
    FileWriter write = new FileWriter(path, append);
    PrintWriter print_line = new PrintWriter(write);
    ArrayList<Product> stock = Inventory.getStock();
    for (int i = 0; i < stock.size(); i++) {
      Product product = stock.get(i);
      String name = product.getProductName();
      String location = product.getLocation().getLocationInfo();
      int UPC = product.getUpc();
      int quantity = product.getQuantity();
      String cost = String.valueOf(product.price.getCost());
      String threshold = String.valueOf(product.getThreshold());
      String price = String.valueOf(product.price.getRegularPrice());
      String distributor = product.getDistributor();
      textLine =
          name + "," + location + "," + UPC + "," + quantity + "," + cost + "," + price + "," +
              threshold + "," + distributor;
      print_line.printf("%s" + "%n", textLine);
    }
    print_line.close();
  }

  /**
   * If an item is onsale, then its sale price, period, and upc gets written to the sale items
   * file.
   * @throws IOException
   */
  private static void writeToSaleItems() throws IOException {
    FileWriter write = new FileWriter(path4, false);
    PrintWriter print_line = new PrintWriter(write);
    ArrayList<Product> stock = Inventory.getStock();
    for (int i = 0; i < stock.size(); i++) {
      Product product = (Product) stock.get(i);
      if (product.price.isOnSale()) {
        int UPC = product.getUpc();
        float price = product.price.getSalePrice();
        String period = product.price.getSalePeriod();

        textLine = UPC + " " + price + " " + period;

        print_line.printf("%s" + "%n", textLine);

      }
    }
    print_line.close();

  }

  private static void writeToRevenueLog() throws IOException{
      FileWriter write = new FileWriter(path6, true);
    PrintWriter print_line = new PrintWriter(write);

      textLine = todayDate+ " "+":"+" "+DailyOperations.revenue.get(todayDate) + " "+ DailyOperations.costs.get(todayDate)+ " "+
          DailyOperations.profit.get(todayDate);
    print_line.printf("%n");
      print_line.printf(textLine);
      print_line.close();


  }




  }










