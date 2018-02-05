package subbook.subbook;

import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

/*
* minci-subook for CMPUT 301 Assignment 1
*
* Author: Minci Zhou
* ccid: minci
* date: 20180205
* sid: 1434760
*
* References:
* https://stackoverflow.com/questions/26703691/android-return-object-as-a-activity-result
* https://developer.android.com/training/basics/intents/result.html
*
* https://github.com/tlafranc/tlafranc-SubBook; license: GPLv3.0
* Author:tlafranc;
* access date:20170205;
*
*/

/*
* SubscriptionList class
* The main activity, it shows the total monthly charge, list of subscriptions in a list view,
* manages adding/editing/deleting subscriptions from the subscription list
* */

public class SubscriptionList extends AppCompatActivity {

    private static String FILENAME = "subbook.sav";
    private ArrayList<Subscription> subList;
    private ArrayAdapter<Subscription> subArrayAdapter;
    private float totalCharge;
    private TextView priceView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_list);

        priceView = findViewById(R.id.textView5);
        /* try to load data from file */
        readSubFromFile(FILENAME);

        /* display monthly charge on screen */
        showChargeView();

        subArrayAdapter = new ArrayAdapter<Subscription>(this, android.R.layout.simple_list_item_1, subList);
        final ListView listView = findViewById(R.id.listView);
        listView.setAdapter(subArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Subscription sub = (Subscription) listView.getAdapter().getItem(position);
                System.out.println(sub.toString());
                /*TODO: get back to EditSub*/
                Intent intent = new Intent(SubscriptionList.this, EditSub.class);
                intent.putExtra("sub", sub);
                intent.putExtra("pos", position);
                startActivityForResult(intent, 0);
            }
        });

        /*defines action after pressing "ADD..." button on main activity */
        Button addButton = (Button) findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SubscriptionList.this, AddSub.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    /* this method tries to read given file contains subscriptions;
    *  if such file is not in the directory then it will automatically create one */
    public void readSubFromFile(String FILENAME) {
        try {
            FileInputStream fileInputStream = openFileInput(FILENAME);
            BufferedReader buf = new BufferedReader(new InputStreamReader(fileInputStream));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subList = gson.fromJson(buf, listType);
        }
        catch (FileNotFoundException e) {
            subList = new ArrayList<Subscription>();
        }
    }
    /* this method tries to save subscription to file, handles all exception*/
    public void saveSubToFile(String FILENAME) {

        Gson gson = new Gson();

        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter buf = new BufferedWriter(new OutputStreamWriter(fos));
            gson.toJson(this.subList, buf);
            buf.flush();
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    // Reference: https://stackoverflow.com/questions/26703691/android-return-object-as-a-activity-result
    //            https://developer.android.com/training/basics/intents/result.html
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int position;

        switch (resultCode) {
            // successfully added subscription
            case 0:
                Subscription resultSub = (Subscription)data.getExtras().getSerializable("newSub");
                addSub(resultSub);
                break;
            case 1:
                break;
            case 2:
                break;
            // successfully deleted sub at given position
            case 3:
                position = data.getExtras().getInt("pos");
                subList.remove(position);
                break;
            // successfully edited sub at given position
            case 4:
                position = data.getExtras().getInt("pos");
                Subscription newSub = (Subscription)data.getExtras().getSerializable("newSub");
                subList.set(position, newSub);
        }

        subArrayAdapter.notifyDataSetChanged();
        showChargeView();
        saveSubToFile(FILENAME);
    }

    /* add single subscription to file*/
    public void addSub(Subscription subscription) {
        this.subList.add(subscription);
        subArrayAdapter.notifyDataSetChanged();
        saveSubToFile(FILENAME);
    }

    /* show monthly change on screen*/
    public void showChargeView() {
        float totalCharge = 0;
        for (Subscription s : subList) {
            totalCharge = totalCharge + s.getPrice();
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        this.priceView.setText(df.format(totalCharge));
    }
}
