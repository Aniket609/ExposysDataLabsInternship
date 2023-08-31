package com.aniketchatterjee.myapplication;

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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AdminLogin extends AppCompatActivity {


        private EditText employeeid;
        private EditText password;
        private TextView error;
        private SharedPreferences sharedPreferences;
        private String emid;
        MethodLibrary lib = new MethodLibrary();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_login);

            sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

            // to collect inputs from user
            employeeid = findViewById(R.id.employee_id);
            password = findViewById(R.id.emp_password);
            error = findViewById(R.id.admin_error);
            final Button login = findViewById(R.id.emp_login);
            final TextView userlogin = findViewById(R.id.user_login_screen);



            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emid = employeeid.getText().toString();
                    String pword = password.getText().toString();

                    List<String> employeelist;
                    List<String> employeepasslist;
                    employeelist = getlist("employeelist");
                    employeepasslist = getlist("employeepasslist");

                    // logic for login process
                    if (employeelist.contains(emid)) {
                        int index = employeelist.indexOf(emid);
                        String pw = employeepasslist.get(index);
                        if (pword.equals(pw)) {
                            lib.pass_textOpen_screen(AdminLogin.this, AdminHome.class, emid);
                        } else {
                            error.setText(("Wrong Username or Password"));
                        }
                    } else {
                        error.setText(("Wrong Employee ID/Employee doesn't Exists!"));
                    }
                }
            });

            userlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lib.openScreen(AdminLogin.this, MainActivity.class);
                }
            });


        }

        @Override
        public void onBackPressed () {
            super.onBackPressed();
            lib.openScreen(AdminLogin.this,MainActivity.class);
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


