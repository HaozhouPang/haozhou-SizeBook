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
import android.widget.Toast;

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
    private int totalNum;
    private EditText total;
    private Person selectedPerson = new Person();

    private String selectedPersonName;
    private String selectedPersonDate;
    private String selectedPersonNeck;
    private String selectedPersonBust;
    private String selectedPersonChest;
    private String selectedPersonWaist;
    private String selectedPersonHip;
    private String selectedPersonInseam;
    private String selectedPersonComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addButton = (Button) findViewById(R.id.AddNew);
        Button deleteButton = (Button) findViewById(R.id.Delete);
        oldRecord = (ListView) findViewById(R.id.oldRecord);
        total = (EditText) findViewById(R.id.totalAmount);

        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (selectedPerson.getName() == null){
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Please select a record first!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    adapter.remove(selectedPerson);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                    selectedPerson = new Person();
                    totalNum = personList.size();
                    total.setText("The total amount of record is: " + totalNum);
                }
            }
        });



/*
        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (selectedPerson.getName() == null){
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Please select a record first!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    adapter.remove(selectedPerson);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                    selectedPerson = new Person();
                    totalNum = personList.size();
                    total.setText("The total amount of record is: " + totalNum);
                }
            }
        });
*/

        /**
         * the selected record will be stored in a Person object.
         */
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

    public void view_read(View view) {
        Intent intent = new Intent(this, View_Edit.class);
        Bundle extras = new Bundle();

        selectedPersonName = selectedPerson.getName();
        selectedPersonDate = selectedPerson.getDate();
        selectedPersonNeck = selectedPerson.getNeck();
        selectedPersonBust = selectedPerson.getBust();
        selectedPersonChest = selectedPerson.getChest();
        selectedPersonWaist = selectedPerson.getWaist();
        selectedPersonHip = selectedPerson.getHip();
        selectedPersonInseam = selectedPerson.getInseam();
        selectedPersonComment = selectedPerson.getComment();

        extras.putString("Person_name",selectedPersonName);
        extras.putString("Person_Date",selectedPersonDate);
        extras.putString("Person_Neck",selectedPersonNeck);
        extras.putString("Person_Bust",selectedPersonBust);
        extras.putString("Person_Chest",selectedPersonChest);
        extras.putString("Person_Waist",selectedPersonWaist);
        extras.putString("Person_Hip",selectedPersonHip);
        extras.putString("Person_Inseam",selectedPersonInseam);
        extras.putString("Person_Comment",selectedPersonComment);
        intent.putExtras(extras);
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
        total.setText("The total amount of record is: "+totalNum);
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
