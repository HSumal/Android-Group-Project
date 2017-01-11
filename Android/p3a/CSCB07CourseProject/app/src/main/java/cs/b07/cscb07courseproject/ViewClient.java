package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import flight.Itinerary;
import app.FlightApp;

public class ViewClient extends AppCompatActivity {
    FlightApp flightApp;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client);
        // pass the username and flightApp to this activity

        // personal and billing info text
        // LastName;FirstNames;Email;Address;CreditCardNumber;ExpiryDate
        flightApp = (FlightApp) getIntent().getSerializableExtra("flightApp");
        username = getIntent().getStringExtra("username");
        TextView lastName = (TextView) findViewById(R.id.last_name);
        TextView firstName = (TextView) findViewById(R.id.first_name);
        TextView address = (TextView) findViewById(R.id.address);
        TextView creditCard = (TextView) findViewById(R.id.credit_card);
        TextView expiryDate = (TextView) findViewById(R.id.expiry_date);
        List<String> info = flightApp.getClient(username).getClientInfo();
        lastName.setText(info.get(0));
        firstName.setText(info.get(1));
        address.setText(info.get(3));
        creditCard.setText(info.get(4));
        expiryDate.setText(info.get(5));


    }

    /**
     *
     * @param view
     */
    public void viewClientItinerary(View view){
        Intent intent = new Intent(this, DisplayItinerary.class);
        intent.putExtra("flightApp", flightApp);
        ArrayList<Itinerary> itineraries = (ArrayList<Itinerary>) flightApp.viewClientItinirary(username);
        intent.putExtra("itineraries", itineraries);
        startActivity(intent);
    }

    /**
     *
     * @param view
     */
    public void editProfile(View view){
        Intent intent = new Intent(this, EditClient.class);
        intent.putExtra("accountType", "admin");
        intent.putExtra("username", username);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("newUser", false);
        startActivity(intent);
    }

}
