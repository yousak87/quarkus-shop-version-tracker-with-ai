package com.skrill.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

@ApplicationScoped
public class JsoupService {

    public String getWebContent(String url, String repoType) throws IOException {

        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("body");
        if(repoType.equals("github")) {
            elements = doc.select(".Box");
        } else if (repoType.equals("gitlab")) {
            elements = doc.select(".content-list");
        }

        String contentResult = "";
        if(elements.get(0) != null) {
            contentResult = elements.get(0).text();
        } else {
            contentResult = elements.text();
        }
        return contentResult;
    }
}
