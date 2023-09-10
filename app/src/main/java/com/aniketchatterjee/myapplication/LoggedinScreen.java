package com.aniketchatterjee.myapplication;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoggedinScreen extends AppCompatActivity {

    private String uname;
    private SharedPreferences sharedPreferences;
    public MethodLibrary lib = new MethodLibrary();
    private boolean exit = false;
    private Handler handler;
    private static final int delay = 2000;


    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);

        final TextView welcome = findViewById(R.id.welcome);
        GridView gridView = findViewById(R.id.domains);
        uname = getIntent().getStringExtra("extra");
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        handler = new Handler();
        List<String> userdata = getlist("userdata");
        int index = userdata.indexOf(uname);
        String name = userdata.get((index -1));

        welcome.setText(("Hi " + name + " Welcome to Exposys Data Lab!Please choose domain for your internship-"));
        List<GridItem> data = new ArrayList<>();
        data.add(new GridItem("Software Development", "Software development involves designing, coding, testing, and maintaining software applications, providing excellent career opportunities in various industries"));
        data.add(new GridItem("Full Stack Developer", "A full stack developer is skilled in both front-end and back-end development, offering diverse career prospects in developing complete web applications."));
        data.add(new GridItem("Mean Stack Developer", "A Mean stack developer specializes in using JavaScript-based technologies like MongoDB, Express.js, Angular, and Node.js to build dynamic web applications, opening doors to exciting opportunities in web development.\n"));
        data.add(new GridItem("Web Development", "Web development focuses on creating websites and web applications, providing numerous career paths for professionals skilled in programming languages like HTML, CSS, and JavaScript."));
        data.add(new GridItem("Data Science", "Data science involves extracting insights and valuable information from large datasets using statistical analysis and machine learning techniques, offering lucrative career opportunities in data-driven industries."));
        data.add(new GridItem("IOT (Internet of Things)", " IoT revolves around connecting devices and enabling them to communicate and share data, creating opportunities for professionals in developing innovative IoT solutions for various industries."));
        data.add(new GridItem("App Developer", "App development entails creating mobile applications for different platforms like iOS and Android, providing a thriving career in the fast-growing mobile app industry."));
        data.add(new GridItem("Cyber Security", " Cyber security focuses on protecting computer systems and networks from unauthorized access and attacks, offering a promising career in ensuring digital security and privacy."));
        data.add(new GridItem("HR (Human Resources)", "HR professionals manage human resources within organizations, including recruitment, employee relations, and talent development, offering diverse career opportunities in managing people and organizational culture."));
        data.add(new GridItem("Process Associate", " Process associates support and streamline business processes, providing career prospects in operations management and process improvement."));
        data.add(new GridItem("Content Writer", "Content writers create written material for various platforms, offering career opportunities in marketing, advertising, and digital content creation."));
        data.add(new GridItem("Digital Marketing", " Digital marketing involves promoting products and services through digital channels, offering career prospects in online advertising, social media marketing, and search engine optimization."));
        data.add(new GridItem("UI/UX Design", " UI/UX designers focus on creating user-friendly and visually appealing interfaces for websites and applications, providing career opportunities in user experience research and design."));
        data.add(new GridItem("Business Development", " Business development professionals identify growth opportunities, build strategic partnerships, and expand business operations, offering career prospects in driving organizational growth and profitability."));
        data.add(new GridItem("Marketing", "Marketing professionals develop and implement marketing strategies to promote products and services, providing career opportunities in branding, advertising, and market research."));
        data.add(new GridItem("Telecaller", "Telecallers make outbound calls to potential customers, offering career prospects in sales and customer service roles."));
        data.add(new GridItem("Email Marketing", "Email marketers create and execute email campaigns to engage customers and drive conversions, providing career opportunities in digital marketing and customer relationship management."));
        data.add(new GridItem("SMS Marketing", "SMS marketers use text messaging to reach and engage customers, offering career prospects in mobile marketing and customer communication."));
        data.add(new GridItem("Photographer/Videographer", "Photographers and videographers capture visual content for various purposes, providing career opportunities in advertising, media, and entertainment industries."));
        data.add(new GridItem("Filmmaker", "Filmmakers create films and videos, offering career prospects in the film industry, including directing, producing, and editing."));
        data.add(new GridItem("Digital Content Creator", " Digital content creators produce various forms of content like videos, podcasts, and blogs for online platforms, providing career opportunities in content creation, influencer marketing, and brand promotion."));
        data.add(new GridItem("Social Media Promoter", " Social media promoters manage and promote brands on social media platforms, offering career prospects in social media marketing and digital branding."));

        GridAdapter adapter = new GridAdapter(this, data);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
            lib.openScreen(LoggedinScreen.this, MainActivity.class);
            return;
        }
        this.exit = true;
        Toast.makeText(this, "Press back again to Logout", Toast.LENGTH_SHORT).show();
        handler.postDelayed(() -> exit = false, delay);
    }

    public List<String> getlist(String key) {
        String serializedList = sharedPreferences.getString(key, "");
        List<String> list = new ArrayList<>();
        if (!serializedList.isEmpty()) {
            String[] items = serializedList.split(",");
            Collections.addAll(list, items);
        }
        return list;
    }


    private static class GridItem {
        private final String domainName;
        private final String domainDescription;

        public GridItem(String domainName, String domainDescription) {
            this.domainName = domainName;
            this.domainDescription = domainDescription;
        }

        public String getDomainName() {
            return domainName;
        }

        public String getDomainDescription() {
            return domainDescription;
        }
    }

    private class GridAdapter extends ArrayAdapter<GridItem> {

        private final LayoutInflater inflater;

        public GridAdapter(Context context, List<GridItem> data) {
            super(context, 0, data);
            inflater = LayoutInflater.from(context);
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.grid_domain, parent, false);

                holder = new ViewHolder();
                holder.domainName = convertView.findViewById(R.id.domainName);
                holder.domainDescription = convertView.findViewById(R.id.domainDescription);
                holder.domainAccept = convertView.findViewById(R.id.domainAccept);

                convertView.setTag(holder);
            } else {
                // Reuse the existing ViewHolder
                holder = (ViewHolder) convertView.getTag();
            }

            // Get the data for the current position
            GridItem item = getItem(position);
            assert item != null;
            String domain = item.getDomainName();

            // Set the data to the views
            holder.domainName.setText(domain);
            holder.domainDescription.setText(item.getDomainDescription());
            holder.domainAccept.setOnClickListener(v -> {
                Bundle extra = new Bundle();
                extra.putString("domain",domain);
                extra.putString("username", uname);
                lib.openScreenBundle(LoggedinScreen.this, DomainConfirm.class, extra);
            });

            return convertView;
        }

        private class ViewHolder {
            TextView domainDescription;
            TextView domainName;
            Button domainAccept;
        }
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
                lib.openScreenMenuAccessible(LoggedinScreen.this, UpdateUserPW.class, LoggedinScreen.class, uname);
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
                lib.openScreen(LoggedinScreen.this, MainActivity.class);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }



