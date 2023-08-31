package com.aniketchatterjee.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MethodLibrary extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

    }

    // to open a new screen
    public void openScreen(Context currentContext, Class<?> nextClass) {
        Intent intent = new Intent(currentContext, nextClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentContext.startActivity(intent);
    }


    public void pass_textOpen_screen(Context context, Class<?> destinationClass, String extraString) {
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra("extra", extraString);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
    public void openScreenMenuAccessible(Context context, Class<?> destinationClass, Class<?> previousClass, String extra) {
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra("previousActivity", previousClass.getName());
        intent.putExtra("extra", extra);
        context.startActivity(intent);
    }


    public void openScreenBundle (Context currentContext, Class<?> nextClass, Bundle extras) {
        Intent intent = new Intent(currentContext, nextClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentContext.startActivity(intent);
    }


    // To generate OTP

    public int randint () {
        int min = 100000;
        int max = 999999;
        Random r = new Random();
        int number = r.nextInt(max-min+1)+min;
        return number;
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

    // to store the list for future use
    public void writelist(List<String> list, String key) {
        StringBuilder serializedList = new StringBuilder();
        for (String item : list) {
            serializedList.append(item).append(",");
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, serializedList.toString());
        editor.apply();
    }
}
