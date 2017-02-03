package com.example.haozhou_sizebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by PANG on 2017-02-02.
 */

public class View_Edit extends AppCompatActivity {
    private EditText nameTxt;
    private EditText dateTxt;
    private EditText neckTxt;
    private EditText bustTxt;
    private EditText chestTxt;
    private EditText waistTxt;
    private EditText hipTxt;
    private EditText inseamTxt;
    private EditText commentTxt;

    Intent intent = getIntent();
    Bundle extras = intent.getExtras();
    private String name = extras.getString("Person_name");
    private String date = extras.getString("Person_date");
    private String neck = extras.getString("Person_neck");
    private String bust = extras.getString("Person_bust");
    private String chest = extras.getString("Person_chest");
    private String waist = extras.getString("Person_waist");
    private String hip = extras.getString("Person_hip");
    private String inseam = extras.getString("Person_inseam");
    private String comment = extras.getString("Person_comment");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit);
        nameTxt = (EditText) findViewById(R.id.NameTxt);
        dateTxt = (EditText) findViewById(R.id.DateDate);
        neckTxt = (EditText) findViewById(R.id.NeckNum);
        bustTxt = (EditText) findViewById(R.id.BustNum);
        chestTxt = (EditText) findViewById(R.id.ChestNum);
        waistTxt = (EditText) findViewById(R.id.WaistNum);
        hipTxt = (EditText) findViewById(R.id.HipNum);
        inseamTxt = (EditText) findViewById(R.id.InseamNum);
        commentTxt = (EditText) findViewById(R.id.CommentTxt);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        nameTxt.setText(name);
        dateTxt.setText(date);
        neckTxt.setText(neck);
        bustTxt.setText(bust);
        chestTxt.setText(chest);
        waistTxt.setText(waist);
        hipTxt.setText(hip);
        inseamTxt.setText(inseam);
        commentTxt.setText(comment);
    }
}
