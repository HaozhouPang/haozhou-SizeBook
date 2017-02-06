package com.example.haozhou_sizebook;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by PANG on 2017-02-02.
 * This activity receives the selected person's information and allows users to view and edit
 * the record. all changes will be sent back to main activity and stored in file.
 */
public class View_Edit extends AppCompatActivity {

    private Intent returnIntent;
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
        setContentView(R.layout.activity_view_edit);
        returnIntent = getIntent();

        /*Button saveChange = (Button) findViewById(R.id.SaveChangeBot);
        saveChange.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(this, MainActivity.class);

            }
        });*/
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

    /**
     * Save change.
     *send the changed record to main activity and get stored in file.
     * @param view the view
     */
    public void saveChange(View view) {
        String returnName = nameTxt.getText().toString();
        String returnDate = dateTxt.getText().toString();
        String returnNeck = neckTxt.getText().toString();
        String returnBust = bustTxt.getText().toString();
        String returnChest = chestTxt.getText().toString();
        String returnWaist = waistTxt.getText().toString();
        String returnHip = hipTxt.getText().toString();
        String returnInseam = inseamTxt.getText().toString();
        String returnComment = commentTxt.getText().toString();

        if (!(returnName.matches(""))) {

            returnIntent.putExtra("resultName", returnName);
            returnIntent.putExtra("resultDate", returnDate);
            returnIntent.putExtra("resultNeck", returnNeck);
            returnIntent.putExtra("resultBust", returnBust);
            returnIntent.putExtra("resultChest", returnChest);
            returnIntent.putExtra("resultWaist", returnWaist);
            returnIntent.putExtra("resultHip", returnHip);
            returnIntent.putExtra("resultInseam", returnInseam);
            returnIntent.putExtra("resultComment", returnComment);
            setResult(Activity.RESULT_OK, returnIntent);

            Context context = getApplicationContext();
            Toast.makeText(context, "changes saved", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Context context = getApplicationContext();
            Toast.makeText(context, "The name field is mandatory for a record!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d("debug", "ok2");

        String name = returnIntent.getStringExtra("Person_Name");
        String date = returnIntent.getStringExtra("Person_Date");
        String neck = returnIntent.getStringExtra("Person_Neck");
        String bust = returnIntent.getStringExtra("Person_Bust");
        String chest = returnIntent.getStringExtra("Person_Chest");
        String waist = returnIntent.getStringExtra("Person_Waist");
        String hip = returnIntent.getStringExtra("Person_Hip");
        String inseam = returnIntent.getStringExtra("Person_Inseam");
        String comment = returnIntent.getStringExtra("Person_Comment");

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