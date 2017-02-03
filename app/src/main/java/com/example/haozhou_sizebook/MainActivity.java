package com.example.haozhou_sizebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ListView oldRecord;
    private ArrayList<Person> personList;
    private ArrayAdapter<Person> adapter;
    private Person selectedPerson;
    private int totalNum;
    private TextView total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addButton = (Button) findViewById(R.id.AddNew);
        Button deleteButton = (Button) findViewById(R.id.Delete);
        oldRecord = (ListView) findViewById(R.id.oldRecord);
        total = (TextView) findViewById(R.id.totalAmount);

        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                adapter.remove(selectedPerson);
                adapter.notifyDataSetChanged();
                saveInFile();
                totalNum = personList.size();
                total.setText("The total amount of record is: "+totalNum);

            }
        });

        oldRecord.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {
                selectedPerson = (Person) adapter.getItemAtPosition(position);
            }
        });
    }
    public void add(View view) {
        Intent intent = new Intent(this, A_NewRecord.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        //tweetList = new ArrayList<Tweet>();
        adapter = new ArrayAdapter<Person>(this,
                R.layout.list_item, personList);
        oldRecord.setAdapter(adapter);

        totalNum = personList.size();
        total.setText(R.string.total + totalNum);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            personList = gson.fromJson(in, new TypeToken<ArrayList<Person>>() {}.getType());


            fis.close();



        } catch (FileNotFoundException e) {
            personList = new ArrayList<Person>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * save tweets into a specified file in JSON format.
     * @throws FileNotFoundException if file is not created.
     */

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();

            gson.toJson(personList, out);

            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}