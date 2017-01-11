
package flight;

import driver.Driver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * A class to represent a FlightDatabase.
 * An object that will store all the Flights from file, or by hand from Users.
 * @author Jacob Kewarth and Harpreet Singh Sumal.
 */
public class FlightDatabase {

  // Instance Variables
  private HashMap<Flight, HashSet<Flight>> database;

  /**
   * Constructor that defines a type FlightDatabase. Creates a FlightDatabase as a type HashMap with
   * the key being Flights and value being a Set of Flights.
   */
  public FlightDatabase() {
    database = new HashMap<Flight, HashSet<Flight>>();
  }

  /**
   * Add a bunch of flights stored in a .txt file to your Database. Note: Flights must be stored in
   * the following format...
   * FlightNumber;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price
   * 
   * @param filename Exact file name in String format to extract Flight data from.
   * @throws IOException Throws exception if the given file is not found/non-existent.
   * @throws ParseException Throws exception if it is unable to parse the given value for cost.
   * @throws NumberFormatException Throws exception if the format of cost is incorrect.
   */
  public void addFlightFile(String filename)
      throws IOException, NumberFormatException, ParseException {
    // read file line by line

    // First have to create the BufferedReader to read the file
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    // String variable to store each line in
    String line;
    // Loop through all the lines until we hit null (end of file, no more
    // lines)
    while ((line = reader.readLine()) != null) {
      // Each line should contain a flight with the same pattern
      // Number;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price
      // Create a list of strings so we can get each piece of information
      // of Flight
      // as each index of the list
      List<String> lineList = new ArrayList<String>(Arrays.asList(line.split(";")));
      // Add a flight with each parameter chosen from list, we assume
      // correct format on file.
      Flight flightToAdd = new Flight(lineList.get(0), lineList.get(1), lineList.get(2),
          lineList.get(3), lineList.get(4), lineList.get(5), Double.parseDouble(lineList.get(6)));
      addFlight(flightToAdd);
    }
    // Close BufferedReader cause we're good programmers :D
    reader.close();
  }

  /**
   * Adds a flight to your FlightDatabase.
   * 
   * @param newFlight Flight to be added to the database.
   */
  public void addFlight(Flight newFlight) {
    database.put(newFlight, new HashSet<Flight>());
    Set<Flight> flights = database.keySet();
    for (Flight flight : flights) {
      if (!(flight == newFlight)) {
        if ((flight.getOrigin().equals(newFlight.getDestination()))
            || (flight.getDestination().equals(newFlight.getOrigin()))) {
          database.get(flight).add(newFlight);
          database.get(newFlight).add(flight);
        }
      }
    }

  }

  /**
   * Makes a list of Itineraries that will show all possible combinations of flights available to
   * take you from your given origin, on the given day, to your destination.
   * 
   * @param origin Place you want to take off from/leave. Place you're currently living.
   * @param destination Place you want to go to.
   * @param departure The date you want to leave origin and begin flying to destination.
   * @return returns a list of Itineraries that satisfy your needs.
   */
  public ArrayList<Itinerary> makeItineraries(String origin, String destination, Date departure) {
    ArrayList<Itinerary> itineraries = new ArrayList<Itinerary>();
    Set<Flight> flights = database.keySet();
    Set<ArrayList<Flight>> itinerariesSet = new HashSet<ArrayList<Flight>>();
    for (Flight flight : flights) {
      // ensures Flight leaves on desired date
      if (flight.getOrigin().equals(origin)) {
        // checks if any flight after flight leads to destination
        if (flight.getDeparture().equals(departure)) {
          // case 1, flight lands at destination
          if (flight.getDestination().equals(destination)) {
            ArrayList<Flight> itineraryList = new ArrayList<>();
            itineraryList.add(flight);
            itinerariesSet.add(itineraryList);
          } else { // case 2, flight doesn't land at destination
            // recursively checks if any there are any Flight's that
            // can be taken after flight which will land at
            // destination
            itinerariesSet.addAll(makeItineraries(destination, flight));
          }
        }
      }
    }
    // converts the Set of Lists of flights in itinerariesSet into a List of
    // Itineraries
    for (ArrayList<Flight> itineraryList : itinerariesSet) {
      if (!(cycles(itineraryList))) {
        Itinerary itinerary = new Itinerary(itineraryList);
        itineraries.add(itinerary);
      }
    }
    return itineraries;
  }

