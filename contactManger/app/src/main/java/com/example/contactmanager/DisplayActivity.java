package com.example.contactmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class DisplayActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {

    static ListView lv;
    static int index;
    static int which = 0;
    private EditText search;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_display);
        //addtextchangelistner
        //recherche sur objet s
        //sous liste data2 : adapter //parameter adapter avec data2
        //arraylist<contact> d
        lv = findViewById(R.id.lv);

        //ArrayAdapter adapter = new ArrayAdapter(AffichageActivity.this,android.R.layout.simple_list_item_1, com.example.gestioncontact.AcceuilActivity.data);
        adapter = new MyAdapter(DisplayActivity.this,HomeActivity.data);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        search = (EditText) findViewById(R.id.et_search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("before:"+s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("ON:"+s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("after:"+s);
                adapter.getFilter().filter(s);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        index = position;
        AlertDialog.Builder alert = new AlertDialog.Builder(DisplayActivity.this);
        alert.setTitle("attention");
        alert.setMessage("Choose an action");
        alert.setPositiveButton("Delete",this);
        alert.setNegativeButton("Edit",this);
        alert.setNeutralButton("Delete all",this);
        alert.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        this.which = which;
        if(which==dialog.BUTTON_POSITIVE) {
            HomeActivity.data.remove(index);
            HomeActivity.saveData();
            lv.invalidateViews();
        }
        if(which==dialog.BUTTON_NEGATIVE) {
            Intent i = new Intent(DisplayActivity.this, EditActivity.class);
            String fname = HomeActivity.data.get(index).nom;
            String lname = HomeActivity.data.get(index).prenom;
            String number = HomeActivity.data.get(index).numero;
            i.putExtra("fname",fname);
            i.putExtra("lname",lname);
            i.putExtra("number",number);
            startActivity(i);
        }
        if(which==dialog.BUTTON_NEUTRAL) {
            HomeActivity.data.clear();
            HomeActivity.saveData();
            Log.e("--------------------", HomeActivity.data.toString() );
            //com.example.gestioncontact.AcceuilActivity.data.clear();
            lv.invalidateViews();
        }
    }
}