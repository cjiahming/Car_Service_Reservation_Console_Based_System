import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)  throws IOException, ParseException, InterruptedException{
        Scanner input = new Scanner(System.in);
        ArrayList<UserInfo> userInfoArrayList= new ArrayList<UserInfo>();
        ArrayList<Service> serviceArrayList = new ArrayList<Service>();
        ArrayList<Car> carsArrayList = new ArrayList<Car>();
        ArrayList<Appointment> appointmentArrayList = new ArrayList<Appointment>();
        ArrayList<String> carTypeArrayList = new ArrayList<String>();
        ArrayList<Staff> staffArrayList = new ArrayList<>();
        ArrayList<Customer> customersArrayList = new ArrayList<Customer>();

        initialize(appointmentArrayList,carsArrayList,userInfoArrayList,serviceArrayList,carTypeArrayList);
        //filter into staff and customer
        for(int i = 0 ; i < userInfoArrayList.size();i++){
            if(userInfoArrayList.get(i) instanceof Staff){
                staffArrayList.add((Staff)userInfoArrayList.get(i));
            }
            else if(userInfoArrayList.get(i) instanceof Customer ){
                customersArrayList.add((Customer)userInfoArrayList.get(i));
            }
        }

        //print front page
        homePage();
        //=====================================================================================================================================================================
        //loginUser is to keep user profile that login
        UserInfo loginUser;

        do {
            //UserMain.mainUser is for user (login/register/etc)
            loginUser = UserMain.mainUser(customersArrayList, staffArrayList);

            String choiceString;
            int choiceInt;
            if(loginUser!=null) {
                do {
                    Main.functionHeader(5);
                    System.out.printf("\n                              >> Welcome %-20s \n                        to our Car Coating System !   <<\n", loginUser.getName());
                    System.out.println("            =====================================================================");
                    System.out.println("                               [ 1 ]. Appointment Module                        ");
                    System.out.println("                               [ 2 ]. Service & Car Module                      ");
                    System.out.println("                               [ 3 ]. Payment Module                            ");
                    System.out.println("                               [ 4 ]. User Module                               ");
                    System.out.println("                               [ 5 ]. Logout                                    ");
                    System.out.println("            =====================================================================");
                    do {
                        System.out.print("                            Please enter your choice >>");
                        choiceString = input.nextLine().trim();
                    } while (!MainAppointment.validateChoice(choiceString, 5));

                    choiceInt = Integer.parseInt(choiceString.trim());

                    //switch do while
                    switch (choiceInt) {
                        case 1:
                            MainAppointment.mainAppointment(loginUser, serviceArrayList, customersArrayList, carsArrayList, appointmentArrayList, userInfoArrayList);
                            break;
                        case 2:
                            MainService_Car.mainService_Car(loginUser, serviceArrayList, carsArrayList, appointmentArrayList,carTypeArrayList);
                            break;
                        case 3:
                            MainPayment.mainPayment(loginUser, appointmentArrayList);
                            break;
                        case 4:
                            if (loginUser instanceof Customer) {
                                UserMain.loginCustomer((Customer) loginUser);
                            } else if (loginUser instanceof Staff) {
                                int numberOfUser = 0;
                                for (int i = 0; i < staffArrayList.size(); i++) {
                                    if (staffArrayList.get(i).getUserID().equals(loginUser.getUserID())) {
                                        numberOfUser = i;
                                    }
                                }
                                UserMain.loginStaff(staffArrayList, customersArrayList, numberOfUser);
                            }
                            break;
                        case 5:
                            System.out.println("\n                                 -[ You successfully logout ]-");
                            break;
                    }

                } while (choiceInt != 5);
            }
        }while(loginUser!=null);

    }
    //Staff password
    public static void initialize(ArrayList<Appointment> arrayListAppointment, ArrayList<Car> carsArrayList, ArrayList<UserInfo> userInfoArrayList, ArrayList<Service> serviceArrayList,ArrayList<String> carTypeArrayList){
        carTypeArrayList.add("SUV");
        carTypeArrayList.add("Sedan");
        carTypeArrayList.add("Coupe");
        carTypeArrayList.add("Convertible");
        carTypeArrayList.add("Truck");
        carTypeArrayList.add("Van");
        carTypeArrayList.add("Wagon");
        carTypeArrayList.add("Hybrid");

        serviceArrayList.add(new Service("PC", "Paint Coating", 500.00, "For Paint Coating"));
        serviceArrayList.add(new Service("GC", "Glass Coating", 500.00, "For Glass Coating"));
        serviceArrayList.add(new Service("LC", "Leather Coating", 700.00, "For Leather Coating"));
        serviceArrayList.add(new Service("FC", "Fabric Coating", 600.00, "For Fabric Coating"));
        serviceArrayList.add(new Service("WC", "Wheel Coating", 300.00, "For Wheel Coating"));
        serviceArrayList.add(new Service("HC", "Headlight Coating", 300.00, "For Headlight Coating"));
        serviceArrayList.add(new Service("TC", "Trim Coating", 200.00, "For Trim Coating"));
        serviceArrayList.add(new Service("NC", "Nano Titanium Coating", 800.00, "For Nano Titanium Coating"));

        //Customer
        userInfoArrayList.add(new Customer("C130721001",new Name("John","Cena"),"johnCena@gmail.com","0123456789","Abc@1234"));
        userInfoArrayList.add(new Customer("C130721002",new Name("Jiah","Ming"),"JIAHMING@gmail.com","0194467878","ABC@3345a"));
        userInfoArrayList.add(new Customer("C130721003",new Name("Jenny","Tan"),"JENNY@gmail.com","0198876565","Abc@334567"));
        userInfoArrayList.add(new Customer("C130721004",new Name("Xiang","Wen"),"XIANGWEN@gmail.com","0197878998","Abc@7878994"));
        //Customer with no cars
        userInfoArrayList.add(new Customer("C130721005",new Name("Gideon","Tan"),"GIDEONTAN@gmail.com","0194487557","Abc@7878994"));

        //Admin
        userInfoArrayList.add(new Staff("920112051230", "13, Jalan Satu, Taman Dua, 13600 Prai, Pulau Pinang", false, "What is your pet's name", "Chubby", "S0001", new Name("Alice", "Tan"), "aliceTan@gmail.com", "0193347878", "Alice200"));
        userInfoArrayList.add(new Staff("920112051210", "1, Jalan Dua, Taman Monkey, 17070 Prai, Pulau Pinang", false, "What is your pet's name", "Zuno", "S0002", new Name("Jessica", "Tan"), "jessicaTan@gmail.com", "0194588989", "jessiCa100"));
        userInfoArrayList.add(new Staff("920112051212", "15, Jalan Batu Besar , Taman Dua, 13377 Prai, Kuala Lumpur", true, "What is your pet's name", "Gigi", "S0003", new Name("Jason", "Tan"), "jasontan@gmail.com", "0123456123", "jaSon2509"));

        //Car array List
        //CAR John Cena
        carsArrayList.add(new Car("PPA3077","Lamboghini","SUV","Spiderman","0174478377",((Customer)userInfoArrayList.get(0))));
        carsArrayList.add(new Car("ZAZ8888","BMW i8","Coupe","Antman","0198863773",((Customer)userInfoArrayList.get(0))));
        carsArrayList.add(new Car("BBB4477","Ford Ranger","Truck","Angelina","0198845573",((Customer)userInfoArrayList.get(0))));
        //CAR Jenny
        carsArrayList.add(new Car("XDA5088","Audi A3","Sedan","Superman","0174478377",((Customer)userInfoArrayList.get(2))));
        carsArrayList.add(new Car("DDD3388","Mercedes-E350","Convertible","Huiwenn","0174478377",((Customer)userInfoArrayList.get(2))));
        //CAR Jiah Ming
        carsArrayList.add(new Car("AAA3488","Camaro ","Convertible","Viemin","0124448448",((Customer)userInfoArrayList.get(1))));

        //CAR Xiang Wen
        carsArrayList.add(new Car("ZZZ7777","Lamboghini","SUV","CicakMen","0194456678",((Customer)userInfoArrayList.get(3))));

        //Deposit and payment have been made
        //User John Cena
        arrayListAppointment.add(new Appointment("A0808202101","-",carsArrayList.get(0),LocalDateTime.of(2021, 8,8,8,0),serviceArrayList.get(0),new Payment("P030821002","Credit Card",LocalDateTime.of(2021, Month.AUGUST,3,18,10,0)),
                new Payment("P090821006","Credit Card",LocalDateTime.of(2021, Month.AUGUST,9,9,10,0))));
        arrayListAppointment.add(new Appointment("A0809202101","-",carsArrayList.get(1),LocalDateTime.of(2021, 9,8,8,0),serviceArrayList.get(1),new Payment("P030821004","Online Banking",LocalDateTime.of(2021, Month.AUGUST,3,18,12,0)),
                new Payment("P090921008","Online Banking",LocalDateTime.of(2021, Month.SEPTEMBER,9,19,05,0))));
        arrayListAppointment.add(new Appointment("A0710202102","-",carsArrayList.get(2),LocalDateTime.of(2021, 10,7,10,0),serviceArrayList.get(4),new Payment("P030821003","Debit Card",LocalDateTime.of(2021, Month.AUGUST,3,20,13,0)),
                new Payment("P081021010","Debit Card",LocalDateTime.of(2021, Month.OCTOBER,8,18,25,0))));


        //User Jenny Tan
        arrayListAppointment.add(new Appointment("A1009202101","-",carsArrayList.get(3),LocalDateTime.of(2021, 9,10,8,0),serviceArrayList.get(1),new Payment("P250821007","Online Banking",LocalDateTime.of(2021, Month.AUGUST,25,10,10,0)),
                new Payment("P110921009","Credit Card",LocalDateTime.of(2021, Month.SEPTEMBER,11,10,10,0))));

        //User Jiah Ming
        arrayListAppointment.add(new Appointment("A1012202101","-",carsArrayList.get(5),LocalDateTime.of(2021, 12,10,8,0),serviceArrayList.get(2),new Payment("P101121011","Debit Card",LocalDateTime.of(2021, Month.NOVEMBER,10,8,10,0)),
                new Payment("P111121014","Online Banking",LocalDateTime.of(2021, Month.NOVEMBER,11,10,0,0))));
        arrayListAppointment.add(new Appointment("A1212202101","-",carsArrayList.get(5),LocalDateTime.of(2021, 12,12,8,0),serviceArrayList.get(2),new Payment("P101121012","Credit Card",LocalDateTime.of(2021, Month.NOVEMBER,10,9,15,0)),
                new Payment("P151121015","Debit Card",LocalDateTime.of(2021, Month.NOVEMBER,15,12,0,0))));

        //-----------------------------------------------------------------------------------------------------------------------------------------
        //Deposit made only
        //User Xiang Wen
        arrayListAppointment.add(new Appointment("A0807202101","-",carsArrayList.get(6),LocalDateTime.of(2021, 07,8,8,0),serviceArrayList.get(4),new Payment("P030721001","Online Banking",LocalDateTime.of(2021, Month.JULY,3,10,10,0)),null));
        //User Jenny Tan
        arrayListAppointment.add(new Appointment("A0810202102","-",carsArrayList.get(4),LocalDateTime.of(2021, 10,8,10,0),serviceArrayList.get(3),new Payment("P040821005","Online Banking",LocalDateTime.of(2021, Month.AUGUST,4,8,10,0)),null));
        //Jian Ming
        arrayListAppointment.add(new Appointment("A1312202101","-",carsArrayList.get(5),LocalDateTime.of(2021, 12,13,8,0),serviceArrayList.get(2),new Payment("P101121013","Credit Card",LocalDateTime.of(2021, Month.NOVEMBER,10,9,15,0)), null));
    }

    //Print header for every module and functions
    public static void functionHeader(int module){
        String headerName="";
        switch(module){
            case 1:
                headerName="A P P O I N T M E N T   M O D U L E ";
                break;
            case 2:
                headerName="S E R V I C E  &  C A R  M O D U L E";
                break;
            case 3:
                headerName="P A Y M E N T  M O D U L E ";
                break;
            case 4:
                headerName="U S E R  M O D U L E  ";
                break;
            case 5:
                headerName="E C O A T  M A I N  M E N U";
                break;
            //User main header
            case 6:
                headerName="R E G I S T E R";
                break;
            case 7:
                headerName="W E L C O M E  A D M I N";
                break;
            //Car header
            case 8:
                headerName="S E R V I C E  S E C T I O N";
                break;
            case 9:
                headerName="A D D  S E R V I C E";
                break;
            case 10:
                headerName="E D I T  S E R V I C E S";
                break;
            case 11:
                headerName="D E L E T E  S E R V I C E S";
                break;
            case 12:
                headerName="C A R  S E C T I O N";
                break;
            case 13:
                headerName="E D I T  C A R  S E R V I C E S ";
                break;
            case 14:
                headerName="D E L E T E  C A R  I N F O ";
                break;
            case 15:
                headerName="A D D  C A R  T Y P E";
                break;
            case 16:
                headerName="D E L E T E  C A R  T Y P E";
                break;
            case 17:
                headerName=" C A R  R E C O R D S";
                break;
            case 18:
                headerName="C A R  R E N T A L";
                break;
            //Appointment
            case 19:
                headerName="A D D  A P P O I N T M E N T";
                break;
            case 20:
                headerName="C U S T O M E R  D E P O S I T";
                break;
            case 21:
                headerName="D E L E T E  A P P O I N M E N T";
                break;
            case 22:
                headerName="C A N C E L  A P P O I N M E N T";
                break;
            case 23:
                headerName="E D I T  A P P O I N M E N T";
                break;
            case 24:
                headerName="D I S P L A Y  A P P O I N M E N T";
                break;
            //Payment
            case 25:
                headerName="P A Y M E N T  H I S T O R Y";
                break;
            case 26:
                headerName="C H E C K - O U T ";
                break;
            case 27:
                headerName="S T A F F  P A Y M E N T  M E N U";
                break;
            case 28:
                headerName="D A I L Y  S A L E S";
                break;
            case 29:
                headerName="M O N T H L Y  S A L E S";
                break;
            case 30:
                headerName="T R A N S A C T I O N  R E P O R T";
                break;
            case 31:
                headerName="P A Y M E N T S O R T I N G   R E P O R T";
                break;
            case 32:
                headerName="C U S T O M E R  P A Y M E N T  M E N U";
                break;


        }
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("\n\n     ----------------------------------------------------------------------------------------------------------");
        System.out.println("     \t\t                                                                                                 ");
        System.out.println("     \t\t8888888888 .d8888b.   .d88888b.        d8888 88888888888                                         ");
        System.out.println("     \t\t888       d88P  Y88b d88P   Y88b      d88888     888                        12,Jln Masjid Negeri ");
        System.out.println("     \t\t888       888    888 888     888     d88P888     888                     Taman Taruc,11600 Mesra ");
        System.out.println("     \t\t8888888   888        888     888    d88P 888     888                                Pulau Pinang ");
        System.out.println("     \t\t888       888        888     888   d88P  888     888                                             ");
        System.out.printf("     \t\t888       888    888 888     888  d88P   888     888   %-40s  \n",headerName);
        System.out.println("     \t\t888       Y88b  d88P Y88b. .d88P d8888888888     888                                             ");
        System.out.printf("     \t\t8888888888  Y8888P     Y88888P  d88P     888     888              Date Time: %-12s \n",dateTime.format(formatter));
        System.out.println("     \t\t                                                                                                 ");
        System.out.println("     ----------------------------------------------------------------------------------------------------------");

    }

    public static void homePage() throws InterruptedException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("\t\t\t        /$$      /$$ /$$$$$$$$ /$$        /$$$$$$   /$$$$$$  /$$      /$$ /$$$$$$$$ ");
        System.out.println("\t\t\t       | $$  /$ | $$| $$_____/| $$       /$$__  $$ /$$__  $$| $$$    /$$$| $$_____/  ");
        System.out.println("\t\t\t       | $$  $$$| $$| $$      | $$      | $$   __ | $$    $$| $$$$  /$$$$| $$       ");
        System.out.println("\t\t\t       | $$/$$ $$ $$| $$$$$   | $$      | $$   |  | $$  | $$| $$ $$/$$ $$| $$$$$    ");
        System.out.println("\t\t\t       | $$$$_  $$$$| $$__/   | $$_____ | $$   |  | $$  | $$| $$  $$$| $$| $$__/    ");
        System.out.println("\t\t\t       |__/     \\__/|________/| $$______/ \\______/  \\______/|__/    |__/|________/");
        System.out.println("\n\t\t\t\t\t\t_________   ______    ______    ______   ________ ");
        System.out.println("\t\t\t\t\t\t$$$$$$$$ / /$$$$$$  |/$$$$$$  |/$$$$$$  |$$$$$$$$/ ");
        System.out.println("\t\t\t\t\t\t$$  |__    $$ |  $$/ $$ |  $$ |$$ |__$$ |   $$ |   ");
        System.out.println("\t\t\t\t\t\t$$     |   $$ |      $$ |  $$ |$$    $$ |   $$ |   ");
        System.out.println("\t\t\t\t\t\t$$$$$ /    $$ |   __ $$ |  $$ |$$$$$$$$ |   $$ |   ");
        System.out.println("\t\t\t\t\t\t$$  |_____ $$ |__/  |$$ |__$$ |$$ |  $$ |   $$ |   ");
        System.out.println("\t\t\t\t\t\t$$$$$$$$ /  $$$$$$/   $$$$$$/  $$/   $$/    $$/   ");
        System.out.println("\n                                                                      Current Time : "+ LocalDate.now()+" "+ LocalTime.now());
        System.out.println("\t\tNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNmmmddhysooo++++++++++oooossyyhdmmmNmmmmmmmmNmNNNNNNNNmdmm");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\tNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNmh+/::------------::-:++++++oo+++++osyhmmmmmmmNmNmNNNNNNmdmm");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\tNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNmy+:------::://++++++::ssoooo+++ys+++///ooosydmmmNmNNNNNNNNNdmm");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\tNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNmh+---:::////+oooooooo+-+ddhyyhdmdhdmdhhyyyyyssyysymmmNmmNNNNNNdmm");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\tNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNmhs/--:://++++oooooooooo+-smNmmmmmmNNddNNmdmmmmdmdmmd/:ohmmmNNNNNNmdd");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\tNNNNNNNNNNNNNNNNNmdhyyso+//::::---...``.```````````.....ohhhsso+++++//////::::::::::.....:+dNNNNNNNN");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\tNNNNNNNNNNNNdy+/--..`````````````````````..........```....------....-:-----------:/:-------omNNNNNNN");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\tNNNNNNNNmho:-...--.............--------...........----------------..--:-...........``````..-oNNNNNNN");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\tNNNNNNNNhymddhssyyyyyyo/ooossooss+oyyo/:.-:osyys/:-----------------------------::::::+oo/:::/oNNNNNN");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\tNNNNNNNy+smNNNdmNNNNNNdhdyyyosssso++/---/ymmdmmNNh/:::::::::://////////////////////+hddmmy////mNNNNN");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\tmmmmmmy---+oss+/sysss/----.........--::+dddmhhdddNd/:://///////////////////:::::///hhmdymNo//+NNNNNN");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\toosssso---o+/+/++++---..--::::::/o::::/ddhmmdyNNddNs::://::::::-------------::////yhydddNmy+/oddddmm");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\t++++oooohhdhhhyyyyyo++++///+++oodNo:::ymdyyhdhhhyhNh::------------:::://++oo+++///mhmyhdhdy++yyyyyyy");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\t+++o++/mNNNNNNNNNNNNNNNmds:/ooooymy::/Nmhhddsyhmdymh/////+++oooooooooo+++////////oNdyyydhdssyyyyyyyy");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\t\t+/////+syddmmmmNmNNmmhddms-.....-/+//+MNdmmyyhymhyNs//////////++++oooossyyyyyyhhhmMNhmdyyhysyyyyyyyy");
        //load 0.5 seconds
        Thread.sleep(500);
        System.out.println("\n\t\t----------------------------------------------------------------------------------------------------");
        System.out.println("\t\t        - Nice To Meet You All . ECOAT provide the best car coating service for you ! -");

    }


}
