package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;

import app.FlightApp;
import flight.Flight;

public class InputFlight extends AppCompatActivity {
    FlightApp flightApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_flight);
        flightApp = (FlightApp) getIntent().getSerializableExtra("flightApp");
    }

    public void addFile(View view){
        EditText fileName = (EditText) findViewById(R.id.flightFile);
        String fileNameText = fileName.getText().toString();

        File directory = this.getApplicationContext().getFilesDir();
        File flightFile = new File(directory, fileNameText);
        try {
            flightApp.addFlightFile(flightFile.getPath());
        }
        catch (Exception e){}

        Intent intent = new Intent(this, AdminMain.class);
        intent.putExtra("flightApp", flightApp);
        startActivity(intent);
    }
}
