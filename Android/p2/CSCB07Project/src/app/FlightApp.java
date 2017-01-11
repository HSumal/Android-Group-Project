package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import flight.Flight;
import flight.FlightDatabase;
import flight.Itinerary;
import users.User;

/**
 * A class that represents the basic functionality of the flight application.
 * This class is responsible for performing the following operations:
 * 1. At creation, it initializes the instance variables used to store the
 *        information on users, flights, and itineraries and loads all previously
 *        saved information.
 * 2. When prompted, it obtains stored information on users, flights and itineraries.
 * 3. It alters stored information according to user prompts.
 */

/**
 * @author angel_000
 *
 */
public class FlightApp {

  // instance variables
  private FlightDatabase dataBase;
  private HashMap<String, User> clients;
  private User currentUser;
  private List<Itinerary> searchItinerary;
  private List<Flight> searchFlight;
  private static final String clientFile = "Clients.txt";
  private static final String adminFile = "Admins.txt";

  /**
   * Constructor that defines a type FlightApp. Creates an instance of FlightDatabase
   * and creates a Hashmap of all users with the key as each User email and value as
   * and instance of User.
   */
  public FlightApp() {
    dataBase = new FlightDatabase();
    clients = new HashMap<String, User>();
    makeClients();

  }

  /**
   * Gets a List of all stored client emails and creates an instance of User for each.
   * Stores an information in Hashmap clients with email as key and instance of User as value.
   */
  private void makeClients() {
    List<String> clientEmail = getClientsFromFile();
    for (String email : clientEmail) {
      clients.put(email, new User(email));
    }
  }

  /**
   * Creates an instance of User for the current User accessing FlightApp.
   * 
   * @param email the email of the current User.
   */
  public void login(String email) {
    currentUser = new User(email);
  }