  /**
   * Method Overloading our other method for use with cases in Driver. This method is the same as
   * the makeItineraries above, except it takes in a String for departure, instead of a date
   * variable.
   * 
   * @param origin origin of flight
   * @param destination destination of flight
   * @param departure time you want to leave origin to go to destination
   * @return returns a list of Itineraries that suit these criteria.
   */
  public ArrayList<Itinerary> makeItineraries(String origin, String destination, String departure) {
    ArrayList<Itinerary> itineraries = new ArrayList<Itinerary>();
    Set<Flight> flights = database.keySet();
    Set<ArrayList<Flight>> itinerariesSet = new HashSet<ArrayList<Flight>>();
    for (Flight flight : flights) {
      // ensures Flight leaves on desired date
      if (flight.getOrigin().equals(origin)) {
        // checks if any flight leaves on desired date
        Date date = flight.getDeparture();
        String newDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        if (newDate.equals(departure)) {
          // case 1, flight lands at destination
          if (flight.getDestination().equals(destination)) {
            ArrayList<Flight> itineraryList = new ArrayList<>();
            itineraryList.add(flight);
            itinerariesSet.add(itineraryList);
          } else { // case 2, flight doesn't land at destination
            // recursively checks if any there are any Flight's that
            // can be taken after flight which will land at
            // destination
            itinerariesSet.addAll(makeItineraries(destination, flight));
          }
        }
      }
    }
    // converts the Set of Lists of flights in itinerariesSet into a List of
    // Itineraries
    for (ArrayList<Flight> itineraryList : itinerariesSet) {
      if (!(cycles(itineraryList))) {
        Itinerary itinerary = new Itinerary(itineraryList);
        itineraries.add(itinerary);
      }
    }
    return itineraries;
  }

  // recursive helper
  private Set<ArrayList<Flight>> makeItineraries(String destination, Flight flight) {
    int secondToMilliSecond = 1000;
    Set<ArrayList<Flight>> itineraries = new HashSet<ArrayList<Flight>>();
    // finds flights which links to flight
    Set<Flight> neighbourFlights = database.get(flight);
    for (Flight neighbourFlight : neighbourFlights) {
      // ensures that the time flight arriving and neighbourFlight
      // departing is between the min
      // and max layover
      // time between neighbourFlight leaving and flight arriving
      long timeBetween = neighbourFlight.getDeparture().getTime() - flight.getArrival().getTime();

      if (timeBetween <= (Driver.MAX_LAYOVER.getSeconds() * secondToMilliSecond)
          && timeBetween >= (Driver.MIN_LAYOVER.getSeconds() * secondToMilliSecond)) {

        // base case, if Flight with desired destination is found
        if (neighbourFlight.getDestination().equals(destination)) {
          ArrayList<Flight> itinerary = new ArrayList<>();
          itinerary.add(neighbourFlight);
          itinerary.add(0, flight);
          itineraries.add(itinerary);
        } else { // occur if neighbourFlight doesn't land at destination
          // recursively checks if flight links to any flights to
          // destination
          Set<ArrayList<Flight>> itinerariesHelper = makeItineraries(destination, neighbourFlight);
          for (ArrayList<Flight> itinerary : itinerariesHelper) {
            itinerary.add(0, flight);
            itineraries.add(itinerary);
          }
        }
      }
    }

    return itineraries;

  }

  /**
   * Another helper method for Driver. This method will return a list of Itinerary as a List of
   * String. But with the same data held inside.
   * 
   * @param itineraries the list of itineraries being converted
   * @return a String version of the list of itineraries.
   */
  public ArrayList<String> makeItinerariesString(ArrayList<Itinerary> itineraries) {
    ArrayList<String> itinerariesString = new ArrayList<>();
    for (Itinerary itinerary : itineraries) {
      itinerariesString.add(itinerary.toString());
    }
    return itinerariesString;
  }

