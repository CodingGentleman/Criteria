package at.fhj.criteria;

import at.fhj.criteria.persistence.PersistenceReader;

import java.util.Scanner;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        var running = true;
        var scanner = new Scanner(System.in);
        var persistenceNames = PersistenceReader.getPersistenceUnitNames().collect(Collectors.toList());

        while(running) {
            System.out.println("Criteria Benchmarks\nEnter the number of the command to run:\n\n1\texit\n");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1": running = false; break;
                default: break;
            }
        }
    }
}
