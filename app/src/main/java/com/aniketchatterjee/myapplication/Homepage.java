package com.aniketchatterjee.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity {
    private String uname;
    private TextView applicantStatus;
    private TextView project;
    private SharedPreferences sharedPreferences;
    MethodLibrary lib = new MethodLibrary();
    private boolean exit = false;
    private Handler handler;
    private static final int delay = 2000;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        uname = getIntent().getStringExtra("extra");
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        handler = new Handler();

       applicantStatus= findViewById(R.id.application_status);
       project = findViewById(R.id.project);
        List<String> userdata = getlist("userdata");
        int index = userdata.indexOf(uname);
        String domain = userdata.get((index+10));
        String status =userdata.get((index+13));
        String assignedProject = userdata.get((index+14));


       if (status.equals("accepted")) {
           applicantStatus.setText(("Your application for internship in the domain of " + domain + " is accepted. You will find your assignment details here once assigned"));
       }
       else if (status.equals("rejected")) {
           applicantStatus.setText(("Sorry! Your application for internship in the domain of " + domain + " is rejected."));
       }
       else {
           applicantStatus.setText(("Your application for internship in the domain of " + domain + " is being reviewed."));
       }
       if (!assignedProject.equals("NA")) {
           applicantStatus.setText(("Please find your project below, Good Luck -"));
           project.setText(assignedProject);
       }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
            lib.openScreen(Homepage.this, MainActivity.class);
            return;
        }
        this.exit = true;
        Toast.makeText(this, "Press back again to Logout", Toast.LENGTH_SHORT).show();
        handler.postDelayed(() ->exit = false, delay);

    }


    public List<String> getlist(String key) {
        String serializedList = sharedPreferences.getString(key, "");
        List<String> list = new ArrayList<>();
        if (!serializedList.isEmpty()) {
            String[] items = serializedList.split(",");
            for (String item : items) {
                list.add(item);
            }
        }
        return list;
    }

    public void writelist(List<String> list, String key) {
        StringBuilder serializedList = new StringBuilder();
        for (String item : list) {
            serializedList.append(item).append(",");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, serializedList.toString());
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();


            if (itemId == R.id.updatepw) {
                lib.openScreenMenuAccessible(Homepage.this, UpdateUserPW.class, Homepage.class, uname);
                return true;
            }
            else if (itemId == R.id.help) {
                String contactus = getString(R.string.contact_us);
                Intent help = new Intent(Intent.ACTION_VIEW, Uri.parse(contactus));
                startActivity(help);
                return true;
            }
            else if (itemId == R.id.about) {
                String aboutus = getString(R.string.about_us);
                Intent about = new Intent(Intent.ACTION_VIEW, Uri.parse(aboutus));
                startActivity(about);
                return true;
            }
            else if (itemId == R.id.user_logout) {
                lib.openScreen(Homepage.this, MainActivity.class);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }


