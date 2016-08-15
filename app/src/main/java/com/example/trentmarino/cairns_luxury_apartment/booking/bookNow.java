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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.devmarvel.creditcardentry.library.CreditCard;
import com.devmarvel.creditcardentry.library.CreditCardForm;
import com.example.trentmarino.cairns_luxury_apartment.DAO.BookingDB;
import com.example.trentmarino.cairns_luxury_apartment.NavagationSingleTon;
import com.example.trentmarino.cairns_luxury_apartment.R;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;



public class bookNow extends AppCompatActivity {
    TextView currentBooking;
    BookingDB bookingDB;
    ListView listView;
    EditText name, email, phone, address;
    Button submit;
    RequestQueue requestQueue;
    private CreditCardForm form;
    private LinearLayout linearLayout;
    boolean nameValid = false, emailValid = false, phoneValid = false, addressValid = false;


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
        currentBooking.append("Location: " + NavagationSingleTon.getInstance().getPropertyLocationName());
        currentBooking.append("\nRoom type: " + NavagationSingleTon.getInstance().getRoomName());
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

                if (name.getText().toString().isEmpty()) {
                    name.setError("must add a name");
                } else {
                    NavagationSingleTon.getInstance().setCustName(name.getText().toString());
                    nameValid = true;
                }
                if (email.getText().toString().isEmpty()) {
                    email.setError("Must enter a valid Email");
                } else {
                    NavagationSingleTon.getInstance().setCustEmail(email.getText().toString());
                    emailValid = true;
                }
                if (phone.getText().toString().isEmpty()) {
                    phone.setError("Must enter phone number");
                } else {
                    NavagationSingleTon.getInstance().setCustPhone(phone.getText().toString());
                    phoneValid = true;
                }
                if (address.getText().toString().isEmpty()) {
                    address.setError("Must enter an Address");
                } else {
                    NavagationSingleTon.getInstance().setCustAddress(address.getText().toString());
                    addressValid = true;
                }
                CreditCard cardForm = form.getCreditCard();
                Card card = new Card(cardForm.getCardNumber(),
                        cardForm.getExpMonth(),
                        cardForm.getExpYear(),
                        cardForm.getSecurityCode());
                Log.i("token Card", card.toString());
                if ( card.validateCard() ) {
                    Stripe stripe = null;
                    try {
                        stripe = new Stripe("pk_test_d40BxHK0kTqMYvq6jyz87TOC");

                    stripe.createToken(
                            card,
                            new TokenCallback() {
                                public void onSuccess(Token token) {
                                    // Send token to your server
                                    Log.i("token", token.toString());
                                    NavagationSingleTon.getInstance().setBookingToken(token);
                                }
                                public void onError(Exception error) {
                                    // Show localized error message
                                    Log.i("token", error.toString());

                                }
                            }
                    );
                    } catch (AuthenticationException e) {
                        e.printStackTrace();
                    }


                }
                if(nameValid && emailValid && phoneValid && addressValid) {
                    Intent intent = new Intent(bookNow.this, ConfirmBooking.class);
                    startActivity(intent);
                }


            }
        });


    }

}
