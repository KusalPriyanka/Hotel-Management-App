package Modal;

import java.io.Serializable;

public class ShortEats  implements  Serializable {
    protected String id;
    protected String name;
    protected float price;
    private boolean pastry;
    private boolean pizza;
    private boolean drinks;
    private String image;


    public ShortEats() {
    }

    public ShortEats(String id, String name, float price, boolean pastry, boolean pizza, boolean drinks, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.pastry = pastry;
        this.pizza = pizza;
        this.drinks = drinks;
        this.image = image;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isPastry() {
        return pastry;
    }

    public void setPastry(boolean pastry) {
        this.pastry = pastry;
    }

    public boolean isPizza() {
        return pizza;
    }

    public void setPizza(boolean pizza) {
        this.pizza = pizza;
    }

    public boolean isDrinks() {
        return drinks;
    }

    public void setDrinks(boolean drinks) {
        this.drinks = drinks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ShortEats{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", pastry=" + pastry +
                ", pizza=" + pizza +
                ", drinks=" + drinks +
                ", image='" + image + '\'' +
                '}';
    }
}
