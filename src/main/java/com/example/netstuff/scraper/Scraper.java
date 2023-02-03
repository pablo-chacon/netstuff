package com.example.netstuff.scraper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Scraper {


    public static void main(String[] args) throws IOException {

        Scraper scraper = new Scraper();
        //String rootURL = "www.cryptocompare.com";
        final String htmlContent = scraper.getContent();
        final String extractedTitle = scraper.extractTitle(htmlContent);
        System.out.println(extractedTitle);
    }

    public static String getContent() throws IOException {
        final OkHttpClient client = new OkHttpClient.Builder().build();
        final Request request = new Request.Builder().url("http://www.cryptocompare.com").build();
        final Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String extractTitle (String htmlContent){
        final Pattern titleRegExp = Pattern.compile("<title>(.*?)</title>", Pattern.DOTALL);
        final Matcher matcher = titleRegExp.matcher(htmlContent);
        matcher.find();
        return matcher.group(1);
    }
}