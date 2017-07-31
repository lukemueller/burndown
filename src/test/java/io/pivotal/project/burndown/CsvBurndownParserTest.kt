package io.pivotal.project.burndown

import io.pivotal.project.testhelpers.aProjectEntityWith
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.time.LocalDate
import java.util.*

class CsvBurndownParserTest {

    @Test
    fun getBurndownForProjectEntity_subtractsWeeklyBurnFromWeeklyBurn() {
        val mockWeeklySpendCalculator = mock<WeeklySpendCalculator>(WeeklySpendCalculator::class.java)
        val stubbedWeeklySpendMap = HashMap<LocalDate, Float>()
        stubbedWeeklySpendMap.put(LocalDate.of(2017, 2, 13), 50.00f)
        stubbedWeeklySpendMap.put(LocalDate.of(2017, 2, 20), 100.00f)

        val project = aProjectEntityWith(
                name = "some-project-name",
                budget = 300
        )

        Mockito.`when`(
                mockWeeklySpendCalculator.getWeeklySpendForProjectEntity(project)
        ).thenReturn(stubbedWeeklySpendMap)

        val parser = CsvBurndownParser(mockWeeklySpendCalculator)
        val burndown = parser.getBurndownForProjectEntity(project)

        assertThat(burndown).containsExactly(300f, 250f, 150f)
    }
}