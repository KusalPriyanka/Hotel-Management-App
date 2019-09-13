package Modal;

public class Customer {

    private String userName;
    private String mobileNo;
    private String email;
    private String role = "Customer";

    public Customer() { }

    public Customer(String userName, String mobileNo, String email) {
        this.userName = userName;
        this.mobileNo = mobileNo;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer Name : " + this.userName;
    }
}
