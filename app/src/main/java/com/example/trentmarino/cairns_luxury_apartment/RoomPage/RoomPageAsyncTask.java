package com.example.trentmarino.cairns_luxury_apartment.RoomPage;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.trentmarino.cairns_luxury_apartment.NavagationSingleTon;
import com.example.trentmarino.cairns_luxury_apartment.R;
import com.example.trentmarino.cairns_luxury_apartment.pageContent.PageContent;
import com.example.trentmarino.cairns_luxury_apartment.pageContent.pageData;

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

/**
 * Created by trentmarino on 11/05/16.
 */
public class RoomPageAsyncTask extends AsyncTask<String, String, String> {
    private TextView noContent;
    private Activity context;
    ArrayList<PageContent> pageDatas;
    private SwipeRefreshLayout swipeContainer;


    public RoomPageAsyncTask(Activity context) {
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
            JSONArray parentArray = parentObject.getJSONArray("page");
            StringBuffer finalBuffer = new StringBuffer();
            ArrayList<String> property = new ArrayList<>();
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject finalObject = parentArray.getJSONObject(i);
                String infoType;
                String content;
                String content_order;
                if (NavagationSingleTon.getInstance().getRoomName().equals(finalObject.getString("idproduct"))) {
                    infoType = finalObject.getString("Info_type");
                    content = finalObject.getString("content");
                    content_order = finalObject.getString("content_order");
                    finalBuffer.append("[" + infoType + "]" + "{" + content + "}" + "*" + content_order + "*" + "\n");
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
    protected void onPostExecute(String pageResult) {
        super.onPostExecute(pageResult);
        Log.i("page info", pageResult + " " + NavagationSingleTon.getInstance().getPropertyLocationID());

        final ArrayList<String> contentType = new ArrayList<>();
        final ArrayList<String> pageContent = new ArrayList<>();
        final ArrayList<String> pageOrder = new ArrayList<>();

        Pattern type = Pattern.compile("\\[([^}]*)\\]");
        Matcher convertedContentType = type.matcher(pageResult);
        while (convertedContentType.find()) {
            contentType.add(convertedContentType.group(1));
        }
        Pattern content = Pattern.compile("\\{([^}]*)\\}");
        Matcher convertedPageContent = content.matcher(pageResult);
        while (convertedPageContent.find()) {
            pageContent.add(convertedPageContent.group(1));
        }

        Pattern order = Pattern.compile("\\*([^}]*)\\*");
        Matcher convertedPageOrder = order.matcher(pageResult);
        while (convertedPageOrder.find()) {
            pageOrder.add(convertedPageOrder.group(1));
        }
        Log.i("layout/contentTypes", " " + contentType);
        Log.i("page Conent", " " + pageContent);
        Log.i("conent Order", " " + pageOrder);

        RecyclerView rvContacts = (RecyclerView) context.findViewById(R.id.roomPage);

        for (int i = 0; i < pageOrder.size(); i++) {
            Log.i("layout/contentTypes", " " + contentType.get(i));
            Log.i("page Conent", " " + pageContent.get(i));
            Log.i("conent Order", " " + pageOrder.get(i));
            pageDatas = pageData.createPage(pageOrder.size(), pageContent.get(i));

        }

        if (pageDatas != null) {
            rvContacts.setHasFixedSize(true);
            PageInfoAdapter adapter = new PageInfoAdapter(pageDatas, contentType, pageContent, pageOrder);
            rvContacts.setAdapter(adapter);
            rvContacts.setLayoutManager(new LinearLayoutManager(context));
        } else {
            noContent = (TextView) context.findViewById(R.id.notContent);
            noContent.setVisibility(View.VISIBLE);
        }


    }


}
