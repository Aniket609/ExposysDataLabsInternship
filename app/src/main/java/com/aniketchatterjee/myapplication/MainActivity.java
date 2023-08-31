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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView error;
    private SharedPreferences sharedPreferences;
    public String uname;
    private boolean exit = false;
    private Handler handler;
    private static final int delay = 2000;

    MethodLibrary lib = new MethodLibrary();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        handler = new Handler();

        // to collect inputs from user
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        error = findViewById(R.id.error);
        final Button login = findViewById(R.id.login);
        final Button signUp = findViewById(R.id.signup);
        final TextView emplogin = findViewById(R.id.emp_login_screen);
        final TextView forgotPW = findViewById(R.id.forgot_pw);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = username.getText().toString();
                String pword = password.getText().toString();


                // initiating necessary data for login process
                List<String> userdata = getlist("userdata");

                // logic for login process
                if (userdata.contains(uname)) {
                    int index = userdata.indexOf(uname);
                    String pw = userdata.get((index+1));
                    if (pword.equals(pw)) {
                       String applied = userdata.get((index+12));
                        if (applied.equals("yes")) {
                            lib.pass_textOpen_screen(MainActivity.this, Homepage.class,uname);
                        }
                        else {
                            lib.pass_textOpen_screen(MainActivity.this, LoggedinScreen.class,uname);
                        }

                    }
                    else {
                        error.setText(("Wrong Username or Password"));
                    }
                } else {
                    error.setText(("User does not Exist, Please Signup!"));
                }
            }
        });

        // to open signup page
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lib.openScreen(MainActivity.this, Signup.class);
            }
        });

        emplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to initiate and add test data if empty
                List<String> employeelist;
                List<String> employeepasslist;
               employeelist = getlist("employeelist");
                employeepasslist = getlist("employeepasslist");
                if (employeelist.isEmpty()||employeepasslist.isEmpty()) {

                    employeelist.add("Exposys");
                    employeepasslist.add("DataLabs");
                    writelist(employeelist, "employeelist");
                    writelist(employeepasslist, "employeepasslist");
                }
                // to open login screen for admins
                lib.openScreen(MainActivity.this, AdminLogin.class);
            }
        });
        forgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lib.openScreen(MainActivity.this, ForgotPassword.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
            return;
        }

        this.exit = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        handler.postDelayed(() -> exit = false, delay);
    }

    // to read and return list from SharedPreferences
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
        inflater.inflate(R.menu.menu_default, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

            if (itemId == R.id.about) {
                String help = getString(R.string.about_us);
                Intent helP = new Intent(Intent.ACTION_VIEW, Uri.parse(help));
                startActivity(helP);
                return true;
            }
            else if (itemId == R.id.help) {
                String contactus = getString(R.string.contact_us);
                Intent contact = new Intent(Intent.ACTION_VIEW, Uri.parse(contactus));
                startActivity(contact);

            return true;
        }
                return super.onOptionsItemSelected(item);
        }
    }



