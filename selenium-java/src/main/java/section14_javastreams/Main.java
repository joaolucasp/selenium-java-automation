package section14_javastreams;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    @Test
    public static void main(String[] args) {
        // Count the number of names starting with alphabet A in lista

        List<String> names = new ArrayList<String>();
        names.add("John");
        names.add("Jane");
        names.add("Bob");
        names.add("Adam");
        names.add("Mary");
        names.add("Jane");
        names.add("Jack");
        names.add("Alex");

        streamFilter(names);
        streamForEachAndLimit(names);
        streamMap(names);
        streamSorted(names);
        streamCollection(names);
    }

    private static void streamFilter(List<String> names) {
        System.out.println("\n----- Filter Method -----X\n");

        Long count = names.stream().filter(name -> name.startsWith("A")).count();

        System.out.println("Count is: " + count);
    }

    private static void streamForEachAndLimit(List<String> names) {
        System.out.println("\nX----- Foreach and limit Method -----X\n");

        names.stream().filter(name -> name.startsWith("A")).limit(1).forEach(name -> {
            System.out.println("Name is: " + name);
        });
    }

    private static void streamMap(List<String> names) {
        System.out.println("\nX----- Map Method -----X\n");

        List<String> namesUppercase = names.stream().filter(name -> name.startsWith("A")).map(name -> {
            return name.toUpperCase();
        }).toList();

        for (String name : namesUppercase) {
            System.out.println("Name is: " + name);
        }
    }

    private static void streamSorted(List<String> names) {
        System.out.println("\nX----- Sorted Method -----X\n");

        List<String> namesSorted = names.stream().sorted().toList();

        System.out.println(namesSorted.toString());
    }

    private static void streamCollection(List<String> names) {
        System.out.println("\nX----- Collection Method -----X\n");

        System.out.println("Original size is: " + names.size());

        long uniqueNamesSize = names.stream().distinct().count();

        System.out.println("Unique names size is: " + uniqueNamesSize);
    }
}
