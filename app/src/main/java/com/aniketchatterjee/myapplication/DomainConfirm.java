package com.aniketchatterjee.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DomainConfirm extends AppCompatActivity {

    private String selected_duration;
    private EditText paymentid;
    private String payment_id;
    private  String domain;
    private String uname;
    private SharedPreferences sharedPreferences;
    private  MethodLibrary lib = new MethodLibrary();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domain_confirm);

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        domain = getIntent().getStringExtra("domain");
        uname = getIntent().getStringExtra("username");
        TextView application= findViewById(R.id.application_welcome);
        TextView link = findViewById(R.id.emp_login_screen);

        Spinner durationlist = findViewById(R.id.duration);
        Button submit = findViewById(R.id.submit);
        List<String> userdata = getlist("userdata");
        int index = userdata.indexOf(uname);
        String name = userdata.get((index-1));

        application.setText(("Hi," +name +" you have choosen " + domain + " for you internship."));
        String duration [] = {"1 Week", "1 Month", "2 Months", "3 Months", "4 Months", "5 Months", "6 Months"};
        ArrayAdapter<String> durations = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, duration);
        durations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationlist.setAdapter(durations);


        durationlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selected_duration = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               //1st option is selected by default
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MethodLibrary lib = new MethodLibrary();
                paymentid = findViewById(R.id.payment_id);
                payment_id = paymentid.getText().toString();
                if (payment_id.isEmpty()){
                    Toast.makeText(DomainConfirm.this, "Please fill payment id and choose internship duration", Toast.LENGTH_LONG).show();
                }
                else {
                    String emailid = userdata.get((index+2));
                    String phone = userdata.get((index+3));
                    String branch = userdata.get((index+4));
                    String college = userdata.get((index+5));
                    String perc10 = userdata.get((index+6));
                    String perc12 = userdata.get((index+7));
                    String percUG = userdata.get((index+8));
                    String percPG = userdata.get((index+9));
                    String location = userdata.get((index+15));


                    ApplicantManager applicantManager = Singleton.getInstance(ApplicantManager.class);
                    AdminHome.ItemModel newApplicant = new AdminHome.ItemModel(name, domain, selected_duration, payment_id, phone, emailid, branch, college, perc10,perc12,percUG,percPG, location);
                    applicantManager.addApplicant(newApplicant);
                    userdata.set((index+10), domain);
                    userdata.set((index+11), payment_id);
                    userdata.set((index+12), "yes");
                    writelist(userdata,"userdata");
                    lib.pass_textOpen_screen(DomainConfirm.this, Homepage.class, uname);

                }
            }
        });


        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.url);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        lib.pass_textOpen_screen(DomainConfirm.this, LoggedinScreen.class, uname);
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
            lib.openScreenMenuAccessible(DomainConfirm.this, UpdateUserPW.class, DomainConfirm.class, uname);
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

            lib.openScreen(DomainConfirm.this, MainActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
        }
    }



