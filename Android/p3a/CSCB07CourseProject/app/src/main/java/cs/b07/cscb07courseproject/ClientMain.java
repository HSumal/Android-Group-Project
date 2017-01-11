package cs.b07.cscb07courseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

import java.io.File;
import java.util.ArrayList;

import app.FlightApp;
import users.User;

public class ClientMain extends AppCompatActivity {
    public static final String CLIENT_FILE = "Clients";

    private FlightApp flightApp;
    String username;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        flightApp = (FlightApp) getIntent().getSerializableExtra("flightApp");
        username = getIntent().getStringExtra("username");
        message = getIntent().getStringExtra("message");

        // ---------- attempts to create new file ----------------
        //File clientFile = new File(this.getApplicationContext().getFilesDir(), CLIENT_FILE);

        TextView textView = (TextView) findViewById(R.id.textView1);
        textView.setTextSize(14);
        textView.setText(message);

        //ViewGroup layout = (ViewGroup) findViewById(R.id.activity_client_main);
        //layout.addView(textView);
    }

    /**
     *
     * @param view
     */
    public void editProfile(View view){
        Intent intent = new Intent(this, EditClient.class);
        intent.putExtra("accountType", "client");
        intent.putExtra("username", username);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("newUser", false);
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void viewItinerary(View view){
        Intent intent = new Intent(this, DisplayItinerary.class);
        intent.putExtra("flightApp", flightApp);
        ArrayList<String> itineraries = (ArrayList) flightApp.viewCurrentItinerariesString();
        intent.putExtra("itineraries", itineraries);
        startActivity(intent);
    }

    /**
     * Method to go to searchFlights window/class/tab/thingy.
     * @param view
     */
    public void searchFlights(View view) {
        Intent intent = new Intent(this, SearchFlights.class);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void searchItinerary(View view) {
        Intent intent = new Intent(this, SearchItinerary.class);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
