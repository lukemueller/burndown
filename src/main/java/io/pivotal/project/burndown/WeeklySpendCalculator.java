package io.pivotal.project.burndown;

import au.com.bytecode.opencsv.CSVReader;
import io.pivotal.project.ProjectEntity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Component
class WeeklySpendCalculator {

    Map<LocalDate, Float> getWeeklySpendForProjectEntity(ProjectEntity projectEntity) {

        ClassPathResource classPathResource = new ClassPathResource(projectEntity.getName() + ".csv");
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(classPathResource.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<LocalDate, Float> weeklySpendInHours = new HashMap<>();
        try {
            String[] nextLine;
            reader.readNext(); // ignore header line
            while ((nextLine = reader.readNext()) != null) {
                LocalDate key = LocalDate.parse(nextLine[0]);
                if (key.isBefore(projectEntity.getStartDate())) {
                    continue;
                }
                float value = Float.parseFloat(nextLine[5]);

                Float existingValue = weeklySpendInHours.getOrDefault(key, 0f);
                weeklySpendInHours.put(key, existingValue + value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        TreeMap<LocalDate, Float> weeklySpendInDollars = new TreeMap<>();
        for (LocalDate key : weeklySpendInHours.keySet()) {
            float spendInWeek = weeklySpendInHours.get(key) * projectEntity.getHourlyRate();
            weeklySpendInDollars.put(key, spendInWeek);
        }

        return weeklySpendInDollars;
    }
}
