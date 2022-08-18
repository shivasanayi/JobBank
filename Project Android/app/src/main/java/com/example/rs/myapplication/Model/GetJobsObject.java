package com.example.rs.myapplication.Model;

public class GetJobsObject {
    private String Id;
    private String UserId;
    private String Name;
    private String Manager;
    private String City;
    private String Address;

    public GetJobsObject(String Id,String UserId,String Name,String Manager,String City,String Address) {
        this.Id=Id;
        this.UserId=UserId;
        this.Name=Name;
        this.Manager=Manager;
        this.City=City;
        this.Address=Address;
    }

    public GetJobsObject() {
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getManager() {
        return Manager;
    }

    public void setManager(String manager) {
        Manager = manager;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
