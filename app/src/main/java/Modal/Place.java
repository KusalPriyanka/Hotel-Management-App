package Modal;

import com.google.firebase.database.Exclude;

public class Place {

    String id;
    String name;
    String details;

    public Place() {
    }

    public Place(String name, String details) {
        this.name = name;
        this.details = details;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
