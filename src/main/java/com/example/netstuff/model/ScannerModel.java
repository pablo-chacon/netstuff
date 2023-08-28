package com.example.netstuff.model;

public class ScannerModel {

    private String ipAddress;
    private int maxPortNum;


    public ScannerModel(String ipAddress) {
        this.ipAddress = ipAddress;
        this.maxPortNum = maxPortNum;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getMaxPortNum() {
        return maxPortNum;
    }

    public void setMaxPortNum(int maxPortNum) {
        this.maxPortNum = maxPortNum;
    }
}