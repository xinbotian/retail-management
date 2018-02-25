import java.io.IOException;
import javax.swing.plaf.synth.SynthTextAreaUI;

/**
 * this location class stores a product's aisle, subsection, and section. When constructing a product,
 * it's passed in as a parameter.
 */
public class Location {

  private String aisle;// aisle in which product is located in store
  private String subsection;// subsection of aisle in which product is located in store
  private String section;// section in which product locates in store.

  Location(String aisle, String subsection, String section) {
    this.aisle = aisle;
    this.subsection = subsection;
    this.section = section;
  }

  /**
   * Prints to screen the location infomation. Called by users to view a product's location
   */
  void locationDisplay(){
    System.out.println("Product aisle: "+aisle+", Section: "+section +", Subsection: "+ subsection);
  }


  /**
   * Returns the information held by this object. used in file writing
   * @return
   */
  String getLocationInfo() {
    return this.aisle + "," + this.subsection + "," + this.section;
  }

  /**
   * Changes subsection of the location
   * @param subsection
   */
  public void setSubsection(String subsection) {
    this.subsection = subsection;
  }


  /**
   * Changes aisle of the location.
   * @param aisle
   */
  public void setAisle(String aisle) {
    this.aisle = aisle;
  }
}
