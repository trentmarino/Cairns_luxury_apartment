package com.example.trentmarino.cairns_luxury_apartment.ListOfRooms;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trentmarino.cairns_luxury_apartment.DAO.BookingDB;
import com.example.trentmarino.cairns_luxury_apartment.NavagationSingleTon;
import com.example.trentmarino.cairns_luxury_apartment.R;
import com.example.trentmarino.cairns_luxury_apartment.RoomPage.SelectedRoom;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DisplayRoom extends AppCompatActivity {
    TextView checkin, checkout, noOfGuests;
    RequestQueue requestQueue;
    RoomListAdapter roomListAdapter;
    ListView roomListView;
    private Activity context;

    String showUrl = "http://54.206.36.198/cla_php_scripts/get_property_info_based_off_selected.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(NavagationSingleTon.getInstance().getPropertyLocationName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkin = (TextView) findViewById(R.id.checkin);
        checkout = (TextView) findViewById(R.id.checkout);
        noOfGuests = (TextView) findViewById(R.id.noOfGuests);
        checkin.setText(NavagationSingleTon.getInstance().getCheckIn());
        checkout.setText(NavagationSingleTon.getInstance().getCheckOut());
        noOfGuests.setText(String.valueOf(NavagationSingleTon.getInstance().getTotalNumGuests()));
        context = this;

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                showUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<String> url = new ArrayList<>();
                final ArrayList<String> room = new ArrayList<>();
                final ArrayList<String> prices = new ArrayList<>();
                final ArrayList<String> productID = new ArrayList<>();
                final  ArrayList<String> desc = new ArrayList<>();
                try {


                    JSONArray properties = response.getJSONArray("product");
                    JSONArray sortedJsonArray = new JSONArray();
                    List<JSONObject> jsonList = new ArrayList<JSONObject>();
                    for (int i = 0; i < properties.length(); i++) {
                        jsonList.add(properties.getJSONObject(i));
                    }


                    Collections.sort( jsonList, new Comparator<JSONObject>() {

                        public int compare(JSONObject a, JSONObject b) {
                            String valA = new String();
                            String valB = new String();

                            try {
                                valA = (String) a.get("deposit_amount_min");
                                valB = (String) b.get("deposit_amount_min");
                            }
                            catch (JSONException e) {
                                //do something
                            }
                            if(NavagationSingleTon.getInstance().getPriceOrder().equals("high")) {


                                return -valA.compareTo(valB);
                            }
                            return valA.compareTo(valB);


                        }
                    });

                    for (int i = 0; i < properties.length(); i++) {
                        sortedJsonArray.put(jsonList.get(i));
                    }

                    if(NavagationSingleTon.getInstance().getPriceOrder().equals("low")){

                    }
                    for (int i = 0; i < sortedJsonArray.length(); i++) {

                        JSONObject property = sortedJsonArray.getJSONObject(i);

                        if (Integer.parseInt(NavagationSingleTon.getInstance().getPropertyLocationID()) == (property.getInt("idproperty")) && (property.getInt("is_thumb") == 1) &&
                                property.getInt("max_pax")  >= NavagationSingleTon.getInstance().getTotalNumGuests()) {
                            productID.add(property.getString("idproduct"));
                            room.add(property.getString("product_name"));
                            prices.add(property.getString("deposit_amount_min"));
                            url.add(property.getString("image_url"));
                            desc.add(property.getString("roomdesc"));
                        }

                    }




                    roomListAdapter = new RoomListAdapter(context, room, prices, url, desc);
                    roomListView = (ListView) context.findViewById(R.id.listOfRooms);
                    roomListView.setAdapter(roomListAdapter);
                    roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Intent roomInfo = new Intent(context, SelectedRoom.class);
                            NavagationSingleTon.getInstance().setRoomName(room.get(position));
                            NavagationSingleTon.getInstance().setPrice(prices.get(position));
                            NavagationSingleTon.getInstance().setRoomNumber(productID.get(position));
                            NavagationSingleTon.getInstance().setNumberOfRooms(productID.size());
                            context.startActivity(roomInfo);


                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if( id == R.id.home){
            return true;
        }
        else if(id == R.id.priceLowToHigh){
            Log.i("priceLow", "chyea boi");
            NavagationSingleTon.getInstance().setPriceOrder("low");
            Intent intent = getIntent();
            finish();
            startActivity(intent);

        } else if (id == R.id.priceHighToLow){
            Log.i("priceHigh", "chyea boi");
            NavagationSingleTon.getInstance().setPriceOrder("high");
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}