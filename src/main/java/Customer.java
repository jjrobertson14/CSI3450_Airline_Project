import java.sql.Date;
import java.sql.Timestamp;
import java.util.Random;

/**
 * This class is the java object mapping of Customer entities from the database
 * Name: Customer.java
 * Location src/main/java
 * Date: November 28th 2017
 * @author Noah
 *
 */
public class Customer {

    private static String[] firstNames = {
        "Andrew",
        "Alice",
        "Andy",
        "Betty",
        "Bob",
        "Brian",
        "Bridgett",
        "Brenda",
        "Boris",
        "Carson",
        "Christian",
        "Clarice",
        "Clare",
        "Candice",
        "Christopher",
        "Danny",
        "Danielle",
        "David",
        "Dennis",
        "Dmitry",
        "Doreen",
        "Greg",
        "Gary",
        "Gwen",
        "Jason",
        "Janice",
        "Jared",
        "James",
        "Jill",
        "John",
        "Joy",
        "Jose",
        "Kristin",
        "Kristof",
        "Lori",
        "Laura",
        "Leo",
        "Ron",
        "Megan",
        "Mohammed",
        "Noah",
        "Nolan"
    };

    private static String[] lastNames = {
        "Ahmed",
        "Anderson",
        "Anders",
        "Andrades",
        "Anderhoff",
        "Bellset",
        "Bellinger",
        "Bowton",
        "Bourque",
        "Brown",
        "Brzinski",
        "Che",
        "Cooper",
        "Deel",
        "Guerrero",
        "Immekus",
        "Jones",
        "Jonas",
        "Keeye",
        "Klaus",
        "Malfoy",
        "Marita",
        "Nicol",
        "Nichols",
        "Picasso",
        "Potter",
        "Rohrbeck",
        "Simpson",
        "Schoonover",
        "Stephanopolous",
        "Stroh",
        "Yousef",
        "White",
        "Zimmerman"
    };

    private int id; 

    private String firstName; 

    private String lastName; 

    private java.sql.Date birthDate; 

    private boolean isMember;

    private boolean wheelchair;

    private boolean oxygen;

    public Customer() {

    }

    public Customer(
            int id, 
            String firstName,
            String lastName,
            java.sql.Date birthDate,
            boolean isMember,
            boolean wheelchair,
            boolean oxygen
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.isMember = isMember;
        this.wheelchair = wheelchair;
        this.oxygen = oxygen;
    }

    public int getID() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() { 
        return lastName;
    }

    public java.sql.Date getBirthDate() {
        return birthDate;
    }

    public boolean getIsMember() {
        return isMember;
    }

    public boolean getWheelchair() {
        return wheelchair;
    }

    public boolean getOxygen() {
        return oxygen;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(java.sql.Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setIsMember(boolean isMember) { 
        this.isMember = isMember;
    }

    public void setWheelchair(boolean wheelchair) {
        this.wheelchair = wheelchair;
    }

    public void setOxygen(boolean oxygen) {
        this.oxygen = oxygen;
    }
    
    @Override
    public String toString() {
    	String res = "Customer " + id + ": " + firstName + " " + lastName;
    	return res;
    }

    /**
     * Generate 50 random customers, with no customerID
     */
    public static Customer[] generateRandomCustomers() {
        Customer[] customers = new Customer[50];
        Random rand = new Random();

        for (int i = 0; i < 50; i++) {
            Customer c = new Customer();

            c.firstName = firstNames[rand.nextInt(firstNames.length)];
            c.lastName  = lastNames[rand.nextInt(lastNames.length)];
            c.isMember = rand.nextBoolean();
            c.wheelchair = rand.nextInt() % 15 == 0;
            c.oxygen = rand.nextInt() % 15 == 0;

            long offset = Timestamp.valueOf("1920-01-01 00:00:00").getTime();
            long end    = Timestamp.valueOf("2017-01-01 00:00:00").getTime();
            long diff   = end - offset + 1;

            c.birthDate = new Date(offset + (long)(Math.random() * diff));

            customers[i] = c;
            System.out.println("Customer created.");
        }

        return customers;
    }
}