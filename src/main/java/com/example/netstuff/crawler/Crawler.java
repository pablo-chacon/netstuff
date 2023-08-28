package com.example.netstuff.crawler;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Crawler {

    private Queue<String> urlQueue;
    private List<String> visitedURLs;


    public Crawler() {
        urlQueue = new LinkedList<>();
        visitedURLs = new ArrayList<>();
    }

    public void crawl(String rootURL, int breakpoint) {
        urlQueue.add(rootURL);
        visitedURLs.add(rootURL);

        while(!urlQueue.isEmpty()){

            // remove next url and crawl.
            String s = urlQueue.remove();
            String rawHTML = "";
            try{
                // Convert URL to String.
                URL url = new URL(s);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String inputLine = in.readLine();

                // ReadHTML content in the URL, concat line to rawHTML.
                while(inputLine  != null){
                    rawHTML += inputLine;

                    inputLine = in.readLine();
                }
                in.close();
            } catch (Exception e){
                e.printStackTrace();
            }

            // regex pattern matches URL.
            String urlPattern = "(www|http:|https:)+[^\s]+[\\w]";
            Pattern pattern = Pattern.compile(urlPattern);
            Matcher matcher = pattern.matcher(rawHTML);

            //When regex matches URL in HTML, add urlQueue..
            breakpoint = getBreakpoint(breakpoint, matcher);

            // exit loop.
            if(breakpoint == 0){
                break;
            }
        }
    }

    private int getBreakpoint(int breakpoint, Matcher matcher) {
        while(matcher.find()){
            String actualURL = matcher.group();

            if(!visitedURLs.contains(actualURL)){
                visitedURLs.add(actualURL);
                System.out.println("Crawler URL results: " + actualURL);
                urlQueue.add(actualURL);
            }
            // exit the loop.
            if(breakpoint == 0){
                break;
            }
            breakpoint--;
        }
        return breakpoint;
    }

}