  /**
   * returns list of all flights from origin to destination leaving on departure.
   * 
   * @param origin the location the flight leaves from
   * @param destination the location the flight lands at
   * @param departure the date the flight leaves
   * @return list of all flights from origin to destination leaving on departure
   */
  public ArrayList<Flight> makeFlights(String origin, String destination, Date departure) {
    ArrayList<Flight> flightsList = new ArrayList<Flight>();
    Set<Flight> flights = this.database.keySet();
    for (Flight flight : flights) {
      if ((flight.getDestination().equals(destination)) && (flight.getOrigin().equals(origin))
          && (flight.getDeparture().equals(departure))) {
        flightsList.add(flight);
      }
    }
    return flightsList;
  }

  /**
   * Method Overloading our other method for use with cases in Driver. This method is the same as
   * the makeFlights above, except it takes in a String for departure, instead of a date variable.
   * 
   * @param origin origin of flight
   * @param destination destination of flight
   * @param departure time you want to leave origin to go to destination
   * @return returns a list of Flights that suit these criteria.
   */
  public ArrayList<Flight> makeFlights(String origin, String destination, String departure) {
    ArrayList<Flight> flightsList = new ArrayList<Flight>();
    Set<Flight> flights = this.database.keySet();
    for (Flight flight : flights) {
      // converts flights departure date to format yyyy-MM-dd
      Date date = flight.getDeparture();
      String newDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
      if ((flight.getDestination().equals(destination)) && (flight.getOrigin().equals(origin))
          && (newDate.equals(departure))) {
        flightsList.add(flight);
      }
    }
    return flightsList;
  }

  /**
   * Another helper method for Driver. This method will return a list of Flights as a List of
   * String. But with the same data held inside.
   * 
   * @param flights the list of Flights being converted
   * @return a String version of the list of Flights.
   */
  public ArrayList<String> makeFlightsString(ArrayList<Flight> flights) {
    ArrayList<String> flightsString = new ArrayList<>();
    for (Flight flight : flights) {
      flightsString.add(flight.toString());
    }
    return flightsString;
  }

  /*
   * SORTING METHODS All of these methods use Quick Sort. (Literally converted A48 Quicksort into a
   * Java version) SORTING METHODS
   */
  /**
   * Returns a list of Flights, sorted by the greatest cost.
   * 
   * @param flights a List of flights to be sorted
   * @return the list of flights sorted from least cost to greatest cost
   */
  public List<Flight> sortFlightsLeastCost(List<Flight> flights) {
    List<Flight> newFlights = new ArrayList<Flight>();
    // base case, flights is an empty List or contains only 1 flight
    if (flights.size() <= 1) {
      newFlights = flights;
    } else { // occurs if flights contains more then 1 flight
      int pivot = (flights.size() - 1);
      Flight pivotFlight = flights.get(pivot);
      int initialCounter = 0;
      // moves all flights which cost more then pivotFlight to the right
      // of the pivot
      while (!(initialCounter == pivot)) {
        if (flights.get(initialCounter).getCost() > pivotFlight.getCost()) {
          ArrayList<Flight> tempFlightsList = new ArrayList<Flight>();
          tempFlightsList.addAll(flights.subList(0, initialCounter));
          tempFlightsList.addAll(flights.subList(initialCounter + 1, flights.size()));
          tempFlightsList.add(flights.get(initialCounter));
          flights = tempFlightsList;
          pivot -= 1;
        } else {
          initialCounter += 1;
        }
      }
      List<Flight> leftSort = sortFlightsLeastCost(flights.subList(0, pivot));
      List<Flight> rightSort = sortFlightsLeastCost(flights.subList(pivot + 1, flights.size()));
      newFlights.addAll(leftSort);
      newFlights.add(flights.get(pivot));
      newFlights.addAll(rightSort);
    }
    return newFlights;
  }

