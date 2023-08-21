class Car {
    private String carNumPlate;
    private String carName;
    private String carType;
    private String contactPersonName;
    private String carOwnerHP;
    Customer customer;

    public Car() {

    }

    public Car(String carNumPlate, String carName, String carType, String contactPersonName, String carOwnerHP, Customer customer) {
        this.carNumPlate = carNumPlate;
        this.carName = carName;
        this.carType = carType;
        this.contactPersonName = contactPersonName;
        this.carOwnerHP = carOwnerHP;
        this.customer = customer;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public void setCarOwnerHP(String carOwnerHP) {
        this.carOwnerHP = carOwnerHP;
    }

    public String getCarOwnerHP() {
        return carOwnerHP;
    }

    public String getCarNumPlate() {
        return carNumPlate;
    }

    public String getCarName() {
        return carName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarNumPlate(String carNumPlate) {
        this.carNumPlate = carNumPlate;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String toString(){
        System.out.printf("%-15s %-15s %-15s %-19s %s", getCarNumPlate(), getCarName(), getCarType(), getContactPersonName(), getCarOwnerHP());
        return " ";
    }

    public Customer getCustomer(){
        return customer;
    }
}
