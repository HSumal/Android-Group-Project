package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import app.FlightApp;

public class ViewAllClients extends AppCompatActivity {
    private FlightApp flightApp;
    private List<String> allClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_clients);
        //viewClient(view, view.getContext().toString().split(" ")[2]);
        flightApp = (FlightApp) getIntent().getSerializableExtra("flightApp");
        allClients = flightApp.getClients();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, allClients);
        ListView listView = (ListView) findViewById(R.id.client_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String flight = (String) adapterView.getItemAtPosition(i); // represents flight clicked on
                //String[] clientInfo = (oldAllClients.get(i).split(";"));
                //viewClient(view, clientInfo[i]);
                viewClient(view, allClients.get(i));
            }
        });
    }

    /**
     *
     * @param view
     * @param username
     */
    public void viewClient(View view, String username) {
        Intent intent = new Intent(this, ViewClient.class);
        intent.putExtra("flightApp", flightApp);
        intent.putExtra("username", username);
        startActivity(intent);
}}
