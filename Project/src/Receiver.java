import java.io.IOException;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This is a receiver object, once a receiver logs in the system, he/she will be able to receive
 * new product from "new products.txt" or receive existing product either by an order made by
 * Inventory, or by a string which is one line in read in from "InventoryStock.txt".
 * Also a receiver is able to check features of a given product identified by unique upc."
 */

class Receiver extends User{


  /**
   * Constructor for Creating a New Reciever . takes in ID as parameter
   * @param id int
   */
  public Receiver(int id) throws IOException {
    super(id);
  }

  static void loadNewOrders() throws IOException {
    for (int i = 0; i < Inventory.getOrderHistory().size(); i++ ) {

      Order o= ((Order) (Inventory.getOrderHistory().get(i)));
      if (!o.isProcessed()) {
        Inventory.getProduct(o.getUpc()).increaseQuantity(o.getQuantity());
        o.setProcessed();
        LoggerHelper.makeALog("Receiver loaded in new products ");
      }
    }
  }


  /**
   * Get order history according to upc.
   * @param upc int
   */
  void getOrderHistoryByUpc(int upc)throws IOException {
    System.out.println("The order history of the order number: " + upc);
    for (int i = 0; i < Inventory.getOrderHistory().size(); i++ ) {
      if (((Order) (Inventory.getOrderHistory().get(i))).getUpc() == upc) {
        System.out.println(((Inventory.getOrderHistory().get(i))).toString());
        LoggerHelper.makeALog("Receiver ID" + getID() + " retrieved order information"
            + "for all orders of product "+upc);

      }
    }

  }




}


