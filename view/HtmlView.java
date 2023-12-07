package com.codegym.task.task28.task2810.view;

import com.codegym.task.task28.task2810.Controller;
import com.codegym.task.task28.task2810.vo.JobPosting;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;



public class HtmlView implements View{

    Controller controller;
    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/jobPostings.html";

    @Override
    public void update(List<JobPosting> jobPostings) {
        updateFile(getUpdatedFileContents(jobPostings));
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void emulateCitySelection(){
        controller.onCitySelected("Odessa");
    }

    private String getUpdatedFileContents(List<JobPosting> jobPostingList){
        try {
            Document doc = getDocument();
            Elements templateHidden = doc.getElementsByClass("template");
            Element template = templateHidden.clone().removeAttr("style").removeClass("template").get(0);

            //remove all prev vacancies
            Elements prevVacancies = doc.getElementsByClass("vacancy");

            for (Element redundant : prevVacancies) {
                if (!redundant.hasClass("template")) {
                    redundant.remove();
                }
            }

            //add new vacancies
            for (JobPosting vacancy : jobPostingList) {
                Element vacancyElement = template.clone();

                Element vacancyLink = vacancyElement.getElementsByAttribute("href").get(0);
                vacancyLink.appendText(vacancy.getTitle());
                vacancyLink.attr("href", vacancy.getUrl());
                Element city = vacancyElement.getElementsByClass("city").get(0);
                city.appendText(vacancy.getCity());
                Element companyName = vacancyElement.getElementsByClass("companyName").get(0);
                companyName.appendText(vacancy.getCompanyName());

                templateHidden.before(vacancyElement.outerHtml());
            }
            return doc.html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Some exception occurred";
    }

    private void updateFile(String s) {
        File file = new File(filePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            fileOutputStream.write(s.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected Document getDocument() throws IOException{

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String content = "";
        while(bufferedReader.ready()) {
            content += bufferedReader.readLine();
        }
        bufferedReader.close();

        Document document = Jsoup.parse(content);
        return document;

    }
}
