package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import app.FlightApp;

public class MainActivity extends AppCompatActivity {
    private FlightApp flightApp;
    private final static String FLIGHT_FILE = "Flights.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        File directory = this.getApplicationContext().getFilesDir();
        File flightFile = new File(directory, FLIGHT_FILE);
        flightApp = new FlightApp(directory);
        try {
            flightApp.addFlightFile(flightFile.getPath());
        }
        catch (Exception e){}
    }

    /**
     *
     * @param view
     */
    public void sendMessage(View view) {
        EditText username = (EditText)findViewById(R.id.editText1);
        String usernameStr = username.getText().toString();
        EditText password = (EditText)findViewById(R.id.editText2);
        String passwordStr = password.getText().toString();


        boolean userExists = flightApp.hasClient(usernameStr);
       if (userExists) {
            boolean login = flightApp.login(usernameStr, passwordStr);

            if (login ) {

                //correct password
                Intent intent = new Intent(this, ClientMain.class);
                EditText editText = (EditText) findViewById(R.id.editText1); // editText1 is the area where I type
                String message = editText.getText().toString();
                intent.putExtra("username", usernameStr);
                intent.putExtra("flightApp", flightApp);
                intent.putExtra("message", "");
                intent.putExtra("newUser", false);
                startActivity(intent);
            } else {
                TextView textView = (TextView) findViewById(R.id.textView1);
                textView.setTextSize(14);
                textView.setText("Wrong Password");
            }
        }
        else if (usernameStr.equals("user") && passwordStr.equals("user")) {
        Intent intent = new Intent(this, AdminMain.class);
        intent.putExtra("flightApp", flightApp);
        startActivity(intent);
        }
        else{
            TextView textView = (TextView) findViewById(R.id.textView1);
            textView.setTextSize(14);
            textView.setText("User Does Not Exist");
        }
    }

    public void makeAccount (View view){
        EditText username = (EditText)findViewById(R.id.editText1);
        String usernameStr = username.getText().toString();
        EditText password = (EditText)findViewById(R.id.editText2);
        String passwordStr = password.getText().toString();

        if (usernameStr.equals("") || passwordStr.equals("")){
            TextView textView = (TextView) findViewById(R.id.textView1);
            textView.setTextSize(14);
            textView.setText("Please enter a username and password. Username must be your email.");
        }
        else{
            Intent intent = new Intent(this, EditClient.class);
            intent.putExtra("username", usernameStr);
            intent.putExtra("password", passwordStr);
            intent.putExtra("newUser", true);
            intent.putExtra("accountType", "client");
            intent.putExtra("flightApp", flightApp);
            startActivity(intent);
        }
    }
}
