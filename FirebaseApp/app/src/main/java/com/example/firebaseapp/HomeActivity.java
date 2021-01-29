package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.firebaseapp.models.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
	ListView lv ;
	ArrayList<Person> data = new ArrayList<>();

	private FirebaseDatabase myDb;
	private DatabaseReference dbRef;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		myDb = FirebaseDatabase.getInstance();
		dbRef = myDb.getReference("persons");

		lv = findViewById(R.id.lv_home);

		dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				for (DataSnapshot child:dataSnapshot.getChildren()){
					data.add(child.getValue(Person.class));
				}
				lv.setAdapter(new ArrayAdapter(HomeActivity.this,
				                               android.R.layout.simple_list_item_1,
				                               data));
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});
	}
}