  /**
   * Returns a list of Flights, sorted by the greatest cost.
   * 
   * @param flights the list of Flights to be sorted by greatest cost.
   * @return returns a sorted version of the inputed list of Flights.
   */
  public List<Flight> sortFlightsGreatestCost(List<Flight> flights) {
    List<Flight> newFlights = new ArrayList<Flight>();
    // base case, flights is an empty List or contains only 1 flight
    if (flights.size() <= 1) {
      newFlights = flights;
    } else { // occurs if flights contains more then 1 flight
      int pivot = (flights.size() - 1);
      Flight pivotFlight = flights.get(pivot);
      int initialCounter = 0;
      // moves all flights which cost less then pivotFlight to the right
      // of the pivot
      while (!(initialCounter == pivot)) {
        if (flights.get(initialCounter).getCost() < pivotFlight.getCost()) {
          ArrayList<Flight> tempFlightsList = new ArrayList<Flight>();
          tempFlightsList.addAll(flights.subList(0, initialCounter));
          tempFlightsList.addAll(flights.subList(initialCounter + 1, flights.size()));
          tempFlightsList.add(flights.get(initialCounter));
          flights = tempFlightsList;
          pivot -= 1;
        } else {
          initialCounter += 1;
        }
      }
      List<Flight> leftSort = sortFlightsGreatestCost(flights.subList(0, pivot));
      List<Flight> rightSort = sortFlightsGreatestCost(flights.subList(pivot + 1, flights.size()));
      newFlights.addAll(leftSort);
      newFlights.add(flights.get(pivot));
      newFlights.addAll(rightSort);
    }
    return newFlights;
  }

  /**
   * Returns a sorted list of Flights, sorted by the least travel time.
   * 
   * @param flights the list of Flights to be sorted by least travel time.
   * @return returns a sorted version of the inputed list of Flights.
   */
  public List<Flight> sortFlightsLeastTime(List<Flight> flights) {
    List<Flight> newFlights = new ArrayList<Flight>();
    // base case, flights is an empty List or contains only 1 flight
    if (flights.size() <= 1) {
      newFlights = flights;
    } else { // occurs if flights contains more then 1 flight
      int pivot = (flights.size() - 1);
      Flight pivotFlight = flights.get(pivot);
      int initialCounter = 0;
      // moves all flights which cost more then pivotFlight to the right
      // of the pivot
      while (!(initialCounter == pivot)) {
        if (flights.get(initialCounter).getTime() > pivotFlight.getTime()) {
          ArrayList<Flight> tempFlightsList = new ArrayList<Flight>();
          tempFlightsList.addAll(flights.subList(0, initialCounter));
          tempFlightsList.addAll(flights.subList(initialCounter + 1, flights.size()));
          tempFlightsList.add(flights.get(initialCounter));
          flights = tempFlightsList;
          pivot -= 1;
        } else {
          initialCounter += 1;
        }
      }
      List<Flight> leftSort = sortFlightsLeastTime(flights.subList(0, pivot));
      List<Flight> rightSort = sortFlightsLeastTime(flights.subList(pivot + 1, flights.size()));
      newFlights.addAll(leftSort);
      newFlights.add(flights.get(pivot));
      newFlights.addAll(rightSort);
    }
    return newFlights;
  }

  /**
   * Returns a sorted list of Flights, sorted by the greatest travel time.
   * 
   * @param flights the list of Flights to be sorted by the greatest travel time.
   * @return returns a sorted version of the inputed list of FLights.
   */
  public List<Flight> sortFlightsGreatestTime(List<Flight> flights) {
    List<Flight> newFlights = new ArrayList<Flight>();
    // base case, flights is an empty List or contains only 1 flight
    if (flights.size() <= 1) {
      newFlights = flights;
    } else { // occurs if flights contains more then 1 flight
      int pivot = (flights.size() - 1);
      Flight pivotFlight = flights.get(pivot);
      int initialCounter = 0;
      // moves all flights which cost less then pivotFlight to the right
      // of the pivot
      while (!(initialCounter == pivot)) {
        if (flights.get(initialCounter).getTime() < pivotFlight.getTime()) {
          ArrayList<Flight> tempFlightsList = new ArrayList<Flight>();
          tempFlightsList.addAll(flights.subList(0, initialCounter));
          tempFlightsList.addAll(flights.subList(initialCounter + 1, flights.size()));
          tempFlightsList.add(flights.get(initialCounter));
          flights = tempFlightsList;
          pivot -= 1;
        } else {
          initialCounter += 1;
        }
      }
      List<Flight> leftSort = sortFlightsGreatestTime(flights.subList(0, pivot));
      List<Flight> rightSort = sortFlightsGreatestTime(flights.subList(pivot + 1, flights.size()));
      newFlights.addAll(leftSort);
      newFlights.add(flights.get(pivot));
      newFlights.addAll(rightSort);
    }
    return newFlights;
  }

