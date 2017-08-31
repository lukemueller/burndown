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

        HashMap<LocalDate, Float> stubbedWeeklySpendMap = new HashMap<>();
        stubbedWeeklySpendMap.put(startDate.plusDays(7) , 50.00f);
        stubbedWeeklySpendMap.put(startDate.plusDays(14), 100.00f);

        ProjectEntity project = new ProjectEntity();
        project.setName("some-project-name");
        project.setBudget(300);
        project.setStartDate(startDate);
        Mockito.when(
            mockWeeklySpendCalculator.getWeeklySpendForProjectEntity(project)
        ).thenReturn(stubbedWeeklySpendMap);

        BurndownCsvParser parser = new BurndownCsvParser(mockWeeklySpendCalculator);
        List<BurndownEntity> burndown = parser.getBurndownForProjectEntity(project);

        assertThat(burndown).containsExactly(
                new BurndownEntity(startDate, 300f),
                new BurndownEntity(startDate.plusDays(7), 250f),
                new BurndownEntity(startDate.plusDays(14), 150f)
        );
    }
}