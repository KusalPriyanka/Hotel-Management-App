package Modal;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EM_HallManagement implements Serializable {

    private String id;
    private String name;
    private Float price;
    private String description;
    private  boolean wedding;
    private  boolean events;
    private String imageName;

    public EM_HallManagement() {
    }

    public EM_HallManagement(String id, String name, Float price, String description, boolean wedding, boolean events, String imageName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.wedding = wedding;
        this.events = events;
        this.imageName = imageName;
    }

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isWedding() {
        return wedding;
    }

    public void setWedding(boolean wedding) {
        this.wedding = wedding;
    }

    public boolean isEvents() {
        return events;
    }

    public void setEvents(boolean events) {
        this.events = events;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "EM_HallManagement{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", wedding=" + wedding +
                ", events=" + events +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
