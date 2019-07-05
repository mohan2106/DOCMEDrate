package com.example.docmedrate;

import com.google.firebase.firestore.GeoPoint;

public class doctor_details_class {
    private String name;
    private String address;
    private String speciality;

    public doctor_details_class(String name, String address, String speciality) {
        this.name = name;
        this.address = address;
        this.speciality = speciality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

}
