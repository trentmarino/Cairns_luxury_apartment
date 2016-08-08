package com.example.trentmarino.cairns_luxury_apartment.booking;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devmarvel.creditcardentry.library.CreditCard;
import com.devmarvel.creditcardentry.library.CreditCardForm;
import com.example.trentmarino.cairns_luxury_apartment.DAO.BookingDB;
import com.example.trentmarino.cairns_luxury_apartment.NavagationSingleTon;
import com.example.trentmarino.cairns_luxury_apartment.R;

import java.util.Hashtable;
import java.util.Map;

public class bookNow extends AppCompatActivity {
    TextView currentBooking;
    BookingDB bookingDB;
    ListView listView;
    EditText name,email,phone,address;
    Button submit;
    RequestQueue requestQueue;
    private CreditCardForm form;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        bookingDB = new BookingDB(this);
        Cursor cursor = bookingDB.getAllCursor();
        currentBooking = (TextView) findViewById(R.id.currentBooking);
        currentBooking.append("Location: "+ NavagationSingleTon.getInstance().getPropertyLocationName());
        currentBooking.append("\nRoom type: "+ NavagationSingleTon.getInstance().getRoomName());
        currentBooking.append("\nnumber of Guests: " + NavagationSingleTon.getInstance().getTotalNumGuests());
        currentBooking.append("\nPrice: " + NavagationSingleTon.getInstance().getPrice());
        listView = (ListView) findViewById(R.id.currentDetails);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.item_view, cursor,
                new String[]{"check_in", "check_out", "no_of_guests"},
                new int[]{R.id.CheckIn, R.id.CheckOut, R.id.noGuests},
                0);
        listView.setAdapter(adapter);
        name = (EditText) findViewById(R.id.custName);
        email = (EditText) findViewById(R.id.custEmail);
        phone = (EditText) findViewById(R.id.custPhone);
        address = (EditText) findViewById(R.id.custAddressStreet);
        submit = (Button) findViewById(R.id.submit);
        form = (CreditCardForm) findViewById(R.id.credit_card_form);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavagationSingleTon.getInstance().setCustName(name.getText().toString());
                NavagationSingleTon.getInstance().setCustEmail(email.getText().toString());
                NavagationSingleTon.getInstance().setCustPhone(phone.getText().toString());
                NavagationSingleTon.getInstance().setCustAddress(address.getText().toString());
                if(form.isCreditCardValid())
                {
                    CreditCard card = form.getCreditCard();
                    Log.i("passws",card.toString());
                    NavagationSingleTon.getInstance().setCreditNumber(card.getCardNumber());
                    NavagationSingleTon.getInstance().setCreditExpiry(card.getExpDate());
                    NavagationSingleTon.getInstance().setCreditCode(card.getSecurityCode());
                }
//                else
//                {
//                    //Alert Credit card invalid
//                }
                Intent intent = new Intent(bookNow.this,ConfirmBooking.class);
                startActivity(intent);


            }
        });

    }

}
