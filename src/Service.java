class Service {
    private String serviceID;
    private String serviceType;
    private Double servicePrice;
    private String serviceDesc;
    static int numService = 1;

    public Service() {

    }

    public Service(String serviceID, String serviceType, Double servicePrice, String serviceDesc) {
        String sNum = "000";
        sNum = String.format("%03d" , numService);
        this.serviceID = serviceID+sNum;
        numService++;
        this.serviceType = serviceType;
        this.servicePrice = servicePrice;
        this.serviceDesc = serviceDesc;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public Double getServicePrice() {
        return servicePrice;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }


    public void setServiceID(String serviceID){
        this.serviceID = serviceID;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String toString(){
        System.out.printf("%-15s %-27s %-23.2f %s", getServiceID(),  getServiceType(), getServicePrice(), getServiceDesc());
        return " ";
    }
}
