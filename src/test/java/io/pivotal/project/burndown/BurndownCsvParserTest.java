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
        HashMap<LocalDate, Float> stubbedWeeklySpendMap = new HashMap<>();
        stubbedWeeklySpendMap.put(LocalDate.of(2017, 2, 13), 50.00f);
        stubbedWeeklySpendMap.put(LocalDate.of(2017, 2, 20), 100.00f);

        ProjectEntity project = new ProjectEntity();
        project.setName("some-project-name");
        project.setBudget(300);
        Mockito.when(
            mockWeeklySpendCalculator.getWeeklySpendForProjectEntity(project)
        ).thenReturn(stubbedWeeklySpendMap);

        BurndownCsvParser parser = new BurndownCsvParser(mockWeeklySpendCalculator);
        List<Float> burndown = parser.getBurndownForProjectEntity(project);

        assertThat(burndown).containsExactly(300f, 250f, 150f);
    }
}