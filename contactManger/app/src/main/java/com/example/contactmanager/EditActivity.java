package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    private Button btnSubmit;
    private EditText etFirstName;
    private TextView etLastName;
    private TextView etNumber;
    private String firstNameExtra, lastNameExtra, numberExtra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnSubmit = findViewById(R.id.btn_submit);
        etFirstName = findViewById(R.id.et_fName);
        etLastName = findViewById(R.id.et_lName);
        etNumber = findViewById(R.id.et_number);

        if(DisplayActivity.which==-2){
            Intent in = this.getIntent();
            Bundle b = in.getExtras();
            firstNameExtra = b.getString("fname");
            lastNameExtra = b.getString("lname");
            numberExtra = b.getString("number");
            if(!firstNameExtra.equals("") && !lastNameExtra.equals("") && !numberExtra.equals("")){
                etFirstName.setText(firstNameExtra);
                etLastName.setText(lastNameExtra);
                etNumber.setText(numberExtra);
            }
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = etFirstName.getText().toString();
                String lName = etLastName.getText().toString();
                String phone = etNumber.getText().toString();
                Contact c = new Contact(fName,lName,phone);
                HomeActivity.data.remove(DisplayActivity.index);
                HomeActivity.data.add(DisplayActivity.index,c);
                DisplayActivity.lv.invalidateViews();
                finish();
            }
        });
    }

}