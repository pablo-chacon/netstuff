package com.example.netstuff.portscanner;


import java.net.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortScanner {


    public static void main(String[] args) throws IOException {
        PortScanner portScanner = new PortScanner();
        try {
            portScanner.PortScan("8.8.8.8", 300);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Queue PortScan(String ip, int maxPortNum) throws IOException {
        ConcurrentLinkedQueue openPorts = new ConcurrentLinkedQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        AtomicInteger port = new AtomicInteger(0);
        while (port.get() < maxPortNum) {
            final int currentPort = port.getAndIncrement();
            executorService.submit(() -> {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, currentPort), 100);
                    socket.close();
                    openPorts.add(currentPort);
                } catch (IOException e) {
                    System.err.println(e);
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List openPortList = new ArrayList<>();
        System.out.println("Found " + openPorts.size() + " open ports.");
        while (!openPorts.isEmpty()) {
            openPortList.add(openPorts.poll());
        }
        openPortList.forEach(p -> System.out.println("port " + p + " is open"));
        return openPorts;
    }

    public String urlToIP(String rootURL) {

        String ipAddress = "";
        try {
            // Fetch IP address by getByName()
            InetAddress ip = InetAddress.getByName(new URL(rootURL)
                    .getHost());

            ipAddress = String.valueOf(ip);
        } catch (MalformedURLException | UnknownHostException e) {
            // It means the URL is invalid
            System.out.println("Invalid URL");
        }
        return ipAddress;
    }

    public String extractIpOnly(String ip) {
        List<String> ipAddress = new ArrayList<>();
        Pattern pattern = Pattern.compile(String.valueOf(ip.matches("[0-9.]+")), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ip);
        while (matcher.find()) {
            ipAddress.add(ip.substring(matcher.start(0), matcher.end(0)));
            ipAddress.add(pattern.pattern());
        }
        if (ipAddress.size() == 0) {
            ipAddress.add(pattern.pattern());
            System.out.println("List is empty.");
        }
        for (String str : ipAddress) {
            return String.valueOf(ipAddress);

        }
        return String.valueOf(ipAddress);
    }
}