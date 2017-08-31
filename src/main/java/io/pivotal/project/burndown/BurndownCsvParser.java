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

    public List<BurndownEntity> getBurndownForProjectEntity(ProjectEntity projectEntity) {
        ArrayList<BurndownEntity> burndown = new ArrayList<>();
        float originalBudget = Float.parseFloat(String.valueOf(projectEntity.getBudget()));
        burndown.add(new BurndownEntity(projectEntity.getStartDate(), originalBudget));

        Map<LocalDate, Float> weeklySpend = weeklySpendCalculator.getWeeklySpendForProjectEntity(projectEntity);
        for (LocalDate weekStarting : weeklySpend.keySet()) {
            Float nextBudgetRemaining = burndown.get(burndown.size() - 1).getBudgetRemaining()-  weeklySpend.get(weekStarting);
            LocalDate nextWeekDate = burndown.get(burndown.size() - 1).getDate().plusDays(7);
            burndown.add(new BurndownEntity(nextWeekDate, nextBudgetRemaining));
        }

        return burndown;
    }
}