package com.codegym.task.task28.task2810.model;

import com.codegym.task.task28.task2810.view.View;
import com.codegym.task.task28.task2810.vo.JobPosting;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private View view;
    private Provider[] providers;

    public Model(View view, Provider...providers) {
        if (view == null || providers == null || providers.length == 0){
            throw new IllegalArgumentException();
        }
        else{
            this.view = view;
            this.providers = providers;
        }
    }

    public void selectCity(String city){

        List<JobPosting> jobPostings = new ArrayList<>();

        for (Provider provider : providers) {
            jobPostings.addAll(provider.getJavaJobPostings(city));
        }

        view.update(jobPostings);
    }
}
