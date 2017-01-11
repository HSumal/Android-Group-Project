package cs.b07.cscb07courseproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import app.FlightApp;



public class EditClient extends AppCompatActivity {
    private FlightApp flightApp;
    private String username;
    private String accountType;
    private boolean newUser;
    EditText firstName;
    EditText lastName;
    EditText address;
    EditText cardNumber;
    EditText expiryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        flightApp = (FlightApp)getIntent().getSerializableExtra("flightApp");
        username = getIntent().getStringExtra("username");
        accountType = getIntent().getStringExtra("accountType");
        newUser = getIntent().getBooleanExtra("newUser", false);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        address = (EditText) findViewById(R.id.address);
        cardNumber = (EditText) findViewById(R.id.cardNumber);
        expiryDate = (EditText) findViewById(R.id.expiryDate);

        if ((accountType.equals("client")) && !newUser){
            List<String> info = flightApp.viewClientInfo(username);
            firstName.setText(info.get(1), TextView.BufferType.EDITABLE);
            firstName.setText(info.get(1), TextView.BufferType.EDITABLE);
            lastName.setText(info.get(0), TextView.BufferType.EDITABLE);
            address.setText(info.get(3), TextView.BufferType.EDITABLE);
            cardNumber.setText(info.get(4), TextView.BufferType.EDITABLE);
            expiryDate.setText(info.get(5), TextView.BufferType.EDITABLE);
        }
    }

    /**
     *
     * @param view
     */
    public void editInfo(View view) {
        Intent intent;

        switch (accountType){
            case "client":
                intent = new Intent(this, ClientMain.class);
                break;
            case "admin":
                intent = new Intent(this, AdminMain.class);
                break;
            default:
                intent = new Intent(this, ClientMain.class);
                break;
        }


        if (firstName.getText().toString().trim().equals("")) {
            firstName.setError("error_field_required");
        } if  (lastName.getText().toString().trim().equals("")) {
            lastName.setError("error_field_required");
        } if (address.getText().toString().trim().equals("")) {
            address.setError("error_field_required");
        } if (cardNumber.getText().toString().trim().equals("")) {
            cardNumber.setError("error_field_required");
        } if (expiryDate.getText().toString().trim().equals("")) {
            expiryDate.setError("error_field_required");
        } else {
            if (newUser){
                String password = getIntent().getStringExtra("password");
                flightApp.addClient(username, password.toString(), lastName.getText().toString() + ";" + firstName.getText().toString() + ";" + username + ";" +
                        address.getText().toString() + ";" + cardNumber.getText().toString() + ";" + expiryDate.getText().toString());
                intent.putExtra("message", "Your account has been created");
            }
            else{
                flightApp.changeClientInfo(username, lastName.getText().toString() + ";" + firstName.getText().toString() + ";" + username + ";" +
                        address.getText().toString() + ";" + cardNumber.getText().toString() + ";" + expiryDate.getText().toString());
                intent.putExtra("message", "Your account has been modified");
            }
            intent.putExtra("flightApp", flightApp);
            startActivity(intent);
        }

    }
}
