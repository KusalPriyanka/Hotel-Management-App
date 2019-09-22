package Modal;

public class Packages {

    private String id;
    private String name;
    private String des;
    private int beds;
    private int people;
    private float price;
    private String url;
    private boolean tv, ac, sp, wf = false;

    public Packages() { }

    public Packages(String id, String name, String des, int beds, int people, float price, String url, boolean tv, boolean ac, boolean sp, boolean wf) {
        this.name = name;
        this.des = des;
        this.beds = beds;
        this.people = people;
        this.price = price;
        this.url = url;
        this.tv = tv;
        this.ac = ac;
        this.sp = sp;
        this.wf = wf;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isAc() {
        return ac;
    }

    public void setAc(boolean ac) {
        this.ac = ac;
    }

    public boolean isSp() {
        return sp;
    }

    public void setSp(boolean sp) {
        this.sp = sp;
    }

    public boolean isWf() {
        return wf;
    }

    public void setWf(boolean wf) {
        this.wf = wf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
