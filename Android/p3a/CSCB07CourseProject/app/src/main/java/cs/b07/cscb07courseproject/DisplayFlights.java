package cs.b07.cscb07courseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.Toast;
import java.util.ArrayList;

import app.FlightApp;
import flight.Flight;
import android.content.Intent;

public class DisplayFlights extends AppCompatActivity {

    private FlightApp flightApp;
    private ArrayList<String> flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_flights);
        flightApp = (FlightApp) getIntent().getSerializableExtra("flightApp");
        flights = (ArrayList) getIntent().getSerializableExtra("flights");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, flights);
        ListView listView = (ListView) findViewById(R.id.mobile_list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String flight = (String) adapterView.getItemAtPosition(i); // represents flight clicked on
        }
    });

    }

    /**
     *
     * @param view
     */
    public void greatestCost(View view) {
        flightApp.sortFlightGreatestCost();
        Intent intent = new Intent(this, DisplayFlights.class);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("flights", (ArrayList) flightApp.getFlightsString());
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void leastCost(View view) {
        flightApp.sortFlightLeastCost();
        Intent intent = new Intent(this, DisplayFlights.class);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("flights", (ArrayList) flightApp.getFlightsString());
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void greatestTime(View view) {
        flightApp.sortFlightGreatestTime();
        Intent intent = new Intent(this, DisplayFlights.class);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("flights", (ArrayList) flightApp.getFlightsString());
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void leastTime(View view) {
        flightApp.sortFlightLeastTime();
        Intent intent = new Intent(this, DisplayFlights.class);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("flights", (ArrayList) flightApp.getFlightsString());
        startActivity(intent);
    }

}
