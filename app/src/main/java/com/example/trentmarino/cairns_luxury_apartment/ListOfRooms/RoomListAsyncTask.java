package com.example.trentmarino.cairns_luxury_apartment.ListOfRooms;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.trentmarino.cairns_luxury_apartment.NavagationSingleTon;
import com.example.trentmarino.cairns_luxury_apartment.R;
import com.example.trentmarino.cairns_luxury_apartment.RoomPage.SelectedRoom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by trentmarino on 20/05/16.
 */
public class RoomListAsyncTask extends AsyncTask<String, String, String> {
    ArrayList<String> retrievedRoom;
    RoomListAdapter roomListAdapter;
    ListView roomListView;
    private Activity context;
    private String priceOrder;

    public RoomListAsyncTask(Activity context) {
        this.context = context;
    }



    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            JSONObject parentObject = new JSONObject(finalJson);
            JSONArray parentArray = parentObject.getJSONArray("product");
            StringBuffer finalBuffer = new StringBuffer();
            ArrayList<String> property = new ArrayList<>();
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject finalObject = parentArray.getJSONObject(i);
                String propertyName;
                String propertyMinDeposit;
                String imageUrl;
                String productID;
                String totalNumGuests;
                if (Integer.parseInt(NavagationSingleTon.getInstance().getPropertyLocationID()) == (finalObject.getInt("idproperty")) && (finalObject.getInt("is_thumb") == 1) &&
                        finalObject.getInt("max_pax")  >= NavagationSingleTon.getInstance().getTotalNumGuests()) {
                    propertyName = finalObject.getString("product_name");
                    propertyMinDeposit = finalObject.getString("deposit_amount_min");
                    productID = finalObject.getString("idproduct");
                    imageUrl = finalObject.getString("image_url");
                    totalNumGuests = finalObject.getString("max_pax");
                    finalBuffer.append("(" + productID + ")" + "[" + propertyName + "]" + "," + "*" + propertyMinDeposit + "*" + ", {" + imageUrl + "}" + "|" + totalNumGuests + "|" + "\n");
                }
            }
            return finalBuffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String resulta) {
        super.onPostExecute(resulta);
        if (resulta.matches("")) {
            TextView noRooms = (TextView) context.findViewById(R.id.no_room);
            noRooms.setText("No room avalible");
        }
        Log.i("results", resulta);
        ArrayList<String> url = new ArrayList<>();
        ArrayList<String> room = new ArrayList<>();
        final ArrayList<String> prices = new ArrayList<>();
        final ArrayList<String> productID = new ArrayList<>();

        Pattern pID = Pattern.compile("\\(([^)]+)\\)");
        Matcher matchedID = pID.matcher(resulta);
        while (matchedID.find()) {
            productID.add(matchedID.group(1));
        }

        Pattern roomType = Pattern.compile("\\[([^}]*)\\]");
        Matcher convertedRoomType = roomType.matcher(resulta);
        while (convertedRoomType.find()) {
            room.add(convertedRoomType.group(1));
        }
        Pattern imageURL = Pattern.compile("\\{([^}]*)\\}");
        Matcher matchURL = imageURL.matcher(resulta);
        while (matchURL.find()) {
            url.add(matchURL.group(1));
        }

        Pattern price = Pattern.compile("\\*([^}]*)\\*");
        Matcher matched = price.matcher(resulta);
        while (matched.find()) {
            prices.add(matched.group(1));
        }


        Log.i("productID", " " + productID);

        Log.i("priceOrder", " " + NavagationSingleTon.getInstance().getPriceOrder());
        if(NavagationSingleTon.getInstance().getPriceOrder().equals("high")){
            Collections.sort(url, Collections.reverseOrder());

            Collections.sort(prices, Collections.reverseOrder());
            Collections.sort(room);

            Collections.sort(productID, Collections.reverseOrder());
            Log.i("sorted price", prices.toString());
            Log.i("names", room.toString());
        }else if(NavagationSingleTon.getInstance().getPriceOrder().equals("low")){

            //Collections.sort(url);

            Collections.sort(prices);
            Collections.sort(room, Collections.reverseOrder());

            Collections.sort(productID);
            Log.i("sorted price", prices.toString());
            Log.i("names", room.toString());
        }

        retrievedRoom = new ArrayList<>();
        roomListAdapter = new RoomListAdapter(context, room, prices, url);
        roomListView = (ListView) context.findViewById(R.id.listOfRooms);
        roomListView.setAdapter(roomListAdapter);
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent roomInfo = new Intent(context, SelectedRoom.class);
                NavagationSingleTon.getInstance().setRoomName(productID.get(position));
                NavagationSingleTon.getInstance().setNumberOfRooms(productID.size());
                context.startActivity(roomInfo);


            }
        });
    }
}

