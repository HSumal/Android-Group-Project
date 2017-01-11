package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import app.FlightApp;

public class AdminMain extends AppCompatActivity {

    private FlightApp flightApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        flightApp = (FlightApp) getIntent().getSerializableExtra("flightApp");
    }

    /**
     *
     * @param view
     */
    public void getFlightFile(View view){
        Intent intent = new Intent(this, InputFlight.class);
        intent.putExtra("flightApp", flightApp);
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void viewClients(View view){
        Intent intent = new Intent(this, ViewAllClients.class);
        intent.putExtra("flightApp", flightApp);
        startActivity(intent);
    }

    public void viewFlights(View view){
        Intent intent = new Intent(this, DisplayFlights.class);
        List<String> allFlights = flightApp.getAllFlightsString();
        flightApp.setAllFlights();
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("flights", (ArrayList)allFlights);
        startActivity(intent);
    }
    /**
     * Method to go to searchFlights window/class/tab/thingy.
     * @param view
     */
    public void searchFlights(View view) {
        // Note this does not pass the FlightApp instance yet.
        Intent intent = new Intent(this, SearchFlights.class);
        intent.putExtra("flightApp", flightApp);
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void searchItinerary(View view) {
        Intent intent = new Intent(this, SearchItinerary.class);
        intent.putExtra("flightApp", flightApp);
        startActivity(intent);
    }
}
