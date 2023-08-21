public class Name {
    private String firstName;
    private String lastName;

    public Name() {
        this("XXX","XXX");
    }

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}

