
package flight;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Jacob Kewarth and Harpreet Singh Sumal.
 *
 */
public class Itinerary {

  // Instance variables for Itinerary class.
  private ArrayList<Flight> flights;
  private double totalCost;
  private double totalTime;
  private Date departure;
  private Date arrival;
  private String origin;
  private String destination;

  /**
   * Creates a new Itinerary (list of flights).
   * 
   * @param flights a list of flights in the Itinerary
   */
  public Itinerary(ArrayList<Flight> flights) {
    this.flights = flights;
    this.totalCost = getCost(flights);
    this.departure = flights.get(0).getDeparture();
    this.arrival = flights.get(flights.size() - 1).getArrival();
    this.origin = flights.get(0).getOrigin();
    this.destination = flights.get(flights.size() - 1).getDestination();
    this.totalTime = this.arrival.getTime() - this.departure.getTime();
  }

  /**
   * returns the date of the departure of the itinerary.
   * 
   * @return the date of the departure of the itinerary
   */
  public Date getDeparture() {
    return this.departure;
  }

  /**
   * returns the date of the Arrival of the Itinerary.
   * 
   * @return the date of the Arrival of the Itinerary
   */
  public Date getArrival() {
    return this.arrival;
  }

  /**
   * returns the origin of the Itinerary.
   * 
   * @return the origin of the Itinerary
   */
  public String getOrigin() {
    return this.origin;
  }

  /**
   * returns the destination of the Itinerary.
   * 
   * @return the destination of the Itinerary
   */
  public String getDestination() {
    return this.destination;
  }

  /**
   * returns the total cost of the list of flights (itinerary). Used in constructor.
   * 
   * @return the cost of the flights
   */
  private double getCost(ArrayList<Flight> flights) {
    double cost = 0;
    for (Flight flight : flights) {
      cost += flight.getCost();
    }
    return cost;
  }

  /**
   * Returns the total cost of all flights in the itinerary.
   * 
   * @return returns the cost of all flights.
   */
  public double getTotalCost() {
    return this.totalCost;
  }


  /**
   * Returns the total time of flight (duration) of all flights in the itinerary.
   * 
   * @return returns the time of all flights.
   */
  public double getTotalTime() {
    return this.totalTime;
  }

  /**
   * Return a List of all the flights in Itinerary.
   * 
   * @return returns a list of all the flights.
   */
  public ArrayList<Flight> getFlights() {
    return this.flights;
  }

  /**
   * Returns the string representation of an Itinerary.
   * 
   * @return returns the string representation.
   */
  public String toString() {
    String result = "";
    for (Flight flight : flights) {
      result += flight.getFlightNum() + ";" + flight.getDepartureString() + ";";
      result += flight.getArrivalString() + ";" + flight.getAirline() + ";";
      result += flight.getOrigin() + ";" + flight.getDestination();
      result += "\n";
    }
    // formatter shows double only to second decimal place
    DecimalFormat df = new DecimalFormat("#.00");
    result += df.format(this.totalCost) + "\n";
    // converts totalTime from milliseconds to hours, then formats
    result += df.format(this.totalTime / 3600000);
    return result;
  }
}
