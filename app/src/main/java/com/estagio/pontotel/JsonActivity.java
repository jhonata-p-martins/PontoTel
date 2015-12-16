package com.estagio.pontotel;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class JsonActivity extends AppCompatActivity
{
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        URLActivity parse = new URLActivity();
        parse.execute();

    }


    private class URLActivity extends AsyncTask<String, String, String>
    {
        private ProgressDialog pDialog;
        private String link = "https://s3-sa-east-1.amazonaws.com/pontotel-docs/data.json";

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialog = new ProgressDialog(JsonActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args)
        {
            try
            {
                URL url = new URL(link);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                InputStream is = conn.getInputStream();
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                {
                    responseStrBuilder.append(inputStr);
                }
                Log.d("doInBackground", responseStrBuilder.toString());

                return responseStrBuilder.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s)
        {
            pDialog.dismiss();
            try
            {
//                Log.d("AsyncTask", s);
                lvItems.setAdapter(itemsAdapter);
                JSONParse jp = new JSONParse(s);
                for(int i = 0; i < jp.dataArray().size(); i++)
                {
                    items.add(jp.dataArray().get(i));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }
}
