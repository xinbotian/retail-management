import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Prints to display the text for a user can see.
 */
class PrintDisplay {

  /**
   * prints to display a user's "home" options in the UI.
   * @param user
   * @throws IOException
   */
  static void printUser(String user) throws IOException {
    BufferedReader br = null;
    String FileName = null;

    if (user.equalsIgnoreCase("Cashier")) {
      FileName = "CashierText.txt";
    } else if (user.equalsIgnoreCase("Manager")) {
      FileName = "ManagerText.txt";
    } else if (user.equalsIgnoreCase("Receiver")) {
      FileName = "ReceiverText.txt";
    } else if (user.equalsIgnoreCase("Reshelver")) {
      FileName = "ReshelverText.txt";
    }
    try {
      br = new BufferedReader(new FileReader(FileName));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (br != null) {
      for (String line; (line = br.readLine()) != null; ) {
        System.out.println(line);
      }
    }
  }


  /**
   * Prints a user's memo files according to which user.
   * @param user
   * @throws IOException
   */
  public static void printMemos(String user) throws IOException {
    {
      BufferedReader br = null;
      String FileName = null;

      if (user.equalsIgnoreCase("Cashier")) {
        FileName = "CashierMemos.txt";
      } else if (user.equalsIgnoreCase("Manager")) {
        FileName = "ManagerMemos.txt";
      } else if (user.equalsIgnoreCase("Receiver")) {
        FileName = "ReceiverMemos.txt";
      } else if (user.equalsIgnoreCase("Reshelver")) {
        FileName = "ReshelverMemos.txt";
      }
      try {
        br = new BufferedReader(new FileReader(FileName));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      if (br != null) {
        for (String line; (line = br.readLine()) != null; ) {
          System.out.println(line);
        }
      }
    }


  }
}


