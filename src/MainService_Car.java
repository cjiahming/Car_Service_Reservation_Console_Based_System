import java.util.*;

public class MainService_Car {
    public static String ANSI_RESET = "\u001B[0m";
    public static String RED_BRIGHT = "\033[0;91m";
    public static String ANSI_RED = "\u001B[31m";
    public static String GREEN_BRIGHT = "\033[0;92m";
    public static String BLUE_BRIGHT = "\033[0;94m";
    public static String ANSI_PURPLE = "\u001B[35m";
    public static String ANSI_YELLOW = "\u001B[33m";
    public static String ANSI_CYAN = "\u001B[36m";


    public static void mainService_Car(UserInfo loginUser, ArrayList<Service> serviceArrayList, ArrayList<Car> carsArrayList, ArrayList<Appointment> arrayListAppointment, ArrayList<String> carTypeArrayList) {
        menuFeatureAccess(loginUser, serviceArrayList, arrayListAppointment, carsArrayList, carTypeArrayList);
    }

    public static void adminMenu(ArrayList<Service> serviceArrayList, ArrayList<Appointment> arrayListAppointment, ArrayList<Car> carsArrayList, ArrayList<String> carTypeArrayList) {
        int choice;
        Scanner s = new Scanner(System.in);

        Main.functionHeader(7);

        do{
            System.out.println("\t\t\t\t---------------------------------------------------");
            System.out.println("\t\t\t\t|\t\t    Choose what you want to do\t\t\t  |");
            System.out.println("\t\t\t\t---------------------------------------------------");
            System.out.println("\t\t\t\t|\t1. Service Section                            |");
            System.out.println("\t\t\t\t|\t2. Car Type Section                           |");
            System.out.println("\t\t\t\t|\t3. View Every Car Record                      |");
            System.out.println("\t\t\t\t|\t4. Back                                       |");
            System.out.println("\t\t\t\t---------------------------------------------------");

            System.out.print("\n\t\t\t\t\tEnter your choice : ");
            integerOnlyValidation(s);
            choice = s.nextInt();

            switch (choice) {
                case 1:
                    servMenu(serviceArrayList, arrayListAppointment, carsArrayList, carTypeArrayList);
                    s.nextLine();
                    break;
                case 2:
                    adminCarTypeMenu(serviceArrayList, carsArrayList, carTypeArrayList, arrayListAppointment);
                    break;
                case 3:
                    displayAllCar(carsArrayList);
                    break;
                case 4:
                    break;
                default:
                    System.out.println(ANSI_RED + "\t\t\t\t\tPlease enter from 1 to 4 only.\n" + ANSI_RESET);
            }
        }while (choice != 4);
    }


