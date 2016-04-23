package com.example.trentmarino.cairns_luxury_apartment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisplayRoom extends AppCompatActivity {
    private MenuItem item;
    String propertyID;
    String roomSelected;
    TextView testRoomSelected, display;
    private BookingDB bookingDB;
    ArrayList<String> retrievedRoom;
    CustomRoomAdapter customRoomAdapter;
    ListView roomListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        testRoomSelected = (TextView) findViewById(R.id.textView3);
//        display = (TextView) findViewById(R.id.displayer);
        roomSelected = getIntent().getStringExtra("location");
        propertyID = getIntent().getStringExtra("propertyID");
        Log.i("Passed String", roomSelected);
        Log.i("paseed Number", propertyID);
        testRoomSelected.setText(roomSelected);

        new JSONTask().execute(" https://whispering-tundra-59848.herokuapp.com/get_property_info_based_off_selected.php");
        bookingDB = new BookingDB(this);
        Cursor cursor = bookingDB.getAllCursor();
        Log.i("returned ", cursor.toString());

        ListView listView = (ListView) findViewById(R.id.listView);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.item_view, cursor,
                new String[]{"check_in", "check_out"},
                new int[]{R.id.CheckIn, R.id.CheckOut},
                0);
        listView.setAdapter(adapter);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);


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

        return super.onOptionsItemSelected(item);
    }


    public class JSONTask extends AsyncTask<String, String, String> {

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
                    if (Integer.parseInt(propertyID) == (finalObject.getInt("idproperty")) && (finalObject.getInt("is_thumb") == 1)) {
                        propertyName = finalObject.getString("product_name");
                        propertyMinDeposit = finalObject.getString("deposit_amount_min");
                        imageUrl = finalObject.getString("image_url");
                        finalBuffer.append("[" + propertyName + "]" + "," + "*" + propertyMinDeposit + "*" + ", {" + imageUrl + "}" + "\n");
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
                TextView noRooms = (TextView) findViewById(R.id.no_room);
                noRooms.setText("No room avalible");
            }
            Log.i("dsfdfgd", resulta);
            ArrayList<String> url = new ArrayList<>();
            ArrayList<String> room = new ArrayList<>();
            ArrayList<String> prices = new ArrayList<>();
            Pattern roomType = Pattern.compile("\\[([^}]*)\\]");
            Matcher convertedRoomType = roomType.matcher(resulta);
            while (convertedRoomType.find()) {
                room.add(convertedRoomType.group(1));
            }

            Pattern p = Pattern.compile("\\{([^}]*)\\}");
            Matcher m = p.matcher(resulta);
            while (m.find()) {
                url.add(m.group(1));
            }

            Pattern price = Pattern.compile("\\*([^}]*)\\*");
            Matcher matched = price.matcher(resulta);
            while (matched.find()) {
                prices.add(matched.group(1));
            }
            String nameo = resulta.replaceAll("\\D+", ",");


            Log.i("namoe", nameo);


            retrievedRoom = new ArrayList<>();

            customRoomAdapter = new CustomRoomAdapter(DisplayRoom.this, room, prices, url);
            roomListView = (ListView) findViewById(R.id.listOfRooms);
            roomListView.setAdapter(customRoomAdapter);
            roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Toast.makeText(DisplayRoom.this,"clickjewd",Toast.LENGTH_SHORT).show();
//                    Intent info = new Intent(DisplayRoom.this,information.class);
//                    startActivity(info);
                }


            });


        }

    }
}


