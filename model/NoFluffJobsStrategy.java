package com.codegym.task.task28.task2810.model;

import com.codegym.task.task28.task2810.vo.JobPosting;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class NoFluffJobsStrategy implements Strategy{

//    https://nofluffjobs.com/Java?page=1&criteria=keyword%3Dtest1,test2
//    https://nofluffjobs.com/Java?page=PAGE_VALUE&criteria=keyword%3DADDITIONAL_VALUE

    private static final String URL_FORMAT = "https://nofluffjobs.com/Java?page=%d&criteria=keyword%%3D%s";

    @Override
    public List<JobPosting> getJobPostings(String searchString) {
        String url = String.format(URL_FORMAT, 1, searchString);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
