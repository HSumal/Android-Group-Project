package flight;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Defines a variable type Flight which will store the various elements that belong to a flight.
 * These include flight number, flight departure date, flight arrival date, the flight airline,
 * origin of flight, destination of flight, the cost of the flight, and the duration of the flight.
 * 
 * @author Jacob Kewarth and Harpreet Singh Sumal
 */
public class Flight {

  // initialize private variables for Flight Class.
  private String flightNum;
  private Date departure;
  private Date arrival;
  private String airline;
  private String origin;
  private String destination;
  private double cost;
  private double time;

  /**
   * Constructor that defines how a Flight is created.
   * 
   * @param flightNum references the given flights number.
   * @param departure references the given flights departure date. (YYYY-MM-DD).
   * @param arrival references the given flights arrival date. (YYYY-MM-DD).
   * @param airline references the given flights airline. (YYYY-MM-DD).
   * @param origin references the place where the flight will depart from.
   * @param destination references the place where the flight will land.
   * @param cost references the price of the flight/flight ticket.
   * @throws ParseException Parses a string to a Date, so if string is not correct format, error
   *         will be thrown as parse cannot occur.
   */
  public Flight(String flightNum, String departure, String arrival, String airline, String origin,
      String destination, double cost) throws ParseException {

    // testing for date
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    this.arrival = dateFormat.parse(arrival);
    this.departure = dateFormat.parse(departure);
    //


    // assign all the instance variables to the given values
    this.flightNum = flightNum;
    // this.departure = departure;
    // this.arrival = arrival;
    this.airline = airline;
    this.origin = origin;
    this.destination = destination;
    this.cost = cost;

    // time in miliseconds
    this.time = this.arrival.getTime() - this.departure.getTime();
  }

  /**
   * Returns the flight number of the given flight.
   * 
   * @return the flightNum.
   */
  public String getFlightNum() {
    return flightNum;
  }

  /**
   * Sets the current flightNum, to the new given flightNum.
   * 
   * @param flightNum the new flight number to be set.
   */
  public void setFlightNum(String flightNum) {
    this.flightNum = flightNum;
  }

  /**
   * Returns the departure date of the given flight.
   * 
   * @return the departure date.
   */
  public Date getDeparture() {
    return departure;
  }

  /**
   * Returns the string format of the given Flight's Departure Date.
   * 
   * @return return Departure as a String
   */
  public String getDepartureString() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String departureString = dateFormat.format(this.departure);
    return departureString;
  }

  /**
   * Returns the string format of the given Flight's Arrival Date.
   * 
   * @return return Arrival as a String.
   */
  public String getArrivalString() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String departureString = dateFormat.format(this.arrival);
    return departureString;
  }

  /**
   * Sets the departure date, to the new given departure date.
   * 
   * @param departure the new departure date.
   */
  public void setDeparture(Date departure) {
    this.departure = departure;
  }

  /**
   * Returns the arrival date of the given flight.
   * 
   * @return the arrival date.
   */
  public Date getArrival() {
    return arrival;
  }

  /**
   * Sets the arrival date, to the new given arrival date.
   * 
   * @param arrival the new arrival date.
   */
  public void setArrival(Date arrival) {
    this.arrival = arrival;
  }

  /**
   * Returns the airline of the given flight.
   * 
   * @return the airline.
   */
  public String getAirline() {
    return airline;
  }

  /**
   * Sets the airline, to the new given airline.
   * 
   * @param airline the new airline.
   */
  public void setAirline(String airline) {
    this.airline = airline;
  }

  /**
   * Gets the origin (place of departure) of the given flight.
   * 
   * @return the origin of the given flight.
   */
  public String getOrigin() {
    return origin;
  }

  /**
   * Sets the origin (place of departure), to the new given origin.
   * 
   * @param origin the new origin (place of departure).
   */
  public void setOrigin(String origin) {
    this.origin = origin;
  }

  /**
   * Returns the destination of the given flight.
   * 
   * @return the destination of the given flight.
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Sets the destination, to the given destination.
   * 
   * @param destination the new destination.
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * Returns the cost of the given flight.
   * 
   * @return the cost of the given flight.
   */
  public double getCost() {
    return cost;
  }

  /**
   * Sets the cost, to the given cost.
   * 
   * @param cost the new cost.
   */
  public void setCost(double cost) {
    this.cost = cost;
  }

  /**
   * Returns the time (duration of) the given flight.
   * 
   * @return the time of the given flight.
   */
  public double getTime() {
    return time;
  }

  /**
   * Sets the time (duration of) the flight, to the given time.
   * 
   * @param time the new time.
   */
  public void setTime(double time) {
    this.time = time;
  }

  // toString for testing, not final, made by jacob
  /**
   * Our string representation of Flight, used for when we want to represent Flight as a String
   * (Driver Class).
   */
  public String toString() {
    String result = "";
    DecimalFormat df = new DecimalFormat("#.00");
    result += this.flightNum + ";" + this.getDepartureString() + ";";
    result += this.getArrivalString() + ";" + this.airline + ";";
    result += this.origin + ";" + this.destination + ";" + df.format(this.cost);
    return result;
  }
}
