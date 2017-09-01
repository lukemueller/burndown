package io.pivotal.project.burndown;

import au.com.bytecode.opencsv.CSVReader;
import io.pivotal.project.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Component
class WeeklySpendCalculator {

    Map<LocalDate, WeeklySpendPeriod> getWeeklySpendAndStaffForProjectEntity(ProjectEntity projectEntity) {
        ClassPathResource classPathResource = new ClassPathResource(projectEntity.getName() + ".csv");
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(classPathResource.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }

        HashMap<LocalDate, WeeklySpendPeriod> weeklySpendInHours = new HashMap<>();

        try {
            String[] nextLine;
            reader.readNext(); // ignore header line
            while ((nextLine = reader.readNext()) != null) {
                LocalDate key = LocalDate.parse(nextLine[0]);
                if (key.isBefore(projectEntity.getStartDate())) {
                    continue;
                }
                float value = Float.parseFloat(nextLine[5]);


                WeeklySpendPeriod existingWeeklySpend = weeklySpendInHours.getOrDefault(key, new WeeklySpendPeriod(0f, 0));
                Float weeklySpend = existingWeeklySpend.getWeeklySpend();
                Integer numberOfEmployees = existingWeeklySpend.getNumberOfEmployees();

                weeklySpendInHours.put(key, new WeeklySpendPeriod(weeklySpend + value, numberOfEmployees+1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        TreeMap<LocalDate, WeeklySpendPeriod> weeklySpendInDollars = new TreeMap<>();
        for (LocalDate key : weeklySpendInHours.keySet()) {
            float spendInWeek = weeklySpendInHours.get(key).getWeeklySpend() * projectEntity.getHourlyRate();
            weeklySpendInDollars.put(key, new WeeklySpendPeriod(spendInWeek, weeklySpendInHours.get(key).getNumberOfEmployees()));
        }

        return weeklySpendInDollars;
    }
}
