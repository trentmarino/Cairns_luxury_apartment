package com.example.trentmarino.cairns_luxury_apartment.booking;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devmarvel.creditcardentry.library.CreditCard;
import com.example.trentmarino.cairns_luxury_apartment.DAO.BookingDB;
import com.example.trentmarino.cairns_luxury_apartment.MainActivity;
import com.example.trentmarino.cairns_luxury_apartment.NavagationSingleTon;
import com.example.trentmarino.cairns_luxury_apartment.R;

import java.util.Hashtable;
import java.util.Map;

public class ConfirmBooking extends AppCompatActivity {
    TextView bookingDetails;
    String insertURL = "http://192.168.0.4/CLA-CMS/web/postCustomer.php";
    RequestQueue requestQueue;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bookingDetails = (TextView) findViewById(R.id.ConfirmDetails);
        bookingDetails.append("Your Details\n");
        bookingDetails.append("Name: " + NavagationSingleTon.getInstance().getCustName());
        bookingDetails.append("\nEmail: " + NavagationSingleTon.getInstance().getCustEmail());
        bookingDetails.append("\nPhone: " + NavagationSingleTon.getInstance().getCustPhone());
        bookingDetails.append("\nAddress: " + NavagationSingleTon.getInstance().getCustAddress());
        bookingDetails.append("\n\n\n");
        bookingDetails.append("Booking Details\n");
        bookingDetails.append("Location: " + NavagationSingleTon.getInstance().getPropertyLocationName());
        bookingDetails.append("\nRoom type: " + NavagationSingleTon.getInstance().getRoomName());
        bookingDetails.append("\nnumber of Guests: " + NavagationSingleTon.getInstance().getTotalNumGuests());
        bookingDetails.append("\nPrice: " + NavagationSingleTon.getInstance().getPrice());
        bookingDetails.append("\nCheck In: " + NavagationSingleTon.getInstance().getCheckIn());
        bookingDetails.append("\nCheck Out: " + NavagationSingleTon.getInstance().getCheckOut());
        confirm = (Button) findViewById(R.id.confirmDetailsBtn);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StringRequest request = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("bob", "it did not work" + error);
                    }

                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> customer = new Hashtable<String, String>();
                        customer.put("name", NavagationSingleTon.getInstance().getCustName());
                        customer.put("email", NavagationSingleTon.getInstance().getCustEmail());
                        customer.put("phone", NavagationSingleTon.getInstance().getCustPhone());
                        customer.put("address", NavagationSingleTon.getInstance().getCustAddress());
                        customer.put("location", NavagationSingleTon.getInstance().getPropertyLocationName());
                        customer.put("roomtype", NavagationSingleTon.getInstance().getRoomName());
                        customer.put("numberofguests", NavagationSingleTon.getInstance().getTotalNumGuests() + "");
                        customer.put("pricepaid", NavagationSingleTon.getInstance().getPrice());
                        customer.put("checkin", NavagationSingleTon.getInstance().getCheckIn());
                        customer.put("checkout", NavagationSingleTon.getInstance().getCheckOut());
                        customer.put("creditcardNumber", NavagationSingleTon.getInstance().getCreditNumber());
                        customer.put("creditcardExpiry", NavagationSingleTon.getInstance().getCreditExpiry());
                        customer.put("creditcardCode", NavagationSingleTon.getInstance().getCreditCode());
                        Log.i("dfdg", customer.toString());

                        return customer;
                    }
                };
                Log.i("Request", request.toString());
                requestQueue.add(request);
                Intent goHome = new Intent(ConfirmBooking.this, MainActivity.class);
                startActivity(goHome);

            }
        });
    }

}
