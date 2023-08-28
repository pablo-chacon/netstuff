package com.example.netstuff.network;


import com.example.netstuff.crawler.Crawler;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class NetStuffClient {


    private Socket client;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String message;
    private Crawler urlCrawler;


    public NetStuffClient(Socket client) throws IOException {
        // establish a connection
        this.client = client;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.message = new Scanner(System.in).next();
        this.urlCrawler = new Crawler();

    }

    public void sendMessage() throws IOException {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            while(client.isConnected()) {
                System.out.println("Message (return to send) >");
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeClient(client, bufferedReader, bufferedWriter);
        }
    }

    public void clientThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(client.isConnected()) {
                    try {
                        message = bufferedReader.readLine();
                        urlCrawler.crawl(message, 10);
                    } catch(IOException e) {
                        closeClient(client, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeClient(Socket client, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (bufferedWriter != null) {
                bufferedWriter.close();
            }

            if (client != null) {
                client.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 8083);
        NetStuffClient netStuffClient = new NetStuffClient(socket);
        netStuffClient.clientThread();
        netStuffClient.sendMessage();


    }



}