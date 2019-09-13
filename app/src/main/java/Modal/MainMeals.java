package Modal;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MainMeals implements Serializable {
    protected String id;
    protected String mealName;
    protected float normalPrice;
    protected String imageName;
    private String type;
    private float largePrice;
    private boolean brakfast;
    private boolean lunch;
    private boolean dinner;


    public MainMeals() {
    }

    public MainMeals(String id, String mealName, float normalPrice, String imageName, String type, float largePrice, boolean brakfast, boolean lunch, boolean dinner) {
        this.id = id;
        this.mealName = mealName;
        this.normalPrice = normalPrice;
        this.imageName = imageName;
        this.type = type;
        this.largePrice = largePrice;
        this.brakfast = brakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public float getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(float normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getLargePrice() {
        return largePrice;
    }

    public void setLargePrice(float largePrice) {
        this.largePrice = largePrice;
    }

    public boolean isBrakfast() {
        return brakfast;
    }

    public void setBrakfast(boolean brakfast) {
        this.brakfast = brakfast;
    }

    public boolean isLunch() {
        return lunch;
    }

    public void setLunch(boolean lunch) {
        this.lunch = lunch;
    }

    public boolean isDinner() {
        return dinner;
    }

    public void setDinner(boolean dinner) {
        this.dinner = dinner;
    }

    @Override
    public String toString() {
        return "MainMeals{" +
                "id='" + id + '\'' +
                ", mealName='" + mealName + '\'' +
                ", normalPrice=" + normalPrice +
                ", imageName='" + imageName + '\'' +
                ", type='" + type + '\'' +
                ", largePrice=" + largePrice +
                ", brakfast=" + brakfast +
                ", lunch=" + lunch +
                ", dinner=" + dinner +
                '}';
    }
}
