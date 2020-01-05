package at.fhj.criteria;

import at.fhj.criteria.persistence.DatabaseManagementSystem;
import at.fhj.criteria.persistence.Persistence;
import at.fhj.criteria.persistence.PersistenceProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Persistence.INST.register(DatabaseManagementSystem.valueOf(args[0]), PersistenceProvider.valueOf(args[1]));
        System.out.println("Initialising Database ...");
        var useCases = new UseCases(Integer.parseInt(args[2]));
        var methodList = Arrays.asList(UseCases.class.getMethods());
        while (true) {
            // System.out.print("\033[H\033[2J");
            // System.out.flush();

            System.out.println("\nEnter method to execute or 'exit': ");
            var selectedMethod = in.nextLine();
            if(selectedMethod.equals("exit")) {
                break;
            }
            var method = methodList.stream().filter(m -> m.getName().equals(selectedMethod)).findFirst();
            if(method.isPresent()) {
                try {
                    System.out.println("Executing ...");
                    var time = System.currentTimeMillis();
                    method.get().invoke(useCases);
                    time = System.currentTimeMillis()-time;
                    System.out.println("Execution took "+time+"ms. ");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
