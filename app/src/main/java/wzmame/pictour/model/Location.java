package wzmame.pictour.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Location")
public class Location extends ParseObject {

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

    public Number getLat() {
        return getNumber("lat");
    }

    public void setLat(double lat) {
        put("lat", lat);
    }

    public Number getLng() {
        return getNumber("lng");
    }

    public void setLng(double lng) {
        put("lng", lng);
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
}
