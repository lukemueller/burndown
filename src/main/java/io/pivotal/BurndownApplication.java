package io.pivotal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class BurndownApplication {

    public static void main(String[] args) {
        SpringApplication.run(BurndownApplication.class);
    }

//    private static final Integer totalBudget = 697467;
//    private static List<Integer> weeklyConsumption = new ArrayList<>();
//    private static Random random = new Random();
//
//    public static void main(String[] args) {
//        while (thereIsStillBudget()) {
//            int next = random.nextInt(40000);
//            System.out.println("next = " + next);
//            weeklyConsumption.add(next);
//        }
//
//        ArrayList<Integer> burndown = new ArrayList<>();
//        int remainingBudget = totalBudget;
//        for (Integer datum : weeklyConsumption) {
//            remainingBudget -= datum;
//            burndown.add(remainingBudget);
//        }
//
//        System.out.println("burndown = " + burndown);
//        System.out.println("burndown.size() = " + burndown.size());
//    }
//
//    private static boolean thereIsStillBudget() {
//        Integer usedBudget = 0;
//        for (Integer datum : weeklyConsumption) {
//            usedBudget += datum;
//        }
//
//        return usedBudget <= totalBudget;
//    }
}
