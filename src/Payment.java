	import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment {

    //Variables
    private String paymentType;
    private String paymentId;
    private static int totalPaymentNumber = 0;
    private LocalDateTime paymentDateTime;

    //PaymentAmount=0
    //Initialize deposit amount ;
    //Constructor w/o parameter


    public Payment() {
    }


    //Constructor with parameters
    public Payment(String paymentId,String paymentType,LocalDateTime paymentDateTime){
        this.paymentType=paymentType;
        this.paymentId=paymentId;
        this.paymentDateTime=paymentDateTime;
        totalPaymentNumber++;
    }


    public Payment(String paymentType) {
        this.paymentType = paymentType;
        //Create a new payment ID
        this.paymentId = generatePaymentId();
        //Create date object and initialize with the system date
        paymentDateTime = LocalDateTime.now();

    }



    public String generatePaymentId() {
        totalPaymentNumber++;//Running number increase for each payment
        LocalDateTime todayDate = LocalDateTime.now(); // Create a date object
        DateTimeFormatter transactionDateAndTime = DateTimeFormatter.ofPattern("ddMMyy");
        String formatTodayDate = todayDate.format(transactionDateAndTime);
        return String.format("P" + formatTodayDate + String.format("%03d\n", totalPaymentNumber)).trim();
    }

    //Getter
    public String getPaymentType() {
        return paymentType;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return paymentDateTime.format(formatter);
    }

   public String toString(){
       return String.format("%-10s     %-18s %-10s  %-5s",paymentId,paymentType,paymentDateTime.toLocalDate(),paymentDateTime.toLocalTime());
   }




}


