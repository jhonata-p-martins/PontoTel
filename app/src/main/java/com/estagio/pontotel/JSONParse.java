package com.estagio.pontotel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jhonata on 12/15/15.
 */
public class JSONParse
{
    private String jsonString;

    //JSON nodes names
    private final String TAG_DATA = "data";
    private final String TAG_ID = "id";
    private final String TAG_NAME = "name";
    private final String TAG_PWD = "pwd";

    public JSONParse(String json)
    {
        this.jsonString = json;
    }

    public ArrayList<String> dataArray()
    {
        ArrayList<String> allData = new ArrayList<>();
        try
        {
            JSONObject jsonObj = new JSONObject(this.jsonString);

            JSONArray data = jsonObj.getJSONArray(TAG_DATA);

            // looping through all data
            for (int i = 0; i < data.length(); i++)
            {
                JSONObject obj = data.getJSONObject(i);

                String id = obj.getString(TAG_ID);
                String name = obj.getString(TAG_NAME);
                String pwd = obj.getString(TAG_PWD);

                //creating the array to be printed
                allData.add("ID: " + id + "\nNAME: " + name + "\nPWD: " + pwd);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return allData;
    }
}



