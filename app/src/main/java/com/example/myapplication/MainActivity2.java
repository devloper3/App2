package com.example.myapplication;

import static com.example.myapplication.DBmain.TABLENAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity2 extends AppCompatActivity {
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    TextInputLayout fname,lname;
    Button submit, display;
    int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        submit = (Button) findViewById(R.id.sbmt_add);
        dBmain = new DBmain(this);
        //create method
        findid();
        insertData();
        editData();
    }
    private void editData() {
        if (getIntent().getBundleExtra("userdata")!=null){
            Bundle bundle=getIntent().getBundleExtra("userdata");
            id=bundle.getInt("id");
            fname.getEditText().setText(bundle.getString("fname"));
            lname.getEditText().setText(bundle.getString("lname"));

        }
    }
    private void insertData() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.getEditText().getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity2.this, "Please enter Product first", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues cv = new ContentValues();
                cv.put("fname", fname.getEditText().getText().toString());
                cv.put("lname", lname.getEditText().getText().toString());

                sqLiteDatabase = dBmain.getWritableDatabase();
                int recupdate;
                if (id > 0) {
                    // Update existing row
                    recupdate = sqLiteDatabase.update(TABLENAME, cv, "id=?", new String[]{String.valueOf(id)});
                    if (recupdate > 0) {
                        Toast.makeText(MainActivity2.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity2.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Insert new row
                    recupdate = (int) sqLiteDatabase.insert(TABLENAME, null, cv);
                    if (recupdate > 0) {
                        Toast.makeText(MainActivity2.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity2.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
                if (recupdate > 0) {
                    //clear when click on submit
                    fname.getEditText().setText("");
                    lname.getEditText().setText("");
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
            private void findid() {
                fname = (TextInputLayout) findViewById(R.id.Product);
                lname = (TextInputLayout) findViewById(R.id.Amount);
                // Initialize the EditText fields
                fname.getEditText().setText("");
                lname.getEditText().setText("");
                submit = (Button) findViewById(R.id.sbmt_add);
//                display = (Button) findViewById(R.id.display_btn);
            }



}