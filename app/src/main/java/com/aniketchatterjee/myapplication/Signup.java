package com.aniketchatterjee.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Signup extends AppCompatActivity {
    EditText personname;
    EditText username1;
    EditText password1;
    EditText password2;
    EditText email;
    EditText phone;
    EditText brnch;
    EditText clg;
    EditText perc10th;
    EditText perc12th;
    EditText percug;
    EditText percpg;
    EditText locate;
    Button signup;
    String password;
    MethodLibrary lib;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        personname = findViewById(R.id.personName);
        username1 = findViewById(R.id.usernameS);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        brnch = findViewById(R.id.branch);
        clg = findViewById(R.id.college);
        perc10th = findViewById(R.id.perc10th);
        perc12th = findViewById(R.id.perc12th);
        percug = findViewById(R.id.percug);
        percpg = findViewById(R.id.percpg);
        locate = findViewById(R.id.location);
        signup = findViewById(R.id.signup1);
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        List<String> userdata  = getlist("userdata");
        lib = new MethodLibrary();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = password1.getText().toString();
                String passcon = password2.getText().toString();

                if (password.equals(passcon)) {
                    String pname = personname.getText().toString();
                    String uname = username1.getText().toString();
                    String emailid = email.getText().toString();
                    String phno = phone.getText().toString();
                    String branch = brnch.getText().toString();
                    String college = clg.getText().toString();
                    String perc10 = perc10th.getText().toString();
                    String perc12 = perc12th.getText().toString();
                    String percUG = percug.getText().toString();
                    String percPG = percpg.getText().toString();
                    String location = locate.getText().toString();

                     if ((pname.isEmpty())||(uname.isEmpty())||(emailid.isEmpty())||(phno.length()<10)||(branch.isEmpty())||(college.isEmpty())||(perc10.isEmpty())||(perc12.isEmpty())||(percUG.isEmpty())||(location.isEmpty())) {
                         Toast.makeText(Signup.this, "All fields must be filled except PG percentage", Toast.LENGTH_LONG).show();
                    }
                     else if ((Integer.parseInt(perc10) < 60)||(Integer.parseInt(perc12) < 60)||(Integer.parseInt(percUG) < 60)) {
                         Toast.makeText(Signup.this, "Minimum 60% across academics is mandatory", Toast.LENGTH_LONG).show();
                     }
                     else if (!(percPG.isEmpty())&&(Integer.parseInt(percPG) < 60)) {
                         Toast.makeText(Signup.this, "Minimum 60% across academics is mandatory", Toast.LENGTH_LONG).show();
                     }
                    else if (userdata.contains(uname)) {
                        Toast.makeText(Signup.this, "Username already registered! Please set a different username!", Toast.LENGTH_LONG).show();
                    }
                    else if (userdata.contains(emailid)){
                        Toast.makeText(Signup.this, "E-mail already registered! Please signup with a different E-mail!", Toast.LENGTH_LONG).show();
                    }
                    else if (userdata.contains(phno)){
                        Toast.makeText(Signup.this, "Phone number already registered! Please signup with a different Phone number!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(Signup.this, "Please verify your E-mail and Phone number", Toast.LENGTH_LONG).show();
                        if (percPG.isEmpty()){
                            percPG = "NA";
                        }
                        Bundle extras = new Bundle();
                         extras.putString("personname", pname);
                        extras.putString("username", uname);
                         extras.putString("password", password);
                        extras.putString("email", emailid);
                        extras.putString("phone", phno);
                         extras.putString("college", college);
                         extras.putString("branch", branch);
                         extras.putString("perc10", perc10);
                         extras.putString("perc12", perc12);
                         extras.putString("percUG", percUG);
                         extras.putString("percPG", percPG);
                         extras.putString("location", location);
                        lib.openScreenBundle(Signup.this, Verification.class, extras);

                        personname.setText("");
                        username1.setText("");
                        password1.setText("");
                        password2.setText("");
                        email.setText("");
                        phone.setText("");
                        brnch.setText("");
                        clg.setText("");
                        perc10th.setText("");
                        perc12th.setText("");
                        percug.setText("");
                        percpg.setText("");
                        locate.setText("");
                    }
                }
                else {
                    Toast.makeText(Signup.this, "Signup Failed due to Password Mismatch", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        lib.openScreen(Signup.this, MainActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_default, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();


            if (itemId == R.id.help) {
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
            return super.onOptionsItemSelected(item);
        }
    }



