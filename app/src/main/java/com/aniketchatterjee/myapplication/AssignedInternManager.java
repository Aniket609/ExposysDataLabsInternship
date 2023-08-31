package com.aniketchatterjee.myapplication;

import java.util.ArrayList;
import java.util.List;

public class AssignedInternManager {
    private static AssignedInternManager instance;
    private List<AssignedInterns.ItemModelIntern> assignedInterns;

    AssignedInternManager()
    {
        assignedInterns = new ArrayList<>();
    }

    public static AssignedInternManager getInstance() {
        if (instance == null) {
            instance = new AssignedInternManager();
        }
        return instance;
    }

    public void addAssignedIntern(AssignedInterns.ItemModelIntern intern) {

        assignedInterns.add(intern);
    }

    public List<AssignedInterns.ItemModelIntern> getAssignedInterns() {

        return assignedInterns;
    }
}
