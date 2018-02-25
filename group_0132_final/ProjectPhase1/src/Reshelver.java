import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO;
import java.io.IOException;

/**
 * This is a reshelver account. Reshelver can get all location information about a product, such as
 * Aisle, Section, Subsection. Reshelver can also get the quantity of a selected product.
 */

class Reshelver extends User {


  /**
   * Constructor for a new reshelver, takes in id
   * @param id int
   */
  public Reshelver(int id) throws IOException {
    super(id);
  }

  /**
   * Allows the reshelver to change the subsection of the product.
   * @param upc
   * @param subsection
   * @throws IOException
   */
  void changeSubSection(int upc, String subsection) throws  IOException{
    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      LoggerHelper.makeALog("product" + upc + "subsection reset at" + subsection);

      if (product.getQuantity() > 0) {
        product.getLocation().setSubsection(subsection);
        System.out.println("Product:" + upc +"(" + product.getProductName() + ")" + " new Subsection: "+
            subsection);
      }
      else {
        System.out.println("Product:" + upc +"(" + product.getProductName() + ")" + "is out of stock!");
      }
    }
  }


  /**
   * Allows the reset of aisle field of location in product.
   * @param upc prodcut's upc
   * @param aisle aisle of product
   * @throws IOException
   */
  void changeAisle(int upc, String aisle) throws  IOException{
    if (Inventory.productInInventory(upc)) {
      Product product = Inventory.getProduct(upc);
      LoggerHelper.makeALog("product" + upc + "aisle reset at" + aisle);

      if (product.getQuantity() > 0) {
        product.getLocation().setAisle(aisle);
        System.out.println("Product:" + upc +"(" + product.getProductName() + ")" + " new aisle: "+
            aisle);
      }
      else {
        System.out.println("Product:" + upc +"(" + product.getProductName() + ")" + "is out of stock!");
      }
    }

  }

  /**
   * Get the quantity of a product according to upc.
   * @param upc int
   */
  void getQuantity(int upc) throws IOException{
    if (Inventory.productInInventory(upc)) {
      LoggerHelper.makeALog("Reshelver ID" +getID()+" retrieved quantity"
          + " for product"+
          upc);
      Product product = Inventory.getProduct(upc);
      System.out.println("Product " + upc +"(" + product.getProductName() + ")" + " Quantity:" + product.getQuantity());
    }
    else {
      System.out.println("This is an invalid upc!");
    }
  }


}