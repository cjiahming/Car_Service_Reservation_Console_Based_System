import java.util.Scanner;

public class Customer extends UserInfo {
    private boolean isCustomer =true;


    public Customer() {

        this("",
                new Name("", ""),
                "",
                "",
                "");
    }

    public Customer(String userID, Name name, String email, String phoneNumber, String password) {

        super(userID, name, email, phoneNumber, password);
    }

    public String toString() {
        return super.toString();
    }

    public void editInfo(boolean editingSelfDetails) {

        Scanner scanner = new Scanner(System.in);
        System.out.printf("\n\t\t\t\t\t%s details\n", editingSelfDetails ? "Your" : "Customer's");

        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');
        System.out.println("\n"+this);
        if (editingSelfDetails)
            System.out.println("\t\t\t\t\tPassword       : " + getPassword());
        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');

        System.out.println("\n");

        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');
        System.out.println("\n\t\t\t\t\t1. Name"); //2
        System.out.println("\t\t\t\t\t2. Email"); //3
        System.out.println("\t\t\t\t\t3. Phone number"); //5
        if (editingSelfDetails)
            System.out.println("\t\t\t\t\t4. Password"); //7
        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');
        System.out.print("\n\t\t\t\t\tSelect which details you want to edit (Example: 1 3): ");
        String userEditSelection = scanner.nextLine().trim();


        int choiceCode = editChoiceCode(userEditSelection, editingSelfDetails ? 4 : 3);

        while (choiceCode == 0) {
            System.out.println("\n\t\t\t\t\tInvalid format has been detected, please follow the format! (Example: 1 3)"); //2
            System.out.print("\t\t\t\t\t");
            for (int i = 0; i < 35; i++) System.out.print('=');
            System.out.println("\n\t\t\t\t\t1. Name"); //2
            System.out.println("\t\t\t\t\t2. Email"); //3
            System.out.println("\t\t\t\t\t3. Phone number"); //5
            if (editingSelfDetails)
                System.out.println("\t\t\t\t\t4. Password"); //7
            System.out.print("\t\t\t\t\t");
            for (int i = 0; i < 35; i++) System.out.print('=');

            System.out.print("\n\t\t\t\t\tSelect which details you want to edit (Example: 1 3): ");
            userEditSelection = scanner.nextLine().trim();

            choiceCode = editChoiceCode(userEditSelection.trim(), editingSelfDetails ? 4 : 3);

        }
        String passwordNew1,emailNew,phoneNumberNew;
        Name nameNew;
        //name
        if (choiceCode % 2 == 0) {

            if (editingSelfDetails) {
                nameNew = inputName("\t\t\t\t\tEnter your new first name: ", "\t\t\t\t\tEnter your new last name: ");


            } else {
                nameNew = inputName("\t\t\t\t\tEnter customer's new first name: ", "\t\t\t\t\tEnter customer's new last name: ");
            }

        } else {
            nameNew = getName();
        }


        //email
        if (choiceCode % 3 == 0) {

            if (editingSelfDetails) emailNew = inputEmail("\t\t\t\t\tEnter your new email: ");
            else emailNew = inputEmail("\t\t\t\t\tEnter customer's new email: ");


        } else emailNew = getEmail();

        //phone number
        if (choiceCode % 5 == 0) {

            if (editingSelfDetails) phoneNumberNew = inputPhoneNumber("\t\t\t\t\tEnter your new phone number: ");
            else phoneNumberNew = inputPhoneNumber("\t\t\t\t\tEnter customer's new phone number: ");


        } else phoneNumberNew = getPhoneNumber();

        //password
        if (choiceCode % 7 == 0) {
            boolean validPassword;

            System.out.println("\t\t\t\t\tYour password should be at least 8 character long and" +
                    " consist of at least one numeric character, uppercase and lowercase letter.\n" +
                    "\t\t\t\t\tExample: MasaruSuki0809");
            System.out.print("\t\t\t\t\tPassword: ");
            passwordNew1 = scanner.nextLine();

            System.out.print("\t\t\t\t\tConfirm Password: ");
            String passwordNew2 = scanner.nextLine();

            if (passwordNew1.equals(passwordNew2) && !UserInfo.validPassword(passwordNew1)) {
                System.out.print("\n\t\t\t\t\tYour password doesn't match the requirement!");
                validPassword = false;
            } else validPassword = true;

            //if password doesnt match, enter again
            while (!(passwordNew1.equals(passwordNew2)) || passwordNew1.isEmpty() || passwordNew2.isEmpty() || !validPassword) {
                System.out.println("\n\t\t\t\t\tPlease confirm your password again!");
                System.out.print("Password: ");
                passwordNew1 = scanner.nextLine();

                System.out.print("\t\t\t\t\tConfirm Password: ");
                passwordNew2 = scanner.nextLine();

                if (passwordNew1.equals(passwordNew2) && !UserInfo.validPassword(passwordNew1)) {
                    System.out.print("\n\t\t\t\t\tYour password doesn't match the requirement!");
                    validPassword = false;
                } else validPassword = true;
            }

        } else passwordNew1 = getPassword();


        Customer newCustomer = new Customer(getUserID(),nameNew,emailNew,phoneNumberNew,passwordNew1);


        System.out.printf("\n\t\t\t\t\t%s new details\n", editingSelfDetails ? "Your" : "Customer's");
        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');

        System.out.println("\n"+newCustomer);
        if (editingSelfDetails)
            System.out.println("\t\t\t\t\tPassword       : " + passwordNew1);
        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');


        if (UserMain.returnYorNmethod("\n\t\t\t\t\tConfirm save your changes? ")) {

            setName(nameNew);
            setEmail(emailNew);
            setPhoneNumber(phoneNumberNew);
            setPassword(passwordNew1);

            System.out.println("\t\t\t\t\tChanges have been saved!");

        } else {
            System.out.println("\t\t\t\t\tChanges have not been saved...");
        }
    }

}