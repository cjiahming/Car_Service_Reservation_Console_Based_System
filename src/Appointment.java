import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private String appointmentID;
    private String appointmentDesc;
    private Car car;
    private Payment payment;
    private Payment deposit;
    private LocalDateTime appointmentTime;
    private Service service;

    public Appointment(){}


    public Appointment(String appointmentID , String appointmentDesc ,Car car, LocalDateTime appointmentTime , Service serviceType,Payment deposit,Payment payment){
        this.appointmentID = appointmentID;
        this.appointmentDesc = appointmentDesc;
        this.car = car;
        this.appointmentTime = appointmentTime;
        this.service = serviceType;
        this.deposit = deposit;
        this.payment = payment;
    }

    //use for add reservation
    public Appointment(Payment deposit ,LocalDateTime appointmentTime , Service service,Car car,String appointmentDesc,Payment payment){
        this.appointmentTime = appointmentTime;
        this.service = service;
        this.car = car;
        this.appointmentID = generateAppointmentID(appointmentTime);
        this.appointmentDesc = appointmentDesc;
        this.deposit = deposit;
        this.payment=payment;
    }

    public Payment getDeposit(){
        return deposit;
    }


    public String getAppointmentID(){
        return appointmentID;
    }

    public String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return appointmentTime.format(formatter);
    }

    public int getHour(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        return Integer.parseInt(appointmentTime.format(formatter));
    }

    public LocalDateTime getAppointmentTime(){
        return this.appointmentTime;
    }

    public Car getCar(){
        return car;
    }

    public Payment getPayment() {
        return payment;
    }

    public Service getService(){
        return service;
    }

    public void setDeposit(Payment deposit){
        this.deposit = deposit;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime){
        this.appointmentID = generateAppointmentID(appointmentTime);
        this.appointmentTime = appointmentTime;
    }

    public void setService(Service service){
        this.service = service;
    }

    public void setPayment(Payment payment){
        this.payment = payment;
    }

    public String generateAppointmentID(LocalDateTime specificTime){
        //A2209202101
        //Appointment + Date + 01(depends on hours)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH");
        String number ; // number behind (etc : 01 , 02)

        if(specificTime.format(formatter1).equals("08")){
            number = "01";
        }else if(specificTime.format(formatter1).equals("10")){
            number = "02";
        }else if(specificTime.format(formatter1).equals("12")){
            number = "03";
        }else if(specificTime.format(formatter1).equals("14")){
            number = "04";
        }else if(specificTime.format(formatter1).equals("16")){
            number = "05";
        }else{
            //18.00
            number = "06";
        }

        return ("A"+specificTime.format(formatter)+number);
    }

    //Once deposit is confirm will return the payment object to appointment to make a payment
    public String toString(){
        char status;
        String color1;
        String colorReset="\033[0m";
        if(payment==null){
            status = 'X';
            color1 = "\u001B[31m";
        }
        else{
            status = '/';
            color1 = "\u001B[32m";
        }
        return String.format("|%-16s %-11s %-6s %-11s %-20s %-11s %s%5c%s          %10s|",appointmentID , getDate() , String.format("%02d:00" , getHour()) , deposit.getPaymentId() , service.getServiceType() , car.getCarNumPlate() , color1,status,colorReset ,car.getCustomer().getUserID());
    }

    public String getAppointmentDesc(){
        return appointmentDesc;
    }


    //--------------------------------------------------------- For Payment ToString Part ---------------------------------------------------------
    public String checkFullPaymentToString(){
        double subtotal = getService().getServicePrice() + (0.04 * getService().getServicePrice()) + (0.06 * getService().getServicePrice());
        char fullPayment;
        fullPayment=payment==null?'X':'/';
        if(payment==null) {
            //Show deposit details
            return String.format("%-11s      %-15s  %-12s      %-16s    %-10s         RM 100.00             %-2s\n", getDeposit().getPaymentId(),getCar().getCustomer().getUserID(),
             getCar().getCustomer().getPhoneNumber(), appointmentID, appointmentTime.toLocalDate(), fullPayment);
        }
        //Show full payment details
        return String.format("%-11s      %-15s  %-12s      %-16s    %-10s         RM%7.2f             %-2s\n",getPayment().getPaymentId(), getCar().getCustomer().getUserID(),
         getCar().getCustomer().getPhoneNumber(), appointmentID, appointmentTime.toLocalDate(),subtotal, fullPayment);
    }

    public String fullPaymentToString(){
        double subtotal = getService().getServicePrice() + (0.04 * getService().getServicePrice()) + (0.06 * getService().getServicePrice());
        return String.format("  %-10s       %-10s       %-10s         %-18s%-13s   RM%-5.2f          %-20s %-15s\n",getPayment().getPaymentId(),getCar().getCustomer().getUserID(),
         getAppointmentID(),
                getCar().getCustomer().getName(), getCar().getCustomer().getPhoneNumber(), subtotal,
                getPayment().getPaymentDateTime().toLocalDate(), getPayment().getPaymentType());
    }

    public String depositPaymentToString(){
        return String.format(" %-10s       %-10s       %-10s        %-18s%-13s   RM100.00         %-20s%-15s\n",getDeposit().getPaymentId(), getCar().getCustomer().getUserID(), 
        	getAppointmentID(),
                getCar().getCustomer().getName(), getCar().getCustomer().getPhoneNumber(),
                getDeposit().getPaymentDateTime().toLocalDate(),getDeposit().getPaymentType());

    }




}




