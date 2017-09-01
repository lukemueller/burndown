package io.pivotal.project.burndown;

import io.pivotal.project.ProjectEntity;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.assertThat;

public class BurndownCsvParserTest {

    @Test
    public void getBurndownForProjectEntity_subtractsWeeklyBurnFromWeeklyBurn() {
        WeeklySpendCalculator mockWeeklySpendCalculator = mock(WeeklySpendCalculator.class);
        LocalDate startDate = LocalDate.of(2017, 2, 6);

        HashMap<LocalDate, WeeklySpendPeriod> stubbedWeeklySpendMap = new HashMap<>();
        stubbedWeeklySpendMap.put(startDate.plusDays(7) , new WeeklySpendPeriod(50.00f, 2));
        stubbedWeeklySpendMap.put(startDate.plusDays(14),  new WeeklySpendPeriod(100.00f, 2));

        ProjectEntity project = new ProjectEntity();
        project.setName("some-project-name");
        project.setBudget(300);
        project.setStartDate(startDate);
        Mockito.when(
            mockWeeklySpendCalculator.getWeeklySpendAndStaffForProjectEntity(project)
        ).thenReturn(stubbedWeeklySpendMap);

        BurndownCsvParser parser = new BurndownCsvParser(mockWeeklySpendCalculator);
        List<BurndownPeriod> burndown = parser.getBurndownForProjectEntity(project);

        assertThat(burndown).containsExactly(
                new BurndownPeriod(startDate, 300f, 2),
                new BurndownPeriod(startDate.plusDays(7), 250f, 2),
                new BurndownPeriod(startDate.plusDays(14), 150f, 2)
        );
    }
}