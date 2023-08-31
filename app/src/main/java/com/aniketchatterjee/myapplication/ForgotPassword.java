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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;


public class ForgotPassword extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private int index;
    private String pw;
    private String uname;
    private String channelId;
    private int otprand;
    private MethodLibrary lib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        TextView guide = findViewById(R.id.guide);
        EditText get_uname = findViewById(R.id.get_username);
        EditText otP = findViewById(R.id.otp);
        EditText get_pw1 = findViewById(R.id.pw1);
        EditText get_pw2 = findViewById(R.id.pw2);
        Button get_otp = findViewById(R.id.get_otp);
        Button verify = findViewById(R.id.verify_otp);
        Button submit = findViewById(R.id.pw_submit);
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        lib = new MethodLibrary();

        channelId = "OTP";
        otprand = lib.randint();

        List<String> userdata = getlist("userdata");
        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = get_uname.getText().toString();
                if (userdata.contains(uname)) {
                    index = userdata.indexOf(uname);
                    showOTP();
                }
                else {
                    Toast.makeText(ForgotPassword.this, "User does not Exist, Please Signup!", Toast.LENGTH_LONG).show();
                }
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oTp = otP.getText().toString();
                int otp = Integer.parseInt(oTp);
                if ( otp == otprand) {
                    get_pw1.setVisibility(View.VISIBLE);
                    get_pw2.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(ForgotPassword.this, "Please Enter Correct OTP!", Toast.LENGTH_LONG).show();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw = userdata.get((index + 1));
                String password = get_pw1.getText().toString();
                String conPassword = get_pw2.getText().toString();
                if (password.equals(conPassword)) {
                    userdata.set((index+1), password);
                    writelist(userdata,"userdata");
                    Toast.makeText(ForgotPassword.this, "Password changed successfully, proceed to login!", Toast.LENGTH_LONG).show();
                    lib.openScreen(ForgotPassword.this, MainActivity.class);
                }
                else {
                    Toast.makeText(ForgotPassword.this, "Failed to change password due to password mismatch! Please enter same password!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed () {
        super.onBackPressed();
        lib.openScreen(ForgotPassword.this,MainActivity.class);
    }

    public void showOTP () {
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

        int otpnoti = 1;
        NotificationCompat.Builder otp = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("OTP")
                .setContentText("Your OTP for password reset is " + otprand + ".")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(otpnoti, otp.build());

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
