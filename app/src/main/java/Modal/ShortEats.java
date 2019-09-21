package Modal;

public class ShortEats {
    protected String id;
    protected String shortEatName;
    protected float price;
    protected String imageName;

    public ShortEats() {
    }

    public ShortEats(String id, String shortEatName, float price, String imageName) {
        this.id = id;
        this.shortEatName = shortEatName;
        this.price= price;
        this.imageName = imageName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortEatName() {
        return shortEatName;
    }

    public void setShortEatName(String shortEatName) {
        this.shortEatName = shortEatName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "ShortEats{" +
                "id='" + id + '\'' +
                ", shortEatName='" + shortEatName + '\'' +
                ", price=" + price +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
