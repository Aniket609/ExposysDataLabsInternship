package com.aniketchatterjee.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
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

public class UpdateAdminPW extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private MethodLibrary lib = new MethodLibrary();
    private String emid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);

        EditText currentPW = findViewById(R.id.currentPass);
        EditText newPW = findViewById(R.id.newPass);
        EditText confirmNewPW = findViewById(R.id.confirmNewPass);
        Button update = findViewById(R.id.update);


        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

       emid = getIntent().getStringExtra("extra");
        List<String> employeelist;
        List<String> employeepasslist;
        employeelist = getlist("employeelist");
        employeepasslist = getlist("employeepasslist");

        int index = employeelist.indexOf(emid);
        String password = employeepasslist.get(index);
        MethodLibrary lib = new MethodLibrary();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_pw = currentPW.getText().toString();
                String new_pw = newPW.getText().toString();
                String confirm_new_pw = confirmNewPW.getText().toString();

                if (current_pw.equals(password)) {
                    if (new_pw.equals(confirm_new_pw)) {
                        employeepasslist.set(index, new_pw);
                        writelist(employeepasslist, "employeepasslist");
                        Toast.makeText(UpdateAdminPW.this, "Password updated successfully! Please login again.", Toast.LENGTH_LONG).show();
                        lib.openScreen(UpdateAdminPW.this, AdminLogin.class);
                    } else {
                        Toast.makeText(UpdateAdminPW.this, "Password update failed due to password mismatch! Please enter same password!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(UpdateAdminPW.this, "Wrong current passowrd entered!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        String previous =  getIntent().getStringExtra("previousActivity");
        super.onBackPressed();
        try {
            Class<?> previousActivityClass = Class.forName(previous);
            lib.pass_textOpen_screen(UpdateAdminPW.this, previousActivityClass, emid);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        inflater.inflate(R.menu.menu_admin, menu);
        MenuItem updatepw = menu.findItem(R.id.updatepw);
        MenuItem home = menu.findItem(R.id.homepage);
        MenuItem interns = menu.findItem(R.id.interns);
        boolean hide = false;
        updatepw.setVisible(hide);
        home.setVisible(hide);
        interns.setVisible(hide);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();


            if (itemId == R.id.user_logout) {
                lib.openScreen(UpdateAdminPW.this, MainActivity.class);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }


