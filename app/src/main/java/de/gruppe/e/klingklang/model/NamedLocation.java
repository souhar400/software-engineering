package de.gruppe.e.klingklang.model;

public class NamedLocation {
    private final double longitude;
    private final double latitude;
    private final int radius;
    private final String address;
    private final String shortName;

    public NamedLocation(double latitude,
                         double longitude,
                         int radius,
                         String adress,
                         String shortName) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.address = adress;
        this.shortName = shortName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getRadius() {
        return radius;
    }

    public String getAddress() {
        return address;
    }

    public String getShortName() {
        return shortName;
    }
}
