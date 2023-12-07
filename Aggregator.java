package com.codegym.task.task28.task2810;

import com.codegym.task.task28.task2810.model.*;
import com.codegym.task.task28.task2810.view.HtmlView;
import com.codegym.task.task28.task2810.view.View;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;

public class Aggregator {

    public static void main(String[] args) {

        HtmlView view = new HtmlView();
        Model model = new Model(view, new Provider(new LinkedinStrategy()), new Provider(new IndeedStrategy()));
        Controller controller = new Controller(model);
        view.setController(controller);
        view.emulateCitySelection();


//        System.out.println(view.filePath);

    }
}
