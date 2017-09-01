package io.pivotal.project.burndown;

import io.pivotal.project.Project;

import java.time.LocalDate;

/**
 * Created by devondapuzzo on 8/31/17.
 */
public class BurndownProjectionModel {
    private static Integer HOURS_PER_DAY = 8;

    public static void buildOut(Project project){
        //TODO- CHECK ASSUMPTION THAT LAS BURNDOWN WILL ALWAYS REPRESENT A MONDAY FIGURE
        assert(project.getBurndown().size() > 0);
        assert(project.getNumberOfEmployees() > 0);

        BurndownPeriod remainingBudget = project.getBurndown().get(project.getBurndown().size()-1);
        Integer costPerDay = project.getHourlyRate() *  HOURS_PER_DAY * project.getNumberOfEmployees();
        Integer costPerWeek = costPerDay * 5;

        Integer weeksLeft = (int) remainingBudget.getBudgetRemaining()/ costPerWeek;
        Integer daysLeft = (int) (remainingBudget.getBudgetRemaining() % costPerWeek) / costPerDay;

        LocalDate projectedEndDate = remainingBudget.getDate();
        projectedEndDate = projectedEndDate.plusWeeks(weeksLeft);
        if(daysLeft == 0){
            projectedEndDate = projectedEndDate.minusDays(2);
        }
        projectedEndDate = projectedEndDate.plusDays(daysLeft);

        project.setProjectedEndDate(projectedEndDate);
    }

}
