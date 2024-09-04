package com.example.vsgo_juniormobileprogrammer.models;

public class UserModel {
    private String nim;
    private String fullname;
    private String birthdate;
    private String gender;
    private String password;

    public UserModel(String nim, String fullname, String birthdate, String gender, String password){
        this.setNim(nim);
        this.setFullname(fullname);
        this.setBirthdate(birthdate);
        this.setGender(gender);
        this.setPassword(password);
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