  /**
   * Adds a new client email to a file of all client email.
   * Creates a new instance of User for new email.
   * 
   * @param email the email that identifies the client.
   */
  public void addClient(String email, String info) {
    try {
      // open file containing all client email
      // add on a new line containing new email
      BufferedWriter writer = new BufferedWriter(new FileWriter(clientFile, true));
      writer.newLine();
      writer.write(email);
      writer.close();
      
      // add a new instance of User for new email
      clients.put(email, new User(email, info));
    } catch (FileNotFoundException fnfe) {
      
      // if client email file does not exisit
      // create a new client email file and add new email
      try {
        File newFile = new File(clientFile);
        newFile.createNewFile();
        FileWriter fileW = new FileWriter(newFile);
        BufferedWriter buffW = new BufferedWriter(fileW);
        buffW.write(email);
        
        //create instance of User for new email
        clients.put(email, new User(email, info));
        buffW.close();
        fileW.close();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * Adds a new admin email to a file of all admin email.
   * Creates a new instance of User for new email.
   * 
   * @param email the email that identifies the admin.
   */
  public void addAdmin(String email) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(adminFile, true));
      writer.newLine();
      writer.write(email);
      writer.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * Returns the client's information in a String.
   * String is in the following format:
   * LastName;FirstNames;Email;Address;CreditCardNumber;ExpiryDate
   * The ExpiryDate is stored in the format yyyy-MM-dd.
   * 
   * @param email the email to identify the client.
   */
  public String viewClientInfo(String email) {
    return clients.get(email).getClientInfoString();
  }

  /**
   * Returns a List of all possible Itineraries with corresponding origin, destination,
   * and departure date.
   * 
   * @param origin the location you are leaving from.
   * @param destination the location you will reach.
   * @param departure the date you leave on. Format: yyyy-MM-dd.
   * @return returns a list of Itineraries with corresponding information.
   */
  public List<Itinerary> getItineraries(String origin, String destination, String departure) {
    searchItinerary = dataBase.makeItineraries(origin, destination, departure);
    return searchItinerary;
  }

  /** Returns the last stored List of Itineraries as a String.
   * 
   * @return the String version of the List of Itineraries to be returned.
   */
  public List<String> getItinerariesString() {
    return dataBase.makeItinerariesString((ArrayList) searchItinerary);
  }

  /**
   * Returns a List of all Flights with corresponding origin, destination, and departure date.
   * 
   * @param origin the location you are leaving from.
   * @param destination the location you will reach.
   * @param departure the date you leave on. Format: yyyy-MM-dd.
   * @return returns a list of Flights with corresponding information.
   */
  public List<Flight> getFlights(String origin, String destination, String departure) {
    searchFlight = dataBase.makeFlights(origin, destination, departure);
    return searchFlight;
  }

  /**
   * Returns the last stored List of Flights as a String.
   * 
   * @return the String version of the List of Flights to be returned.
   */
  public List<String> getFlightsString() {
    return dataBase.makeFlightsString((ArrayList) searchFlight);
  }

  /**
   * Creates and stores an instance of Flight for all flight information stored in .txt file
   * Flight information is stored in the following format:
   * FlightNumber;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price
   * 
   * @param fileName Exact file name in String format to extract Flight data from.
   * @throws IOException Throws exception if the given file is not found/non-existent.
   * @throws ParseException Throws exception if it is unable to parse the given value for cost.
   * @throws NumberFormatException Throws exception if the format of cost is incorrect.
   */
  public void uploadFlightFile(String fileName)
      throws NumberFormatException, IOException, ParseException {
    dataBase.addFlightFile(fileName);
  }

  /**
   * Stores an instance of Flight.
   * 
   * @param flight the Flight to be stored.
   */
  public void addFlight(Flight flight) {
    dataBase.addFlight(flight);
  }

  /**
   * Creates and stores an instance of Flight from flight information in specifies .txt file.
   * Flight information is stored in the following format:
   * FlightNumber;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price
   * 
   * @param path the exact file path from which extract Flight information.
   * @throws NumberFormatException Throws exception if the format of cost is incorrect.
   * @throws IOException Throws exception if the given file is not found/non-existent.
   * @throws ParseException Throws exception if it is unable to parse the given value for cost.
   */
  public void addFlightFile(String path) throws NumberFormatException, IOException, ParseException {
    dataBase.addFlightFile(path);
  }

  /**
   * Returns the last stored List of Itineraries sorted by least cost.
   * 
   * @return returns the stored List of Itineraries sorted by least cost.
   */
  public List<Itinerary> sortLeastCost() {
    searchItinerary = dataBase.sortItinerariesLeastCost(searchItinerary);
    return searchItinerary;
  }

  /**
   * Returns the last stored List of Itineraries sorted by least time.
   * 
   * @return returns the stored List of Itineraries sorted by least time.
   */
  public List<Itinerary> sortLeastTime() {
    searchItinerary = dataBase.sortItinerariesLeastTime(searchItinerary);
    return searchItinerary;
  }

  /**
   * Returns the last stored List of Itineraries sorted by greatest cost.
   * 
   * @return returns the stored List of Itineraries sorted by greatest cost.
   */
  public List<Itinerary> sortGreatestCost() {
    searchItinerary = dataBase.sortItinerariesGreatestCost(searchItinerary);
    return searchItinerary;
  }

  /**
   * Returns the last stored List of Itineraries sorted by greatest time.
   * 
   * @return returns the stored List of Itineraries sorted by greatest time.
   */
  public List<Itinerary> sortGreatestTime() {
    searchItinerary = dataBase.sortItinerariesGreatestTime(searchItinerary);
    return searchItinerary;
  }

  /**
   * Returns a List of email of all stored client email.
   * 
   * @return returns the List of email of all clients.
   */
  public List<String> getClients() {
    Set<String> keys = clients.keySet();
    List<String> emailList = new ArrayList<String>();
    for (String email : keys) {
      emailList.add(email);
    }
    return emailList;
  }

  /**
   * Reads and returns a List of all client email from a file containing all client email.\
   * 
   * @return a List of all client email.
   */
  private List<String> getClientsFromFile() {
    List<String> result = new ArrayList<>();
    BufferedReader reader;
    String line;
    try {
      reader = new BufferedReader(new FileReader(clientFile));
      line = reader.readLine();
      while (line != null) {
        result.add(line);
        line = reader.readLine();
      }
      reader.close();
    } catch (FileNotFoundException fnfe) {
      result = new ArrayList<>();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return result;
  }

  /**
   * Changes the stored info of specified client to new given info.
   * 
   * @param email the specified client to be modified.
   * @param info the new given info to be stored.
   */
  public void changeClientInfo(String email, String info) {
    clients.get(email).changeClientInfoString(info);
  }

  /**
   * Given a file of a specified client, changes currently stored information to
   * information found in given file.
   * 
   * @param path the exact file path from which information is obtained.
   * @throws IOException Throws exception if the given file is not found/non-existent.
   */
  public void changeClientInfoFile(String path) throws IOException {
    BufferedReader reader = null;
    String line;
    // open the file and split the info at ;
    reader = new BufferedReader(new FileReader(path));
    line = reader.readLine();
    List<String> info = new ArrayList<String>(Arrays.asList(line.split(";")));
    changeClientInfo(info.get(2), line);
    reader.close();
  }
}
