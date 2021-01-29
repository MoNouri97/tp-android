package com.example.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnValid, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnValid = findViewById(R.id.btn_submit);
        btnExit = findViewById(R.id.btn_exit);

//        dev
        etPassword.setText("mono");
        etUsername.setText("mono");

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
        btnValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username= etUsername.getText().toString();
                String password= etPassword.getText().toString();
                if(username.equalsIgnoreCase("mono") && password.equalsIgnoreCase("mono")) {
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    i.putExtra("USER", username);
                    startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this, "not authorized", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}