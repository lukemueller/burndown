package io.pivotal.project.burndown;

import io.pivotal.project.ProjectEntity;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WeeklySpendCalculatorTest {

    @Test
    public void getWeeklySpendForProjectByName() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName("test-project-name");
        projectEntity.setHourlyRate(10);
        projectEntity.setStartDate(LocalDate.of(2015, 10, 26));

        WeeklySpendCalculator calculator = new WeeklySpendCalculator();
        Map<LocalDate, WeeklySpendPeriod> weeklySpend = calculator.getWeeklySpendAndStaffForProjectEntity(projectEntity);

        assertThat(weeklySpend).hasSize(4);

        assertThat(weeklySpend.get(LocalDate.of(2015, 10, 26)).getWeeklySpend()).isEqualTo(60f);
        assertThat(weeklySpend.get(LocalDate.of(2015, 11, 18)).getWeeklySpend()).isEqualTo(110f);
        assertThat(weeklySpend.get(LocalDate.of(2015, 11, 25)).getWeeklySpend()).isEqualTo(60f);
        assertThat(weeklySpend.get(LocalDate.of(2015, 12, 02)).getWeeklySpend()).isEqualTo(50f);

        assertThat(weeklySpend.get(LocalDate.of(2015, 10, 26)).getNumberOfEmployees()).isEqualTo(2);
        assertThat(weeklySpend.get(LocalDate.of(2015, 11, 18)).getNumberOfEmployees()).isEqualTo(2);
        assertThat(weeklySpend.get(LocalDate.of(2015, 11, 25)).getNumberOfEmployees()).isEqualTo(2);
        assertThat(weeklySpend.get(LocalDate.of(2015, 12, 02)).getNumberOfEmployees()).isEqualTo(1);

    }
}