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

    public List<BurndownPeriod> getBurndownForProjectEntity(ProjectEntity projectEntity) {
        Map<LocalDate, WeeklySpendPeriod> weeklySpend = weeklySpendCalculator.getWeeklySpendAndStaffForProjectEntity(projectEntity);

        ArrayList<BurndownPeriod> burndown = new ArrayList<>();
        float originalBudget = Float.parseFloat(String.valueOf(projectEntity.getBudget()));
        burndown.add(new BurndownPeriod(projectEntity.getStartDate(), originalBudget, 1));

        for (LocalDate weekStarting : weeklySpend.keySet()) {
            WeeklySpendPeriod week = weeklySpend.get(weekStarting);

            Float nextBudgetRemaining = burndown.get(burndown.size() - 1).getBudgetRemaining()- week.getWeeklySpend() ;
            LocalDate nextWeekDate = burndown.get(burndown.size() - 1).getDate().plusDays(7);

            burndown.add(new BurndownPeriod(nextWeekDate, nextBudgetRemaining, week.getNumberOfEmployees()));
        }

        if(burndown.size() > 1) burndown.get(0).setNumberOfEmployees(burndown.get(1).getNumberOfEmployees());

        return burndown;
    }
}