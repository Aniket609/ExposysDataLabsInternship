package com.aniketchatterjee.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AdminHome extends AppCompatActivity {

    private static AcceptedApplicantsAdapter acceptedApplicantsAdapter;
    private static AcceptedApplicantManager acceptedApplicantManager;
    private static SharedPreferences sharedPreferences;
    public MethodLibrary lib = new MethodLibrary();
    private String emid;
    private static int applicant;
    private static int acceptedApplicant;
    @SuppressLint("StaticFieldLeak")
    private static TextView pending_applications;
    private static List<AdminHome.ItemModel>  applicantdata;
    private static List<AdminHome.ItemModel>  acceptedApplicantData;
    private boolean exit = false;
    private Handler handler;
    private static final int delay = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);

        RecyclerView applicants = findViewById(R.id.applicant_list);
        RecyclerView accepted_applicants = findViewById(R.id.accepted_applicants);
        pending_applications = findViewById(R.id.pending);
        emid = getIntent().getStringExtra("extra");
        handler = new Handler();

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);


        // Create a list of item models
        ApplicantManager applicantManager = Singleton.getInstance(ApplicantManager.class);
        applicantdata = applicantManager.getApplicants();
        acceptedApplicantManager = Singleton.getInstance(AcceptedApplicantManager.class);
        acceptedApplicantData = acceptedApplicantManager.getAcceptedApplicants();
        applicant = applicantdata.size();
        acceptedApplicant = acceptedApplicantData.size();
        pending();


        ApplicantsAdapter applicantsAdapter = new ApplicantsAdapter(applicantdata);
        applicants.setAdapter(applicantsAdapter);
        acceptedApplicantsAdapter = new AcceptedApplicantsAdapter(acceptedApplicantData);
        accepted_applicants.setAdapter(acceptedApplicantsAdapter);

        // Setting layout manager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager acclayoutManager = new LinearLayoutManager(this);
        applicants.setLayoutManager(layoutManager);
        accepted_applicants.setLayoutManager(acclayoutManager);


        }

    @Override
    public void onBackPressed() {
            if (exit) {
                super.onBackPressed();
                lib.openScreen(AdminHome.this, AdminLogin.class);
                return;
            }
            this.exit = true;
            Toast.makeText(this, "Press back again to Logout", Toast.LENGTH_SHORT).show();
            handler.postDelayed(() ->exit = false, delay);
        }



    public static List<String> getlist(String key) {
        String serializedList = sharedPreferences.getString(key, "");
        List<String> list = new ArrayList<>();
        if (!serializedList.isEmpty()) {
            String[] items = serializedList.split(",");
            list.addAll(Arrays.asList(items));
        }
        return list;
    }

    @SuppressLint("SetTextI18n")
    public static void pending(){
        if (applicant==0&&acceptedApplicant==0) {
            pending_applications.setText(("There no pending internship applications and no intern is available to be assigned a project"));
        }
        else if (applicant == 0){
            pending_applications.setText(("There is no pending internship applications. " +acceptedApplicant + " intern is available to be assigned a project"));
        }
        else if (acceptedApplicant == 0){
            pending_applications.setText(("There is "+ applicant+ " intership application is available to be reviewed. No intern is available to be assigned a project"));
        }
        else {
            pending_applications.setText(("There is "+ applicant+ " intership application is available to be reviewed. "+ acceptedApplicant + " intern is available to be assigned a project"));
        }
    }

    public static void writelist(List<String> list, String key) {
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
        boolean hide = false;
        MenuItem home = menu.findItem(R.id.homepage);
        home.setVisible(hide);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId ==  R.id.updatepw) {
            lib.openScreenMenuAccessible(AdminHome.this, UpdateAdminPW.class, AdminHome.class, emid);
            return true;
        }
        else if (itemId == R.id.interns) {
            lib.pass_textOpen_screen(AdminHome.this, AssignedInterns.class, emid);
            return true;
        }
        else if ( itemId == R.id.adminmenu_logout) {
            lib.openScreen(AdminHome.this, AdminLogin.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
        }





    public static class ItemModel {
        private final String name;
        private final String domain;
        private final String duration;
        private final String paymentid;
        private final String phone;
        private final String email;
        private final String branch;
        private final String college;
        private final String perc10;
        private final String perc12;
        private final String percUG;
        private final String percPG;
        private final String location;

        public ItemModel(String name, String domain, String duration, String paymentid, String phone, String email, String branch, String college, String perc10, String perc12, String percUG, String percPG, String location ) {
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
        public String getLocation() {

            return location;
        }

    }

    public static class ApplicantsAdapter extends RecyclerView.Adapter<ApplicantsAdapter.ViewHolder> {
        private final List<ItemModel> applicants;

        public ApplicantsAdapter(List<ItemModel> applicants) {

            this.applicants = applicants;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_applicants, parent, false);
            return new ViewHolder(itemView);
        }


        @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ItemModel item = applicants.get(position);
            String phone = item.getPhone();
            holder.applicantName.setText(("Name: " + item.getName()));
            holder.applicantDomain.setText(("Domain: " + item.getDomain()));
            holder.applicantDuration.setText(("Duration: " + item.getDuration()));
            holder.applicantPaymentID.setText(("Payment ID: " + item.getPaymentID()));
            holder.applicantPhone.setText(("Phone: " + phone));
            holder.applicantEmail.setText(("Email: " + item.getEmail()));
            holder.applicantBranch.setText(("Branch: " + item.getBranch()));
            holder.applicantCollege.setText(("College: " + item.getCollege()));
            holder.applicantPerc10.setText(("10th Percentage: " + item.getPerc10()));
            holder.applicantPerc12.setText(("12th Percentage: " + item.getPerc12()));
            holder.applicantPercUG.setText(("UG Percentage: " + item.getPercUG()));
            holder.applicantPercPG.setText(("PG Percentage: " + item.getPercPG()));
            holder.applicantLocation.setText(("Location: " + item.getLocation()));



            holder.accept.setOnClickListener(v -> {
                List<String> userdata = getlist("userdata");
                int index = userdata.indexOf(phone);
               userdata.set((index+10), "accepted");
               writelist(userdata,"userdata");
                Toast.makeText(v.getContext(), "Accepted: " + item.getName(), Toast.LENGTH_SHORT).show();

                // Removing applicant from applicantList
                applicants.remove(position);
                notifyDataSetChanged();
                // Adding applicant to acceptedApplicantList
                acceptedApplicantManager.addAcceptedApplicant(item);
                acceptedApplicantsAdapter.notifyDataSetChanged();
                applicant = AdminHome.applicantdata.size();
                acceptedApplicant = AdminHome.acceptedApplicantData.size();
                AdminHome.pending();
            });

            holder.reject.setOnClickListener(v -> {
                List<String> userdata = getlist("userdata");
                int index = userdata.indexOf(phone);
                userdata.set((index+4), "rejected");
                writelist(userdata,"userdata");
                Toast.makeText(v.getContext(), "Rejected: " + item.getName(), Toast.LENGTH_SHORT).show();
                applicants.remove(position);
                notifyDataSetChanged();
                applicant = AdminHome.applicantdata.size();
                acceptedApplicant = AdminHome.acceptedApplicantData.size();
                AdminHome.pending();
            });
        }


        @Override
        public int getItemCount() {

            return applicants.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView applicantName;
            public TextView applicantDomain;
            public TextView applicantDuration;
            public TextView applicantPaymentID;
            public TextView applicantPhone;
            public TextView applicantEmail;
            public TextView applicantBranch;
            public TextView applicantCollege;
            public TextView applicantPerc10;
            public TextView applicantPerc12;
            public TextView applicantPercUG;
            public TextView applicantPercPG;
            public  TextView applicantLocation;
            public Button accept;
            public Button reject;

            public ViewHolder(View itemView) {
                super(itemView);
                applicantName = itemView.findViewById(R.id.applicant_name);
                applicantDomain = itemView.findViewById(R.id.applicant_domain);
                applicantDuration = itemView.findViewById(R.id.applicant_duration);
                applicantPaymentID = itemView.findViewById(R.id.applicant_paymentid);
                applicantPhone = itemView.findViewById(R.id.applicant_phone);
                applicantEmail = itemView.findViewById(R.id.applicant_email);
                applicantBranch = itemView.findViewById(R.id.applicant_branch);
                applicantCollege = itemView.findViewById(R.id.applicant_college);
                applicantPerc10 = itemView.findViewById(R.id.applicant_perc10);
                applicantPerc12 = itemView.findViewById(R.id.applicant_perc12);
                applicantPercUG = itemView.findViewById(R.id.applicant_perc_ug);
                applicantPercPG = itemView.findViewById(R.id.applicant_perc_pg);
                applicantLocation = itemView.findViewById(R.id.applicant_location);
                accept = itemView.findViewById(R.id.accept);
                reject = itemView.findViewById(R.id.reject);
            }
        }
    }

    public static class AcceptedApplicantsAdapter extends RecyclerView.Adapter<AcceptedViewHolder> {
        private final List<ItemModel> acceptedApplicants;

        public AcceptedApplicantsAdapter(List<ItemModel> acceptedApplicants) {
            this.acceptedApplicants = acceptedApplicants;
        }

        @NonNull
        @Override
        public AcceptedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_accepted_applicants, parent, false);
            return new AcceptedViewHolder(itemView);
        }


        @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
        @Override
        public void onBindViewHolder(AcceptedViewHolder accholder, int acceptedposition) {
            ItemModel item = acceptedApplicants.get(acceptedposition);
            String name = item.getName();
            String domain = item.getDomain();
            String duration = item.getDuration();
            String paymentID = item.getPaymentID();
            String phone = item.getPhone();
            String email = item.getEmail();
            String branch = item.getBranch();
            String college = item.getCollege();
            String perc10 = item.getPerc10();
            String perc12 = item.getPerc12();
            String percUG = item.getPercUG();
            String percPG = item.getPercPG();
            String location = item.getLocation();

            accholder.acceptedApplicantName.setText(("Name: " + name));
            accholder.acceptedApplicantDomain.setText(("Domain: " + domain));
            accholder.acceptedApplicantDuration.setText(("Duration: " + duration));
            accholder.acceptedApplicantPaymentID.setText(("Payment ID: " + paymentID));
            accholder.acceptedApplicantPhone.setText(("Phone: " + phone));
            accholder.acceptedApplicantEmail.setText(("Email: " + email));
            accholder.acceptedApplicantBranch.setText(("Branch: " + branch));
            accholder.acceptedApplicantCollege.setText(("College: " + college));
            accholder.acceptedApplicantPerc10.setText(("10th Percentage: " + perc10));
            accholder.acceptedApplicantPerc12.setText(("12th Percentage: " + perc12));
            accholder.acceptedApplicantPercUG.setText(("UG Percentage: " + percUG));
            accholder.acceptedApplicantPercPG.setText(("PG Percentage: " + percPG));
            accholder.acceptedApplicantLocation.setText(("Location: " + location));

            // Set up button click listeners
            accholder.assign.setOnClickListener(v -> {
                List<String> userdata = getlist("userdata");
                int index = userdata.indexOf(phone);
                String assignedProject = AcceptedViewHolder.assignedDetails.getText().toString();
                userdata.set((index+11), assignedProject);
                writelist(userdata,"userdata");
                Toast.makeText(v.getContext(), "Project assigned successfully to" + name, Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String currentDateTime = dateFormat.format(calendar.getTime()) + " , " + timeFormat.format(calendar.getTime());
                AssignedInternManager interns = Singleton.getInstance(AssignedInternManager.class);
                AssignedInterns.ItemModelIntern newIntern = new AssignedInterns.ItemModelIntern(name,domain,duration,paymentID,phone,email,branch,college,perc10,perc12,percUG,percPG, assignedProject, currentDateTime, location);
                interns.addAssignedIntern(newIntern);
                acceptedApplicants.remove(acceptedposition);
                notifyDataSetChanged();
                applicant = AdminHome.applicantdata.size();
                acceptedApplicant = AdminHome.acceptedApplicantData.size();
                AdminHome.pending();
            });

        }

        @Override
        public int getItemCount() {
            return acceptedApplicants.size();
        }

    }

    public static class AcceptedViewHolder extends RecyclerView.ViewHolder {
        public TextView acceptedApplicantName;
        public TextView acceptedApplicantDomain;
        public TextView acceptedApplicantDuration;
        public TextView acceptedApplicantPaymentID;
        public TextView acceptedApplicantPhone;
        public TextView acceptedApplicantEmail;
        public TextView acceptedApplicantBranch;
        public TextView acceptedApplicantCollege;
        public TextView acceptedApplicantPerc10;
        public TextView acceptedApplicantPerc12;
        public TextView acceptedApplicantPercUG;
        public TextView acceptedApplicantPercPG;
        public TextView acceptedApplicantLocation;
        @SuppressLint("StaticFieldLeak")
        public static EditText assignedDetails;
        public Button assign;


        public AcceptedViewHolder(View itemView) {
            super(itemView);
            acceptedApplicantName = itemView.findViewById(R.id.accepted_name);
            acceptedApplicantDomain = itemView.findViewById(R.id.accepted_domain);
            acceptedApplicantDuration = itemView.findViewById(R.id.accepted_duration);
            acceptedApplicantPaymentID = itemView.findViewById(R.id.accepted_paymentid);
            acceptedApplicantPhone = itemView.findViewById(R.id.accepted_phone);
            acceptedApplicantEmail = itemView.findViewById(R.id.accepted_email);
            acceptedApplicantBranch = itemView.findViewById(R.id.accepted_branch);
            acceptedApplicantCollege = itemView.findViewById(R.id.accepted_college);
            acceptedApplicantPerc10 = itemView.findViewById(R.id.accepted_perc10);
            acceptedApplicantPerc12 = itemView.findViewById(R.id.accepted_perc12);
            acceptedApplicantPercUG = itemView.findViewById(R.id.accepted_perc_ug);
            acceptedApplicantPercPG = itemView.findViewById(R.id.accepted_perc_pg);
            acceptedApplicantLocation = itemView.findViewById(R.id.accepted_location);
            assignedDetails = itemView.findViewById(R.id.assigned_details);
            assign = itemView.findViewById(R.id.assign);
        }
    }


}
