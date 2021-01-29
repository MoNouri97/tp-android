package com.example.firebaseapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	TextInputEditText etName, etPw;
	Button btnConnect;
	TextView tvNew;
	String name, pw;

	private FirebaseAuth mAuth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, AddUserActivity.class));
			}
		});

		mAuth = FirebaseAuth.getInstance();


		// refs
		etName = findViewById(R.id.ednom_auth);
		etPw = findViewById(R.id.edpwd_auth);
		btnConnect = findViewById(R.id.btnconnect_auth);
		tvNew = findViewById(R.id.tv_new_auth);

		// listeners
		btnConnect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// log-in
				name = etName.getText().toString().trim();
				pw = etPw.getText().toString().trim();

				if (!validateInputs())
					return;

				mAuth.signInWithEmailAndPassword(name,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()){
							startActivity(new Intent(MainActivity.this,HomeActivity.class));
						}else {
							Log.e("Tag","onComplete: "+ name+pw);
							Log.e("Tag","onComplete: "+ task.getException().toString() );
							Toast.makeText(MainActivity.this, "Error While Signing In", Toast.LENGTH_SHORT).show();
						}
					}
				});

			}
		});

		tvNew.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// sign-up
				name = etName.getText().toString().trim();
				pw = etPw.getText().toString().trim();

				if (!validateInputs())
					return;

				mAuth.createUserWithEmailAndPassword(name,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()){
							startActivity(new Intent(MainActivity.this,HomeActivity.class));
						}else {
							Toast.makeText(MainActivity.this, "Error While Creating", Toast.LENGTH_SHORT).show();

						}
					}
				});
			}
		});
	}

	private boolean validateInputs() {
		if (!nameIsValid(name)){
			etName.setError("Not a valid email");
			return false;
		}
		if (!pwIsValid(pw)){
			etPw.setError("Not a valid Password");
			return false;
		}
		return true;
	}

	private boolean pwIsValid(String pw) {
		return pw.length() >= 6;
	}

	private boolean nameIsValid(String name) {
		return name.contains("@") && name.contains(".") && name.length() >= 4;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}