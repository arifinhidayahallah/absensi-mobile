package com.example.vsgo_juniormobileprogrammer.models;

public class PresenceModel {
    private String id;
    private String date;
    private String onDuty;
    private String offDuty;
    private String nim;

    public PresenceModel( String nim, String date, String onDuty, String offDuty) {
        this.date = date;
        this.onDuty = onDuty;
        this.offDuty = offDuty;
        this.nim = nim;
    }
    public PresenceModel(String id,String nim, String date, String onDuty, String offDuty) {
        this.date = date;
        this.onDuty = onDuty;
        this.offDuty = offDuty;
        this.nim = nim;
        this.id = id;
    }


    public String getId(){ return id; }

    public void setId(String id){this.id = id;}

    public String getNim(){
        return nim;
    }

    public void setNim(String nim){
        this.nim = nim;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOnDuty() {
        return onDuty;
    }

    public void setOnDuty(String onDuty) {
        this.onDuty = onDuty;
    }

    public String getOffDuty() {
        return offDuty;
    }

    public void setOffDuty(String offDuty) {
        this.offDuty = offDuty;
    }
}
