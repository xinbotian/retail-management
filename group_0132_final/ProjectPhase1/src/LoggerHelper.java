/*
This is a logger helper class. it is used to help log messages when a user's method is called.
The messages are logged to log.txt
The orders are logged to orderLog.txt
 */

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;


public class LoggerHelper {

  static Logger logger = Logger.getLogger("LoggerHelper");

  /**
   * This function logs important actions
   * @param logMessage the action in which the user performed
   * @throws IOException
   */
  static void makeALog(String logMessage) throws IOException{
    logger.setUseParentHandlers(false);
    FileHandler handler = new FileHandler("log.txt", true);
    logger.addHandler(handler);
    handler.setFormatter(new SimpleFormatter());
    logger.info(logMessage);
    handler.close();
  }

  /**
   * This function logs orders made.
   * @param logMessage New orders!
   * @throws IOException
   */
  static void addToOrderLog(String logMessage) throws IOException{
    logger.setUseParentHandlers(false);
    FileHandler handler = new FileHandler("orderLog.txt", true);
    logger.addHandler(handler);
    handler.setFormatter(new SimpleFormatter());
    logger.info(logMessage);
    handler.close();
  }



}