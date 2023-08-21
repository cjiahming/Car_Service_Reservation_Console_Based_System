import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UserMain {
    public static UserInfo mainUser(ArrayList<Customer> customers, ArrayList<Staff> staffs) {
        Scanner scanner = new Scanner(System.in);

        do {
            int mainMenuChoice = mainMenu();       //select register, login, reset password or search for userid
            //register
            if (mainMenuChoice == 1) {
                Customer newCustomer = registerCustomer(customers, staffs);     //go to register new customer
                if (newCustomer != null)                                        //if got a new customer,
                    customers.add(newCustomer);   //add a new customer into customers array list
            }

            //login
            else if (mainMenuChoice == 2) {
                String indexNumberOfUserString = loginMenu(customers, staffs);                      //get type of user (c,s) and the index number
                int indexNumberOfUser = Integer.parseInt(indexNumberOfUserString.substring(1));

                if (indexNumberOfUserString.charAt(0) == 'C')                                       //if customer login
                    if (customers.get(indexNumberOfUser).getFrozen())                                   //if customer acc is frozen, cannot login
                        System.out.println("\t\t\t\t\tYour account has been frozen, please contact staff for recovery.");
                    else {
                        //else go to customer menu
                        //successfully login as customer
                        //loginCustomer(customers.get(indexNumberOfUser));
                        return customers.get(indexNumberOfUser);
                    }

                else {                                                                              //if staff login
                    if (staffs.get(indexNumberOfUser).getFrozen())                                      //if staff is frozen, cannot login
                        System.out.println("\t\t\t\t\tYour account has been frozen, please contact staff for recovery.");
                    else {
                        //else go to staff menu
                        //successfully login as staff
                        //loginStaff(staffs, customers, indexNumberOfUser);
                        return staffs.get(indexNumberOfUser);
                    }
                }

            } else if (mainMenuChoice == 3) {       //forget password

                String targetEmail = "", targetPhoneNumber = "";
                boolean isCustomer = false;
                int indexNumber = -1;

                System.out.print("\t\t\t\tEnter your User ID: ");                       //user enter user id
                String targetUserID = scanner.nextLine().toUpperCase().trim();

                if (targetUserID.startsWith("C")) {                             //if user is customer, search from customer array
                    for (int loop = 0; loop < customers.size(); loop++) {
                        if (customers.get(loop).getUserID().equals(targetUserID)) {    //if found, record down email, phone
                            indexNumber = loop;
                            targetEmail = customers.get(loop).getEmail();
                            targetPhoneNumber = customers.get(loop).getPhoneNumber();
                            isCustomer = true;
                            break;
                        }
                    }
                } else if (targetUserID.startsWith("S")) {                      //if user is staff or admin, search from staff array
                    for (int loop = 0; loop < staffs.size(); loop++) {
                        if (staffs.get(loop).getUserID().equals(targetUserID)) {    //if found, record down email and phone number
                            indexNumber = loop;
                            targetEmail = staffs.get(loop).getEmail();
                            targetPhoneNumber = staffs.get(loop).getPhoneNumber();
                            isCustomer = false;
                            break;
                        }
                    }
                }

                if (indexNumber != -1) {
                    //System.out.println(targetEmail + targetPhoneNumber + isCustomer);   //test
                    System.out.print("\t\t\t\tEnter your email: ");
                    String answerEmail = scanner.nextLine().trim();             //user enter email
                    //enter email and phone number
                    System.out.print("\t\t\t\tEnter your phone number: ");              //user enter password
                    String answerPhoneNumber = scanner.nextLine().trim();


                    boolean valid;  //identity

                    //if the user is staff and if email and phone number is same as the user one, ask them security question
                    if (answerEmail.equals(targetEmail)) {
                        valid = answerPhoneNumber.equals(targetPhoneNumber);

                        if (!isCustomer) {
                            System.out.print("\t\t\t\t\n\t\t\t\t" + staffs.get(indexNumber).getSecurityQuestion() + ": ");
                            if (!scanner.nextLine().equalsIgnoreCase(staffs.get(indexNumber).getSecurityAnswer())) {
                                valid = false;
                            }
                        }

                    } else valid = false;


                    if (valid) {    //same, change password
                        System.out.println("\t\t\t\t\tIdentity verified!\n");

                        //enter password
                        boolean validPassword;

                        System.out.println("\t\t\t\t\tYour password should be at least 8 character long and" +
                                " consist of at least one numeric character, uppercase and lowercase letter.\n" +
                                "Example: MasaruSuki0809");
                        System.out.print("\t\t\t\tPassword: ");
                        String password1 = scanner.nextLine();                  //enter password

                        System.out.print("\t\t\t\tConfirm Password: ");
                        String password2 = scanner.nextLine();

                        if (password1.equals(password2) && !UserInfo.validPassword(password1)) {            //check password
                            System.out.print("\t\t\t\t\n\t\t\t\tYour password doesn't match the requirement!");
                            validPassword = false;
                        } else validPassword = true;

                        //if password doesnt match, enter again
                        while (!(password1.equals(password2)) || password1.isEmpty() || password2.isEmpty() || !validPassword) {
                            System.out.println("\t\t\t\t\t\nPlease confirm your password again!");
                            System.out.print("\t\t\t\tPassword: ");
                            password1 = scanner.nextLine();
                            //enter password twice
                            System.out.print("\t\t\t\tConfirm Password: ");
                            password2 = scanner.nextLine();

                            if (password1.equals(password2) && !UserInfo.validPassword(password1)) {            //check password
                                System.out.print("\t\t\t\t\n\t\t\t\tYour password doesn't match the requirement!");
                                validPassword = false;
                            } else validPassword = true;
                        }


                        customers.get(indexNumber).setPassword(password1);          //set password
                        System.out.print("\t\t\t\tPassword reset successfully!");

                    } else {          //not same, account will be freeze
                        if (isCustomer) {
                            customers.get(indexNumber).setFrozen(true);
                            System.out.println("\t\t\t\t\tYour account has been frozen, please contact staff for recovery.");
                        } else {
                            if (!staffs.get(indexNumber).isAdmin()) {               //if admin, no freeze else freeze
                                staffs.get(indexNumber).setFrozen(true);
                                System.out.println("\t\t\t\t\tYour account has been frozen, please contact admin for recovery.");
                            }else{
                                System.out.println("\t\t\t\t\tPlease try again.");
                            }
                        }

                    }
                } else System.out.println("\t\t\t\t\tNo result found...");

            } else if (mainMenuChoice == 4) {      //search for userid
                boolean isCustomer;
                System.out.print("\t\t\t\tEnter your email or phone number: ");
                String keyword = scanner.nextLine().trim();

                int indexNumber = findCustomerIndexNumber(customers, keyword, true);

                if (indexNumber == -1) {
                    indexNumber = findStaffIndexNumber(staffs, keyword, true);
                    isCustomer = false;
                } else isCustomer = true;

                if (indexNumber == -1) {
                    System.out.println("\t\t\t\t\tNo result found...");
                } else {
                    if (isCustomer)
                        System.out.println("\t\t\t\t\tYou User ID is " + customers.get(indexNumber).getUserID() + "!");
                    else System.out.println("\t\t\t\t\tYou User ID is " + staffs.get(indexNumber).getUserID() + "!");
                }


            } else break;
        } while (true);
        //return null if
        return null;
    }

    public static int mainMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean continueWhile = true;
        int menuChoice = 0;
        System.out.println("\t\t\t\t\t\n\n                   HI USER , ECOAT WELCOME YOU ! REMEMBER TO LOGIN IN ORDER TO MAKE BOOKING ^^ ");
        System.out.println("\t\t        =================================================================================");
        System.out.println("\t\t                          ( 1 ) - R E G I S T E R       ");
        System.out.println("\t\t                          ( 2 ) - L O G I N              ");
        System.out.println("\t\t                          ( 3 ) - F O R G E T  P A S S W O R D    ");
        System.out.println("\t\t                          ( 4 ) - S E A R C H  U S E R  I D              ");
        System.out.println("\t\t                          ( 5 ) - E X I T              ");
        System.out.println("\t\t        =================================================================================");
        System.out.print("\t\t\t\t                 Please enter your choice >> ");
        do {
            try {
                menuChoice = scanner.nextInt();     //scan the choice from user
                continueWhile = false;              //valid, so will not loop
            } catch (Exception ex) {

                scanner.nextLine();                 //will not prompt error and exit program if user input non int

            }
            if (continueWhile || (menuChoice < 1 || menuChoice > 5)) {     //if user enter invalid value

                System.out.println("\t\t\t\t\t\n\n                   -[ Invalid choice ! Please enter again ! ]-\n");
                System.out.println("\t\t\t\t\t\n\n                   HI USER , ECOAT WELCOME YOU ! REMEMBER TO LOGIN IN ORDER TO MAKE BOOKING ^^ ");
                System.out.println("\t\t        =================================================================================");
                System.out.println("\t\t                          ( 1 ) - R E G I S T E R       ");
                System.out.println("\t\t                          ( 2 ) - L O G I N              ");
                System.out.println("\t\t                          ( 3 ) - F O R G E T  P A S S W O R D    ");
                System.out.println("\t\t                          ( 4 ) - S E A R C H  U S E R  I D              ");
                System.out.println("\t\t                          ( 5 ) - E X I T              ");
                System.out.println("\t\t        =================================================================================");
                System.out.print("\t\t\t\t                 Please enter your choice >> ");
                continueWhile = true;                              //set to true to loop again

            }
        } while (continueWhile);                        //if true, input again
        return menuChoice;
    }

    private static String loginMenu(ArrayList<Customer> customers, ArrayList<Staff> staffs) {
        Scanner scanner = new Scanner(System.in);
        boolean userIDfound = false, passwordMatch = false, isCustomer;
        int attemptCount = 1;
        int indexNumber = -1;
        String userIDLogin, triedUserID = "", passwordLogin;

        do {


            do {
                try {
                    System.out.print("\n\t\t\t\t\n\t\t\t\tEnter your User ID: ");                     //enter userid
                    userIDLogin = scanner.nextLine().trim().toUpperCase();

                    isCustomer = userIDLogin.startsWith("C");

                    System.out.print("\t\t\t\t\n\t\t\t\tEnter your password: ");                      //enter password
                    passwordLogin = scanner.nextLine();
                    break;
                } catch (Exception ex) {
                    System.out.println("\t\t\t\t\t\n\t\t\t\tInvalid User ID and password!");
                    scanner.nextLine();
                }
            } while (true);

            try {
                //search for record
                if (userIDLogin.charAt(0) == 'C')                               //if type is customer, search for customer arraylist
                    for (int loop = 0; loop < customers.size(); loop++) {
                        if (userIDLogin.equals(customers.get(loop).getUserID())) {

                            userIDfound = true;
                            indexNumber = loop;

                            if (passwordLogin.equals(customers.get(loop).getPassword())) {
                                passwordMatch = true;


                                break;
                            } else passwordMatch = false;
                        } else userIDfound = false;
                    }
                else if (userIDLogin.charAt(0) == 'S')                          //if type is staff, search for staff arraylist
                {
                    for (int loop = 0; loop < staffs.size(); loop++) {
                        if (userIDLogin.equals(staffs.get(loop).getUserID())) {

                            userIDfound = true;

                            indexNumber = loop;
                            if (passwordLogin.equals(staffs.get(loop).getPassword())) {
                                passwordMatch = true;


                                break;
                            } else passwordMatch = false;
                        } else userIDfound = false;
                    }
                } else {                                                          //if not both, go back to input again
                    passwordMatch = false;
                    userIDfound = false;
                }

                if (triedUserID.equals(userIDLogin)) {      //if the previous user id is same as current one
                    attemptCount++;                         //add 1 to attemptcount
                    //System.out.println(attemptCount);       //test
                    if (!passwordMatch && attemptCount == 3) {                   //if password not match and attempt = 3, freeze acc
                        if (isCustomer) {
                            customers.get(indexNumber).setFrozen(true);
                            break;
                        } else if (!staffs.get(indexNumber).isAdmin()) {
                            staffs.get(indexNumber).setFrozen(true);
                            break;
                        }
                    }
                } else attemptCount = 1;


            } catch (Exception exception) {
                System.out.println("\t\t\t\t\tPlease enter your User ID and password!!");
                passwordMatch = false;
                userIDfound = false;
            }
            triedUserID = userIDLogin;

            if (!userIDfound || !passwordMatch) {
                // System.out.println("\t\t\t\t\tuseridfound =" + userIDfound + " passwordmatch = " + passwordMatch);        //test
                System.out.println("\t\t\t\t\t\n\t\t\tInvalid userID or password!");       //if invalid input, prompt error
            }

        } while (!passwordMatch || !userIDfound);


        return String.format("%c%d", userIDLogin.charAt(0), indexNumber);   //format = "C2" / "S12"
    }

    public static void loginCustomer(Customer customer) {
        Scanner scanner = new Scanner(System.in);

        int userChoice = 0;
        boolean continueWhile = true;

        do {
            Main.functionHeader(4);

            System.out.print("\t\t\t\t\t");
            for (int i = 0; i < 35; i++) System.out.print('=');
            System.out.println("\n\t\t\t\t\t1.View details");
            System.out.println("\t\t\t\t\t2.Edit details");
            System.out.println("\t\t\t\t\t3.Exit");
            System.out.print("\t\t\t\t\t");
            for (int i = 0; i < 35; i++) System.out.print('=');
            System.out.print("\t\t\t\t\n\t\t\t\tYour choice: ");


            do {
                try {
                    userChoice = scanner.nextInt();     //scan the choice from user
                    continueWhile = false;              //valid, so will not loop
                } catch (Exception ex) {

                    scanner.nextLine();                 //will not prompt error and exit program if user input non int

                }
                if (continueWhile || (userChoice < 1 || userChoice > 3)) {     //if user enter invalid value
                    System.out.println("\n\t\t\t\t\tInvalid choice!");
                    System.out.print("\t\t\t\t\t");
                    for (int i = 0; i < 35; i++) System.out.print('=');
                    System.out.println("\n\t\t\t\t\t1.View details");
                    System.out.println("\t\t\t\t\t2.Edit details");
                    System.out.println("\t\t\t\t\t3.Exit");
                    System.out.print("\t\t\t\t\t");
                    for (int i = 0; i < 35; i++) System.out.print('=');
                    System.out.print("\t\t\t\t\n\t\t\t\tYour choice: ");
                    continueWhile = true;                              //set to true to loop again

                }
            } while (continueWhile);                        //if true, input again
            if (userChoice == 1) {
                System.out.println("\n\t\t\t\t\tYour details");
                System.out.print("\t\t\t\t\t");

                for (int i = 0; i < 35; i++) System.out.print('=');
                System.out.println("\n"+customer);

                System.out.print("\t\t\t\t\t");
                for (int i = 0; i < 35; i++) System.out.print('=');

            } else if (userChoice == 2) {
                customer.editInfo(true);
            } else continueWhile = true;

        } while (!continueWhile);     //loop forever until user want to logout
    }

    public static void loginStaff(ArrayList<Staff> staffs, ArrayList<Customer> customers, int currentLoginIndex) {
        Scanner scanner = new Scanner(System.in);
        int userChoice = 0;
        boolean continueWhile = true, continueLogIn = true;


        do {
            Main.functionHeader(4);
            System.out.print("\t\t\t\t\t");
            for (int i = 0; i < 35; i++) System.out.print('=');

            System.out.println("\n\t\t\t\t\t1.View your details");
            System.out.println("\t\t\t\t\t2.Edit your details");
            System.out.println("\t\t\t\t\t3.View customer's details");
            System.out.println("\t\t\t\t\t4.Edit customer's details");
            System.out.println("\t\t\t\t\t5.Delete customer's account");
            System.out.println("\t\t\t\t\t6.Create staff account");
            System.out.println("\t\t\t\t\t7.Freeze an account");
            System.out.println("\t\t\t\t\t8.Unfreeze an account");
            if (staffs.get(currentLoginIndex).isAdmin()) {
                System.out.println("\t\t\t\t\t9.Promote staff to admin");
                System.out.println("\t\t\t\t\t10.Exit");
            } else System.out.println("\t\t\t\t\t9.Exit");
            System.out.print("\t\t\t\t\t");
            for (int i = 0; i < 35; i++) System.out.print('=');

            System.out.print("\n\t\t\t\tYour choice: ");
            do {
                try {
                    userChoice = scanner.nextInt();     //scan the choice from user
                    continueWhile = false;              //valid, so will not loop
                } catch (Exception ex) {

                    scanner.nextLine();                 //will not prompt error and exit program if user input non int

                }
                if (continueWhile || (userChoice < 1 || userChoice > 10)) {     //if user enter invalid value
                    System.out.println("\n\t\t\t\t\tPlease enter a valid value!");
                    System.out.print("\t\t\t\t\t");
                    for (int i = 0; i < 35; i++) System.out.print('=');
                    System.out.println("\n\t\t\t\t\t1.View your details");
                    System.out.println("\t\t\t\t\t2.Edit your details");
                    System.out.println("\t\t\t\t\t3.View customer's details");
                    System.out.println("\t\t\t\t\t4.Edit customer's details");
                    System.out.println("\t\t\t\t\t5.Delete customer's account");
                    System.out.println("\t\t\t\t\t6.Create staff account");
                    System.out.println("\t\t\t\t\t7.Freeze an account");
                    System.out.println("\t\t\t\t\t8.Unfreeze an account");
                    if (staffs.get(currentLoginIndex).isAdmin()) {
                        System.out.println("\t\t\t\t\t9.Promote staff to admin");
                        System.out.println("\t\t\t\t\t10.Change admin password");
                        System.out.println("\t\t\t\t\t11.Exit");
                    } else {
                        System.out.println("\t\t\t\t\t9.Exit");
                    }
                    System.out.print("\t\t\t\t\t");
                    for (int i = 0; i < 35; i++) System.out.print('=');
                    System.out.print("\t\t\t\t\n\t\t\t\tYour choice: ");
                    continueWhile = true;                              //set to true to loop again

                }
            } while (continueWhile);

            //view your details
            if (userChoice == 1) {
                System.out.println("\n\t\t\t\t\tYour details");
                System.out.print("\t\t\t\t\t");
                for (int i = 0; i < 35; i++) System.out.print('=');
                System.out.println("\t\t\t\t\t\n"+staffs.get(currentLoginIndex));
                System.out.println("\t\t\t\t\tPassword       : " + staffs.get(currentLoginIndex).getPassword());
                System.out.println("\t\t\t\t\tSecurity question: " + staffs.get(currentLoginIndex).getSecurityQuestion());
                System.out.println("\t\t\t\t\tSecurity answer: " + staffs.get(currentLoginIndex).getSecurityAnswer());

                System.out.print("\t\t\t\t\t");
                for (int i = 0; i < 35; i++) System.out.print('=');

                System.out.println("\n");
            }

            //edit your details
            else if (userChoice == 2) staffs.set(currentLoginIndex, staffs.get(currentLoginIndex).editInfo());


                //View customer details
            else if (userChoice == 3) {
                int customerIndex;
                scanner.nextLine();
                do {

                    System.out.print("\t\t\t\t\n\t\t\t\tEnter User ID, email or phone number: ");
                    String keyword = scanner.nextLine();

                    customerIndex = findCustomerIndexNumber(customers, keyword, false);

                    if (customerIndex == -1) {      //if result not found
                        if (!UserMain.returnYorNmethod("\nNo result...\nDo you want to search again? "))
                            break;

                    }
                } while (customerIndex == -1);

                if (customerIndex != -1) {
                    System.out.println("\n\t\t\t\t\tCustomer's details");
                    System.out.print("\t\t\t\t\t");
                    for (int i = 0; i < 35; i++) System.out.print('=');
                    System.out.println("\n"+customers.get(customerIndex));
                    System.out.print("\t\t\t\t\t");
                    for (int i = 0; i < 35; i++) System.out.print('=');

                }
            }


            //edit customer details
            else if (userChoice == 4) {

                int customerIndex;
                String keyword;
                scanner.nextLine();
                do {

                    System.out.print("\t\t\t\tEnter User ID, email or phone number: ");
                    keyword = scanner.nextLine();

                    customerIndex = findCustomerIndexNumber(customers, keyword, false);

                    if (customerIndex == -1) {      //if result not found
                        if (!returnYorNmethod("\n\t\t\t\t\tNo result...\n\t\t\t\t\tDo you want to search again? "))
                            break;

                    }
                } while (customerIndex == -1);

                if (customerIndex != -1) {

                    customers.get(customerIndex).editInfo(false);
                }

            }

            //delete customer
            else if (userChoice == 5) {

                int customerIndex;
                String keyword;
                scanner.nextLine();
                do {

                    System.out.print("\t\t\t\tEnter User ID, email or phone number: ");
                    keyword = scanner.nextLine();

                    customerIndex = findCustomerIndexNumber(customers, keyword, false);

                    if (customerIndex == -1) {      //if result not found
                        if (!returnYorNmethod("\n\t\t\t\t\tNo result...\n\t\t\t\t\tDo you want to search again? "))
                            break;

                    }
                } while (customerIndex == -1);

                if (customerIndex != -1) {
                    System.out.println("\n\t\t\t\t\tCustomer's details");
                    System.out.print("\t\t\t\t\t");
                    for (int i = 0; i < 35; i++) System.out.print('=');
                    System.out.println("\n"+customers.get(customerIndex));
                    System.out.print("\t\t\t\t\t");
                    for (int i = 0; i < 35; i++) System.out.print('=');
                    if (returnYorNmethod("\n\t\t\t\t\tAre you sure you want to delete this customer? ")) {

                        customers.remove(customerIndex);
                        System.out.println("\t\t\t\t\tThis account has been deleted!");

                    } else System.out.println("\t\t\t\t\tThis account has not been deleted...");

                }
            }

            //create staff acc
            else if (userChoice == 6) {


                Staff newStaff = registerStaff(customers, staffs);

                if (newStaff != null) {
                    staffs.add(newStaff);
                    System.out.println("\t\t\t\t\tYour User ID: " + newStaff.getUserID());
                }


                //System.out.println("\t\t\t\t\tNEW: " + staffs.get(staffs.size() - 1));  //test

            }

            //unfreeze and freeze acc
            else if (userChoice == 7 || userChoice == 8) {
                int accountIndexNumber;
                String keyword;
                scanner.nextLine();
                boolean isCustomer = false;

                do {

                    System.out.print("\n\t\t\t\t\tEnter User ID, email or phone number: ");         //user enter keyword
                    keyword = scanner.nextLine();


                    accountIndexNumber = findCustomerIndexNumber(customers, keyword, false);        //find from customer arraylist
                    if (accountIndexNumber != -1) {                                                                 //if found, set iscustomer to true and go to freeze/unfreeze
                        isCustomer = true;
                        break;
                    } else {                                                                                        //if not found, find from staff arraylist
                        accountIndexNumber = findStaffIndexNumber(staffs, keyword, false);

                    }

                    if (accountIndexNumber != -1) {                                                                 //if found, set iscustomer to false and go to freeze
                        break;
                    } else {                                                                                     //if not found, ask user want to search again or not
                        if (!returnYorNmethod("\n\t\t\t\tNo result...\n\t\t\t\tDo you want to search again? "))                     //if dont want to search, break; else, loop
                            break;

                    }
                } while (true);


                //System.out.println("\t\t\t\t\tIsCustomer = " + isCustomer + "\nindex=" + accountIndexNumber); //test


                if (accountIndexNumber != -1) { //if account found

                    if (!(isCustomer == false && currentLoginIndex == accountIndexNumber))//the account is not staff him/herself
                    {
                        System.out.println("\t\t\t\t\tDetails");
                        System.out.print("\t\t\t\t\t");
                        for (int i = 0; i < 35; i++) System.out.print('=');
                        if (isCustomer) System.out.println("\n"+customers.get(accountIndexNumber));
                        else System.out.println("\n"+staffs.get(accountIndexNumber));
                        System.out.print("\t\t\t\t\t");
                        for (int i = 0; i < 35; i++) System.out.print('=');

                        if (returnYorNmethod(String.format("\n\t\t\t\tAre you sure to %sfreeze this account?", userChoice == 7 ? "" : "un"))) {   //ask for user decision, if confirm want to do the action,

                            if (isCustomer) {                                                                                       //if the target is customer


                                if (customers.get(accountIndexNumber).getFrozen()) {     //if the customer is frozen
                                    if (userChoice == 7) {                          //user want to freeze customer
                                        System.out.println("\t\t\t\t\tThis account is frozen.");
                                    } else {                                        //user want to unfreeze customer
                                        System.out.println("\t\t\t\t\tThis account has been unfreeze");
                                        customers.get(accountIndexNumber).setFrozen(false);
                                    }
                                } else {                                            //if the customer is not frozen
                                    if (userChoice == 7) {                             //user want to freeze customer
                                        System.out.println("\t\t\t\t\tThis account has been freeze");
                                        customers.get(accountIndexNumber).setFrozen(true);
                                    } else                                        //user want to unfreeze customer
                                        System.out.println("\t\t\t\t\tThis account is not frozen.");
                                }


                            } else {                                                //if target is staff

                                if (staffs.get(currentLoginIndex).isAdmin() && !staffs.get(accountIndexNumber).isAdmin()) { //if user is admin and frozen acc owner is staff, aka valid change
                                    if (staffs.get(accountIndexNumber).getFrozen()) {     //if the staff is frozen


                                        if (userChoice == 7) {                          //user want to freeze staff
                                            System.out.println("\t\t\t\t\tThis account is frozen.");
                                        } else {                                        //user want to unfreeze staff


                                            System.out.println("\t\t\t\t\tThis account has been unfreeze");
                                            staffs.get(accountIndexNumber).setFrozen(false);


                                        }
                                    } else {                                            //if the staff is not frozen
                                        if (userChoice == 7) {                             //user want to freeze staff
                                            System.out.println("\t\t\t\t\tThis account has been freeze");
                                            staffs.get(accountIndexNumber).setFrozen(true);
                                        } else                                        //user want to unfreeze staff
                                            System.out.println("\t\t\t\t\tThis account is not frozen.");
                                    }
                                } else                                                                                      //if user cannot freeze/unfreeze acc
                                    System.out.printf("\t\t\t\t\tYou don't have the right to %sfreeze this account...\n", userChoice == 7 ? "" : "un");
                            }
                        } else {
                            System.out.println("\t\t\t\t\tYou didn't take any action...");
                        }
                    } else {
                        System.out.println("\t\t\t\t\tYou CANNOT freeze or unfreeze your account!");
                    }
                }
            }


            //logout for staff and promote staff to admin for admin
            else if (userChoice == 9) {
                if (!staffs.get(currentLoginIndex).isAdmin()) {
                    continueLogIn = false;

                } else {
                    int staffIndex;
                    String keyword;
                    scanner.nextLine();
                    do {

                        System.out.print("\n\t\t\t\tEnter User ID, email or phone number: ");
                        keyword = scanner.nextLine();

                        staffIndex = findStaffIndexNumber(staffs, keyword, false);

                        if (staffIndex == -1) {      //if result not found
                            if (!returnYorNmethod("\n\t\t\t\tNo result...\n\t\t\t\tDo you want to search again? "))
                                break;


                        }
                    } while (staffIndex == -1);
                    if (staffIndex != -1) {
                        System.out.println("\n\t\t\t\t\tDetails");
                        System.out.print("\t\t\t\t\t");
                        for (int i = 0; i < 35; i++) System.out.print('=');
                        System.out.println("\t\t\t\t\t\n"+staffs.get(staffIndex));
                        System.out.print("\t\t\t\t\t");
                        for (int i = 0; i < 35; i++) System.out.print('=');


                        if (returnYorNmethod("\n\t\t\t\t\tAre you sure to promote this staff to admin? (YOU CANNOT UNDO THIS ACTION!) ")) {
                            if (staffs.get(staffIndex).isAdmin()) {
                                System.out.println("\t\t\t\t\tThe account owner is already an admin!");
                            } else {
                                System.out.print("\t\t\t\tPlease enter your password to process: ");
                                String password = scanner.nextLine();
                                if (password.equals(staffs.get(currentLoginIndex).getPassword())) {      //if password is correct
                                    staffs.get(staffIndex).setAdmin(true);
                                    System.out.println("\t\t\t\t\tThis staff has been promoted to admin");
                                } else {                                                                   //if password is not correct
                                    System.out.println("\t\t\t\t\tThis is not the correct password!\n\t\t\t\t\tDue to security propose, system has exit from user module!");
                                    break;
                                }
                            }

                        }
                    }
                }

            }
            //invalid value for staff and logout for admin
            else {
                if (!staffs.get(currentLoginIndex).isAdmin()) {         //is staff, not admin
                    System.out.println("\n\t\t\t\t\tPlease enter a valid value!");
                } else {
                    continueLogIn = false;
                }
            }


        } while (continueLogIn);
    }

    public static Customer registerCustomer(ArrayList<Customer> customers, ArrayList<Staff> staffs) {

        Scanner scanner = new Scanner(System.in);
        Customer newCustomer = new Customer();

        Main.functionHeader(6);

        //enter name
        newCustomer.setName(UserInfo.inputName("\t\t\t\t\tEnter your first name: ", "\t\t\t\t\tEnter your last name: "));

        //enter email and phone number
        newCustomer.setEmail(UserInfo.inputEmail("\t\t\t\t\tEnter your email: "));

        for (int loop = 0; loop < customers.size(); loop++) {
            if (customers.get(loop).getEmail().equals(newCustomer.getEmail())) {
                System.out.println("\t\t\t\t\tEach person can only register for one account!");
                System.out.println("\t\t\t\t\tReminder: Your User ID is " + customers.get(loop).getUserID());
                return null;
            }
        }

        for (int loop = 0; loop < staffs.size(); loop++) {
            if (staffs.get(loop).getEmail().equals(newCustomer.getEmail())) {
                System.out.println("\t\t\t\t\tEach person can only register for one account!");
                System.out.println("\t\t\t\t\tReminder: Your User ID is " + staffs.get(loop).getUserID());
                return null;
            }
        }

        newCustomer.setPhoneNumber(UserInfo.inputPhoneNumber("\t\t\t\t\tEnter your phone number: "));

        for (int loop = 0; loop < customers.size(); loop++) {
            if (customers.get(loop).getPhoneNumber().equals(newCustomer.getPhoneNumber())) {
                System.out.println("\t\t\t\t\tEach person can only register for one account!");
                System.out.println("\t\t\t\t\tReminder: Your User ID is " + customers.get(loop).getUserID());
                return null;
            }
        }

        for (int loop = 0; loop < staffs.size(); loop++) {
            if (staffs.get(loop).getPhoneNumber().equals(newCustomer.getPhoneNumber())) {
                System.out.println("\t\t\t\t\tEach person can only register for one account!");
                System.out.println("\t\t\t\t\tReminder: Your User ID is " + staffs.get(loop).getUserID());
                return null;
            }
        }


        //enter password
        boolean validPassword;

        System.out.println("\t\t\t\t\tYour password should be at least 8 character long and" +
                " consist of at least one numeric character, uppercase and lowercase letter.\n" +
                "\t\t\t\t\tExample: MasaruSuki0809");
        System.out.print("\t\t\t\tPassword: ");
        String password1 = scanner.nextLine();

        System.out.print("\t\t\t\tConfirm Password: ");
        String password2 = scanner.nextLine();

        if (password1.equals(password2) && !UserInfo.validPassword(password1)) {
            System.out.print("\n\t\t\t\tYour password doesn't match the requirement!");
            validPassword = false;
        } else validPassword = true;

        //if password doesnt match, enter again
        while (!(password1.equals(password2)) || password1.isEmpty() || password2.isEmpty() || !validPassword) {
            System.out.println("\n\t\t\t\t\tPlease confirm your password again!");
            System.out.print("\t\t\t\tPassword: ");
            password1 = scanner.nextLine();

            System.out.print("\t\t\t\tConfirm Password: ");
            password2 = scanner.nextLine();

            if (password1.equals(password2) && !UserInfo.validPassword(password1)) {
                System.out.print("\n\t\t\t\tYour password doesn't match the requirement!");
                validPassword = false;
            } else validPassword = true;
        }
        newCustomer.setPassword(password1);


        //create userID, user ID format is CDDMMYYXXX
        LocalDateTime localDateTime = LocalDateTime.now();

        int userIDIndex;

        int year = localDateTime.getYear() % 100;       //get todays year
        int month = localDateTime.getMonthValue();      //get todays month
        int dayOfMonth = localDateTime.getDayOfMonth(); //get todays day


        int previousUserIDDay = Integer.parseInt(customers.get(customers.size() - 1).getUserID().substring(1, 3)); //get day in previous userid
        int previousUserIDMonth = Integer.parseInt(customers.get(customers.size() - 1).getUserID().substring(3, 5)); //get month
        int previousUserIDYear = Integer.parseInt(customers.get(customers.size() - 1).getUserID().substring(5, 7)); //get year

        if (previousUserIDDay == dayOfMonth &&          //if created date of this user id is same as the previous one
                previousUserIDMonth == month &&
                previousUserIDYear == year)

            userIDIndex = Integer.parseInt(customers.get(customers.size() - 1).getUserID().substring(7)) + 1;    //index + 1

        else userIDIndex = 1;       //if not, index reset to 1

        newCustomer.setUserID(String.format("C%02d%02d%02d%03d", dayOfMonth, month, year, userIDIndex));    //create user id based on the format
        System.out.println("\t\t\t\t\tRegister successfully!");
        System.out.println("\t\t\t\t\tYour User ID is " + newCustomer.getUserID());

        return newCustomer;
    }


    public static Staff registerStaff(ArrayList<Customer> customers, ArrayList<Staff> staffs) {
//ic address isadmin userid name email phonenumber password

        Staff newStaff = new Staff();
        Scanner scanner = new Scanner(System.in);
        Main.functionHeader(6);
        newStaff.setName(UserInfo.inputName("\t\t\t\t\tEnter your first name: ", "\t\t\t\t\tEnter your last name: "));
        newStaff.setIcNumber(Staff.inputICNumber("\t\t\t\t\tEnter your IC number: "));


        for (int loop = 0; loop < staffs.size(); loop++) {
            if (staffs.get(loop).getIcNumber().equals(newStaff.getIcNumber())) {
                System.out.println("\t\t\t\t\tEach person can only register for one account!");
                System.out.println("\t\t\t\t\tReminder: Your User ID is " + staffs.get(loop).getUserID());
                return null;
            }
        }

        newStaff.setEmail(UserInfo.inputEmail("\t\t\t\t\tEnter your email: "));


        for (int loop = 0; loop < customers.size(); loop++) {
            if (customers.get(loop).getEmail().equals(newStaff.getEmail())) {
                System.out.println("\t\t\t\t\tEach person can only register for one account!");
                System.out.println("\t\t\t\t\tReminder: Your User ID is " + customers.get(loop).getUserID());
                return null;
            }
        }

        for (int loop = 0; loop < staffs.size(); loop++) {
            if (staffs.get(loop).getEmail().equals(newStaff.getEmail())) {
                System.out.println("\t\t\t\t\tEach person can only register for one account!");
                System.out.println("\t\t\t\t\tReminder: Your User ID is " + staffs.get(loop).getUserID());
                return null;
            }
        }

        newStaff.setPhoneNumber(UserInfo.inputPhoneNumber("\t\t\t\t\tEnter your phone number: "));

        for (int loop = 0; loop < customers.size(); loop++) {
            if (customers.get(loop).getPhoneNumber().equals(newStaff.getPhoneNumber())) {
                System.out.println("\t\t\t\t\tEach person can only register for one account!");
                System.out.println("\t\t\t\t\tReminder: Your User ID is " + customers.get(loop).getUserID());
                return null;
            }
        }

        for (int loop = 0; loop < staffs.size(); loop++) {
            if (staffs.get(loop).getPhoneNumber().equals(newStaff.getPhoneNumber())) {
                System.out.println("\t\t\t\t\tEach person can only register for one account!");
                System.out.println("\t\t\t\t\tReminder: Your User ID is " + staffs.get(loop).getUserID());
                return null;
            }
        }

        System.out.print("\t\t\t\t\tEnter your home address: ");

        String address = scanner.nextLine();
        newStaff.setAddress(address);

        //enter password
        boolean validPassword;

        System.out.println("\t\t\t\t\tYour password should be at least 8 character long and" +
                " consist of at least one numeric character, uppercase and lowercase letter.\n" +
                "\t\t\t\t\tExample: MasaruSuki0809");
        System.out.print("\t\t\t\t\tPassword: ");
        String password1 = scanner.nextLine();

        System.out.print("\t\t\t\t\tConfirm Password: ");
        String password2 = scanner.nextLine();

        if (password1.equals(password2) && !UserInfo.validPassword(password1)) {
            System.out.print("\t\t\t\t\n\t\t\t\t\tYour password doesn't match the requirement!");
            validPassword = false;
        } else validPassword = true;

        //if password doesnt match, enter again
        while (!(password1.equals(password2)) || password1.isEmpty() || password2.isEmpty() || !validPassword) {
            System.out.println("\n\t\t\t\t\tPlease confirm your password again!");
            System.out.print("\t\t\t\t\tPassword: ");
            password1 = scanner.nextLine();

            System.out.print("\t\t\t\t\tConfirm Password: ");
            password2 = scanner.nextLine();

            if (password1.equals(password2) && !UserInfo.validPassword(password1)) {
                System.out.print("\t\t\t\t\n\t\t\t\t\tYour password doesn't match the requirement!");
                validPassword = false;
            } else validPassword = true;
        }

        newStaff.setPassword(password1);

        System.out.print("\t\t\t\t\tSet your security question (Example: \"What is your favorite country\"): ");
        newStaff.setSecurityQuestion(scanner.nextLine());

        System.out.print("\t\t\t\t\tSet your security answer (Example: \"Thailand\"): ");
        newStaff.setSecurityAnswer(scanner.nextLine());

        Staff.addStaffIndexNumber();
        newStaff.setAdmin(false);

        newStaff.setUserID(String.format("%s%04d", "S", Staff.getStaffIndexNumber()));


        System.out.println("\t\t\t\t\tCreated successfully!");

        return newStaff;

    }

    public static boolean returnYorNmethod(String question) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(question);
        do {

            try {

                char answer = scanner.nextLine().toUpperCase().charAt(0);


                while (answer != 'Y' && answer != 'N') {
                    System.out.print("\t\t\t\tInvalid answer! Please enter Y or N!: ");
                    answer = scanner.nextLine().toUpperCase().charAt(0);

                }
                return answer == 'Y';
            } catch (Exception exception) {
                System.out.print("\t\t\t\tInvalid answer! Please enter Y or N!: ");
            }
        } while (true);
    }

    public static int findCustomerIndexNumber(ArrayList<Customer> customers, String keyword, boolean searchForUserID) {
        int indexNumber = -1;

        if (keyword.isEmpty())
            return -1;
        else if (keyword.contains("@")) {       //email
            for (int loop = 0; loop < customers.size(); loop++) {
                if (keyword.equalsIgnoreCase(customers.get(loop).getEmail())) {
                    indexNumber = loop;
                    break;
                }
            }
        } else if (keyword.startsWith("01")) {    //phone number
            for (int loop = 0; loop < customers.size(); loop++) {
                if (keyword.equalsIgnoreCase(customers.get(loop).getPhoneNumber())) {
                    indexNumber = loop;
                    break;
                }
            }
        } else {             //user id
            if (!searchForUserID)
                for (int loop = 0; loop < customers.size(); loop++) {
                    if (keyword.equalsIgnoreCase(customers.get(loop).getUserID())) {
                        indexNumber = loop;
                        break;
                    }
                }
        }

        return indexNumber;
    }

    public static int findStaffIndexNumber(ArrayList<Staff> staffs, String keyword, boolean searchForUserID) {
        int indexNumber = -1;

        if (keyword.isEmpty())
            return -1;
        else if (keyword.contains("@")) {       //email
            for (int loop = 0; loop < staffs.size(); loop++) {
                if (keyword.equalsIgnoreCase(staffs.get(loop).getEmail())) {
                    indexNumber = loop;
                    break;
                }
            }
        } else if (keyword.startsWith("01")) {    //phone number
            for (int loop = 0; loop < staffs.size(); loop++) {
                if (keyword.equalsIgnoreCase(staffs.get(loop).getPhoneNumber())) {
                    indexNumber = loop;
                    break;
                }
            }
        } else {             //user id

            if (!searchForUserID)    //if want to search for user id, stop searching from user id
                for (int loop = 0; loop < staffs.size(); loop++) {
                    if (keyword.equalsIgnoreCase(staffs.get(loop).getUserID())) {
                        indexNumber = loop;
                        break;
                    }
                }
        }

        return indexNumber;
    }
}
