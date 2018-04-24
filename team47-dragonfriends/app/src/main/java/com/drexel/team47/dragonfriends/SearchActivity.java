package com.drexel.team47.dragonfriends;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

//import com.google.auth.oauth2.GoogleCredentials;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Dictionary;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private TextView className;
    private TextView secNum;
    private TextView crnNum;
    private TextView classType;
    private TextView instructor;
    private TextView dayTime;


    private FloatingActionButton addClass;

    private SearchView classSearch;

    private JSONObject res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        classSearch = (SearchView) findViewById(R.id.classSearchView);

        className = (TextView) findViewById(R.id.class_name);
        secNum = (TextView) findViewById(R.id.sec_num);
        crnNum = (TextView) findViewById(R.id.crn_num);
        classType = (TextView) findViewById(R.id.instr_type);
        instructor = (TextView) findViewById(R.id.instr_name);
        dayTime = (TextView) findViewById(R.id.day_time);

        addClass = (FloatingActionButton) findViewById(R.id.add_class);

        addClass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                System.out.println("Added class " + res);
                //replace with the backend function to add the class to the user's info in database
            }


        });

        //Listen to query text
        classSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Do something when text is submitted


                AsyncHttpClient client = new AsyncHttpClient();

                //Add parameters for POST request
                RequestParams params = new RequestParams("crn", s); //create a key value pair of 'crn': s
                System.out.println("logging");
                String url = "https://dragonfriends-eb4fc.firebaseapp.com/classByCrn";

                System.out.println("url" + url);


                client.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        try {

                            res= new JSONObject(new String(responseBody));
                            System.out.println(res);
                            displayData(res);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        System.out.println("Error getting class by crn");
                        Log.e("error", "get class by crn", error);
                    }
                });



                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //do something when text changes
                return false;
            }
        });

    }

    /**
     * private TextView className;
     private TextView secNum;
     private TextView crnNum;
     private TextView classType;
     private TextView instructor;
     private TextView dayTime;
     * @param info
     */
    private void displayData(JSONObject info){
        try {
            String name = info.getString("courseTitle");
            String sec = info.getString("sec");
            String crnN = info.getString("crn");
            String classT = info.getString("instrType");
            String instr = info.getString("instructor");
            String dayT = info.getString("dayTime");
            className.setText(name);
            secNum.setText(sec);
            crnNum.setText(crnN);
            classType.setText(classT);
            instructor.setText(instr);
            dayTime.setText(dayT);
            addClass.setVisibility(View.VISIBLE);
            addClass.setClickable(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
