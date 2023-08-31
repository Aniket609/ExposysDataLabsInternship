package com.aniketchatterjee.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssignedInterns extends AppCompatActivity {

    private RecyclerView assignedData;
    MethodLibrary lib = new MethodLibrary();
    private String emid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_interns);
        assignedData = findViewById(R.id.assigned_data);
        AssignedInternManager interns = Singleton.getInstance(AssignedInternManager.class);
        List<AssignedInterns.ItemModelIntern> internData = interns.getAssignedInterns();
        InternsAdapter internsAdapter = new InternsAdapter(internData);
        assignedData.setAdapter(internsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        assignedData.setLayoutManager(layoutManager);
        emid = getIntent().getStringExtra("extra");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        lib.pass_textOpen_screen(AssignedInterns.this, AdminHome.class,emid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin, menu);
        boolean hide = false;
        MenuItem interns = menu.findItem(R.id.interns);
        interns.setVisible(hide);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId ==  R.id.updatepw) {
            lib.openScreenMenuAccessible(AssignedInterns.this, UpdateAdminPW.class, AssignedInterns.class, emid);
            return true;
        }
        else if ( itemId == R.id.homepage) {
            lib.pass_textOpen_screen(AssignedInterns.this, AdminHome.class, emid);
            return true;
        }
        else if (itemId == R.id.adminmenu_logout) {
            lib.openScreen(AssignedInterns.this, AdminLogin.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
        }


    static class ItemModelIntern {
        private String name;
        private String domain;
        private String duration;
        private String paymentid;
        private String phone;
        private String email;
        private String branch;
        private String college;
        private String perc10;
        private String perc12;
        private String percUG;
        private String percPG;
        private String project;
        private String dateTime;
        private String location;

        public ItemModelIntern(String name, String domain, String duration, String paymentid, String phone, String email, String branch, String college, String perc10, String perc12, String percUG, String percPG, String project, String dateTime, String location) {
            this.name = name;
            this.domain = domain;
            this.duration = duration;
            this.paymentid = paymentid;
            this.phone = phone;
            this.email = email;
            this.branch = branch;
            this.college = college;
            this.perc10 = perc10;
            this.perc12 = perc12;
            this.percUG = percUG;
            this.percPG = percPG;
            this.project = project;
            this.dateTime = dateTime;
            this.location = location;

        }

        public String getName() {

            return name;
        }

        public String getDomain() {

            return domain;
        }

        public String getDuration() {

            return duration;
        }

        public String getPaymentID() {

            return paymentid;
        }

        public String getPhone() {

            return phone;
        }

        public String getEmail() {

            return email;
        }
        public String getBranch() {

            return branch;
        }
        public String getCollege() {

            return college;
        }
        public String getPerc10() {

            return perc10;
        }
        public String getPerc12() {

            return perc12;
        }
        public String getPercUG() {

            return percUG;
        }
        public String getPercPG() {

            return percPG;
        }

        public String getProject() {

            return project;
        }

        public String getDateTime() {

            return dateTime;
        }
        public String getLocation() {

            return location;
        }
    }

    public class InternsAdapter extends RecyclerView.Adapter<InternsAdapter.ViewHolder> {
        private List<ItemModelIntern> interns;

        public InternsAdapter(List <ItemModelIntern> interns) {
            this.interns = interns;
        }

        @Override
        public InternsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_assigned_list, parent, false);
            return new ViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ItemModelIntern item = interns.get(position);
            String phone = item.getPhone();
            holder.internName.setText(("Name: " + item.getName()));
            holder.internDomain.setText(("Domain: " + item.getDomain()));
            holder.internDuration.setText(("Duration: " + item.getDuration()));
            holder.internPaymentID.setText(("Payment ID: " + item.getPaymentID()));
            holder.internPhone.setText(("Phone: " + phone));
            holder.internEmail.setText(("Email: " + item.getEmail()));
            holder.internBranch.setText(("Branch: " + item.getBranch()));
            holder.internCollege.setText(("College: " + item.getCollege()));
            holder.internPerc10.setText(("10th Percentage: " + item.getPerc10()));
            holder.internPerc12.setText(("12th Percentage: " + item.getPerc12()));
            holder.internPercUG.setText(("UG Percentage: " + item.getPercUG()));
            holder.internPercPG.setText(("PG Percentage: " + item.getPercPG()));
            holder.internLocation.setText(("Location: " + item.getLocation()));
            holder.internProject.setText(("Project: " + item.getProject()));
            holder.internDateTime.setText(("Assigned on: " + item.getDateTime()));



            holder.complete.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Internship Completed for " + item.getName(), Toast.LENGTH_SHORT).show();
                interns.remove(position);
                notifyDataSetChanged();
            });
        }


        @Override
        public int getItemCount() {

            return interns.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView internName;
            public TextView internDomain;
            public TextView internDuration;
            public TextView internPaymentID;
            public TextView internPhone;
            public TextView internEmail;
            public TextView internBranch;
            public TextView internCollege;
            public TextView internPerc10;
            public TextView internPerc12;
            public TextView internPercUG;
            public TextView internPercPG;
            public TextView internLocation;
            public TextView internProject;
            public TextView internDateTime;
            public Button complete;

            public ViewHolder(View itemView) {
                super(itemView);
                internName = itemView.findViewById(R.id.intern_name);
                internDomain = itemView.findViewById(R.id.intern_domain);
                internDuration = itemView.findViewById(R.id.intern_duration);
                internPaymentID = itemView.findViewById(R.id.intern_paymentid);
                internPhone = itemView.findViewById(R.id.intern_phone);
                internEmail = itemView.findViewById(R.id.intern_email);
                internBranch = itemView.findViewById(R.id.intern_branch);
                internCollege = itemView.findViewById(R.id.intern_college);
                internPerc10 = itemView.findViewById(R.id.intern_perc10);
                internPerc12 = itemView.findViewById(R.id.intern_perc12);
                internPercUG = itemView.findViewById(R.id.intern_perc_ug);
                internPercPG = itemView.findViewById(R.id.intern_perc_pg);
                internLocation = itemView.findViewById(R.id.intern_location);
                internProject = itemView.findViewById(R.id.intern_project);
                internDateTime = itemView.findViewById(R.id.intern_date_time);
                complete = itemView.findViewById(R.id.complete);
            }
        }
    }
}
