package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebaseapp.models.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddUserActivity extends AppCompatActivity {

	EditText etName,etLname,etAdress;
	Button btnSubmit;

	private FirebaseDatabase myDb;
	private DatabaseReference dbRef;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_user);

		myDb = FirebaseDatabase.getInstance();
		dbRef = myDb.getReference("persons");

//		dbRef.addValueEventListener(new ValueEventListener() {
//			@Override
//			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//				String s = dataSnapshot.getValue(String.class);
//				Toast.makeText(AddUserActivity.this, "Person was changed", Toast.LENGTH_SHORT).show();
//
//			}
//
//			@Override
//			public void onCancelled(@NonNull DatabaseError databaseError) {
//
//			}
//		});

		// refs
		etName = findViewById(R.id.et_fname_add);
		etLname = findViewById(R.id.et_lname_add);
		etAdress = findViewById(R.id.et_address_add);
		btnSubmit = findViewById(R.id.btn_submit_add);

		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String fn,ln,add,key;
				fn = etName.getText().toString();
				ln = etLname.getText().toString();
				add = etAdress.getText().toString();

//				dbRef.setValue(fn+ln+add);
				key = dbRef.push().getKey();
				dbRef.child(key).setValue(new Person(fn,ln,add));

			}
		});

	}
}