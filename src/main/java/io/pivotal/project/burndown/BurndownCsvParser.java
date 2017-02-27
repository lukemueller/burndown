package io.pivotal.project.burndown;

import io.pivotal.project.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BurndownCsvParser {

    private WeeklySpendCalculator weeklySpendCalculator;

    @Autowired
    public BurndownCsvParser(WeeklySpendCalculator weeklySpendCalculator) {
        this.weeklySpendCalculator = weeklySpendCalculator;
    }

    public List<Float> getBurndownForProjectEntity(ProjectEntity projectEntity) {
        Map<LocalDate, Float> weeklySpend = weeklySpendCalculator.getWeeklySpendForProjectEntity(projectEntity);

        float originalBudget = Float.parseFloat(String.valueOf(projectEntity.getBudget()));

        ArrayList<Float> burndown = new ArrayList<>();
        burndown.add(originalBudget);

        for (LocalDate weekStarting : weeklySpend.keySet()) {
            Float previousWeekRemainingBudget = burndown.get(burndown.size() - 1);
            burndown.add(previousWeekRemainingBudget - weeklySpend.get(weekStarting));
        }

        return burndown;
    }
}