package training;

import java.util.ArrayList;
import java.util.Arrays;

public class JavaRevision {
    public static void main(String[] args) {
        JavaRevision.dataTypes();
        JavaRevision.stringOperations();
    }

    public static void dataTypes() {
        String name = "John";
        Number age = 35;
        float salary = 1000.00f;
        boolean isUnder = true;

        System.out.println("\n----- Default DataTypes -----\n");
        System.out.println("Name: " + name + ", Age: " + age + ", Salary: " + salary + ", IsUnder: " + isUnder);

        // Array Types
        System.out.println("\n----- Array DataTypes -----\n");

        // Allocate memory for three elements (string type), with default values
        String[] cars1 = new String[3];
        cars1[0] = "Ferrari";

        // Initializes with values
        String[] cars2 = {"Ferrari", "Audi", "BMW"};

        for (int i = 0; i < cars1.length; i++) {
            System.out.println(cars1[i]);
        }

        for (String car : cars2) {
            System.out.println(car);
        }

        // Arraylist
        System.out.println("\n----- ArrayList DataTypes -----\n");

        ArrayList<String> cars3 = new ArrayList<String>(Arrays.asList(cars2));
        System.out.println("Getting the last element: " + cars3.get(cars1.length - 1));

        for (int i = 0; i < cars3.size(); i++) {
            System.out.println(cars3.get(i));
        }

        if (cars3.contains("Ferrari")) {
            System.out.println("Eureka");
        }
    }

    public static void stringOperations() {
        System.out.println("\n----- String Operations -----\n");
        // This is a string literal. It will be stored in the String Pool.
        // If another variable is assigned the same literal value, it will point to the same object.
        String name1 = "John Lucca";
        String name3 = "Petter Men";

        // This creates a new String object explicitly using the 'new' keyword.
        // Even though the content is the same, this object will be different in memory from 'name1' (not in the String Pool).
        String name2 = new String("John Lucca");


        String[] splittedName = name1.split(" ");
        System.out.println(Arrays.toString(splittedName));
        System.out.println(name1.equals(name2));
    }
}
