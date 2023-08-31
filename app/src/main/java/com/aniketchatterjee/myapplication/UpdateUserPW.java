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

public class UpdateUserPW extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private List<String> userdata;
    private String uname;
    private String password;
    private MethodLibrary lib = new MethodLibrary();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);

        EditText currentPW = findViewById(R.id.currentPass);
        EditText newPW = findViewById(R.id.newPass);
        EditText confirmNewPW = findViewById(R.id.confirmNewPass);
        Button update = findViewById(R.id.update);
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        userdata = getlist("userdata");
        uname = getIntent().getStringExtra("extra");
        int index = userdata.indexOf(uname);
        password = userdata.get((index+1));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_pw = currentPW.getText().toString();
                String new_pw = newPW.getText().toString();
                String confirm_new_pw = confirmNewPW.getText().toString();
                if (current_pw.equals(password)) {
                    if (new_pw.equals(confirm_new_pw)) {
                        userdata.set((index+1), new_pw);
                        writelist(userdata,"userdata");
                        Toast.makeText(UpdateUserPW.this, "Password updated successfully", Toast.LENGTH_LONG).show();
                        lib.openScreen(UpdateUserPW.this, MainActivity.class);
                    } else {
                        Toast.makeText(UpdateUserPW.this, "Password update failed due to password mismatch! Please enter same password!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(UpdateUserPW.this, "Wrong current passowrd entered!", Toast.LENGTH_LONG).show();
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
            lib.pass_textOpen_screen(UpdateUserPW.this, previousActivityClass, uname);
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
        inflater.inflate(R.menu.menu_user, menu);
        MenuItem updatepw = menu.findItem(R.id.updatepw);
        boolean hide = false;
        updatepw.setVisible(hide);
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
            else if (itemId == R.id.user_logout) {
                lib.openScreen(UpdateUserPW.this, MainActivity.class);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }


