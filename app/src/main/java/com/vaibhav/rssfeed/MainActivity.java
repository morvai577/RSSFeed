package com.vaibhav.rssfeed;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.net.Proxy.Type.HTTP;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: starting Async task");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("URL goes here");
        Log.d(TAG, "onCreate: done");
    }

    // Following is a subclass, subclass is used because MainActivity is the only one that uses this class.
    // Async Task
    private class DownloadData extends AsyncTask<String, Void, String> { // First Param = RSS Feed URL (string), Second Param = Used if you want to display progrss bar, in this case not using, Third Param = Type of the result to return, XML in this case of type string
        private static final String TAG = "DownloadData";


        @Override
        protected void onPostExecute(String s) { // This is called to get back a alert when the job is done
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is: " + s);
        }

        @Override
        protected String doInBackground(String... strings) { // This method is used to tell what code you want to run in the background thread
            Log.d(TAG, "doInBackground: starts with: " + strings[0]);
            String rssFeed = downloadXML(strings[0]); // Call to downloadXML within doInBackground means this method will also run in the backgriund thread
            if(rssFeed == null){
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        private String downloadXML(String urlPath){
            StringBuilder xmlResult = new StringBuilder(); // Used to append data that we read into a single string

            try { // Always use try block when accessing external data as many things could go wrong.
                URL url = new URL(urlPath); // Used to create URL from parsed string
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Used to open the connection
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was " + response);
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
            } catch (MalformedURLException e) { // Used to handle any errors in line 58
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            }
        }

    }
}

