package com.aniketchatterjee.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;

public class Verification extends AppCompatActivity {

    EditText emailotp;
    EditText phnotp;
    Button verify;
    Button back;
    private List<String> userdata;
    private SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        emailotp = findViewById(R.id.emailotp);
        phnotp = findViewById(R.id.phnotp);
        verify = findViewById(R.id.verify);
        back = findViewById(R.id.back);

        MethodLibrary lib = new MethodLibrary();
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        userdata = getlist("userdata");


        String channelId = "OTP";
        int emailotpnoti = 1;
        int phoneotpnoti = 2;
        int emailotprand = lib.randint();
        int phoneotprand = lib.randint();


        // Creating notification channel for Android 8.0 Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence channelName = "OTP Channel";
            String channelDescription = "Channel for displaying OTP";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            channel.enableLights(true);
            channel.setLightColor(Color.WHITE);
            channel.enableVibration(true);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mailotp = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Email OTP")
                .setContentText("Your E-mail verification OTP is " + emailotprand + ".")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(emailotpnoti, mailotp.build());

        NotificationCompat.Builder phoneotp = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Phone OTP")
                .setContentText("Your phone number verification OTP is " + phoneotprand + ".")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(phoneotpnoti, phoneotp.build());

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otpemail = emailotp.getText().toString();
                String otpphn = phnotp.getText().toString();


                if (otpemail.equals(String.valueOf(emailotprand)) && otpphn.equals(String.valueOf(phoneotprand))) {
                    Toast.makeText(Verification.this, "Signed Up! Proceed to Login", Toast.LENGTH_LONG).show();
                    lib.openScreen(Verification.this, MainActivity.class);
                    save();
                } else {
                    Toast.makeText(Verification.this, "Please enter the correct OTPs", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lib.openScreen(Verification.this, Signup.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    public void save() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String emailid = intent.getStringExtra("email");
        String phno = intent.getStringExtra("phone");
        String pname = intent.getStringExtra("personname");
        String password = intent.getStringExtra("password");
        String domain = "NA";
        String paymentID = "NA";
        String applied = "no";
        String status = "NA";
        String assignedProject = "NA";
        String branch = intent.getStringExtra("branch");
        String college = intent.getStringExtra("college");
        String perc10 = intent.getStringExtra("perc10");
        String perc12 = intent.getStringExtra("perc12");
        String percUG = intent.getStringExtra("percUG");
        String percPG = intent.getStringExtra("percPG");
        String location = intent.getStringExtra("location");

        userdata.add(pname);
        userdata.add(username);
        userdata.add(password);
        userdata.add(emailid);
        userdata.add(phno);
        userdata.add(branch);
        userdata.add(college);
        userdata.add(perc10);
        userdata.add(perc12);
        userdata.add(percUG);
        userdata.add(percPG);
        userdata.add(domain);
        userdata.add(paymentID);
        userdata.add(applied);
        userdata.add(status);
        userdata.add(assignedProject);
        userdata.add(location);


        writelist(userdata, "userdata");
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
        } else if (itemId == R.id.help) {
            String contactus = getString(R.string.contact_us);
            Intent contact = new Intent(Intent.ACTION_VIEW, Uri.parse(contactus));
            startActivity(contact);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}





