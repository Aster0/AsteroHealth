package me.astero.asterohealth.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import me.astero.asterohealth.R;
import me.astero.asterohealth.database.dao.HealthDao;
import me.astero.asterohealth.database.objects.GridData;
import me.astero.asterohealth.database.objects.NotesData;

@Database(entities = {GridData.class, NotesData.class}, version = 1)

public abstract class HealthDatabase extends RoomDatabase {

    public abstract HealthDao healthDao();

    private static HealthDatabase healthDatabase = null;


    public static HealthDatabase getInstance(Context context)
    {
        if(healthDatabase == null)
        {
            healthDatabase = Room.databaseBuilder(context,
                    HealthDatabase.class, "database-health").allowMainThreadQueries().build();


            readJsonFile(context);
        }


        return healthDatabase;
    }

    private static void loadJsonData(JSONObject jsonObj, Context context) throws JSONException {

        JSONArray gridArrays = jsonObj.getJSONArray("grids");








        for(int i = 0; i < gridArrays.length(); i++)
        {



            String currentKey =  new JSONObject(gridArrays.get(i).toString()).keys().next();

            JSONObject currentArrayObj = new JSONObject(gridArrays.get(i).toString());

            JSONArray currentArray = currentArrayObj.getJSONArray(currentKey);



            for(int x = 0; x < currentArray.length(); x++)
            {
                JSONObject currentObject = new JSONObject(currentArray.get(x).toString());
                System.out.println(currentObject + "JSON");

                int duration = 0;

                String image = "";
                String icon = "";

                try
                {
                    duration = currentObject.getInt("duration");

                    image = currentObject.getString("image");

                }
                catch(JSONException e)
                {

                }

                try
                {

                    icon = currentObject.getString("icon");

                }
                catch(JSONException e)
                {

                }

                GridData gridData = new GridData(currentObject.getString("name"),
                        currentObject.getString("metrics"), currentObject.getInt("value"),
                        duration, currentKey, image, icon);


                getInstance(context).healthDao().insert(gridData);
            }








        }


    }

    private static void readJsonFile(Context context)
    {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {




                    StringBuilder builder = new StringBuilder();
                    InputStream in = context.getResources().openRawResource(R.raw.health);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }



                    JSONObject jsonObj = new JSONObject(builder.toString());

                    loadJsonData(jsonObj, context);







                }
                catch(JSONException | IOException e) {

                    System.out.println(e);
                }
            }
        });

        t1.start();


    }
}