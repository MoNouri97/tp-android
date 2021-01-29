package com.example.contactmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {

    private EditText etFirstName;
    private TextView etLastName;
    private TextView etNumber;
    private Button btnCancel;
    private Button btnSubmit;
//    String nomExtra="",prenomExtra="",numeroExtra="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etFirstName = findViewById(R.id.et_fName);
        etLastName = findViewById(R.id.et_lName);
        etNumber = findViewById(R.id.et_number);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = etFirstName.getText().toString();
                String lName = etLastName.getText().toString();
                String phone = etNumber.getText().toString();
                Contact c = new Contact(fName,lName,phone);
                HomeActivity.data.add(c);
                etFirstName.setText("");
                etLastName.setText("");
                etNumber.setText("");
                if(DisplayActivity.which==-2){
                    finish();
                    DisplayActivity.which=0;
                    DisplayActivity.lv.invalidateViews();
                }
            }
        });
        btnCancel.setOnClickListener(v -> finish());
    }
}