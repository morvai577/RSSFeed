package com.vaibhav.rssfeed;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
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
//                InputStream inputStream = connection.getInputStream(); // Creating new inputStream
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream); // Creating new inputStreamReader that is used to create a new bufferedReader
//                BufferedReader reader = new BufferedReader(inputStreamReader); // Bufferes the input stream reader and the buffered reader is actually used to read the XML
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Creates a buffer to read the XML input

                int charsRead; // BufferedReader reads in characters not string, so we need to convert them to string.
                char[] inputBuffer = new char[500];
                while (true){ // Keeps running until the end of the input stream has been reached
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) { // if buffered reader's read method returns a value less than 0, this signals the end of the input stream
                        break; // break out of the loop
                    }
                    if (charsRead > 0) { // if buffered reader's read method returns a value greater than 0
                     xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead)); // Add characters read to string result
                    }
                }
                reader.close();
            } catch (MalformedURLException e) { // Used to handle any errors in line 58. This will be caught when there is a problem with the URL.
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage()); // error.getMessages gives us more info about the error.
            } catch (IOException e) { // handles any errors involving reading/writing. This is used to handle any errors in lines 60, 61, 63
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            }
        }

    }
}

