package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;


import java.text.ParseException;
import java.util.ArrayList;

import app.FlightApp;
import flight.Flight;
import flight.Itinerary;

public class SearchItinerary extends AppCompatActivity {

    private FlightApp flightApp;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_itinerary);
        username = getIntent().getStringExtra("username");
        flightApp = (FlightApp) getIntent().getSerializableExtra("flightApp");
    }

    /**
     *
     * @param view
     */
    public void displayItinerary(View view) {
        Intent intent = new Intent(this, DisplayItinerary.class);
        EditText origin = (EditText) findViewById(R.id.origin);
        EditText destination = (EditText) findViewById(R.id.destination);
        EditText date = (EditText) findViewById(R.id.date);
        ArrayList<Itinerary> itineraries = (ArrayList) flightApp.getItineraries(origin.getText().toString(), destination.getText().toString(), date.getText().toString());

        intent.putExtra("flightApp", flightApp);
        intent.putExtra("username", username);
        intent.putExtra("itineraries", itineraries);
        startActivity(intent);
    }

}


