package com.example.netstuff.networkmap;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
//Network Mapper.

public class NetworkMapper {


    public static void mapNetwork() throws SocketException, UnknownHostException {
        // Get all network interfaces
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

        // Iterate network interfaces
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();

            // Get IP addresses
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

            // Iterate IP addresses
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                System.out.println("Interface: " + networkInterface.getDisplayName());
                System.out.println("IP Address: " + inetAddress.getHostAddress());
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        try {
            mapNetwork();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }
}