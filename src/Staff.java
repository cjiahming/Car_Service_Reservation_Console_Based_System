import java.util.Scanner;

public class Staff extends UserInfo {
    private String icNumber;
    private String address;
    private boolean isAdmin; //admin higher
    private String securityQuestion;
    private String securityAnswer;
    private static int staffIndexNumber = 3;

    public Staff() {
        this("", ""
                , false, "", "", "", new Name("", ""), "",
                "", "");
    }

    public Staff(String icNumber, String address, boolean isAdmin, String securityQuestion, String securityAnswer
            , String userID, Name name, String email, String phoneNumber, String password) {

        super(userID, name, email, phoneNumber, password);
        this.icNumber = icNumber;
        this.address = address;
        this.isAdmin = isAdmin;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public static int getStaffIndexNumber() {
        return staffIndexNumber;
    }

    public String getIcNumber() {
        return icNumber;
    }

    public String getAddress() {
        return address;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public static void addStaffIndexNumber() {
        staffIndexNumber++;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static String inputICNumber(String question) {
        Scanner scanner = new Scanner(System.in);
        boolean validation = true;
        String ic;

        do {
            if (!validation) System.out.println("\n\t\t\t\t\tInvalid format, please try again!");

            System.out.print(question);
            ic = scanner.nextLine().trim();

            if (ic.length() == 12) {
                for (int loop = 0; loop < 12; loop++) {
                    if (Character.isDigit(ic.charAt(loop))) {
                        validation = true;
                    } else {
                        validation = false;
                        break;
                    }
                }
            } else validation = false;
        } while (!validation);
        return ic;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n\t\t\t\t\tIC Number      : " + icNumber +
                "\n\t\t\t\t\tAddress        : " + address;
    }

    public Staff editInfo() {

        Staff newStaff = new Staff();

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\t\t\t\t\tYour details");

        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');
        System.out.println("\n" + this);
        System.out.println("\t\t\t\t\tPassword       : " + getPassword());
        System.out.println("\t\t\t\t\tSecurity question: " + getSecurityQuestion());
        System.out.println("\t\t\t\t\tSecurity answer: " + getSecurityAnswer());
        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');

        System.out.println("\n");

        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');
        System.out.println("\n\t\t\t\t\t1. Name"); //2
        System.out.println("\t\t\t\t\t2. IC number");//3
        System.out.println("\t\t\t\t\t3. Email"); //5
        System.out.println("\t\t\t\t\t4. Phone number"); //7
        System.out.println("\t\t\t\t\t5. Address"); //11
        System.out.println("\t\t\t\t\t6. Password"); //13
        System.out.println("\t\t\t\t\t7. Security question"); //17
        System.out.println("\t\t\t\t\t8. Security answer"); //19
        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');

        System.out.print("\n\t\t\t\t\tSelect which details you want to edit (Example: 1 3 4): ");
        String userEditSelection = scanner.nextLine().trim();


        int choiceCode = editChoiceCode(userEditSelection, 8);

        while (choiceCode == 0) {
            System.out.println("\n\t\t\t\t\tInvalid format has been detected, please follow the format! (Example: 1 3)"); //2
            System.out.print("\t\t\t\t\t");
            for (int i = 0; i < 35; i++) System.out.print('=');
            System.out.println("\n\t\t\t\t\t1. Name"); //2
            System.out.println("\t\t\t\t\t2. IC number");//3
            System.out.println("\t\t\t\t\t3. Email"); //5
            System.out.println("\t\t\t\t\t4. Phone number"); //7
            System.out.println("\t\t\t\t\t5. Address"); //11
            System.out.println("\t\t\t\t\t6. Password"); //13
            System.out.println("\t\t\t\t\t7. Security question"); //17
            System.out.println("\t\t\t\t\t8. Security answer"); //19
            System.out.print("\t\t\t\t\t");
            for (int i = 0; i < 35; i++) System.out.print('=');


            System.out.print("\n\t\t\t\t\tSelect which details you want to edit (Example: 1 3 4): ");
            userEditSelection = scanner.nextLine().trim();


            choiceCode = editChoiceCode(userEditSelection, 8);

        }


        //name
        if (choiceCode % 2 == 0) {
            newStaff.setName(inputName("\t\t\t\t\tEnter your new first name: ", "\t\t\t\t\tEnter your new last name: "));

        } else {
            newStaff.setName(getName());
        }

        //ic number
        if (choiceCode % 3 == 0) {
            newStaff.icNumber = inputICNumber("\t\t\t\t\tEnter your new IC number: ");

        } else newStaff.icNumber = getIcNumber();

        //email
        if (choiceCode % 5 == 0) {

            newStaff.setEmail(inputEmail("\t\t\t\t\tEnter your new email: "));


        } else newStaff.setEmail(getEmail());

        //phone number
        if (choiceCode % 7 == 0) {

            newStaff.setPhoneNumber(inputPhoneNumber("\t\t\t\t\tEnter your new phone number: "));


        } else newStaff.setPhoneNumber(getPhoneNumber());

        //address
        if (choiceCode % 11 == 0) {
            System.out.print("\t\t\t\t\tEnter your new address: ");
            newStaff.address = scanner.nextLine();
        } else newStaff.address = getAddress();

        //password
        if (choiceCode % 13 == 0) {


            boolean validPassword;

            System.out.println("\t\t\t\t\tYour password should be at least 8 character long and" +
                    " consist of at least one numeric character, uppercase and lowercase letter.\n" +
                    "\t\t\t\t\tExample: MasaruSuki0809");
            System.out.print("\t\t\t\t\tPassword: ");
            String password1 = scanner.nextLine();

            System.out.print("\t\t\t\t\tConfirm Password: ");
            String password2 = scanner.nextLine();

            if (password1.equals(password2) && !UserInfo.validPassword(password1)) {
                System.out.print("\n\t\t\t\t\tYour password doesn't match the requirement!");
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
                    System.out.print("\n\t\t\t\t\tYour password doesn't match the requirement!");
                    validPassword = false;
                } else validPassword = true;
            }
            newStaff.setPassword(password1);



        } else newStaff.setPassword(getPassword());

        //security question
        if (choiceCode % 17 == 0) {
            System.out.print("\t\t\t\t\tEnter your new security question: ");
            newStaff.securityQuestion = scanner.nextLine();
        } else newStaff.securityQuestion = getSecurityQuestion();

        //security answer
        if (choiceCode % 19 == 0) {
            System.out.print("\t\t\t\t\tEnter your new security answer: ");
            newStaff.securityAnswer = scanner.nextLine();
        } else newStaff.securityAnswer = getSecurityAnswer();

        newStaff.setUserID(getUserID());                        //initialized previous user id

        System.out.println("\n\t\t\t\t\tYour details");
        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');
        System.out.println("\n" + newStaff);
        System.out.println("\t\t\t\t\tPassword       : " + newStaff.getPassword());
        System.out.println("\t\t\t\t\tSecurity question: " + newStaff.getSecurityQuestion());
        System.out.println("\t\t\t\t\tSecurity answer: " + newStaff.getSecurityAnswer());
        System.out.print("\t\t\t\t\t");
        for (int i = 0; i < 35; i++) System.out.print('=');


        if (UserMain.returnYorNmethod("\n\t\t\t\t\tConfirm save your changes? ")) {


            System.out.println("\t\t\t\t\tChanges have been saved!");
            return newStaff;

        } else {
            System.out.println("\t\t\t\t\tChanges have not been saved...");
            return this;
        }


    }

}