    //service menu that lets user to navigate to different methods
    public static void servMenu(ArrayList<Service> serviceArrayList, ArrayList<Appointment> arrayListAppointment, ArrayList<Car> carsArrayList, ArrayList<String> carTypeArrayList) {
        Main.functionHeader(8);

        Scanner s = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\t\t\t\t---------------------------------------------------");
            System.out.println("\t\t\t\t|\t\t    Choose what you want to do\t\t\t  |");
            System.out.println("\t\t\t\t---------------------------------------------------");
            System.out.println("\t\t\t\t|\t1.Display Services                            |");
            System.out.println("\t\t\t\t|\t2.Add Services                                |");
            System.out.println("\t\t\t\t|\t3.Edit Services                               |");
            System.out.println("\t\t\t\t|\t4.Delete Services                             |");
            System.out.println("\t\t\t\t|\t5.Service Report                              |");
            System.out.println("\t\t\t\t|\t6.Back                                        |");
            System.out.println("\t\t\t\t---------------------------------------------------");
            System.out.print("\n\t\t\t\t\tEnter your choice : ");
            integerOnlyValidation(s);
            choice = s.nextInt();

            switch (choice) {
                case 1:
                    servDisplay(serviceArrayList);
                    s.nextLine();
                    break;
                case 2:
                    servAdd(serviceArrayList);
                    break;
                case 3:
                    servEdit(serviceArrayList);
                    break;
                case 4:
                    servDelete(serviceArrayList);
                    break;
                case 5: servReport(serviceArrayList, arrayListAppointment); break;
                case 6:
                    break;
                default:
                    System.out.println(ANSI_RED + "\t\t\t\t\tPlease enter from 1 to 6 only.\n" + ANSI_RESET);
            }
        } while (choice != 6);
    }

    //for displaying current service records in the serviceArrayList
    public static void servDisplay(ArrayList<Service> serviceArrayList) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        int count=0;

        Main.functionHeader(8);
        System.out.println("\t\t-------------------------------------------------------------------------------------------------------");
        System.out.println("\t\t| No.|  Service ID\t\tService Type\t\t\t\tService Price(RM)\t\tService Description\t\t  |");
        System.out.println("\t\t-------------------------------------------------------------------------------------------------------");

        //Display every service records
        for (int i = 0; i < serviceArrayList.size(); i++) {
            count++;
            System.out.printf("\t\t|%2d. |  %-11s\t\t%-20s\t\t%-16.2f\t\t%-26s|\n", count, (serviceArrayList.get(i)).getServiceID(), (serviceArrayList.get(i)).getServiceType(), (serviceArrayList.get(i)).getServicePrice(), (serviceArrayList.get(i)).getServiceDesc());
        }

        System.out.println("\t\t-------------------------------------------------------------------------------------------------------\n");
        System.out.println("\n\t\t------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    //for adding new service records into serviceArrayList
    public static void servAdd(ArrayList<Service> serviceArrayList) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        Scanner s = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        String sType;
        char choiceAdd;
        char addContinue;
        int i;
        boolean found = false;

        Main.functionHeader(9);

        do{
            System.out.print("\t\t\t\t\tEnter new service type(Type XXX to quit) : ");
            alphabetOnlyValidation(s); //Input only alphabet validation
            sType = s.nextLine();
            for (i = 0; i < serviceArrayList.size(); i++) {
                //check if the user input service is currently existed in the serviceArrayList or not
                while ((serviceArrayList.get(i).getServiceType().compareTo(sType)) == 0) {
                    System.out.print(RED_BRIGHT + "\t\t\t\t\tService Type Existed. Please Re-Enter : " + ANSI_RESET);
                    alphabetOnlyValidation(s);
                    sType = s.nextLine();
                    i = 0;
                }
            }

            if(sType.equalsIgnoreCase("XXX")) {
                System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                break;
            }

            //For generating service ID based on service type input. Eg:Paint Coating = 'PC'
            String sID = generateServiceID(sType);

            System.out.print("\t\t\t\t\tEnter new service price : ");
            sPriceValidation(s); //Validation only allow numbers
            Double sPrice = s.nextDouble();

            s.nextLine();

            System.out.print("\t\t\t\t\tEnter new service desc : ");
            alphabetOnlyValidation(s); //Validation for alphabet input only
            String sDesc = s.nextLine();

            System.out.print("\n\t\t\t\t\tAre you sure to add this record? (Y/N): ");
            choiceAdd = s.next().trim().charAt(0);

            if (choiceAdd == 'y' || choiceAdd == 'Y') {
                serviceArrayList.add(new Service(sID, sType, sPrice, sDesc));
                System.out.println(GREEN_BRIGHT + "\n\t\t\t\t\tRecord is added!" + ANSI_RESET);
            } else {
                System.out.println(BLUE_BRIGHT + "\n\t\t\t\t\tRecord is not added!" + ANSI_RESET);
            }

            System.out.print("\n\t\t\t\t\tDo you want to continue adding? (Y/N) : ");
            addContinue = s.next().trim().charAt(0);
            s.nextLine();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }while(addContinue == 'y' || addContinue == 'Y');
    }

    //for editing current existing service records and save it into serviceArrayList
    public static void servEdit(ArrayList<Service> serviceArrayList) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        Scanner s = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);

        String sType = null;
        String sIDEdit = null;
        boolean found = false;
        char choice = 0;
        char continueChoice;
        int editChoose;
        int i = 0;
        int serviceIndex = 0;

        Main.functionHeader(10);

        do{
            found = false;
            System.out.print("\n\t\t\t\t\tEnter Service ID to edit(Type XXX to quit) : ");
            String sID = s.nextLine();
            sID = sID.toUpperCase();

            if(sID.equalsIgnoreCase("XXX")){
                System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                break;
            }
            System.out.println("\t\t\t\t\t-------------------------");

            for (i = 0; i < serviceArrayList.size(); i++) {
                if ((serviceArrayList.get(i).getServiceID().compareTo(sID)) == 0) {  //Find same service ID from the serviceArrayList with the service ID entered from the input
                    found = true;

                    System.out.println("\n\t\t\t\t\tResults : ");
                    System.out.println("\t\t\t\t\t---------------------------------------------------------------------------------------------------");
                    System.out.println("\t\t\t\t\t|  Service ID\t\tService Type\t\t\t\tService Price(RM)\t\tService Description\t\t  |");
                    System.out.println("\t\t\t\t\t---------------------------------------------------------------------------------------------------");
                    System.out.printf("\t\t\t\t\t|  %-16s %-27s %-23.2f %-26s|\n", (serviceArrayList.get(i).getServiceID()), (serviceArrayList.get(i).getServiceType()), (serviceArrayList.get(i).getServicePrice()), (serviceArrayList.get(i).getServiceDesc()));
                    serviceIndex = i;  //Store service index so that can use for later
                    System.out.println("\t\t\t\t\t---------------------------------------------------------------------------------------------------");

                    //Store the service ID digit to prevent system from changing it to other numbers. Eg: PC001 --> store the '001' into sIDNum.
                    String sIDNum = "";
                    sIDNum += serviceArrayList.get(serviceIndex).getServiceID().charAt(2);
                    sIDNum += serviceArrayList.get(serviceIndex).getServiceID().charAt(3);
                    sIDNum += serviceArrayList.get(serviceIndex).getServiceID().charAt(4);

                    System.out.println("\n\t\t\t\t\tPlease choose which to edit");
                    System.out.println("\t\t\t\t\t---------------------------");
                    System.out.println("\t\t\t\t\t1.Edit Service Type");
                    System.out.println("\t\t\t\t\t2.Edit Service Price");
                    System.out.println("\t\t\t\t\t3.Edit Service Description");

                    System.out.print("\n\t\t\t\t\tEnter your choice : ");
                    editChoose = s2.nextInt();

                    if(editChoose == 1) {
                        System.out.print("\n\t\t\t\t\tEnter new service type : ");
                        alphabetOnlyValidation(s);
                        sType = s.nextLine();
                        for (i = 0; i < serviceArrayList.size(); i++) {
                            while ((serviceArrayList.get(i).getServiceType().compareTo(sType)) == 0) {
                                found = true;
                                System.out.print(RED_BRIGHT + "\t\t\t\t\tService Type Existed. Please Re-Enter : " + ANSI_RESET);
                                alphabetOnlyValidation(s);
                                sType = s.nextLine();
                                i = 0;
                            }
                        }

                        //Generate service ID based on the first letter and the first letter after the spacing. eg: Paint Coating = 'PC'
                        sIDEdit = generateServiceID(sType) + sIDNum;

                        System.out.print("\n\t\t\t\t\tAre you sure you want to edit this service? (Y/N) : ");
                        choice = s.next().trim().charAt(0);

                        if(choice == 'y' || choice == 'Y') {
                            (serviceArrayList.get(serviceIndex)).setServiceID(sIDEdit);
                            (serviceArrayList.get(serviceIndex)).setServiceType(sType);
                        }
                    }

                    else if (editChoose == 2) {
                        System.out.print("\n\t\t\t\t\tEnter new service price :");
                        sPriceValidation(s); //service price validation
                        Double sPrice = s.nextDouble();
                        s.nextLine();

                        System.out.print("\n\t\t\t\t\tAre you sure you want to edit this service? (Y/N) : ");
                        choice = s.next().trim().charAt(0);

                        if(choice == 'y' || choice == 'Y') {
                            (serviceArrayList.get(serviceIndex)).setServicePrice(sPrice);
                        }
                    }

                    else if(editChoose == 3) {
                        System.out.print("\n\t\t\t\t\tEnter new service desc :");
                        alphabetOnlyValidation(s); //input alphabet only validation
                        String sDesc = s.nextLine();

                        System.out.print("\n\t\t\t\t\tAre you sure you want to edit this service? (Y/N) : ");
                        choice = s.next().trim().charAt(0);

                        if(choice == 'y' || choice == 'Y') {
                            (serviceArrayList.get(serviceIndex)).setServiceDesc(sDesc);
                        }
                    }

                    else {
                        System.out.println(RED_BRIGHT+"Please choose from 1 to 3 only."+ANSI_RESET);
                    }


                    if (choice == 'y' || choice == 'Y') {
                        //edit service record based on the input

                        System.out.println(GREEN_BRIGHT + "\n\t\t\t\t\tService Record edited successfully" + ANSI_RESET);
                    } else {
                        System.out.println(BLUE_BRIGHT + "\n\t\t\t\t\tThis service record is not edited." + ANSI_RESET);
                    }
                }
            }
            if (!found) {
                System.out.println(RED_BRIGHT + "\t\t\t\t\tRecord Not Found" + ANSI_RESET);
            }

            System.out.print("\n\t\t\t\t\tDo you want to continue editing? (Y/N) : ");
            continueChoice = s.next().trim().charAt(0);
            s.nextLine();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }while(continueChoice == 'y' || continueChoice == 'Y');
    }

    //for deleting service records from the serviceArrayList
    public static void servDelete(ArrayList<Service> serviceArrayList) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        Scanner s = new Scanner(System.in);
        boolean found = false;
        int i = 0;
        int serviceIndex;
        char choice;

        Main.functionHeader(11);
        System.out.print("\t\t\t\t\tEnter Service ID to delete : ");
        String sID = s.nextLine();
        sID = sID.toUpperCase();

        System.out.println("\t\t\t\t\t---------------------------");

        for (i = 0; i < serviceArrayList.size(); i++) {
            if ((serviceArrayList.get(i).getServiceID().compareTo(sID)) == 0) {  //Finding if the service ID input match with any same service record in the serviceArrayList
                found = true;

                System.out.println("\n\t\t\t\t\tResults : ");
                System.out.println("\t\t\t\t\t---------------------------------------------------------------------------------------------------");
                System.out.println("\t\t\t\t\t|  Service ID\t\tService Type\t\t\t\tService Price(RM)\t\tService Description\t\t  |");
                System.out.println("\t\t\t\t\t---------------------------------------------------------------------------------------------------");
                System.out.printf("\t\t\t\t\t|  %-16s %-27s %-23.2f %-26s|\n", (serviceArrayList.get(i).getServiceID()), (serviceArrayList.get(i).getServiceType()), (serviceArrayList.get(i).getServicePrice()), (serviceArrayList.get(i).getServiceDesc()));
                serviceIndex = i;  //Store service index so that can use for later
                System.out.println("\t\t\t\t\t---------------------------------------------------------------------------------------------------");

                System.out.print("\n\t\t\t\t\tAre you sure you want to delete this service? : ");
                choice = s.next().trim().charAt(0);

                if (choice == 'y' || choice == 'Y') {
                    serviceArrayList.remove(serviceIndex); //Remove service record based on service ID entered
                    System.out.println(GREEN_BRIGHT + "\n\t\t\t\t\tRecord Deleted Successfully!" + ANSI_RESET);
                } else {
                    System.out.println(BLUE_BRIGHT + "\n\t\t\t\t\tThis service record is not deleted." + ANSI_RESET);
                }
            }
        }

        if (!found) {
            System.out.println(RED_BRIGHT + "\t\t\t\t\tRecord Not Found!" + ANSI_RESET);
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    //Report of highest total ordered service to lowest ordered service
    public static void servReport(ArrayList<Service> serviceArrayList, ArrayList<Appointment> arrayListAppointment) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        int numService = 0;
        int count = 0;

        ArrayList<Service> serviceArrayListReport = new ArrayList<Service>(serviceArrayList);

        for(int i = 0; i < serviceArrayList.size(); i++) {
            numService++;
        }

        int[] serviceCount = new int[numService];

        //check service one by one
        for (int i = 0; i < arrayListAppointment.size(); i++) {
            for (int x = 0; x < serviceArrayList.size(); x++) {
                if (serviceArrayList.get(x).getServiceType().equals(arrayListAppointment.get(i).getService().getServiceType())) {
                    if (arrayListAppointment.get(i).getPayment() == null) {
                        serviceCount[x]++;
                    }
                }
            }
        }

        //Sort by from highest order to lowest order

        for (int i = 0; i < (serviceCount.length) - 1; i++) {
            for (int j = 0; j < (serviceCount.length) - i - 1; j++) {
                if (serviceCount[j] < serviceCount[j + 1]) {
                    int temp = serviceCount[j];
                    serviceCount[j] = serviceCount[j + 1];
                    serviceCount[j + 1] = temp;
                    Collections.swap(serviceArrayListReport, j, j + 1);
                }
            }
        }

        System.out.println(BLUE_BRIGHT+"\t\t\t\t\tReport Of Number Of Different Service Orders"+ANSI_RESET);
        System.out.println("\t\t\t\t------------------------------------------------------");
        System.out.println("\t\t\t\t| No.|  Service Type\t\t\t\tNumber Of Orders |");
        System.out.println("\t\t\t\t------------------------------------------------------");
        for(int i = 0; i < serviceArrayListReport.size(); i++) {
            count++;
            System.out.printf("\t\t\t\t|%2d. |  %-35s %-9d|\n", count,  (serviceArrayListReport.get(i)).getServiceType(), serviceCount[i]);
        }
        System.out.println("\t\t\t\t------------------------------------------------------");
        System.out.println(ANSI_YELLOW+"\nPurpose Of This Report : This report is to show each service orders from high to low.\n"+ANSI_RESET);

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    //methods for generating service ID eg: Paint Coating = 'PC'
    public static String generateServiceID(String sType) {
        String sID = "";
        for (String s : sType.split(" ")) {
            sID += s.charAt(0);
        }
        return sID;
    }

    /*****************************  CAR  ***********************************/

    //Initialize car records into carArrayList
    public static void carInitialize(ArrayList<Car> carsArrayList) {
        carsArrayList.add(new Car("PJK8888", "Mercedes", "Coupe", "Lim Ah Beng", "018-2804925", new Customer()));
        carsArrayList.add(new Car("PJK8882", "BMW", "Sedan", "Tan Ah Kao", "018-1234567", new Customer()));
        carsArrayList.add(new Car("PJK8886", "Honda XRV", "SUV", "Lim Xiao Ming", "018-1235567", new Customer("C130621891", new Name("Wong", "Tan"), "bohntan@hotmail.com", "0164381233", "123123")));
        carsArrayList.add(new Car("PJK2222", "Bentley", "Coupe", "Zhong Li", "018-2801192", new Customer()));
        carsArrayList.add(new Car("PKR8836", "Viva", "Van", "Lee Meng Zi", "018-81251367", new Customer()));
        carsArrayList.add(new Car("PKB3882", "Honda CRV", "SUV", "Lim Zi Ping", "018-12591267", new Customer()));
    }

    //Car Menu
    public static void carMenu(ArrayList<Car> carArrayList, Customer customer, ArrayList<Appointment> arrayListAppointment, ArrayList<String> carTypeArrayList) {
        Main.functionHeader(12);
        Scanner s = new Scanner(System.in);
        int carMenuChoice;
        do {
            System.out.println("\t\t\t\t---------------------------------------------------");
            System.out.println("\t\t\t\t|\t\t    Choose what you want to do\t\t\t  |");
            System.out.println("\t\t\t\t---------------------------------------------------");
            System.out.println("\t\t\t\t|\t1.Display Car Information                     |");
            System.out.println("\t\t\t\t|\t2.Add Car                                     |");
            System.out.println("\t\t\t\t|\t3.Edit Car Information                        |");
            System.out.println("\t\t\t\t|\t4.Remove A Car                                |");
            System.out.println("\t\t\t\t|\t5.Back                                        |");
            System.out.println("\t\t\t\t---------------------------------------------------");
            System.out.print("\n\t\t\t\t\tEnter your choice : ");
            integerOnlyValidation(s); //Input must integer only validation
            carMenuChoice = s.nextInt();

            switch (carMenuChoice) {
                case 1:
                    carDisplay(carArrayList, customer);
                    s.nextLine();
                    break;
                case 2:
                    carAdd(carArrayList, customer, carTypeArrayList);
                    break;
                case 3:
                    carEdit(carArrayList, customer, carTypeArrayList);
                    break;
                case 4:
                    carDelete(carArrayList, customer);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("\n\t\t\t\t\t" + ANSI_RED + "Please enter from 1 to 5 only.\n" + ANSI_RESET);
            }
        } while (carMenuChoice != 5);
    }

    //Display all car
    public static void carDisplay(ArrayList<Car> carsArrayList, Customer customer) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        int count=0;
        Main.functionHeader(12);
        System.out.println("\n\t\t\t\t\tName : " + customer.getName() + "\t\t\t\t\t\t\t\tContact No. : " + customer.getPhoneNumber());
        System.out.println("");

        System.out.println("\t\t\t----------------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t| No.|  Car Plate\t\tCar Name\t\t\t\tCar Type\t\t\tCar Owner Name\t\t\tCar Owner HP |");
        System.out.println("\t\t\t----------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < carsArrayList.size(); i++) {
            if (customer.getUserID().equals(carsArrayList.get(i).customer.getUserID())) {
                count++;
                System.out.printf("\t\t\t|%2d. | %8s\t\t\t%-16s\t\t%-10s\t\t\t%-18s\t\t%-13s|\n", count, (carsArrayList.get(i)).getCarNumPlate(), (carsArrayList.get(i)).getCarName(), (carsArrayList.get(i)).getCarType(), (carsArrayList.get(i)).getContactPersonName(), (carsArrayList.get(i)).getCarOwnerHP());
            }
        }
        System.out.println("\t\t\t----------------------------------------------------------------------------------------------------------");
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    //Add new car into carArrayList
    public static void carAdd(ArrayList<Car> carsArrayList, Customer customer, ArrayList<String> carTypeArrayList) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        Scanner s = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        String carOwnerHP;
        String cNumPlate;
        String cName;
        String cType = null;
        int i = 0;
        int cTypeChoice;
        char carAddChoice;
        char carAddContinue;

        Main.functionHeader(9);
        do{
            System.out.print("\t\t\t\t\tPlease enter Car Owner Name (Type XXX to quit) : ");
            //Input must be alphabet only validation
            alphabetOnlyValidation(s);
            String contactPersonName = s.nextLine();

            if(contactPersonName.equalsIgnoreCase("XXX")) {
                System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                break;
            }

            //HandPhone number validation
            do {
                System.out.print("\t\t\t\t\tPlease enter Car Owner HP with '-': ");
                carOwnerHP = s.nextLine();
            } while (!validatePhoneNumber(carOwnerHP));

            //Do validation for car number plate input
            do {
                System.out.print("\t\t\t\t\tEnter Car Number Plate : ");
                cNumPlate = s.nextLine();
                cNumPlate = cNumPlate.toUpperCase();
                for (i = 0; i < carsArrayList.size(); i++) {
                    if (customer.getUserID().equals(carsArrayList.get(i).customer.getUserID())) {
                        //check if the user input car is currently existed in the current user carArrayList or not
                        while ((carsArrayList.get(i).getCarNumPlate().compareTo(cNumPlate)) == 0) {
                            System.out.print(RED_BRIGHT + "\t\t\t\t\tCar Number Plate Existed. Please Re-Enter : " + ANSI_RESET);
                            cNumPlate = s.nextLine();
                            cNumPlate = cNumPlate.toUpperCase();
                            i = 0;
                        }
                    }
                }
            } while (!carNumPlateValidation(cNumPlate));

            //Car Name validation
            do {
                System.out.print("\t\t\t\t\tEnter Car Name : ");
                cName = s.nextLine();
            } while (!carNameValidation(cName));


            System.out.println("\n\t\t\t\t\tChoose Your Car Type :");


            System.out.println("\t\t\t\t\t-------------------------------------");
            System.out.println("\t\t\t\t\t| No.|\t\t\tCar Types\t\t\t|");
            System.out.println("\t\t\t\t\t-------------------------------------");

            int count=0;
            for(i=0; i<carTypeArrayList.size(); i++) {
                count++;
                System.out.printf("\t\t\t\t\t|%2d. | \t\t\t%-15s\t\t|\n", count, carTypeArrayList.get(i));
            }
            System.out.println("\t\t\t\t\t-------------------------------------\n");

            do {
                System.out.print("\t\t\t\t\tPlease choose your car type : ");
                integerOnlyValidation(s2);
                cTypeChoice = s2.nextInt();
                if(cTypeChoice < 1 || cTypeChoice > count) {
                    System.out.println(RED_BRIGHT+"\t\t\t\t\tPlease enter only from 1 to "+count+ ANSI_RESET+"\n");
                }
            } while (cTypeChoice < 1 || cTypeChoice > count);
            cType = carTypeArrayList.get(cTypeChoice-1);

            System.out.print("\n\t\t\t\t\tAre you sure you want to add this new car record? (Y/N) :");
            carAddChoice = s.next().trim().charAt(0);
            carAddChoice = Character.toUpperCase(carAddChoice);

            if (carAddChoice == 'Y') {
                carsArrayList.add(new Car(cNumPlate, cName, cType, contactPersonName, carOwnerHP, customer));
                System.out.println(GREEN_BRIGHT + "\n\t\t\t\t\tRecord is added!" + ANSI_RESET);
            } else {
                System.out.println(BLUE_BRIGHT + "\n\t\t\t\t\tCar record is not added." + ANSI_RESET);
            }

            System.out.print("\n\t\t\t\t\tDo you want to continue adding car records? (Y/N) : ");
            carAddContinue = s.next().trim().charAt(0);
            s.nextLine();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }while(carAddContinue == 'y' || carAddContinue == 'Y');
    }

    //Edit existing car
    public static void carEdit(ArrayList<Car> carsArrayList, Customer customer, ArrayList<String> carTypeArrayList) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        Scanner s = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        String carOwnerHP;
        String cName;
        String cType = null;
        boolean foundCar = false;
        char carEditContinue;
        int i = 0;
        int count = 0;
        int editCTypeChoice;
        int getCarIndex;
        char carEditChoice = 0;
        int carEditChoose;
        Main.functionHeader(13);

        do{
            foundCar= false;
            System.out.print("\n\t\t\t\t\tEnter Car Number Plate to edit (Type XXX to quit): ");
            String cNumPlate = s.nextLine();
            cNumPlate = cNumPlate.toUpperCase();

            if(cNumPlate.equalsIgnoreCase("XXX")){
                System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                break;
            }
            System.out.println("\t\t\t\t\t--------------------------------");

            for (i = 0; i < carsArrayList.size(); i++) {
                if ((carsArrayList.get(i).getCarNumPlate().compareTo(cNumPlate)) == 0) {  //find car records that are same with the car number plate input by user
                    if (customer.getUserID().equals(carsArrayList.get(i).customer.getUserID())) {
                        foundCar = true;
                        count++;

                        System.out.println("\n\t\t\t\t\tResults : ");
                        System.out.println("\t\t\t\t\t----------------------------------------------------------------------------------------------------------");
                        System.out.println("\t\t\t\t\t| No.|  Car Plate\t\tCar Name\t\t\t\tCar Type\t\t\tCar Owner Name\t\t\tCar Owner HP |");
                        System.out.println("\t\t\t\t\t----------------------------------------------------------------------------------------------------------");
                        System.out.printf("\t\t\t\t\t|%2d. | %8s\t\t\t%-16s\t\t%-10s\t\t\t%-18s\t\t%-13s|\n", count, (carsArrayList.get(i)).getCarNumPlate(), (carsArrayList.get(i)).getCarName(), (carsArrayList.get(i)).getCarType(), (carsArrayList.get(i)).getContactPersonName(), (carsArrayList.get(i)).getCarOwnerHP());

                        getCarIndex = i;
                        System.out.println("\t\t\t\t\t----------------------------------------------------------------------------------------------------------");


                        System.out.println("\n\t\t\t\t\tPlease choose which to edit");
                        System.out.println("\t\t\t\t\t---------------------------");
                        System.out.println("\t\t\t\t\t1.Change Car Owner Name");
                        System.out.println("\t\t\t\t\t2.Change Car Owner HP No.");
                        System.out.println("\t\t\t\t\t3.Change Car Number Plate");
                        System.out.println("\t\t\t\t\t4.Change Car Name");
                        System.out.println("\t\t\t\t\t5.Change Car Type");

                        System.out.print("\n\t\t\t\t\tEnter your choice : ");
                        carEditChoose = s2.nextInt();

                        if(carEditChoose == 1) {
                            System.out.print("\n\t\t\t\t\tPlease Enter Car Owner Name : ");
                            alphabetOnlyValidation(s);
                            String contactPersonName = s.nextLine();

                            System.out.print("\n\t\t\t\t\tAre you sure you want to edit this car record? : ");
                            carEditChoice = s.next().trim().charAt(0);

                            if (carEditChoice == 'y' || carEditChoice == 'Y') {
                                (carsArrayList.get(getCarIndex)).setContactPersonName(contactPersonName);
                            }
                        }

                        else if(carEditChoose == 2) {
                            //Validation for car owner hand phone number input
                            //HandPhone number validation
                            do {
                                System.out.print("\n\t\t\t\t\tPlease enter Car Owner HP with '-': ");
                                carOwnerHP = s.nextLine();
                            } while (!validatePhoneNumber(carOwnerHP));

                            System.out.print("\n\t\t\t\t\tAre you sure you want to edit this car record? : ");
                            carEditChoice = s.next().trim().charAt(0);

                            if (carEditChoice == 'y' || carEditChoice == 'Y') {
                                (carsArrayList.get(getCarIndex)).setCarOwnerHP(carOwnerHP);
                            }
                        }

                        else if(carEditChoose == 3) {
                            //Validation for car number plate input
                            do {
                                System.out.print("\n\t\t\t\t\tEnter Car Number Plate : ");
                                cNumPlate = s.nextLine();
                                cNumPlate = cNumPlate.toUpperCase();
                                for (i = 0; i < carsArrayList.size(); i++) {
                                    if (customer.getUserID().equals(carsArrayList.get(i).customer.getUserID())) {

                                        //check if the user input service is currently existed in the serviceArrayList or not
                                        while ((carsArrayList.get(i).getCarNumPlate().compareTo(cNumPlate)) == 0) {
                                            System.out.print(RED_BRIGHT + "\t\t\t\t\tCar Number Plate Existed. Please Re-Enter : " + ANSI_RESET);
                                            cNumPlate = s.nextLine();
                                            cNumPlate = cNumPlate.toUpperCase();
                                            i = 0;
                                        }
                                    }
                                }
                            } while (!carNumPlateValidation(cNumPlate));

                            System.out.print("\n\t\t\t\t\tAre you sure you want to edit this car record? : ");
                            carEditChoice = s.next().trim().charAt(0);

                            if (carEditChoice == 'y' || carEditChoice == 'Y') {
                                (carsArrayList.get(getCarIndex)).setCarNumPlate(cNumPlate);
                            }
                        }

                        else if(carEditChoose == 4) {
                            //Validation for car name input
                            do {
                                System.out.print("\n\t\t\t\t\tEnter New Car Name : ");
                                cName = s.nextLine();
                            } while (!carNameValidation(cName));

                            System.out.print("\n\t\t\t\t\tAre you sure you want to edit this car record? : ");
                            carEditChoice = s.next().trim().charAt(0);

                            if (carEditChoice == 'y' || carEditChoice == 'Y') {
                                (carsArrayList.get(getCarIndex)).setCarName(cName);
                            }
                        }

                        else if(carEditChoose == 5) {
                            System.out.println("\n\t\t\t\t\tChoose Your Car Type :");

                            System.out.println("\t\t\t\t\t-------------------------------------");
                            System.out.println("\t\t\t\t\t| No.|\t\t\tCar Types\t\t\t|");
                            System.out.println("\t\t\t\t\t-------------------------------------");

                            count = 0;

                            for(i=0; i<carTypeArrayList.size(); i++) {
                                count++;
                                System.out.printf("\t\t\t\t\t|%2d. | \t\t\t%-15s\t\t|\n", count, carTypeArrayList.get(i));
                            }
                            System.out.println("\t\t\t\t\t-------------------------------------\n");

                            do {
                                System.out.print("\t\t\t\t\tPlease enter your car type : ");
                                integerOnlyValidation(s2);
                                editCTypeChoice = s2.nextInt();
                                if(editCTypeChoice < 1 || editCTypeChoice > count) {
                                    System.out.println(RED_BRIGHT+"\t\t\t\t\tPlease enter only from 1 to "+count+ ANSI_RESET+"\n");
                                }
                            } while (editCTypeChoice < 1 || editCTypeChoice > count);
                            cType = carTypeArrayList.get(editCTypeChoice-1);

                            System.out.print("\n\t\t\t\t\tAre you sure you want to edit this car record? : ");
                            carEditChoice = s.next().trim().charAt(0);

                            if (carEditChoice == 'y' || carEditChoice == 'Y') {
                                (carsArrayList.get(getCarIndex)).setCarType(cType);
                            }
                        }

                        else {
                            System.out.println(RED_BRIGHT+"\t\t\t\t\tPlease choose from 1 to 5 only."+ANSI_RESET);
                        }

                        if (carEditChoice == 'y' || carEditChoice == 'Y') {
                            System.out.println(GREEN_BRIGHT + "\n\t\t\t\t\tCar Record edited successfully" + ANSI_RESET);
                        } else {
                            System.out.println(BLUE_BRIGHT + "\n\t\t\t\t\tThis car record is not edited." + ANSI_RESET);
                        }
                    }
                }
            }

            if (!foundCar) {
                System.out.println(RED_BRIGHT + "\t\t\t\t\tRecord Not Found" + ANSI_RESET);
            }

            System.out.print("\n\t\t\t\t\tDo you want to continue editing car records? (Y/N) : ");
            carEditContinue = s.next().trim().charAt(0);
            s.nextLine();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }while(carEditContinue == 'y' || carEditContinue == 'Y');
    }

    //Delete car from carArrayList
    public static void carDelete(ArrayList<Car> carsArrayList, Customer customer) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        Scanner s = new Scanner(System.in);
        boolean found = false;
        int i = 0;
        int carIndex;
        char cChoice;

        Main.functionHeader(14);
        System.out.print("\n\t\t\t\t\tEnter Car Number Plate to delete : ");
        String cNumPlate = s.nextLine();
        cNumPlate = cNumPlate.toUpperCase();
        System.out.println("\t\t\t\t\t---------------------------------");

        for (i = 0; i < carsArrayList.size(); i++) {
            if ((carsArrayList.get(i).getCarNumPlate().compareTo(cNumPlate)) == 0) {  //find car records that are same with the car number plate input by user
                if (customer.getUserID().equals(carsArrayList.get(i).customer.getUserID())) {

                    found = true;

                    System.out.println("\n\t\t\t\t\tResults : \n");
                    System.out.println("\t\t\t\t\t--------------------------------------------------------------------------------------------------");
                    System.out.println("\t\t\t\t\t| Car Plate\t\tCar Name\t\t\t\tCar Type\t\t\tCar Owner Name\t\t\tCar Owner HP |");
                    System.out.println("\t\t\t\t\t--------------------------------------------------------------------------------------------------");
                    System.out.printf("\t\t\t\t\t| %-13s %-24s %-18s %-23s %-13s|\n", (carsArrayList.get(i)).getCarNumPlate(), (carsArrayList.get(i)).getCarName(), (carsArrayList.get(i)).getCarType(), (carsArrayList.get(i)).getContactPersonName(), (carsArrayList.get(i)).getCarOwnerHP());
                    carIndex = i;
                    System.out.println("\t\t\t\t\t--------------------------------------------------------------------------------------------------");

                    System.out.print("\n\t\t\t\t\tAre you sure you want to delete this car? : ");
                    cChoice = s.next().trim().charAt(0);

                    if (cChoice == 'y' || cChoice == 'Y') {
                        carsArrayList.remove(carIndex);  //delete a specific car record from carArrayList
                        System.out.println(GREEN_BRIGHT + "\n\t\t\t\t\tRecord Deleted Successfully!" + ANSI_RESET);
                    } else {
                        System.out.println(BLUE_BRIGHT + "\n\t\t\t\t\tThis car record is not deleted." + ANSI_RESET);
                    }
                }
            }
        }

        if (!found) {
            System.out.println(RED_BRIGHT + "\t\t\t\t\tRecord Not Found." + ANSI_RESET);
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

    }

    public static void adminCarTypeMenu(ArrayList<Service> serviceArrayList, ArrayList<Car> carsArrayList, ArrayList<String> carTypeArrayList, ArrayList<Appointment> arrayListAppointment) {
        Scanner s = new Scanner(System.in);
        int carMenuChoice;
        do {
            Main.functionHeader(12);
            System.out.println("\t\t\t\t---------------------------------------------------");
            System.out.println("\t\t\t\t|\t\t    Choose what you want to do\t\t\t  |");
            System.out.println("\t\t\t\t---------------------------------------------------");
            System.out.println("\t\t\t\t|\t1.Display Car Types                           |");
            System.out.println("\t\t\t\t|\t2.Add Car Types                               |");
            System.out.println("\t\t\t\t|\t3.Edit Car Types                              |");
            System.out.println("\t\t\t\t|\t4.Delete car types                            |");
            System.out.println("\t\t\t\t|\t5.Car report                                  |");
            System.out.println("\t\t\t\t|\t6.Back                                        |");
            System.out.println("\t\t\t\t---------------------------------------------------");
            System.out.print("\n\t\t\t\t\tEnter your choice : ");
            integerOnlyValidation(s); //Input must integer only validation
            carMenuChoice = s.nextInt();

            switch (carMenuChoice) {
                case 1:
                    displayCarTypes(carTypeArrayList);
                    break;
                case 2:
                    carTypeAdd(carTypeArrayList);
                    break;
                case 3:
                    carTypeEdit(carTypeArrayList);
                    break;
                case 4:
                    deleteCarType(carTypeArrayList);
                    break;
                case 5:
                    carReport(carsArrayList, carTypeArrayList, arrayListAppointment);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("\t\t\t\t\t" + ANSI_RED + "Please enter from 1 to 6 only.\n" + ANSI_RESET);
            }
        } while (carMenuChoice != 6);
    }

    public static void displayCarTypes(ArrayList<String> carTypeArrayList) {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        int count = 0;
        Main.functionHeader(12);

        System.out.println("\t\t\t\t\t\t\t\t-------------------------------------");
        System.out.println("\t\t\t\t\t\t\t\t| No.|\t\t\tCar Types\t\t\t|");
        System.out.println("\t\t\t\t\t\t\t\t-------------------------------------");

        for(int i=0; i<carTypeArrayList.size(); i++) {
            count++;
            System.out.printf("\t\t\t\t\t\t\t\t|%2d. | \t\t\t%-15s\t\t|\n", count, carTypeArrayList.get(i));
        }
        System.out.println("\t\t\t\t\t\t\t\t-------------------------------------\n");
        System.out.println("\t\t\tNote : Admin is able to add new car types into the system by pressing 2 at the car menu.");
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }

    public static void carTypeAdd(ArrayList<String> carTypeArrayList) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        Scanner s = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        char carTypesChoice;
        char carTypeAddContinue;
        String carTypes;
        int i = 0;

        Main.functionHeader(15);

        do{
            System.out.print("\t\t\t\t\tPlease enter new Car Type to add (Type XXX to add): ");
            //Input must be alphabet only validation
            alphabetOnlyValidation(s);
            carTypes = s.nextLine();
            for (i = 0; i < carTypeArrayList.size(); i++) {
                //check if the user input service is currently existed in the serviceArrayList or not
                while ((carTypeArrayList.get(i).compareTo(carTypes)) == 0) {
                    System.out.print(RED_BRIGHT + "\t\t\t\t\tCar Type Existed. Please Re-Enter : " + ANSI_RESET);
                    alphabetOnlyValidation(s);
                    carTypes = s.nextLine();
                    i = 0;
                }
            }

            if(carTypes.equalsIgnoreCase("XXX")){
                System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                break;
            }

            System.out.print("\n\t\t\t\t\tAre you sure you want to add this new car type? (Y/N) :");
            carTypesChoice = s2.next().trim().charAt(0);
            carTypesChoice = Character.toUpperCase(carTypesChoice);

            if (carTypesChoice == 'Y') {
                carTypeArrayList.add(carTypes);
                System.out.println(GREEN_BRIGHT + "\n\t\t\t\t\tNew car type is added!" + ANSI_RESET);
            } else {
                System.out.println(BLUE_BRIGHT + "\n\t\t\t\t\tNo car type is added." + ANSI_RESET);
            }

            System.out.print("\n\t\t\t\t\tDo you want to continue adding car types into the system? (Y/N) :");
            carTypeAddContinue = s2.next().trim().charAt(0);
            s2.nextLine();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }while(carTypeAddContinue == 'y' || carTypeAddContinue == 'y');
    }

    public static void carTypeEdit(ArrayList<String> carTypeArrayList) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        Scanner s = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Scanner s3 = new Scanner(System.in);
        int i = 0;
        int count = 0;
        int editCarTypeChoose;
        char carTypeEditChoice;
        char carTypeEditContinue;

        Main.functionHeader(10);
        System.out.println("\n\t\t\t\tThese are all the car types available in our system.");

        do{
            count = 0;
            System.out.println("\t\t\t\t\t\t-------------------------------------");
            System.out.println("\t\t\t\t\t\t| No.|\t\t\tCar Types\t\t\t|");
            System.out.println("\t\t\t\t\t\t-------------------------------------");

            for(i=0; i<carTypeArrayList.size(); i++) {
                count++;
                System.out.printf("\t\t\t\t\t\t|%2d. | \t\t\t%-15s\t\t|\n", count, carTypeArrayList.get(i));
            }
            System.out.println("\t\t\t\t\t\t-------------------------------------\n");

            do{
                System.out.print("\t\t\t\t\tPlease choose which car type to edit. Eg - 1 : ");
                integerOnlyValidation(s); //Input must integer only validation
                editCarTypeChoose = s.nextInt();

                if(editCarTypeChoose < 1 || editCarTypeChoose > count) {
                    System.out.println(RED_BRIGHT+"\t\t\t\t\tPlease enter only from 1 to "+count+ ANSI_RESET+"\n");
                }
            }while(editCarTypeChoose < 1 || editCarTypeChoose > count);

            System.out.print("\n\t\t\t\t\tPlease enter new car type name : ");
            //Input must be alphabet only validation
            alphabetOnlyValidation(s2);
            String editCarType = s2.nextLine();
            for (i = 0; i < carTypeArrayList.size(); i++) {
                //check if the user input service is currently existed in the serviceArrayList or not
                while ((carTypeArrayList.get(i).compareTo(editCarType)) == 0) {
                    System.out.print(RED_BRIGHT + "\t\t\t\t\tCar Type Existed. Please Re-Enter : " + ANSI_RESET);
                    alphabetOnlyValidation(s2);
                    editCarType = s2.nextLine();
                    i = 0;
                }
            }

            System.out.println("");

            System.out.print("\t\t\t\t\tAre you sure you want to edit this car type from the system? : ");
            carTypeEditChoice = s2.next().trim().charAt(0);

            if(carTypeEditChoice == 'y' || carTypeEditChoice == 'Y') {
                carTypeArrayList.set(editCarTypeChoose-1, editCarType);
                System.out.println(GREEN_BRIGHT+"\n\t\t\t\t\tCar type record is changed."+ANSI_RESET);
            }
            else {
                System.out.println(RED_BRIGHT+"\n\t\t\t\t\tCar type record is not changed."+ANSI_RESET);
            }

            System.out.print("\n\t\t\t\t\tDo you want to continue editing car types? : ");
            carTypeEditContinue = s2.next().trim().charAt(0);
            s2.nextLine();
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }while(carTypeEditContinue == 'y' || carTypeEditContinue == 'Y');
    }

    public static void deleteCarType(ArrayList<String> carTypeArrayList) {
        int count = 0;
        int deleteCarTypeChoose;
        char carTypeDeleteChoice;
        Scanner s = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        Main.functionHeader(16);
        System.out.println("\n\t\t\t\tThese are all the car types available in our system.");
        System.out.println("\t\t\t\t\t\t-------------------------------------");
        System.out.println("\t\t\t\t\t\t| No.|\t\t\tCar Types\t\t\t|");
        System.out.println("\t\t\t\t\t\t-------------------------------------");

        for(int i=0; i<carTypeArrayList.size(); i++) {
            count++;
            System.out.printf("\t\t\t\t\t\t|%2d. | \t\t\t%-15s\t\t|\n", count, carTypeArrayList.get(i));
        }
        System.out.println("\t\t\t\t\t\t-------------------------------------\n");

        do{
            System.out.print("\t\t\t\t\tPlease choose which car type to edit. Eg - 1 : ");
            integerOnlyValidation(s); //Input must integer only validation
            deleteCarTypeChoose = s.nextInt();

            if(deleteCarTypeChoose < 1 || deleteCarTypeChoose > count) {
                System.out.println(RED_BRIGHT+"\t\t\t\t\tPlease enter only from 1 to "+count+ ANSI_RESET+"\n");
            }
        }while(deleteCarTypeChoose < 1 || deleteCarTypeChoose > count);

        System.out.println("");

        System.out.print("\t\t\t\t\tAre you sure you want to delete this car record? : ");
        carTypeDeleteChoice = s2.next().trim().charAt(0);

        if(carTypeDeleteChoice == 'y' || carTypeDeleteChoice == 'Y') {
            System.out.println("\n\t\t\t\t\t"+GREEN_BRIGHT+carTypeArrayList.get(deleteCarTypeChoose-1)+" car type is deleted from the system."+ANSI_RESET);
            carTypeArrayList.remove(deleteCarTypeChoose-1);
        }
        else {
            System.out.println("\n\t\t\t\t\t"+RED_BRIGHT+carTypeArrayList.get(deleteCarTypeChoose-1)+" car type is not deleted from the system."+ANSI_RESET);
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }
    //Report of car types that have order our services
    public static void carReport(ArrayList<Car> carsArrayList, ArrayList<String> carTypeArrayList, ArrayList<Appointment> arrayListAppointment) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        int numCar = 0;
        int count = 0;

        ArrayList<String> carArrayListReport = new ArrayList<String>(carTypeArrayList);

        for (int i = 0; i < carTypeArrayList.size(); i++) {
            numCar++;
        }

        //Use for counting number of car types that order which service
        int[] carCount = new int[numCar];

        for (int i = 0; i < arrayListAppointment.size(); i++) {
            for (int y = 0; y < carsArrayList.size(); y++) {
                if (carTypeArrayList.get(y).equals(arrayListAppointment.get(i).getCar().getCarType())) {
                    if (arrayListAppointment.get(i).getPayment() == null) {
                        carCount[y]++;
                    }
                }
            }
        }

        for (int i = 0; i < carCount.length-1; i++){
            for (int j = 0; j < (carCount.length)-i-1; j++) {
                if (carCount[j] < carCount[j+1])
                {
                    int temp = carCount[j];
                    carCount[j] = carCount[j+1];
                    carCount[j+1] = temp;
                    Collections.swap(carTypeArrayList, j, j+1);
                }
            }
        }

        System.out.println(BLUE_BRIGHT+"\t\t\t\t Report Of Number Of Car Types From Our Services"+ANSI_RESET);
        System.out.println("\t\t\t\t-------------------------------------------------");
        System.out.println("\t\t\t\t| No.|  Car Types\t\t\tNumber Of Car Types |");
        System.out.println("\t\t\t\t-------------------------------------------------");

        for(int i = 0; i < carTypeArrayList.size(); i++) {
            count++;
            System.out.printf("\t\t\t\t|%2d. |  %-16s\t\t\t%-11d |\n", count, carTypeArrayList.get(i), carCount[i]);
        }
        System.out.println("\t\t\t\t-------------------------------------------------");
        System.out.println(ANSI_YELLOW+"\nPurpose Of This Report : This report is to show the number of car types that have ordered our car coating services from High to Low\n"+ANSI_RESET);
        System.out.println("");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void displayAllCar(ArrayList<Car> carsArrayList) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        int count=0;

        Main.functionHeader(17);

        System.out.println("");

        System.out.println("\t\t\t----------------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t| No.|  Car Plate\t\tCar Name\t\t\t\tCar Type\t\t\tCar Owner Name\t\t\tCar Owner HP |");
        System.out.println("\t\t\t----------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < carsArrayList.size(); i++) {
            count++;
            System.out.printf("\t\t\t|%2d. | %8s\t\t\t%-16s\t\t%-10s\t\t\t%-18s\t\t%-13s|\n", count, (carsArrayList.get(i)).getCarNumPlate(), (carsArrayList.get(i)).getCarName(), (carsArrayList.get(i)).getCarType(), (carsArrayList.get(i)).getContactPersonName(), (carsArrayList.get(i)).getCarOwnerHP());
        }
        System.out.println("\t\t\t----------------------------------------------------------------------------------------------------------");
        System.out.println(ANSI_CYAN+"\t\t\t\t\t\t\tNote : This are all the car records that are in our system database.\n"+ANSI_RESET);
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    /************************************ VALIDATION ****************************************/

    //Validation input must be alphabet only
    public static void alphabetOnlyValidation(Scanner s) {
        while (!s.hasNext("[A-Za-z]+")) {
            System.out.println(RED_BRIGHT + "\t\t\t\t\t(Only Alphabetical Input)\n" + ANSI_RESET);
            System.out.print("\t\t\t\t\tEnter again : ");
            s.nextLine();
        }
    }

    //Validation input must be integer only
    public static void integerOnlyValidation(Scanner s2) {
        while (!s2.hasNextInt()) {
            System.out.println(RED_BRIGHT + "\t\t\t\t\t(Only Number Input)\n" + ANSI_RESET);
            System.out.print("\t\t\t\t\tEnter again : ");
            s2.nextLine();
        }
    }

    //service price validation
    public static void sPriceValidation(Scanner s) {
        while (!s.hasNextDouble()) {
            System.out.println(RED_BRIGHT + "\t\t\t\t\t(Input must be numbers only. Eg: 123 / 123.00)\n" + ANSI_RESET);
            System.out.print("\t\t\t\t\tType the Price again :");
            s.next();
        }
    }

    //Validation for Car Number Plate input must be alphanumeric
    public static boolean carNumPlateValidation(String carNumPlate) {
        boolean isAlpha = false;
        boolean isNum = false;

        for (int i = 0; i < carNumPlate.length(); i++) {
            if (Character.isLetter(carNumPlate.charAt(i))) {
                isAlpha = true;
            } else if (Character.isDigit(carNumPlate.charAt(i))) {
                isNum = true;
            }
        }

        //Validate user input must not contain special characters
        if (!isAlpha && !isNum) {
            System.out.println(RED_BRIGHT + "\t\t\t\t\t(Input must not contain special characters)" + ANSI_RESET);
            System.out.println("");
            return false;
        }

        //Validate user input must have at least one alphabet
        else if (isAlpha == false) {
            System.out.println(RED_BRIGHT + "\t\t\t\t\t(Input needs to contain at least one alphabet)" + ANSI_RESET);
            System.out.println("");
            return false;
        }

        //Validate user input must have at least one number
        else if (isNum == false) {
            System.out.println(RED_BRIGHT + "\t\t\t\t\t(Input needs to have numbers)" + ANSI_RESET);
            System.out.println("");
            return false;
        }
        return true;
    }

    //Validation for car name
    public static boolean carNameValidation(String cName) {
        boolean isAlpha = false;
        boolean isNum = false;

        for (int i = 0; i < cName.length(); i++) {
            if (Character.isLetter(cName.charAt(i))) {
                isAlpha = true;
            } else if (Character.isDigit(cName.charAt(i))) {
                isNum = true;
            }
        }

        if (!isAlpha && !isNum) {
            System.out.println(RED_BRIGHT + "\t\t\t\t\t(Input must not contain special characters)" + ANSI_RESET);
            System.out.println("");
            return false;
        } else if (!isAlpha) {
            System.out.println(RED_BRIGHT + "\t\t\t\t\t(Input needs to contain at least one alphabet)" + ANSI_RESET);
            System.out.println("");
            return false;
        }
        return true;
    }

    //Validation for phone number
    private static boolean validatePhoneNumber(String carOwnerHP) {
        //validating phone number must have "-" and total must have 10 numbers,
        if (carOwnerHP.matches("\\d{3}[-s]\\d{7}")) {
            return true;
        } else if (carOwnerHP.matches("\\d{3}[-s]\\d{8}")) {  //validating phone number must have "-" and total must have 1 numbers
            return true;
        } else {
            System.out.println(RED_BRIGHT + "\t\t\t\t\t(Please enter a valid phone number)" + ANSI_RESET);
            System.out.println("");
            return false;
        }

    }

    //To limit customer access to admin menu feature
    public static void menuFeatureAccess(UserInfo userInfo, ArrayList<Service> serviceArrayList, ArrayList<Appointment> arrayListAppointment, ArrayList<Car> carsArrayList, ArrayList<String> carTypeArrayList) {
        if(userInfo instanceof Customer) {
            Customer customer = (Customer)userInfo;
            carMenu(carsArrayList, customer, arrayListAppointment, carTypeArrayList);
        }
        else{
            adminMenu(serviceArrayList, arrayListAppointment, carsArrayList, carTypeArrayList);
        }
    }
}



