package wzmame.pictour.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Tour")
public class Tour extends ParseObject {
    public Tour() { }

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
}
