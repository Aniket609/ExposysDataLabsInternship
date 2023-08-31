package com.aniketchatterjee.myapplication;

import java.util.ArrayList;
import java.util.List;

public class ApplicantManager {
    private static ApplicantManager instance;
    private List<AdminHome.ItemModel> applicants;

    ApplicantManager() {
        applicants = new ArrayList<>();
    }

    public static ApplicantManager getInstance() {
        if (instance == null) {
            instance = new ApplicantManager();
        }
        return instance;
    }

    public void addApplicant(AdminHome.ItemModel applicant) {
        applicants.add(applicant);
    }

    public List<AdminHome.ItemModel> getApplicants() {
        return applicants;
    }
}
