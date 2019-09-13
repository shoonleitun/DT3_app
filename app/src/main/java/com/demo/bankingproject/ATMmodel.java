package com.demo.bankingproject;

class ATMmodel {

String atmname,address,lat,lng;

    public String getAtmname() {
        return atmname;
    }

    public void setAtmname(String atmname) {
        this.atmname = atmname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public ATMmodel(String atmname, String address, String lat, String lng) {

        this.atmname = atmname;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }
}
