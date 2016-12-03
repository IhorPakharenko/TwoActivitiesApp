package com.example.isao.twoactivities2.model;


public class Contact {
    private String contactName;
    private String contactNumber;

    public Contact (String contactName, String contactNumber) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }
    public String getContactNumber() {
        return contactNumber;
    }
}
