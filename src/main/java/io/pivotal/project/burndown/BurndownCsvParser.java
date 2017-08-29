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
        ArrayList<Float> burndown = new ArrayList<>();
        float originalBudget = Float.parseFloat(String.valueOf(projectEntity.getBudget()));
        burndown.add(originalBudget);

        Map<LocalDate, Float> weeklySpend = weeklySpendCalculator.getWeeklySpendForProjectEntity(projectEntity);
        for (LocalDate weekStarting : weeklySpend.keySet()) {
            Float previousWeekRemainingBudget = burndown.get(burndown.size() - 1);
            burndown.add(previousWeekRemainingBudget - weeklySpend.get(weekStarting));
        }

        return burndown;
    }
}