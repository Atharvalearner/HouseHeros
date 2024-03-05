package com.example.constructionmodel;

public class Worker_summerize {
    private int id;
    private String name,address;
    private int image,experience;
    private long contact;

    public Worker_summerize(int id, String name, String address, int image, int experience, long contact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
        this.experience = experience;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }
}
