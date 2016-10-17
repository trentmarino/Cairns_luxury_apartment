package com.example.trentmarino.cairns_luxury_apartment.booking;

import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ConfirmBooking extends AppCompatActivity {
    TextView bookingDetails;
    String insertURL = "http://cla-cms.me/cla_php_scripts/postCustomer.php";
    public BookingDB bookingDB;
    RequestQueue requestQueue;
    Button confirm;
    SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
    CheckBox lateChekin;
    int late = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bookingDB = new BookingDB(this);

        setTitle("Booking Details");

        //Displaying the current details of the booking
        long date = 0;
        try {

            Date date1 = myFormat.parse(NavagationSingleTon.getInstance().getCheckIn());
            Date date2 = myFormat.parse(NavagationSingleTon.getInstance().getCheckOut());
            long diff = date2.getTime() - date1.getTime();
           date =  TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        } catch (ParseException e) {
            e.printStackTrace();
        }
            bookingDetails = (TextView) findViewById(R.id.ConfirmDetails);
            bookingDetails.append("Your Details:\n");
            bookingDetails.append("Name: " + NavagationSingleTon.getInstance().getCustName());
            bookingDetails.append("\nEmail: " + NavagationSingleTon.getInstance().getCustEmail());
            bookingDetails.append("\nPhone: " + NavagationSingleTon.getInstance().getCustPhone());
            bookingDetails.append("\nAddress: " + NavagationSingleTon.getInstance().getCustAddress());
            bookingDetails.append("\n\n");
            bookingDetails.append("Booking Details\n");
            bookingDetails.append("Location: " + NavagationSingleTon.getInstance().getPropertyLocationName());
            bookingDetails.append("\nPrice: $" + Integer.parseInt(NavagationSingleTon.getInstance().getPrice()) * date + ".00");
            bookingDetails.append("\nRoom type: " + NavagationSingleTon.getInstance().getRoomName());
            bookingDetails.append("\nnumber of Guests: " + NavagationSingleTon.getInstance().getTotalNumGuests());
            bookingDetails.append("\nCheck In: " + NavagationSingleTon.getInstance().getCheckIn());
            bookingDetails.append("\nCheck Out: " + NavagationSingleTon.getInstance().getCheckOut());
            bookingDetails.append("\nThe price above is the total cost of the booking including the deposit of 10%");
            bookingDetails.append("\nYour deposit for this booking is $" + (Integer.parseInt(NavagationSingleTon.getInstance().getPrice()) * date) / 10 + ".00");
            Log.i("number", "" + Integer.parseInt(NavagationSingleTon.getInstance().getPrice()));


        //defines the buttons and checkboxes
        confirm = (Button) findViewById(R.id.confirmDetailsBtn);
        lateChekin = (CheckBox) findViewById(R.id.lateCheckInCheck);

        lateChekin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lateChekin.isChecked()) {
                    Log.i("clicked", "checkbox has been clicked");
                    late = 1;
                    Log.i("clicked", "" + late);


                }
            }
        });


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final long finalDate = date;
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = getApplicationContext();
                CharSequence text = "Your booking is being confirmed.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                StringRequest request = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response", response);
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
                        customer.put("pricepaid", String.valueOf((Integer.parseInt(NavagationSingleTon.getInstance().getPrice()) * finalDate) / 10));
                        customer.put("checkin", NavagationSingleTon.getInstance().getCheckIn());
                        customer.put("checkout", NavagationSingleTon.getInstance().getCheckOut());
                        customer.put("creditcardNumber", NavagationSingleTon.getInstance().getBookingToken().getId());
                        customer.put("lateCheckIn", String.valueOf(late));
                        customer.put("propertyid", NavagationSingleTon.getInstance().getPropertyLocationID());
                        customer.put("onesignalid", NavagationSingleTon.getInstance().getOneSignalUserId());
                        Log.i("dfdg", NavagationSingleTon.getInstance().getBookingToken().toString());
                        return customer;

                    }
                };
                bookingDB.insertCursor(String.valueOf((Integer.parseInt(NavagationSingleTon.getInstance().getPrice()) * finalDate) / 10),
                        NavagationSingleTon.getInstance().getCheckIn(), NavagationSingleTon.getInstance().getCheckOut(),
                        String.valueOf(NavagationSingleTon.getInstance().getTotalNumGuests()),
                                NavagationSingleTon.getInstance().getRoomName());
                Log.i("Request", request.toString());
                requestQueue.add(request);
                Intent goHome = new Intent(ConfirmBooking.this, MainActivity.class);
                startActivity(goHome);

            }
        });
    }

}
