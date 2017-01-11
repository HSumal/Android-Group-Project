package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import app.FlightApp;
import flight.Flight;

public class SearchFlights extends AppCompatActivity {

    private FlightApp flightApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flights);
        flightApp = (FlightApp) getIntent().getSerializableExtra("flightApp");
    }

    /**
     *
     * @param view
     */
    public void displayFlights(View view) {
        Intent intent = new Intent(this, DisplayFlights.class);
        EditText origin = (EditText) findViewById(R.id.origin);
        EditText destination = (EditText) findViewById(R.id.destination);
        EditText date = (EditText) findViewById(R.id.date);
        flightApp.getFlights(origin.getText().toString(), destination.getText().toString(), date.getText().toString());
        ArrayList<String> flights = (ArrayList) flightApp.getFlightsString();
        //intent.putExtra("origin", origin.getText().toString());
        //intent.putExtra("destination", destination.getText().toString());
        //intent.putExtra("date", date.getText().toString());
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("flights", flights);
        startActivity(intent);

    }
}
