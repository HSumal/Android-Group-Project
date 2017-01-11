package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import app.FlightApp;
import flight.Itinerary;

public class DisplayItinerary extends AppCompatActivity {

    private FlightApp flightApp;
    private ArrayList<Itinerary> itineraries;
    ArrayList<String> itinerariesString;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_itinerary);

        username = getIntent().getStringExtra("username");
        flightApp = (FlightApp) getIntent().getSerializableExtra("flightApp");
        itineraries = (ArrayList) getIntent().getSerializableExtra("itineraries");
        itinerariesString = (ArrayList) flightApp.getItinerariesString();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, itinerariesString);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itinerary = (String) adapterView.getItemAtPosition(i); // represents flight clicked on
                Toast.makeText(view.getContext(), "Itinerary Booked!" + i, Toast.LENGTH_SHORT).show();
                flightApp.bookItinirary(username, itineraries.get(i));
            }
        });
    }

    /**
     *
     * @param view
     */
    public void greatestCost(View view) {
        flightApp.sortGreatestCost();
        Intent intent = new Intent(this, DisplayItinerary.class);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("itineraries", (ArrayList) flightApp.getItinerariesString());
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void leastCost(View view) {
        flightApp.sortLeastCost();
        Intent intent = new Intent(this, DisplayItinerary.class);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("itineraries", (ArrayList) flightApp.getItinerariesString());
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void greatestTime(View view) {
        flightApp.sortGreatestTime();
        Intent intent = new Intent(this, DisplayItinerary.class);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("itineraries", (ArrayList) flightApp.getItinerariesString());
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void leastTime(View view) {
        flightApp.sortLeastTime();
        Intent intent = new Intent(this, DisplayItinerary.class);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("itineraries", (ArrayList) flightApp.getItinerariesString());
        startActivity(intent);
    }
}
