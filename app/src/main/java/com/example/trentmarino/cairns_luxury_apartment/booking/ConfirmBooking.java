package com.example.trentmarino.cairns_luxury_apartment.booking;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
            bookingDetails.append("<b>Your Details:</b><br>");
            bookingDetails.append("<b>Name: </b>" + NavagationSingleTon.getInstance().getCustName() + "<br>");
            bookingDetails.append("<b>Email: </b>" + NavagationSingleTon.getInstance().getCustEmail() + "<br>");
            bookingDetails.append("<b>Phone: </b>" + NavagationSingleTon.getInstance().getCustPhone() + "<br>");
            bookingDetails.append("<b>Address: </b>" + NavagationSingleTon.getInstance().getCustAddress() + "<br>");
            bookingDetails.append("<br><br>");
            bookingDetails.append("<b>Booking Details:</b><br>");
            bookingDetails.append("<b>Location: </b>" + NavagationSingleTon.getInstance().getPropertyLocationName() + "<br>");
            bookingDetails.append("<b>Price: </b>$" + Integer.parseInt(NavagationSingleTon.getInstance().getPrice()) * date + ".00<br>");
            bookingDetails.append("<b>Room type: </b>" + NavagationSingleTon.getInstance().getRoomName() + "<br>");
            bookingDetails.append("<b>Number of Guests: </b>" + NavagationSingleTon.getInstance().getTotalNumGuests() + "<br>");
            bookingDetails.append("<b>Check In: </b>" + NavagationSingleTon.getInstance().getCheckIn() + "<br>");
            bookingDetails.append("<b>Check Out: </b>" + NavagationSingleTon.getInstance().getCheckOut() + "<br>");
            bookingDetails.append("The price above is the total cost of the booking including the deposit of 10%<br>");
            bookingDetails.append("<b>Your deposit for this booking is: </b>$" + (Integer.parseInt(NavagationSingleTon.getInstance().getPrice()) * date) / 10 + ".00<br>");
            Log.i("number", "" + Integer.parseInt(NavagationSingleTon.getInstance().getPrice()));
            bookingDetails.setText(Html.fromHtml(bookingDetails.getText().toString()));

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
