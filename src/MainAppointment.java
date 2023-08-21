import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainAppointment{

    //Color word
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void mainAppointment(UserInfo loginAccount , ArrayList<Service> serviceArrayList , ArrayList<Customer> customersArrayList , ArrayList<Car> carsArrayList , ArrayList<Appointment> appointmentArrayList, ArrayList<UserInfo> userInfoArrayList) throws InterruptedException, IOException, ParseException {
        Scanner input = new Scanner(System.in);
        LocalDateTime[] thisWeekAppointmentDate = new LocalDateTime[7];
        //for input choice
        String choiceString;
        int choiceInt;

        do {
            Main.functionHeader(1);
            System.out.println("\t\t\t\t\t_________________________________________________________________");
            System.out.println("\t\t\t\t\t/_-_-_-_-_-_-Welcome to Appointment Module Menu_-_-_-_-_-_-_-_-\\");
            System.out.println("\t\t\t\t\t.'.'.''.'..'.'.'[1] Display Available time     .'.'.'.'.'.'.'.'.");
            System.out.println("\t\t\t\t\t.'.'.''.'..'.'.'[2] Add new Appointment        .'.'.'.'.'.'.'.'.");
            System.out.println("\t\t\t\t\t.'.'.''.'..'.'.'[3] Edit Appointment Details   .'.'.'.'.'.'.'.'.");
            System.out.println("\t\t\t\t\t.'.'.''.'..'.'.'[4] Display Appointment History.'.'.'.'.'.'.'.'.");
            System.out.println("\t\t\t\t\t.'.'.''.'..'.'.'[5] Cancel Appointment         .'.'.'.'.'.'.'.'.");
            System.out.println("\t\t\t\t\t.'.'.''.'..'.'.'[6] Back to HomePage           .'.'.'.'.'.'.'.'.");
            System.out.println("\t\t\t\t\t-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=-=-=-=-=-=-=-");


            do {
                System.out.print("\t\t\t\t\t             -=-=-=-=--Your Choice:");
                choiceString = input.nextLine().trim();
            } while (!validateChoice(choiceString, 6));

            choiceInt = Integer.parseInt(choiceString.trim());//parse into Int
            switch (choiceInt) {
                case 1:
                    displayAppointment(appointmentArrayList, thisWeekAppointmentDate);
                    break;
                case 2:
                    addAppointment(appointmentArrayList, serviceArrayList, carsArrayList, loginAccount);
                    break;
                case 3:
                    editAppointmentMenu(appointmentArrayList, loginAccount, serviceArrayList,userInfoArrayList);
                    break;
                case 4:
                    displayAppointmentMenu(appointmentArrayList, loginAccount, serviceArrayList, customersArrayList);
                    break;
                case 5:
                    deleteAppointmentMenu(appointmentArrayList, loginAccount);
                    break;
                case 6:
                    break;
            }
            System.out.println("\n\n\n\n\n");
        }while(choiceInt!=6);



    }

    public static void displayAppointment(ArrayList<Appointment> arrayListAppointment,LocalDateTime[] thisWeekAppointmentDate){
        //Declare localDateTime
        LocalDateTime specificTimeChoose=LocalDateTime.now();
        //format the time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //Display menu whether to display this week appointment list or specific week appointment list
        Scanner input = new Scanner(System.in);

        Main.functionHeader(18);
        System.out.println("\t\t\t\t\t--------------Welcome to display Available Time----------------");
        System.out.println("\t\t\t\t\t|            1.Display this week appointment list             |");
        System.out.println("\t\t\t\t\t|            2.Display specific week                          |");
        System.out.println("\t\t\t\t\t---------------------------------------------------------------");

        String choiceInput;
        int choiceInt=0;
        String inputDate;

        do {
            System.out.print("\t\t\t\t\t       Enter your choice(Type XXX to quit):");
            choiceInput = input.nextLine().trim();
            if (choiceInput.equalsIgnoreCase("XXX")) {
                break;
            }
        } while (!validateChoice(choiceInput, 2));

        if(!choiceInput.equalsIgnoreCase("XXX")) {
            choiceInt = Integer.parseInt(choiceInput.trim());



            if (choiceInt == 2) {
                do {
                    System.out.print("\t\t\t\t\t       Enter Appointment Date in (dd/mm/yyyy)(03/03/2021): ");
                    inputDate = input.next().trim();
                } while (!isValidDate(inputDate, 'R'));

                //need to validate if its valid date or not !!!

                LocalDate convertDate = LocalDate.parse(inputDate, formatter);
                specificTimeChoose = convertDate.atStartOfDay();
            }

            //getAllDateInThisWeek
            getAllDateInThisWeek(specificTimeChoose, thisWeekAppointmentDate);
            //get the Date like (19/7/2020)
            String dateTimeString = specificTimeChoose.format(formatter);

            //get the day(monday/tuesday etc)
            DayOfWeek nowDayOfWeek = specificTimeChoose.getDayOfWeek();

            //innitialize the dayofweek
            String[] dayOfWeek = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};

            int totalAccumulateBookingOfTheWeek = 0;

            System.out.printf("\nTime Table of day %10s - %10s", thisWeekAppointmentDate[0].format(formatter), thisWeekAppointmentDate[6].format(formatter));
            System.out.printf("\nCurrent Date is %s (%s)\n", dateTimeString, nowDayOfWeek);
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            System.out.println("Day\\Time  | 8.00AM -10.00AM | 10.00AM -12.00PM | 12.00PM-2.00PM | 2.00PM-4.00PM | 4.00PM-6.00PM | 6.00PM-8.00PM |");

            for (int i = 0; i < 7; i++) {
                //Coloured out today day of week the code is turn nowDayOfWeek to string
                if (dayOfWeek[i].compareTo(nowDayOfWeek.toString()) == 0) {
                    System.out.printf("%s%10s%s|\n", ANSI_GREEN, dayOfWeek[i], ANSI_RESET);
                    System.out.printf("%s%10s%s|", ANSI_GREEN, thisWeekAppointmentDate[i].format(formatter), ANSI_RESET);
                } else {
                    System.out.printf("%10s|\n", dayOfWeek[i]);
                    System.out.print(thisWeekAppointmentDate[i].format(formatter) + "|");
                }

                //loop all arrayListAppointment to check all arraylist object
                int hour = 8;
                for (int y = 0; y < 6; y++) {
                    int found = 0;
                    for (int x = 0; x < arrayListAppointment.size(); x++) {
                        //to meet two condition:the date is the same and the hour is the same
                        if (((arrayListAppointment.get(x)).getAppointmentTime().toLocalDate()).equals(thisWeekAppointmentDate[i].toLocalDate())) {
                            if (((arrayListAppointment.get(x)).getHour() == hour)) {
                                found = 1;
                                totalAccumulateBookingOfTheWeek++;
                            }
                        }
                    }
                    if (found == 0) {
                        System.out.printf("%s        /        %s", ANSI_GREEN, ANSI_RESET);
                    }
                    else {
                        System.out.printf("%s        X        %s", ANSI_RED, ANSI_RESET);
                    }
                    hour = hour + 2;
                }
                System.out.println();

            }
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            System.out.printf("Left space to be booked :%d", 42 - totalAccumulateBookingOfTheWeek);
        }
    }

    public static void getAllDateInThisWeek(LocalDateTime specificDate,LocalDateTime[] thisWeekAppointmentDate){
        //this method is to check monday to sunday of the specific week that the user wanted

        //get the day of the week in string
        DayOfWeek nowDayOfWeek = specificDate.getDayOfWeek();

        //convert enum dayofweek(eg:Saturday) to int like 0 - 6
        //save the date into the object array by its day_of_week (for the specific day)
        int dayOfWeekInt;

        if(nowDayOfWeek.toString().compareTo("MONDAY")==0){
            dayOfWeekInt = 0 ;
            thisWeekAppointmentDate[0]=specificDate;
        }
        else if(nowDayOfWeek.toString().compareTo("TUESDAY")==0){
            dayOfWeekInt = 1 ;
            thisWeekAppointmentDate[1]=specificDate;
        }
        else if(nowDayOfWeek.toString().compareTo("WEDNESDAY")==0){
            dayOfWeekInt = 2 ;
            thisWeekAppointmentDate[2]=specificDate;
        }
        else if(nowDayOfWeek.toString().compareTo("THURSDAY")==0){
            dayOfWeekInt = 3 ;
            thisWeekAppointmentDate[3]=specificDate;
        }
        else if(nowDayOfWeek.toString().compareTo("FRIDAY")==0){
            dayOfWeekInt = 4 ;
            thisWeekAppointmentDate[4]=specificDate;
        }
        else if(nowDayOfWeek.toString().compareTo("SATURDAY")==0){
            dayOfWeekInt = 5 ;
            thisWeekAppointmentDate[5]=specificDate;
        }
        else{
            dayOfWeekInt = 6 ;
            thisWeekAppointmentDate[6]=specificDate;
        }


        //this variable consider the smallest to the largest (0-6) consider the index of the day
        int count=0;

        if(count == dayOfWeekInt){
            count++;
        }

        //know the date that are before the specific days but in the same week
        for(int i=dayOfWeekInt;i>0;i--){
            //this week appointment date starting from monday will keep in this object
            thisWeekAppointmentDate[count]=specificDate.minusDays(i);
            count++;

            //this avoid the dayofweek that predefined collapse agn
            if(count==dayOfWeekInt){
                count++;
            }
        }

        //know the date that are after the specific days but in the same week
        for(int i = 0;i<6-dayOfWeekInt;i++){
            thisWeekAppointmentDate[count]=specificDate.plusDays(i+1);
            count++;

            //this avoid the dayofweek that predefined collapse agn
            if(count==dayOfWeekInt){
                count++;
            }
        }
    }

    //link to service type and customer arraylist too
    public static void addAppointment(ArrayList<Appointment> arrayListAppointment,ArrayList<Service> serviceArrayList,ArrayList<Car> carsArrayList,UserInfo userInfo) throws InterruptedException, IOException, ParseException {
        //Validate did they have a car register in the system
        if(userInfo instanceof Customer) {
            Customer customer = (Customer)userInfo;
            ArrayList<Car> localCarArray = new ArrayList<Car>();
            for (int i = 0; i < carsArrayList.size(); i++) {
                if (customer.getUserID().equalsIgnoreCase((carsArrayList.get(i)).getCustomer().getUserID())) {
                    localCarArray.add(carsArrayList.get(i));
                }
            }
            if (localCarArray.size() > 0) {
                //Validate did they pay their last pay
                int foundLastPaymentNoPay = 0;
                for (int i = 0; i < arrayListAppointment.size(); i++) {
                    if (customer.getUserID().equalsIgnoreCase(arrayListAppointment.get(i).getCar().getCustomer().getUserID())) {
                        if (arrayListAppointment.get(i).getAppointmentTime().isBefore(LocalDateTime.now())) {
                            if (arrayListAppointment.get(i).getPayment() == null) {
                                foundLastPaymentNoPay++;
                            }
                        }
                    }
                }

                //if they have pay for previous payment or they have no appointment on going
                if (foundLastPaymentNoPay == 0) {
                    Scanner input = new Scanner(System.in);
                    LocalDateTime chosenTime = LocalDateTime.now();
                    //Input variable (String) by user
                    String inputDate;
                    String choiceTime;
                    String serviceID;
                    String inputCar;

                    //parse from string to int user choice
                    int choiceServiceID;
                    int choiceTimeInt;
                    int choiceCarInt;

                    Main.functionHeader(19);
                    //let user choose time
                    do {
                        do {
                            System.out.print("\t\t\t  Enter Appointment Date in (dd/mm/yyyy)(03/03/2021): ");
                            inputDate = input.next().trim();
                        } while (!isValidDate(inputDate, 'R'));

                        //pass inputDate into LocalDateTime and update chosen Time
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate convertDate = LocalDate.parse(inputDate, formatter);
                        chosenTime = convertDate.atStartOfDay();


                        //menu for time list
                        System.out.println("\t\t\t\t                -----------------");
                        System.out.println("\t\t\t\t                |Enter your Time|");
                        System.out.println("\t\t\t\t----------------------------------------------------");
                        System.out.println("\t\t\t\t|   [1]. 8.00AM-10.00AM       [4]. 2.00PM-4.00PM   |");
                        System.out.println("\t\t\t\t|   [2]. 10.00AM-12.00AM      [5]. 4.00PM-6.00PM   |");
                        System.out.println("\t\t\t\t|   [3]. 12.00PM-2.00PM       [6]. 6.00PM-8.00PM   |");
                        System.out.println("\t\t\t\t----------------------------------------------------");


                        input.nextLine(); // clear buffer

                        do {
                            System.out.print("\t\t\t\t        Your Choice:");
                            choiceTime = input.nextLine().trim();
                        } while (!validateChoice(choiceTime, 6));

                        choiceTimeInt = Integer.parseInt(choiceTime.trim());//parse into Int


                        //put hours inside chosenTime(localDateTime)
                        if (choiceTimeInt == 1) {
                            chosenTime = chosenTime.with(LocalTime.of(8, 0));
                        } else if (choiceTimeInt == 2) {
                            chosenTime = chosenTime.with(LocalTime.of(10, 0));
                        } else if (choiceTimeInt == 3) {
                            chosenTime = chosenTime.with(LocalTime.of(12, 0));
                        } else if (choiceTimeInt == 4) {
                            chosenTime = chosenTime.with(LocalTime.of(14, 0));
                        } else if (choiceTimeInt == 5) {
                            chosenTime = chosenTime.with(LocalTime.of(16, 0));
                        } else {
                            chosenTime = chosenTime.with(LocalTime.of(18, 0));
                        }
                    } while (!appointmentTimeValidate(arrayListAppointment, chosenTime));

                    //Let user choose service
                    MainService_Car.servDisplay(serviceArrayList); //must be deleted or changed after combine
                    do {
                        System.out.print("\t\t\t\t Choose your service number(etc:1) :");
                        serviceID = input.nextLine();
                    } while (!validateChoice(serviceID, serviceArrayList.size()));

                    //Parse choice service from string into Int
                    choiceServiceID = Integer.parseInt(serviceID.trim());

                    System.out.print("\t\t\t\t Enter Appointment comments (Put - if no) :");
                    String appointmentDesc = input.nextLine();


                    //print all the car registered under user account and let them select
                    System.out.println("\t\t\t\t-------------------------------------------------");
                    System.out.printf("\t\t\t\t| Car Plate Number |  Car Name       | Car Type |\n");
                    System.out.println("\t\t\t\t-------------------------------------------------");
                    for (int i = 0; i < localCarArray.size(); i++) {
                        System.out.printf("\t\t\t\t|%d %-16s|%-17s|%-10s|\n", i + 1, (localCarArray.get(i)).getCarNumPlate(), (localCarArray.get(i)).getCarName(), (localCarArray.get(i)).getCarType());
                    }
                    System.out.println("\t\t\t\t-------------------------------------------------");

                    //Let user to choose from localCarArray(Car Registered under their account)
                    do {
                        System.out.print("\n" + "\t\t\t\t Enter your car choice(etc:1) :");
                        inputCar = input.nextLine();
                    } while (!validateChoice(inputCar, localCarArray.size()));

                    choiceCarInt = Integer.parseInt(inputCar.trim());


                    //keep it as local first

                    Payment depositLocal = customerDepositCheckOut(serviceArrayList.get(choiceServiceID - 1).getServicePrice(), customer,serviceArrayList.get(choiceServiceID - 1).getServiceType());

                    if (depositLocal == null) {
                        //if they fail to finnish deposit
                        System.out.println("\t\t\t\tFail to finish deposit");
                        System.out.println(ANSI_RED + "Appointment has not been created" + "\nQuited add appointment." + ANSI_RESET);
                    }
                    else {
                        //add appointment
                        Appointment appointment=new Appointment(depositLocal, chosenTime, serviceArrayList.get(choiceServiceID - 1), localCarArray.get(choiceCarInt - 1), appointmentDesc,null);
                        arrayListAppointment.add(appointment);
                        //Display invoice
                        MainPayment.invoiceDetails(appointment,arrayListAppointment,customer);
                        System.out.println(ANSI_BLUE + "\n\n------------------------Successfully added appointment-----------------------" + ANSI_RESET);
                    }
                } else if (foundLastPaymentNoPay > 0) {
                    //if previous appointment they haven't give full payment
                    System.out.printf("\t\tYou have still %d Appointment haven't pay. Please go to payment and pay\n", foundLastPaymentNoPay);
                    System.out.println(ANSI_RED + "Quited register for new Appointment" + ANSI_RESET);
                }
            } else {
                //if they have no car
                System.out.println("\t\tYou need to have at least 1 car register in the program to add appointment");
                System.out.println(ANSI_RED + "Quited register for new Appointment" + ANSI_RESET);
            }
        }
        else{
            System.out.println("\n                   Admin cannot add appointment!");
        }
    }

    public static Payment customerDepositCheckOut(double amount, Customer customer,String serviceType) throws InterruptedException {
        Payment deposit = new Payment();
        Scanner scanner = new Scanner(System.in);
        String checkOut = "",confirmCheckOut="";
        //Display payment details for user
        Main.functionHeader(20);
        System.out.println("\n\t\t==========================================================================");
        System.out.printf("\t\t|                                                            #%10s |\n", customer.getUserID().trim());
        System.out.println("\t\t|                    Customer Deposit Details                            |");
        System.out.println("\t\t|                                                                        |");
        System.out.println("\t\t|                                                                        |");
        System.out.printf("\t\t|               Service Type               :%-16s             |\n", serviceType);
        System.out.printf("\t\t|               Service Amount             :RM%-8.2f                   |\n", amount);
        System.out.println("\t\t|               Deposit                    :RM100.00                     |");
        System.out.println("\t\t|                                                                        |");
        System.out.println("\t\t|------------------------------------------------------------------------|");
        System.out.println("\t\t|   Terms of payment :                                                   |");
        System.out.printf("\t\t| * You need to pay RM100.00 as a deposit for your appointment           |\n");
        System.out.printf("\t\t| * Full payment is required upon service completed                      |\n");
        System.out.printf("\t\t| * SST and Service charge will be charged when full payment             |\n");
        System.out.println("\t\t| * All deposit are NOT refundable of any cancellation appointment       |");
        System.out.println("\t\t| * Temporary Bill acknowledge                                           |");
        System.out.println("\t\t==========================================================================");
        while (true){
            try {
                System.out.printf("\n\t\t  Do you want to continue proceed your payment ? ('Y' or 'N') :");
                checkOut= scanner.nextLine().toUpperCase().trim();
                //show appointment date time
            } catch (Exception e) {
                System.out.printf("\n\t\t\t\t      -[  Invalid Response .Try Again ]-\n");
            }

            //Invalid and empty responses are not allowed
            if (!checkOut.equals("Y")&& !checkOut.equals("N")|| checkOut.isEmpty())
                System.out.printf("\n\t\t\t\t      -[  Invalid Response .Try Again ]-\n");
            else break;
        }
        if (checkOut.equals("Y")) {
            //Go to payment method process
            int paymentType =MainPayment.paymentMethod();
            while(true){
                try {
                    //Ask user to confirm again
                    System.out.printf("\n\t\t>> Do you confirm to pay RM100.00 as your deposit payment ? ('Y' or 'N') :");
                    //show appointment date time
                    confirmCheckOut = scanner.nextLine().toUpperCase().trim();
                } catch (Exception e) {
                    System.out.printf("\n\t\t\t\t      -[  Invalid Response .Try Again ]-\n");
                }
                if (!confirmCheckOut.equals("Y")&& !confirmCheckOut.equals("N")||confirmCheckOut.isEmpty())
                    System.out.printf("\n\t\t\t\t      -[  Invalid Response .Try Again ]-\n");
                else
                    break; //If valid input out of looping
            }
            switch(confirmCheckOut){
                case "Y":
                    System.out.println("\n\t\t\t*****************************************************");
                    System.out.println("\t\t\t  PAYMENT LOADING PROCESS ,PLEASE BE PATIENCE. #3sec");
                    System.out.println("\t\t\t*****************************************************");
                    //load 3 seconds
                    Thread.sleep(3000);
                    System.out.println("\n\t\t\t\t\t\t         ====================================");
                    System.out.println("\t\t\t\t\t              >> DEPOSIT PAYMENT SUCCESSFULLY <<");
                    String paymentTypeWords = "";
                    //Move their paymentType choice into words ,if paymentType==1->Debit,2->Credit,3->Online Banking
                    paymentTypeWords = paymentType == 1 ? "Debit Card" : paymentType == 2 ? "Credit Card" : "Online Banking";
                    //Create a new deposit object
                    deposit = new Payment(paymentTypeWords);
                    break;
                case "N":
                    System.out.println("\n\t\t\t\t          - [ No payment made ]-");
                    deposit = null;
                    break;
            }
        } else {
            System.out.println("\n\t\t\t\t          - [ No payment made ]-");
            deposit = null;
        }
        //Return deposit object to appointment module
        return deposit;
    }


    private static boolean isValidDate(String input,char mode) {
        //R mode is for register appointment
        //F mode is check format only
        String formatString = "dd/MM/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate convertDate;

        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            format.setLenient(false);
            format.parse(input);
            convertDate = LocalDate.parse(input,formatter);
        } catch (Exception e) {
            System.out.println("\t\t\t"+ANSI_RED + "        Invalid Date, Please re-enter!"+ANSI_RESET);
            return false;
        }

        if(Character.compare(mode,'R')==0) {
            //convertDate (LocalDate data type) into LocalDateTime type
            LocalDateTime dateChoose = convertDate.atStartOfDay();
            LocalDateTime dateNow = LocalDateTime.now();

            //validate it whether the date has passed
            if (dateChoose.isBefore(dateNow)) {
                System.out.println("\t\t\t"+ANSI_RED + "        Date entered has passed , Please re-enter" + ANSI_RESET);
                return false;
            }
        }

        return true;
    }

    public static boolean validateChoice(String choice , int maxInt){
        int choice_int;

        try{
            choice_int = Integer.parseInt(choice.trim());
        }catch(Exception e){
            System.out.println(ANSI_RED + "                     Invalid choice , Try again!"+ANSI_RESET);
            return false;
        }

        choice_int = Integer.parseInt(choice.trim());
        //if choice is bigger than max or smaller than 1 return false
        if(choice_int>maxInt || choice_int<1){
            System.out.println(ANSI_RED + "                     Choice out of range , Try again!"+ANSI_RESET);
            return false;
        }

        return true;

    }

    //validate whether the time is available
    public static boolean  appointmentTimeValidate(ArrayList<Appointment> appointmentArrayList , LocalDateTime specificTimeChoose){
        for(int i = 0 ; i < appointmentArrayList.size();i++){
            if(specificTimeChoose.compareTo((appointmentArrayList.get(i).getAppointmentTime()))==0){
                System.out.println(ANSI_RED+"               Time found! Please choose another Time"+ANSI_RESET);
                return false;
            }
        }
        return true;
    }

    public static void deleteAppointmentMenu(ArrayList<Appointment> appointmentArrayList, UserInfo userInfo){
        if(userInfo instanceof Customer){
            Customer customer = (Customer)userInfo;
            deleteAppointment(appointmentArrayList,customer);
        }
        else{
            //if its staff
            deleteAppointmentAdmin(appointmentArrayList);
        }

    }

    public static void deleteAppointment(ArrayList<Appointment> appointmentArrayList,Customer customer){
        Scanner input = new Scanner(System.in);

        ArrayList<Appointment> localAppointment = new ArrayList<Appointment>();

        int count = 0 ;         //to count how many appointment user have
        String choiceDelete;    // keep as string to validate(user choice)
        int choice ;      //keep user choice to delete


        Main.functionHeader(21);
        System.out.println("\t\t\t\t  ----------------------------------------------------------------------");
        System.out.printf("\t\t\t\t  | AppointmentID |AppointmentDate |AppointmentTime   |Service         |\n");
        System.out.println("\t\t\t\t  ----------------------------------------------------------------------");
        for(int i = 0 ; i < appointmentArrayList.size();i++){
            //validate the login customer account and their appointment
            if((appointmentArrayList.get(i).getCar().getCustomer().getUserID()).equals(customer.getUserID())){
                if(appointmentArrayList.get(i).getAppointmentTime().isAfter(LocalDateTime.now())) {
                    //check whether the appointment has passed
                    System.out.printf("\t\t\t\t%d.| %-13s |%-15s |%3s.00            |%-15s |\n", count+1,appointmentArrayList.get(i).getAppointmentID(), appointmentArrayList.get(i).getDate(), appointmentArrayList.get(i).getHour(),appointmentArrayList.get(i).getService().getServiceType());
                    count++;
                    //add into localAppointment
                    localAppointment.add(appointmentArrayList.get(i));
                }
            }
        }
        System.out.println("\t\t\t\t  ----------------------------------------------------------------------");

        //if no appointment then it will automatically quit this method
        if(count == 0){
            System.out.println("\t\t\t\t     You have no appointment!");
        }else{
            //if got appointment
            System.out.printf("\t\t\t\t%s%d%s upcoming appointment\n\n",ANSI_BLUE,count,ANSI_RESET);

            do {
                System.out.print("\t\t\t\t Enter your choice to delete(Type XXX to quit):");
                choiceDelete = input.nextLine().trim();
                if(choiceDelete.equals("XXX")){
                    break;
                }
            } while (!validateChoice(choiceDelete, count));

            //if choice is equals to XXX then quit module
            if(choiceDelete.equalsIgnoreCase("XXX")) {
                System.out.println(ANSI_RED+"Successfully quited delete module"+ANSI_RESET);
            }
            else{
                //if not then proceed to delete
                choice = Integer.parseInt(choiceDelete.trim());
                for(int i = 0 ; i < appointmentArrayList.size();i++){
                    //if the appointment is the same with the choice then delete
                    if(localAppointment.get(choice-1).getAppointmentID().equals(appointmentArrayList.get(i).getAppointmentID())){
                        System.out.println(ANSI_RED+"               "+appointmentArrayList.get(i).getAppointmentID()+ANSI_RESET+" appointment is successfully cancelled");
                        appointmentArrayList.remove(i);

                    }
                }
            }

        }



    }

    public static void deleteAppointmentAdmin(ArrayList<Appointment> appointmentArrayList){
        int count =0;

        ArrayList<Appointment> localAppointment = new ArrayList<Appointment>();
        Scanner input = new Scanner(System.in);
        String choiceDelete;
        int choice;


        Main.functionHeader(22);
        System.out.println("\t\t\t  ---------------------------------------------------------------------------------------------");
        System.out.printf("\t\t\t  | AppointmentID |AppointmentDate |AppointmentTime   |Service         | Customer             |\n");
        System.out.println("\t\t\t  ---------------------------------------------------------------------------------------------");

        //display all upcoming appointment for admin
        for(int i = 0 ; i < appointmentArrayList.size(); i++){
            //validate the future appointment
            if(appointmentArrayList.get(i).getAppointmentTime().isAfter(LocalDateTime.now())) {
                System.out.printf("\t\t\t%d.| %-13s |%-15s |%3s.00            |%-15s | %-20s |\n", count + 1, appointmentArrayList.get(i).getAppointmentID(), appointmentArrayList.get(i).getDate(), appointmentArrayList.get(i).getHour(), appointmentArrayList.get(i).getService().getServiceType(),appointmentArrayList.get(i).getCar().getCustomer().getName().toString());
                count++;
                localAppointment.add(appointmentArrayList.get(i));
            }
        }
        System.out.println("\t\t\t  ---------------------------------------------------------------------------------------------");
        System.out.printf("\t\t\t        Total upcoming Appointment %s%d%s\n",ANSI_RED,count,ANSI_RESET);

        do {
            System.out.print("\t\t\t        Enter your choice to delete(Type XXX to quit):");
            choiceDelete = input.nextLine().trim();
            if(choiceDelete.equals("XXX")){
                break;
            }
        } while (!validateChoice(choiceDelete, count));

        if(choiceDelete.equalsIgnoreCase("XXX")){
            System.out.println("\t\t\t      Successfully quit appointment cancelled module");
        }else{
            choice = Integer.parseInt(choiceDelete.trim());
            for(int i = 0 ; i < appointmentArrayList.size();i++){
                //if the appointment is the same with the choice then delete
                if(localAppointment.get(choice-1).getAppointmentID().equals(appointmentArrayList.get(i).getAppointmentID())){
                    System.out.println(ANSI_RED+"               "+appointmentArrayList.get(i).getAppointmentID()+ANSI_RESET+" appointment is successfully cancelled");
                    appointmentArrayList.remove(i);

                }
            }
        }



    }

    public static void editAppointmentMenu(ArrayList<Appointment> appointmentArrayList,UserInfo userInfo ,ArrayList<Service> serviceArrayList,ArrayList<UserInfo> userInfoArrayList){
        Scanner input = new Scanner(System.in);
        if(userInfo instanceof Customer) {
            Customer customer = (Customer)userInfo;
            Main.functionHeader(23);
            System.out.println("\t\t\t\t\t---------------Edit Menu------------");
            System.out.println("\t\t\t\t\t|   [1].Edit appointment Time      |");
            System.out.println("\t\t\t\t\t|   [2].Edit appointment Service   |");
            System.out.println("\t\t\t\t\t------------------------------------");

            String userChoice;
            int userChoiceInt;
            do {
                System.out.print("\t\t\t\t\t     Enter your choice:");
                userChoice = input.nextLine().trim();
            } while (!validateChoice(userChoice, 2));

            userChoiceInt = Integer.parseInt(userChoice.trim());

            if(userChoiceInt==1){
                editAppointmentTime(appointmentArrayList,customer);
            }
            else if(userChoiceInt ==2){
                editAppointmentService(appointmentArrayList,customer,serviceArrayList);
            }

        }
        else{
            //if its admin
            Customer customerChoice;
            customerChoice=editAppointmentAdmin(userInfoArrayList);

            if(customerChoice==null){
                //if return null so just quit
                System.out.println("\n\t\t\t\t\tQuit edit appointment\n");
            }
            else{
                Main.functionHeader(23);
                System.out.println("\t\t\t\t\t---------------Edit Menu------------");
                System.out.println("\t\t\t\t\t|   [1].Edit appointment Time      |");
                System.out.println("\t\t\t\t\t|   [2].Edit appointment Service   |");
                System.out.println("\t\t\t\t\t------------------------------------");

                String userChoice;
                int userChoiceInt;
                do {
                    System.out.print("\t\t\t\t\t     Enter your choice:");
                    userChoice = input.nextLine().trim();
                } while (!validateChoice(userChoice, 2));

                userChoiceInt = Integer.parseInt(userChoice.trim());

                if(userChoiceInt==1){
                    editAppointmentTime(appointmentArrayList,customerChoice);
                }
                else if(userChoiceInt ==2){
                    editAppointmentService(appointmentArrayList,customerChoice,serviceArrayList);
                }
            }
        }
    }

    public static Customer editAppointmentAdmin(ArrayList<UserInfo> userInfoArrayList){
        Scanner input = new Scanner(System.in);
        ArrayList<Customer> customerArrayList= new ArrayList<>();
        int count = 0;

        System.out.println("\t\t\t  ======================================================");
        System.out.println("\t\t\t  |CustomerID    CustomerName     CustomerPhoneNumber  |");
        System.out.println("\t\t\t  ======================================================");
        for(int i = 0 ; i < userInfoArrayList.size();i++){
            if(userInfoArrayList.get(i) instanceof Customer) {
                System.out.printf("\t\t\t%d | %-12s %-20s %-17s|\n",count+1,userInfoArrayList.get(i).getUserID(),userInfoArrayList.get(i).getName(),userInfoArrayList.get(i).getPhoneNumber());
                customerArrayList.add((Customer)userInfoArrayList.get(i));
                count++;
            }
        }
        System.out.println("\t\t\t  =====================================================");

        String choiceString;
        int choice;

        do {
            System.out.print("\t\t\t    Enter your choice (Type XXX to quit):");
            choiceString = input.nextLine().trim();
            if (choiceString.equalsIgnoreCase("XXX")) {
                break;
            }
        } while (!validateChoice(choiceString, customerArrayList.size()));

        if(!choiceString.equalsIgnoreCase("XXX")){
            choice = Integer.parseInt(choiceString.trim());
            return customerArrayList.get(choice-1);
        }
        else{
            return null;
        }
    }

    public static void editAppointmentTime(ArrayList<Appointment> appointmentArrayList,Customer customer){
        Scanner input = new Scanner(System.in);
        ArrayList<Appointment> localAppointment = new ArrayList<Appointment>();

        int count = 0 ; // count how many appointment is available to edit
        Main.functionHeader(23);

        //print all the appointment under customer
        System.out.println("\t\t\t  ---------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t  |AppointmentID   |AppointmentServiceType  |AppointmentDate    |AppointmentTime    |Car      |");
        System.out.println("\t\t\t  ---------------------------------------------------------------------------------------------");
        for(int i = 0 ; i < appointmentArrayList.size(); i++){
            //validate appointment under the specific customer
            if(customer.getUserID().equals(appointmentArrayList.get(i).getCar().getCustomer().getUserID())){
                //validate the time and it to before 3 days (include today)
                if(LocalDateTime.now().isBefore(appointmentArrayList.get(i).getAppointmentTime().minusDays(2))){
                    count++;
                    System.out.printf("\t\t\t%d.|%-15s |%-23s |%-18s |%-18d |%-9s|\n",count,appointmentArrayList.get(i).getAppointmentID(), appointmentArrayList.get(i).getService().getServiceType(),appointmentArrayList.get(i).getDate(),appointmentArrayList.get(i).getHour(),appointmentArrayList.get(i).getCar().getCarNumPlate());
                    localAppointment.add(appointmentArrayList.get(i));
                }
            }
        }
        System.out.println("\t\t\t  ---------------------------------------------------------------------------------------------");
        System.out.printf("\t\t\t             Total %d available appointment to change\n\n",count);

        if(count == 0){
            System.out.println(ANSI_RED+"You have no appointment that can edit!"+ANSI_RESET);
        }
        else {
            String choiceEdit;  //keep string choice to validate
            int choice;         //keep choice
            //get user choice
            do {
                System.out.print("\t\t\t    Enter your choice to Edit(Type XXX to quit):");
                choiceEdit = input.nextLine().trim();
                if (choiceEdit.equalsIgnoreCase("XXX")) {
                    break;
                }
            } while (!validateChoice(choiceEdit, count));

            //If user want to leave
            if (choiceEdit.equalsIgnoreCase("XXX")){
                System.out.println(ANSI_RED +"Successfully Quited edit "+ANSI_RESET);
            }
            else {
                //if user want to edit
                choice = Integer.parseInt(choiceEdit.trim());
                LocalDateTime chosenEditTime;       //chosen time keep under here
                String inputDate;                   //keep user enter Date
                String choiceTime;
                int choiceTimeInt;


                for(int i = 0 ; i<appointmentArrayList.size();i++){
                    if(localAppointment.get(choice-1).getAppointmentID().equalsIgnoreCase(appointmentArrayList.get(i).getAppointmentID())){
                        do {
                            do {
                                System.out.print("\n\t\t\t    Enter New Appointment Date in (dd/mm/yyyy)(03/03/2021):");
                                inputDate = input.next().trim();
                            } while (!isValidDate(inputDate,'R'));

                            //convert date time into
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate convertDate = LocalDate.parse(inputDate, formatter);
                            chosenEditTime = convertDate.atStartOfDay();

                            System.out.println("\n\t\t\t||============================================||");
                            System.out.println("\t\t\t||            Enter your Time                 ||");
                            System.out.println("\t\t\t||1. 8.00AM-10.00AM  ||" + "4. 2.00PM-4.00PM       ||");
                            System.out.println("\t\t\t||2. 10.00AM-12.00AM ||" + "5. 4.00PM-6.00PM       ||");
                            System.out.println("\t\t\t||3. 12.00PM-2.00PM  ||" + "6. 6.00PM-8.00PM       ||");
                            System.out.println("\t\t\t==============================================||");

                            input.nextLine(); // clear buffer

                            do {
                                System.out.print("\t\t\t           Your Choice:");
                                choiceTime = input.nextLine().trim();
                            } while (!validateChoice(choiceTime, 6));

                            choiceTimeInt = Integer.parseInt(choiceTime.trim());//parse into Int


                            //put hours inside chosenTime(localDateTime)
                            if (choiceTimeInt == 1) {
                                chosenEditTime = chosenEditTime.with(LocalTime.of(8, 0));
                            } else if (choiceTimeInt == 2) {
                                chosenEditTime = chosenEditTime.with(LocalTime.of(10, 0));
                            } else if (choiceTimeInt == 3) {
                                chosenEditTime = chosenEditTime.with(LocalTime.of(12, 0));
                            } else if (choiceTimeInt == 4) {
                                chosenEditTime = chosenEditTime.with(LocalTime.of(14, 0));
                            } else if (choiceTimeInt == 5) {
                                chosenEditTime = chosenEditTime.with(LocalTime.of(16, 0));
                            } else {
                                chosenEditTime = chosenEditTime.with(LocalTime.of(18, 0));
                            }
                        }while(!appointmentTimeValidate(appointmentArrayList,chosenEditTime));

                        System.out.printf("\n\t\t\t   Confirm to change to Date:%s Time:%d (Enter Y=yes):",inputDate,chosenEditTime.getHour());
                        String confirm = input.nextLine();

                        if(confirm.equalsIgnoreCase("Y")){
                            appointmentArrayList.get(i).setAppointmentTime(chosenEditTime);
                            System.out.println("\t\t\tAppointment has been changed to:");
                            System.out.println("\t\t\t-------------------------------");
                            System.out.printf("\t\t\t|AppointmentID  :%-13s|" +
                                    "\n\t\t\t|AppointmentDate:%-13s|\n" +
                                    "\t\t\t|AppointmentTime:%-13s|",appointmentArrayList.get(i).getAppointmentID(),appointmentArrayList.get(i).getDate(), String.format("%02d:00" , appointmentArrayList.get(i).getHour()));
                            System.out.println("\n\t\t\t-------------------------------");
                        }

                        else{
                            System.out.println("\t\t\t    Appointment has not been changed !");
                        }


                    }
                }
            }
        }


    }

    //edit Service
    public static void editAppointmentService(ArrayList<Appointment> appointmentArrayList,Customer customer,ArrayList<Service> serviceArrayList){
        Scanner input = new Scanner(System.in);
        ArrayList<Appointment> localAppointment = new ArrayList<Appointment>();


        int count = 0 ; // count how many appointment is available to edit Service
        Main.functionHeader(23);

        //print all the appointment under customer
        System.out.println("\t\t\t  ---------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t  |AppointmentID   |AppointmentServiceType  |AppointmentDate    |AppointmentTime    |Car      |");
        System.out.println("\t\t\t  ---------------------------------------------------------------------------------------------");
        for(int i = 0 ; i < appointmentArrayList.size(); i++){
            //validate appointment under the specific customer
            if(customer.getUserID().equals(appointmentArrayList.get(i).getCar().getCustomer().getUserID())){
                //validate the time and it to before 3 days (include today)
                if(LocalDateTime.now().isBefore(appointmentArrayList.get(i).getAppointmentTime().minusDays(2))){
                    count++;
                    System.out.printf("\t\t\t%d.|%-15s |%-23s |%-18s |%-18d |%-9s|\n",count,appointmentArrayList.get(i).getAppointmentID(), appointmentArrayList.get(i).getService().getServiceType(),appointmentArrayList.get(i).getDate(),appointmentArrayList.get(i).getHour(),appointmentArrayList.get(i).getCar().getCarNumPlate());
                    localAppointment.add(appointmentArrayList.get(i));
                }
            }
        }
        System.out.println("\t\t\t  ---------------------------------------------------------------------------------------------");
        System.out.printf("  \t\t         Total %d available appointment to change\n\n",count);

        if(count == 0 ){
            System.out.println("  \t\t          There is no appointment to edit");
        }
        else{
            int choice;
            String choiceEdit;
            do {
                System.out.print("  \t\t    Enter your choice to Edit(Type XXX to quit):");
                choiceEdit = input.nextLine().trim();
                if (choiceEdit.equalsIgnoreCase("XXX")) {
                    break;
                }
            } while (!validateChoice(choiceEdit, count));

            if(choiceEdit.equalsIgnoreCase("XXX")){
                System.out.println("  \t\t"+ANSI_RED+"Successfully quited edit appointment service"+ANSI_RESET);
            }
            else {
                choice = Integer.parseInt(choiceEdit.trim());

                for (int i = 0; i < appointmentArrayList.size(); i++) {
                    if (localAppointment.get(choice-1).getAppointmentID().equals(appointmentArrayList.get(i).getAppointmentID())){
                        String serviceID;
                        int serviceIDInt;
                        //display all the service
                        MainService_Car.servDisplay(serviceArrayList);
                        do {
                            System.out.print("  \t\tChoose your service number(etc:1) :");
                            serviceID = input.nextLine();
                        }while(!validateChoice(serviceID,serviceArrayList.size()));

                        serviceIDInt = Integer.parseInt(serviceID.trim());

                        //ask whether to confirm edit
                        System.out.print("  \t\tConfirm to edit your service? (Y=yes):");
                        String confirm = input.nextLine();

                        //confirm to edit
                        if(confirm.equalsIgnoreCase("y")){
                            appointmentArrayList.get(i).setService(serviceArrayList.get(serviceIDInt-1));
                            System.out.println("  \t\t--------------------------------");
                            System.out.printf("  \t\t|Updated AppointmentID:%s\n\t\t|AppointmentServiceType:%s",appointmentArrayList.get(i).getAppointmentID(),appointmentArrayList.get(i).getService().getServiceType());
                            System.out.println("\n  \t\t--------------------------------");
                        }
                        else{
                            System.out.println("  \t\t     Appointment does not updated");
                        }

                    }
                }
            }


        }

    }

    public static void displayAppointmentMenu(ArrayList<Appointment> appointmentArrayList, UserInfo userInfo,ArrayList<Service> serviceArrayList,ArrayList<Customer> customerArrayList){
        if(userInfo instanceof Customer) {
            //display specific customer user info
            Customer customer = (Customer)userInfo;
            displaySpecificCustomerAppointment(appointmentArrayList,customer);
        }
        else if(userInfo instanceof Staff){
            Scanner input = new Scanner(System.in);
            Staff staff = (Staff) userInfo;


            String userChoice;
            int userChoiceInt;
            char userContinue;

            do {
                userContinue='N';
                userChoiceInt = 0 ;
                System.out.println("\t\t\t\t-------------[Display Specific Appointment Records Menu]-------------");
                System.out.println("\t\t\t\t|       [1].Specific customer appointment records                   |");
                System.out.println("\t\t\t\t|       [2].Specific car appointment records                        |");
                System.out.println("\t\t\t\t|       [3].Specific service appointment records                    |");
                System.out.println("\t\t\t\t|       [4].All appointment records                                 |");
                System.out.println("\t\t\t\t|       [5].Specific Appointment with appointmentID                 |");
                System.out.println("\t\t\t\t|       [6].Specific Appointment between date                       |");
                System.out.println("\t\t\t\t|       [7].exit function                                           |");
                System.out.println("\t\t\t\t---------------------------------------------------------------------");
                do {
                    System.out.print("\t\t\t\t              Enter your choice:");
                    userChoice = input.nextLine().trim();
                } while (!validateChoice(userChoice, 7));

                userChoiceInt = Integer.parseInt(userChoice.trim());

                switch (userChoiceInt) {
                    case 1:
                        displaySpecificCustomerAppointmentAdmin(appointmentArrayList, customerArrayList);
                        break;
                    case 2:
                        displaySpecificCarAppointment(appointmentArrayList);
                        break;
                    case 3:
                        displaySpecificServiceRecords(appointmentArrayList, serviceArrayList);
                        break;
                    case 4:
                        displayAllAppointment(appointmentArrayList);
                        ;
                        break;
                    case 5:
                        displaySpecificAppointment(appointmentArrayList);
                        ;
                        break;
                    case 6:
                        displayAppointmentBetweenDate(appointmentArrayList);
                        break;
                    case 7:

                        break;
                    default:
                        System.out.println("\t\t\t\t        Invalid choice!");
                        break; //wont hit this default
                }

                if(userChoiceInt!=7) {
                    do {
                        System.out.print("\n\t\t\t\t  Do you want to continue searching? (Y=yes/n=No):");
                        userContinue = input.next().toUpperCase().charAt(0);
                        input.nextLine();
                        if (userContinue != 'N' && userContinue != 'Y') {
                            System.out.println(ANSI_RED + "Invalid input N or Y only!" + ANSI_RESET);
                        }
                    } while (userContinue != 'N' && userContinue != 'Y');
                }

            }while(userChoiceInt!=7 && userContinue=='Y');

            System.out.println(ANSI_RED+"---------------Quited display Appointment menu---------------"+ANSI_RESET);

        }


    }

    public static void displaySpecificCustomerAppointment(ArrayList<Appointment> appointmentArrayList , Customer customer){
        ArrayList<Appointment> localAppointment = new ArrayList<Appointment>();


        for (int i = 0; i < appointmentArrayList.size(); i++) {
            if (appointmentArrayList.get(i).getCar().getCustomer().getUserID().equals(customer.getUserID())) {
                localAppointment.add(appointmentArrayList.get(i));
            }

        }

        //sort by
        bubbleSortTime(localAppointment, localAppointment.size(), 1);

        System.out.println("\t\t\t\t---------------------------------------------------------------------------");
        System.out.printf("\t\t\t\t|                 Appointment History of %s%-33s%s|                      \n", ANSI_BLUE,customer.getName().toString(),ANSI_RESET);
        System.out.println("\t\t\t\t|AppointmentID   AppointmentService   CarDate  \t Time \t\t Hour\tStatus|");
        for (int i = 0; i < localAppointment.size(); i++) {
            //check whether got pay or not
            if (localAppointment.get(i).getPayment() == null) {
                System.out.printf("\t\t\t\t|%-16s %-19s %-10s %-12s %-2d     %sX%s    |\n", localAppointment.get(i).getAppointmentID(), localAppointment.get(i).getService().getServiceType(), localAppointment.get(i).getCar().getCarNumPlate(), localAppointment.get(i).getDate(), localAppointment.get(i).getHour(), ANSI_RED, ANSI_RESET);
            } else {
                System.out.printf("\t\t\t\t|%-16s %-19s %-10s %-12s %-2d   \t%sPAID%-6s|\n", localAppointment.get(i).getAppointmentID(), localAppointment.get(i).getService().getServiceType(), localAppointment.get(i).getCar().getCarNumPlate(), localAppointment.get(i).getDate(), localAppointment.get(i).getHour(), ANSI_GREEN, ANSI_RESET);
            }
        }
        System.out.println("\t\t\t\t---------------------------------------------------------------------------");
        System.out.printf("\t\t\t\tTotal %s%d%s records from %s%s%s\n",ANSI_BLUE,localAppointment.size(),ANSI_RESET,ANSI_BLUE,customer.getName().toString(),ANSI_RESET);
    }

    public static void displaySpecificCustomerAppointmentAdmin(ArrayList<Appointment> appointmentArrayList,ArrayList<Customer> customerArrayList){
        Customer localCustomer = new Customer();
        Scanner input = new Scanner(System.in);

        int found= 0;

        System.out.print("\t\t\t   Enter Specific User ID :");
        String userID = input.nextLine();

        //check whether got the userID
        for(int i = 0 ; i <customerArrayList.size();i++){
            if(customerArrayList.get(i).getUserID().equalsIgnoreCase(userID)){
                localCustomer = customerArrayList.get(i);
                found++;
            }
        }

        if(found!=0) {
            //display all
            displaySpecificCustomerAppointment(appointmentArrayList, localCustomer);
        }
        else{
            System.out.printf("\t\t\t\t    UserID %s%s%s is not found!",ANSI_BLUE,userID,ANSI_RESET);
            System.out.println(ANSI_RED+"Successfully quited search(Customer)"+ANSI_RESET);
        }
    }

    public static void displaySpecificCarAppointment(ArrayList<Appointment> appointmentArrayList){
        //display specific car appointment by car plate
        Scanner input = new Scanner(System.in);
        String carPlate;
        //input into local so can sort for the dates
        ArrayList<Appointment> localAppointment = new ArrayList<>();

        //ask for car plate
        do {
            System.out.print("\t\t\t\t     Please Enter Car Plate :");
            carPlate = input.nextLine();
            if(!carPlateValidation(carPlate)){
                System.out.println(ANSI_RED +"              Invalid CarPlate (Must have at least 1 number and 1 character)"+ANSI_RESET);
            }
        }while(!carPlateValidation(carPlate));

        //validate whether the car plate have register for appointment before
        for(int i = 0 ; i < appointmentArrayList.size();i++){
            if(appointmentArrayList.get(i).getCar().getCarNumPlate().equalsIgnoreCase(carPlate)){
                localAppointment.add(appointmentArrayList.get(i));
            }
        }

        //if found appointment with this car plate
        if(localAppointment.size()!=0) {
            //header
            System.out.println("\n\t\t\t\t---------------------------------------------------------------------------");
            System.out.printf("\t\t\t\t|                  |Appointment History of %s%s%s|                       |\n", ANSI_BLUE,carPlate,ANSI_RESET);
            System.out.println("\t\t\t\t|AppointmentID   AppointmentService   CarDate  \tTime \t\t Hour\tStatus|");
            //sort it with bubble sort
            bubbleSortTime(localAppointment, localAppointment.size(), 0);
            for(int i = 0 ; i <localAppointment.size();i++){
                if (localAppointment.get(i).getPayment() == null) {
                    System.out.printf("\t\t\t\t|%-16s %-19s %-10s %-12s %-2d     %sX%s    |\n", localAppointment.get(i).getAppointmentID(), localAppointment.get(i).getService().getServiceType(), localAppointment.get(i).getCar().getCarNumPlate(), localAppointment.get(i).getDate(), localAppointment.get(i).getHour(), ANSI_RED, ANSI_RESET);
                } else {
                    System.out.printf("\t\t\t\t|%-16s %-19s %-10s %-12s %-2d   \t%sPAID%-6s|\n", localAppointment.get(i).getAppointmentID(), localAppointment.get(i).getService().getServiceType(), localAppointment.get(i).getCar().getCarNumPlate(), localAppointment.get(i).getDate(), localAppointment.get(i).getHour(), ANSI_GREEN, ANSI_RESET);
                }
            }
            System.out.println("\t\t\t\t---------------------------------------------------------------------------");
            System.out.printf("\t\t\t\tTotal %s%d%s records from car %s%s%s",ANSI_BLUE,localAppointment.size(),ANSI_RESET,ANSI_BLUE,carPlate,ANSI_RESET);
        }
        else{
            System.out.printf("\t\t\t\tThere was no appointment record found for %s%s%s\n",ANSI_BLUE,carPlate,ANSI_RESET);
            System.out.println(ANSI_RED+"               Successfully quited search (car)"+ANSI_RESET);
        }





    }

    public static void displaySpecificServiceRecords(ArrayList<Appointment> appointmentArrayList,ArrayList<Service> serviceArrayList){
        Scanner input = new Scanner(System.in);
        MainService_Car.servDisplay(serviceArrayList);

        //enter choice from the service
        String choice;
        int choiceInt;
        do {
            System.out.print("  \t\tEnter your choice for Service(Type XXX to quit):");
            choice = input.nextLine().trim();
            if (choice.equalsIgnoreCase("XXX")) {
                break;
            }
        } while (!validateChoice(choice, serviceArrayList.size()));



        //if choice XXX quit function or proceed to validate the choice
        if(choice.equalsIgnoreCase("XXX")){
            System.out.println("  \t\t"+ANSI_RED+"Successfully quited search (service)"+ANSI_RESET);
        }
        else {
            choiceInt = Integer.parseInt(choice.trim());
            //if it was found
            int found = 0;

            //create local appointment to keep
            ArrayList<Appointment> localAppointment = new ArrayList<>();

            for (int i = 0; i < appointmentArrayList.size(); i++) {
                if (serviceArrayList.get(choiceInt - 1).getServiceType().equalsIgnoreCase(appointmentArrayList.get(i).getService().getServiceType())) {
                    localAppointment.add(appointmentArrayList.get(i));
                    found++;
                }
            }

            if (found != 0) {
                //sort it and print out the appointment record
                bubbleSortTime(localAppointment, localAppointment.size(), 0);

                System.out.println("\n\t\t\t---------------------------------------------------------------------------");
                System.out.printf("\t\t\t|                  Appointment History of %s%-30s%s  |\n", ANSI_BLUE, serviceArrayList.get(choiceInt - 1).getServiceType(), ANSI_RESET);
                System.out.println("\t\t\t|AppointmentID   AppointmentService   CarDate  \tTime \t\t Hour\tStatus|");
                for (int i = 0; i < localAppointment.size(); i++) {
                    if (localAppointment.get(i).getPayment() == null) {
                        System.out.printf("\t\t\t|%-16s %-19s %-10s %-12s %-2d     %sX%s    |\n", localAppointment.get(i).getAppointmentID(), localAppointment.get(i).getService().getServiceType(), localAppointment.get(i).getCar().getCarNumPlate(), localAppointment.get(i).getDate(), localAppointment.get(i).getHour(), ANSI_RED, ANSI_RESET);
                    } else {
                        System.out.printf("\t\t\t|%-16s %-19s %-10s %-12s %-2d   \t%sPAID%-6s|\n", localAppointment.get(i).getAppointmentID(), localAppointment.get(i).getService().getServiceType(), localAppointment.get(i).getCar().getCarNumPlate(), localAppointment.get(i).getDate(), localAppointment.get(i).getHour(), ANSI_GREEN, ANSI_RESET);
                    }
                }
                System.out.println("\t\t\t---------------------------------------------------------------------------");
                System.out.printf("\t\t\tTotal %s%d%s records with %s%s%s service", ANSI_BLUE, localAppointment.size(), ANSI_RESET, ANSI_BLUE, serviceArrayList.get(choiceInt - 1).getServiceType(), ANSI_RESET);
            }
            else {
                System.out.printf("\t\t\tNo record found for this %s%s%s service",ANSI_BLUE,serviceArrayList.get(choiceInt-1).getServiceType(),ANSI_RESET);
            }
        }

    }

    public static void displayAllAppointment(ArrayList<Appointment> appointmentArrayList){
        Scanner input = new Scanner(System.in);
        String userChoice;

        //accept user input for sorting
        System.out.println("\t\t\t==========");
        System.out.println("\t\t\t||Sorting Menu:\n \t\t\t|| [1].Sort pass date to later date||\n \t\t\t|| [2].Sort later date to pass date||");
        System.out.println("\t\t\t                          ===========");
        do {
            System.out.print("\t\t\t                Enter your choice:");
            userChoice = input.nextLine().trim();
        } while (!validateChoice(userChoice, 2));

        System.out.println("\n\t\t------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t\t|                                        |All Appointment History|                                         |\n");
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.println("\t\t|AppointmentID   AppointmentTime     DepositID   ServiceType          CarPlate   FullPayment     UserID    |");

        //choice - 1
        bubbleSortTime(appointmentArrayList,appointmentArrayList.size(),Integer.parseInt(userChoice)-1);

        for(int i = 0 ;i<appointmentArrayList.size();i++){
            System.out.println("\t\t"+appointmentArrayList.get(i).toString());
        }
        System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
        System.out.printf("\t\tTotal %s%d%s reservation found.",ANSI_BLUE,appointmentArrayList.size(),ANSI_RESET);
    }

    public static void displaySpecificAppointment(ArrayList<Appointment> appointmentArrayList){
        Scanner input = new Scanner(System.in);
        int found=0;

        Main.functionHeader(24);
        System.out.print("\t\t                    Enter Appointment ID :");
        String reservationID = input.nextLine();

        for(int i = 0 ; i < appointmentArrayList.size();i++){
            if(appointmentArrayList.get(i).getAppointmentID().equals(reservationID)){
                found=1;
                System.out.println("\n\t\t\t\t===============================================");
                System.out.printf("\t\t\t\t|     Appointment %s%s%s Info:           |\n",ANSI_BLUE,reservationID,ANSI_RESET);
                System.out.println("\t\t\t\t===============================================");
                System.out.printf("\t\t\t\t|AppointmentID           :%-20s|\n",reservationID);
                System.out.printf("\t\t\t\t|AppointmentTime         :%-10s %-8s |\n" , appointmentArrayList.get(i).getDate(),String.format("%02d:00" , appointmentArrayList.get(i).getHour()));
                System.out.printf("\t\t\t\t|AppointmentDescription  :%-20s\n",appointmentArrayList.get(i).getAppointmentDesc());
                System.out.println("\t\t\t\t=============="+ANSI_GREEN+"Service Chosen"+ANSI_RESET+"===================");
                System.out.printf("\t\t\t\t|ServiceType             :%-20s|\n",appointmentArrayList.get(i).getService().getServiceType());
                System.out.printf("\t\t\t\t|ServicePrice            :RM%-18.2f|\n",appointmentArrayList.get(i).getService().getServicePrice());
                System.out.println("\t\t\t\t=============="+ANSI_GREEN+ "Car Details"+ANSI_RESET+ "======================");
                System.out.printf("\t\t\t\t|Car                     :%-20s|\n",appointmentArrayList.get(i).getCar().getCarNumPlate());
                System.out.printf("\t\t\t\t|CarOwnerHp              :%-20s|\n",appointmentArrayList.get(i).getCar().getCarOwnerHP());
                System.out.println("\t\t\t\t=============="+ANSI_GREEN+ "Customer Details"+ANSI_RESET+ "=================");
                System.out.printf("\t\t\t\t|CustomerID              :%-20s|\n",appointmentArrayList.get(i).getCar().getCustomer().getUserID());
                System.out.printf("\t\t\t\t|CustomerName            :%-20s|\n" , appointmentArrayList.get(i).getCar().getCustomer().getName());
                System.out.printf("\t\t\t\t|CustomerHp              :%-20s|\n" ,appointmentArrayList.get(i).getCar().getCustomer().getPhoneNumber());
                System.out.println("\t\t\t\t=============="+ANSI_GREEN+ "Deposit Details"+ANSI_RESET+ "==================");
                System.out.printf("\t\t\t\t|DepositID               :%-20s|\n",appointmentArrayList.get(i).getDeposit().getPaymentId());
                System.out.printf("\t\t\t\t|DepositTime             :%-20s|\n",appointmentArrayList.get(i).getDeposit().getDate());
                if(appointmentArrayList.get(i).getPayment()==null){
                    System.out.println("\t\t\t\t==============="+ANSI_GREEN+ "Payment Details"+ANSI_RESET+ "================");
                    System.out.println(ANSI_RED+"               *Havent pay for full price"+ANSI_RESET);
                }
                else{
                    System.out.println("\t\t\t\t=============="+ANSI_GREEN+ "Payment Details"+ANSI_RESET+ "==================");
                    System.out.printf("\t\t\t\t|PaymentID               :%-20s|\n",appointmentArrayList.get(i).getPayment().getPaymentId());
                    System.out.printf("\t\t\t\t|PaymentTime             :%-20s|\n",appointmentArrayList.get(i).getPayment().getDate());
                    System.out.println("\t\t\t\t==============================================");
                }

            }
        }

        if(found == 0){
            System.out.printf("\t\t\t\t          %sAppointment %s not found!%s",ANSI_RED,reservationID,ANSI_RESET);
        }
    }

    public static void displayAppointmentBetweenDate(ArrayList<Appointment> appointmentArrayList){
        Scanner input = new Scanner(System.in);
        String inputStartDate,inputEndDate="";
        LocalDateTime startDate=LocalDateTime.now();
        LocalDateTime endDate=LocalDateTime.now();
        String userChoice; // user choice for sort date

        Main.functionHeader(24);
        System.out.println("\t\t\t==========");
        System.out.println("\t\t\t||Sorting Menu:\n \t\t\t|| [1].Sort pass date to later date||\n \t\t\t|| [2].Sort later date to pass date||");
        System.out.println("\t\t\t                          ===========");
        do {
            System.out.print("\t\t\t\t           Enter your choice:");
            userChoice = input.nextLine().trim();
        } while (!validateChoice(userChoice, 2));

        do {
            do {
                System.out.print("\t\t\t\tEnter Start Date(dd/mm/yyyy)(Enter XXX to quit): ");
                inputStartDate = input.next().trim().toUpperCase();
                if(inputStartDate.equalsIgnoreCase("XXX")){
                    break;
                }
            } while (!isValidDate(inputStartDate, 'F'));

            //if its xxx then quit
            if(inputStartDate.equalsIgnoreCase("XXX")){
                break;
            }
            do {
                System.out.print("\t\t\t\tEnter End Date(dd/mm/yyyy)(03/03/2021): ");
                inputEndDate = input.next().trim();
            } while (!isValidDate(inputEndDate, 'F'));

            //pass inputDate into LocalDateTime and update chosen Time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate convertDate = LocalDate.parse(inputStartDate, formatter);
            startDate = convertDate.atStartOfDay();

            convertDate = LocalDate.parse(inputEndDate, formatter);
            endDate = convertDate.atStartOfDay();

            if(startDate.isAfter(endDate)){
                System.out.println("\n  \t\tStart date cannot be after end date\n");
            }

        }while(startDate.isAfter(endDate));



        int found=0;
        if(!inputStartDate.equals("XXX")){
            System.out.println("\n\t\t------------------------------------------------------------------------------------------------------------");
            System.out.printf("\t\t|                                        |All Appointment History|                                         |\n");
            System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
            System.out.println("\t\t|AppointmentID   AppointmentTime     DepositID   ServiceType          CarPlate   FullPayment     UserID    |");

            bubbleSortTime(appointmentArrayList,appointmentArrayList.size(),Integer.parseInt(userChoice)-1);

            for(int i = 0 ; i < appointmentArrayList.size();i++){
                if((startDate.toLocalDate()).equals(appointmentArrayList.get(i).getAppointmentTime().toLocalDate()) || (endDate.toLocalDate()).equals(appointmentArrayList.get(i).getAppointmentTime().toLocalDate()) ||
                        appointmentArrayList.get(i).getAppointmentTime().isAfter(startDate) && appointmentArrayList.get(i).getAppointmentTime().isBefore(endDate)){
                    System.out.println("\t\t"+appointmentArrayList.get(i).toString());
                    found++;
                }
            }
            System.out.println("\t\t------------------------------------------------------------------------------------------------------------");
            if(found==0){
                System.out.printf("\t\tThere is no records between startDate %s endDate %s",inputStartDate,inputEndDate);
            }
            else if(found>0){
                System.out.printf("\t\tTotal %s%d%s appointment found!",ANSI_BLUE,found,ANSI_RESET);
            }
        }
        else{
            System.out.println("\t\tQuitting searching Appointment between dates");
        }
    }


    public static boolean carPlateValidation(String carPlate){
        //validate car plate
        //Atleast need one alphabet and digit

        int alpha = 0 ,digit = 0 ;

        for(int i = 0 ; i < carPlate.length();i++){
            if(Character.isLetter(carPlate.charAt(i))){
                alpha++;
            }
            else if(Character.isDigit(carPlate.charAt(i))){
                digit++;
            }
            else{
                return false;
            }
        }

        if(digit==0 || alpha==0) {
            return false;
        }

        return true;
    }

    public static void bubbleSortTime(ArrayList<Appointment> arrayListAppointment, int size,int choice){
        //choice 1 is before to after
        //choice 0 is after to before
        for(int i = 0 ; i <size-1;i++){
            for(int j = 0 ; j<size-i-1;j++){
                if(choice ==0){
                    if(arrayListAppointment.get(j).getAppointmentTime().isAfter(arrayListAppointment.get(j+1).getAppointmentTime())) {
                        Collections.swap(arrayListAppointment, j, j + 1);
                    }
                }
                else{
                    if(arrayListAppointment.get(j).getAppointmentTime().isBefore(arrayListAppointment.get(j+1).getAppointmentTime())) {
                        Collections.swap(arrayListAppointment, j, j + 1);
                    }
                }
            }
        }


    }







}
