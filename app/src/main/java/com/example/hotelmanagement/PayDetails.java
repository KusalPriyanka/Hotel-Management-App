package com.example.hotelmanagement;

public class PayDetails {
    private String Pdate;
    private  String Ptime;
    private String spin;
    private String place;
    private String Vehicle;
    private String cName;
    private String cNo;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcNo() {
        return cNo;
    }

    public void setcNo(String cNo) {
        this.cNo = cNo;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public void setVehicle(String vehicle) {
        Vehicle = vehicle;
    }

    public String getSpin() {
        return spin;
    }

    public void setSpin(String spin) {
        this.spin = spin;
    }

    public String getPdate() {
        return Pdate;
    }

    public void setPdate(String pdate) {
        Pdate = pdate;
    }

    public String getPtime() {
        return Ptime;
    }

    public void setPtime(String ptime) {
        Ptime = ptime;
    }

    public PayDetails() {
    }
}
