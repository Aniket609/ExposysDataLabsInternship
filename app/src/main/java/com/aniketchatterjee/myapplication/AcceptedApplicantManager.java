package com.aniketchatterjee.myapplication;

import java.util.ArrayList;
import java.util.List;

public class AcceptedApplicantManager {
    private static AcceptedApplicantManager instance;
    private List<AdminHome.ItemModel> acceptedApplicants;

    AcceptedApplicantManager() {
        acceptedApplicants = new ArrayList<>();
    }

    public static AcceptedApplicantManager getInstance() {
        if (instance == null) {
            instance = new AcceptedApplicantManager();
        }
        return instance;
    }

    public void addAcceptedApplicant(AdminHome.ItemModel applicant) {

        acceptedApplicants.add(applicant);
    }

    public List<AdminHome.ItemModel> getAcceptedApplicants() {

        return acceptedApplicants;
    }
}
