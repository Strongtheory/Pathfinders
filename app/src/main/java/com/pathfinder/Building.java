package com.pathfinder;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class representing a building on GT Campus
 * Connor Reeder
 */

public class Building implements FilterableItem {
    private double latitude;
    private double longitude;
    private String name;
    private String address;
    private long id;
    private String url;

    public Building(String name, String address, double latitude, double longitude, long id, String url) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.url = url;
    }
    public Building (JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.address = jsonObject.getString("address");
            this.latitude = jsonObject.getDouble("latitude");
            this.longitude = jsonObject.getDouble("longitude");
            this.id = jsonObject.getInt("id");
            this.url = jsonObject.getString("imageURL");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String valueToFiler() {
        return name;
    }
}