  /**
   * Returns a sorted list of Itineraries, sorted by the least cost.
   * 
   * @param itineraries the list of Itinerary to be sorted by least flight cost.
   * @return returns a sorted version of the inputed list of itineraries.
   */
  public List<Itinerary> sortItinerariesLeastCost(List<Itinerary> itineraries) {
    List<Itinerary> newItineraries = new ArrayList<Itinerary>();
    // base case, itineraries is an empty List or contains only 1 itinerary
    if (itineraries.size() <= 1) {
      newItineraries = itineraries;
    } else { // occurs if flights contains more then 1 Itinerary
      int pivot = (itineraries.size() - 1);
      Itinerary pivotItinerary = itineraries.get(pivot);
      int initialCounter = 0;
      // moves all Itineraries which cost more then pivotItinerary to the
      // right of the pivot
      while (!(initialCounter == pivot)) {
        if (itineraries.get(initialCounter).getTotalCost() > pivotItinerary.getTotalCost()) {
          ArrayList<Itinerary> tempItinerariesList = new ArrayList<Itinerary>();
          tempItinerariesList.addAll(itineraries.subList(0, initialCounter));
          tempItinerariesList.addAll(itineraries.subList(initialCounter + 1, itineraries.size()));
          tempItinerariesList.add(itineraries.get(initialCounter));
          itineraries = tempItinerariesList;
          pivot -= 1;
        } else {
          initialCounter += 1;
        }
      }
      List<Itinerary> leftSort = sortItinerariesLeastCost(itineraries.subList(0, pivot));
      List<Itinerary> rightSort =
          sortItinerariesLeastCost(itineraries.subList(pivot + 1, itineraries.size()));
      newItineraries.addAll(leftSort);
      newItineraries.add(itineraries.get(pivot));
      newItineraries.addAll(rightSort);
    }
    return newItineraries;
  }

  /**
   * Returns a sorted list of Itineraries, sorted by the greatest cost.
   * 
   * @param itineraries the list of Itinerary to be sorted by greatest flight cost.
   * @return returns a sorted version of the inputed list of itineraries.
   */
  public List<Itinerary> sortItinerariesGreatestCost(List<Itinerary> itineraries) {
    List<Itinerary> newItineraries = new ArrayList<Itinerary>();
    // base case, itineraries is an empty List or contains only 1 itinerary
    if (itineraries.size() <= 1) {
      newItineraries = itineraries;
    } else { // occurs if flights contains more then 1 Itinerary
      int pivot = (itineraries.size() - 1);
      Itinerary pivotItinerary = itineraries.get(pivot);
      int initialCounter = 0;
      // moves all Itineraries which cost more then pivotItinerary to the
      // right of the pivot
      while (!(initialCounter == pivot)) {
        if (itineraries.get(initialCounter).getTotalCost() < pivotItinerary.getTotalCost()) {
          ArrayList<Itinerary> tempItinerariesList = new ArrayList<Itinerary>();
          tempItinerariesList.addAll(itineraries.subList(0, initialCounter));
          tempItinerariesList.addAll(itineraries.subList(initialCounter + 1, itineraries.size()));
          tempItinerariesList.add(itineraries.get(initialCounter));
          itineraries = tempItinerariesList;
          pivot -= 1;
        } else {
          initialCounter += 1;
        }
      }
      List<Itinerary> leftSort = sortItinerariesGreatestCost(itineraries.subList(0, pivot));
      List<Itinerary> rightSort =
          sortItinerariesGreatestCost(itineraries.subList(pivot + 1, itineraries.size()));
      newItineraries.addAll(leftSort);
      newItineraries.add(itineraries.get(pivot));
      newItineraries.addAll(rightSort);
    }
    return newItineraries;
  }

