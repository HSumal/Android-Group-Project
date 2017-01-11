package users;


import android.util.Log;

import flight.Flight;
import flight.Itinerary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class to represent a User.
 * A User will store and modify information pertaining to this User.
 * 
 * @author Irha Ali
 * @author Angela Zhu
 */
public class User implements Serializable {

  private static final long serialVersionUID = 1508216254266634313L;
// initialize private variables for User Class.
  private String email;
  private String password;
  private List<String> clientInfo;
  private List<Itinerary> itinerarys;
  private File directory;

  /**
   * Constructor that defines a type User. 
   * Assigns given value of email to email.
   * Creates an instance of a List of Itineraries
   * Reads and stores information from file specified by email.
   * Information includes:
   * 1. Personal and billing information stored in the following format:
   * LastName;FirstNames;Email;Address;CreditCardNumber;ExpiryDate
   * The ExpiryDate is stored in the format yyyy-MM-dd.
   * 2. Itineraries in which all Flights are stored in the following format:
   * FlightNumber;DepartureDateTime;ArrivalDateTime;Airline;Origin;Destination;Price
   * 
   * @param email the email that identifies the user.
   */
  public User(String email, String password, File directory) {
    this.email = email;
    this.directory = directory;
    itinerarys = new ArrayList<Itinerary>();
    this.password = password;
    loadInfo();
  }

  /**
   * Constructor that defines a type User. 
   * Assigns given value of email to email.
   * Creates an instance of a List of Itineraries
   * Creates and stores info in new file specified as email.txt.
   * Info is stored in the following format:
   * LastName;FirstNames;Email;Address;CreditCardNumber;ExpiryDate
   * The ExpiryDate is stored in the format yyyy-MM-dd.
   * 
   * @param email the email that identifies the user.
   * @param info User information to be stored.
   */
  public User(String email, String password, File directory, String info) {
    this.email = email;
    this.directory = directory;
    this.password = password;
    itinerarys = new ArrayList<Itinerary>();
    clientInfo = Arrays.asList(info.split(";"));
    File newFile = new File(directory, email + ".txt");
    try {
      newFile.createNewFile();
      FileWriter fileW = new FileWriter(newFile);
      BufferedWriter buffW = new BufferedWriter(fileW);
      buffW.write(info);
      buffW.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    catch (Exception e){

    }
  }

  public void saveFile() {
    File emailFile = new File(directory, email + ".txt");
    try {
      BufferedWriter buffW = new BufferedWriter(new FileWriter(emailFile.getPath()));
      buffW.write(getClientInfoString());
      buffW.newLine();
      for (Itinerary i: itinerarys){
        buffW.write(i.flightToString());
        buffW.newLine();
      }
      buffW.close();
    }
    catch (Exception e){

    }
  }
  /**
   * Stores a given itinerary to this User.
   * 
   * @param itinerary the itinerary to be stored.
   */
  public void addItinerary(Itinerary itinerary) {
    // ensures that each flight in itinerary has at least one seat
    if(!(itinerary.booked())) {
      itinerarys.add(itinerary);
      // decrements the seats for each flight in itinerary
      itinerary.bookItinerary();
    }
    saveFile();
  }

  /**
   * Removes a given itinerary from this User.
   * 
   * @param itinerary the itinerary to be removed.
   */
  public void deleteItinerary(Itinerary itinerary) {
    itinerary.unBookItinerary();
    itinerarys.remove(itinerary);
    saveFile();
  }

  /**
   * Reads the file containing this User's password and returns password.
   */
  public void loadInfo() {
    BufferedReader reader = null;
    String line;
    try {
      File emailFile = new File(directory, email+".txt");
      reader = new BufferedReader(new FileReader(emailFile.getPath()));
      line = reader.readLine();
      List<String> info = new ArrayList<String>(Arrays.asList(line.split(";")));
      clientInfo = info;
      line = reader.readLine();
      while (line != null) {
        loadItinerary(line);
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    catch (Exception e){

    }
  }

  /**
   * Converts a string representation of an itinerary into an Itinerary and 
   * stores the new Itinerary.
   * 
   * @param line the string representation of an itinerary.
   */
  private void loadItinerary(String line) {
    String[] info = line.split("/");
    ArrayList<Flight> flights = new ArrayList<>();
    for (int i = 0; i < info.length; i++) {
      String[] details = info[i].split(";");
      double cost = Double.parseDouble(details[6]);
      try {
        flights.add(new Flight(details[0], details[1], details[2], details[3], details[4],
            details[5], cost, Integer.parseInt(details[7])));
      } catch (ParseException pe) {
        pe.printStackTrace();
      }catch (Exception e){

      }
    }
    itinerarys.add(new Itinerary(flights));
  }

  /**
   * Changes this User's information to given information.
   * 
   * @param newInfo the new information to be stored.
   */
  public void changeClientInfo(List<String> newInfo) {
    clientInfo = newInfo;
    saveFile();
  }

  /**
   * Converts given String into a List of String and changes this User's
   * information to given information.
   * 
   * @param newInfo the new information to be stored.
   */
  public void changeClientInfoString(String newInfo) {
    clientInfo = Arrays.asList(newInfo.split(";"));
    saveFile();
  }

  /**
   * Returns the email of this User.
   *
   * @return the email of this User.
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns the password of this User.
   *
   * @return the password of this User.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Returns the information of this User.
   * This includes personal and billing information.
   * 
   * @return the information of this User.
   */
  public List<String> getClientInfo() {
    return clientInfo;
  }

  /**
   * Returns the information of this User as a String in the following format:
   * LastName;FirstNames;Email;Address;CreditCardNumber;ExpiryDate
   * The ExpiryDate is stored in the format yyyy-MM-dd.
   * 
   * @return the information of this User.
   */
  public String getClientInfoString() {
    String result = "";
    for (String i : clientInfo) {
      result += i + ';';
    }
    return result.substring(0, result.length() - 1);
  }

  /**
   * Returns a List of all Itineraries of this User.
   * @return the List of all Itineraries.
   */
  public List<Itinerary> getItinerarys() {
    return itinerarys;
  }

}
