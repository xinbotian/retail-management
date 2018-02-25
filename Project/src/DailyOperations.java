import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.*;

/**
 * DailyOperations has 3 hashmaps to keep track daily operations. (revenue and cost)
 * Each day is a key. Revenues and costs stored as the value.
 */

class DailyOperations {

  //today's date
  private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
  private static LocalDate localDate = LocalDate.now();
  private static String todayDate = dtf.format(localDate);

  //Hashmap to store revenue of different dates
  static HashMap<String, Float> revenue = new HashMap<>();

  //Hashmap to store costs of different dates
  static HashMap<String, Float> costs = new HashMap<>();

  //Hashmap to store profits of different dates
  static HashMap<String, Float> profit = new HashMap<>();

  /**
   * Increase the daily revenue when product is sold.
   *
   * @param rev float
   */
  static void addToDailyRevenue(float rev) {
    Float castedRev = (Float) rev;
    Float newRev = revenue.get(todayDate) + castedRev;
    revenue.put(todayDate, newRev);
  }

  /**
   * Decrease the daily revenue when product is returned.
   *
   * @param rev float
   */
  static void subtractFromDailyRevenue(float rev) {
    Float castedRev = (Float) rev;
    Float newRev = revenue.get(todayDate) - castedRev;
    revenue.put(todayDate, newRev);
  }

  /**
   * Increase the daily cost.
   *
   * @param cost float
   */
  static void addToDailyCost(float cost) {
    Float castedCost = (Float) cost;
    Float newCost = costs.get(todayDate) + castedCost;
    costs.put(todayDate, newCost);
  }

  /**
   * Decrease the daily cost when product is returned.
   *
   * @param cost float
   */
  static void subtractFromDailyCost(float cost) {
    Float castedCost = (Float) cost;
    Float newCost = costs.get(todayDate) - castedCost;
    costs.put(todayDate, newCost);
  }

}


