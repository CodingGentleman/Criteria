package at.fhj.criteria;

import at.fhj.criteria.persistence.DatabaseManagementSystem;
import at.fhj.criteria.persistence.Persistence;
import at.fhj.criteria.persistence.PersistenceProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        var dbms = DatabaseManagementSystem.valueOf(args[0]);
        var jpaProvider = PersistenceProvider.valueOf(args[1]);
        Persistence.INST.register(dbms, jpaProvider);
        System.out.println("DB: "+dbms+", JPA:"+jpaProvider);
        System.out.println("Initialising Database ...");
        var useCases = new UseCases(Integer.parseInt(args[2]));
        var methodList = new LinkedList<>(Arrays.asList(UseCases.class.getDeclaredMethods()));
        filterMethods(methodList);
        while (true) {
            // System.out.print("\033[H\033[2J");
            // System.out.flush();

            System.out.println("\nEnter method to execute, 'auto' or 'exit': ");
            var selectedMethod = in.nextLine();
            if(selectedMethod.equals("exit")) {
                break;
            }
            if(selectedMethod.equals("auto")) {
                var performanceMap = new TreeMap<String, List<Long>>();
                for(int i = 0; i < 50; i++) {
                    for (var method : methodList) {
                        var values = performanceMap.getOrDefault(method.getName(), new ArrayList<>());
                        var time = executeMethod(useCases, method);
                        values.add(time);
                        performanceMap.put(method.getName(), values);
                    }
                }
                for(var key : performanceMap.keySet()) {
                    var avgNano = performanceMap.get(key).stream().mapToLong(i->i).average().getAsDouble();
                    System.out.println(key+"\t"+(avgNano/1000000));
                }
            } else {
                var method = methodList.stream().filter(m -> m.getName().equals(selectedMethod)).findFirst();
                if (method.isPresent()) {
                    var time = executeMethod(useCases, method.get());
                    System.out.println(selectedMethod + ":" + time);
                }
            }
        }
    }

    private static void filterMethods(List<Method> methodList) {
        var toRemove = new ArrayList<Method>();
        if(Persistence.INST.getPersistenceProvider().equals(PersistenceProvider.ECLIPSELINK)) {
            for (var method : methodList) {
                if(method.getName().toLowerCase().contains("cast")) {
                    toRemove.add(method);
                }
            }
        }
        methodList.removeAll(toRemove);
    }

    private static long executeMethod(UseCases useCases, Method method) {
        var time = System.nanoTime();
        Persistence.INST.inTransaction(() -> {
            try {
                method.invoke(useCases);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return System.nanoTime()-time;
    }
}
