import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainPayment {

    //Validation for the ID type(idType '1' for Customer ,'2'-Payment)
    public static boolean validationForId(int idType, String traceItems) {
        char type = 'C'; //First character of customer is C
        if (traceItems.length() != 10) {
            System.out.println("\n\t\t    -[ The length of ID is incorrect , it must consists of 10 digits ]-");
            return false;//Return Invalid id type
        }
        //If the parameter of the id type is 1 , means that the staff want to validate the customer id
        if (idType == 1)
            type = 'C';
            //If the parameter of the id type is 2 , means that the staff want to validate the payment id
        else if (idType == 2)
            type = 'P';
        ;
        //Check whether the first digit is same as the type or not
        if (traceItems.toUpperCase().charAt(0) != type) {
            System.out.printf("\n\t\t            -[ First digit is %s ]-\n", type);
            return false;//Return Invalid id type
        }
        //Check for the behind Id type digits is a number or not
        for (char ch : traceItems.substring(1).toCharArray()) {
            if (!Character.isDigit(ch)) {
                System.out.println("\n\t\t        -[ After Id type digits must consists of numbers ]-");
                return false; //Return Invalid id type
            }
        }
        return true; //Return true for the valid id format
    }

    //For debit and credit card payment
    public static boolean isCardNum(String cardNum) {
        if (cardNum.trim().length() != 16) {
            System.out.println("\n\t\t\t- [ Sorry , card number must have 16 numbers ]-");
            return false;
        }
        //Check whether the length is consists of only number
        for (int i = 0; i < 16; i++) {
            char ch = cardNum.charAt(i);
            if (!Character.isDigit(ch)) {
                System.out.println("\n\t\t\t - [ Sorry , card number only consists of numbers ]-");
                return false;
            }
        }
        return true;
    }

    //Online Card use different method to validate the length number
    public static boolean isOnlineCard(int bankType, String cardNum) {
        //If public bank, length only consists of 10
        if (bankType == 1) {
            if (!cardNum.trim().matches("[0-9]{10}")) {
                System.out.println("\n\t    - [ Sorry , validation failed of your online banking card ]-");
                return false;
            }
        }
        //If RHB, length only consists of 14
        else if (bankType == 2) {
            if (!cardNum.trim().matches("[0-9]{14}")) {
                System.out.println("\n\t    - [ Sorry , validation failed of your online banking card ]-");
                return false;
            }
        }
        //If may bank, length only consists of 12
        else {
            if (!cardNum.trim().matches("[0-9]{12}")) {
                System.out.println("\n\t\t\t     - [ Sorry , validation failed of your online banking card ]-");
                return false;
            }
        }
        return true;
    }

    public static int paymentMethod() {
        String cardNumber;
        int paymentType = 0;
        Scanner scanner = new Scanner(System.in);
        boolean check;
        do {
            try {
                System.out.println("\n\n\t\t\t====================================================");
                System.out.println("\t\t\t|               Payment Method                     |");
                System.out.println("\t\t\t|                                                  |");
                System.out.println("\t\t\t|              1. Debit Card                       |");
                System.out.println("\t\t\t|              2. Credit Card                      |");
                System.out.println("\t\t\t|              3. Online Banking                   |");
                System.out.println("\t\t\t====================================================");
                System.out.printf("\t\t             Please enter your choice : ");
                String choice = scanner.nextLine();
                //Change string to integer
                paymentType = Integer.parseInt(choice.trim());
                check = true;
            }
            //To prevent user input invalid data type
            catch (Exception e) {
                check = false;
            }
            //If the service choice out of the range return false else return true
            check = (paymentType < 1 || paymentType > 3) ? false : true;
            if (!check) {
                System.out.printf("\n\t\t\t\t\t-[ Invalid Response .Try Again ]-\n");
            }
        } while (!check);
        //If the payment type is credit or debit card
        if (paymentType == 1 || paymentType == 2) {
            //Ask user for enter the account number
            do {
                System.out.printf("\n\t\t  >> Please enter your account number (16 digits) :");
                cardNumber = scanner.nextLine().trim();
            } while (isCardNum(cardNumber) == false); //Looping for invalid card number length
        }
        //If the payment type is online banking
        else {
            int bankTypeLength = 0, paymentChoice = 0;
            do {
                try {
                    System.out.println("\n\n\t\t\t====================================================");
                    System.out.println("\t\t\t|          Online Banking Payment                  |");
                    System.out.println("\t\t\t|                                                  |");
                    System.out.println("\t\t\t|              1. Public bank                      |");
                    System.out.println("\t\t\t|              2. RHB bank                         |");
                    System.out.println("\t\t\t|              3. May Bank                         |");
                    System.out.println("\t\t\t====================================================");
                    System.out.printf("\t\t          Please enter your bank type choice : ");
                    String choice = scanner.nextLine();
                    //Change string to integer
                    paymentChoice = Integer.parseInt(choice.trim());
                    check = true;
                }
                //To prevent user input validChoice data type
                catch (Exception e) {
                    check = false;
                }
                //If the service choice out of the range return false else return true
                check = (paymentChoice < 1 || paymentChoice > 3) ? false : true;
                //If the service choice out of the range
                if (!check) {
                    System.out.printf("\n\t\t\t\t\t-[  Invalid Response .Try Again ]-\n");
                }
                //Find each bank length->paymentChoice=1(Public bank ->10) ;paymentChoice=2(Public bank ->14);paymentChoice=3(Public bank ->12)
                bankTypeLength = paymentChoice == 1 ? 10 : paymentChoice == 2 ? 14 : 12;
                //Ask user for enter the account number

            } while (!check);
            do {
                System.out.printf("\n\t\t\t >> Enter you account number (%d digits) :", bankTypeLength);
                cardNumber = scanner.nextLine().trim();
            } while (isOnlineCard(paymentChoice, cardNumber) == false);//Looping for invalid card number length
        }

        return paymentType;
    }

    //Display customer payment menu and return their choice
    public static int customerPaymentMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int intMenuChoice = 0;
        while (true) { //Looping for invalid choice
            try {
                Main.functionHeader(32);
                System.out.println("\t\t\t+====================================================================================+");
                System.out.println("\t\t\t|                          1. Check-out bills                                        |");
                System.out.println("\t\t\t|                          2. Payment History                                        |");
                System.out.println("\t\t\t|                          3. Back to HomePage                                       |");
                System.out.println("\t\t\t+====================================================================================+");
                System.out.printf("\t\t\t                            Please enter your choice : ");
                String menuChoice = scanner.nextLine();
                //Change string to integer
                intMenuChoice = Integer.parseInt(menuChoice.trim());
            }
            //To prevent user input invalid data type
            catch (Exception e) {
                System.out.printf("\n\t\t\t\t                     -[ Invalid Response .Try Again ]-\n");
            }
            if (intMenuChoice < 1 || intMenuChoice > 3) {
                System.out.printf("\n\t\t\t\t                     -[ Invalid Response .Try Again ]-\n");
            } else break; //Valid choice will out of the looping
        }
        return intMenuChoice;
    }

    //Let the customer to view their payment history records
    public static void customerPaymentHistory(ArrayList<Appointment> appointmentArrayList, Customer customer) {
        Main.functionHeader(25);
        //Check the user ID
        boolean found = false;
        for (Appointment appointment : appointmentArrayList) {
            if (customer.getUserID().equals(appointment.getCar().getCustomer().getUserID())) {
                //How to check whether the payment has made
                if (appointment.getPayment() != null) { //If payment ID exists
                    System.out.printf("\n\n\t\t\t\t             %20s, here's your full payment details \n", customer.getName());
                    double subtotal = appointment.getService().getServicePrice() + (0.04 * appointment.getService().getServicePrice()) + (0.06 * appointment.getService().getServicePrice());
                    System.out.println("\t\t================================================================================================================");
                    System.out.println("\t\t|                                   Payment Coating Details                                                    |");
                    System.out.println("\t\t|                                                                                                              |");
                    System.out.printf("\t\t|   Payment ID                 :%-15s                                                                |\n", appointment.getPayment().getPaymentId());
                    System.out.printf("\t\t|   Service Type               :%-18s        Service Price     :RM%-5.2f                          |\n", appointment.getService().getServiceType(), appointment.getService().getServicePrice());
                    System.out.printf("\t\t|   Appointment ID             :%-15s           Appointment Date  :%-11s ( %-2s:00 )             |\n", appointment.getAppointmentID(), appointment.getDate(), appointment.getHour());
                    System.out.println("\t\t|                                                                                                              |");
                    System.out.println("\t\t|                                                      ================================================        |");
                    System.out.printf("\t\t|                                                       $ Subtotal(4%%SST+6%%Service Charge) : RM%-5.2f          |\n", subtotal);
                    System.out.println("\t\t|                                                                                                              |");
                    System.out.printf("\t\t|        You have been successfully make your full payment on %-11s via %-15s                  |\n", appointment.getPayment().getPaymentDateTime().toLocalDate(), appointment.getPayment().getPaymentType());
                    System.out.println("\t\t|                                 T    H     A    N     K          Y     O    U                                |");
                    System.out.println("\t\t================================================================================================================");
                } else {//Only deposit successfully made
                    System.out.printf("\n\n\t\t\t\t             %20s, here's your deposit payment details \n", customer.getName());
                    System.out.println("\t\t================================================================================================================");
                    System.out.println("\t\t|                                   Payment Coating Details                                                    |");
                    System.out.println("\t\t|                                                                                                              |");
                    System.out.printf("\t\t|   Deposit ID                 :%-15s                                                                |\n", appointment.getDeposit().getPaymentId());
                    System.out.printf("\t\t|   Service Type               :%-18s        Service Price     :RM%-5.2f                          |\n", appointment.getService().getServiceType(), appointment.getService().getServicePrice());
                    System.out.printf("\t\t|   Appointment ID             :%-15s           Appointment Date  :%-11s ( %-2s:00 )             |\n", appointment.getAppointmentID(), appointment.getDate(), appointment.getHour());
                    System.out.println("\t\t|                                                                                                              |");
                    System.out.printf("\t\t|   You have been successfully make your deposit payment (RM100.00) on %-11s via %-15s         |\n", appointment.getDeposit().getPaymentDateTime().toLocalDate(), appointment.getDeposit().getPaymentType());
                    System.out.println("\t\t|--------------------------------------------------------------------------------------------------------------|");
                    System.out.println("\t\t|     # Please do remember to make full payment after service provided. Thanks for your cooperation !          |");
                    System.out.println("\t\t|                                 T    H     A    N     K          Y     O    U                                |");
                    System.out.println("\t\t================================================================================================================");
                }
                found = true;
            }
        }
        //If no payment history found
        if (!found) {
            System.out.println("\n\t\t***********************************************************************************************************************");
            System.out.printf(" %20s,currently no payment made ,please kindly make your order now ^^ We will provide the best for you !\n", customer.getName());
            System.out.println("\t\t***********************************************************************************************************************");
        }
    }

    //Search for current customer's appointment index
    public static int searchAppointmentIndex(ArrayList<Appointment> appointmentArrayList, Customer customer, String appointmentId) {
        int appIndex = -1; //If no record existing
        for (int i = 0; i < appointmentArrayList.size(); i++) {
            if (appointmentArrayList.get(i).getCar().getCustomer().getUserID().equals(customer.getUserID())) {
                //If have existing record
                if (appointmentArrayList.get(i).getPayment() == null) {
                    if (appointmentArrayList.get(i).getAppointmentID().trim().toUpperCase().equals(appointmentId)) {
                        return i; //Return index
                    }
                }
            }
        }
        return appIndex;
    }

    //Let user make full payment
    public static void fullPayment(ArrayList<Appointment> appointmentArrayList, Customer customer) throws IOException, ParseException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int count = 0;//Count -count the total records
        String paymentTypeWords = ""; //If full payment successfully done ,return the the payment type in words "Online banking" /"Credit card" /"Debit Card"
        String checkOut = "";
        Main.functionHeader(26);
        System.out.println("\n\n       ========================================================================");
        System.out.println("       |             E C O A T   C H E C K - O U T  B I L L S                 |");
        System.out.println("       |                                                                      |");
        System.out.printf("       |                                           Date : %-18s |\n", LocalDateTime.now().toString().substring(0, 19));
        System.out.println("       |                         ydds                                         |");
        System.out.println("       |                      ..dMMd-.`                                       |");
        System.out.println("       |                   -ohmNMMMMNmdy/`                                    |");
        System.out.println("       |                 `sNMMMMMMMMMMMMMm:                                   |");
        System.out.println("       |                 sMMMMMMy++sdMMMNNm.                                  |");
        System.out.println("       |                  NMMMMMN:``` +o/-.                                   | ");
        System.out.println("       |                  dMMMMMMMMmdys+-`               K I N D L Y          |");
        System.out.println("       |                  .mMMMMMMMMMMMMMMMh-              M A K E            |");
        System.out.println("       |                    `/hNMMMMMMMMMMMMM+                Y O U R         |");
        System.out.println("       |                        ..:+sydNMMMMMNNN`         B A L A N C E       |");
        System.out.println("       |                  oyhmNNh-    .MMMMMMM`              B Y  N O W       |");
        System.out.println("       |                  +MMMMMMNdyyymMMMMMMs                                |");
        System.out.println("       |                    :dMMMMMMMMMMMMMMmo`                               | ");
        System.out.println("       |                      `:sdmNMMMMNmdy/.            T H A N K  Y O U    | ");
        System.out.println("       |                       `..dMMd..`                      - E C O A T    |");
        System.out.println("       |                          ydds                                        |");
        System.out.println("       ========================================================================");
        int appIndex = 0;//Store current appointment index
        boolean proceedPayment = false;
        int countId = 1;
        System.out.println("\n          Below shows your Appointment Records haven't pay for full payment");
        System.out.println("\n       -------------------------------------------------------------------------");
        for (Appointment appointment : appointmentArrayList) {
            if (appointment.getCar().getCustomer().getUserID().equals(customer.getUserID())) {
                if (appointment.getPayment() == null) {
                    //Display for user see their current appointment ID( Only show to see the appointment ID those haven't make the full payment
                    System.out.printf("             %02d- Current Appointment ID : %s (%s)\n", countId, appointment.getAppointmentID(), appointment.getDate());
                    countId++;
                    proceedPayment = true; //Can proceed the payment process
                }
            }
        }
        System.out.println("       -------------------------------------------------------------------------");
        String appId = "";
        if (proceedPayment == true) { //Only if their payment ID found that haven't make full payment can proceed
            while (true) {
                System.out.printf("\n    >> Enter your Appointment ID ('ADDMMYYYYXX') to make your full payment : ");
                appId = scanner.nextLine().toUpperCase().trim();
                if (appId.length() != 11) //Validation for length
                    System.out.println("\n\t\t         -[ Invalid length , try again ]-");
                else if (appId.length() == 11) { //If length correct
                    //Check the appointment ID and return current appointment index
                    appIndex = searchAppointmentIndex(appointmentArrayList, customer, appId);
                    if (appIndex == -1) //If index return is -1 , not payment ID found
                    {
                        System.out.println("\n\t\t         -[ Payment ID not found ]-");
                    } else break;
                }
            }
            double amount = appointmentArrayList.get(appIndex).getService().getServicePrice();
            //Subtotal=service charge 4 % + SST 6%
            double subtotal = amount + (0.04 * amount) + (0.06 * amount);
            //Balance = subtotal- deposit
            double balance = subtotal - 100.00;
            System.out.println("\n\t\t==========================================================================");
            System.out.printf("\t\t|                                                            #%10s |\n", customer.getUserID().trim());
            System.out.println("\t\t|                         S U M M A R Y                                  |");
            System.out.println("\t\t|                                                                        |");
            System.out.printf("\t\t|    * Service Type                          :%-18s         |\n", appointmentArrayList.get(appIndex).getService().getServiceType());
            System.out.printf("\t\t|    * Service Amount                        :RM%-8.2f                 |\n", appointmentArrayList.get(appIndex).getService().getServicePrice());
            System.out.printf("\t\t|    * Subtotal (6%% SST + 4%% Service Charge) :RM%-8.2f                 |\n", subtotal);
            System.out.println("\t\t|                                                                        |");
            System.out.println("\t\t|                               ######################################## |");
            System.out.printf("\t\t|                                $ Balance (Exclude deposit) :RM%-5.2f   |\n", balance);
            System.out.println("\t\t|                               ######################################## |");
            System.out.println("\t\t|                                                                        |");
            System.out.println("\t\t|------------------------------------------------------------------------|");
            System.out.println("\t\t|   Terms of payment :                                                   |");
            System.out.println("\t\t| * All deposit are NOT refundable of any cancellation appointment       |");
            System.out.println("\t\t==========================================================================");
            System.out.printf("\n\t\t\t\t    $$$ Currently you left RM%.2f balance $$$\n", balance);
            while (true) {
                try {
                    System.out.printf("\n\t\tDo you want to continue proceeding your full payment ? ('Y' or 'N') :");
                    checkOut = scanner.nextLine().toUpperCase().trim();
                } catch (Exception e) { //Catch for any exception
                    System.out.printf("\t\t               -[  Invalid Response .Try Again ]-\n");
                }
                //Invalid response
                if (!checkOut.equals("Y") && !checkOut.equals("N") || checkOut.isEmpty())
                    System.out.printf("\t\t               -[  Invalid Response .Try Again ]-\n");
                else break;
            }
            //If confirm to check out
            if (checkOut.equals("Y")) {
                int paymentType = paymentMethod();
                String confirmCheckOut = "";
                while (true) {
                    try {
                        //Ask user to confirm again
                        System.out.printf("\n\t\t     >> Do you confirm your full payment ? ('Y' or 'N') :");
                        //show appointment date time
                        confirmCheckOut = scanner.nextLine().toUpperCase().trim();
                    } catch (Exception e) {
                        System.out.printf("\t\t\t\t       -[ Invalid Response .Try Again ]-\n");
                    }
                    if (!confirmCheckOut.equals("Y") && !confirmCheckOut.equals("N") || confirmCheckOut.isEmpty())
                        System.out.printf("\t\t\t\t       -[ Invalid Response .Try Again ]-\n");
                    else break;
                }
                switch (confirmCheckOut) {
                    case "Y":
                        System.out.println("\n\t\t\t*****************************************************");
                        System.out.println("\t\t\t  PAYMENT LOADING PROCESS ,PLEASE BE PATIENCE. #3sec");
                        System.out.println("\t\t\t*****************************************************");
                        //load 3 seconds
                        Thread.sleep(3000);
                        //Move their paymentType choice into words ,if paymentType==1->Debit,2->Credit,3->Online Banking
                        paymentTypeWords = paymentType == 1 ? "Debit Card" : paymentType == 2 ? "Credit Card" : "Online Banking";
                        Payment payment = new Payment(paymentTypeWords);
                        appointmentArrayList.get(appIndex).setPayment(payment);//Set payment
                        System.out.printf("\n  -[ %s ,your payment has been successfully paid by %s on %s %s ]-\n", appointmentArrayList.get(appIndex).getCar().getCustomer().getName(), paymentTypeWords, LocalDate.now(), LocalTime.now().toString().substring(0, 8));
                        String receipt = "";
                        while (true) {
                            try {
                                System.out.printf("\n\t\t  Dear, do you wish to have your payment receipt ? ('Y' or 'N') :");
                                receipt = scanner.nextLine().toUpperCase().trim();
                            } catch (Exception e) {
                                System.out.printf("\t\t\t\t      -[ Invalid Response .Try Again ]-\n");
                            }
                            if (!receipt.equals("Y") && !receipt.equals("N") || receipt.isEmpty()) //invalid response
                                System.out.printf("\t\t\t\t      -[ Invalid Response .Try Again ]-\n");
                            else//Out of looping if valid user input
                                break;
                        }
                        if (receipt.equals("Y")) { //If they wish to have receipt
                            //Display receipt if the customer wish to
                            System.out.println("\n\n\t\t--------------------------------------------------------------");
                            System.out.println("\t\t|                      TRANSACTION RECEIPT                   |");
                            System.out.println("\t\t|                                                            |");
                            System.out.printf("\t\t|                                  Payment ID :%-12s  |\n", payment.getPaymentId());
                            System.out.printf("\t\t|                                  Date       :%-12s  |\n", LocalDate.now());//Get date
                            System.out.printf("\t\t|                                  Time       :%-12s  |\n", LocalTime.now().toString().substring(0, 8));//Get time
                            System.out.println("\t\t|____________________________________________________________|");
                            System.out.printf("\t\t| Appointment ID :%-10s      Customer ID :%-10s   |\n", appointmentArrayList.get(appIndex).getAppointmentID().trim(), appointmentArrayList.get(appIndex).getCar().getCustomer().getUserID().trim());
                            System.out.println("\t\t|____________________________________________________________|");
                            System.out.println("\t\t|                S     A     L     E     S                   |");
                            System.out.println("\t\t|                                                            |");
                            System.out.println("\t\t|       Items                                  Amount        |");
                            System.out.printf("\t\t|  %-23s                  >> RM%-7.2f     |\n", appointmentArrayList.get(appIndex).getService().getServiceType(), appointmentArrayList.get(appIndex).getService().getServicePrice());
                            System.out.println("\t\t|                                                            |");
                            System.out.println("\t\t|============================================================|");
                            System.out.printf("\t\t| Subtotal (6%%SST + 4%%Service Charge)        $ RM%-8.2f    |\n", subtotal);
                            System.out.printf("\t\t| Payment Type  : %-18s                         |\n", paymentTypeWords);
                            System.out.println("\t\t|____________________________________________________________|");
                            System.out.println("\t\t|          T   H    A   N    K       Y    O   U              |");
                            System.out.println("\t\t|                                                            |");
                            System.out.println("\t\t|                               - E C O A T   C O A T I N G  |");
                            System.out.println("\t\t--------------------------------------------------------------");
                            System.out.println("\n\t\t\t     Press 'ENTER' to continue your payment ! ");
                            scanner.nextLine();
                            //Rating
                        } else if (receipt.equals("N")) { //If they don't want have the receipt
                            System.out.println("\t\t         T    H     A    N     K          Y     O    U              ");
                            System.out.println("\n\t\t\tPress 'ENTER' to continue your payment ! ");
                        }
                        break;
                    case "N": //If they enter the bank card details but they don't want make the payment
                        System.out.println("\n\t\t\t\t          - [ No payment made ]-");
                }
            } else if (checkOut.equals("N")) {//If they don't want to make a payment
                System.out.println("\n\t\t\t\t          - [ No payment made ]-");
            }
        } else
            System.out.println("       -[ Sorry , currently no payment ID found that haven't pay the balance ]-");
    }

    //After deposit done,the system will generate invoice details to the user
    public static void invoiceDetails(Appointment appointment, ArrayList<Appointment> appointmentArrayList, Customer customer) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\t\t=========================================================================");
        System.out.println("\t\t|                    *.*.*TRANSACTION INVOICE.*.*.*                     |");
        System.out.println("\t\t|                                                                       |");
        //Get customer's details
        System.out.printf("\t\t|                                            Deposit ID :%-12s   |\n", appointment.getDeposit().getPaymentId().trim());
        System.out.printf("\t\t|                                            Date       :%-12s   |\n", LocalDate.now());//Get date
        System.out.printf("\t\t|                                            Time       :%-12s   |\n", LocalTime.now().toString().substring(0, 8));//Get time
        System.out.println("\t\t|_______________________________________________________________________|");
        System.out.println("\t\t|                                                                       |");
        System.out.printf("\t\t| Appointment ID          :%-15s                              |\n", appointment.getAppointmentID().trim());
        System.out.printf("\t\t| Customer ID             :%-15s                              |\n", appointment.getCar().getCustomer().getUserID().trim());
        System.out.printf("\t\t| Customer Name           :%-15s                              |\n", appointment.getCar().getCustomer().getName());
        System.out.printf("\t\t| Payment Type            :%-15s                              |\n", appointment.getDeposit().getPaymentType());
        System.out.printf("\t\t|#######################################################################|\n");
        System.out.printf("\t\t| Service Type            :%-24s    #RM%-5.2f        |\n", appointment.getService().getServiceType(), appointment.getService().getServicePrice());
        System.out.println("\t\t|                                                                       |");
        System.out.printf("\t\t|                                          #############################|\n");
        System.out.printf("\t\t|                                           $ Deposit : RM100.00        |\n");
        System.out.println("\t\t|_______________________________________________________________________|");
        System.out.println("\t\t|                                                                       |");
        System.out.println("\t\t|  Terms of payment :                                                   |");
        System.out.printf("\t\t|  * FULL payment is required upon service completed                    |\n");
        System.out.println("\t\t|  * All deposit are NOT refundable of any cancellation appointment     |");
        System.out.println("\t\t|  * Temporary Bill acknowledge                                         |");
        System.out.println("\t\t|                                                                       |");
        System.out.println("\t\t|                         T H A N K  Y O U                              |");
        System.out.println("\t\t=========================================================================");
        System.out.println("\n\t\t                Press 'ENTER' to continue your payment ! ");
        scanner.nextLine();
    }

    //Display staff menu and return their choice
    public static int staffPaymentMenu() {
        Scanner scanner = new Scanner(System.in);
        int intMenuChoice = 0;
        while (true) {
            try {
                Main.functionHeader(27);
                System.out.println("\t\t\t+====================================================================================+");
                System.out.println("\t\t\t|                       1.  Daily Sales Report                                       |");
                System.out.println("\t\t\t|                       2.  Monthly Sales Report                                     |");
                System.out.println("\t\t\t|                       3.  Trace Payment Transaction                                |");
                System.out.println("\t\t\t|                       4.  Check-out Customer Bills                                 |");
                System.out.println("\t\t\t|                       5.  Sorting Payment Records                                  |");
                System.out.println("\t\t\t|                       6.  Back to HomePage                                         |");
                System.out.println("\t\t\t+====================================================================================+");
                System.out.printf("\t\t                           Please enter your choice >> ");
                String menuChoice = scanner.nextLine();
                //Change string to integer
                intMenuChoice = Integer.parseInt(menuChoice.trim());
            }
            //To prevent user input invalid data type
            catch (Exception e) {
                System.out.printf("\n\t\t\t\t                     -[ Invalid Response .Try Again ]-\n");
            }
            if (intMenuChoice < 1 || intMenuChoice > 6)
                System.out.printf("\n\t\t\t\t                     -[ Invalid Response .Try Again ]-\n");
            else break;
        }
        return intMenuChoice;
    }


    //Display daily sales report
    public static void dailySalesReport(ArrayList<Appointment> appointmentArrayList) {
        Scanner scanner = new Scanner(System.in);
        String choice = "Y";
        do {
            Main.functionHeader(28);
            System.out.println("\t\t\t    _______");
            System.out.println("\t\t\t   |       |         ");
            System.out.println("\t\t\t   |       |                _______      ");
            System.out.println("\t\t\t   |       |_______        |       |");
            System.out.println("\t\t\t   |       |       |       |       |");
            System.out.println("\t\t\t   |       |       |       |       |");
            System.out.println("\t\t\t   |       |       |_______|       |");
            System.out.println("\t\t\t   |       |       |       |       |");
            System.out.println("\t\t\t   |       |       |       |       |_______");
            System.out.println("\t\t\t   |       |       |       |       |       |_______");
            System.out.println("\t\t\t   |       |       |       |       |       |       |");
            System.out.println("\t\t\t   __________________________________________________");
            boolean found = false, paymentFound = false, depositFound = false;
            int countTotalPayment = 0, countTotalDeposit = 0;//Count total payment of deposit and full payment
            double totalSales = 0, totalServiceAmount = 0, totalTax = 0, totalServiceCharge = 0, totalDeposit = 0;  //Count total of each type of payment
            String date = "";
            int day = 0;
            int month = 0;
            while (true) {
                try {
                    do {
                        System.out.printf("\n\t  Please enter the date which you want to have the daily sales report (yyyy-mm-dd) :");
                        date = scanner.nextLine().trim();
                        //Check date format
                        if (date.length() != 10) {
                            System.out.printf("\n\t\t      * Please enter the correct date format *\n");
                        } else if (date.charAt(4) != '-' || date.charAt(7) != '-') {
                            System.out.println("\n\t\t            -[ Your date required a slash '-' ]-");
                        }
                    } while (date.length() != 10 || date.charAt(4) != '-' || date.charAt(7) != '-');
                } catch (Exception e) {
                    System.out.printf("\n\t\t            -[ Sorry .Invalid date.Try Again ]-\n");
                    scanner.nextLine();
                }
                //Get day,month and year from the string
                day = Integer.parseInt(date.substring(8, 10));
                month = Integer.parseInt(date.substring(5, 7));
                //Validation for date
                if ((day > 31 || day < 0) || (month < 0 || month > 12)) {
                    System.out.printf("\n                -[  Sorry invalid date provided. Please TRY AGAIN ! ]-\n");
                } else break;
            }
            LocalDate dateTime = LocalDate.parse(date);
            System.out.printf("\n\n\t\t\t                    S A L E S   P A Y M E N T   R E P O R T  O N  %-15s", date);
            System.out.printf("\n     ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.printf("           Payment ID      Customer ID      Appointment ID       Service Type      Service Price     Sales Tax    Service Charge      Subtotal       Transaction Time     Payment Method    \n");
            System.out.printf("     ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            for (Appointment appointment : appointmentArrayList) {
                if (appointment.getPayment() != null) {
                    if (appointment.getPayment().getPaymentDateTime().toLocalDate().compareTo(dateTime) == 0) {
                        //If full payment made
                        double sst = 0.06 * appointment.getService().getServicePrice();
                        double serviceCharge = 0.04 * appointment.getService().getServicePrice();
                        double subtotal = appointment.getService().getServicePrice() + sst + serviceCharge;
                        System.out.printf("       %02d- %-10s      %-10s        %-10s         %-16s     RM%-5.2f         RM%-5.2f        RM%-5.2f          RM%-5.2f           %-10s      %-15s\n", countTotalPayment + 1, appointment.getPayment().getPaymentId(), appointment.getCar().getCustomer().getUserID(), appointment.getAppointmentID(),
                                appointment.getService().getServiceType(), appointment.getService().getServicePrice(), sst, serviceCharge, subtotal,
                                appointment.getPayment().getPaymentDateTime().toLocalTime(), appointment.getPayment().getPaymentType());
                        //Store each price into each price variable
                        totalSales += subtotal;
                        totalServiceAmount += appointment.getService().getServicePrice();
                        totalTax += sst;
                        totalServiceCharge += serviceCharge;
                        countTotalPayment++;
                        found = true;
                        paymentFound = true;
                    }
                }
            }
            System.out.printf("     ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            if (!paymentFound)
                System.out.println("\n                                      -[   Currently no payment records   ]-              ");
            System.out.printf("\n\n\t\t\t            S A L E S   D E P O S I T   R E P O R T  O N  %-15s", date);
            System.out.printf("\n     ---------------------------------------------------------------------------------------------------------------\n");
            System.out.printf("           Deposit ID     Customer ID     Appointment ID        Amount      Transaction Time     Payment Method    \n");
            System.out.printf("     ---------------------------------------------------------------------------------------------------------------\n");
            for (Appointment appointment : appointmentArrayList) {
                if (appointment.getPayment() == null) {
                    if (appointment.getDeposit().getPaymentDateTime().toLocalDate().equals(dateTime)) {
                        System.out.printf("       %02d- %-10s     %-10s      %-10s         RM100.00          %-10s        %-15s\n", countTotalDeposit + 1, appointment.getDeposit().getPaymentId(), appointment.getCar().getCustomer().getUserID(), appointment.getAppointmentID(),
                                appointment.getDeposit().getPaymentDateTime().toLocalTime(), appointment.getDeposit().getPaymentType());
                        totalDeposit += 100.00;
                        countTotalDeposit++;
                        found = true;
                        depositFound = true;
                    }
                }
            }
            System.out.printf("     ---------------------------------------------------------------------------------------------------------------\n");

            if (!depositFound) {
                System.out.println("\n                                         -[ No deposit found ]-");
            }
            if (paymentFound) {
                double profit = totalSales - totalServiceAmount;
                System.out.printf("\n\n\t\t\t             D A I L Y   S A L E S   R E P O R T   S U M M A R Y     \n");
                System.out.println("\t\t\t--------------------------------------------------------------------------------------");
                System.out.println("\t\t\t        Total Service Amount          Total SST           Total Service Charge ");
                System.out.println("\t\t\t      =======================       =============        ======================= ");
                System.out.printf("\t\t               RM%-5.2f                    RM%-5.2f                   RM%-5.2f  \n", totalServiceAmount, totalTax, totalServiceCharge);
                System.out.println("\t\t\t--------------------------------------------------------------------------------------");
                System.out.printf("\t\t\t                                                       > Subtotal     :RM%-5.2f \n", totalSales);
                System.out.printf("\t\t\t                                                       > Total Profit :RM%-5.2f\n", profit);
                System.out.println("\t\t\t--------------------------------------------------------------------------------------");
                System.out.printf("\t\t\t              # Total %02d number of people paid on %-10s  #                  \n", countTotalPayment, date);

            }
            if (depositFound) {
                System.out.printf("\n\n\t\t\t         D A I L Y   S A L E S   D E P O S I T   R E P O R T   S U M M A R Y    \n");
                System.out.println("\t\t\t----------------------------------------------------------------------------------");
                System.out.printf("\t\t\t         > Total Amount of Deposit     : RM%-5.2f \n", totalDeposit);
                System.out.println("\t\t\t-----------------------------------------------------------------------------------");
                System.out.printf("\t\t\t          # Total %02d number of people paid on %-10s  #                  \n", countTotalDeposit, date);

            }
            while (true) {
                try {
                    System.out.printf("\n\n           Do you want to continue to search the date of daily sales report ('Y'/'N') >> ");
                    choice = scanner.nextLine().toUpperCase().trim();
                } catch (Exception e) {
                    System.out.println("                                    -[ Something goes wrong. Try again ]- ");
                }
                if (choice.charAt(0) != 'Y' && choice.charAt(0) != 'N') {
                    System.out.println("                                    -[ Invalid response . Try again ]- ");
                } else break;
            }
        }while(choice.charAt(0) == 'Y' );

    }

    //Display monthly sales report
    public static void monthlySalesReport(ArrayList<Appointment> appointmentArrayList) {
        Scanner scanner = new Scanner(System.in);
        String choice = "Y";
        do{
            Main.functionHeader(29);
            System.out.println("\t\t\t    _______");
            System.out.println("\t\t\t   |       |         ");
            System.out.println("\t\t\t   |       |                _______      ");
            System.out.println("\t\t\t   |       |_______        |       |");
            System.out.println("\t\t\t   |       |       |       |       |");
            System.out.println("\t\t\t   |       |       |       |       |");
            System.out.println("\t\t\t   |       |       |_______|       |");
            System.out.println("\t\t\t   |       |       |       |       |");
            System.out.println("\t\t\t   |       |       |       |       |_______");
            System.out.println("\t\t\t   |       |       |       |       |       |_______");
            System.out.println("\t\t\t   |       |       |       |       |       |       |");
            System.out.println("\t\t\t   __________________________________________________");
            boolean found = false, paymentFound = false, depositFound = false; //If the records found will initialise as true
            int countTotalPayment = 0, countTotalDeposit = 0;//Count total payment of deposit and full payment
            double totalSales = 0, totalServiceAmount = 0, totalTax = 0, totalServiceCharge = 0, totalDeposit = 0;  //Count total of each type of payment
            String date = "";
            int month = 0;
            while (true) {
                try {
                    do {
                        System.out.printf("\n\t  Please enter the month to have the monthly sales report (yyyy-mm) :");
                        date = scanner.nextLine().trim();
                        //Check date format
                        if (date.length() != 7) {
                            System.out.printf("\n\t\t      * Please enter the correct date format *\n");
                        } else if (date.charAt(4) != '-') {
                            System.out.println("            -[ Your date required a slash '-' ]- ");
                        }
                    } while (date.length() != 7 || date.charAt(4) != '-');
                } catch (Exception e) {
                    System.out.printf("\n\t\t             -[ Sorry .Invalid date.Try Again ]-\n");
                }
                month = Integer.parseInt(date.substring(5, 7));
                //Validation for date
                if ((month < 0 || month > 12)) {
                    System.out.printf("\n                 -[ Sorry invalid date provided. Please TRY AGAIN ! ]-\n");
                } else
                    break;
            }
            System.out.printf("\n\n\t\t\t            M O N T H L Y   S A L E S   P A Y M E N T   R E P O R T  O N  %-10s", Month.of(month));
            System.out.printf("\n     ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            System.out.printf("           Payment ID      Customer ID      Appointment ID       Service Type      Service Price     Sales Tax    Service Charge      Subtotal       Transaction Time     Payment Method    \n");
            System.out.printf("     ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            for (Appointment appointment : appointmentArrayList) {
                if (appointment.getPayment() != null) { //Make sure payment made before
                    if (appointment.getPayment().getPaymentDateTime().getMonth().compareTo(Month.of(month)) == 0) {
                        //If full payment made
                        double sst = 0.06 * appointment.getService().getServicePrice();
                        double serviceCharge = 0.04 * appointment.getService().getServicePrice();
                        double subtotal = appointment.getService().getServicePrice() + sst + serviceCharge;
                        System.out.printf("       %02d- %-10s      %-10s        %-10s         %-16s     RM%-5.2f         RM%-5.2f        RM%-5.2f          RM%-5.2f           %-10s      %-15s\n", countTotalPayment + 1, appointment.getPayment().getPaymentId(), appointment.getCar().getCustomer().getUserID(), appointment.getAppointmentID(),
                                appointment.getService().getServiceType(), appointment.getService().getServicePrice(), sst, serviceCharge, subtotal,
                                appointment.getPayment().getPaymentDateTime().toLocalTime(), appointment.getPayment().getPaymentType());
                        //Store each price into each price variable
                        totalSales += subtotal;
                        totalServiceAmount += appointment.getService().getServicePrice();
                        totalTax += sst;
                        totalServiceCharge += serviceCharge;
                        countTotalPayment++;
                        found = true;
                        paymentFound = true;//If payment records found
                    }
                }
            }
            System.out.printf("     ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            //If payment null
            System.out.printf("\n\n\t\t\t           M O N T H L Y   S A L E S   D E P O S I T   R E P O R T  O N  %-10s", Month.of(month));
            System.out.printf("\n     ---------------------------------------------------------------------------------------------------------------\n");
            System.out.printf("           Deposit ID     Customer ID     Appointment ID        Amount      Transaction Time     Payment Method    \n");
            System.out.printf("     ---------------------------------------------------------------------------------------------------------------\n");
            for (Appointment appointment : appointmentArrayList) {
                if (appointment.getPayment() == null) {
                    if (appointment.getDeposit().getPaymentDateTime().getMonth().compareTo(Month.of(month)) == 0) { //Get deposit details
                        System.out.printf("       %02d- %-10s     %-10s      %-10s         RM100.00          %-10s        %-15s\n", countTotalDeposit + 1, appointment.getDeposit().getPaymentId(), appointment.getCar().getCustomer().getUserID(), appointment.getAppointmentID(),
                                appointment.getDeposit().getPaymentDateTime().toLocalTime(), appointment.getDeposit().getPaymentType());
                        totalDeposit += 100.00;
                        countTotalDeposit++;
                        found = true;
                        depositFound = true;//If deposit records not found
                    }
                }
            }
            System.out.printf("     ---------------------------------------------------------------------------------------------------------------\n");
            if (!found)//If no any records found
                System.out.println("\n                                   -[   Currently no payment records   ]-              ");

            //If payment records found after that will show sales summary report
            if (paymentFound) {
                double profit = totalSales - totalServiceAmount; //Count the profit

                System.out.printf("\n\n\t\t\t      M O N T H L Y    S A L E S   R E P O R T   S U M M A R Y     \n");
                System.out.println("\t\t\t--------------------------------------------------------------------------------------");
                System.out.println("\t\t\t        Total Service Amount          Total SST           Total Service Charge ");
                System.out.println("\t\t\t      =======================       =============        ======================= ");
                System.out.printf("\t\t               RM%-5.2f                    RM%-5.2f                   RM%-5.2f  \n", totalServiceAmount, totalTax, totalServiceCharge);
                System.out.println("\t\t\t--------------------------------------------------------------------------------------");
                System.out.printf("\t\t\t                                                       > Subtotal     :RM%-5.2f \n", totalSales);
                System.out.printf("\t\t\t                                                       > Total Profit :RM%-5.2f\n", profit);
                System.out.println("\t\t\t--------------------------------------------------------------------------------------");
                System.out.printf("\t\t\t              # Total %02d number of people paid on %-10s  #                  \n", countTotalPayment, Month.of(month));

            }
            //If deposit records found after that will show sales summary report
            if (depositFound) {

                System.out.printf("\n\n\t\t\t       M O N T H L Y   S A L E S   D E P O S I T   R E P O R T   S U M M A R Y    \n");
                System.out.println("\t\t\t----------------------------------------------------------------------------------");
                System.out.printf("\t\t\t         > Total Amount of Deposit     : RM%-5.2f \n", totalDeposit);
                System.out.println("\t\t\t-----------------------------------------------------------------------------------");
                System.out.printf("\t\t\t          # Total %02d number of people paid on %-10s  #                  \n", countTotalDeposit, Month.of(month));

            }

            while (true) {
                try {
                    System.out.printf("\n\n           Do you want to continue to search the date of monthly sales report ('Y'/'N') >> ");
                    choice = scanner.nextLine().toUpperCase().trim();
                } catch (Exception e) {
                    System.out.println("                 -[ Something goes wrong. Try again ]- ");
                }
                if (choice.charAt(0) != 'Y' && choice.charAt(0) != 'N') {
                    System.out.println("                 -[ Invalid response . Try again ]- ");
                } else break;
            }
        }while(choice.charAt(0) == 'Y' );
    }

    //Trace transaction full payment header
    public static void fullPaymentHeader() {
        System.out.println("\n    ==========================================================================================================================================================");
        System.out.printf("       No.    Payment ID      Customer ID      Appointment ID      Customer Name      Phone No        Subtotal       Transaction Time       Payment Method    \n");
        System.out.printf("    ..........................................................................................................................................................\n");
    }

    //Trace transaction deposit payment header
    public static void depositHeader() {
        System.out.println("\n    ==========================================================================================================================================================");
        System.out.printf("       No.  Deposit ID      Customer ID      Appointment ID     Customer Name        Phone No       Amount         Transaction Time     Payment Method    \n");
        System.out.printf("    ..........................................................................................................................................................\n");
    }

    //Trace payment Records (For Staff only)
    public static void tracePaymentTransaction(ArrayList<Appointment> appointmentArrayList) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int count = 1;
        String traceItems = ""; //Trace items input
        int choice = 0;        //User choice
        boolean found = false; //Any records not found will initialise as false
        while (true) {      //Looping if the staff wish to trace the records
            while (true) {  //Looping for invalid trace choices
                try {
                    Main.functionHeader(30);
                    System.out.println("\n\t\t\t    +=========================================================================+");
                    System.out.println("\t\t\t\t|    Welcome here ! Which payment details do you wish to trace ?          |");
                    System.out.println("\t\t\t\t|             ( 1 ) Trace By Customer ID                                  |");
                    System.out.println("\t\t\t\t|             ( 2 ) Trace By Payment (ID/Date)                            |");
                    System.out.println("\t\t\t\t|             ( 3 ) Trace By Deposit (ID/Date)                            |");
                    System.out.println("\t\t\t\t|             ( 4 ) Display all Payment records                           |");
                    System.out.println("\t\t\t\t|             ( 5 ) Back to Payment Menu                                  |");
                    System.out.println("\t\t\t    +=========================================================================+");
                    System.out.printf("\t\t\t\t                 Please enter your choice >> ");
                    choice = scanner.nextInt();
                } catch (Exception e) { //Capture any invalid choice
                    System.out.printf("\n\t\t                 -[ Sorry .Invalid response.Try Again ]-\n");
                    scanner.nextLine();
                }
                if ((choice < 1 || choice > 5)) //If not in the choice range
                    System.out.printf("\n\t\t                  -[ Sorry .Invalid range response.Try Again ]-\n");
                else break;
            }
            scanner.nextLine();
            count = 1;//Reset the count
            switch (choice) {
                case 1://If want to trace customer ID
                    do {
                        System.out.printf("\n       Please enter the Customer ID to trace the records ('CXXXXXXXXX') :");
                        traceItems = scanner.nextLine().toUpperCase().trim();
                        //Pass customer ID to validationForId module for validation
                    } while (!validationForId(1, traceItems)); //Go validation ID module , looping for invalid user input,customer id type=1
                    System.out.printf("\n\n                                       CUSTOMER(%s) PAYMENT DETAILS", traceItems);
                    fullPaymentHeader();
                    for (Appointment appointment : appointmentArrayList) {
                        if (appointment.getCar().getCustomer().getUserID().equals(traceItems)) {
                            if (appointment.getPayment() != null) {
                                //Check whether full payment given or not
                                System.out.printf("       %02d-  %s", count, appointment.fullPaymentToString());
                                found = true;
                                count++;
                            }

                        }
                    }
                    System.out.println("    ==========================================================================================================================================================");
                    //Search for the deposit records
                    System.out.printf("\n\n                                         CUSTOMER(%s) DEPOSIT DETAILS", traceItems);
                    depositHeader();
                    count = 1; //Reset count
                    for (Appointment appointment : appointmentArrayList) {
                        if (appointment.getCar().getCustomer().getUserID().equals(traceItems)) {
                            if (appointment.getPayment() == null) {//If inside payment is null = only deposit made
                                System.out.printf("       %02d-  %s", count, appointment.depositPaymentToString());
                                found = true;
                                count++;
                            }

                        }
                    }
                    System.out.println("    ==========================================================================================================================================================");
                    System.out.println("\t\t                                       ***** Noted : Only deposit paid ********\n");
                    //If no current customer id found
                    if (!found) {
                        System.out.println("\n\n\t\t           -[ Currently this customer no have any payment records yet .Thanks you.  ]-");
                    }
                    break;
                case 2://If want to trace payment details
                    int paymentChoice = 0;
                    while (true) {
                        try {
                            System.out.printf("\n      Which payment details do you wish to search ( 1 )-Payment ID or ( 2 )-Payment Date :");
                            paymentChoice = Integer.parseInt(scanner.nextLine());
                        } catch (Exception e) {
                            System.out.printf("\n\t\t                 -[ Sorry .Invalid response.Try Again ]- \n\n");
                        }
                        if (paymentChoice != 1 && paymentChoice != 2)
                            System.out.printf("\n\t\t             -[  Invalid choice. Please enter 1 or 2 as your choice ]- \n\n");
                        else
                            break;
                    }
                    switch (paymentChoice) {
                        case 1://If the staff want to track the Payment ID
                            do {
                                System.out.printf("\n\t  Please enter Payment ID to track ('PXXXXXXXXX') :");
                                traceItems = scanner.nextLine().trim();
                            } while (!validationForId(2, traceItems));//Payment id type=2
                            System.out.printf("\n\n\t\t\t                              PAYMENT ID(%s) DETAILS", traceItems);
                            fullPaymentHeader();
                            for (Appointment appointment : appointmentArrayList) {
                                if (appointment.getPayment() != null) { //Make sure that payment is made
                                    if (appointment.getPayment().getPaymentId().equals(traceItems)) {
                                        System.out.printf("        01 -%s", appointment.fullPaymentToString());
                                        found = true;
                                    }
                                }
                            }
                            System.out.println("   ===========================================================================================================================================================");
                            //If no current appointment found
                            if (!found) {
                                System.out.println("\n\t\t    -[ Currently no payment id found.Thanks you.  ]-");
                            }
                            break;
                        case 2://If the staff want to track the Payment Date
                            while (true) {
                                try {
                                    do {
                                        System.out.printf("\n\t  Please enter the payment date to trace the records (yyyy-mm-dd) :");
                                        traceItems = scanner.nextLine().trim();
                                        //Check date format
                                        if (traceItems.length() != 10) {
                                            System.out.printf("\n\t\t      * Please enter the correct date format *\n");
                                        } else if (traceItems.charAt(4) != '-' || traceItems.charAt(7) != '-') {
                                            System.out.println("            -[ Your date required a slash '-' ]-");
                                        }
                                    } while (traceItems.length() != 10 || traceItems.charAt(4) != '-' || traceItems.charAt(7) != '-');
                                } catch (Exception e) {
                                    System.out.printf("\n\t\t                  -[ Sorry .Invalid response.Try Again ]- \n");
                                }
                                //Get day,month and year from the string
                                int day = Integer.parseInt(traceItems.substring(8, 10));
                                int month = Integer.parseInt(traceItems.substring(5, 7));
                                //Validation for date
                                if ((day > 31 || day < 0) || (month < 0 || month > 12)) {
                                    System.out.printf("\n                 -[ Sorry invalid date provided. Please TRY AGAIN !]- \n");
                                } else
                                    break;
                            }
                            count = 1;
                            LocalDate dateTime = LocalDate.parse(traceItems);
                            System.out.printf("\n\n\t\t                             PAYMENT DATE ON %s DETAILS", traceItems);
                            fullPaymentHeader();
                            for (Appointment appointment : appointmentArrayList) {
                                if (appointment.getPayment() != null) {
                                    if (appointment.getPayment().getPaymentDateTime().toLocalDate().compareTo(dateTime) == 0) { //Compare time
                                        System.out.printf("       %02d-  %s", count, appointment.fullPaymentToString());
                                        found = true;
                                        count++;
                                    }

                                }
                            }
                            System.out.println("    ==========================================================================================================================================================");
                            //If no current appointment found
                            if (!found) {
                                System.out.println("\n\t\t             -[ Currently no payment date found.Thank you ]-");
                            }
                            break;
                    }
                    break;
                case 3://If want to trace deposit details
                    paymentChoice = 0;
                    while (true) {
                        try {
                            System.out.printf("\n      Which deposit details do you wish to search ( 1 )-Deposit ID or ( 2 )-Deposit Date :");
                            paymentChoice = Integer.parseInt(scanner.nextLine());
                        } catch (Exception e) {
                            System.out.printf("\n\t\t              -[ Sorry .Invalid response.Try Again ]- \n\n");
                        }
                        if (paymentChoice != 1 && paymentChoice != 2)
                            System.out.printf("\n\t\t          -[  Invalid choice. Please enter 1 or 2 as your choice ]- \n\n");
                        else
                            break;
                    }
                    switch (paymentChoice) {
                        case 1://If the staff want to track the deposit ID
                            do {
                                System.out.printf("\n\t  Please enter Deposit ID to track ('PXXXXXXXXX') :");
                                traceItems = scanner.nextLine().trim();
                            } while (!validationForId(2, traceItems));
                            System.out.printf("\n\n                                                    DEPOSIT ID(%s) DETAILS", traceItems);
                            depositHeader();
                            for (Appointment appointment : appointmentArrayList) {
                                if(appointment.getPayment()==null){
                                    if (appointment.getDeposit().getPaymentId().equals(traceItems)) {
                                        System.out.printf("      01 - %s", appointment.depositPaymentToString());
                                        found = true;
                                    }
                                }
                            }
                            System.out.println("    ==========================================================================================================================================================");
                            System.out.println("\n\t\t                                       ***** Noted : Only deposit paid ********");
                            //If no deposit id found
                            if (!found) {
                                System.out.println("\n\t\t                                -[ Currently no deposit id found. Thank you  ]-");
                            }
                            break;
                        case 2://If the staff want to track the deposit date
                            while (true) {
                                try {
                                    do {
                                        System.out.printf("\n\t  Please enter the deposit date to trace the records (yyyy-mm-dd) :");
                                        traceItems = scanner.nextLine().trim();
                                        //Check date format
                                        if (traceItems.length() != 10) {
                                            System.out.printf("\n\t\t      * Please enter the correct date format *\n");
                                        } else if (traceItems.charAt(4) != '-' || traceItems.charAt(7) != '-') {
                                            System.out.println("\n             -[ Your date required a slash '-' ]-");
                                        }
                                    } while (traceItems.length() != 10 || traceItems.charAt(4) != '-' || traceItems.charAt(7) != '-');
                                } catch (Exception e) {
                                    System.out.printf("\n\t\t           -[ Sorry .Invalid date.Try Again ]-\n");
                                }
                                //Get day,month and year from the string
                                int day = Integer.parseInt(traceItems.substring(8, 10));
                                int month = Integer.parseInt(traceItems.substring(5, 7));
                                //Validation for date
                                if ((day > 31 || day < 0) || (month < 0 || month > 12)) {
                                    System.out.printf("\n        -[  Sorry invalid date provided. Please Try Again ! ]-\n");
                                } else
                                    break;
                            }
                            LocalDate dateTime = LocalDate.parse(traceItems);

                            count = 1; //Reset count
                            System.out.printf("\n\n\t                                   DEPOSIT DATE ON %s DETAILS", traceItems);
                            depositHeader();
                            for (Appointment appointment : appointmentArrayList) {
                                if(appointment.getPayment()==null){
                                    if (appointment.getDeposit().getPaymentDateTime().toLocalDate().compareTo(dateTime) == 0) {
                                        System.out.printf("       %02d-  %s", count, appointment.depositPaymentToString());
                                        found = true;
                                        count++;
                                    }
                                }

                            }
                            System.out.println("    ==========================================================================================================================================================");
                            System.out.println("\t\t                                       ***** Noted : Only deposit paid ********\n\n");

                            //If no current appointment found
                            if (!found) {
                                System.out.println("\n\t\t                              -[ Currently No deposit date found.Thank you ]-");
                            }
                            break;
                    }
                    break;
                case 4: //Display all payment records
                    count = 1;
                    System.out.printf("\n\n                                                 - F U L L  P A Y M E N T  R E C O R D S  -");
                    fullPaymentHeader();

                    for (Appointment appointment : appointmentArrayList) {
                        //If full payment has done
                        if (appointment.getPayment() != null) {

                            double subtotal = appointment.getService().getServicePrice() + (0.04 * appointment.getService().getServicePrice()) + (0.06 * appointment.getService().getServicePrice());
                            System.out.printf("       %02d-  %s", count, appointment.fullPaymentToString());
                            found = true;
                            count++;
                        }
                    }

                    System.out.println("    ==========================================================================================================================================================\n");
                    System.out.printf("                                        -[ Total %02d people found in full payment records  ]-\n\n",count-1);
                    count = 1;
                    System.out.printf("\n                                          - D E P O S I T   P A Y M E N T  R E C O R D S  -");
                    depositHeader();
                    for (Appointment appointment : appointmentArrayList) {
                        if (appointment.getPayment() == null) { //Only if deposit made , no payment made
                            System.out.printf("       %02d-  %s", count, appointment.depositPaymentToString());
                            found = true;
                            count++;
                        }
                    }
                    System.out.println("    ==========================================================================================================================================================");
                    System.out.println("\n\t\t                                       ***** Noted : Only deposit paid ********");
                    System.out.printf("\n                                          -[  Total %02d people found in deposit payment records  ]-\n",count);
                    if (!found) {
                        System.out.println("                                                -[ Currently no payment records found ]-");
                    }
                    break;
            }
            String continueTrace = "";
            while (true) {
                System.out.printf("\n\t >> Do you want continue to trace the payment records ('Y'-Continue) or ('N'-Back To Menu) : ");
                continueTrace = scanner.nextLine().toUpperCase().trim();
                if (!continueTrace.equals("Y") && !continueTrace.equals("N") || continueTrace.isEmpty()) {
                    System.out.printf("\n\t\t             -[ Sorry .Invalid response.Try Again ]-\n");
                } else
                    break;
            }
            if (continueTrace.equals("N"))  //If the staff don't want to continue trace the payment records
                break;
        }
    }

    //For staff to search for customers' appointment index
    public static int searchCustomerAppIndex(ArrayList<Appointment> appointmentArrayList, String appId) {
        int appIndex = -1;
        for (int i = 0; i < appointmentArrayList.size(); i++) {
            //If have existing record
            if (appointmentArrayList.get(i).getPayment() == null) {
                if (appointmentArrayList.get(i).getAppointmentID().trim().toUpperCase().equals(appId)) {
                    return i;
                }
            }
        }
        return appIndex;
    }

    //Let staff make customer full payment (Offline payment method)
    public static void checkOutCustomerBills(ArrayList<Appointment> appointmentArrayList) {
        Scanner scanner = new Scanner(System.in);
        Main.functionHeader(26);
        System.out.println("\n\n          ========================================================================");
        System.out.println("          |        E C O A T   S T A F F   C H E C K - O U T  B I L L S          |");
        System.out.println("          |                                                                      |");
        System.out.printf("          |                                           Date : %-18s |\n", LocalDateTime.now().toString().substring(0, 19));
        System.out.println("          |                         ydds                                         |");
        System.out.println("          |                      ..dMMd-.`                                       |");
        System.out.println("          |                   -ohmNMMMMNmdy/`                                    |");
        System.out.println("          |                 `sNMMMMMMMMMMMMMm:                                   |");
        System.out.println("          |                 sMMMMMMy++sdMMMNNm.                                  |");
        System.out.println("          |                  NMMMMMN:``` +o/-.                                   | ");
        System.out.println("          |                  dMMMMMMMMmdys+-`               K I N D L Y          |");
        System.out.println("          |                  .mMMMMMMMMMMMMMMMh-              M A K E            |");
        System.out.println("          |                    `/hNMMMMMMMMMMMMM+                Y O U R         |");
        System.out.println("          |                        ..:+sydNMMMMMNNN`         B A L A N C E       |");
        System.out.println("          |                  oyhmNNh-    .MMMMMMM`              B Y  N O W       |");
        System.out.println("          |                  +MMMMMMNdyyymMMMMMMs                                |");
        System.out.println("          |                    :dMMMMMMMMMMMMMMmo`                               | ");
        System.out.println("          |                      `:sdmNMMMMNmdy/.            T H A N K  Y O U    | ");
        System.out.println("          |                       `..dMMd..`                      - E C O A T    |");
        System.out.println("          |                          ydds                                        |");
        System.out.println("          ========================================================================");
        int countId = 1;
        System.out.println("\n                  Below shows customers who haven't pay for the full payment");
        System.out.println("       ---------------------------------------------------------------------------------");
        System.out.println("            No.      Customer ID     Appointment ID     Appointment Date     Balance   ");
        System.out.println("       ---------------------------------------------------------------------------------");
        boolean proceedPayment = false;
        for (Appointment appointment : appointmentArrayList) {
            //Show all customers who haven't pay the balance
            if (appointment.getPayment() == null) {
                double amount = appointment.getService().getServicePrice();
                //Subtotal=service charge 4 % + SST 6%
                double subtotal = amount + (0.04 * amount) + (0.06 * amount);
                //Balance = subtotal- deposit
                double balance = subtotal - 100.00;
                System.out.printf("            %02d       %-13s     %-13s      %-13s     RM%5.2f\n", countId, appointment.getCar().getCustomer().getUserID(), appointment.getAppointmentID(), appointment.getDate(), balance);
                countId++;
                proceedPayment = true;
            }
        }

        //Only payment ID found can proceed
        if (proceedPayment == true) {
            int appIndex = 0;
            String appId = "";
            while (true) {
                System.out.printf("\n       >> Enter your Appointment ID ('ADDMMYYYYXX') to make your full payment : ");
                appId = scanner.nextLine().toUpperCase().trim();
                if (appId.length() != 11) //Validation for length
                    System.out.println("\n\t\t         -[ Invalid length , try again ]-");
                else if (appId.length() == 11) { //If length correct
                    appIndex = searchCustomerAppIndex(appointmentArrayList, appId);
                    if (appIndex == -1) //If index return is -1 , not payment ID found
                    {
                        System.out.println("\n\t\t         -[ Payment ID not found ]-");
                    } else break;
                }
            }
            double amount = appointmentArrayList.get(appIndex).getService().getServicePrice();
            //Subtotal=service charge 4 % + SST 6%
            double subtotal = amount + (0.04 * amount) + (0.06 * amount);
            //Balance = subtotal- deposit
            double balance = subtotal - 100.00;
            System.out.println("\n\t\t==========================================================================");
            System.out.printf("\t\t|                                                            #%10s |\n", appointmentArrayList.get(appIndex).getCar().getCustomer().getUserID().trim());
            System.out.println("\t\t|                         S U M M A R Y                                  |");
            System.out.println("\t\t|                                                                        |");
            System.out.printf("\t\t|    * Service Type                          :%-18s         |\n", appointmentArrayList.get(appIndex).getService().getServiceType());
            System.out.printf("\t\t|    * Service Amount                        :RM%-8.2f                 |\n", appointmentArrayList.get(appIndex).getService().getServicePrice());
            System.out.printf("\t\t|    * Subtotal (6%% SST + 4%% Service Charge) :RM%-8.2f                 |\n", subtotal);
            System.out.println("\t\t|                                                                        |");
            System.out.println("\t\t|                               ######################################## |");
            System.out.printf("\t\t|                                $ Balance (Exclude deposit) :RM%-5.2f   |\n", balance);
            System.out.println("\t\t|                               ######################################## |");
            System.out.println("\t\t|                                                                        |");
            System.out.println("\t\t|------------------------------------------------------------------------|");
            System.out.println("\t\t|   Terms of payment :                                                   |");
            System.out.println("\t\t| * All deposit are NOT refundable of any cancellation appointment       |");
            System.out.println("\t\t==========================================================================");
            System.out.printf("\n\t\t\t\t $$$ Currently the customer left RM%.2f balance $$$\n", balance);
            String checkOut = "";
            while (true) {
                try {
                    System.out.printf("\n\t\t  Do you want to continue proceeding your full payment ? ('Y' or 'N') :");
                    checkOut = scanner.nextLine().toUpperCase().trim();
                } catch (Exception e) { //Catch for any exception
                    System.out.printf("\n\t\t-[   Invalid Response .Try Again ]-\n");
                }
                //Invalid response
                if (!checkOut.equals("Y") && !checkOut.equals("N") || checkOut.isEmpty())
                    System.out.printf("\n\t\t                 -[ Invalid Response .Try Again ]-\n");
                else break;
            }
            //If confirm to check out
            if (checkOut.equals("Y")) {
                //Create payment object
                Payment payment = new Payment("Offline Payment");
                appointmentArrayList.get(appIndex).setPayment(payment);
                appointmentArrayList.get(appIndex).setPayment(payment);//Set payment
                //Display receipt
                System.out.println("\n\n\t\t--------------------------------------------------------------");
                System.out.println("\t\t|                    TRANSACTION RECEIPT                     |");
                System.out.println("\t\t|                                                            |");
                System.out.printf("\t\t|                                  Payment ID :%-12s  |\n", payment.getPaymentId());
                System.out.printf("\t\t|                                  Date       :%-12s  |\n", LocalDate.now());//Get date
                System.out.printf("\t\t|                                  Time       :%-12s  |\n", LocalTime.now().toString().substring(0, 8));//Get time
                System.out.println("\t\t|____________________________________________________________|");
                System.out.printf("\t\t| Appointment ID :%-10s     Customer ID :%-10s    |\n", appointmentArrayList.get(appIndex).getAppointmentID().trim(), appointmentArrayList.get(appIndex).getCar().getCustomer().getUserID().trim());
                System.out.println("\t\t|____________________________________________________________|");
                System.out.println("\t\t|                S     A     L     E     S                   |");
                System.out.println("\t\t|                                                            |");
                System.out.println("\t\t|       Items                                  Amount        |");
                System.out.printf("\t\t|  %-23s                  >> RM%-7.2f     |\n", appointmentArrayList.get(appIndex).getService().getServiceType(), appointmentArrayList.get(appIndex).getService().getServicePrice());
                System.out.println("\t\t|                                                            |");
                System.out.println("\t\t|============================================================|");
                System.out.printf("\t\t| Subtotal (6%%SST + 4%%Service Charge)        $ RM%-8.2f    |\n", subtotal);
                System.out.printf("\t\t| Payment Type  : %-18s                         |\n", appointmentArrayList.get(appIndex).getPayment().getPaymentType());
                System.out.println("\t\t|____________________________________________________________|");
                System.out.println("\t\t|            T   H    A   N    K        Y    O   U           |");
                System.out.println("\t\t|                                                            |");
                System.out.println("\t\t|                               - E C O A T   C O A T I N G  |");
                System.out.println("\t\t--------------------------------------------------------------");
                System.out.println("\n\t\t\t     Press 'ENTER' to continue your payment ! ");
                scanner.nextLine();

            } //If they don't want to make a payment
            else {
                System.out.println("\n\t\t\t\t          - [ No payment made ]-");
            }
        } else {
            System.out.printf("\n    >> Sorry currently no payment ID exists .Press ENTER to continue.");
            scanner.nextLine();
        }
    }

    public static void sortingPaymentRecords(ArrayList<Appointment> appointmentArrayList) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String paymentStartDate = "", paymentEndDate = "";
        LocalDateTime startDate ;
        LocalDateTime endDate;
        int userChoice = 0; // user choice for sort date

        while (true) { //Looping for invalid choice
            try {
                Main.functionHeader(31);
                System.out.println("\t\t\t    +===========================================================================+");
                System.out.println("\t\t\t\t|    Welcome here ! Choose the type of sorting payment date you wish to     |");
                System.out.println("\t\t\t\t|                ( 1 ) Sort from the oldest date                            |");
                System.out.println("\t\t\t\t|                ( 2 ) Sort from the latest date                            |");
                System.out.println("\t\t\t\t|                ( 3 ) Back to Payment Module                               | ");
                System.out.println("\t\t\t    +===========================================================================+");
                System.out.printf("\t\t\t\t                Please enter your choice >> ");
                String menuChoice = scanner.nextLine();
                //Change string to integer
                userChoice = Integer.parseInt(menuChoice.trim());
            }
            //To prevent user input invalid data type
            catch (Exception e) {
                System.out.printf("\n\t\t\t\t            -[   Invalid Response .Try Again ]-\n");
            }
            if (userChoice < 1 || userChoice > 3) {
                System.out.printf("\n\t\t\t\t            -[   Invalid Response .Try Again ]-\n");
            } else break; //Valid choice will out of the looping
        }
        int day = 0, month = 0;
        switch (userChoice) {
            case 1:
            case 2:
                do {
                    while (true) {
                        try {
                            do {
                                System.out.printf("\n\t  Please enter the payment start date to trace the records (yyyy-mm-dd) >> ");
                                paymentStartDate = scanner.nextLine().trim();
                                //Check date format
                                if (paymentStartDate.length() != 10) {
                                    System.out.printf("\n\t\t      * Please enter the correct date format *\n");
                                } else if (paymentStartDate.charAt(4) != '-' || paymentStartDate.charAt(7) != '-') {
                                    System.out.println("\n             -[ Your date required a slash '-' ]-");
                                }
                            } while (paymentStartDate.length() != 10 || paymentStartDate.charAt(4) != '-' || paymentStartDate.charAt(7) != '-');
                        } catch (Exception e) {
                            System.out.printf("\n\t\t           -[ Sorry .Invalid date.Try Again ]-\n");
                        }
                        //Get day,month and year from the string
                        day = Integer.parseInt(paymentStartDate.substring(8, 10));
                        month = Integer.parseInt(paymentStartDate.substring(5, 7));
                        //Validation for date
                        if ((day > 31 || day < 0) || (month < 0 || month > 12)) {
                            System.out.printf("\n        -[  Sorry invalid date provided. Please Try Again ! ]-\n");
                        } else
                            break;
                    }
                    paymentStartDate = paymentStartDate.substring(8, 10) + '/' + paymentStartDate.substring(5, 7) + '/' + paymentStartDate.substring(0, 4);
                    while (true) {
                        try {
                            do {
                                System.out.printf("\n\t  Please enter the payment end date to trace the records (yyyy-mm-dd)   >> ");
                                paymentEndDate = scanner.nextLine().trim();
                                //Check date format
                                if (paymentEndDate.length() != 10) {
                                    System.out.printf("\n\t\t      * Please enter the correct date format *\n");
                                } else if (paymentEndDate.charAt(4) != '-' || paymentEndDate.charAt(7) != '-') {
                                    System.out.println("\n             -[ Your date required a slash '-' ]-");
                                }
                            } while (paymentEndDate.length() != 10 || paymentEndDate.charAt(4) != '-' || paymentEndDate.charAt(7) != '-');
                        } catch (Exception e) {
                            System.out.printf("\n\t\t           -[ Sorry .Invalid date.Try Again ]-\n");
                            scanner.nextLine();
                        }
                        //Get day,month and year from the string
                        day = Integer.parseInt(paymentEndDate.substring(8, 10));
                        month = Integer.parseInt(paymentEndDate.substring(5, 7));
                        //Validation for date
                        if ((day > 31 || day < 0) || (month < 0 || month > 12)) {
                            System.out.printf("\n        -[  Sorry invalid date provided. Please Try Again ! ]-\n");
                        } else
                            break;
                    }
                    paymentEndDate = paymentEndDate.substring(8, 10) + '/' + paymentEndDate.substring(5, 7) + '/' + paymentEndDate.substring(0, 4);
                    //pass inputDate into LocalDateTime and update chosen Time
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate convertDate = LocalDate.parse(paymentStartDate, formatter);
                    startDate = convertDate.atStartOfDay();

                    convertDate = LocalDate.parse(paymentEndDate, formatter);
                    endDate = convertDate.atStartOfDay();
                    if (startDate.isAfter(endDate)) {
                        System.out.println("\n             -[ Start date cannot be after end date ]-\n");
                    }
                } while (startDate.isAfter(endDate));
                if (startDate.equals(endDate)) {
                    endDate = endDate.plusDays(1);
                }
                int found = 0;
                System.out.printf("\n\n                                          -  P A Y M E N T   H I S T O R Y   R E C O R D S  -");
                System.out.printf("\n     ========================================================================================================================================\n");
                System.out.printf("        No.  Payment ID      Customer ID        Phone No       Appointment ID      Transaction Date      Subtotal       Full Payment status\n");
                System.out.printf("     ========================================================================================================================================\n");
                paymentSortTime(appointmentArrayList, appointmentArrayList.size(), userChoice - 1);
                for (int i = 0; i < appointmentArrayList.size(); i++) {
                    if ((startDate.toLocalDate()).equals(appointmentArrayList.get(i).getDeposit().getPaymentDateTime().toLocalDate()) || (endDate.toLocalDate()).equals(appointmentArrayList.get(i).getDeposit().getPaymentDateTime().toLocalDate()) ||
                            appointmentArrayList.get(i).getAppointmentTime().isAfter(startDate) && appointmentArrayList.get(i).getAppointmentTime().isBefore(endDate)) {
                        System.out.printf("\t    %02d - %s", found + 1, appointmentArrayList.get(i).checkFullPaymentToString());
                        found++;
                    }
                }
                System.out.printf("     ========================================================================================================================================\n");
                if (found == 0) {
                    System.out.printf("\n                                  -[ There is no records between  %s and %s ]-", paymentStartDate, paymentEndDate);
                } else if (found > 0) {
                    System.out.printf(" \t\t\t\t                                      -[ Total %d payment records found ! ]-", found);
                }
                break;
            case 3:
                staffPaymentMenu();
        }
    }

    //For staff to sort the payment time
    public static void paymentSortTime(ArrayList<Appointment> arrayListAppointment, int size, int choice) {
        //choice 1 is before to after
        //choice 0 is after to before
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (choice == 0) {
                    if (arrayListAppointment.get(j).getAppointmentTime().isAfter(arrayListAppointment.get(j + 1).getAppointmentTime())) {
                        Collections.swap(arrayListAppointment, j, j + 1);
                    }
                } else {
                    if (arrayListAppointment.get(j).getAppointmentTime().isBefore(arrayListAppointment.get(j + 1).getAppointmentTime())) {
                        Collections.swap(arrayListAppointment, j, j + 1);
                    }
                }
            }
        }
    }


    public static void mainPayment(UserInfo loginUser, ArrayList<Appointment> arrayListAppointment) throws IOException, ParseException, InterruptedException {

        //Full payment will return payment object
        //Go to customer menu and select the menu choice
        if (loginUser instanceof Customer) {
            Customer customer = (Customer) loginUser;
            int customerMenuChoice;
            do {
                customerMenuChoice = customerPaymentMenu();
                switch (customerMenuChoice) {
                    case 1://For full payment check-out
                        fullPayment(arrayListAppointment, customer);
                        break;
                    case 2: //If customer want to view their payment history records
                        customerPaymentHistory(arrayListAppointment, customer);
                        break;
                }
            } while (customerMenuChoice != 3);
        } else if (loginUser instanceof Staff) {
            int staffMenuChoice;
            do {
                staffMenuChoice = staffPaymentMenu();
                switch (staffMenuChoice) {
                    case 1:
                        dailySalesReport(arrayListAppointment);
                        break;
                    case 2:
                        monthlySalesReport(arrayListAppointment);
                        break;
                    case 3:
                        tracePaymentTransaction(arrayListAppointment);
                        break;
                    case 4:
                        checkOutCustomerBills(arrayListAppointment);
                        break;
                    case 5:
                        sortingPaymentRecords(arrayListAppointment);
                        break;
                    case 6://back to homepage
                        break;

                }
            } while (staffMenuChoice != 6);
        }


    }
}