  /**
   * Returns a sorted list of Itineraries, sorted by the smallest flight (duration) time.
   * 
   * @param itineraries the list of Itinerary to be sorted by smallest flight time.
   * @return returns a sorted version of the inputed list of itineraries.
   */
  public List<Itinerary> sortItinerariesLeastTime(List<Itinerary> itineraries) {
    List<Itinerary> newItineraries = new ArrayList<Itinerary>();
    // base case, itineraries is an empty List or contains only 1 itinerary
    if (itineraries.size() <= 1) {
      newItineraries = itineraries;
    } else { // occurs if flights contains more then 1 Itinerary
      int pivot = (itineraries.size() - 1);
      Itinerary pivotItinerary = itineraries.get(pivot);
      int initialCounter = 0;
      // moves all Itineraries which cost more then pivotItinerary to the
      // right of the pivot
      while (!(initialCounter == pivot)) {
        if (itineraries.get(initialCounter).getTotalTime() > pivotItinerary.getTotalTime()) {
          ArrayList<Itinerary> tempItinerariesList = new ArrayList<Itinerary>();
          tempItinerariesList.addAll(itineraries.subList(0, initialCounter));
          tempItinerariesList.addAll(itineraries.subList(initialCounter + 1, itineraries.size()));
          tempItinerariesList.add(itineraries.get(initialCounter));
          itineraries = tempItinerariesList;
          pivot -= 1;
        } else {
          initialCounter += 1;
        }
      }
      List<Itinerary> leftSort = sortItinerariesLeastTime(itineraries.subList(0, pivot));
      List<Itinerary> rightSort =
          sortItinerariesLeastTime(itineraries.subList(pivot + 1, itineraries.size()));
      newItineraries.addAll(leftSort);
      newItineraries.add(itineraries.get(pivot));
      newItineraries.addAll(rightSort);
    }
    return newItineraries;
  }

  /**
   * Returns a sorted List of Itineraries, sorted by the greatest flight (duration) time.
   * 
   * @param itineraries the list of Itinerary to be sorted by greatest flight time.
   * @return returns a sorted version of the inputed list of itineraries.
   */
  public List<Itinerary> sortItinerariesGreatestTime(List<Itinerary> itineraries) {
    List<Itinerary> newItineraries = new ArrayList<Itinerary>();
    // base case, itineraries is an empty List or contains only 1 itinerary
    if (itineraries.size() <= 1) {
      newItineraries = itineraries;
    } else { // occurs if flights contains more then 1 Itinerary
      int pivot = (itineraries.size() - 1);
      Itinerary pivotItinerary = itineraries.get(pivot);
      int initialCounter = 0;
      // moves all Itineraries which cost more then pivotItinerary to the
      // right of the pivot
      while (!(initialCounter == pivot)) {
        if (itineraries.get(initialCounter).getTotalTime() < pivotItinerary.getTotalTime()) {
          ArrayList<Itinerary> tempItinerariesList = new ArrayList<Itinerary>();
          tempItinerariesList.addAll(itineraries.subList(0, initialCounter));
          tempItinerariesList.addAll(itineraries.subList(initialCounter + 1, itineraries.size()));
          tempItinerariesList.add(itineraries.get(initialCounter));
          itineraries = tempItinerariesList;
          pivot -= 1;
        } else {
          initialCounter += 1;
        }
      }
      List<Itinerary> leftSort = sortItinerariesGreatestTime(itineraries.subList(0, pivot));
      List<Itinerary> rightSort =
          sortItinerariesGreatestTime(itineraries.subList(pivot + 1, itineraries.size()));
      newItineraries.addAll(leftSort);
      newItineraries.add(itineraries.get(pivot));
      newItineraries.addAll(rightSort);
    }
    return newItineraries;
  }

  /**
   * Returns true if the flights in the given list of flights visits the same destination/origin
   * more then once. Returns false otherwise.
   * 
   * @param flights an ArrayList of type Flight.
   * @return true if the flights in flights visits the same destination/origin more then once.
   *         returns false otherwise.
   */
  private boolean cycles(ArrayList<Flight> flights) {
    Set<String> origins = new HashSet<>();
    Set<String> destinations = new HashSet<>();

    // fills sets with the origins and destinations of each flight in
    // flights
    for (Flight flight : flights) {
      destinations.add(flight.getDestination());
      origins.add(flight.getOrigin());
    }
    // if the size of origin and destination is the same as the size of
    // flight
    // => that all origins and destinations are unique => no place is
    // visited twice
    if ((origins.size() == (flights.size())) && (destinations.size() == flights.size())) {
      return false;
    }
    return true;
  }  
}
