package com.example.contentprovider;

public class SMS {
    private String address;
    private String body;

    public SMS(String address, String body) {
        this.address = address;
        this.body = body;
    }

    public String getAddress() {
        return address;
    }

    public String getBody() {
        return body;
    }
}
