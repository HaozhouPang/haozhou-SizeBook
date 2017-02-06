package com.example.haozhou_sizebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

/**
 * The is the Main activity.
 */
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
        //Button addButton = (Button) findViewById(R.id.AddNew);
        Button deleteButton = (Button) findViewById(R.id.Delete);
        oldRecord = (ListView) findViewById(R.id.oldRecord);
        total = (TextView) findViewById(R.id.totalAmount);
        selectedPerson = new Person();

        oldRecord.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {
                selectedPerson = (Person) adapter.getItemAtPosition(position);
            }
        });


        /**
         * The deleteButton will retrieve the selected person information and remove
         * the selected person from personList, and store the change in file.
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                if (selectedPerson.getName() != null) {
                    personList.remove(selectedPerson);
                    saveInFile();
                    totalNum = personList.size();
                    total.setText("The total amount of record is: " + totalNum);
                    adapter.notifyDataSetChanged();
                    selectedPerson = new Person();
                }
                else{
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Please select a record first!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


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
     * save personList into a specified file in JSON format.
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
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Add.
     * OnclickListener for the "add a new record" button, it goes to the A_NewRecord activity.
     *
     * @param view the view
     */
    public void add(View view) {
        Intent intent = new Intent(this, A_NewRecord.class);
        startActivity(intent);
    }

    /**
     * View edit.
     * OncClickListener for the "Edit and View" button, it sends the information of the selected person
     * to the "View_Edit" activity
     *
     * @param view the view
     */
    public void viewEdit(View view) {

        if (selectedPerson.getName() != null) {
            String selectedPersonName = selectedPerson.getName();
            String selectedPersonDate = selectedPerson.getDate();
            String selectedPersonNeck = selectedPerson.getNeck();
            String selectedPersonBust = selectedPerson.getBust();
            String selectedPersonChest = selectedPerson.getChest();
            String selectedPersonWaist = selectedPerson.getWaist();
            String selectedPersonHip = selectedPerson.getHip();
            String selectedPersonInseam = selectedPerson.getInseam();
            String selectedPersonComment = selectedPerson.getComment();

            Intent intent = new Intent(this, View_Edit.class);

            intent.putExtra("Person_Name", selectedPersonName);
            intent.putExtra("Person_Date", selectedPersonDate);
            intent.putExtra("Person_Neck", selectedPersonNeck);
            intent.putExtra("Person_Bust", selectedPersonBust);
            intent.putExtra("Person_Chest", selectedPersonChest);
            intent.putExtra("Person_Waist", selectedPersonWaist);
            intent.putExtra("Person_Hip", selectedPersonHip);
            intent.putExtra("Person_Inseam", selectedPersonInseam);
            intent.putExtra("Person_Comment", selectedPersonComment);

            startActivityForResult(intent, 1);
            //selectedPerson = new Person();
        }
        else{
            Context context = getApplicationContext();
            Toast.makeText(context, "Please select a record first!", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    /**
     * This method receives the edited information from "View_Edit" activity
     * and stores the changes into the file.sav.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String resultName = data.getStringExtra("resultName");
                String resultDate = data.getStringExtra("resultDate");
                String resultNeck = data.getStringExtra("resultNeck");
                String resultBust = data.getStringExtra("resultBust");
                String resultChest = data.getStringExtra("resultChest");
                String resultWaist = data.getStringExtra("resultWaist");
                String resultHip = data.getStringExtra("resultHip");
                String resultInseam = data.getStringExtra("resultInseam");
                String resultComment = data.getStringExtra("resultComment");
                personList.remove(selectedPerson);

                Person newPerson = new Person();
                newPerson.setName(resultName);
                newPerson.setDate(resultDate);
                newPerson.setNeck(resultNeck);
                newPerson.setBust(resultBust);
                newPerson.setChest(resultChest);
                newPerson.setWaist(resultWaist);
                newPerson.setHip(resultHip);
                newPerson.setInseam(resultInseam);
                newPerson.setComment(resultComment);

                personList.add(newPerson);
                adapter.notifyDataSetChanged();
                saveInFile();
                selectedPerson = new Person();

            }
        }
    }
}