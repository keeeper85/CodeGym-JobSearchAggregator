package com.codegym.task.task28.task2810.model;

import com.codegym.task.task28.task2810.vo.JobPosting;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinkedinStrategy implements Strategy{

//    https://www.linkedin.com/jobs/search?keywords=Java+San+Francisco&start=25
//    https://www.linkedin.com/jobs/search?keywords=Java+ADDITIONAL_VALUE&start=PAGE_VALUE

    private static final String URL_FORMAT = "https://www.linkedin.com/jobs/search?keywords=Java+%s&start=%d";
//    private static final String TEST_URL = "http://codegym.cc/testdata/big28linkedin.html";

    protected Document getDocument(String searchString, int page) throws IOException {
        Document doc = null;
        doc = Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                .referrer("http://www.google.com")
                .get();
        return doc;
    }

    @Override
    public List<JobPosting> getJobPostings(String searchString) {
        List<JobPosting> list = new ArrayList<>();
        try {
            int i = 0;
            while (true) {
                Document document = getDocument(searchString, i);
                if (!document.getElementsByClass("jobs-search-result-item").isEmpty()) {
                    for (Element e : document.getElementsByClass("jobs-search-result-item")) {
                        JobPosting job = new JobPosting();
                        job.setTitle(e.getElementsByClass("listed-job-posting__title").text());
                        job.setCity(e.getElementsByClass("listed-job-posting__location").text());
                        job.setCompanyName(e.getElementsByClass("listed-job-posting__company").text());
                        job.setWebsiteName(URL_FORMAT);
                        Element link = e.select("meta[itemprop=url]").first();
                        job.setUrl(link.attr("content"));
                        if(!list.contains(job))
                            list.add(job);
                    }
                    i++;
                }
                else break;
            }
        }
        catch (IOException ignored){}

        return list;
    }
}
