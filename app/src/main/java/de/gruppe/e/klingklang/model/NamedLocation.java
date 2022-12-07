package de.gruppe.e.klingklang.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NamedLocation implements Parcelable {
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

    protected NamedLocation(Parcel in) {
        longitude = in.readDouble();
        latitude = in.readDouble();
        radius = in.readInt();
        address = in.readString();
        shortName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeInt(radius);
        dest.writeString(address);
        dest.writeString(shortName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NamedLocation> CREATOR = new Creator<NamedLocation>() {
        @Override
        public NamedLocation createFromParcel(Parcel in) {
            return new NamedLocation(in);
        }

        @Override
        public NamedLocation[] newArray(int size) {
            return new NamedLocation[size];
        }
    };

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
