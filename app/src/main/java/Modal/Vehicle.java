package Modal;

import com.google.firebase.database.Exclude;

public class Vehicle {

    String id;
    double price;
    String name;

    public Vehicle() {
    }

    public Vehicle(double price, String name) {
        this.price = price;
        this.name = name;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
