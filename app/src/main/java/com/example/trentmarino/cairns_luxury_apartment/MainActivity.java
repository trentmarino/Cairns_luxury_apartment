package com.example.trentmarino.cairns_luxury_apartment;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trentmarino.cairns_luxury_apartment.DAO.BookingDB;
import com.example.trentmarino.cairns_luxury_apartment.ListOfRooms.DisplayRoom;
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
import java.util.Calendar;
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView result, checkIn, checkOut;
    private Button gotToNewPage;
    Spinner locations;
    private MenuItem item;
    private CalendarView calendarView;
    private int yr, mon, dy;
    private Calendar selectedDate;
    private String finalCheckIn;
    private String finalCheckOut;
    private CalendarView calendarView2;
    public BookingDB bookingDB;
    public int noOfGuests;
    public String noAdult = "5";
    public String noChild = "5";
    public EditText adult;
    public EditText child;
    private NavagationSingleTon navagationSingleTon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Calendar c = Calendar.getInstance();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        checkIn = (TextView) findViewById(R.id.Check_In);
        checkOut = (TextView) findViewById(R.id.Check_Out);
        adult = (EditText) findViewById(R.id.no_adult);
        child = (EditText) findViewById(R.id.no_child);


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        ImageLoader.getInstance().displayImage("https://newevolutiondesigns.com/images/freebies/city-wallpaper-47.jpg", imageView); // Default options will be used


        new JSONTask().execute("http://cla-cms.me/cla_php_scripts/get_property_names.php");

        yr = c.get(Calendar.YEAR);
        mon = c.get(Calendar.MONTH);
        dy = c.get(Calendar.DAY_OF_MONTH);
        ImageButton datePickerButton = (ImageButton) findViewById(R.id.date_picker_button);
        ImageButton datePickerButton2 = (ImageButton) findViewById(R.id.imageButton2);
        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView2 = (CalendarView) findViewById(R.id.calendar_view2);
        finalCheckIn = dy + "/" + (mon+1) + "/" + yr;
        finalCheckOut = (dy+1) + "/" + (mon+1) + "/" + yr;
        checkIn.setText(finalCheckIn);
        checkOut.setText(finalCheckOut);
        bookingDB = new BookingDB(this);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, dateListener, yr, mon, dy).show();
            }
        });
        datePickerButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, dateListener2, yr, mon, dy).show();
            }
        });

        navagationSingleTon = new NavagationSingleTon();

    }
    private DatePickerDialog.OnDateSetListener dateListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    selectedDate = Calendar.getInstance();
                    yr = year;
                    mon = monthOfYear;
                    dy = dayOfMonth;
                    selectedDate.set(yr, mon, dy);
                    calendarView.setDate(selectedDate.getTimeInMillis());
                    finalCheckIn = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                    if (checkOut.getText().toString().equals("Check-Out")){
                        checkIn.setText(finalCheckIn);
                        checkOut.setText((dayOfMonth+1) + "/" + (monthOfYear+1) + "/" + year);
                        finalCheckOut = (dayOfMonth+1) + "/" + (monthOfYear+1) + "/" + year;
                    }else {
                        checkIn.setText(finalCheckIn);
                    }
                    Toast.makeText(getApplicationContext(), "Selected date is " + dayOfMonth + "/" + (monthOfYear+ 1) + "/" +
                            year, Toast.LENGTH_SHORT).show();
                }
            };
    private DatePickerDialog.OnDateSetListener dateListener2 =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    selectedDate = Calendar.getInstance();
                    yr = year;
                    mon = monthOfYear;
                    dy = dayOfMonth;
                    selectedDate.set(yr, mon, dy);
                    calendarView2.setDate(selectedDate.getTimeInMillis());
                    finalCheckOut = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                    checkOut.setText(finalCheckOut);
                    Toast.makeText(getApplicationContext(), "Selected date is " + dayOfMonth + "/" + (monthOfYear+ 1) + "/" +
                            year, Toast.LENGTH_SHORT).show();
                }
            };
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        this.item = item;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.button2) {
            Intent intent = new Intent(this, MapsLocation.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_manage) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public class JSONTask extends AsyncTask<String,String,String > {
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
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    String propertyName = finalObject.getString("property_name");
                    int propertyId = finalObject.getInt("idproperty");
                    finalBuffer.append(propertyId+ "," + propertyName + "\n");
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
            Log.i("Result from the DB", resulta);
            final ArrayList<String> propertyLocation = new ArrayList<>();
            final ArrayList<String> ids = new ArrayList<>();
            String name = resulta.replaceAll("[\\\\d*[,0-9]?\\\\d+]", "");
            Log.i("namoe", name);
            String[] separated = name.split("\n");
            String removedAllLetters = resulta.replaceAll("\\D+", ",");
            String[] propertID = removedAllLetters.split(",");
            Log.i("removed All letters", " " + removedAllLetters.toString());
            for(int j = 0; j< propertID.length;j++){
                ids.add(propertID[j]);
            }
            Log.i("printOut", " " + ids);
            for (int i = 0; i < separated.length; i++) {
                propertyLocation.add(separated[i]);
            }
            locations = (Spinner) findViewById(R.id.spinner1);
            locations.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, propertyLocation));
            locations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0,
                                           View arg1,final int position, long arg3) {
                    final Intent local = new Intent(MainActivity.this, DisplayRoom.class);
                    gotToNewPage = (Button) findViewById(R.id.button);
                    gotToNewPage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            noAdult = adult.getText().toString();
                            noChild = child.getText().toString();
                            int numAdult = Integer.parseInt(noAdult);
                            int numChild = Integer.parseInt(noChild);
                            noOfGuests = numAdult + numChild;
                            bookingDB.updateBooking("1", finalCheckIn, finalCheckOut, String.valueOf(noOfGuests));
                            NavagationSingleTon.getInstance().setPropertyLocationID(ids.get(position));
                            NavagationSingleTon.getInstance().setPropertyLocationName(propertyLocation.get(position));
//                            local.putExtra("location", propertyLocation.get(position));
//                            local.putExtra("propertyID", ids.get(position));
//                            setResult(RESULT_OK, local);
                            startActivity(local);
                        }
                    });
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }
}