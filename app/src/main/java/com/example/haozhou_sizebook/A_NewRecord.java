package com.example.haozhou_sizebook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class A_NewRecord extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private ArrayList<Person> personList;
    private ArrayAdapter<Person> adapter;
    private EditText nameTxt;
    private EditText dateTxt;
    private EditText neckTxt;
    private EditText bustTxt;
    private EditText chestTxt;
    private EditText waistTxt;
    private EditText hipTxt;
    private EditText inseamTxt;
    private EditText commentTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__new_record);
        personList = new ArrayList<>();
        nameTxt = (EditText) findViewById(R.id.NameTxt);
        dateTxt = (EditText) findViewById(R.id.DateDate);
        neckTxt = (EditText) findViewById(R.id.NeckNum);
        bustTxt = (EditText) findViewById(R.id.BustNum);
        chestTxt = (EditText) findViewById(R.id.ChestNum);
        waistTxt = (EditText) findViewById(R.id.WaistNum);
        hipTxt = (EditText) findViewById(R.id.HipNum);
        inseamTxt = (EditText) findViewById(R.id.InseamNum);
        commentTxt = (EditText) findViewById(R.id.CommentTxt);
        Button saveButton = (Button) findViewById(R.id.SaveBot);

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String name = nameTxt.getText().toString();

                /**
                 * The following codes that show empty name warning were copied from StackOverflow
                 * http://stackoverflow.com/questions/3862394/when-do-you-use-apply-and-when-funcall
                 */
                if (name.matches("")) {
                    Context context = getApplicationContext();
                    Toast.makeText(context, "The name field is mandatory for a record!", Toast.LENGTH_SHORT).show();
                    return;

                }
                else{
                    setResult(RESULT_OK);
                    String date = dateTxt.getText().toString();
                    String neck = neckTxt.getText().toString();
                    String bust = bustTxt.getText().toString();
                    String chest = chestTxt.getText().toString();
                    String waist = waistTxt.getText().toString();
                    String hip = hipTxt.getText().toString();
                    String inseam = inseamTxt.getText().toString();
                    String comment = commentTxt.getText().toString();


                    Person person = new Person(name);
                    person.setDate(date);
                    person.setNeck(neck);
                    person.setBust(bust);
                    person.setChest(chest);
                    person.setWaist(waist);
                    person.setHip(hip);
                    person.setInseam(inseam);
                    person.setComment(comment);

                    personList.add(person);
//adapter.add(tweetList);
                    // adapter.notifyDataSetChanged();
                    saveInFile();

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
        //adapter = new ArrayAdapter<Person>(this,
        //        R.layout.list_item, personList);
        //oldRecord.setAdapter(adapter);
    }
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
}