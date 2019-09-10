package Modal;

public class MainMeals extends Meals {
    private String type;
    private float largePrice;
    private boolean brakfast;
    private boolean lunch;
    private boolean dinner;

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
                "type='" + type + '\'' +
                ", largePrice=" + largePrice +
                ", brakfast=" + brakfast +
                ", lunch=" + lunch +
                ", dinner=" + dinner +
                ", id=" + id +
                ", mealName='" + mealName + '\'' +
                ", normalPrice=" + normalPrice +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
