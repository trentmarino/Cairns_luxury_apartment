package com.example.trentmarino.cairns_luxury_apartment.RoomPage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trentmarino.cairns_luxury_apartment.NavagationSingleTon;
import com.example.trentmarino.cairns_luxury_apartment.R;
import com.example.trentmarino.cairns_luxury_apartment.booking.bookNow;
import com.example.trentmarino.cairns_luxury_apartment.pageContent.PageContent;
import com.example.trentmarino.cairns_luxury_apartment.pageContent.pageData;
import com.google.gson.JsonObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectedRoom extends AppCompatActivity {


    String propertyID;
    String roomSelected;
    int numberOfRooms;
    private TextView noContent;
    private String getUrl = "http://54.206.36.198/cla_php_scripts/get_page_info.php";
    RequestQueue requestQueue;
    ArrayList<String> pageTypes = new ArrayList<>();
    ArrayList<String> pageOrder = new ArrayList<>();
    ArrayList<String> contentArray = new ArrayList<>();
    ArrayList<PageContent> pageDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(NavagationSingleTon.getInstance().getRoomName());
        propertyID = NavagationSingleTon.getInstance().getPropertyLocationID();
        roomSelected = NavagationSingleTon.getInstance().getRoomNumber();
        numberOfRooms = NavagationSingleTon.getInstance().getNumberOfRooms();
        Log.i("Passed String", roomSelected);
        Log.i("paseed Number", propertyID);
        Log.i("number of rooms", "" + numberOfRooms);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        requestQueue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                getUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                try{
                    RecyclerView rvContacts = (RecyclerView) findViewById(R.id.roomPage);

                    JSONArray page = response.getJSONArray("page");
                    for (int i = 0; i < page.length() ; i++) {
                        JSONObject pageContent = page.getJSONObject(i);
                        if(NavagationSingleTon.getInstance().getRoomNumber().equals(pageContent.getString("idproduct"))){
                            String contentType = pageContent.getString("Info_type");
                            String content = pageContent.getString("content");
                            String contentOrder = pageContent.getString("content_order");
                            pageTypes.add(contentType);
                            contentArray.add(content);
                            pageOrder.add(contentOrder);
                        }

                    }

                    for (int j = 0; j < pageOrder.size(); j++) {
                        if(pageTypes.get(j).equals("5")){
                            JSONObject tourObject = new JSONObject(contentArray.get(j));
                            Log.i("tourObject", tourObject.toString());
                            ArrayList<String> tourArray = new ArrayList<>();
                            tourArray.add(0, tourObject.getString("title"));
                            tourArray.add(1, tourObject.getString("url"));
                            tourArray.add(2, tourObject.getString("image"));
                            for (int k = 0; k < tourArray.size() ; k++) {
                                Log.i("arrayOut", tourArray.get(k));
                                pageDatas = pageData.createPage(pageOrder.size(), tourArray.get(k));
                            }

                        }

                        pageDatas = pageData.createPage(pageOrder.size(), contentArray.get(j));

                    }

                    if (pageDatas != null) {
                        rvContacts.setHasFixedSize(true);
                        PageInfoAdapter adapter = new PageInfoAdapter(pageDatas, pageTypes, contentArray, pageOrder, getApplicationContext());
                        rvContacts.setAdapter(adapter);
                        rvContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    } else {
                        noContent = (TextView) findViewById(R.id.notContent);
                        noContent.setVisibility(View.VISIBLE);
                    }

                    System.out.println(pageTypes + " " + contentArray + " " + pageOrder);
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


    public void bookNow(View view) {

        Log.i("clciked", "has been clicked");
        Intent intent = new Intent(this, bookNow.class);
        startActivity(intent);


    }
}
