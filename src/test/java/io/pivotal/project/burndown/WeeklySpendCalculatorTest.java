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
        Map<LocalDate, Float> weeklySpend = calculator.getWeeklySpendForProjectEntity(projectEntity);

        assertThat(weeklySpend).hasSize(3);
        assertThat(weeklySpend.get(LocalDate.of(2015, 10, 26))).isEqualTo(60f);
        assertThat(weeklySpend.get(LocalDate.of(2015, 11, 18))).isEqualTo(110f);
        assertThat(weeklySpend.get(LocalDate.of(2015, 11, 25))).isEqualTo(60f);
    }
}