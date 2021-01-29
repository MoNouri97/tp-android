package com.example.contactmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    public static ArrayList<Contact> data = new ArrayList<Contact>();
    boolean permission_write = false;
    boolean once;
    private TextView tvWelcome;
    private Button btnAdd;
    private Button btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWelcome = findViewById(R.id.tv_welcome);
        btnAdd = findViewById(R.id.btn_add);
        btnDisplay = findViewById(R.id.btn_display);

        //once = true;

        Intent in = this.getIntent();
        Bundle b = in.getExtras();
        String s = b.getString("USER");
        if (Objects.equals(s, "")) s = "USER";
        tvWelcome.setText("Welcome " + s);

        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, AddActivity.class);
            startActivity(i);
        });
        btnDisplay.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, DisplayActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            permission_write =true;
            //import
            importData();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

    }

    private void importData() {
        if (data.size() != 0) {
            return;
        }
        String dir = Environment.getExternalStorageDirectory().getPath();
        File f = new File(dir, "fichier.txt");
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    String[] t = line.split("#");
                    Contact c = new Contact(t[0], t[1], t[2]);
                    data.add(c);
                }
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        //save
        if (permission_write) {
            saveData();
            //AffichageActivity.lv.invalidateViews();
            //AcceuilActivity.data.clear();

        } else {
            Toast.makeText(this, "cant save data", Toast.LENGTH_SHORT).show();
        }
        super.onStop();
    }

    static void saveData() {
        String dir = Environment.getExternalStorageDirectory().getPath();
        File f = new File(dir, "fichier.txt");
        if (!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fw = new FileWriter(f, false);
            BufferedWriter bw = new BufferedWriter(fw);

            Log.e("---------------------", "saveData:"+data.toString() );
            for (Contact c : data) {
                bw.write(c.nom + "#" + c.prenom + "#" + c.numero + "\n");
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            permission_write = (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }
}