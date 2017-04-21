package com.vaibhav.rssfeed;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
        protected String doInBackground(String... strings) { // This method is used to tell what code you want to run in the background
            Log.d(TAG, "doInBackground: starts with: " + strings[0]);
            return "doInBackground completed.";
        }

    }
}

