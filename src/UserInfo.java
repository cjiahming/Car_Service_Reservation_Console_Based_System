import java.util.Scanner;

public class UserInfo {
    private String userID;
    private Name name;
    private String email;
    private String phoneNumber;
    private String password;
    private boolean frozen = false;

    public UserInfo(String userID, Name name, String email, String phoneNumber, String password) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public boolean getFrozen() {
        return frozen;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public String toString() {
        return "\t\t\t\t\tUser ID        : " + userID +
                "\n\t\t\t\t\tName           : " + name +
                "\n\t\t\t\t\tEmail          : " + email +
                "\n\t\t\t\t\tPhone Number   : " + phoneNumber;

    }

    int editChoiceCode(String choiceString, int maxChoice) {
        int primeNumber = 1, indexLoop = 0;
        char choiceChar;
        do {
            try {
                choiceChar = choiceString.charAt(indexLoop);
            } catch (Exception exception) {

                return 0;
            }

            if (choiceChar == '1' && maxChoice >= Character.getNumericValue(choiceChar)) primeNumber *= 2;
            else if (choiceChar == '2' && maxChoice >= Character.getNumericValue(choiceChar)) primeNumber *= 3;
            else if (choiceChar == '3' && maxChoice >= Character.getNumericValue(choiceChar)) primeNumber *= 5;
            else if (choiceChar == '4' && maxChoice >= Character.getNumericValue(choiceChar)) primeNumber *= 7;
            else if (choiceChar == '5' && maxChoice >= Character.getNumericValue(choiceChar)) primeNumber *= 11;
            else if (choiceChar == '6' && maxChoice >= Character.getNumericValue(choiceChar)) primeNumber *= 13;
            else if (choiceChar == '7' && maxChoice >= Character.getNumericValue(choiceChar)) primeNumber *= 17;
            else if (choiceChar == '8' && maxChoice >= Character.getNumericValue(choiceChar)) primeNumber *= 19;

            else primeNumber = 0;


            if (primeNumber == 0) break;
            indexLoop += 2;

            //        6                   5
        } while (indexLoop <= choiceString.length());

        return primeNumber;
    }

    public static Name inputName(String firstNameQuestion, String lastNameQuestion) {
        Scanner scanner = new Scanner(System.in);
        boolean validation = true;
        String firstName, lastName;

        do {

            if (!validation) System.out.println("\n\t\t\t\t\tInvalid format, please try again!");
            System.out.print(firstNameQuestion);
            firstName = scanner.nextLine().trim();
            System.out.print(lastNameQuestion);
            lastName = scanner.nextLine().trim();
            try {

                for (int loop = 0; loop < firstName.length(); loop++) {
                    if (Character.isAlphabetic(firstName.charAt(loop)) || firstName.charAt(loop) == ' ' || firstName.charAt(loop) == '/') {
                        validation = true;
                    } else {
                        validation = false;
                        break;
                    }
                }
                if (validation) {
                    for (int loop = 0; loop < lastName.length(); loop++) {
                        if (Character.isAlphabetic(lastName.charAt(loop)) || lastName.charAt(loop) == ' ' || lastName.charAt(loop) == '/') {
                            validation = true;
                        } else {
                            validation = false;
                            break;
                        }
                    }
                }
            } catch (Exception exception) {

                System.out.println("\t\t\t\t\tEnter the name!");
                validation = false;
            }

        } while (!validation);

        return new Name(firstName, lastName);
    }

    public static String inputEmail(String question) {
        Scanner scanner = new Scanner(System.in);
        boolean validation = true;
        String email;

        do {
            if (!validation) System.out.println("\n\t\t\t\t\tInvalid format, please try again!");

            System.out.print(question);
            email = scanner.nextLine().trim();


            validation = email.contains("@") && email.contains(".com")
                    && email.charAt(0) != '@' && email.charAt(0) != '.';


        } while (!validation);

        return email;
    }

    public static String inputPhoneNumber(String question) {
        Scanner scanner = new Scanner(System.in);
        boolean validation = true;
        String phoneNumber;
        do {
            if (!validation) System.out.println("\n\t\t\t\t\tInvalid format, please try again!");

            System.out.print(question);
            phoneNumber = scanner.nextLine().trim();


            if ((phoneNumber.length() == 10 || phoneNumber.length() == 11) &&
                    phoneNumber.charAt(0) == '0' && phoneNumber.charAt(1) == '1') {


                for (int loop = 0; loop < phoneNumber.length(); loop++) {
                    if (Character.isDigit(phoneNumber.charAt(loop))) {
                        validation = true;
                    } else {
                        validation = false;
                        break;
                    }
                }

            } else validation = false;


        } while (!validation);

        return phoneNumber;
    }

    public static boolean validPassword(String password) {
        //consist of numeric, upper and lowercase alphabet, length >= 8
        boolean numeric = false, uppercase = false, lowercase = false;

        if (password.length() <= 6) return false;
        for (int loop = 0; loop < password.length(); loop++) {
            if (!numeric) if (Character.isDigit(password.charAt(loop))) numeric = true;
            if (Character.isAlphabetic(password.charAt(loop))) {
                if (!uppercase && Character.isUpperCase(password.charAt(loop))) uppercase = true;
                if (!lowercase && Character.isLowerCase(password.charAt(loop))) lowercase = true;
            }

        }
        return numeric && uppercase && lowercase;


    }
}
