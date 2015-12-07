package wzmame.pictour.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Location")
public class Location extends ParseObject {

    private boolean latitudeSet = false;
    private boolean longitudeSet = false;

    public Location() { }

    public String getTourId() {
        return getString("tourId");
    }

    public void setTourId(String tourId) {
        put("tourId", tourId);
    }

    public String getName() {
        String name = getString("name");
        if (name == null) {
            return "";
        }

        return name;
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getDescription() {
        String description = getString("description");
        if (description == null) {
            return "";
        }

        return description;
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public ParseFile getPicture() {
        return getParseFile("picture");
    }

    public void setPicture(ParseFile picture) {
        put("picture", picture);
    }

    public Double getLatitude() {
        if (!latitudeSet) {
            return null;
        }

        return getDouble("latitude");
    }

    public void setLatitude(double latitude) {
        latitudeSet = true;
        put("latitude", latitude);
    }

    public Double getLongitude() {
        if (!longitudeSet) {
            return null;
        }

        return getDouble("longitude");
    }

    public void setLongitude(double longitude) {
        longitudeSet = true;
        put("longitude", longitude);
    }
}
